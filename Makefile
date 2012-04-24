####################################################################################################
##  Phony Targets
####################################################################################################

.PHONY: all
all: lint xmlvm EpicBuilder.jar EpicDesktop.jar EpicIos.jar

dist: all
	@echo "$(GREEN)General$(NOCOLOR) - Copying build artifacts to dist/"
	@mkdir -p dist dist/resources
	@cp build/EpicDesktop.jar dist/
	@cp EpicBuilder/resources/* dist/resources	

.PHONY: clean
clean:
	rm -rf build


####################################################################################################
##  Functions
####################################################################################################

classpathify = $(subst $(eval) ,:,$(wildcard $1))
logger       = echo "$(GREEN)$1$(NOCOLOR) -$2"


####################################################################################################
##  Variables
####################################################################################################


java_src_files_json    := $(shell find EpicJson/src -name '*.java' -type file -follow)
java_src_files_builder := $(shell find EpicBuilder/src -name '*.java' -type file -follow)
java_src_files_null    := $(shell find EpicNullPlat/src -name '*.java' -type file -follow)
java_src_files_common  := $(shell find EpicCommon/src -name '*.java' -type file -follow)
java_src_files_desktop := $(shell find EpicDesktop/src -name '*.java' -type file -follow)
java_src_files_ios     := $(shell find EpicIos/src -name '*.java' -type file -follow)
java_src_files_applet  := $(shell find EpicDesktop/src -name '*.java' -type file -follow)

JAVAC_PROCESSOR        := -processor com.epic.framework.build.EpicAnnotationProcessor 

CP_JSON  := build/classes_json
CP_BLD   := build/classes_builder
CP_COMM  := build/classes_common
CP_DESK  := build/classes_desktop
CP_IOS   := build/classes_ios
CP_AND   := build/classes_android

GREEN    := \033[32m
YELLOW   := \033[38;5;226m
NOCOLOR  := \033[39;0m

####################################################################################################
##  Targets
####################################################################################################

dbg:
	echo $(call classpathify,build/EpicCommon.jar EpicDesktop/lib/*.jar)	

node_modules: package.json
	npm install
	@touch node_modules

.PHONY: lint
lint:
	@echo "$(GREEN)General$(NOCOLOR) - Validating Javascript"
	@jshint  bin/underscore-template  bin/epic-resources jslib/*

.PHONY: xmlvm
xmlvm:
	@echo "$(GREEN)General$(NOCOLOR) - Make xmlvm"
	@make -C xmlvm

####################################################################################################
##  Epic{Json,Builder,Common}
####################################################################################################

.PHONY: EpicJson
EpicJson: build/.make.EpicJson
build/.make.EpicJson: $(java_src_files_json)
	@mkdir -p $(CP_JSON)
	@echo "$(GREEN)EpicJson$(NOCOLOR) - Compiling $(words $(java_src_files_json)) source files ..."
	@javac -d $(CP_JSON) $(java_src_files_json)
	@touch build/.make.EpicJson


.PHONY: EpicBuilder
EpicBuilder: EpicJson build/.make.EpicBuilder
build/.make.EpicBuilder: $(java_src_files_builder)
	@mkdir -p $(CP_BLD)
	@echo "$(GREEN)EpicBuilder$(NOCOLOR) - Compiling $(words $(java_src_files_builder)) source files ..."
	@javac -d $(CP_BLD) -cp $(CP_JSON) $(java_src_files_builder)
	@touch build/.make.EpicBuilder


.PHONY: EpicCommon
EpicCommon: EpicJson EpicBuilder build/.make.EpicCommon
build/.make.EpicCommon: $(java_src_files_common) $(java_src_files_null)
	@mkdir -p $(CP_COMM)
	@echo "$(GREEN)EpicCommon$(NOCOLOR) - Compiling $(words $(java_src_files_common)) + $(words $(java_src_files_null)) source files ..."
	@rsync -a $(CP_JSON)/ $(CP_COMM)/
	@javac -sourcepath EpicNullPlat/src -implicit:class -d $(CP_COMM) -cp $(CP_JSON):$(CP_BLD) $(JAVAC_PROCESSOR) $(java_src_files_common)
	@rm -rf $(CP_COMM)/com/epic/framework/implementation
	@touch build/.make.EpicCommon

.PHONY: EpicBuilder.jar
EpicBuilder.jar: EpicBuilder build/EpicBuilder.jar
build/EpicBuilder.jar: build/.make.EpicBuilder $(shell find EpicBuilder/src)
	@echo "$(GREEN)EpicBuilder.jar$(NOCOLOR) - Merging dependencies into build/jar_builder"
	@mkdir -p build/jar_builder
	rsync -a {EpicJson,EpicBuilder}/src/ build/jar_builder/
	rsync -a $(CP_JSON)/ $(CP_BLD)/ build/jar_builder/
	@echo "$(GREEN)EpicBuilder.jar$(NOCOLOR) - Combining $$(find build/jar_builder -type f | wc -l) files into build/EpicBuilder.jar"
	jar cf build/EpicBuilder.jar -C build/jar_builder .

####################################################################################################
##  EpicDesktop
####################################################################################################

.PHONY: EpicDesktop
EpicDesktop: EpicCommon build/.make.EpicDesktop build/EpicBuilder.jar
build/.make.EpicDesktop: $(java_src_files_desktop)
	@mkdir -p $(CP_DESK)
	@echo "$(GREEN)EpicDesktop$(NOCOLOR) - Compiling $(words $(java_src_files_desktop)) source files ..."
	javac -d $(CP_DESK) -cp $(CP_JSON):$(CP_BLD):$(CP_COMM):$(call classpathify,EpicDesktop/lib/*.jar) $(JAVAC_PROCESSOR) $(java_src_files_desktop)
	@touch build/.make.EpicDesktop

.PHONY: EpicDesktop.jar
EpicDesktop.jar: EpicDesktop build/EpicDesktop.jar
build/EpicDesktop.jar: build/.make.EpicDesktop build/.make.EpicCommon build/.make.EpicBuilder $(shell find EpicBuilder/src EpicCommon/src/ EpicDesktop/src/)
	@echo "$(GREEN)EpicDesktop.jar$(NOCOLOR) - Merging dependencies into build/jar_desktop"
	@mkdir -p build/jar_desktop
	cd build/jar_desktop && for j in $(CURDIR)/EpicDesktop/lib/*.jar; do jar xf $$j; done
	rsync -a {EpicJson,EpicBuilder,EpicCommon,EpicDesktop}/src/  build/jar_desktop/
	rsync -a $(CP_JSON)/ $(CP_BLD)/ $(CP_COMM)/ $(CP_DESK)/     build/jar_desktop/
	@echo "$(GREEN)EpicDesktop.jar$(NOCOLOR) - Combining $$(find build/jar_desktop -type f | wc -l) files into build/EpicDesktop.jar"
	jar cf build/EpicDesktop.jar -C build/jar_desktop .

####################################################################################################
##  EpicIos
####################################################################################################

.PHONY: EpicIos
EpicIos: EpicCommon build/.make.EpicIos build/EpicBuilder.jar
build/.make.EpicIos: $(java_src_files_ios)
	@mkdir -p $(CP_IOS)
	@echo "$(GREEN)EpicIos$(NOCOLOR) - Compiling $(words $(java_src_files_ios)) source files ..."
	javac -d $(CP_IOS) -cp xmlvm/xmlvm.jar:$(CP_JSON):$(CP_BLD):$(CP_COMM):$(call classpathify,EpicIos/lib/*.jar) $(JAVAC_PROCESSOR) $(java_src_files_ios)
	xmlvm --in=$(CP_JSON) --in=$(CP_BLD) --in=$(CP_COMM) --out=build/xmlvm --target=iphone --enable-ref-counting --app-name=EpicFramework
	@touch build/.make.EpicIos

.PHONY: EpicIos.jar
EpicIos.jar: EpicIos build/EpicIos.jar
build/EpicIos.jar: build/.make.EpicIos build/.make.EpicCommon build/.make.EpicBuilder $(shell find EpicBuilder/src EpicCommon/src/ EpicIos/src/)
	@echo "$(GREEN)EpicIos.jar$(NOCOLOR) - Merging dependencies into build/jar_ios"
	@mkdir -p build/jar_ios
	rsync -a $(CP_JSON)/ $(CP_COMM)/ $(CP_IOS)/ build/jar_ios/
	rsync -a {EpicJson,EpicCommon,EpicIos}/src/ build/jar_ios/
	@echo "$(GREEN)EpicIos.jar$(NOCOLOR) - Combining $$(find build/jar_ios -type f | wc -l) files into build/EpicIos.jar"
	jar cf build/EpicIos.jar -C build/jar_ios .

####################################################################################################
##  Other / Third-party
####################################################################################################

.PHONY: jcommander
jcommander: EpicBuilder/lib/jcommander-1.24-SNAPSHOT-bundle.jar
EpicBuilder/lib/jcommander-1.24-SNAPSHOT-bundle.jar: 
	third-party/jcommander/build-with-maven
	cp third-party/jcommander/target/jcommander-*-bundle.jar EpicBuilder/lib/



