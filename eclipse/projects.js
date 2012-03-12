

module.exports = {
  EpicBuilder: {
    name: "EpicBuilder",
    sources: {
      "EpicBuilder": "PARENT-1-WORKSPACE_LOC/EpicBuilder/src",
    },
    dirs: {
      "resources": "PARENT-1-WORKSPACE_LOC/EpicBuilder/resources"
    }, 
    deps: [
      "../../EpicBuilder/lib/jcommander-1.24-SNAPSHOT-bundle.jar",
    ]
  },

  Android: {
    name: "Android",
    sources: {
      "EpicCommon": "PARENT-1-WORKSPACE_LOC/EpicCommon/src",
      "EpicAndroid": "PARENT-1-WORKSPACE_LOC/EpicAndroid/src"
    },
    deps: [
      "/workspace/SDK/android-sdk-r06/platforms/android-11/android.jar",
      { project: "EpicBuilder" }
    ]
  },

  iOS: {
    name: "iOS",
    sources: {
      "EpicCommon": "PARENT-1-WORKSPACE_LOC/EpicCommon/src",
      "EpicIos": "PARENT-1-WORKSPACE_LOC/EpicIos/src"
    },
    deps: [
      "../../xmlvm/xmlvm.jar",
      { project: "EpicBuilder" }
    ]
  },

  Desktop: {
    name: "Desktop",
    sources: {
      "EpicCommon": "PARENT-1-WORKSPACE_LOC/EpicCommon/src",
      "EpicDesktop": "PARENT-1-WORKSPACE_LOC/EpicDesktop/src"
    },
    deps: [
      "../../EpicDesktop/lib/jl1.0.1.jar",
      { project: "EpicBuilder" }
    ]
  },

  NullPlat: {
    name: "NullPlat",
    sources: {
      "EpicCommon": "PARENT-1-WORKSPACE_LOC/EpicCommon/src",
      "EpicNullPlat": "PARENT-1-WORKSPACE_LOC/EpicNullPlat/src"
    },
    deps: [
      { project: "EpicBuilder" }
    ]
  },

  HelloResources: {
    name: "Example: HelloResources",
    sources: {
      "src": "PARENT-1-WORKSPACE_LOC/examples/HelloResources/src",
      "gen": "PARENT-1-WORKSPACE_LOC/examples/HelloResources/gen"
    },
    dirs: {
      "resources": "PARENT-1-WORKSPACE_LOC/examples/HelloResources/resources"
    },
    deps: [
      "../../build/EpicDesktop.jar"
    ]
  },

  HelloWorld: {
    name: "Example: HelloWorld",
    sources: {
      "src": "PARENT-1-WORKSPACE_LOC/examples/HelloWorld/src"
    },
    deps: [
      "../../build/EpicDesktop.jar"
    ]
  },

  HelloUIv2: {
    name: "Example: HelloUIv2",
    sources: {
      "src": "PARENT-1-WORKSPACE_LOC/examples/HelloUIv2/src"
    },
    deps: [
      "../../build/EpicDesktop.jar"
    ]
  }
}

_.map(module.exports, function (obj) {
  if (!obj.dirs) {
    obj.dirs = [];
  }
});
