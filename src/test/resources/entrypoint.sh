#!/bin/bash

env_line=$(mvn package -Ps3actions -nsu -q -Dexec.args="payload")
echo "PAYLOAD:"
echo $env_line
eval "mvn package -PlistTag -nsu $env_line"
eval "mvn install $env_line"
mvn package -Ps3actions -nsu -Dexec.args="report"