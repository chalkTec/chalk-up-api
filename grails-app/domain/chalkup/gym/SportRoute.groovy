package chalkup.gym

class SportRoute extends Route {

    static mapping = {
        discriminator "sport-route"
    }

    static embedded = ['initialGrade']


    SportGrade initialGrade

    public SportRoute() {
        this.initialGrade = SportGrade.zero()
    }

}
