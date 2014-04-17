import chalkup.gym.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.marshaller.ClosureObjectMarshaller

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.app.context = "/"

grails.project.groupId = "chalkTec" // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

grails.databinding.dateFormats = ['yyyy-MM-dd', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'HH:mm:ssZ"]

grails.gorm.failOnError = true

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = ['chalkup']
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.converters.default.pretty.print = true
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://api.chalkup.de"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    // Set level for all application artifacts
    info 'grails.app'

	// enable logging of restful-api plugin
    // info  'RestfulApiController_messageLog'
    // trace 'grails.app.controllers'

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    fatal 'RestfulApiController_messageLog'
}




// Added by the Spring Security Core plugin:

//grails.plugin.springsecurity.apf.allowSessionCreation = false
//grails.plugin.springsecurity.scr.allowSessionCreation = false

grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

grails.plugin.springsecurity.interceptUrlMap = [
        '/images/**':               ['permitAll'],
        '/rest/**':            ['isFullyAuthenticated() or request.getMethod().equals("GET")']
]

grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false


grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'


cache.headers.enabled = false

// ******************************************************************************
//                              CORS Configuration
// ******************************************************************************
// Note: If changing custom header names, remember to reflect them here.
//
cors.url.pattern = '/rest/*'
cors.allow.origin.regex = '.*'
cors.expose.headers = 'content-type,X-chalkup-totalCount,X-chalkup-pageOffset,X-chalkup-pageMaxSize,' +
        'X-chalkup-message,X-chalkup-Media-Type,X-Auth-Token'

// we need to overwrote this in order to add X-Auth-Token
cors.headers = ['Access-Control-Allow-Headers': 'origin, authorization, accept, content-type, x-requested-with, X-Auth-Token']

// ******************************************************************************
//             RESTful API Custom Response Header Name Configuration
// ******************************************************************************
//
restfulApi.header.totalCount = 'X-chalkup-totalCount'
restfulApi.header.pageOffset = 'X-chalkup-pageOffset'
restfulApi.header.pageMaxSize = 'X-chalkup-pageMaxSize'
restfulApi.header.message = 'X-chalkup-message'
restfulApi.header.mediaType = 'X-chalkup-Media-Type'

// ******************************************************************************
//             RESTful API 'Paging' Query Parameter Name Configuration
// ******************************************************************************
//
restfulApi.page.max = 'max'
restfulApi.page.offset = 'offset'

