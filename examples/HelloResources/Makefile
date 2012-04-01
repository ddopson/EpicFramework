
FRAMEWORK_DIR = /workspace/EpicFramework

EPIC_RESOURCES = $(FRAMEWORK_DIR)/bin/epic-resources
UTEMPLATE = $(FRAMEWORK_DIR)/bin/underscore-template

java_src_files = $(shell find src gen -name '*.java')

objc_resources = $(shell find $(FRAMEWORK_DIR)/EpicIos/objc-plugins -type d | perl -ne 'chomp; print "$$_/:"')

.PHONY: default
default: prebuild javac xmlvm trim xcodebuild

.PHONY: build
build: xcodebuild

.PHONY: prebuild
prebuild: build/.make.prebuild
build/.make.prebuild: $(shell find resources)
	@echo "Preprocessing Resources"
	@mkdir -p build gen/com/epic/resources/ resources/files
	@$(UTEMPLATE) -d resources/json -m json > resources/files/config.json
	@$(EPIC_RESOURCES) -s resources | $(UTEMPLATE)  -t $(FRAMEWORK_DIR)/EpicBuilder/resources/EpicImages.template -d _ > gen/com/epic/resources/EpicImages.java
	@$(EPIC_RESOURCES) -s resources | $(UTEMPLATE)  -t $(FRAMEWORK_DIR)/EpicBuilder/resources/EpicFiles.template -d _ > gen/com/epic/resources/EpicFiles.java
	@touch build/.make.prebuild

.PHONY: javac
javac: build/.make.javac
build/.make.javac: build/.make.prebuild $(java_src_files)
	@mkdir -p build/classes
	javac -d build/classes -cp $(FRAMEWORK_DIR)/build/EpicIos.jar $(java_src_files)
	@touch build/.make.javac

.PHONY: xmlvm
xmlvm: javac build/.make.xmlvm
build/.make.xmlvm: build/.make.javac
	@#xmlvm --in=build/classes/ --out=build/xmlvm --target=xmlvm --enable-ref-counting --app-name=HelloResources --in=$(FRAMEWORK_DIR)/build/classes_ios/  --in=$(FRAMEWORK_DIR)/build/classes_common --in=$(FRAMEWORK_DIR)/build/classes_json --resource=resources/images/:resources/files/
	xmlvm --in=build/classes/ --out=build/xcode --target=iphone --enable-ref-counting --app-name=HelloResources --in=$(FRAMEWORK_DIR)/build/classes_ios/  --in=$(FRAMEWORK_DIR)/build/classes_common --in=$(FRAMEWORK_DIR)/build/classes_json --resource=resources/images/:resources/files/:$(objc_resources) --lib=SystemConfiguration.framework --lib=StoreKit.framework
	cp $(FRAMEWORK_DIR)/EpicIos/objc-overrides/* build/xcode/build/xcode/src/app
	@touch build/.make.xmlvm

.PHONY: trim
trim: build/.make.trim
build/.make.trim: build/.make.xmlvm
	ant go
	@touch build/.make.trim

.PHONY: xcodebuild
xcodebuild: build/.make.xcodebuild
build/.make.xcodebuild:
	cd build/xcode/dist; xcodebuild -parallelizeTargets -sdk iphonesimulator -configuration Debug | tee $(CURDIR)/xcodebuild.log
	@cat $(CURDIR)/xcodebuild.log | ack '(?<= )(error)(?=: )' || true


.PHONY: clean
clean:
	rm -rf build


.PHONY: run
run:
	@echo > stderr.log
	@ios-sim launch build/xcode/dist/build/Debug-iphonesimulator/HelloResources.app --stderr stderr.log --family iphone --setenv NSZombieEnabled=YES --setenv NSDebugEnabled=YES &
	@osascript -e "tell application \"iPhone Simulator\" to activate"
	@tail -n10000 -f stderr.log
