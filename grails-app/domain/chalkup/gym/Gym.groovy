package chalkup.gym

import chalkup.user.User
import io.userapp.client.exceptions.UserAppException

class Gym {

    def userappService

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

    Set<User> getRouteSetters() {
        try {
            return userappService.listRouteSettersForGym(this)
        }
        catch(UserAppException e) {
            log.error("could not retrieve list of route setters for gym $this", e)
            return []
        }
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
