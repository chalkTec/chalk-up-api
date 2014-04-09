package chalkup.gym

abstract class Route<GradeSystem> {

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
        dateSet nullable: true

        initialGradeLow nullable: true
        initialGradeHigh nullable: true
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

    Date dateSet

    Date end

    String description

    public final void location(FloorPlan floorPlan, double x, double y) {
        location = new Location(floorPlan: floorPlan, x: x, y: y)
        location.route = this
    }

    // will be interpreted by subclasses
    Double initialGradeLow, initialGradeHigh


    public GradeCertainty getInitialGradeCertainty() {
        if(initialGradeLow == null && initialGradeHigh == null) {
            return GradeCertainty.UNKNOWN
        }
        else if(initialGradeHigh == null) {
            return GradeCertainty.ASSIGNED
        }
        else {
            return GradeCertainty.RANGE
        }
    }

    public double getGradeValue() {
        switch(getInitialGradeCertainty()) {
            case GradeCertainty.UNKNOWN:
                throw new RuntimeException("there is no value for unknown grade")
            case GradeCertainty.ASSIGNED:
                return initialGradeLow
            case GradeCertainty.RANGE:
                return initialGradeLow + (initialGradeHigh - initialGradeLow) / 2.0;
        }
    }

    public final void assignedGrade(GradeSystem grade) {
        initialGradeLow = grade.value
        initialGradeHigh = null
    }

    protected abstract GradeSystem gradeForValue(double value);


    public GradeSystem getAssignedGrade() {
        return gradeForValue(initialGradeLow)
    }

    public final void gradeRange(GradeSystem gradeRangeLow, GradeSystem gradeRangeHigh) {
        initialGradeLow = gradeRangeLow.value
        initialGradeHigh = gradeRangeHigh.value
    }

    public GradeSystem getGradeRangeLow() {
        return gradeForValue(initialGradeLow)
    }

    public GradeSystem getGradeRangeHigh() {
        return gradeForValue(initialGradeHigh)
    }

    public final void unknownGrade() {
        initialGradeLow = null
        initialGradeHigh = null
    }

    public abstract String getReadableInitialGrade();

}
