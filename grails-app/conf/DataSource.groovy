dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory'
    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production {
        dataSource {
            dbCreate = 'validate' // use 'update', 'validate', 'create' or 'create-drop'

            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQL9Dialect

            uri = new URI(System.env.DATABASE_URL ?: "postgres://test:test@localhost/test")

            url = "jdbc:postgresql://" + uri.host + uri.path
            username = uri.userInfo.split(":")[0]
            password = uri.userInfo.split(":")[1]

            properties {
                initialSize = 8
                maxActive = 8
                maxIdle = 8
                maxWait = 10000 // no connection can be retrieved from the pool within this time => exception
                maxAge = 10 * 60000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                testWhileIdle = true
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
                testOnBorrow = true
                testOnReturn = false
            }
        }
    }
}
