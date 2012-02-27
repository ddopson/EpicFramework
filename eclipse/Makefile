

.PHONY: build
build: Desktop Android iOS NullPlat

.PHONY: Desktop Android iOS NullPlat
Desktop Android iOS NullPlat:
	@mkdir $@ -p
	@echo "Generating Eclipse project for '$@'"
	@underscore-template -t template/dot_classpath -d projects.json -s $@ -v -o $@/.classpath
	@underscore-template -t template/dot_project   -d projects.json -s $@ -v -o $@/.project
	@underscore-template -t template/dot_gitignore -d projects.json -s $@ -v -o $@/.gitignore

.PHONY: clean
clean:
	rm -rf Android iOS Desktop NullPlat .metadata
#!/bin/bash

