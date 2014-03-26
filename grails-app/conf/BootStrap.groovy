import chalkup.SampleData
import chalkup.gym.FloorPlan
import chalkup.gym.Gym

class BootStrap {

    def grailsApplication

    def createGyms() {
        SampleData.createBoulderwelt(grailsApplication).save(flush: true);
        SampleData.createHeavensGate(grailsApplication).save(flush: true);
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
            }
        }
    }
    def destroy = {
    }
}
