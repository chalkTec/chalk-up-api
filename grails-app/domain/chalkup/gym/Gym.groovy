package chalkup.gym

class Gym {

    String name

    static hasMany = [floorPlans: FloorPlan, routes: Route]

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        floorPlans lazy: false
    }


    Date dateCreated
    Date lastUpdated

    Gym(String name) {
        this.name = name
        this.floorPlans = [] as Set
        this.routes = [] as Set
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Gym)) return false

        Gym gym = (Gym) o

        if (name != gym.name) return false

        return true
    }

    int hashCode() {
        return name.hashCode()
    }
}
