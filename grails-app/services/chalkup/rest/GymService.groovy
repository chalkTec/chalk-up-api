package chalkup.rest

import chalkup.exceptions.NotFoundException
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

    def show(def params) {
        long id = Long.valueOf(params['id'])
        def gym = Gym.findById(id)
        if (!gym)
            throw new NotFoundException(objectName: 'gym', objectId: id)
        return gym;
    }

}
