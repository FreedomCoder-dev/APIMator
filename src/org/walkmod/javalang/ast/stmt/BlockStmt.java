package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ASTManager;
import org.walkmod.javalang.ParseException;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAware;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.expr.BinaryExpr;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.PrimitiveType;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.body.VariableDeclarator;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;
import org.walkmod.javalang.ast.body.MethodDeclaration;
import org.walkmod.javalang.ast.expr.FieldAccessExpr;
import org.walkmod.javalang.ast.expr.ThisExpr;
import org.walkmod.javalang.ast.expr.AssignExpr;
import org.walkmod.javalang.ast.expr.NameExpr;

import java.util.ArrayList;
import org.walkmod.javalang.ast.body.VariableDeclaratorId;
import org.walkmod.javalang.ast.expr.IntegerLiteralExpr;

import java.util.Arrays;
import org.walkmod.javalang.ast.expr.UnaryExpr;
import org.walkmod.javalang.ast.expr.MethodCallExpr;

public final class BlockStmt extends Statement implements ScopeAware {

	private List<Statement> stmts;

	private Object par;

	public BlockStmt() {}

	public BlockStmt(List<Statement> stmts) {
		setStmts(stmts);
	}

	public BlockStmt(int beginLine, int beginColumn, int endLine, int endColumn, List<Statement> stmts) {
		super(beginLine, beginColumn, endLine, endColumn);
		setStmts(stmts);
	}

	public void setPar(Object par) {
		this.par = par;
	}

