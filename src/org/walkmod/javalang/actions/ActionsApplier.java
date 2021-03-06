package org.walkmod.javalang.actions;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.util.FileUtils;

public class ActionsApplier {

	private String text;

	private List<Action> actions;

	private StringBuffer modifiedText;

	private Character indentationChar = null;

	private boolean annotationsInNewLines = false;

	public boolean isAnnotationsInNewLines() {
		return annotationsInNewLines;
	}

	public void setAnnotationsInNewLines(boolean annotationsInNewLines) {
		this.annotationsInNewLines = annotationsInNewLines;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(File file) {
		try {
			this.text = FileUtils.fileToString(file.getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException("Error reading the file " + file.getAbsolutePath());
		}
	}

	public void setActionList(List<Action> actions) {
		this.actions = actions;
	}

	public String getModifiedText() {
		return modifiedText.toString();
	}

	public Character getIndentationChar() {
		return indentationChar;
	}

	public void inferIndentationChar(char[] contents) {
		if (indentationChar == null) {
			indentationChar = getIndentationChar(contents, '\n');
			if (indentationChar == null) {
				indentationChar = getIndentationChar(contents, '{');
				if (indentationChar == null) {
					indentationChar = '\0';
				}
			}
		}
	}

	private Character getIndentationChar(char[] contents, char separator) {
		int spaces = 0;
		int tabs = 0;
		boolean containsSeparator = false;
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] == separator) {
				containsSeparator = true;
				if (i + 1 < contents.length) {
					if (contents[i + 1] == ' ') {
						spaces++;
					} else if (contents[i + 1] == '\t') {
						tabs++;
					}
				}
			}
		}
		if (tabs > spaces) {
			return '\t';
		} else {
			if (!containsSeparator) {
				return null;
			} else if (spaces == 0 && spaces == tabs) {
				return '\0';
			}
			return ' ';
		}
	}

	public void execute() {
		modifiedText = new StringBuffer();
		if (actions != null && text != null) {

			Iterator<Action> it = actions.iterator();

			char[] contents = text.toCharArray();

			int index = 0;

			int line = 0;
			int actionColumn = 0;

			Action previousAction = null;
			StringBuffer accum = null;

			StringBuffer indentation = new StringBuffer();

			while (it.hasNext()) {
				Action next = it.next();
				int actionLine = next.getBeginLine() - 1;

				if (previousAction != null && previousAction.getType().equals(ActionType.REMOVE) 
				&& next.getType().equals(ActionType.APPEND)) {
					accum = modifiedText;
					modifiedText = new StringBuffer();
				}

				for (int i = line; i < actionLine; i++) {

					boolean endLine = false;
					while (index < contents.length && !endLine) {
						endLine = contents[index] == '\n';
						modifiedText.append(contents[index]);
						index++;
						if (endLine) {
							line++;
							actionColumn = 0;
							if (indentation.length() != 0) {
								indentation = new StringBuffer();
							}
						}

					}
				}

				if (index < contents.length) {
					int incr = next.getBeginColumn() - 1 - actionColumn;
					boolean stop = false;
					if (incr > 0) {
						for (int i = index; i < index + incr; i++) {

							if (!stop && (contents[i] == ' ' || contents[i] == '\t')) {
								indentation.append(contents[i]);
							} else {
								stop = true;
							}
							modifiedText.append(contents[i]);
							actionColumn++;

						}
						index = index + incr;
					}
					if (previousAction != null && previousAction.getType().equals(ActionType.REMOVE) 
					&& next.getType().equals(ActionType.APPEND)) {

						if (modifiedText.toString().trim().length() != 0) {
							accum.append(modifiedText);

						}
						modifiedText = accum;
					}

					if (next.getType().equals(ActionType.REMOVE)) {
						RemoveAction remove = (RemoveAction) next;
						int lastLine = modifiedText.lastIndexOf("\n");
						String aux = modifiedText.substring(lastLine + 1);

						for (; (actionLine < (remove.getEndLine() - 1) || (actionLine == (remove.getEndLine() - 1) 
						&& actionColumn < remove.getEndColumn())); index++) {

							if (contents[index] == '\r') {

								actionColumn++;
							} else if (contents[index] != '\n') {
								actionColumn++;
							} else {
								actionLine++;
								line++;
								actionColumn = 0;

							}
						}
						if (index < contents.length && contents[index] == '\r') {
							index++;
							actionColumn++;
						}
						boolean nextIsNewLine = (index < contents.length && contents[index] == '\n');

						if (nextIsNewLine) {

							index++;
							line++;
							actionColumn = 0;

							if (remove.getBeginColumn() > 1) {
								List<Character> chars = new LinkedList<Character>();
								while (index < contents.length && (contents[index] == ' ' || contents[index] == '\t')) {
									chars.add(contents[index]);
									actionColumn++;
									index++;
								}
								if (actionColumn + 1 > 1 && actionColumn + 1 < remove.getBeginColumn()) {
									if (aux.matches("\\s+") || aux.equals("")) {
										if (!"".equals(aux)) {
											modifiedText = modifiedText.delete(lastLine + 1, modifiedText.length());
										}
									} else {
										modifiedText.append('\n');
									}

									for (int k = 0; k < actionColumn; k++) {
										modifiedText.append(chars.get(k));
									}
								} else if (actionColumn + 1 > remove.getBeginColumn()) {
									for (int k = remove.getBeginColumn(); k < actionColumn + 1; k++) {
										modifiedText.append(chars.get(k - remove.getBeginColumn()));
									}
								} else if (actionColumn == 0 && (aux.matches("\\s+") || "".equals(aux))) {
									modifiedText = modifiedText.delete(lastLine + 1, modifiedText.length());
								}
							}
						}
					} else if (next.getType().equals(ActionType.APPEND)) {
						inferIndentationChar(contents);

						AppendAction append = (AppendAction) next;

						char indent = indentationChar;
						if (indentation.length() > 1) {
							indent = indentation.charAt(indentation.length() - 1);
						}

						String code = append.getText(indentation.toString(), indent);
						modifiedText.append(code);

					} else if (next.getType().equals(ActionType.REPLACE)) {
						inferIndentationChar(contents);

						char indent = indentationChar;
						if (indentation.length() > 1) {
							indent = indentation.charAt(indentation.length() - 1);
						}

						ReplaceAction replace = (ReplaceAction) next;
						String code = replace.getText(indentation.toString(), indent);
						modifiedText.append(code);

						int futureLine = replace.getOldEndLine() - 1;

						for (; actionLine < futureLine && index < contents.length; index++) {
							if (contents[index] == '\r' || contents[index] != '\n') {
								actionColumn++;
							} else {
								actionLine++;
								actionColumn = 0;
							}
						}
						line = futureLine;
						index += (replace.getOldEndColumn() - actionColumn);
						actionColumn = replace.getOldEndColumn();
					}
				}
				previousAction = next;
			}

			for (int i = index; i < contents.length; i++) {
				modifiedText.append(contents[i]);
			}
		}
	}
}
