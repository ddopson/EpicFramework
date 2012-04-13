package com.epic.framework.build;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class TrivialElementVisitor implements ElementVisitor<Object, Object>{
	
	public void visitExecutable(ExecutableElement e) { }
	public void visitPackage(PackageElement e) { }
	public void visitType(TypeElement e) { }
	public void visitTypeParameter(TypeParameterElement e) { }
	public void visitUnknown(Element e) { }
	public void visitVariable(VariableElement e) { }

	@Override
	public Object visit(Element e) {
		return null;
	}

	@Override
	public Object visit(Element e, Object p) {
		return null;
	}

	@Override
	public Object visitExecutable(ExecutableElement e, Object p) {
		visitExecutable(e);
		return null;
	}

	@Override
	public Object visitPackage(PackageElement e, Object p) {
		visitPackage(e);
		return null;
	}

	@Override
	public Object visitType(TypeElement e, Object p) {
		visitType(e);
		return null;
	}

	@Override
	public Object visitTypeParameter(TypeParameterElement e, Object p) {
		visitTypeParameter(e);
		return null;
	}

	@Override
	public Object visitUnknown(Element e, Object p) {
		visitUnknown(e);
		return null;
	}

	@Override
	public Object visitVariable(VariableElement e, Object p) {
		visitVariable(e);
		return null;
	}
}
