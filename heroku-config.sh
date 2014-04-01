#!/bin/bash

heroku config:add -a chalk-up-api JAVA_OPTS='-Xmx256m -Xss512k -XX:+UseCompressedOops -javaagent:newrelic/newrelic.jar'
heroku config:add -a chalk-up-api-staging JAVA_OPTS='-Xmx256m -Xss512k -XX:+UseCompressedOops -javaagent:newrelic/newrelic.jar -Dnewrelic.environment=staging'

# copy content of production DB to staging DB
#heroku pgbackups:transfer --app chalk-up-api HEROKU_POSTGRESQL_JADE_URL chalk-up-api-staging::HEROKU_POSTGRESQL_CRIMSON_URL
