
FRAMEWORK_DIR = /workspace/EpicFramework

EPIC_RESOURCES = $(FRAMEWORK_DIR)/bin/epic-resources

.PHONY: default
default: prebuild

.PHONY: prebuild
prebuild:
	@echo "Preprocessing Resources"
	@mkdir -p gen/com/epic/resources/
	@epic-resources -s resources/images | underscore-template  -t /workspace/EpicFramework/EpicBuilder/resources/EpicImages.template -d _ > gen/com/epic/resources/EpicImages.java	
