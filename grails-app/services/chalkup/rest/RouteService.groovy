package chalkup.rest

import chalkup.exceptions.InvalidModificationException
import chalkup.exceptions.NotFoundException
import chalkup.gym.*
import grails.transaction.Transactional
import grails.validation.ValidationException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.http.HttpStatus

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
        if (parent == null) {
            // allow access without parent
        } else if (parent == 'gyms') {
            long gymId = Long.valueOf(params['parentId'])

            if (!Gym.exists(gymId))
                throw new NotFoundException(objectName: 'gym', objectId: gymId)

            criteria = criteria.where {
                gym.id == gymId
            }
        } else {
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

    String getRouteType(def map) {
        String type = map['type']
        if(!type) {
            throw new RuntimeException() {
                // required by restful-api
                int getHttpStatusCode() {
                    return HttpStatus.BAD_REQUEST.value();
                }

                def returnMap = {
                    return [message: "route type not set"]
                }
            }
        }
        if(type != "sport-route") {
            throw new RuntimeException() {
                // required by restful-api
                int getHttpStatusCode() {
                    return HttpStatus.BAD_REQUEST.value();
                }

                def returnMap = {
                    return [message: "route type $type not known"]
                }
            }
        }
        return type;
    }


    def create(def map, def params) {
        String type = getRouteType(map)
        Route route;
        switch(type) {
            case "sport-route":
                route = new SportRoute();
                break;
        }
        bindSportRoute(route, map)

        route.save()
        return route
    }


    private Route findRoute(id) {
        long lid = Long.valueOf(id)
        Route route = Route.findById(lid)
        if (route == null)
            throw new NotFoundException(objectName: 'route', objectId: lid)
        route
    }


    void bindSportRoute(SportRoute route, def map) {
        if (map['color'])
            route.color = RouteColor.valueOf(map['color']['name'])
        map.remove('color')

        route.properties = map  // name, number, description, foreignId, 4x date

        if (map['type'] && map['type'] != SportRoute.DISCRIMINATOR)
            throw new InvalidModificationException(reason: "cannot change the type of a route")

        if (map['initialGrade']) {
            String uiaa = map['initialGrade']['uiaa']
            if (SportGrade.isUiaaScaleGrade(uiaa)) {
                route.initialGrade = SportGrade.fromUiaaScale(uiaa)
            } else {
                route.errors.reject('chalkup.invalid.grade.message',
                        [uiaa, 'UIAA'] as Object[], "$uiaa is not a valid UIAA grade");
            }
        }
    }

    Route update(def id, def map, def params) {
        Route route = findRoute(id)

        // TODO: why does this not work automatically?
        if (map['version'] != null && map['version'] != route.version) {
            throw new OptimisticLockingFailureException("route has changed in the mean time")
        }

        // check for changing gym
        if (map['gym'] && map['gym'].id != route.gym.id)
            throw new InvalidModificationException(reason: "cannot move routes between gyms")

        // bind parameters
        if (route instanceof SportRoute)
            bindSportRoute(route, map)
        else
            throw new UnsupportedOperationException('cannot bind boulders yet')

        if(route.hasErrors()) {
            throw new ValidationException("route not valid", route.errors)
        }

        route.save(flush: true)

        log.info("$springSecurityService.currentUser.email updated route $id")

        return route
    }


    void delete(def id, def map, def params) {
        Route route = findRoute(id)

        if (map['version'] != null && map['version'] != route.version) {
            throw new OptimisticLockingFailureException("route has changed in the mean time")
        }

        route.delete()

        log.info("$springSecurityService.currentUser.email deleted route $id")
    }

}
