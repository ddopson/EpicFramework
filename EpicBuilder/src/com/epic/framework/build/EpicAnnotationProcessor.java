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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.vendor.org.json.simple.JSONArray;
import com.epic.framework.vendor.org.json.simple.JSONObject;

@SuppressWarnings("unused")
public class EpicAnnotationProcessor extends AbstractProcessor {

	// Members: Options
	private int DEBUG = 0;
	private String fwDir;
	
	// Members: Processing State
	private TypeElement currentElement;	
	
	// Members: Utils
	private Elements ElementUtils;
	private Types TypeUtils;


	private void printError(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.ERROR, msg, currentElement);
	}

	private void printWarning(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.WARNING, msg, currentElement);
	}

	private void printNotice(String msg) {
		this.processingEnv.getMessager().printMessage(Kind.NOTE, msg, currentElement);
	}

	public void writeGeneratedClass(String generatedClassName, String content) {
		try {
			Writer w = this.processingEnv.getFiler().createSourceFile(generatedClassName).openWriter();
			w.append(content);
			w.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		this.processingEnv = processingEnv;

		this.ElementUtils = processingEnv.getElementUtils();
		this.TypeUtils = processingEnv.getTypeUtils();
		this.fwDir = findFrameworkDir();
		this.processOptions(processingEnv.getOptions());
		

	};

	private void processOptions(Map<String, String> options) {
		if(options.containsKey("DEBUG")) {
			this.DEBUG++;
		}
	}
	
	/**
	 * This method recursively searches for the framework's root directory, signified by location of the 'package.json' file.
	 * It begins with the location of the JAR or .class file containing this code, and searches the parent directory until 'package.json' is located.
	 * @return
	 */
	private String findFrameworkDir() {
		String path = EpicAnnotationProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File f = new File(path).getParentFile();

		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().equals("package.json");
			}
		};
		
		while(f.listFiles(filter).length == 0) {
			f = f.getParentFile();
			if (f == null) {
				// TODO: support passing in the fwDir as an arg
				printError("Unable to locate Framework Directory (has the jar file been moved somewhere unexpected?).  Search began with path=" + path);
			}
		}
		printNotice("Initializing EpicAnnotationProcessor (" + path + "). fwDir=" + f.toString());
		return f.toString();
	}

	/**
	 * This method is the entry point for processing.
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Set<? extends Element> elements = env.getElementsAnnotatedWith(EpicInflatableClass.class);
		for(Element el : elements) {
			// System.out.println("Element: " + el.getSimpleName());
			el.accept(new TrivialElementVisitor() {
				public void visitType(TypeElement typeElement) {
					EpicAnnotationProcessor.this.currentElement = typeElement;
					processType(typeElement);
				};
			}, null);
		}
		return true;
	}
	
	private void processType(TypeElement typeElement) {
		final EpicClassDescription classDescription = new EpicClassDescription();
		classDescription.qualifiedName = typeElement.getQualifiedName().toString();
		classDescription.isAbstract = typeElement.getModifiers().contains(Modifier.ABSTRACT);
		classDescription.inflatable = typeElement.getAnnotation(EpicInflatableClass.class).inflatable() && ! classDescription.isAbstract;
		classDescription.parent = typeElement.getSuperclass().toString();
		addFields(classDescription, typeElement);
		String generatedClass = generate(classDescription);
		writeGeneratedClass(classDescription.getGeneratedClassName(), generatedClass);
	}

	private void addFields(final EpicClassDescription classDescription, TypeElement typeElement) {
		TypeMirror superclass = typeElement.getSuperclass();
		
		// Handle Inheritance - if the superclass also supports EpicFieldInflation, then add the logic for the superclass's fields too
		if (! typeElement.getAnnotation(EpicInflatableClass.class).ignoreSuperclass()) {
			Element superclassElement = TypeUtils.asElement(superclass);
			if(superclassElement == null) {
				if(DEBUG >=1 ) System.out.println("superclassElement is NULL. sc=" + superclass.toString());
			} else {
				if(DEBUG >=1 ) System.out.println("Visiting superclass " + superclassElement.toString());
				superclassElement.accept(new TrivialElementVisitor() {
					@Override
					public void visitType(TypeElement supertypeElement) {
						if(supertypeElement.getAnnotation(EpicInflatableClass.class) != null) {
							addFields(classDescription, supertypeElement);
						}
					}
				}, null);
			}
		}
		for(Element enclosed : typeElement.getEnclosedElements()) {
			// System.out.println("SubElement: " + enclosed.getSimpleName());
			enclosed.accept(new TrivialElementVisitor() {
				public void visitVariable(VariableElement variableElement) {
					if(variableElement.getModifiers().contains(Modifier.STATIC)) {
						return;
					}
					EpicFieldInflation annotation = variableElement.getAnnotation(EpicFieldInflation.class);
					if(annotation != null && annotation.ignore()) {
						return;
					}
					EpicFieldDescription fieldDescription = new EpicFieldDescription();
					fieldDescription.name = variableElement.getSimpleName().toString();
					fieldDescription.type = variableElement.asType().toString();
					classDescription.fields.add(fieldDescription);
				};

			}, null);
		}
	}
	
	public String generate(EpicClassDescription clazz) {
		if(DEBUG >= 1) {
			printNotice("Generating " + clazz.qualifiedName);
		}

		String metadata = clazz.toJSON().toString();
		String[] params = {"bin/underscore-template", "-t", "EpicBuilder/resources/class.template", "-j", metadata};
		String[] env = { "PATH=" + fwDir + "/bin" };
		File cwd = new File(fwDir);
		try {
			Process child = Runtime.getRuntime().exec(params, env, cwd);
			OutputStream stdin = child.getOutputStream ();
			InputStream stderr = child.getErrorStream ();
			InputStream stdout = child.getInputStream (); // this is actually stdout (gotta love java)
			stdin.close();
			String result = StreamUtil.consumeInputStream(stdout);
			String errors = StreamUtil.consumeInputStream(stderr);
			if(errors != null && ! errors.equals("")) {
				printError("CLI-ERRORS: " + errors.replaceAll("\n", "\\n"));
				printNotice("CLI=bin/underscore-template -t EpicBuilder/resources/class.template -j " + metadata);
			}
			if (DEBUG >= 2) {
				printNotice("Input Metadata: " + metadata.replaceAll("\n", "\\n"));
				printNotice("Generated Class: " + result.replaceAll("\n", "\\n"));
			}
			
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
