package org.walkmod.javalang.ast;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.visitors.DumpVisitor;
import org.walkmod.javalang.visitors.EqualsVisitor;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.modelchecker.ConstrainedElement;
import org.walkmod.modelchecker.Constraint;

public abstract class Node implements Serializable, Cloneable, ConstrainedElement {

	private int beginLine;

	private int beginColumn;

	private int endLine;

	private int endColumn;

	private Object data;

	private Node parentNode;

	private List<Constraint> constraints;

	private List<Constraint> constraintsAux;

	public Node() {}

	public void setConstraints(List<Constraint> constraints) {
		if (this.constraints != constraints) {
			this.constraints = constraints;
			if (this.constraints != null) {
				List<Node> children = getChildren();
				if (children != null) {
					for (Node child : children) {
						child.setConstraints(constraints);
					}
				}
			}
		}
	}

	public boolean remove() {
		Node parent = getParentNode();
		if (parent != null) {
			return parent.removeChild(this);
		}
		return false;
	}

	public abstract boolean removeChild(Node child);

	public abstract List<Node> getChildren();

	@Override
	public List<Constraint> getConstraints() {
		return constraints;
	}

	public Node(int beginLine, int beginColumn, int endLine, int endColumn) {
		this.beginLine = beginLine;
		this.beginColumn = beginColumn;
		this.endLine = endLine;
		this.endColumn = endColumn;
	}

	public abstract <R, A> R accept(GenericVisitor<R, A> v, A arg);

	public abstract <A> void accept(VoidVisitor<A> v, A arg);

	public final int getBeginColumn() {
		return beginColumn;
	}

	public final int getBeginLine() {
		return beginLine;
	}

	public final Object getData() {
		return data;
	}

	public final int getEndColumn() {
		return endColumn;
	}

	public final int getEndLine() {
		return endLine;
	}

	public final void setBeginColumn(int beginColumn) {
		this.beginColumn = beginColumn;
	}

