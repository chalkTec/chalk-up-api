package chalkup.rest

import chalkup.exceptions.NotFoundException
import chalkup.gym.Gym
import chalkup.gym.Route
import grails.transaction.Transactional
import org.springframework.dao.OptimisticLockingFailureException

@Transactional
class RouteService {

    def springSecurityService

    def criteria(def params) {
        def dateParam = params['date']
        // TODO: properly parse data parameter in case it is given
        Date date = dateParam ? new Date() : new Date()

        def criteria = Route.where {
            end == null || end >= date
        }

        def parent = params['parentPluralizedResourceName']
        if(parent == null) {
            // allow access without parent
        }
        else if(parent == 'gyms') {
            long gymId = Long.valueOf(params['parentId'])

            if(!Gym.exists(gymId))
                throw new NotFoundException(objectName: 'gym', objectId: gymId)

            criteria = criteria.where {
                gym.id == gymId
            }
        }
        else {
            throw new NotFoundException(objectName: parent, objectId: params['parentId'])
        }
        return criteria
    }

    def list(def params) {
        def criteria = criteria(params)
        return criteria.list()
    }

    def count(def params) {
        def criteria = criteria(params)
        return criteria.count()
    }

    def create(def map, def params) {
        return null;
    }

    def delete(def id, def map, def params) {
        long lid = Long.valueOf(id)
        Route route = Route.findById(lid)
        if(route == null)
            throw new NotFoundException(objectName: 'route', objectId: lid)

        if(map.version && map.version != route.version) {
            throw new OptimisticLockingFailureException("route has changed in the mean time")
        }

        log.info("$springSecurityService.currentUser.email deleted route $id")

        route.delete()
    }

}
