
Platforms:
 * Support building for IOS
 ** Need Ios version of JSON
 * Support building for Android
 * Support modular framework plugins


JSON:
  * Rewrite everything as a streaming parser that retains textual context and can emit errors that reference textual offsets
  * Add a debug popup for Parse errors that shows the JSON text and the location of the error.  Error types: JSONSyntax, JSONParse, Epic
  * support for reading an entire directory hierarchy and spitting out a consolidated and validated JSON file


Embedding Dependencies:
 * embed Node.js + deps
 * embed ImageMagick

Framework Cleanup:
 * Clean up the EpicSocial code-clusterfuck
 * Clean up config story - core set of config entries in a well-known-location (eg, EpicPlatform) that can be overridden per-project and per-platform.  The project should have a configInit() method or similar to override config settings.  Need a well-documented Bootup story
 * Get all player state refs out of the framework


UIv2:
 * Support for arrays - array constructor in the codegen + Registry.inflateArray
 * Inheritance







Build:
 * Need underscore template based generation for eclipse projects
 * New Project creation script
 * Finish commander work on the CodeGen thingy and integrate it into EpicFramework
 * Finish todo items for codegen
 * Integrate the prebuild.pl crap into the Framework
 * Create sample project using new framework and add a basic main menu
 * Rationalize how we expose the N build steps so the per-project Makefile isnt batshit insane

Done:
 * Codegen tool - written as an Annotation Processor
 * JAR files build successfully
 * lots of reorganizing
 * using Eclipse links since Eclipse sucks donkey balls when given symlinks
