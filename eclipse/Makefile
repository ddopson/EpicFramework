
PROJECTS  := Desktop Android iOS NullPlat HelloWorld EpicBuilder HelloUIv2
UTEMPLATE ?= $(shell PATH=$$PATH:../bin; which underscore-template)

.PHONY: build
build: $(PROJECTS)

.PHONY: $(PROJECTS)
$(PROJECTS):
	@mkdir -p $@/.settings
	@echo "Generating Eclipse project for '$@'"
	@$(UTEMPLATE) -t template/dot_classpath   -d projects.js -s $@ -v -o $@/.classpath
	@$(UTEMPLATE) -t template/dot_project     -d projects.js -s $@ -v -o $@/.project
	@$(UTEMPLATE) -t template/dot_gitignore   -d projects.js -s $@ -v -o $@/.gitignore
	@$(UTEMPLATE) -t template/dot_factorypath -d projects.js -s $@ -v -o $@/.factorypath
	@$(UTEMPLATE) -t template/dot_settings/org.eclipse.jdt.apt.core.prefs -d projects.js -s $@ -v -o $@/.settings/org.eclipse.jdt.apt.core.prefs
	@$(UTEMPLATE) -t template/dot_settings/org.eclipse.jdt.core.prefs -d projects.js -s $@ -v -o $@/.settings/org.eclipse.jdt.core.prefs


.PHONY: clean
clean:
	rm -rf $(PROJECTS) .metadata
#!/bin/bash

