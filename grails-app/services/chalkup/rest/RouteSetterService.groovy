package chalkup.rest

import chalkup.gym.Gym
import chalkup.gym.Route
import chalkup.user.RouteSetter
import chalkup.user.User
import grails.transaction.Transactional
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException

@Transactional
class RouteSetterService {

    def springSecurityService

    RouteSetter create(def map, def params) {
        if(params['parentPluralizedResourceName'] != 'gyms')
            throw new RuntimeException()

        Gym gym = Gym.findById(Long.valueOf(params['parentId']))
        User user = springSecurityService.principal
        if(!user.canEdit(gym))
            throw new AccessDeniedException("$user is not allowed to create a route setter for gym $gym")

        println(params)
        RouteSetter routeSetter = new RouteSetter(map['nickname'])

        gym.addToRouteSetters(routeSetter)

        try {
            gym.save(flush: true)
        }
        catch(DuplicateKeyException e) {
            throw new RuntimeException() {
                // required by restful-api
                int getHttpStatusCode() {
                    return HttpStatus.CONFLICT.value();
                }

                def returnMap = {
                    return [message: "route setter ${map['nickname']} already exists for gym $gym"]
                }
            }
        }

        log.info("$user created route setter $routeSetter for gym $gym")

        return routeSetter
    }

}
