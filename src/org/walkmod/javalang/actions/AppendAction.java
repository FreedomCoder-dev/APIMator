package org.walkmod.javalang.actions;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.stmt.BlockStmt;

public class AppendAction extends Action {

	private final Node node;

	private int endLine = -1;

	private int endColumn = -1;

	private int textIndentationLevel = 0;

	private int indentationSize = 0;

	private int beginPosition = 0;

	private char indentationChar = ' ';

	private String text = null;

	private int positionIndentationLevel = 0;

	private int endLines = 0;

	public AppendAction(int beginLine, int beginPosition, Node node, int blockLevel, int indentationSize) {
		this(beginLine, beginPosition, node, blockLevel, indentationSize, 0);
	}

	public AppendAction(int beginLine, int beginPosition, Node node, int blockLevel, int indentationSize, int endLines) {
		super(beginLine, beginPosition, ActionType.APPEND);
		this.textIndentationLevel = blockLevel;
		this.indentationSize = indentationSize;
		this.node = node;
		this.beginPosition = beginPosition;
		this.endLines = endLines;

		adjustTextIndentationLevelToNodeIndentation();

		getText("", indentationChar);

		String[] lines = text.split("\n");
		endLine = getBeginLine() + lines.length - 1;
		if (endLine == beginLine) {
			endColumn = getBeginColumn() + lines[lines.length - 1].length();
		} else {
			endColumn = lines[lines.length - 1].length();
		}
	}

	private void adjustTextIndentationLevelToNodeIndentation() {
		if (beginPosition > 1 && indentationSize > 0) {
			positionIndentationLevel = (beginPosition - 1) / indentationSize;

			if (positionIndentationLevel <= textIndentationLevel) {
				textIndentationLevel = textIndentationLevel - positionIndentationLevel;
			} else {
				textIndentationLevel = positionIndentationLevel;
			}
		}
	}

	public int getEndLines() {
		return endLines;
	}

	public String getText(String indentation, char indentationChar) {

		this.indentationChar = indentationChar;
		text = node.getPrettySource(indentationChar, textIndentationLevel, indentationSize);

		if (endLines > 0) {
			if (!text.endsWith("\n")) {
				if (text.endsWith(" ")) {
					text = text.substring(0, text.length() - 1);
				}
				text += "\n";
			}
		}

		String[] lines = text.split("\n");

		if (beginPosition > 1 && indentationSize > 0) {
			if (lines.length > 1) {
				StringBuffer aux = new StringBuffer();

				aux.append(FormatterHelper.indent(text, indentation, indentationChar, textIndentationLevel, indentationSize, requiresExtraIndentationOnFirstLine(node)));

				for (int i = 1; i < endLines; i++) {
					aux.append('\n');
				}

				text = aux.toString();
			}
			if (indentation.length() > 0) {
				text += indentation;
			} else {
				StringBuffer extraString = new StringBuffer();

				for (int i = 0; i < positionIndentationLevel * indentationSize; i++) {
					extraString.append(indentationChar);
				}
				text += extraString;
			}
		}

		return text;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public int getIndentations() {
		return indentationSize;
	}

	private boolean requiresExtraIndentationOnFirstLine(Node node) {

		return !((node instanceof Expression) || (node instanceof BlockStmt));
	}

}
