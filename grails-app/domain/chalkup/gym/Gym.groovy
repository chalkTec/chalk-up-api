package chalkup.gym

class Gym {

    String name

    static hasMany = [floorPlans: FloorPlan]

    static constraints = {
        name blank: false
    }

    Date dateCreated
    Date lastUpdated

    Gym(String name) {
        this.name = name
        this.floorPlans = [] as Set
    }

}
