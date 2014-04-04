package chalkup.gym

class SportRoute extends Route {

    public final static String DISCRIMINATOR = "sport-route"

    static mapping = {
        discriminator DISCRIMINATOR
    }

    static embedded = ['initialGrade']


    SportGrade initialGrade

    public SportRoute() {
        this.initialGrade = SportGrade.zero()
    }

}
