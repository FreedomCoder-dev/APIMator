package org.walkmod.javalang.constraints;

import java.util.HashSet;

import org.walkmod.javalang.ast.Node;
import org.walkmod.modelchecker.Constraint;

public class NodesPerLineConstraint implements Constraint<Node> {

	private final HashSet<Integer> segments = new HashSet<Integer>();

	public NodesPerLineConstraint() {}

	public void addLine(int beginLine, int endLine) {

		for (int i = beginLine; i <= endLine; i++) {
			segments.add(i);
		}
	}

	public void addLine(int lineNumber) {
		segments.add(lineNumber);
	}

	@Override
	public boolean isConstrained(Node o) {
		if (o.isNewNode()) {
			return false;
		}
		int beginLine = o.getBeginLine();
		int endLine = o.getEndLine();

		if (segments.contains(beginLine) && segments.contains(endLine)) {
			return false;
		}
		boolean includesAChildrenNode = false;
		for (int i = beginLine; i <= endLine && !includesAChildrenNode; i++) {
			includesAChildrenNode = segments.contains(i);
		}
		return !includesAChildrenNode;
	}

}
