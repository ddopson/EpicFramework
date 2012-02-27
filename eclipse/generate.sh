#!/bin/bash

for plat in Desktop Android iOS NullPlat; do
  mkdir $plat -p
  underscore-template -t template/dot_classpath -d projects.json -s $plat -v -o $plat/.classpath
  underscore-template -t template/dot_project   -d projects.json -s $plat -v -o $plat/.project
  underscore-template -t template/dot_gitignore -d projects.json -s $plat -v -o $plat/.gitignore
done
