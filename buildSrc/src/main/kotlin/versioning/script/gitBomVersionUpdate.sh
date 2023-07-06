#!/bin/bash

version=$1

git checkout -b bump-${version}
git add --all
git commit -m "Bump bom version to $version"
git push -u origin bump-${version}
