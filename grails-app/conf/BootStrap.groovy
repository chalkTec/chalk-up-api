import chalkup.SampleData

class BootStrap {

    def createGyms() {
        SampleData.createBoulderwelt().save(flush: true);
        SampleData.createHeavensGate().save(flush: true);
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
