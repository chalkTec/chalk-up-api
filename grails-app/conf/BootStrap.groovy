import chalkup.SampleData
import chalkup.gym.FloorPlan
import chalkup.gym.Gym
import chalkup.user.Role
import chalkup.user.User
import chalkup.user.UserRole

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

    private void createUser(String username, List<Role> roles) {
        def user = new User(email: username, password: 'p')
        user.save()

        roles.each { role ->
            UserRole.create(user, role)
        }
    }

    private void createUsers(Role routeSetterRole) {
        createUser 'test@abc.de', [routeSetterRole]
    }

    def init = { servletContext ->

        Role routeSetterRole = Role.findByAuthority('ROLE_ROUTE_SETTER') ?: new Role(authority: 'ROLE_ROUTE_SETTER').save(failOnError: true)

        environments {
            production {
            }
            development {
                createGyms()
                createRoutes()

                createUsers(routeSetterRole)
            }
            test {
                createGyms()
                createRoutes()

                createUsers(routeSetterRole)
            }
        }
    }
    def destroy = {
    }
}
