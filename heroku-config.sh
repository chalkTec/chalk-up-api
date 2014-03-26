#!/bin/bash

heroku config:add JAVA_OPTS='-Xmx256m -Xss512k -XX:+UseCompressedOops -javaagent:newrelic/newrelic.jar'
