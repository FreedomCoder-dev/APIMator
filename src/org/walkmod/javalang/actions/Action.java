package org.walkmod.javalang.actions;

public abstract class Action {
	private int beginLine;
	private int beginColumn;
	private ActionType type;

	public Action(int beginLine, int beginPosition, ActionType type) {
		this.beginLine = beginLine;
		this.beginColumn = beginPosition;
		this.type = type;
	}

	public int getBeginLine() {
		return beginLine;
	}

	public void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}

	public int getBeginColumn() {
		return beginColumn;
	}

	public void setBeginColumn(int beginPosition) {
		this.beginColumn = beginPosition;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public abstract int getEndLine();

	public abstract int getEndColumn();

	public boolean isPreviousThan(int beginLine, int beginColumn) {

		if (beginLine > getEndLine()) {
			return true;
		}
        return beginLine == getEndLine() && beginColumn >= getEndColumn();
    }

	public boolean contains(Action action) {

		if ((getBeginLine() < action.getBeginLine()) 
		|| ((getBeginLine() == action.getBeginLine()) && getBeginColumn() <= action.getBeginColumn())) {
			if (getEndLine() > action.getEndLine()) {
				return true;
			} else if ((getEndLine() == action.getEndLine()) && getEndColumn() >= action.getEndColumn()) {
				return true;
			} else if (getEndLine() > action.getBeginLine()) {
				return true;
			} else return getEndLine() == action.getBeginLine() && getEndColumn() > action.getBeginColumn();
		}
		return false;
	}

	public boolean isEmpty() {
		return getBeginLine() == getEndLine() && getBeginColumn() == getEndColumn();
	}
}
