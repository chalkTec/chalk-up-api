package chalkup.security

import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import io.userapp.client.UserApp
import io.userapp.client.exceptions.ServiceException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails;


class UserappTokenStorageService implements TokenStorageService {

    public static final String USERAPP_PERMISSION_ROUTE_SETTER = 'route_setter'
    public static final String USERAPP_PERMISSION_ADMIN = 'admin'
    public static final String AUTHORITY_ROUTE_SETTER = 'ROLE_ROUTE_SETTER'
    public static final String AUTHORITY_ADMIN = 'ROLE_ADMIN'

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
            // user.get('permissions').get('route_setter').get('value').toBoolean()
            // user.get('permissions').get('admin').get('value').toBoolean()

            // in addition to that, a reference to a gym is required


            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            if (user.get('permissions').get(USERAPP_PERMISSION_ROUTE_SETTER).get('value').toBoolean())
                authorities.add(new SimpleGrantedAuthority(AUTHORITY_ROUTE_SETTER))
            if (user.get('permissions').get(USERAPP_PERMISSION_ADMIN).get('value').toBoolean()) {
                authorities.add(new SimpleGrantedAuthority(AUTHORITY_ADMIN))
            }

            return new UserDetails() {

                String getId() {
                    return user.get('user_id').toString()
                }

                @Override
                Collection<? extends GrantedAuthority> getAuthorities() {
                    return authorities
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
