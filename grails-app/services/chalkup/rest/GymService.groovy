package chalkup.rest

import chalkup.gym.Gym
import grails.transaction.Transactional

@Transactional
class GymService {

    def list(def params) {
        return Gym.findAll()
    }

    def count(def params) {
        return Gym.count()
    }

}
