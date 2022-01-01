package org.walkmod.javalang.ast.body;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.MarkerAnnotationExpr;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.comparators.MethodDeclarationComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;
import org.walkmod.merger.Mergeable;
import org.walkmod.javalang.ast.MethodSymbolData;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolDataAware;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.ast.TypeParameter;

public final class MethodDeclaration extends BodyDeclaration implements Mergeable<MethodDeclaration>, SymbolDataAware<MethodSymbolData>, SymbolDefinition {

	private int modifiers;

	private List<TypeParameter> typeParameters;

	private Type type;

	private String name;

	private List<Parameter> parameters;

	private int arrayCount;

	private List<ClassOrInterfaceType> throws_;

	private BlockStmt body;

	private boolean isDefault = false;

	private MethodSymbolData symbolData;

	private List<SymbolReference> usages;

	private List<SymbolReference> bodyReferences;

	private int scopeLevel = 0;

	public MethodDeclaration() {}

	public MethodDeclaration(int modifiers, Type type, String name) {
		this.modifiers = modifiers;
		setType(type);
		this.name = name;
	}

	public MethodDeclaration(int modifiers, Type type, String name, List<Parameter> parameters) {
		this.modifiers = modifiers;
		setType(type);
		this.name = name;
		setParameters(parameters);
	}

