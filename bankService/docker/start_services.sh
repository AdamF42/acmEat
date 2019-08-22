#!/bin/bash
# Start the jolie soap service
setsid ./soap_service >/dev/null 2>&1 < /dev/null &

# Start the rest interface service
setsid ./rest_service >/dev/null 2>&1 < /dev/null &

# should check for services in background
while sleep 60; do
  ps aux |grep soap_service |grep -q -v grep
  SOAP_SERVICE_STATUS=$?
  ps aux |grep rest_service |grep -q -v grep
  REST_SERVICE_STATUS=$?
  # If the greps above find anything, they exit with 0 status
  # If they are not both 0, then something is wrong
  if [ $SOAP_SERVICE_STATUS -ne 0 ]; then
    echo "Soap service down."
    exit 1
  fi
  if [ $REST_SERVICE_STATUS -ne 0 ]; then
    echo "Rest service down."
    exit 1
  fi
done