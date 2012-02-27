####################################################################################################
##  Phony Targets
####################################################################################################

.PHONY: build
build: node_modules build/EpicBuilder.jar build/EpicNullPlat.jar build/EpicCommon.jar build/EpicDesktop.jar

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


java_src_files_builder := $(shell find EpicBuilder/src -name '*.java' -type file -follow)
java_src_files_null    := $(shell find EpicNullPlat/src -name '*.java' -type file -follow)
java_src_files_common  := $(shell find EpicCommon/src -name '*.java' -type file -follow)
java_src_files_desktop := $(shell find EpicDesktop/src -name '*.java' -type file -follow)
java_src_files_applet  := $(shell find EpicDesktop/src -name '*.java' -type file -follow)

JAROPT_COMMON          := -C build/classes_common com/epic/framework/common
JAROPT_COMMON_SRC      := -C EpicCommon/src com/epic/framework/common


####################################################################################################
##  Targets
####################################################################################################


node_modules: package.json
	npm install
	@touch node_modules

build/EpicBuilder.jar: build/.make.EpicBuilder
build/.make.EpicBuilder: $(java_src_files_builder)
	@mkdir -p build/classes_builder
	@echo "$(GREEN)javac$(NOCOLOR) - Compiling $(words $(java_src_files_builder)) EpicBuilder source files ..."
	@javac -sourcepath EpicBuilder/src -d build/classes_builder $(java_src_files_builder)
	jar cf build/EpicBuilder.jar -C build/classes_builder .
	jar cf build/EpicBuilder.src.jar -C EpicBuilder/src .
	@touch build/.make.EpicBuilder

build/EpicNullPlat.jar: build/.make.EpicNullPlat
build/.make.EpicNullPlat: $(java_src_files_null)
	@mkdir -p build/classes_common
	@echo "$(GREEN)javac$(NOCOLOR) - Compiling $(words $(java_src_files_null)) EpicNullPlat source files ..."
#    javac here will "autobuild" classes from EpicCommon as needed"
	@javac -sourcepath EpicCommon/src -d build/classes_common $(java_src_files_null)
	jar cf build/EpicNullPlat.jar -C build/classes_common com/epic/framework/implementation
	jar cf build/EpicNullPlat.src.jar -C EpicNullPlat/src com/epic/framework/implementation
	@touch build/.make.EpicNullPlat

build/EpicCommon.jar: build/.make.EpicCommon
build/.make.EpicCommon: $(java_src_files_common) build/EpicNullPlat.jar
	@mkdir -p build/classes_common
	@echo "$(GREEN)javac$(NOCOLOR) - Compiling $(words $(java_src_files_common)) EpicCommon source files ..."
	@javac -d build/classes_common -classpath build/EpicNullPlat.jar $(java_src_files_common) 
	@echo "$(GREEN)jar$(NOCOLOR) - EpicCommon.jar"
	jar cf build/EpicCommon.jar $(JAROPT_COMMON)
	jar cf build/EpicCommon.src.jar $(JAROPT_COMMON_SRC)
	@touch build/.make.EpicCommon

# DDOPSON-2012-02-26 - Note on Dependencies - Desktop/Andraid/Ios deliberately "depend" on build/EpicNullPlat.jar rather than build/EpicCommon.jar
# It has fewer false-positives, yet any changes that affects API will break EpicNullPlat (low false-negative) - making it a better "dependency" for Makfile purposes

build/EpicDesktop.jar: build/.make.EpicDesktop
build/.make.EpicDesktop: $(java_src_files_desktop) build/EpicNullPlat.jar
	@mkdir -p build/classes_desktop
	@echo "$(GREEN)javac$(NOCOLOR) - Compiling $(words $(java_src_files_desktop)) EpicDesktop source files ..."
	javac -d build/classes_desktop -classpath $(call classpathify,build/EpicCommon.jar EpicDesktop/lib/*.jar) $(java_src_files_desktop)
	@echo "$(GREEN)unzip$(NOCOLOR) - Merging dependency jars into build/classes_desktop"
	@cd build/classes_desktop jar xf EpicDesktop/lib/*.jar
	@echo "$(GREEN)jar$(NOCOLOR) - EpicDesktop.jar"
	jar cf build/EpicDesktop.jar -C build/classes_desktop . $(JAROPT_COMMON)
	jar cf build/EpicDesktop.src.jar -C EpicDesktop/src . $(JAROPT_COMMON_SRC)
	@touch build/.make.EpicDesktop
