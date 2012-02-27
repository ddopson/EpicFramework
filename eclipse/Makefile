
PROJECTS  := Desktop Android iOS NullPlat HelloWorld EpicBuilder HelloUIv2

UTEMPLATE ?= $(shell PATH=$$PATH:../bin; which underscore-template)

.PHONY: build
build: $(PROJECTS)

.PHONY: $(PROJECTS)
$(PROJECTS):
	@mkdir -p $@
	@echo "Generating Eclipse project for '$@'"
	@$(UTEMPLATE) -t template/dot_classpath -d projects.json -s $@ -v -o $@/.classpath
	@$(UTEMPLATE) -t template/dot_project   -d projects.json -s $@ -v -o $@/.project
	@$(UTEMPLATE) -t template/dot_gitignore -d projects.json -s $@ -v -o $@/.gitignore

.PHONY: clean
clean:
	rm -rf $(PROJECTS) .metadata
#!/bin/bash

