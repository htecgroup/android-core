#!/bin/bash

module=$1
version=$2
git add --all
git commit -m "Bump $module module version to $version"
git push
