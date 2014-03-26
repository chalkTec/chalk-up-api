package chalkup.gym

class Location {

    static belongsTo = [route: Route]

    static constraints = {
        floorPlan validator: { floorPlan, location ->
            return location.route.gym.floorPlans.contains(floorPlan)
        }
        x min: 0.0d, max: 1.0d
        y min: 0.0d, max: 1.0d
    }

    FloorPlan floorPlan

    double x, y

}
