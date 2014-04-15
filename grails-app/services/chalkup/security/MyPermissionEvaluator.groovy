package chalkup.security

import chalkup.gym.Gym
import chalkup.user.User
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication

public class MyPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if(targetDomainObject instanceof Gym) {
            User user = User.findById(authentication.principal.id)
            return user.mayAdministrate(targetDomainObject)
		}
	    else {
			throw new UnsupportedOperationException()
		}
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
	    try {
		    return hasPermission(authentication, Class.forName(targetType).findById(targetId), permission)
	    }
	    catch (ClassNotFoundException e) {
		    throw new RuntimeException(e)
	    }
    }
}