	public MethodDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, Type type, String name, List<Parameter> parameters, int arrayCount, List<ClassOrInterfaceType> throws_, BlockStmt block) {
		super(annotations, javaDoc);
		this.modifiers = modifiers;
		setTypeParameters(typeParameters);
		setType(type);
		this.name = name;
		setParameters(parameters);
		this.arrayCount = arrayCount;
		setThrows(throws_);
		setBody(block);
	}

	public MethodDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, Type type, String name, List<Parameter> parameters, int arrayCount, List<ClassOrInterfaceType> throws_, BlockStmt block, boolean isDefault) {
		this(javaDoc, modifiers, annotations, typeParameters, type, name, parameters, arrayCount, throws_, block);
		this.isDefault = isDefault;
	}

	public MethodDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, Type type, String name, List<Parameter> parameters, int arrayCount, List<ClassOrInterfaceType> throws_, BlockStmt block) {
		super(beginLine, beginColumn, endLine, endColumn, annotations, javaDoc);
		this.modifiers = modifiers;
		setTypeParameters(typeParameters);
		setType(type);
		this.name = name;
		setParameters(parameters);
		this.arrayCount = arrayCount;
		setThrows(throws_);
		setBody(block);
	}

	public MethodDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, Type type, String name, List<Parameter> parameters, int arrayCount, List<ClassOrInterfaceType> throws_, BlockStmt block, boolean isDefault) {

		this(beginLine, beginColumn, endLine, endColumn, javaDoc, modifiers, annotations, typeParameters, type, name, parameters, arrayCount, throws_, block);
		this.isDefault = isDefault;
	}

	public MethodDeclaration override() {
		List<AnnotationExpr> annotations = getAnnotations();
		if (annotations == null) annotations = new LinkedList<AnnotationExpr>();
		annotations.add(new MarkerAnnotationExpr(new NameExpr("Override")));
		setAnnotations(annotations);
		return this;
	}

	public MethodDeclaration addThrows(String name) {
		List<ClassOrInterfaceType> params = getThrows();
		if (params == null) params = new LinkedList<ClassOrInterfaceType>();
		params.add(new ClassOrInterfaceType(name));
		setThrows(params);
		return this;
	}
	public MethodDeclaration addParameter(Type type, String name) {
		Parameter p = new Parameter(type, new VariableDeclaratorId(name));

		List<Parameter> params = getParameters();
		if (params == null) params = new LinkedList<Parameter>();
		params.add(p);
		setParameters(params);
		return this;
	}

	public MethodDeclaration addParameter(Type type, String name, int arrayC) {
		VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId(name);
		variableDeclaratorId.setArrayCount(arrayC);
		Parameter p = new Parameter(type, variableDeclaratorId);

		List<Parameter> params = getParameters();
		if (params == null) params = new LinkedList<Parameter>();
		params.add(p);
		setParameters(params);
		return this;
	}

	public BlockStmt makeBody() {
		body = new BlockStmt();
		body.setPar(this);
		return body;
	}

	public MethodDeclaration addModifier(int modifier) {
		modifiers |= modifier;
		return this;
	}

	public MethodDeclaration removeModifier(int modifier) {
		modifiers &= ~modifier;
		return this;
	}

	public MethodDeclaration setPublic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PUBLIC);
			setPrivate(false);
		}

		else {
			removeModifier(ModifierSet.PUBLIC);
		}

		return this;
	}

	public MethodDeclaration setPrivate(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PRIVATE);
			setPublic(false);
		}

		else {
			removeModifier(ModifierSet.PRIVATE);
		}

		return this;
	}

	public MethodDeclaration setProtected(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PROTECTED);
		}

		else {
			removeModifier(ModifierSet.PROTECTED);
		}
		return this;
	}

	public MethodDeclaration setNative(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.NATIVE);
		}

		else {
			removeModifier(ModifierSet.NATIVE);
		}
		return this;
	}

	public MethodDeclaration setAbstract(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.ABSTRACT);
		}

		else {
			removeModifier(ModifierSet.ABSTRACT);
		}
		return this;
	}

	public MethodDeclaration setFinal(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.FINAL);
		}

		else {
			removeModifier(ModifierSet.FINAL);
		}
		return this;
	}

	public MethodDeclaration setStatic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STATIC);
		}

		else {
			removeModifier(ModifierSet.STATIC);
		}
		return this;
	}

	public MethodDeclaration setSynchronized(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.SYNCHRONIZED);
		}

		else {
			removeModifier(ModifierSet.SYNCHRONIZED);
		}
		return this;
	}

	public MethodDeclaration setStrictfp(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STRICTFP);
		}

		else {
			removeModifier(ModifierSet.STRICTFP);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration done() {
		return (ClassOrInterfaceDeclaration) getParentNode();
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
				if (child instanceof TypeParameter) {
					if (typeParameters != null) {
						List<TypeParameter> typeParametersAux = new LinkedList<TypeParameter>(typeParameters);
						result = typeParametersAux.remove(child);
						typeParameters = typeParametersAux;
					}
				}
				else if (child instanceof Parameter) {
					if (parameters != null) {
						List<Parameter> parametersAux = new LinkedList<Parameter>(parameters);
						result = parametersAux.remove(child);
						parameters = parametersAux;
					}
				}
				else if (child instanceof ClassOrInterfaceType) {
					if (throws_ != null) {
						List<ClassOrInterfaceType> throwsAux = new LinkedList<ClassOrInterfaceType>();
						result = throwsAux.remove(child);
						throws_ = throwsAux;
					}
				}
				else if (child instanceof BlockStmt) {
					if (body != null && body == child) {
						body = null;
						result = true;
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
		if (typeParameters != null) {
			children.addAll(typeParameters);
		}
		if (type != null) {
			children.add(type);
		}
		if (parameters != null) {
			children.addAll(parameters);
		}
		if (throws_ != null) {
			children.addAll(throws_);
		}
		if (body != null) {
			children.add(body);
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

	public int getArrayCount() {
		return arrayCount;
	}

	public BlockStmt getBody() {
		return body;
	}

	public int getModifiers() {
		return modifiers;
	}

	public String getName() {
		return name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public List<ClassOrInterfaceType> getThrows() {
		return throws_;
	}

	public Type getType() {
		return type;
	}

	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}

	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}

	public void setBody(BlockStmt body) {
		if (this.body != null) {
			updateReferences(this.body);
		}
		this.body = body;
		setAsParentNodeOf(body);
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public MethodDeclaration setName(String name) {
		this.name = name;
		return this;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
		setAsParentNodeOf(parameters);
	}

	public void setThrows(List<ClassOrInterfaceType> throws_) {
		this.throws_ = throws_;
		setAsParentNodeOf(throws_);
	}

	public MethodDeclaration setType(Type type) {
		if (this.type != null) {
			updateReferences(this.type);
		}
		this.type = type;
		setAsParentNodeOf(type);
		return this;
	}

	public MethodDeclaration setType(String type) {
		setType(new ClassOrInterfaceType(type));
		return this;
	}

	public void setTypeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
		setAsParentNodeOf(typeParameters);
	}

	@Override
	public Comparator<?> getIdentityComparator() {

		return new MethodDeclarationComparator();
	}

	@Override
	public void merge(MethodDeclaration remote, MergeEngine configuration) {
		super.merge(remote, configuration);

		setBody((BlockStmt) configuration.apply(getBody(), remote.getBody(), BlockStmt.class));

		List<Parameter> resultParams = new LinkedList<Parameter>();
		configuration.apply(getParameters(), remote.getParameters(), resultParams, Parameter.class);
		if (!resultParams.isEmpty()) {
			setParameters(resultParams);
		}

		else {
			setParameters(null);
		}

		List<TypeParameter> resultTypeParams = new LinkedList<TypeParameter>();
		configuration.apply(getTypeParameters(), remote.getTypeParameters(), resultTypeParams, TypeParameter.class);
		if (!resultTypeParams.isEmpty()) {
			setTypeParameters(resultTypeParams);
		}

		else {
			setTypeParameters(null);
		}

		List<ClassOrInterfaceType> resultThrows = new LinkedList<ClassOrInterfaceType>();
		configuration.apply(getThrows(), remote.getThrows(), resultThrows, ClassOrInterfaceType.class);
		if (!resultThrows.isEmpty()) {
			setThrows(resultThrows);
		}

		else {
			setThrows(null);
		}

	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public MethodSymbolData getSymbolData() {
		return symbolData;
	}

	@Override
	public void setSymbolData(MethodSymbolData symbolData) {
		this.symbolData = symbolData;
	}

	@Override
	public List<SymbolReference> getUsages() {
		return usages;
	}

	@Override
	public void setUsages(List<SymbolReference> usages) {
		this.usages = usages;
	}

	@Override
	public List<SymbolReference> getBodyReferences() {
		return bodyReferences;
	}

	@Override
	public void setBodyReferences(List<SymbolReference> bodyReferences) {
		this.bodyReferences = bodyReferences;
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
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean update = super.replaceChildNode(oldChild, newChild);
		if (!update) {
			if (type == oldChild) {
				setType((Type) newChild);
				update = true;
			}

			if (!update) {
				if (body == oldChild) {
					setBody((BlockStmt) newChild);
					update = true;
				}
			}
			if (!update && parameters != null) {
				List<Parameter> auxParams = new LinkedList<Parameter>(parameters);
				update = replaceChildNodeInList(oldChild, newChild, auxParams);
				if (update) {
					parameters = auxParams;
				}

			}
			if (!update && throws_ != null) {
				List<ClassOrInterfaceType> auxThrows = new LinkedList<ClassOrInterfaceType>(throws_);
				update = replaceChildNodeInList(oldChild, newChild, auxThrows);
				if (update) {
					throws_ = auxThrows;
				}
			}
			if (!update && typeParameters != null) {
				List<TypeParameter> auxTParameter = new LinkedList<TypeParameter>(typeParameters);
				update = replaceChildNodeInList(oldChild, newChild, auxTParameter);
				if (update) {
					typeParameters = auxTParameter;
				}
			}

		}
		return update;
	}

	@Override
	public MethodDeclaration clone() throws CloneNotSupportedException {
		return new MethodDeclaration(clone(getJavaDoc()), getModifiers(), clone(getAnnotations()), clone(getTypeParameters()), clone(getType()), getName(), clone(getParameters()), getArrayCount(), clone(getThrows()), clone(getBody()));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		Map<String, SymbolDefinition> result = super.getVariableDefinitions();
		if (parameters != null) {
			for (Parameter param : parameters) {
				result.put(param.getSymbolName(), param);
			}
		}
		return result;
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		Map<String, SymbolDefinition> result = super.getVariableDefinitions();
		if (typeParameters != null) {
			for (TypeParameter tp : typeParameters) {
				result.put(tp.getSymbolName(), tp);
			}
		}
		return result;
	}

	@Override
	public String getSymbolName() {
		return name;
	}

}
