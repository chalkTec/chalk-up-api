package chalkup.gym

class Rating {

    static constraints = {
        value min: 1, max: 5
    }

    static belongsTo = [route: Route]

    int value
    Date dateCreated

}
