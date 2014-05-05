package chalkup.userapp

import chalkup.gym.Gym
import chalkup.user.User
import io.userapp.client.UserApp
import io.userapp.client.exceptions.UserAppException
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserappService {

    def grailsApplication

    private User extractUser(Map<?,?> userResult) {
        // return something that at least has the following properties
        // - password
        // - authorities

        // in addition to that, a reference to a gym is required, and the user_id of userapp.io might be included

        User user = new User();
        user.id = userResult.get('user_id')

        if (userResult.get('email'))
            user.email = userResult.get('email')
        else
            user.email = "N/A"

        user.authorities = userResult.get('permissions').entrySet().grep {
            return it.value.get('value') == true
        }.collect {
            return new SimpleGrantedAuthority(it.key)
        }

        user.nickname = userResult.get('properties').get('nickname').get('value')
        user.gymId = Integer.valueOf(userResult.get('properties').get('gym').get('value'))

        user.username = userResult.get('login')
        user.locked = Boolean.valueOf(userResult.get('lock'))

        return user
    }

    private UserApp.API createApi(String tokenValue) {
        UserApp.API api = new UserApp.API(grailsApplication.config.userapp.appId)
        UserApp.ClientOptions options = api.getOptions()
        options.token = tokenValue
        options.debug = grailsApplication.config.userapp.debug
        api.setOptions(options)
        return api
    }

    User getUserByToken(String tokenValue) throws UserAppException {
        UserApp.API api = createApi(tokenValue)

        UserApp.Result result = api.method("user.get")
                .parameter("user_id", "self")
                .call();

        def user = result.get(0)

        return extractUser(user.toHashMap())
    }

    User getUserById(String userId) throws UserAppException {
        UserApp.API api = createApi(grailsApplication.config.userapp.apiToken)

        UserApp.Result result = api.method("user.get")
                .parameter("user_id", userId)
                .call();

        def user = result.get(0)

        return extractUser(user.toHashMap())
    }


    Set<User> listRouteSettersForGym(Gym gym) throws UserAppException {
        UserApp.API api = createApi(grailsApplication.config.userapp.apiToken)

        // no idea why the second quotes for query are need...
        UserApp.Result result = api.method("user.search")
                .parameter("filters", new UserApp.Struct().parameter("query", "\"properties.gym:${gym.id}\""))
                .parameter("fields", "*")
                .call();


        def items = result.get('items').toArray()
        List<User> routeSetters = items.collect {
            return extractUser(it)
        }

        return routeSetters
    }

}
