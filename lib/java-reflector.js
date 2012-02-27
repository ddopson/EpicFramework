#!/usr/bin/env node

var _ = require('underscore');
var java = require('java');

java.classpath.push(__dirname + "/../build/EpicBuilder.jar");
var reflector = java.newInstanceSync('com.epic.framework.build.EpicClassReflector');

exports.reflect = function (className) {
  var obj = java.newInstanceSync(className);
  var clazz = obj.getClassSync();
  var json = reflector.inspectSync(clazz);
  return JSON.parse(json);
}

