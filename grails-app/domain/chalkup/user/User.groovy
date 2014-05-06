package chalkup.user

import chalkup.gym.Gym
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
    boolean locked
    Collection<? extends GrantedAuthority> authorities

    Long gymId

    public boolean hasRole(String role) {
        return getAuthorities().any {
            it.getAuthority().equals(role)
        }
    }

    public boolean canEdit(Gym gym) {
        if(hasRole('admin')) {
            return true
        }
        else if(hasRole('route_setter')) {
            return getGymId() == gym.id
        }
        else {
            return false
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
