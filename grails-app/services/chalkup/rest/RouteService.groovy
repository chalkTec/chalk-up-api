package chalkup.rest

import chalkup.exceptions.InvalidModificationException
import chalkup.exceptions.NotFoundException
import chalkup.gym.*
import grails.transaction.Transactional
import grails.validation.ValidationException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize

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
        if (!type) {
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
        if (type != "sport-route" && type != "boulder") {
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


    private Route findRoute(id) {
        long lid = Long.valueOf(id)
        Route route = Route.findById(lid)
        if (route == null)
            throw new NotFoundException(objectName: 'route', objectId: lid)
        route
    }

    // bind attributes common to sport routes and boulders
    void bindRoute(Route route, def map) {
        if (map['color'])
            route.color = RouteColor.valueOf(map['color']['name'])
        map.remove('color')

        route.properties = map  // name, number, description, foreignId, 4x date
    }

    void bindSportRoute(SportRoute route, def map) {
        bindRoute(route, map)

        if (map['type'] && map['type'] != SportRoute.DISCRIMINATOR)
            throw new InvalidModificationException(reason: "cannot change the type of a route")

        if (map['initialGrade']) {
            String uiaa = map['initialGrade']['uiaa']

            // TODO: support ranges beyond slash-ranges (7+/8-)
            def slashGrade = uiaa =~ /(.*)\/(.*)/
            if (slashGrade) {
                SportGrade low = SportGrade.fromUiaaScale(slashGrade[0][1])
                SportGrade high = SportGrade.fromUiaaScale(slashGrade[0][2])
                route.gradeRange(low, high)
            } else if (SportGrade.isUiaaScaleGrade(uiaa)) {
                route.assignedGrade(SportGrade.fromUiaaScale(uiaa))
            } else {
                route.errors.reject('chalkup.invalid.grade.message',
                        [uiaa, 'UIAA'] as Object[], "$uiaa is not a valid UIAA grade");
            }
        } else {
            route.unknownGrade()
        }
    }

    void bindBoulder(Boulder route, def map) {
        bindRoute(route, map)

        if (map['type'] && map['type'] != Boulder.DISCRIMINATOR)
            throw new InvalidModificationException(reason: "cannot change the type of a route")

        if (map['initialGrade']) {
            String font = map['initialGrade']['font']

            // TODO: support ranges beyond slash-ranges (7a/7a+)
            def slashGrade = font =~ /(.*)\/(.*)/
            if (slashGrade) {
                BoulderGrade low = BoulderGrade.fromFontScale(slashGrade[0][1])
                BoulderGrade high = BoulderGrade.fromFontScale(slashGrade[0][2])
                route.gradeRange(low, high)
            } else if (BoulderGrade.isFontScaleGrade(font)) {
                route.assignedGrade(BoulderGrade.fromFontScale(font))
            } else {
                route.errors.reject('chalkup.invalid.grade.message',
                        [font, 'Fontainebleau'] as Object[], "$font is not a valid Fontainebleau grade");
            }
        } else {
            route.unknownGrade()
        }
    }


    @PreAuthorize("hasPermission(#map['gym']['id'], 'chalkup.gym.Gym', 'create')")
    Route create(def map, def params) {
        String type = getRouteType(map)
        Route route;
        switch (type) {
            case "sport-route":
                route = new SportRoute()
                bindSportRoute(route, map)
                break;
            case "boulder":
                route = new Boulder()
                bindBoulder(route, map)
                break;
        }

        route.save()

        log.info("$springSecurityService.currentUser.email created route $route.id for gym $route.gym.id")

        return route
    }


    @PreAuthorize("hasPermission(#map['gym']['id'], 'chalkup.gym.Gym', 'update')")
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
        else if (route instanceof Boulder)
            bindBoulder(route, map)
        else
            throw new UnsupportedOperationException('cannot bind boulders yet')

        if (route.hasErrors()) {
            throw new ValidationException("route not valid", route.errors)
        }

        route.save(flush: true)

        log.info("$springSecurityService.currentUser.email updated route $id for gym $route.gym.id")

        return route
    }


    @PreAuthorize("hasPermission(#map['gym']['id'], 'chalkup.gym.Gym', 'delete')")
    void delete(def id, def map, def params) {
        Route route = findRoute(id)

        if (map['version'] != null && map['version'] != route.version) {
            throw new OptimisticLockingFailureException("route has changed in the mean time")
        }

        route.delete()

        log.info("$springSecurityService.currentUser.email deleted route $id for gym $route.gym.id")
    }

}
