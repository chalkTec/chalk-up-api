package chalkup.rest

import grails.converters.JSON
import net.hedtech.restfulapi.spock.RestSpecification

import java.text.SimpleDateFormat

class GymSpec extends RestSpecification {

    static final String localBase = "http://127.0.0.1:8080/rest"

    def "Test /gyms resource"() {
        setup:

        when: "GET to /gyms"
        get("$localBase/gyms") {
            headers['Content-Type'] = 'application/json'
            headers['Accept'] = 'application/json'
        }

        then:
        200 == response.status
        'application/json' == response.contentType
        def json = JSON.parse response.text
        json.size != 0
        def gym = json[0]

        gym.id != null
        gym.name != null
        gym.version != null
        gym.routes.current != null
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(gym.created).after(new Date().minus(10000))
        gym.floorPlans != null
        gym.floorPlans.id != null
        gym.floorPlans.img != null
    }

}