	public BlockStmt makeBlock() {
		BlockStmt blockStmt = new BlockStmt();
		List<Statement> types = getStmts();
		if (types == null) types = new LinkedList<Statement>();
		types.add(blockStmt);
		setStmts(types);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public ExpressionStmt makeExpression(Expression ex) {
		ExpressionStmt blockStmt = new ExpressionStmt(ex);
		List<Statement> types = getStmts();
		if (types == null) types = new LinkedList<Statement>();
		types.add(blockStmt);
		setStmts(types);
		return blockStmt;
	}

	public BlockStmt addSourceDirectly(String source) throws ParseException {
		addStmt((Statement) ASTManager.parse(Statement.class, source));
		return this;
	}

	public BlockStmt addStmt(Statement stmt) {
		List<Statement> types = getStmts();
		if (types == null) types = new LinkedList<Statement>();
		types.add(stmt);
		setStmts(types);
		return this;
	}

	public BlockStmt addSetFieldToExpr(String field, Expression ex) {
		FieldAccessExpr expr = new FieldAccessExpr(new ThisExpr(), field);
		AssignExpr ass = new AssignExpr(expr, ex, AssignExpr.Operator.assign);
		makeExpression(ass);
		return this;
	}

	public BlockStmt addSetVarFromField(String localVar, String fieldGlobal) {
		FieldAccessExpr expr = new FieldAccessExpr(new ThisExpr(), fieldGlobal);
		AssignExpr ass = new AssignExpr(new NameExpr(localVar), expr, AssignExpr.Operator.assign);
		makeExpression(ass);
		return this;
	}

	public BlockStmt addSetFieldFromVar(String fieldGlobal, String localVar) {
		FieldAccessExpr expr = new FieldAccessExpr(new ThisExpr(), fieldGlobal);
		AssignExpr ass = new AssignExpr(expr, new NameExpr(localVar), AssignExpr.Operator.assign);
		makeExpression(ass);
		return this;
	}

	public BlockStmt addSetVar(String field, Expression ex) {
		FieldAccessExpr expr = new FieldAccessExpr(null, field);
		AssignExpr ass = new AssignExpr(expr, ex, AssignExpr.Operator.assign);
		makeExpression(ass);
		return this;
	}

	public BlockStmt addReturn(String value) {
		ReturnStmt stmt = new ReturnStmt();
		stmt.setExpr(new NameExpr(value));
		addStmt(stmt);
		return this;
	}

	public BlockStmt addVar(String type, String... names) {
		VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();
		variableDeclarationExpr.setType(new ClassOrInterfaceType(type));
		ArrayList<VariableDeclarator> vars = new ArrayList<VariableDeclarator>();
		for (String name : names) {
			VariableDeclarator variableDeclarator = new VariableDeclarator();
			variableDeclarator.setId(new VariableDeclaratorId(name));
			vars.add(variableDeclarator);
		}
		variableDeclarationExpr.setVars(vars);
		makeExpression(variableDeclarationExpr);
		return this;
	}

	public BlockStmt makeFor(String name, int startInclusive, int stopExclusive) {
		ForStmt stmt = new ForStmt();
		BlockStmt blockStmt = new BlockStmt();
		stmt.setBody(blockStmt);
		stmt.setCompare(new BinaryExpr(new NameExpr(name), new IntegerLiteralExpr(stopExclusive), BinaryExpr.Operator.less));
		VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr(PrimitiveType.Int(), Arrays.asList(new VariableDeclarator(new VariableDeclaratorId(name), new IntegerLiteralExpr(startInclusive))));
		stmt.setInit(Arrays.asList(variableDeclarationExpr));
		stmt.setUpdate(Arrays.asList(new UnaryExpr(new NameExpr(name), UnaryExpr.Operator.posIncrement)));
		addStmt(stmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public IfStmt makeIf(String cond) throws ParseException {
		IfStmt stmt = new IfStmt();

		stmt.setCondition((Expression) ASTManager.parse(Expression.class, cond));
		addStmt(stmt);

		return stmt;
	}

	public BlockStmt makeFor(String name, Expression start, Expression stop) {
		ForStmt stmt = new ForStmt();
		BlockStmt blockStmt = new BlockStmt();
		stmt.setBody(blockStmt);
		stmt.setCompare(new BinaryExpr(new NameExpr(name), stop, BinaryExpr.Operator.less));
		VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr(PrimitiveType.Int(), Arrays.asList(new VariableDeclarator(new VariableDeclaratorId(name), start)));
		stmt.setInit(Arrays.asList(variableDeclarationExpr));
		stmt.setUpdate(Arrays.asList(new UnaryExpr(new NameExpr(name), UnaryExpr.Operator.posIncrement)));
		addStmt(stmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public BlockStmt makeForEach(VariableDeclarationExpr var, Expression iterable) {
		ForeachStmt stmt = new ForeachStmt();
		BlockStmt blockStmt = new BlockStmt();
		stmt.setBody(blockStmt);
		stmt.setIterable(iterable);
		stmt.setVariable(var);
		addStmt(stmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public BlockStmt makeForEachVar(String type, String name, String iterable) {
		ForeachStmt stmt = new ForeachStmt();
		BlockStmt blockStmt = new BlockStmt();
		stmt.setBody(blockStmt);
		stmt.setIterable(new NameExpr(iterable));
		stmt.setVariable(new VariableDeclarationExpr(new ClassOrInterfaceType(type), Arrays.asList(new VariableDeclarator(new VariableDeclaratorId(name)))));
		addStmt(stmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public BlockStmt makeForEachField(String type, String name, String iterable) {
		ForeachStmt stmt = new ForeachStmt();
		BlockStmt blockStmt = new BlockStmt();
		stmt.setBody(blockStmt);
		stmt.setIterable(new FieldAccessExpr(new ThisExpr(), iterable));
		stmt.setVariable(new VariableDeclarationExpr(new ClassOrInterfaceType(type), Arrays.asList(new VariableDeclarator(new VariableDeclaratorId(name)))));
		addStmt(stmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public BlockStmt addVoidMethodCall(String target, String method) {
		MethodCallExpr expr = new MethodCallExpr();
		expr.setScope(new NameExpr(target));
		expr.setName(method);
		makeExpression(expr);
		return this;
	}

	public BlockStmt addMethodCall(String target, String method, String... vars) {
		MethodCallExpr expr = new MethodCallExpr();
		if (!target.isEmpty()) expr.setScope(new NameExpr(target));
		expr.setName(method);
		ArrayList<Expression> varss = new ArrayList<Expression>();
		for (String name : vars) {
			varss.add(new NameExpr(name));
		}
		expr.setArgs(varss);
		makeExpression(expr);
		return this;
	}

	public MethodDeclaration doneMethodBody() {
		return (MethodDeclaration) par;
	}

	public MethodDeclaration endMethodBody() {
		return (MethodDeclaration) par;
	}

	public MethodDeclaration exitToMethod() {
		return (MethodDeclaration) par;
	}

	public IfStmt exitToIf() {
		return (IfStmt) par;
	}

	public BlockStmt exit() {
		return (BlockStmt) par;
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (stmts != null) {
				if (child instanceof Statement) {
					List<Statement> stmtsAux = new LinkedList<Statement>(stmts);
					result = stmtsAux.remove(child);
					stmts = stmtsAux;
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
		if (stmts != null) {
			children.addAll(stmts);
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

	public List<Statement> getStmts() {
		return stmts;
	}

	public void setStmts(List<Statement> stmts) {
		this.stmts = stmts;
		setAsParentNodeOf(stmts);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		if (stmts != null) {
			List<Statement> auxStmts = new LinkedList<Statement>(stmts);
			if (replaceChildNodeInList(oldChild, newChild, auxStmts)) {
				stmts = auxStmts;
				return true;
			}
		}
		return false;
	}

	@Override
	public BlockStmt clone() throws CloneNotSupportedException {
		return new BlockStmt(clone(stmts));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		Map<String, SymbolDefinition> result = ScopeAwareUtil.getVariableDefinitions(this);
		if (stmts != null) {
			for (Statement stmt : stmts) {
				if (stmt instanceof ExpressionStmt) {
					ExpressionStmt exprStmt = (ExpressionStmt) stmt;
					Expression expr = exprStmt.getExpression();
					if (expr instanceof VariableDeclarationExpr) {
						VariableDeclarationExpr vde = (VariableDeclarationExpr) expr;
						List<VariableDeclarator> vars = vde.getVars();
						if (vars != null) {
							for (VariableDeclarator vd : vars) {
								result.put(vd.getSymbolName(), vd);
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(BlockStmt.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(BlockStmt.this);
	}
}
