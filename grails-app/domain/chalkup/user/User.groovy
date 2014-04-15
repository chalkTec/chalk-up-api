package chalkup.user

import chalkup.gym.Gym

class User {

    transient springSecurityService

    String email
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date dateCreated

    /// the gym that the user is allowed to administrate
    Gym gym

    static transients = ['springSecurityService']

    static constraints = {
        email unique: true
        gym nullable: true
    }

    static mapping = {
        table '"user"'
        password column: '"password"'
    }

    boolean mayAdministrate(Gym gym) {
        return gym.equals(this.gym)
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
