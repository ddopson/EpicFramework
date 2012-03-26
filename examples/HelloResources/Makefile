
FRAMEWORK_DIR = /workspace/EpicFramework

EPIC_RESOURCES = $(FRAMEWORK_DIR)/bin/epic-resources
UTEMPLATE = $(FRAMEWORK_DIR)/bin/underscore-template

.PHONY: default
default: prebuild

.PHONY: prebuild
prebuild:
	@echo "Preprocessing Resources"
	@mkdir -p gen/com/epic/resources/ resources/files
	@$(UTEMPLATE) -d resources/json -m json > resources/files/config.json
	@$(EPIC_RESOURCES) -s resources | $(UTEMPLATE)  -t $(FRAMEWORK_DIR)/EpicBuilder/resources/EpicImages.template -d _ > gen/com/epic/resources/EpicImages.java
	@$(EPIC_RESOURCES) -s resources | $(UTEMPLATE)  -t $(FRAMEWORK_DIR)/EpicBuilder/resources/EpicFiles.template -d _ > gen/com/epic/resources/EpicFiles.java

.PHONY: xmlvm
xmlvm:
	xmlvm --in=build/classes/ --out=build/xcode --target=iphone --app-name=HelloResources --in=/workspace/EpicFramework/build/classes_ios/	


.PHONY: xcodebuild
xcodebuild:
	cd build/xcode/dist; xcodebuild -parallelizeTargets -sdk iphonesimulator -configuration Debug | tee $(CURDIR)/output.log

.PHONY: clean
clean:
	rm -rf build
