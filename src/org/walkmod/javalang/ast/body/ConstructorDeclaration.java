package org.walkmod.javalang.ast.body;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.comparators.ConstructorDeclarationComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;
import org.walkmod.merger.Mergeable;
import org.walkmod.javalang.ast.ConstructorSymbolData;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolDataAware;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.ast.TypeParameter;

public final class ConstructorDeclaration extends BodyDeclaration implements Mergeable<ConstructorDeclaration>, SymbolDataAware<ConstructorSymbolData>, SymbolDefinition {

	private int modifiers;

	private List<TypeParameter> typeParameters;

	private String name;

	private List<Parameter> parameters;

	private List<ClassOrInterfaceType> throws_;

	private BlockStmt block;

	private ConstructorSymbolData symbolData;

	private List<SymbolReference> usages;

	private List<SymbolReference> bodyReferences;

	private int scopeLevel = 0;

	public ConstructorDeclaration() {}

	public ConstructorDeclaration(int modifiers, String name) {
		this.modifiers = modifiers;
		this.name = name;
	}

	public ConstructorDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, String name, List<Parameter> parameters, List<ClassOrInterfaceType> throws_, BlockStmt block) {
		super(annotations, javaDoc);
		this.modifiers = modifiers;
		this.typeParameters = typeParameters;
		this.name = name;
		setParameters(parameters);
		this.throws_ = throws_;
		setBlock(block);
	}

	public ConstructorDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, List<TypeParameter> typeParameters, String name, List<Parameter> parameters, List<ClassOrInterfaceType> throws_, BlockStmt block) {
		super(beginLine, beginColumn, endLine, endColumn, annotations, javaDoc);
		this.modifiers = modifiers;
		this.typeParameters = typeParameters;
		this.name = name;
		setParameters(parameters);
		this.throws_ = throws_;
		setBlock(block);
	}

	public ConstructorDeclaration addParameter(String mainclass, String p1) {
		addParameter(new ClassOrInterfaceType(mainclass), p1);
		return this;
	}

	public ConstructorDeclaration addParameter(Type type, String name) {
		Parameter p = new Parameter(type, new VariableDeclaratorId(name));

		List<Parameter> params = getParameters();
		if (params == null) params = new LinkedList<Parameter>();
		params.add(p);
		setParameters(params);
		return this;
	}

	public ConstructorDeclaration addThrows(String name) {
		List<ClassOrInterfaceType> params = getThrows();
		if (params == null) params = new LinkedList<ClassOrInterfaceType>();
		params.add(new ClassOrInterfaceType(name));
		setThrows(params);
		return this;
	}

	public ConstructorDeclaration addParameter(Type type, String name, int arrayC) {
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
		block = new BlockStmt();
		block.setPar(this);
		return block;
	}

	public ConstructorDeclaration addModifier(int modifier) {
		modifiers |= modifier;
		return this;
	}

	public ConstructorDeclaration removeModifier(int modifier) {
		modifiers &= ~modifier;
		return this;
	}

	public ConstructorDeclaration setPublic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PUBLIC);
			setPrivate(false);
		} else {
			removeModifier(ModifierSet.PUBLIC);
		}

		return this;
	}

	public ConstructorDeclaration setPrivate(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PRIVATE);
			setPublic(false);
		} else {
			removeModifier(ModifierSet.PRIVATE);
		}

		return this;
	}

	public ConstructorDeclaration setProtected(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.PROTECTED);
		} else {
			removeModifier(ModifierSet.PROTECTED);
		}
		return this;
	}

	public ConstructorDeclaration setNative(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.NATIVE);
		} else {
			removeModifier(ModifierSet.NATIVE);
		}
		return this;
	}

	public ConstructorDeclaration setAbstract(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.ABSTRACT);
		} else {
			removeModifier(ModifierSet.ABSTRACT);
		}
		return this;
	}

	public ConstructorDeclaration setFinal(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.FINAL);
		} else {
			removeModifier(ModifierSet.FINAL);
		}
		return this;
	}

	public ConstructorDeclaration setStatic(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STATIC);
		} else {
			removeModifier(ModifierSet.STATIC);
		}
		return this;
	}

	public ConstructorDeclaration setSynchronized(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.SYNCHRONIZED);
		} else {
			removeModifier(ModifierSet.SYNCHRONIZED);
		}
		return this;
	}

	public ConstructorDeclaration setStrictfp(boolean enable) {
		if (enable) {
			addModifier(ModifierSet.STRICTFP);
		} else {
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
				if (child instanceof TypeParameter) {
					if (typeParameters != null) {
						List<TypeParameter> typeParametersAux = new LinkedList<TypeParameter>(typeParameters);
						result = typeParametersAux.remove(child);
						typeParameters = typeParametersAux;
					}
				} else if (child instanceof Parameter) {
					if (parameters != null) {
						List<Parameter> paramsAux = new LinkedList<Parameter>(parameters);
						result = paramsAux.remove(child);
						parameters = paramsAux;
					}
				} else if (child instanceof ClassOrInterfaceType) {
					if (throws_ != null) {
						List<ClassOrInterfaceType> throwsAux = new LinkedList<ClassOrInterfaceType>(throws_);
						result = throwsAux.remove(child);
						throws_ = throwsAux;
					}
				} else if (child == block && block != null) {
					block = null;
					result = true;
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
		if (parameters != null) {
			children.addAll(parameters);
		}
		if (throws_ != null) {
			children.addAll(throws_);
		}
		if (block != null) {
			children.add(block);
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

	public BlockStmt getBlock() {
		return block;
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

	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}

	public void setBlock(BlockStmt block) {
		if (this.block != null) {
			updateReferences(this.block);
		}
		this.block = block;
		setAsParentNodeOf(block);
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
		setAsParentNodeOf(parameters);
	}

	public void setThrows(List<ClassOrInterfaceType> throws_) {
		this.throws_ = throws_;
	}

	public void setTypeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
	}

	@Override
	public Comparator<?> getIdentityComparator() {
		return new ConstructorDeclarationComparator();
	}

	@Override
	public void merge(ConstructorDeclaration remote, MergeEngine configuration) {

		super.merge(remote, configuration);
		setBlock((BlockStmt) configuration.apply(getBlock(), remote.getBlock(), BlockStmt.class));

		List<Parameter> resultParams = new LinkedList<Parameter>();
		configuration.apply(getParameters(), remote.getParameters(), resultParams, Parameter.class);

		if (!resultParams.isEmpty()) {
			setParameters(resultParams);
		} else {
			setParameters(null);
		}

		List<TypeParameter> resultTypeParams = new LinkedList<TypeParameter>();
		configuration.apply(getTypeParameters(), remote.getTypeParameters(), resultTypeParams, TypeParameter.class);

		if (!resultTypeParams.isEmpty()) {
			setTypeParameters(resultTypeParams);
		} else {
			setTypeParameters(null);
		}

		List<ClassOrInterfaceType> resultThrows = new LinkedList<ClassOrInterfaceType>();
		configuration.apply(getThrows(), remote.getThrows(), resultThrows, ClassOrInterfaceType.class);

		if (!resultThrows.isEmpty()) {
			setThrows(resultThrows);
		} else {
			setThrows(null);
		}

	}

	@Override
	public ConstructorSymbolData getSymbolData() {
		return symbolData;
	}

	@Override
	public void setSymbolData(ConstructorSymbolData symbolData) {
		this.symbolData = symbolData;
	}

	@Override
	public List<SymbolReference> getUsages() {
		return usages;
	}

	@Override
	public List<SymbolReference> getBodyReferences() {
		return bodyReferences;
	}

	@Override
	public void setUsages(List<SymbolReference> usages) {
		this.usages = usages;
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
	public TypeDeclaration getParentNode() {
		return (TypeDeclaration) super.getParentNode();
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean update = super.replaceChildNode(oldChild, newChild);
		if (!update && parameters != null) {
			List<Parameter> auxParameters = new LinkedList<Parameter>(parameters);
			update = replaceChildNodeInList(oldChild, newChild, auxParameters);
			if (update) {
				parameters = auxParameters;
			}
		}
		if (!update && typeParameters != null) {
			List<TypeParameter> auxTypeParams = new LinkedList<TypeParameter>(typeParameters);

			update = replaceChildNodeInList(oldChild, newChild, auxTypeParams);
			if (update) {
				typeParameters = auxTypeParams;
			}

		}
		if (!update && throws_ != null) {
			List<ClassOrInterfaceType> auxThrows = new LinkedList<ClassOrInterfaceType>(throws_);
			update = replaceChildNodeInList(oldChild, newChild, auxThrows);
			if (update) {
				throws_ = auxThrows;
			}
		}
		return update;

	}

	@Override
	public ConstructorDeclaration clone() throws CloneNotSupportedException {
		return new ConstructorDeclaration(clone(getJavaDoc()), getModifiers(), clone(getAnnotations()), clone(getTypeParameters()), getName(), clone(getParameters()), clone(getThrows()), clone(getBlock()));
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
