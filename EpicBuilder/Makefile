
java_src_files = $(shell find src -name '*.java' -type file)

.PHONY: build
build:
	mkdir -p build/classes
	javac -d build/classes $(java_src_files)
	jar cf build/EpicBuild.jar $$(find build/classes)
