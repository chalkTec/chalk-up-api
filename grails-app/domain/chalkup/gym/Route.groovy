package chalkup.gym

abstract class Route {

    public enum GradeCertainty {
        UNKNOWN, RANGE, ASSIGNED
    }

    static mapping = {
        end column: '"end"'
        discriminator column: "route_type"
        location fetch: 'join'
    }

    static constraints = {
        foreignId nullable: true, unique: 'gym'

        name nullable: true
        number nullable: true
        description nullable: true
        end nullable: true
    }

    static belongsTo = [gym: Gym]

    static hasOne = [location: Location]


    String name

    String number

    Long foreignId

    /**
     * Either the color of the holds and feet or any other marks to distinguish this route from others.
     */
    RouteColor color

    Date dateCreated
    Date lastUpdated


    Date end

    String description

    public final void location(FloorPlan floorPlan, double x, double y) {
        location = new Location(floorPlan: floorPlan, x: x, y: y)
        location.route = this
    }

}
