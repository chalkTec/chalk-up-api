package chalkup.rest

import chalkup.exceptions.NotFoundException
import chalkup.gym.Gym
import chalkup.gym.Route
import grails.transaction.Transactional

@Transactional
class RouteService {

    def criteria(params) {
        def dateParam = params['date']
        // TODO: properly parse data parameter in case it is given
        Date date = dateParam ? new Date() : new Date()

        def criteria = Route.where {
            end == null || end >= date
        }

        def parent = params['parentPluralizedResourceName']
        if(parent) {
            if(parent != 'gyms')
                throw new NotFoundException(objectName: parent, objectId: params['parentId'])

            def gymIdParam = params['parentId']

            if(!Gym.exists(gymIdParam))
                throw new NotFoundException(objectName: 'gym', objectId: gymIdParam)

            criteria = criteria.where {
                gym.id == gymIdParam
            }
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

}
