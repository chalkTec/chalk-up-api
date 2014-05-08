package chalkup.rest

import chalkup.exceptions.NotFoundException
import chalkup.gym.Rating
import chalkup.gym.Route
import grails.transaction.Transactional

@Transactional
class RatingService {

    Rating create(def map, def params) {
        def parent = params['parentPluralizedResourceName']
        if (parent == null) {
            throw new NotFoundException(objectName: parent, objectId: params['parentId'])
        }

        long routeId = Long.valueOf(params['parentId'])
        Route route = Route.findById(routeId)

        Rating rating = new Rating(value: map['value'])
        route.addToRatings(rating)

        log.info("route $route of gym $route.gym was rated ${map['value']}")

        return rating
    }

}