	public final void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}

	public final void setData(Object data) {
		this.data = data;
	}

	public final void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

	public final void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public void enableConstraints() {
		if (constraintsAux != null && constraints != null) {
			constraints.addAll(constraintsAux);
		}
	}

	public void diableConstraints() {
		if (constraints != null) {
			constraintsAux = new LinkedList<Constraint>(constraints);
			constraints.clear();
		}
	}

	@Override
	public final String toString() {
		enableConstraints();
		DumpVisitor visitor = new DumpVisitor();
		accept(visitor, null);
		String aux = visitor.getSource();
		diableConstraints();
		return aux;
	}

	@Override
	public final int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsVisitor.equals(this, (Node) obj);
	}

	public boolean isNewNode() {
		return (0 == getEndLine() && 0 == getEndColumn() && getBeginLine() == 0);
	}

	public boolean contains(Node node2) {
		if ((getBeginLine() < node2.getBeginLine()) 
		|| ((getBeginLine() == node2.getBeginLine()) && getBeginColumn() <= node2.getBeginColumn())) {
			if (getEndLine() > node2.getEndLine()) {
				return true;
			} else return (getEndLine() == node2.getEndLine()) && getEndColumn() >= node2.getEndColumn();
		}
		return false;
	}

	public boolean isInEqualLocation(Node node2) {
		if (!isNewNode() && !node2.isNewNode()) {
			return getBeginLine() == node2.getBeginLine() && getBeginColumn() == node2.getBeginColumn() 
			&& getEndLine() == node2.getEndLine() && getEndColumn() == node2.getEndColumn();
		}
		return false;
	}

	public boolean isPreviousThan(Node node) {
		if (getEndLine() < node.getBeginLine()) {
			return true;
		} else return (getEndLine() == node.getBeginLine()) && (getEndColumn() <= node.getBeginColumn());
	}

	public String getPrettySource(char indentationChar, int indentationLevel, int indentationSize) {

		return getPrettySource(indentationChar, indentationLevel, indentationSize, null);
	}

	public String getPrettySource(char indentationChar, int indentationLevel, int indentationSize, 
	List<Comment> comments) {
		DumpVisitor visitor = new DumpVisitor();
		visitor.setIndentationChar(indentationChar);
		visitor.setIndentationLevel(indentationLevel);
		visitor.setIndentationSize(indentationSize);
		if (comments != null) {
			visitor.setComments(new LinkedList<Comment>(comments));
		}
		accept(visitor, null);
		return visitor.getSource();
	}

	public Node getParentNode() {
		return parentNode;
	}

	private void setParentNode(Node parent) {
		this.parentNode = parent;
	}

	public boolean isAncestorOf(Node other) {
		Node parent = other.getParentNode();
		while (parent != null) {
			if (parent == this) {
				return true;
			}
			parent = parent.getParentNode();
		}

		return false;
	}

	public Node getCommonAncestor(Node other) {
		Node parent = getParentNode();

		while (parent != null) {
			Node parent2 = other;
			while (parent2 != null) {
				if (parent == parent2) {
					return parent;
				}
				parent2 = parent2.getParentNode();
			}
			parent = parent.getParentNode();
		}
		return null;
	}

	protected void setAsParentNodeOf(List<? extends Node> childNodes) {
		if (childNodes != null) {
			Iterator<? extends Node> it = childNodes.iterator();
			while (it.hasNext()) {
				Node current = it.next();
				current.setParentNode(this);
			}
		}
	}

	protected void setAsParentNodeOf(Node childNode) {
		if (childNode != null) {
			childNode.setParentNode(this);
		}
	}

	protected void updateReferences(Object o) {
		if (o instanceof SymbolReference) {
			SymbolReference sr = (SymbolReference) o;
			SymbolDefinition sd = sr.getSymbolDefinition();
			if (sd != null) {
				List<SymbolReference> usages = sd.getUsages();
				if (usages != null) {
					Iterator<SymbolReference> it = usages.iterator();
					boolean found = false;
					while (it.hasNext() && !found) {
						SymbolReference current = it.next();
						if (current == o) {
							it.remove();
							found = true;
						}
					}
				}
			}
		}
		if (o instanceof SymbolDefinition) {
			SymbolDefinition sd = (SymbolDefinition) o;
			List<SymbolReference> usages = sd.getUsages();
			if (usages != null) {
				Iterator<SymbolReference> it = usages.iterator();
				while (it.hasNext()) {
					SymbolReference current = it.next();
					current.setSymbolDefinition(null);
					it.remove();
				}
				List<SymbolReference> bodyRefs = sd.getBodyReferences();
				if (bodyRefs != null) {
					for (SymbolReference sr : bodyRefs) {
						updateReferences(sr);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected boolean replaceChildNodeInList(Node oldChild, Node newChild, List list) {
		if (list != null) {
			Iterator<?> it = list.iterator();
			boolean updated = false;
			int i = 0;
			while (it.hasNext() && !updated) {
				Object current = it.next();
				if (current == oldChild) {
					it.remove();
					updateReferences(current);
					updated = true;
				}
				if (!updated) {
					i++;
				}
			}
			if (updated) {
				Node parent = oldChild.getParentNode();
				if (parent != null) {
					parent.setAsParentNodeOf(newChild);
				}
				list.add(i, newChild);
			}
			return updated;
		}
		return false;
	}

	public abstract boolean replaceChildNode(Node oldChild, Node newChild);

	protected <T extends Node> List<T> clone(List<T> original) throws CloneNotSupportedException {
		List<T> aux = null;
		if (original != null) {
			aux = new LinkedList<T>();
			for (T node : original) {
				aux.add(clone(node));
			}
		}
		return aux;
	}

	protected <T extends Node> T clone(T node) throws CloneNotSupportedException {
		if (node == null) {
			return null;
		}
		return (T) node.clone();
	}

	@Override
	public abstract Object clone() throws CloneNotSupportedException;

	@Override
	public boolean check() {
		if ((constraints == null) || constraints.isEmpty()) {
			return true;
		} else {
			Iterator<Constraint> it = constraints.iterator();
			boolean isConstrained = false;
			while (it.hasNext() && !isConstrained) {
				Constraint c = it.next();
				isConstrained = c.isConstrained(this);
			}
			return !isConstrained;
		}
	}

}
