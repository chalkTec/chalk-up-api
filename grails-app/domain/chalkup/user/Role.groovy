package chalkup.user

class Role {

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority unique: true
	}
}
