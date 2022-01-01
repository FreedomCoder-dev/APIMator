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

public interface GenericVisitor<R, A> {

	R visit(CompilationUnit n, A arg);

	R visit(PackageDeclaration n, A arg);

	R visit(ImportDeclaration n, A arg);

	R visit(TypeParameter n, A arg);

	R visit(LineComment n, A arg);

	R visit(BlockComment n, A arg);

	R visit(ClassOrInterfaceDeclaration n, A arg);

	R visit(EnumDeclaration n, A arg);

	R visit(EmptyTypeDeclaration n, A arg);

	R visit(EnumConstantDeclaration n, A arg);

	R visit(AnnotationDeclaration n, A arg);

	R visit(AnnotationMemberDeclaration n, A arg);

	R visit(FieldDeclaration n, A arg);

	R visit(VariableDeclarator n, A arg);

	R visit(VariableDeclaratorId n, A arg);

	R visit(ConstructorDeclaration n, A arg);

	R visit(MethodDeclaration n, A arg);

	R visit(Parameter n, A arg);

	R visit(EmptyMemberDeclaration n, A arg);

	R visit(InitializerDeclaration n, A arg);

	R visit(JavadocComment n, A arg);

	R visit(ClassOrInterfaceType n, A arg);

	R visit(PrimitiveType n, A arg);

	R visit(ReferenceType n, A arg);

	R visit(VoidType n, A arg);

	R visit(WildcardType n, A arg);

	R visit(ArrayAccessExpr n, A arg);

	R visit(ArrayCreationExpr n, A arg);

	R visit(ArrayInitializerExpr n, A arg);

	R visit(AssignExpr n, A arg);

	R visit(BinaryExpr n, A arg);

	R visit(CastExpr n, A arg);

	R visit(ClassExpr n, A arg);

	R visit(ConditionalExpr n, A arg);

	R visit(EnclosedExpr n, A arg);

	R visit(FieldAccessExpr n, A arg);

	R visit(InstanceOfExpr n, A arg);

	R visit(StringLiteralExpr n, A arg);

	R visit(IntegerLiteralExpr n, A arg);

	R visit(LongLiteralExpr n, A arg);

	R visit(IntegerLiteralMinValueExpr n, A arg);

	R visit(LongLiteralMinValueExpr n, A arg);

	R visit(CharLiteralExpr n, A arg);

	R visit(DoubleLiteralExpr n, A arg);

	R visit(BooleanLiteralExpr n, A arg);

	R visit(NullLiteralExpr n, A arg);

	R visit(MethodCallExpr n, A arg);

	R visit(NameExpr n, A arg);

	R visit(ObjectCreationExpr n, A arg);

	R visit(QualifiedNameExpr n, A arg);

	R visit(ThisExpr n, A arg);

	R visit(SuperExpr n, A arg);

	R visit(UnaryExpr n, A arg);

	R visit(VariableDeclarationExpr n, A arg);

	R visit(MarkerAnnotationExpr n, A arg);

	R visit(SingleMemberAnnotationExpr n, A arg);

	R visit(NormalAnnotationExpr n, A arg);

	R visit(MemberValuePair n, A arg);

	R visit(ExplicitConstructorInvocationStmt n, A arg);

	R visit(TypeDeclarationStmt n, A arg);

	R visit(AssertStmt n, A arg);

	R visit(BlockStmt n, A arg);

	R visit(LabeledStmt n, A arg);

	R visit(EmptyStmt n, A arg);

	R visit(ExpressionStmt n, A arg);

	R visit(SwitchStmt n, A arg);

	R visit(SwitchEntryStmt n, A arg);

	R visit(BreakStmt n, A arg);

	R visit(ReturnStmt n, A arg);

	R visit(IfStmt n, A arg);

	R visit(WhileStmt n, A arg);

	R visit(ContinueStmt n, A arg);

	R visit(DoStmt n, A arg);

	R visit(ForeachStmt n, A arg);

	R visit(ForStmt n, A arg);

	R visit(ThrowStmt n, A arg);

	R visit(SynchronizedStmt n, A arg);

	R visit(TryStmt n, A arg);

	R visit(CatchClause n, A arg);

	R visit(MultiTypeParameter n, A arg);

	R visit(LambdaExpr n, A arg);

	R visit(MethodReferenceExpr n, A arg);

	R visit(TypeExpr n, A arg);

	R visit(IntersectionType n, A arg);
}
