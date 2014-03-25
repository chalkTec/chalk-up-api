package chalkup.gym

class FloorPlan {

    static belongsTo = [gym: Gym]

    int widthInPx, heightInPx
    String imageUrl

}
