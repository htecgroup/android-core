#!/bin/bash

module=$1
version=$2
git add *version.properties
git commit -m "Bump $module module version to $version"
git push
