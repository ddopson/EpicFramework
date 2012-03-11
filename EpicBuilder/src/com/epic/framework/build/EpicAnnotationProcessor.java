package com.epic.framework.build;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.Diagnostic.Kind;

import com.epic.framework.vendor.org.json.JSONArray;
import com.epic.framework.vendor.org.json.JSONException;
import com.epic.framework.vendor.org.json.JSONObject;

@SuppressWarnings("unused")
public class EpicAnnotationProcessor extends AbstractProcessor {
	private TypeElement currentElement;
	private String fwDir;
	public static int DEBUG = 0;
	
	private void printError(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.ERROR, msg, currentElement);
	}
	
	private void printWarning(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.WARNING, msg, currentElement);
	}
	
	private void printNotice(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.NOTE, msg, currentElement);
	}
	
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new HashSet<String>(Arrays.asList("*"));
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_6;
	}

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		String path = EpicAnnotationProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		
		File f = new File(path).getParentFile();
		
		while(f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().equals("package.json");
			}
		}).length == 0) {
			f = f.getParentFile();
			if (f == null) {
				// TODO: support passing in the fwDir as an arg
				printNotice("Initializing EpicAnnotationProcessor (" + path + "). fwDir=" + fwDir);
				printError("Unable to locate Framework Directory (has the jar file been moved somewhere unexpected?)");
			}
		}
		fwDir = f.toString();
		printNotice("Initializing EpicAnnotationProcessor (" + path + "). fwDir=" + fwDir);
	};
	private static class EpicClassDescription {
		String qualifiedName;
		String parent;
		boolean isAbstract;
		boolean inflatable;
		ArrayList<EpicFieldDescription> fields = new ArrayList<EpicFieldDescription>();
		public JSONObject toJSON() {
			try {
				JSONObject o = new JSONObject();
				int di = qualifiedName.lastIndexOf(".");
				o.put("name", qualifiedName.substring(di + 1));
				o.put("fullname", qualifiedName);
				o.put("package", qualifiedName.substring(0, di));
				o.put("parent", parent);
				o.put("abstract", isAbstract);
				o.put("inflatable", inflatable);
				JSONArray fieldsJson = new JSONArray();
				for(EpicFieldDescription fd : this.fields) {
					fieldsJson.put(fd.toJSON());
				}
				o.put("fields", fieldsJson);
				return o;
			} catch(JSONException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static class EpicFieldDescription {
		String name;
		String type;
		public JSONObject toJSON() {
			try {
				JSONObject o = new JSONObject();
				o.put("name", name);
				o.put("type", type);
				return o;
			} catch(JSONException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Set<? extends Element> elements = env.getElementsAnnotatedWith(EpicInflatableClass.class);
		for(Element el : elements) {
			// System.out.println("Element: " + el.getSimpleName());
			el.accept(new SimpleElementVisitor6<Object, Object>() {
				public Object visitType(TypeElement typeElement, Object p) {
					EpicAnnotationProcessor.this.currentElement = typeElement;
					final EpicClassDescription classDescription = new EpicClassDescription();
					classDescription.qualifiedName = typeElement.getQualifiedName().toString();
					classDescription.isAbstract = typeElement.getModifiers().contains(Modifier.ABSTRACT);
					classDescription.inflatable = typeElement.getAnnotation(EpicInflatableClass.class).inflatable() && ! classDescription.isAbstract;
					classDescription.parent = typeElement.getSuperclass().toString();
					for(Element enclosed : typeElement.getEnclosedElements()) {
						// System.out.println("SubElement: " + enclosed.getSimpleName());
						enclosed.accept(new SimpleElementVisitor6<Object, Object>() {
							public Object visitVariable(VariableElement variableElement, Object p) {
								if(variableElement.getModifiers().contains(Modifier.STATIC)) {
									return null;
								}
								EpicFieldDescription fieldDescription = new EpicFieldDescription();
								fieldDescription.name = variableElement.getSimpleName().toString();
								fieldDescription.type = variableElement.asType().toString();
								classDescription.fields.add(fieldDescription);
								return null;
							};

						}, null);
					}
					writeGeneratedClass(classDescription);
					return null;
				};
			}, null);
		}
		return true;
	}

	public static String consumeInputStream(InputStream in) throws IOException {
		Reader reader = new InputStreamReader(in);
		char[] buff = new char[4096];
		StringBuilder sb = new StringBuilder();
		int ret = 0;
		while(ret != -1) {
			ret = reader.read(buff);
			if(ret > 0) {
				sb.append(buff, 0, ret);
			}
		}
		return sb.toString();
	}

	public String generate(String metadata) throws IOException {
		String[] params = {"bin/underscore-template", "-t", "EpicBuilder/resources/class.template", "-j", metadata};
		String[] env = { "PATH=" + fwDir + "/bin" };
		File cwd = new File(fwDir);
		Process child = Runtime.getRuntime().exec(params, env, cwd);
		OutputStream stdin = child.getOutputStream ();
		InputStream stderr = child.getErrorStream ();
		InputStream stdout = child.getInputStream (); // this is actually stdout (gotta love java)
		stdin.close();
		String result = consumeInputStream(stdout);
		String errors = consumeInputStream(stderr);
		if(errors != null && ! errors.equals("")) {
			printError("CLI-ERRORS: " + errors.replaceAll("\n", "\\n"));
		}
		
		if (DEBUG >= 2) {
			printNotice("Input Metadata: " + metadata.replaceAll("\n", "\\n"));
			printNotice("Generated Class: " + result.replaceAll("\n", "\\n"));
		}
		return result;
	}

	public void writeGeneratedClass(EpicClassDescription clazz) {
		String generatedClassName = clazz.qualifiedName.replaceAll("[.]([^.]+)$", ".Class$1");
		if(DEBUG >= 1) {
			printNotice("Generating " + clazz.qualifiedName);
		}

		try {
			Writer w = this.processingEnv.getFiler().createSourceFile(generatedClassName).openWriter();
			String contents = generate(clazz.toJSON().toString());
			w.append(contents);
			w.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
