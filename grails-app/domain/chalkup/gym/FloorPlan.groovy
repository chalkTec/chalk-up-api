package chalkup.gym

class FloorPlan {

    static belongsTo = [gym: Gym]

    int widthInPx, heightInPx
    String imageUrl

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof FloorPlan)) return false

        FloorPlan floorPlan = (FloorPlan) o

        if (imageUrl != floorPlan.imageUrl) return false

        return true
    }

    int hashCode() {
        return (imageUrl != null ? imageUrl.hashCode() : 0)
    }
}
