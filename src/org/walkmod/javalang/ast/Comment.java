package org.walkmod.javalang.ast;

import java.util.LinkedList;
import java.util.List;

public abstract class Comment extends Node {

	private String content;

	public Comment() {}

	public Comment(String content) {
		this.content = content;
	}

	public Comment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.content = content;
	}

	@Override
	public boolean removeChild(Node child) {
		return false;
	}

	public final String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getPrettySource(char indentationChar, int indentationLevel, int indentationSize) {
		String text = super.getPrettySource(indentationChar, indentationLevel, indentationSize);
		if (!text.endsWith("\n")) {
			text += "\n";
		}
		return text;
	}

	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		return new LinkedList<Node>();
	}

}
