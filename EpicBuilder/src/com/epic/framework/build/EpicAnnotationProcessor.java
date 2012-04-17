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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Arrays;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
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
			this.DEBUG = 3;
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
			if(el instanceof TypeElement) {
				TypeElement typeElement = (TypeElement)el;
				EpicAnnotationProcessor.this.currentElement = typeElement;
				processType(typeElement);
			}
		}
		return true;
	}
	private static AnnotationMirror getAnnotationMirror(TypeElement typeElement, String className) {
		for(AnnotationMirror m : typeElement.getAnnotationMirrors()) {
			if(m.getAnnotationType().toString().equals(className)) {
				return m;
			}
		}
		return null;
	}

	private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
		for(Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
			if(entry.getKey().getSimpleName().toString().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	private TypeElement getAnnotationValueAsType(AnnotationMirror annotationMirror, String key) {
		AnnotationValue annotationValue = getAnnotationValue(annotationMirror, key);
		if(annotationValue == null) {
			return null;
		}
		TypeMirror typeMirror = (TypeMirror)annotationValue.getValue();
		if(typeMirror == null) {
			return null;
		}
		return (TypeElement)TypeUtils.asElement(typeMirror);
	}

	private void processType(TypeElement typeElement) {
		if(! (typeElement.getEnclosingElement() instanceof PackageElement)) {
			System.out.println("Skipping " + typeElement.toString() + "(" + typeElement.getEnclosingElement() + ")");
			return;
		}
		final EpicClassDescription classDescription = new EpicClassDescription();
		EpicInflatableClass annotation = typeElement.getAnnotation(EpicInflatableClass.class);
		classDescription.qualifiedName = typeElement.getQualifiedName().toString();
		classDescription.isAbstract = typeElement.getModifiers().contains(Modifier.ABSTRACT);
		classDescription.parent = typeElement.getSuperclass().toString();

		AnnotationMirror annotationMirror = getAnnotationMirror(typeElement, EpicInflatableClass.class.getCanonicalName());
		TypeElement inflationArgsValue = getAnnotationValueAsType(annotationMirror, "inflationArguments");

		if(inflationArgsValue == null || inflationArgsValue.getQualifiedName().toString().equals(EpicInflatableClass.class.getCanonicalName()) /* default case */) {
			classDescription.inflatable = annotation.inflatable() && ! classDescription.isAbstract;
			classDescription.inflationArgumentsType = classDescription.qualifiedName;
			classDescription.fields = getFields(typeElement);
			classDescription.inflationMode = "simple";
		} else {
			classDescription.inflatable = true;
			classDescription.inflationArgumentsType = inflationArgsValue.getQualifiedName().toString();
			classDescription.fields = getFields(inflationArgsValue);
			classDescription.inflationMode = "init";
		}

		String generatedClass = generate(classDescription);
		writeGeneratedClass(classDescription.getGeneratedClassName(), generatedClass);
	}

	private Collection<EpicFieldDescription> getFields(Class<?> clazz) {
		ArrayList<EpicFieldDescription> fields = new ArrayList<EpicFieldDescription>();
		Class<?> superclass = clazz.getSuperclass();
		if(superclass != null && superclass.getAnnotation(EpicInflatableClass.class) != null) {
			fields.addAll(getFields(superclass));
		}
		for(Field f : clazz.getFields()) {
			if(java.lang.reflect.Modifier.isAbstract(f.getModifiers())) {
				continue;
			}
			EpicFieldInflation annotation = f.getAnnotation(EpicFieldInflation.class);
			if(annotation != null && annotation.ignore()) {
				continue;
			}
			EpicFieldDescription fieldDescription = new EpicFieldDescription();
			fieldDescription.name = f.getName().toString();
			fieldDescription.type = f.getType().getCanonicalName();
			fields.add(fieldDescription);
		}
		return fields;
	}

	private Collection<EpicFieldDescription> getFields(TypeElement typeElement) {
		TypeMirror superclass = typeElement.getSuperclass();
		final ArrayList<EpicFieldDescription> fields = new ArrayList<EpicFieldDescription>();
		EpicInflatableClass annotation = typeElement.getAnnotation(EpicInflatableClass.class);
		// Handle Inheritance - if the superclass also supports EpicFieldInflation, then add the logic for the superclass's fields too
		if (! (annotation != null && annotation.ignoreSuperclass())) {
			Element superclassElement = TypeUtils.asElement(superclass);
			if(superclassElement == null) {
				if(DEBUG >=1 ) System.out.println("superclassElement is NULL. sc=" + superclass.toString());
			} else {
				if(DEBUG >=1 ) System.out.println("Visiting superclass " + superclassElement.toString());
				superclassElement.accept(new TrivialElementVisitor() {
					@Override
					public void visitType(TypeElement supertypeElement) {
						if(supertypeElement.getAnnotation(EpicInflatableClass.class) != null) {
							fields.addAll(getFields(supertypeElement));
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
					Element variableTypeElement = TypeUtils.asElement(variableElement.asType());
					if(variableTypeElement != null && variableTypeElement.getAnnotation(EpicInflatableClass.class) != null) {
						fieldDescription.isInflatable = true;
					} else {
						fieldDescription.isInflatable = false;
					}
					fields.add(fieldDescription);
				};

			}, null);
		}
		return fields;
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
				printNotice("Generated Class: " + result.replaceAll("\n", "\\n"));
			}

			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