// ******************************************************************************
//                       RESTful API Endpoint Configuration
// ******************************************************************************
//
restfulApiConfig = {
    marshallerGroups {
        // this group will be used for all JSON representations
        group 'date' marshallers {
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(Date, {
                    return it?.format("yyyy-MM-dd'T'HH:mm:ssZ")
                })
                priority = 100
            }
        }
        group 'floorPlan' marshallers {
            jsonDomainMarshaller {
                supports chalkup.gym.FloorPlan
                includesFields {}
                additionalFields { Map map ->
                    // use additional field mapping to wrap floor plan image properties in img JSON object
                    def floorPlan = map['beanWrapper'].getWrappedInstance()
                    def img = [:]
                    img['widthInPx'] = floorPlan.widthInPx
                    img['heightInPx'] = floorPlan.heightInPx
                    img['url'] = floorPlan.imageUrl
                    map['json'].property("img", img)
                }
            }
        }
        group 'routeColor' marshallers {
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(RouteColor, { RouteColor color ->
                    def map = [:]
                    map['name'] = color.toString();
                    def messageSource = grailsApplication.mainContext.getBean('messageSource')
                    map['germanName'] = messageSource.getMessage("chalkup.color.$color", null, Locale.GERMAN);
                    map['englishName'] = messageSource.getMessage("chalkup.color.$color", null, Locale.ENGLISH);

                    map['primary'] = RouteColor.asRgb(color.primaryColor)
                    if (color.secondaryColor)
                        map['secondary'] = RouteColor.asRgb(color.secondaryColor)
                    if (color.ternaryColor)
                        map['ternary'] = RouteColor.asRgb(color.ternaryColor)
                    return map
                })
            }
        }
        group 'grades' marshallers {
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(GradeCertainty, {
                    it.toString()
                })
            }
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(BoulderGrade, { BoulderGrade grade ->
                    def map = [:]
                    map['value'] = grade.value
                    map['font'] = grade.toFontScale()
                    return map
                })
            }
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(SportGrade, { SportGrade grade ->
                    def map = [:]
                    map['value'] = grade.value
                    map['uiaa'] = grade.toUiaaScale()
                    return map
                })
            }
        }
    }

    jsonDomainMarshallerTemplates {
        template 'json-shortObject' config {
            shortObject { Map map ->
                def json = map['json']
                def writer = json.getWriter()
                def resource = map['resourceName']
                def id = map['resourceId']
                writer.object()
                writer.key("link").value("/$resource/$id")
                writer.key("id").value(id)
                writer.endObject()
            }
        }
    }

    resource 'routes' config {
        representation {
            mediaTypes = ["application/json"]
            marshallers {
                jsonDomainMarshaller {
                    supports chalkup.gym.Route
                    inherits = ['json-shortObject']
                    includesFields {
                        field 'color'
                        field 'dateCreated' name 'created'
                        field 'lastUpdated' name 'updated'
                        field 'location' deep true
                        field 'gym'
                    }
                    additionalFields { Map map ->
                        def json = map['json']
                        def route = map['beanWrapper'].getWrappedInstance()

                        if (route.name)
                            json.property('name', route.name)
                        if (route.number)
                            json.property('number', route.number)
                        if (route.description)
                            json.property('description', route.description)
                        if (route.dateSet)
                            json.property('dateSet', route.dateSet)
                        if (route.end)
                            json.property('end', route.end)

                        // TODO: add route setter as soon as we have users
                        // if(route.setter) {
                        //  def setter = [:]
                        //  setter['id'] = route.setter.id
                        //  setter['nickname'] = route.setter.nickname
                        //  json.property('setter', setter)
                        // }

                        if (route instanceof Boulder) {
                            json.property('type', 'boulder')

                            // TODO: create photo as resource
                            //if (route.hasPhoto()) {
                            //    def photo = [:]
                            //    // creation of link using controller, action, and ID does not work since the mapping involves HTTP
                            //    // methods to actions
                            //    photo['url'] = grailsLinkGenerator.link(['uri'     : "/rest/v1/boulders/$route.id/photo",
                            //                                             'absolute': true])
                            //    map['photo'] = photo
                            //}

                            if (route.getInitialGradeCertainty() == GradeCertainty.ASSIGNED ||
                                    route.getInitialGradeCertainty() == GradeCertainty.RANGE) {
                                def initialGrade = [:]
                                initialGrade['value'] = route.getGradeValue()
                                initialGrade['font'] = route.getReadableInitialGrade()
                                json.property('initialGrade', initialGrade)
                            }
                        } else if (route instanceof SportRoute) {
                            json.property('type', 'sport-route')

                            if (route.getInitialGradeCertainty() == GradeCertainty.ASSIGNED ||
                                    route.getInitialGradeCertainty() == GradeCertainty.RANGE) {
                                def initialGrade = [:]
                                initialGrade['value'] = route.getGradeValue()
                                initialGrade['uiaa'] = route.getReadableInitialGrade()
                                json.property('initialGrade', initialGrade)
                            }
                        }

                    }
                }
                jsonDomainMarshaller {
                    supports chalkup.gym.Location
                    includesFields {
                        includesId false
                        includesVersion false
                        field 'floorPlan' deep true
                        field 'x'
                        field 'y'
                    }
                }
                marshallerGroup 'grades'
                marshallerGroup 'routeColor'
                marshallerGroup 'floorPlan'
                marshallerGroup 'date'
            }
            jsonExtractor {
                property 'dateSet' date true
                property 'end' date true
                dateFormats = ["yyyy-MM-dd'T'HH:mm:ssZ"]
            }
        }
    }

    resource 'gyms' config {
        representation {
            mediaTypes = ["application/json"]
            marshallers {
                jsonDomainMarshaller {
                    supports chalkup.gym.Gym
                    field 'floorPlans' deep true
                    field 'dateCreated' name 'created'
                    excludesFields {
                        field 'lastUpdated'
                        field 'routes'
                    }
                    additionalFields { Map map ->
                        def routes = [:]
                        routes["current"] = "/rest/${map['resourceName']}/${map['resourceId']}/routes"
                        map['json'].property('routes', routes)
                        map['json'].property('colors', RouteColor.values())
                    }
                }
                marshallerGroup 'date'
                marshallerGroup 'routeColor'
                marshallerGroup 'floorPlan'
            }
        }
    }

}
