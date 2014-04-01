#!/bin/bash

#heroku config:add JAVA_OPTS='-Xmx256m -Xss512k -XX:+UseCompressedOops -javaagent:newrelic/newrelic.jar'

heroku pgbackups:transfer --app chalk-up-api HEROKU_POSTGRESQL_JADE_URL chalk-up-api-staging::HEROKU_POSTGRESQL_CRIMSON_URL
