package chalkup

import chalkup.gym.FloorPlan
import chalkup.gym.Gym

class SampleData {

    public static Gym createBoulderwelt(def grailsApplication) {
        Gym gym = new Gym("Boulderwelt München")

        def linkGenerator = grailsApplication.mainContext.getBean("grailsLinkGenerator")
        def imageUrl = linkGenerator.resource(dir: 'images', file: 'floorPlans/boulderwelt-muenchen.jpg',
                absolute: true)

        FloorPlan fp = new FloorPlan(widthInPx: 2000, heightInPx: 1393, imageUrl: imageUrl)
        gym.addToFloorPlans(fp)
    }

    public static Gym createHeavensGate(def grailsApplication) {
        Gym gym = new Gym("Heavens Gate")

        def linkGenerator = grailsApplication.mainContext.getBean("grailsLinkGenerator")
        def imageUrl = linkGenerator.resource(dir: 'images', file: 'floorPlans/heavens-gate.png',
                absolute: true)

        FloorPlan fp = new FloorPlan(widthInPx: 1304, heightInPx: 1393, imageUrl: imageUrl)
        gym.addToFloorPlans(fp)
    }

}