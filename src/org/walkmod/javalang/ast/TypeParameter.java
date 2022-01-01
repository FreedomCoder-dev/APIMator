package org.walkmod.javalang.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;

public final class TypeParameter extends Node implements SymbolDefinition {

	private String name;

	private List<AnnotationExpr> annotations;

	private List<ClassOrInterfaceType> typeBound;

	private List<SymbolReference> usages;

	private int scopeLevel = 0;

	public TypeParameter() {}

	public TypeParameter(String name, List<ClassOrInterfaceType> typeBound) {
		this.name = name;
		setTypeBound(typeBound);
	}

	public TypeParameter(String name, List<ClassOrInterfaceType> typeBound, List<AnnotationExpr> annotations) {
		this.name = name;
		setTypeBound(typeBound);
		setAnnotations(annotations);
	}

	public TypeParameter(int beginLine, int beginColumn, int endLine, int endColumn, String name, List<ClassOrInterfaceType> typeBound) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.name = name;
		setTypeBound(typeBound);
	}

	public TypeParameter(int beginLine, int beginColumn, int endLine, int endColumn, String name, List<ClassOrInterfaceType> typeBound, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.name = name;
		setTypeBound(typeBound);
		setAnnotations(annotations);
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = new LinkedList<Node>();
		if (annotations != null) {
			children.addAll(annotations);
		}
		if (typeBound != null) {
			children.addAll(typeBound);
		}
		return children;
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		if (!check()) {
			return null;
		}
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		if (check()) {
			v.visit(this, arg);
		}
	}

	public String getName() {
		return name;
	}

	public List<ClassOrInterfaceType> getTypeBound() {
		return typeBound;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTypeBound(List<ClassOrInterfaceType> typeBound) {
		this.typeBound = typeBound;
		setAsParentNodeOf(typeBound);
	}

	public List<AnnotationExpr> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationExpr> annotations) {
		this.annotations = annotations;
		setAsParentNodeOf(annotations);
	}

	@Override
	public List<SymbolReference> getUsages() {
		return usages;
	}

	@Override
	public void setUsages(List<SymbolReference> usages) {
		this.usages = usages;
	}

	@Override
	public boolean addUsage(SymbolReference usage) {
		if (usage != null) {
			usage.setSymbolDefinition(this);
			if (usages == null) {
				usages = new LinkedList<SymbolReference>();
			}
			return usages.add(usage);
		}
		return false;
	}

	@Override
	public List<SymbolReference> getBodyReferences() {
		return null;
	}

	@Override
	public void setBodyReferences(List<SymbolReference> bodyReferences) {}

	@Override
	public boolean addBodyReference(SymbolReference bodyReference) {
		return false;
	}

	@Override
	public int getScopeLevel() {
		return scopeLevel;
	}

	@Override
	public void setScopeLevel(int scopeLevel) {
		this.scopeLevel = scopeLevel;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean update = false;
		if (annotations != null) {
			List<AnnotationExpr> auxAnnotations = new LinkedList<AnnotationExpr>(annotations);
			update = replaceChildNodeInList(oldChild, newChild, auxAnnotations);
			if (update) {
				annotations = auxAnnotations;
			}
		}
		if (!update && typeBound != null) {
			List<ClassOrInterfaceType> auxTypeBound = new LinkedList<ClassOrInterfaceType>(typeBound);
			update = replaceChildNodeInList(oldChild, newChild, auxTypeBound);
		}

		return update;
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (child instanceof ClassOrInterfaceType) {
				if (typeBound != null) {
					List<ClassOrInterfaceType> auxTypeBound = new LinkedList<ClassOrInterfaceType>(typeBound);
					result = auxTypeBound.remove(child);
					this.typeBound = auxTypeBound;
				}
			} else if (child instanceof AnnotationExpr) {
				if (annotations != null) {
					List<AnnotationExpr> annotationsAux = new LinkedList<AnnotationExpr>(annotations);
					result = annotationsAux.remove(child);
					annotations = annotationsAux;
				}
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
	}

	@Override
	public TypeParameter clone() throws CloneNotSupportedException {
		return new TypeParameter(name, clone(typeBound), clone(annotations));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(this);
	}

	@Override
	public String getSymbolName() {
		return name;
	}

}
