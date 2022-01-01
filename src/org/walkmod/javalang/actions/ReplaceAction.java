package org.walkmod.javalang.actions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Comment;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.body.BodyDeclaration;
import org.walkmod.javalang.ast.body.JavadocComment;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.stmt.BlockStmt;

public class ReplaceAction extends Action {

	private String newCode;

	private int endLine;

	private int endColumn;

	private final String oldCode;

	private final char indentationChar = ' ';

	private final int indentationLevel;

	private final int indentationSize;

	private final Node newNode;

	private final Node oldNode;

	private final List<Comment> acceptedComments = new LinkedList<Comment>();

	public ReplaceAction(int beginLine, int beginPosition, Node oldNode, Node newNode, int indentation, int indentationSize, List<Comment> comments) {
		super(beginLine, beginPosition, ActionType.REPLACE);

		if (comments != null) {
			Iterator<Comment> it = comments.iterator();

			while (it.hasNext()) {
				Comment next = it.next();
				if (oldNode.contains(next) && !(next instanceof JavadocComment)) {
					acceptedComments.add(next);
					it.remove();
				}
			}
		}

		this.oldNode = oldNode;
		this.indentationLevel = indentation;

		oldCode = oldNode.getPrettySource(indentationChar, indentationLevel, indentationSize, acceptedComments);

		this.indentationSize = indentationSize;
		this.newNode = newNode;

		getText("", indentationChar);

		String[] lines = newCode.split("\n");
		endLine = getBeginLine() + lines.length - 1;
		endColumn = lines[lines.length - 1].length();

		if (oldNode.getEndLine() >= endLine) {
			if (oldNode.getEndLine() == endLine) {
				if (oldNode.getEndColumn() > endColumn) {
					endLine = oldNode.getEndLine();
					endColumn = oldNode.getEndColumn();
				}
			} else {
				endLine = oldNode.getEndLine();
				endColumn = oldNode.getEndColumn();
			}
		}
	}

	public String getText(String indentation, char indentationChar) {

		newCode = FormatterHelper.indent(newNode.getPrettySource(indentationChar, indentationLevel, indentationSize, acceptedComments), indentation, indentationChar, indentationLevel, indentationSize, requiresExtraIndentationOnFirstLine());

		return newCode;
	}

	@Override
	public int getEndLine() {
		return endLine;
	}

	public int getOldEndLine() {
		return oldNode.getEndLine();
	}

	public int getOldEndColumn() {
		return oldNode.getEndColumn();
	}

	@Override
	public int getEndColumn() {
		return endColumn;
	}

	public String getNewText() {
		return newCode;
	}

	public String getOldText() {
		return oldCode;
	}

	private boolean requiresExtraIndentationOnFirstLine() {
		if (newNode.getClass().getName().equals(oldNode.getClass().getName())) {
			return false;
		}
		return !((newNode instanceof Expression) || (newNode instanceof BlockStmt) 
		|| (newNode instanceof BodyDeclaration));
	}

}
