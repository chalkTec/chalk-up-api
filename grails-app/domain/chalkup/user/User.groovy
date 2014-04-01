package chalkup.user

class User {

    transient springSecurityService

    String email
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date dateCreated

    static transients = ['springSecurityService']

    static constraints = {
        email unique: true
    }

    static mapping = {
        table '"user"'
        password column: '"password"'
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
