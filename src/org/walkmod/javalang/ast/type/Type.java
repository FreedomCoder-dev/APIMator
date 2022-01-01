package org.walkmod.javalang.ast.type;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolData;
import org.walkmod.javalang.ast.SymbolDataAware;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public abstract class Type extends Node implements SymbolDataAware<SymbolData>, SymbolReference {

	private List<AnnotationExpr> annotations;

	private SymbolData symbolData;

	private SymbolDefinition symbolDefinition;

	public Type() {}

	public Type(List<AnnotationExpr> annotation) {
		setAnnotations(annotation);
	}

	public Type(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn);
	}

	public Type(int beginLine, int beginColumn, int endLine, int endColumn, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn);
		setAnnotations(annotations);
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = new LinkedList<Node>();
		if (annotations != null) {
			children.addAll(annotations);
		}
		return children;
	}

	public List<AnnotationExpr> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationExpr> annotations) {
		this.annotations = annotations;
		setAsParentNodeOf(annotations);
	}

	@Override
	public SymbolData getSymbolData() {
		return symbolData;
	}

	@Override
	public void setSymbolData(SymbolData symbolData) {
		this.symbolData = symbolData;
	}

	@Override
	public SymbolDefinition getSymbolDefinition() {
		return symbolDefinition;
	}

	@Override
	public void setSymbolDefinition(SymbolDefinition symbolDefinition) {
		this.symbolDefinition = symbolDefinition;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean update = false;
		if (annotations != null) {
			List<AnnotationExpr> auxAnn = new LinkedList<AnnotationExpr>(annotations);
			update = replaceChildNodeInList(oldChild, newChild, auxAnn);
			if (update) {
				annotations = auxAnn;
			}
		}
		return update;
	}

	@Override
	public abstract Type clone() throws CloneNotSupportedException;

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(Type.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(Type.this);
	}
}
