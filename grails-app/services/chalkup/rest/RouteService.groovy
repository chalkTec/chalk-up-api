package chalkup.rest

import chalkup.gym.Gym
import chalkup.gym.Route
import grails.transaction.Transactional

@Transactional
class RouteService {

    def list(def params) {
        if(params['parentId']) {
            Gym gym = Gym.findById(params['parentId'])
            return Route.findAllByGym(gym)
        }
        else {
            return Route.findAll()
        }
    }

    def count(def params) {
        if(params['parentId']) {
            Gym gym = Gym.findById(params['parentId'])
            return Route.countByGym(gym)
        }
        else {
            return Route.count()
        }
    }

}
