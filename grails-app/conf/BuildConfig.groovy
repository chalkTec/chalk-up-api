grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
    // configure settings for the run-app JVM
    run: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        mavenRepo 'http://repo.spring.io/milestone' // required for Spring Security plugin's dependencies

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // see http://stackoverflow.com/questions/16019586/unsupported-major-minor-version-51-0-unable-to-load-class
        // -org-postgresql-driver for which version of postgresql dependency to take for which Java version
        runtime 'org.postgresql:postgresql:9.3-1100-jdbc4'

        // required by userapp
        compile 'org.json:json:20140107'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.52.1"

        compile ':spring-security-core:2.0-RC2'

        compile ":spring-security-rest:1.3.3"

        compile ":restful-api:0.9.0"

        compile ":inflector:0.2"    // required by restful-api, see https://github.com/restfulapi/restful-api
        compile ":cache-headers:1.1.6"  // required by restful-api, see https://github.com/restfulapi/restful-api

        test ":funky-spock:0.2.1"   // required by restful-api, see https://github.com/restfulapi/restful-api

        compile ':cache:1.1.1'
        runtime ":cors:1.1.4"

        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.5.1"
        runtime ":database-migration:1.4.0"
    }
}
