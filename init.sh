#!/bin/sh

# TODO: sh -> gradle task
if [ -b deploy ]; then
    cp ./deploy/miita/android/fabric.properties ./app
fi;
