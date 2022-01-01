package org.walkmod.javalang.visitors;

import org.walkmod.javalang.ast.BlockComment;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.ImportDeclaration;
import org.walkmod.javalang.ast.LineComment;
import org.walkmod.javalang.ast.PackageDeclaration;
import org.walkmod.javalang.ast.TypeParameter;
import org.walkmod.javalang.ast.body.AnnotationDeclaration;
import org.walkmod.javalang.ast.body.AnnotationMemberDeclaration;
import org.walkmod.javalang.ast.body.ClassOrInterfaceDeclaration;
import org.walkmod.javalang.ast.body.ConstructorDeclaration;
import org.walkmod.javalang.ast.body.EmptyMemberDeclaration;
import org.walkmod.javalang.ast.body.EmptyTypeDeclaration;
import org.walkmod.javalang.ast.body.EnumConstantDeclaration;
import org.walkmod.javalang.ast.body.EnumDeclaration;
import org.walkmod.javalang.ast.body.FieldDeclaration;
import org.walkmod.javalang.ast.body.InitializerDeclaration;
import org.walkmod.javalang.ast.body.JavadocComment;
import org.walkmod.javalang.ast.body.MethodDeclaration;
import org.walkmod.javalang.ast.body.MultiTypeParameter;
import org.walkmod.javalang.ast.body.Parameter;
import org.walkmod.javalang.ast.body.VariableDeclarator;
import org.walkmod.javalang.ast.body.VariableDeclaratorId;
import org.walkmod.javalang.ast.expr.ArrayAccessExpr;
import org.walkmod.javalang.ast.expr.ArrayCreationExpr;
import org.walkmod.javalang.ast.expr.ArrayInitializerExpr;
import org.walkmod.javalang.ast.expr.AssignExpr;
import org.walkmod.javalang.ast.expr.BinaryExpr;
import org.walkmod.javalang.ast.expr.BooleanLiteralExpr;
import org.walkmod.javalang.ast.expr.CastExpr;
import org.walkmod.javalang.ast.expr.CharLiteralExpr;
import org.walkmod.javalang.ast.expr.ClassExpr;
import org.walkmod.javalang.ast.expr.ConditionalExpr;
import org.walkmod.javalang.ast.expr.DoubleLiteralExpr;
import org.walkmod.javalang.ast.expr.EnclosedExpr;
import org.walkmod.javalang.ast.expr.FieldAccessExpr;
import org.walkmod.javalang.ast.expr.InstanceOfExpr;
import org.walkmod.javalang.ast.expr.IntegerLiteralExpr;
import org.walkmod.javalang.ast.expr.IntegerLiteralMinValueExpr;
import org.walkmod.javalang.ast.expr.LambdaExpr;
import org.walkmod.javalang.ast.expr.LongLiteralExpr;
import org.walkmod.javalang.ast.expr.LongLiteralMinValueExpr;
import org.walkmod.javalang.ast.expr.MarkerAnnotationExpr;
import org.walkmod.javalang.ast.expr.MemberValuePair;
import org.walkmod.javalang.ast.expr.MethodCallExpr;
import org.walkmod.javalang.ast.expr.MethodReferenceExpr;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.expr.NormalAnnotationExpr;
import org.walkmod.javalang.ast.expr.NullLiteralExpr;
import org.walkmod.javalang.ast.expr.ObjectCreationExpr;
import org.walkmod.javalang.ast.expr.QualifiedNameExpr;
import org.walkmod.javalang.ast.expr.SingleMemberAnnotationExpr;
import org.walkmod.javalang.ast.expr.StringLiteralExpr;
import org.walkmod.javalang.ast.expr.SuperExpr;
import org.walkmod.javalang.ast.expr.ThisExpr;
import org.walkmod.javalang.ast.expr.TypeExpr;
import org.walkmod.javalang.ast.expr.UnaryExpr;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;
import org.walkmod.javalang.ast.stmt.AssertStmt;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.stmt.BreakStmt;
import org.walkmod.javalang.ast.stmt.CatchClause;
import org.walkmod.javalang.ast.stmt.ContinueStmt;
import org.walkmod.javalang.ast.stmt.DoStmt;
import org.walkmod.javalang.ast.stmt.EmptyStmt;
import org.walkmod.javalang.ast.stmt.ExplicitConstructorInvocationStmt;
import org.walkmod.javalang.ast.stmt.ExpressionStmt;
import org.walkmod.javalang.ast.stmt.ForStmt;
import org.walkmod.javalang.ast.stmt.ForeachStmt;
import org.walkmod.javalang.ast.stmt.IfStmt;
import org.walkmod.javalang.ast.stmt.LabeledStmt;
import org.walkmod.javalang.ast.stmt.ReturnStmt;
import org.walkmod.javalang.ast.stmt.SwitchEntryStmt;
import org.walkmod.javalang.ast.stmt.SwitchStmt;
import org.walkmod.javalang.ast.stmt.SynchronizedStmt;
import org.walkmod.javalang.ast.stmt.ThrowStmt;
import org.walkmod.javalang.ast.stmt.TryStmt;
import org.walkmod.javalang.ast.stmt.TypeDeclarationStmt;
import org.walkmod.javalang.ast.stmt.WhileStmt;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.IntersectionType;
import org.walkmod.javalang.ast.type.PrimitiveType;
import org.walkmod.javalang.ast.type.ReferenceType;
import org.walkmod.javalang.ast.type.VoidType;
import org.walkmod.javalang.ast.type.WildcardType;

