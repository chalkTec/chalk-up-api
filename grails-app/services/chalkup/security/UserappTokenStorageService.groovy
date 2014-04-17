package chalkup.security

import chalkup.gym.Gym
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import io.userapp.client.UserApp
import io.userapp.client.exceptions.ServiceException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails;


class UserappTokenStorageService implements TokenStorageService {

    @Override
    Object loadUserByToken(String tokenValue) throws TokenNotFoundException {
        UserApp.API api = new UserApp.API("534e382ac8f18");
        UserApp.ClientOptions options = api.getOptions();
        options.token = tokenValue;
        api.setOptions(options);

        try {
            UserApp.Result result = api.method("user.get")
                    .parameter("user_id", "self")
                    .call();

            def user = result.get(0)

            // return something that at least has the following properties
            // - password
            // - authorities

            // in addition to that, a reference to a gym is required, and the user_id of userapp.io might be included


            Set<GrantedAuthority> authorities = user.get('permissions').toHashMap().entrySet().grep {
                return it.value.get('value') == true
            }.collect {
                return new SimpleGrantedAuthority(it.key)
            }

            return new UserDetails() {

                String getEmail() {
                    if(user.get('email').exists())
                        return user.get('email').toString()
                    else
                        return "N/A"
                }

                long getGymId(Gym gym) {
                    return user.get('properties').get('gym').get('value').toInteger()
                }

                String getId() {
                    return user.get('user_id').toString()
                }

                @Override
                Collection<? extends GrantedAuthority> getAuthorities() {
                    return authorities
                }

                public boolean hasRole(String role) {
                    return getAuthorities().any {
                        it.getAuthority().equals(role)
                    }
                }

                @Override
                String getPassword() {
                    return "N/A"
                }

                @Override
                String getUsername() {
                    return user.get('login').toString()
                }

                @Override
                boolean isAccountNonExpired() {
                    return true
                }

                @Override
                boolean isAccountNonLocked() {
                    return !user.get('lock').toBoolean()
                }

                @Override
                boolean isCredentialsNonExpired() {
                    return true
                }

                @Override
                boolean isEnabled() {
                    return true
                }

                public String toString() {
                    return getEmail()
                }
            }
        }
        catch (ServiceException exception) {
            log.error(exception)
            throw new TokenNotFoundException(exception.getMessage())
        }
    }

    @Override
    void storeToken(String tokenValue, Object principal) {
        throw new UnsupportedOperationException("cannot store tokens, they are generated with userapp")
    }

    @Override
    void removeToken(String tokenValue) throws TokenNotFoundException {
        throw new UnsupportedOperationException("cannot store tokens, they are generated with userapp")
    }
}
