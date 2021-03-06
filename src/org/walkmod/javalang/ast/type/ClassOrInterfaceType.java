package org.walkmod.javalang.ast.type;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolData;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.comparators.ClassOrInterfaceTypeComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.IdentificableNode;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class ClassOrInterfaceType extends Type implements IdentificableNode, SymbolDefinition {

	private ClassOrInterfaceType scope;

	private String name;

	private List<Type> typeArgs;

	private SymbolData symbolData;

	private List<SymbolReference> usages;

	private List<SymbolReference> bodyReferences;

	private int scopeLevel = 0;

	public ClassOrInterfaceType() {}

	public ClassOrInterfaceType(String name) {
		this.name = name;
	}

	public ClassOrInterfaceType(ClassOrInterfaceType scope, String name) {
		setScope(scope);
		this.name = name;
	}

	public ClassOrInterfaceType(ClassOrInterfaceType scope, String name, List<Type> typeArgs, List<AnnotationExpr> annotations) {
		setScope(scope);
		this.name = name;
		setTypeArgs(typeArgs);
		setAnnotations(annotations);
	}

	public ClassOrInterfaceType(int beginLine, int beginColumn, int endLine, int endColumn, ClassOrInterfaceType scope, String name, List<Type> typeArgs) {
		super(beginLine, beginColumn, endLine, endColumn);
		setScope(scope);
		this.name = name;
		setTypeArgs(typeArgs);
	}

	public ClassOrInterfaceType(int beginLine, int beginColumn, int endLine, int endColumn, ClassOrInterfaceType scope, String name, List<Type> typeArgs, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn, annotations);
		setScope(scope);
		this.name = name;
		setTypeArgs(typeArgs);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;

		if (child != null) {
			if (scope == child) {
				scope = null;
				result = true;
			}
			if (!result) {
				if (typeArgs != null) {
					if (child instanceof Type) {
						List<Type> typeArgsAux = new LinkedList<Type>(typeArgs);
						result = typeArgsAux.remove(child);
						typeArgs = typeArgsAux;
					}
				}
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (scope != null) {
			children.add(scope);
		}
		if (typeArgs != null) {
			children.addAll(typeArgs);
		}
		return children;
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

	public String getName() {
		return name;
	}

	public ClassOrInterfaceType getScope() {
		return scope;
	}

	public List<Type> getTypeArgs() {
		return typeArgs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScope(ClassOrInterfaceType scope) {
		if (this.scope != null) {
			updateReferences(this.scope);
		}
		this.scope = scope;
		setAsParentNodeOf(scope);
	}

	public void setTypeArgs(List<Type> typeArgs) {
		this.typeArgs = typeArgs;
		setAsParentNodeOf(typeArgs);
	}

	@Override
	public Comparator<?> getIdentityComparator() {
		return new ClassOrInterfaceTypeComparator();
	}

	@Override
	public SymbolData getSymbolData() {
		return symbolData;
	}

	@Override
	public void setSymbolData(SymbolData symbolData) {
		this.symbolData = symbolData;
	}

	public List<SymbolReference> getUsages() {
		return usages;
	}

	public List<SymbolReference> getBodyReferences() {
		return bodyReferences;
	}

	public void setUsages(List<SymbolReference> usages) {
		this.usages = usages;
	}

	public void setBodyReferences(List<SymbolReference> bodyReferences) {
		this.bodyReferences = bodyReferences;
	}

	@Override
	public boolean addBodyReference(SymbolReference bodyReference) {
		if (bodyReference != null) {
			SymbolDefinition definition = bodyReference.getSymbolDefinition();
			if (definition != null) {
				int scope = definition.getScopeLevel();
				if (scope <= scopeLevel) {
					if (bodyReferences == null) {
						bodyReferences = new LinkedList<SymbolReference>();
					}
					return bodyReferences.add(bodyReference);
				}
			}
		}
		return false;
	}

	@Override
	public boolean addUsage(SymbolReference usage) {
		if (usage != null) {
			usage.setSymbolDefinition(this);
			if (usages == null) {
				usages = new LinkedList<SymbolReference>();
			}
			return usages.add(usage);
		}
		return false;

	}

	@Override
	public int getScopeLevel() {
		return scopeLevel;
	}

	@Override
	public void setScopeLevel(int scopeLevel) {
		this.scopeLevel = scopeLevel;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = super.replaceChildNode(oldChild, newChild);
		if (oldChild == scope) {
			setScope((ClassOrInterfaceType) newChild);
			updated = true;
		}

		if (!updated && typeArgs != null) {
			List<Type> auxTypeArgs = new LinkedList<Type>(typeArgs);
			updated = replaceChildNodeInList(oldChild, newChild, auxTypeArgs);
			if (updated) {
				typeArgs = auxTypeArgs;
			}
		}
		return updated;
	}

	@Override
	public ClassOrInterfaceType clone() throws CloneNotSupportedException {
		return new ClassOrInterfaceType(clone(getScope()), getName(), clone(getTypeArgs()), clone(getAnnotations()));
	}

	@Override
	public String getSymbolName() {
		return name;
	}

}
