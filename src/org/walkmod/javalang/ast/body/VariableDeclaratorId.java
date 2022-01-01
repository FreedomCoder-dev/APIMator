package org.walkmod.javalang.ast.body;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class VariableDeclaratorId extends Node {

	private String name;

	private int arrayCount;

	public VariableDeclaratorId() {}

	public VariableDeclaratorId(String name) {
		this.name = name;
	}

	public VariableDeclaratorId(int beginLine, int beginColumn, int endLine, int endColumn, String name, int arrayCount) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.name = name;
		this.arrayCount = arrayCount;
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

	public int getArrayCount() {
		return arrayCount;
	}

	public String getName() {
		return name;
	}

	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public VariableDeclaratorId clone() throws CloneNotSupportedException {
		return new VariableDeclaratorId(getName());
	}
}
