package org.walkmod.javalang.actions;

import org.walkmod.javalang.ast.LineComment;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.body.TypeDeclaration;

public class RemoveAction extends Action {

	private int endLine;

	private int endColumn;

	private String text;

	public RemoveAction(int beginLine, int beginPosition, int endLine, int endColumn, Node node) {
		super(beginLine, beginPosition, ActionType.REMOVE);
		this.endLine = endLine;
		this.endColumn = endColumn;

		this.text = node.toString();
		if (text.endsWith("\n") && node instanceof LineComment) {
			text = text.substring(0, text.length() - 1);
			this.endColumn--;
		} else if (text.endsWith("}") && node instanceof TypeDeclaration) {
			this.endColumn++;
		}
	}

	public String getText() {
		return text;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

}
