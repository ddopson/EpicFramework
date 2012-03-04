//package com.epic.framework.build;
//
//import java.io.File;
//import java.io.FileReader;
//import java.lang.reflect.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.ArrayList;
//
//import org.json.*;
//import org.mozilla.javascript.Context;
//import org.mozilla.javascript.ContextFactory;
//import org.mozilla.javascript.Scriptable;
//
//import com.beust.jcommander.JCommander;
//import com.beust.jcommander.Parameter;
//import com.beust.jcommander.ParameterException;
//
//public class EpicClassReflector {
//	public static class EpicClassReflectorArgs {
//		@Parameter(names = {"-s", "--src"}, description="The folder to search for source files")
//		String src;
//		
//		@Parameter(names = {"-d", "--dest"}, description="The folder to output generated classes to")
//		String dest;
//	}
//	
//	
//	public static void main(String[] argv) throws Exception {
//		EpicClassReflectorArgs args = new EpicClassReflectorArgs();
//		JCommander program = new JCommander(args);
//		program.setProgramName("EpicClassReflector");
//		try {
//			program.parse(argv);
//		} catch (ParameterException e) {
//			System.err.println("Error: " + e.getMessage());
//			System.err.println();
//			StringBuilder sb = new StringBuilder();
//			program.usage(sb);
//			System.err.println(sb);
//			System.exit(1);
//		}
//		EpicClassReflector reflector = new EpicClassReflector();
//		extendClasspath(new File(args.src));
//		String result = reflector.scanDir(args.src);
//		System.out.println(result);
//	}
//	
//	private static void extendClasspath(File file) throws SecurityException, NoSuchMethodException, IllegalArgumentException, MalformedURLException, IllegalAccessException, InvocationTargetException {
//	    Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
//	    method.setAccessible(true);
//	    method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});
//	}
//	
//	public String scanDir(String path) {
//		try {
//			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
//			_scanDir("", path, classes);
//			JSONArray json = new JSONArray();
//			for(Class<?> clazz : classes) {
//				json.put(_inspect(clazz));
//			}
//			return json.toString();
//		} catch(JSONException e) {
//			System.out.println("ACK, JSONException: " + e.toString());
//			return "ERROR";
//		}
//	}
//
//	public void _scanDir(String pkg, String path, ArrayList<Class<?>> classes) {
//		File directory = new File(path);
//		if (directory != null && directory.exists() && directory.isDirectory()) {
//			// Get the list of the files contained in the package
//			String[] files = directory.list();
//			for (int i = 0; i < files.length; i++) {
//				// we are only interested in .class files
//				if (files[i].endsWith(".class")) {
//					// removes the .class extension
//					String className = (pkg.equals("") ? "" : pkg + ".") + files[i].substring(0, files[i].length() - 6);
//					try {
//						classes.add(Class.forName(className));
//					} 
//					catch (ClassNotFoundException e) {
//						System.out.print("ClassNotFoundException loading " + className + ": " + e.toString());
//						throw new RuntimeException("ClassNotFoundException loading " + className);
//					}
//				} else {
//					File f = new File(directory, files[i]);
//					if(f.isDirectory()) {
//						String pkgName = (pkg.equals("") ? "" : pkg + ".") + files[i];
//						_scanDir(pkgName, f.getAbsolutePath(), classes);
//					}
//				}
//			}
//		}
//
//	}
//
//	public String inspect(Class<?> clazz) throws JSONException {
//		return _inspect(clazz).toString();
//	}
//
//	public JSONObject _inspect(Class<?> clazz) throws JSONException {
//		JSONObject classDescription = new JSONObject();
//		classDescription.put("name", clazz.getSimpleName());
//		classDescription.put("package", clazz.getPackage().getName());
//		classDescription.put("abstract", Modifier.isAbstract(clazz.getModifiers()));
//		classDescription.put("parent", clazz.getSuperclass() == null ? null : clazz.getSuperclass().getName());
//		JSONArray fields = new JSONArray();
//		classDescription.put("fields", fields);
//		for(Field f : clazz.getDeclaredFields()) {
//			if(!Modifier.isStatic(f.getModifiers())) { // exclude static fields
//				JSONObject jf = new JSONObject();
//				jf.put("name", f.getName());
//				jf.put("type", f.getType().getName());
//				jf.put("modifiers", f.getModifiers());
//				fields.put(jf);
//			}
//		}
//		return classDescription;
//	}
//
//	// TODO:
//	// inheritance - need to initialize fields of base classes
//	// arrays
//
//	// DONE:
//	// basics
//}
