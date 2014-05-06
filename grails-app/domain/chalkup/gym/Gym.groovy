package chalkup.gym

import chalkup.user.RouteSetter

class Gym {

    def userappService

    String name

    static hasMany = [floorPlans: FloorPlan, routes: Route, routeSetters: RouteSetter]

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

    @Override
    public String toString() {
        return name;
    }
}
