package org.walkmod.javalang.ast.body;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class JavadocTag extends Node implements SymbolReference {

	private String name;

	private List<String> values = null;

	private boolean isInline = false;

	private SymbolDefinition definition;

	public JavadocTag() {}

	public JavadocTag(String name, List<String> values, boolean isInline) {
		setName(name);
		setValues(values);
		setInline(isInline);
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {

		return null;
	}

	@Override
	public List<Node> getChildren() {
		return new LinkedList<Node>();
	}

	@Override
	public boolean removeChild(Node child) {
		return false;
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public boolean isInline() {
		return isInline;
	}

	public void setInline(boolean isInline) {
		this.isInline = isInline;
	}

	@Override
	public SymbolDefinition getSymbolDefinition() {
		return definition;
	}

	@Override
	public void setSymbolDefinition(SymbolDefinition definition) {
		this.definition = definition;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public JavadocTag clone() throws CloneNotSupportedException {
		List<String> copy = null;
		if (values != null) {
			copy = new LinkedList<String>(values);
		}
		return new JavadocTag(name, copy, isInline);
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(JavadocTag.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(JavadocTag.this);
	}
}
