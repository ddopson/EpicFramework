package com.epic.framework.build;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.util.Collections.*;

//@SuppressWarnings("restriction")
public class EpicAnnotationProcessor extends AbstractProcessor {

	//	private static class EpicAnnotationProcessor2 extends SimpleDeclarationVisitor implements AnnotationProcessor {
	//		private final AnnotationProcessorEnvironment env;
	//		EpicAnnotationProcessor2(AnnotationProcessorEnvironment env) {
	//			this.env = env;
	//		}
	//
	//		public void process() {
	//			for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()) {
	//				System.out.println("Type: " + typeDecl.getQualifiedName());
	//				typeDecl.accept(DeclarationVisitors.getDeclarationScanner(this, DeclarationVisitors.NO_OP));
	//			}
	//		}
	//
	//		public void visitClassDeclaration(ClassDeclaration clazz) {
	//			if(clazz.getAnnotation(EpicInflatableClass.class) == null) {
	//				System.out.println("Ignoring " + clazz.getSimpleName());
	//				return;
	//			}
	//			if(clazz.getSimpleName().startsWith("Class")) {
	//				System.out.println("Ignoring " + clazz.getSimpleName());
	//				return;
	//			} else {
	//				System.out.println("Processing " + clazz.getSimpleName());
	//			}
	//
	//			try {
	//				String generatedClass = clazz.getQualifiedName().replaceAll("[.]([^.]+)$", ".Class$1");
	//				String metadata = inspectClass(clazz);
	//				System.out.println(metadata);
	//				String contents = EpicClassTemplate.generate(metadata);
	//				writeClass(generatedClass, contents);
	//			} catch (IOException e) {
	//				throw new RuntimeException(e);
	//			} catch (JSONException e) {
	//				throw new RuntimeException(e);
	//			}
	//		}
	//
	//		public void writeClass(String name, String contents) {
	//			Filer filer = env.getFiler();
	//			PrintWriter pw, pw2;
	//			try {
	//				pw = filer.createSourceFile(name);
	//				pw2 = filer.createTextFile(Filer.Location.CLASS_TREE, name.replaceAll("[.][^.]+$", ""), new File(name.replaceAll(".*[.]", "") + ".java"), null);
	//			} catch (IOException e) {
	//				throw new RuntimeException(e);
	//			}
	//			pw.append(contents);
	//			pw2.append(contents);
	//			pw.close();
	//			pw2.close();
	//		}
	//
	//		public String inspectClass(ClassDeclaration clazz) throws JSONException {
	//			JSONObject classDescription = new JSONObject();
	//			classDescription.put("name", clazz.getSimpleName());
	//			classDescription.put("fullname", clazz.getQualifiedName());
	//			classDescription.put("package", clazz.getPackage().getQualifiedName());
	//			classDescription.put("abstract", clazz.getModifiers().contains(Modifier.ABSTRACT));
	//			classDescription.put("parent", clazz.getSuperclass() == null ? null : clazz.getSuperclass().getDeclaration().getQualifiedName());
	//			JSONArray fields = new JSONArray();
	//			classDescription.put("fields", fields);
	//			for(FieldDeclaration f : clazz.getFields()) {
	//				if(!f.getModifiers().contains(Modifier.STATIC)) { // exclude static fields
	//					JSONObject jf = new JSONObject();
	//					jf.put("name", f.getSimpleName());
	//					jf.put("type", f.getType().toString());
	//					jf.put("modifiers", f.getModifiers());
	//					fields.put(jf);
	//				}
	//			}
	//			return classDescription.toString();
	//		}
	//	}

	//	@Override
	//	public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
	//		return Arrays.asList();
	//	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new HashSet<String>(Arrays.asList("*"));
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_6;
	}

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
		final Elements elementUtil = this.processingEnv.getElementUtils();
		final Filer filer = this.processingEnv.getFiler();
		final Types typeUtil = this.processingEnv.getTypeUtils();
		final Messager messager = this.processingEnv.getMessager();
		Set<? extends Element> elements = env.getElementsAnnotatedWith(EpicInflatableClass.class);
		for(Element el : elements) {
			//			System.out.println("Element: " + el.getSimpleName());
			el.accept(new SimpleElementVisitor6<Object, Object>() {
				public Object visitType(TypeElement typeElement, Object p) {
					final EpicClassDescription classDescription = new EpicClassDescription();
					classDescription.qualifiedName = typeElement.getQualifiedName().toString();
					classDescription.isAbstract = typeElement.getModifiers().contains(Modifier.ABSTRACT);
					classDescription.inflatable = typeElement.getAnnotation(EpicInflatableClass.class).inflatable() && ! classDescription.isAbstract;
					classDescription.parent = typeElement.getSuperclass().toString();
					for(Element enclosed : typeElement.getEnclosedElements()) {
						//						System.out.println("SubElement: " + enclosed.getSimpleName());
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
//					messager.printMessage(Kind.NOTE, "Generating " + classDescription.qualifiedName, typeElement);
					writeGeneratedClass(filer, classDescription);
//					System.out.println(classDescription.toJSON().toString());
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
	
	public static String generate(String metadata) throws IOException {
		String fwDir = "/workspace/EpicFramework";
		String[] params = {"bin/underscore-template", "-t", fwDir + "/EpicBuilder/resources/class.template", "-j", metadata};
		Process p = Runtime.getRuntime().exec(params, null, new File(fwDir));
		InputStream in = p.getInputStream(); // this is actually stdout (gotta love java)
		String result = consumeInputStream(in);
		
		System.out.println("Input Metadata: " + metadata);
//		System.out.println("Generated Class: " + result);
		return result;
	}
	
	public static void writeGeneratedClass(Filer filer, EpicClassDescription clazz) {
		String generatedClassName = clazz.qualifiedName.replaceAll("[.]([^.]+)$", ".Class$1");
		try {
			Writer w = filer.createSourceFile(generatedClassName).openWriter();
			String contents = generate(clazz.toJSON().toString());
			w.append(contents);
			w.close();

			//			(Filer.Location.CLASS_TREE, name.replaceAll("[.][^.]+$", ""), new File(name.replaceAll(".*[.]", "") + ".java"), null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
