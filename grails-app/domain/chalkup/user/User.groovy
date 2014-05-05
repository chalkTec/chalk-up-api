package chalkup.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User implements UserDetails {

    static mapWith = "none"

    /// creates a dummy user that indicates that no user with this ID is found at userapp
    static User createUserNotExistingAtUserapp(String userId) {
        return new User(id: userId, username: 'N/A', nickname: 'N/A', email: 'N/A')
    }

    String id
    String username
    String email
    String nickname
    boolean locked
    Collection<? extends GrantedAuthority> authorities

    Long gymId

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
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return locked
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
