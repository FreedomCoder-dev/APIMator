package org.walkmod.javalang.ast.body;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.FieldSymbolData;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.expr.NullLiteralExpr;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.comparators.FieldDeclarationComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;
import org.walkmod.merger.Mergeable;

public final class FieldDeclaration extends BodyDeclaration implements Mergeable<FieldDeclaration>, SymbolDefinition {

	private int modifiers;

	private Type type;

	private List<VariableDeclarator> variables;

	private List<FieldSymbolData> symbolData;

	private int scopeLevel = 0;

	public FieldDeclaration() {}

	public FieldDeclaration(int modifiers, Type type, VariableDeclarator variable) {
		this.modifiers = modifiers;
		setType(type);
		this.variables = new ArrayList<VariableDeclarator>();
		setAsParentNodeOf(variable);
		this.variables.add(variable);
	}

	public FieldDeclaration(int modifiers, Type type, List<VariableDeclarator> variables) {
		this.modifiers = modifiers;
		setType(type);
		setVariables(variables);
	}

	public FieldDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, Type type, List<VariableDeclarator> variables) {
		super(annotations, javaDoc);
		this.modifiers = modifiers;
		setType(type);
		setVariables(variables);
	}

	public FieldDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, Type type, List<VariableDeclarator> variables) {
		super(beginLine, beginColumn, endLine, endColumn, annotations, javaDoc);
		this.modifiers = modifiers;
		setType(type);
		setVariables(variables);
	}

	public FieldDeclaration addVariable(String name) {
		VariableDeclarator decl = new VariableDeclarator();
		decl.setId(new VariableDeclaratorId(name));
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public FieldDeclaration addArrayVariable(String name, int arrayCount) {
		VariableDeclarator decl = new VariableDeclarator();
		VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId(name);
		variableDeclaratorId.setArrayCount(arrayCount);
		decl.setId(variableDeclaratorId);
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public FieldDeclaration addNullInitVariable(String name) {
		VariableDeclarator decl = new VariableDeclarator();
		decl.setId(new VariableDeclaratorId(name));
		decl.setInit(new NullLiteralExpr());
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public FieldDeclaration addNullInitArrayVariable(String name, int arrayCount) {
		VariableDeclarator decl = new VariableDeclarator();
		VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId(name);
		variableDeclaratorId.setArrayCount(arrayCount);
		decl.setId(variableDeclaratorId);
		decl.setInit(new NullLiteralExpr());
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public FieldDeclaration addInitVariable(String name, Expression init) {
		VariableDeclarator decl = new VariableDeclarator();
		decl.setId(new VariableDeclaratorId(name));
		decl.setInit(init);
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public FieldDeclaration addInitArrayVariable(String name, int arrayCount, Expression init) {
		VariableDeclarator decl = new VariableDeclarator();
		VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId(name);
		variableDeclaratorId.setArrayCount(arrayCount);
		decl.setId(variableDeclaratorId);
		decl.setInit(init);
		List<VariableDeclarator> types = getVariables();
		if (types == null) types = new ArrayList<VariableDeclarator>();
		types.add(decl);
		setVariables(types);
		return this;
	}

	public ClassOrInterfaceDeclaration done() {
		return (ClassOrInterfaceDeclaration) getParentNode();
	}

	public FieldDeclaration addModifier(int modifier) {
		modifiers |= modifier;
		return this;
	}

	public FieldDeclaration removeModifier(int modifier) {
		modifiers &= ~modifier;
		return this;
	}

	public FieldDeclaration setPublic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PUBLIC);
			setPrivate(false);
		} else {
			removeModifier(ModifierSet.PUBLIC);
		}
		return this;
	}

	public FieldDeclaration setPrivate(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PRIVATE);
			setPublic(false);
		} else {
			removeModifier(ModifierSet.PRIVATE);
		}
		return this;
	}

	public FieldDeclaration setProtected(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PROTECTED);
		} else {
			removeModifier(ModifierSet.PROTECTED);
		}
		return this;
	}

	public FieldDeclaration setNative(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.NATIVE);
		} else {
			removeModifier(ModifierSet.NATIVE);
		}
		return this;
	}

	public FieldDeclaration setAbstract(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.ABSTRACT);
		} else {
			removeModifier(ModifierSet.ABSTRACT);
		}
		return this;
	}

	public FieldDeclaration setFinal(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.FINAL);
		} else {
			removeModifier(ModifierSet.FINAL);
		}
		return this;
	}

	public FieldDeclaration setStatic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STATIC);
		} else {
			removeModifier(ModifierSet.STATIC);
		}
		return this;
	}

	public FieldDeclaration setSynchronized(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.SYNCHRONIZED);
		} else {
			removeModifier(ModifierSet.SYNCHRONIZED);
		}
		return this;
	}

	public FieldDeclaration setStrictfp(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STRICTFP);
		} else {
			removeModifier(ModifierSet.STRICTFP);
		}
		return this;
	}

	public FieldDeclaration setVolatile(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.VOLATILE);
		} else {
			removeModifier(ModifierSet.VOLATILE);
		}
		return this;
	}

	public FieldDeclaration setTransient(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.TRANSIENT);
		} else {
			removeModifier(ModifierSet.TRANSIENT);
		}
		return this;
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			result = super.removeChild(child);
			if (!result) {
				if (child == type && type != null) {
					type = null;
					result = true;
				}
				if (variables != null) {
					List<VariableDeclarator> auxVars = new LinkedList<VariableDeclarator>(variables);
					auxVars.remove(child);
					variables = auxVars;
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
		if (type != null) {
			children.add(type);
		}
		if (variables != null) {
			children.addAll(variables);
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

	public int getModifiers() {
		return modifiers;
	}

	public Type getType() {
		return type;
	}

	public List<VariableDeclarator> getVariables() {
		return variables;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public void setType(Type type) {
		if (this.type != null) {
			updateReferences(this.type);
		}
		this.type = type;
		setAsParentNodeOf(type);
	}

	public void setVariables(List<VariableDeclarator> variables) {
		this.variables = variables;
		setAsParentNodeOf(variables);
	}

	@Override
	public Comparator<?> getIdentityComparator() {
		return new FieldDeclarationComparator();
	}

	@Override
	public void merge(FieldDeclaration remote, MergeEngine configuration) {
		super.merge(remote, configuration);
		setType((Type) configuration.apply(getType(), remote.getType(), Type.class));
		List<VariableDeclarator> resultList = new LinkedList<VariableDeclarator>();
		configuration.apply(getVariables(), remote.getVariables(), resultList, VariableDeclarator.class);
		setVariables(resultList);
	}

	public List<FieldSymbolData> getFieldsSymbolData() {
		return symbolData;
	}

	public void setFieldsSymbolData(List<FieldSymbolData> symbolData) {
		this.symbolData = symbolData;
	}

	@Override
	public List<SymbolReference> getUsages() {
		List<SymbolReference> result = null;
		if (variables != null) {
			result = new LinkedList<SymbolReference>();
			for (VariableDeclarator vd : variables) {
				List<SymbolReference> usages = vd.getUsages();
				if (usages != null) {
					result.addAll(usages);
				}
			}
			if (result.isEmpty()) {
				result = null;
			}
		}

		return result;
	}

	@Override
	public void setUsages(List<SymbolReference> usages) {}

	@Override
	public List<SymbolReference> getBodyReferences() {
		List<SymbolReference> result = null;
		if (variables != null) {
			result = new LinkedList<SymbolReference>();
			for (VariableDeclarator vd : variables) {
				List<SymbolReference> bodyReferences = vd.getBodyReferences();
				if (bodyReferences != null) {
					result.addAll(bodyReferences);
				}
			}
			if (result.isEmpty()) {
				result = null;
			}
		}
		return result;
	}

	@Override
	public void setBodyReferences(List<SymbolReference> bodyReferences) {}

	@Override
	public int getScopeLevel() {
		return scopeLevel;
	}

	@Override
	public void setScopeLevel(int scopeLevel) {
		this.scopeLevel = scopeLevel;
	}

	@Override
	public boolean addBodyReference(SymbolReference bodyReference) {
		return false;
	}

	@Override
	public boolean addUsage(SymbolReference usage) {
		return false;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean update = super.replaceChildNode(oldChild, newChild);

		if (!update) {
			if (oldChild == type) {
				setType((Type) newChild);
				update = true;
			} else {
				if (variables != null) {
					List<VariableDeclarator> auxVars = new LinkedList<VariableDeclarator>(variables);
					update = replaceChildNodeInList(oldChild, newChild, auxVars);
					if (update) {
						variables = auxVars;
					}
				}
			}
		}
		return update;
	}

	@Override
	public FieldDeclaration clone() throws CloneNotSupportedException {
		return new FieldDeclaration(clone(getJavaDoc()), getModifiers(), clone(getAnnotations()), clone(getType()), clone(getVariables()));
	}

	@Override
	public String getSymbolName() {
		if (variables != null && variables.size() == 1) {
			return variables.get(0).getId().getName();
		}
		return null;
	}

}
