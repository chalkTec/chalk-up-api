package chalkup

import chalkup.gym.Gym

class GymController {

    static responseFormats = ['json']

    def index() {
        respond Gym.findAll()
    }

}
