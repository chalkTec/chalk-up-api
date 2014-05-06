import chalkup.SampleData
import chalkup.gym.FloorPlan
import chalkup.gym.Gym
import chalkup.user.RouteSetter

class BootStrap {

    def grailsApplication

    def createGyms() {
        def boulderwelt = SampleData.createBoulderwelt(grailsApplication)
        boulderwelt.addToRouteSetters(new RouteSetter('Markus'))
        boulderwelt.addToRouteSetters(new RouteSetter('Dave'))
        boulderwelt.save(flush: true);

        def heavensGate = SampleData.createHeavensGate(grailsApplication)
        heavensGate.addToRouteSetters(new RouteSetter('Timo'))
        heavensGate.addToRouteSetters(new RouteSetter('Tom'))
        heavensGate.addToRouteSetters(new RouteSetter('Marcel'))
        heavensGate.save(flush: true);
    }

    private void createRoutes() {
        Gym gym1 = Gym.findById(1)
        FloorPlan fp1 = gym1.floorPlans.first()

        gym1.addToRoutes(SampleData.createSportRoute1(fp1))
        gym1.addToRoutes(SampleData.createBoulder1(fp1))
        gym1.addToRoutes(SampleData.createBoulder2(fp1))
        gym1.addToRoutes(SampleData.createBoulder3(fp1))
        gym1.addToRoutes(SampleData.createBoulder4(fp1))
        gym1.addToRoutes(SampleData.createBoulder5(fp1))
        gym1.addToRoutes(SampleData.createBoulder6(fp1))
        gym1.addToRoutes(SampleData.createBoulder7(fp1))
        gym1.addToRoutes(SampleData.createBoulder8(fp1))
        gym1.save(flush: true)


        Gym gym2 = Gym.findById(2)
        FloorPlan fp2 = gym2.floorPlans.first()

        gym2.addToRoutes(SampleData.createSportRoute2(fp2))
        gym2.addToRoutes(SampleData.createBoulder9(fp2))
    }

    def init = { servletContext ->

        environments {
            production {
            }
            development {
                createGyms()
                createRoutes()
            }
            test {
                createGyms()
                createRoutes()
            }
        }
    }
    def destroy = {
    }
}
