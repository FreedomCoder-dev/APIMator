package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.MethodSymbolData;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolData;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.ast.TypeParameter;

public class MethodReferenceExpr extends Expression implements SymbolReference {

	private Expression scope;

	private List<TypeParameter> typeParameters;

	private String identifier;

	private MethodSymbolData referencedMethodSymbolData;

	private SymbolData[] referencedArgsSymbolData;

	private SymbolDefinition symbolDefinition;

	public MethodReferenceExpr() {}

	public MethodReferenceExpr(Expression scope, List<TypeParameter> typeParameters, String identifier) {

		setScope(scope);
		setTypeParameters(typeParameters);
		this.identifier = identifier;
	}

	public MethodReferenceExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression scope, List<TypeParameter> typeParameters, String identifier) {

		super(beginLine, beginColumn, endLine, endColumn);
		setScope(scope);
		setTypeParameters(typeParameters);
		this.identifier = identifier;
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
				if (typeParameters != null) {
					if (child instanceof TypeParameter) {
						List<TypeParameter> typeParametersAux = new LinkedList<TypeParameter>(typeParameters);
						result = typeParametersAux.remove(child);
						typeParameters = typeParametersAux;
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
		List<Node> children = new LinkedList<Node>();
		if (scope != null) {
			children.add(scope);
		}
		if (typeParameters != null) {
			children.addAll(typeParameters);
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

	public Expression getScope() {
		return scope;
	}

	public void setScope(Expression scope) {
		if (this.scope != null) {
			updateReferences(this.scope);
		}
		this.scope = scope;
		setAsParentNodeOf(scope);
	}

	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
		setAsParentNodeOf(typeParameters);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public SymbolData getSymbolData() {
		return super.getSymbolData();
	}

	public MethodSymbolData getReferencedMethodSymbolData() {
		return referencedMethodSymbolData;
	}

	public void setReferencedMethodSymbolData(MethodSymbolData referencedMethodSymbolData) {
		this.referencedMethodSymbolData = referencedMethodSymbolData;
	}

	public SymbolData[] getReferencedArgsSymbolData() {
		return referencedArgsSymbolData;
	}

	public void setReferencedArgsSymbolData(SymbolData[] referencedArgsSymbolData) {
		this.referencedArgsSymbolData = referencedArgsSymbolData;
	}

	@Override
	public SymbolDefinition getSymbolDefinition() {
		return symbolDefinition;
	}

	@Override
	public void setSymbolDefinition(SymbolDefinition symbolDefinition) {
		this.symbolDefinition = symbolDefinition;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == scope) {
			setScope((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			List<TypeParameter> auxTypeParameters = new LinkedList<TypeParameter>(typeParameters);
			updated = replaceChildNodeInList(oldChild, newChild, auxTypeParameters);
			if (updated) {
				typeParameters = auxTypeParameters;
			}

		}
		return updated;
	}

	@Override
	public MethodReferenceExpr clone() throws CloneNotSupportedException {

		return new MethodReferenceExpr(clone(scope), clone(typeParameters), identifier);
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(MethodReferenceExpr.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(MethodReferenceExpr.this);
	}
}
