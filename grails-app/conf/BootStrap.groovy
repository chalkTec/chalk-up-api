import chalkup.SampleData

class BootStrap {

    def grailsApplication

    def createGyms() {
        SampleData.createBoulderwelt(grailsApplication).save(flush: true);
        SampleData.createHeavensGate(grailsApplication).save(flush: true);
    }

    def init = { servletContext ->
        environments {
            production {
            }
            development {
                createGyms()
            }
            test {
            }
        }
    }
    def destroy = {
    }
}