public interface VoidVisitor<A> {

	void visit(CompilationUnit n, A arg);

	void visit(PackageDeclaration n, A arg);

	void visit(ImportDeclaration n, A arg);

	void visit(TypeParameter n, A arg);

	void visit(LineComment n, A arg);

	void visit(BlockComment n, A arg);

	void visit(ClassOrInterfaceDeclaration n, A arg);

	void visit(EnumDeclaration n, A arg);

	void visit(EmptyTypeDeclaration n, A arg);

	void visit(EnumConstantDeclaration n, A arg);

	void visit(AnnotationDeclaration n, A arg);

	void visit(AnnotationMemberDeclaration n, A arg);

	void visit(FieldDeclaration n, A arg);

	void visit(VariableDeclarator n, A arg);

	void visit(VariableDeclaratorId n, A arg);

	void visit(ConstructorDeclaration n, A arg);

	void visit(MethodDeclaration n, A arg);

	void visit(Parameter n, A arg);

	void visit(EmptyMemberDeclaration n, A arg);

	void visit(InitializerDeclaration n, A arg);

	void visit(JavadocComment n, A arg);

	void visit(ClassOrInterfaceType n, A arg);

	void visit(PrimitiveType n, A arg);

	void visit(ReferenceType n, A arg);

	void visit(VoidType n, A arg);

	void visit(WildcardType n, A arg);

	void visit(ArrayAccessExpr n, A arg);

	void visit(ArrayCreationExpr n, A arg);

	void visit(ArrayInitializerExpr n, A arg);

	void visit(AssignExpr n, A arg);

	void visit(BinaryExpr n, A arg);

	void visit(CastExpr n, A arg);

	void visit(ClassExpr n, A arg);

	void visit(ConditionalExpr n, A arg);

	void visit(EnclosedExpr n, A arg);

	void visit(FieldAccessExpr n, A arg);

	void visit(InstanceOfExpr n, A arg);

	void visit(StringLiteralExpr n, A arg);

	void visit(IntegerLiteralExpr n, A arg);

	void visit(LongLiteralExpr n, A arg);

	void visit(IntegerLiteralMinValueExpr n, A arg);

	void visit(LongLiteralMinValueExpr n, A arg);

	void visit(CharLiteralExpr n, A arg);

	void visit(DoubleLiteralExpr n, A arg);

	void visit(BooleanLiteralExpr n, A arg);

	void visit(NullLiteralExpr n, A arg);

	void visit(MethodCallExpr n, A arg);

	void visit(NameExpr n, A arg);

	void visit(ObjectCreationExpr n, A arg);

	void visit(QualifiedNameExpr n, A arg);

	void visit(ThisExpr n, A arg);

	void visit(SuperExpr n, A arg);

	void visit(UnaryExpr n, A arg);

	void visit(VariableDeclarationExpr n, A arg);

	void visit(MarkerAnnotationExpr n, A arg);

	void visit(SingleMemberAnnotationExpr n, A arg);

	void visit(NormalAnnotationExpr n, A arg);

	void visit(MemberValuePair n, A arg);

	void visit(ExplicitConstructorInvocationStmt n, A arg);

	void visit(TypeDeclarationStmt n, A arg);

	void visit(AssertStmt n, A arg);

	void visit(BlockStmt n, A arg);

	void visit(LabeledStmt n, A arg);

	void visit(EmptyStmt n, A arg);

	void visit(ExpressionStmt n, A arg);

	void visit(SwitchStmt n, A arg);

	void visit(SwitchEntryStmt n, A arg);

	void visit(BreakStmt n, A arg);

	void visit(ReturnStmt n, A arg);

	void visit(IfStmt n, A arg);

	void visit(WhileStmt n, A arg);

	void visit(ContinueStmt n, A arg);

	void visit(DoStmt n, A arg);

	void visit(ForeachStmt n, A arg);

	void visit(ForStmt n, A arg);

	void visit(ThrowStmt n, A arg);

	void visit(SynchronizedStmt n, A arg);

	void visit(TryStmt n, A arg);

	void visit(CatchClause n, A arg);

	void visit(MultiTypeParameter n, A arg);

	void visit(LambdaExpr n, A arg);

	void visit(MethodReferenceExpr n, A arg);

	void visit(TypeExpr n, A arg);

	void visit(IntersectionType n, A arg);
}
