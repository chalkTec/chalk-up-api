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

grails.project.groupId = "chalkUpTec" // change this to alter the default package name and Maven publishing destination

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

grails.databinding.dateFormats = ['yyyy-MM-dd', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'hh:mm:ss'Z'"]

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




cache.headers.enabled = false

// ******************************************************************************
//                              CORS Configuration
// ******************************************************************************
// Note: If changing custom header names, remember to reflect them here.
//
cors.url.pattern = '/rest/*'
cors.allow.origin.regex = '.*'
cors.expose.headers = 'content-type,X-chalkup-totalCount,X-chalkup-pageOffset,X-chalkup-pageMaxSize,X-chalkup-message,X-chalkup-Media-Type'

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
        group 'defaultJson' marshallers {
            marshaller {
                instance = new ClosureObjectMarshaller<JSON>(Date, {
                    return it?.format("yyyy-MM-dd'T'HH:mm:ssZ")
                })
                priority = 100
            }
        }
    }

    // handle any pluralized resource name by mapping it to the singularized service name,
    // e.g. persons is handled by personService.
    // Dynamic marshallers/extractors are used.
    // If you want to whitelist only, remove the anyResource block and replace with
    // definitions for specific resources.
    anyResource {
        representation {
            mediaTypes = ["application/json"]
            marshallers {
                jsonDomainMarshaller {
                    priority = 101
                }
                jsonBeanMarshaller {
                    priority = 100
                }
                marshallerGroup 'defaultJson'
            }
            jsonExtractor {}
        }
    }
}
