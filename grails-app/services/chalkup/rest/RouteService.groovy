package chalkup.rest

import chalkup.exceptions.NotFoundException
import chalkup.gym.Gym
import chalkup.gym.Route
import grails.transaction.Transactional

@Transactional
class RouteService {

    def criteria(gymId, dateParam) {
        // TODO: properly parse data parameter in case it is given
        Date date = dateParam ? new Date() : new Date()

        def criteria = Route.where {
            end == null || end >= date
        }

        if(gymId) {
            if(!Gym.exists(gymId))
                throw new NotFoundException(objectName: 'gym', objectId: gymId)

            criteria = criteria.where {
                gym.id == gymId
            }
        }
        return criteria
    }

    def list(def params) {
        def criteria = criteria(params['parentId'], params['date'])
        return criteria.list()
    }

    def count(def params) {
        def criteria = criteria(params['parentId'], params['date'])
        return criteria.count()
    }

}
