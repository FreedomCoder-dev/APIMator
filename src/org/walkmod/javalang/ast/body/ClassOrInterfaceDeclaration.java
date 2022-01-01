package org.walkmod.javalang.ast.body;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ASTManager;
import org.walkmod.javalang.ParseException;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.TypeParameter;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.stmt.ExpressionStmt;
import org.walkmod.javalang.ast.stmt.Statement;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.ast.type.VoidType;
import org.walkmod.javalang.comparators.BodyDeclaratorComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;

import java.util.Collections;

public final class ClassOrInterfaceDeclaration extends TypeDeclaration
{

	private boolean interface_;

	private List<TypeParameter> typeParameters;

	private List<ClassOrInterfaceType> extendsList;

	private List<ClassOrInterfaceType> implementsList;

	private CompilationUnit unit;

	public ClassOrInterfaceDeclaration(CompilationUnit unit)
	{
		this.unit = unit;
	}

	public ClassOrInterfaceDeclaration()
	{}

	public ClassOrInterfaceDeclaration(int modifiers, boolean isInterface, String name)
	{
		this.interface_ = isInterface;
		setModifiers(modifiers);
		setName(name);
	}

	public ClassOrInterfaceDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, boolean isInterface, String name, List<TypeParameter> typeParameters, List<ClassOrInterfaceType> extendsList, List<ClassOrInterfaceType> implementsList, List<BodyDeclaration> members)
	{
		this.interface_ = isInterface;
		setModifiers(modifiers);
		setName(name);
		setTypeParameters(typeParameters);
		setExtends(extendsList);
		setImplements(implementsList);
		setJavaDoc(javaDoc);
		setMembers(members);
	}

	public ClassOrInterfaceDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, boolean isInterface, String name, List<TypeParameter> typeParameters, List<ClassOrInterfaceType> extendsList, List<ClassOrInterfaceType> implementsList, List<BodyDeclaration> members)
	{
		super(beginLine, beginColumn, endLine, endColumn, annotations, javaDoc, modifiers, name, members);
		this.interface_ = isInterface;
		setTypeParameters(typeParameters);
		setExtends(extendsList);
		setImplements(implementsList);
	}

	ConstructorDeclaration mainconstructor = null;

	public void setMainconstructor(ConstructorDeclaration mainconstructor)
	{
		this.mainconstructor = mainconstructor;
	}

	public ConstructorDeclaration getMainconstructor()
	{
		return mainconstructor;
	}

	public ClassOrInterfaceDeclaration addEmptyConstructor()
	{
		ConstructorDeclaration meth = new ConstructorDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(getName());
		meth.makeBody();
		setMainconstructor(meth);
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public ConstructorDeclaration makeConstructor()
	{
		ConstructorDeclaration meth = new ConstructorDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(getName());
		setMainconstructor(meth);
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return meth;
	}

	public ClassOrInterfaceDeclaration addAutoinitConstructor(String... fields) throws ParseException
	{
		ConstructorDeclaration meth = new ConstructorDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(getName());
		setMainconstructor(meth);

		BlockStmt body = meth.makeBody();
		for (String f : fields)
		{
			VariableDeclarator field = getField(f);
			if (field == null)
			{
				throw new ParseException("field " + field + " not found");
			}
			FieldDeclaration decl = (FieldDeclaration) field.getParentNode();
			meth.addParameter(decl.getType(), f);
			body.addSetFieldFromVar(f, f);
		}
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public ClassOrInterfaceDeclaration makeClass(String name)
	{
		ClassOrInterfaceDeclaration meth = new ClassOrInterfaceDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(name);

		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return meth;
	}

	public ClassOrInterfaceDeclaration addExtends(String name)
	{
		ClassOrInterfaceType meth = new ClassOrInterfaceType(name);

		List<ClassOrInterfaceType> types = getExtends();
		if (types == null) types = new ArrayList<ClassOrInterfaceType>();
		types.add(meth);
		setExtends(types);
		return this;
	}

	public ClassOrInterfaceDeclaration addImplements(String name)
	{
		ClassOrInterfaceType meth = new ClassOrInterfaceType(name);

		List<ClassOrInterfaceType> types = getImplements();
		if (types == null) types = new ArrayList<ClassOrInterfaceType>();
		types.add(meth);
		setImplements(types);
		return this;
	}

	public ClassOrInterfaceDeclaration addAutoinitBuilder(String... fields) throws ParseException
	{
		return addAutoinitBuilder(true, fields);
	}

	public ClassOrInterfaceDeclaration addAutoinitBuilder(boolean makeConstructor, String... fields) throws ParseException
	{
		if (makeConstructor) addAutoinitConstructor(fields);
		ClassOrInterfaceDeclaration meth = new ClassOrInterfaceDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setStatic(true);
		meth.setName("Builder");
		meth.addEmptyConstructor();

		FieldDeclaration[] decls = new FieldDeclaration[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			String f = fields[i];
			VariableDeclarator field = getField(f);
			if (field == null)
			{
				throw new ParseException("field " + field + " not found");
			}
			FieldDeclaration decl = (FieldDeclaration) field.getParentNode();
			decls[i] = decl;
			meth.addField(decl.getType().toString(), f);
			meth.addGetter(f);
			meth.addSetter(f, true);
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String s : fields)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(", ");
			}
			sb.append(s);
		}
		meth.makeMethod("build").setPublic(true).setType(getName()).makeBody().addSourceDirectly("return new " + getName() + "(" + sb + ");");
		meth.sortMembers();
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public ClassOrInterfaceDeclaration sortMembers()
	{
		List<BodyDeclaration> members = getMembers();
		if (members == null) members = new ArrayList<BodyDeclaration>();

		Collections.sort(members, new BodyDeclaratorComparator());

		return this;
	}

	public String tostrclasses(Object[] list)
	{
		StringBuilder sb = new StringBuilder();
		for (Object bd : list)
		{
			sb.append(bd.getClass());
			sb.append(',');
		}
		return sb.toString();
	}

	public String tostrclasses(List list)
	{
		StringBuilder sb = new StringBuilder();
		for (Object bd : list)
		{
			sb.append(bd.getClass());
			sb.append(',');
		}
		return sb.toString();
	}

	public ClassOrInterfaceDeclaration addGetter(String field) throws ParseException
	{
		MethodDeclaration meth = new MethodDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		VariableDeclarator f = getField(field);
		if (field == null)
		{
			throw new ParseException("field " + field + " not found");
		}
		FieldDeclaration decl = (FieldDeclaration) f.getParentNode();
		meth.setName(getterName(decl.getType().toString().equalsIgnoreCase("boolean"), field));
		meth.setType(decl.getType());
		meth.makeBody().addReturn("this." + field);
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public ClassOrInterfaceDeclaration addSetter(String field, boolean returnSelf) throws ParseException
	{
		MethodDeclaration meth = new MethodDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		VariableDeclarator f = getField(field);
		if (field == null)
		{
			throw new ParseException("field " + field + " not found");
		}
		FieldDeclaration decl = (FieldDeclaration) f.getParentNode();
		meth.setName(setterName(decl.getType().toString().equalsIgnoreCase("boolean"), field));
		meth.setType(returnSelf ? new ClassOrInterfaceType(getName()) : new VoidType());
		meth.addParameter(decl.getType(), field);
		BlockStmt makeBody = meth.makeBody();
		makeBody.addSetFieldFromVar(field, field);
		if (returnSelf) makeBody.addReturn("this");
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public ClassOrInterfaceDeclaration addSetter(String field) throws ParseException
	{
		return addSetter(field, false);
	}

	public String getterName(boolean isBoolean, String from)
	{
		if (isBoolean)
		{
			return "is" + capitalizeFirstLetter(from);
		}

		else
		{
			return "get" + capitalizeFirstLetter(from);
		}
	}

	public String setterName(boolean isBoolean, String from)
	{
		return "set" + capitalizeFirstLetter(from);
	}

	public String lowercaseFirstLetter(String src)
	{
		char[] arr = src.toCharArray();
		arr[0] = Character.toLowerCase(arr[0]);
		return new String(arr);
	}

	public String capitalizeFirstLetter(String src)
	{
		char[] arr = src.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}

	public MethodDeclaration getMethod(String name)
	{
		List<BodyDeclaration> members = getMembers();
		for (BodyDeclaration bd : members)
		{
			if (bd instanceof MethodDeclaration)
			{
				MethodDeclaration md = (MethodDeclaration) bd;
				if (md.getName() == name)
				{
					return md;
				}
			}
		}
		return null;
	}

	public VariableDeclarator getField(String name)
	{
		List<BodyDeclaration> members = getMembers();
		for (BodyDeclaration bd : members)
		{
			if (bd instanceof FieldDeclaration)
			{
				FieldDeclaration md = (FieldDeclaration) bd;
				for (VariableDeclarator vd : md.getVariables())
				{
					if (vd.getId().getName().equals(name))
					{
						return vd;
					}
				}
			}
		}
		return null;
	}

	public ClassOrInterfaceDeclaration addModifier(int modifier)
	{
		setModifiers(getModifiers() | modifier);
		return this;
	}

	public ClassOrInterfaceDeclaration removeModifier(int modifier)
	{
		setModifiers(getModifiers() & ~modifier);
		return this;
	}

	public ClassOrInterfaceDeclaration setPublic(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.PUBLIC);
			setPrivate(false);
		}

		else
		{
			removeModifier(ModifierSet.PUBLIC);
		}

		return this;
	}

	public ClassOrInterfaceDeclaration setPrivate(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.PRIVATE);
			setPublic(false);
		}

		else
		{
			removeModifier(ModifierSet.PRIVATE);
		}

		return this;
	}

	public ClassOrInterfaceDeclaration setProtected(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.PROTECTED);
		}

		else
		{
			removeModifier(ModifierSet.PROTECTED);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setNative(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.NATIVE);
		}

		else
		{
			removeModifier(ModifierSet.NATIVE);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setAbstract(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.ABSTRACT);
		}

		else
		{
			removeModifier(ModifierSet.ABSTRACT);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setFinal(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.FINAL);
		}

		else
		{
			removeModifier(ModifierSet.FINAL);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setStatic(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.STATIC);
		}

		else
		{
			removeModifier(ModifierSet.STATIC);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setSynchronized(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.SYNCHRONIZED);
		}

		else
		{
			removeModifier(ModifierSet.SYNCHRONIZED);
		}
		return this;
	}

	public ClassOrInterfaceDeclaration setStrictfp(boolean enable)
	{
		if (enable)
		{
			addModifier(ModifierSet.STRICTFP);
		}

		else
		{
			removeModifier(ModifierSet.STRICTFP);
		}
		return this;
	}

	public FieldDeclaration makeField(Type type)
	{
		FieldDeclaration meth = new FieldDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setType(type);
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return meth;
	}

	public FieldDeclaration makeField(String type)
	{
		FieldDeclaration meth = new FieldDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setType(new ClassOrInterfaceType(type));
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return meth;
	}

	public ClassOrInterfaceDeclaration addSourceDirectly(String source) throws ParseException
	{
		addMember((BodyDeclaration) ASTManager.parse(BodyDeclaration.class, source));
		return this;
	}

	public ClassOrInterfaceDeclaration addMember(BodyDeclaration bdecl)
	{
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(bdecl);
		setMembers(types);
		return this;
	}
	public ClassOrInterfaceDeclaration addField(String type, String... names)
	{
		return addField(type, false, names);
	}
	public ClassOrInterfaceDeclaration addField(String type, boolean init, String... names)
	{
		FieldDeclaration meth = new FieldDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setType(new ClassOrInterfaceType(type));
		if (init)
		{
			String initExpression = "null";
			switch (type)
			{
				case "boolean":
					initExpression = "false";
					break;
				case "char":
				case "int":
				case "short":
				case "byte":
				case "Char":
				case "Integer":
				case "Short":
				case "Byte":
					initExpression = "0";
					break;
				case "float":
				case "Float":
                    initExpression = "0f";
					break;
				case "Double":
				case "double":
                    initExpression = "0d";
					break;
				case "long":
				case "Long":
				    initExpression = "0l";
                    break;
				case "String":
					initExpression = "\"\"";
					break;
				default:
					initExpression = "new " + type + "()";
					break;
			}
			Expression parse = null;
			try
			{
				parse = ((ExpressionStmt) ASTManager.parse(Statement.class, initExpression + ";")).getExpression();
			}
			catch (ParseException e)
			{e.printStackTrace();} 
			for (String name : names)
			{
				meth.addInitVariable(name, parse);
			}
		}
		else 
			for (String name : names) meth.addVariable(name);
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return this;
	}

	public MethodDeclaration makeMethod(String name)
	{
		MethodDeclaration meth = new MethodDeclaration();
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(name);
		meth.setType(new VoidType());
		List<BodyDeclaration> types = getMembers();
		if (types == null) types = new ArrayList<BodyDeclaration>();
		types.add(meth);
		setMembers(types);
		return meth;
	}

	public CompilationUnit done()
	{
		return unit;
	}

	@Override
	public boolean removeChild(Node child)
	{
		boolean result = false;
		if (child != null)
		{
			result = super.removeChild(child);
			if (!result)
			{
				if (child instanceof TypeParameter)
				{
					if (typeParameters != null)
					{
						List<TypeParameter> tp = new LinkedList<TypeParameter>(typeParameters);
						result = tp.remove(child);
						typeParameters = tp;
					}
				}
				else if (child instanceof ClassOrInterfaceType)
				{
					if (extendsList != null)
					{
						List<ClassOrInterfaceType> extendsListAux = new LinkedList<ClassOrInterfaceType>(extendsList);
						result = extendsListAux.remove(child);
						extendsList = extendsListAux;
					}
					if (!result && implementsList != null)
					{
						List<ClassOrInterfaceType> implementsListAux = new LinkedList<ClassOrInterfaceType>(implementsList);
						result = implementsListAux.remove(child);
						implementsList = implementsListAux;
					}
				}
			}
		}
		if (result)
		{
			updateReferences(child);
		}
		return result;
	}

	@Override
	public List<Node> getChildren()
	{
		List<Node> children = super.getChildren();
		if (typeParameters != null)
		{
			children.addAll(typeParameters);
		}
		if (extendsList != null)
		{
			children.addAll(extendsList);
		}
		if (implementsList != null)
		{
			children.addAll(implementsList);
		}
		return children;
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg)
	{
		if (!check())
		{
			return null;
		}
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg)
	{
		if (check())
		{
			v.visit(this, arg);
		}
	}

	public List<ClassOrInterfaceType> getExtends()
	{
		return extendsList;
	}

	public List<ClassOrInterfaceType> getImplements()
	{
		return implementsList;
	}

	public List<TypeParameter> getTypeParameters()
	{
		return typeParameters;
	}

	public boolean isInterface()
	{
		return interface_;
	}

	public void setExtends(List<ClassOrInterfaceType> extendsList)
	{
		this.extendsList = extendsList;
		setAsParentNodeOf(extendsList);
	}

	public void setImplements(List<ClassOrInterfaceType> implementsList)
	{
		this.implementsList = implementsList;
		setAsParentNodeOf(implementsList);
	}

	public void setInterface(boolean interface_)
	{
		this.interface_ = interface_;
	}

	public void setTypeParameters(List<TypeParameter> typeParameters)
	{
		this.typeParameters = typeParameters;
		setAsParentNodeOf(typeParameters);
	}

	@Override
	public void merge(TypeDeclaration remoteTypeDeclaration, MergeEngine configuration)
	{
		super.merge(remoteTypeDeclaration, configuration);
		List<TypeParameter> typeParametersList = new LinkedList<TypeParameter>();
		configuration.apply(getTypeParameters(), ((ClassOrInterfaceDeclaration) remoteTypeDeclaration).getTypeParameters(), typeParametersList, TypeParameter.class);
		if (!typeParametersList.isEmpty())
		{
			setTypeParameters(typeParametersList);
		}

		else
		{
			setTypeParameters(null);
		}
		List<ClassOrInterfaceType> implementsList = new LinkedList<ClassOrInterfaceType>();
		configuration.apply(getImplements(), ((ClassOrInterfaceDeclaration) remoteTypeDeclaration).getImplements(), implementsList, ClassOrInterfaceType.class);
		if (!implementsList.isEmpty())
		{
			setImplements(implementsList);
		}

		else
		{
			setImplements(null);
		}
		List<ClassOrInterfaceType> extendsList = new LinkedList<ClassOrInterfaceType>();
		configuration.apply(getExtends(), ((ClassOrInterfaceDeclaration) remoteTypeDeclaration).getExtends(), extendsList, ClassOrInterfaceType.class);
		if (!extendsList.isEmpty())
		{
			setExtends(extendsList);
		}

		else
		{
			setExtends(null);
		}
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild)
	{
		boolean update = super.replaceChildNode(oldChild, newChild);
		if (!update)
		{
			if (extendsList != null)
			{
				List<ClassOrInterfaceType> auxExtends = new LinkedList<ClassOrInterfaceType>(extendsList);
				update = replaceChildNodeInList(oldChild, newChild, auxExtends);
				if (update)
				{
					extendsList = auxExtends;
				}

			}
			if (!update && implementsList != null)
			{
				List<ClassOrInterfaceType> auxImplementsList = new LinkedList<ClassOrInterfaceType>(implementsList);
				update = replaceChildNodeInList(oldChild, newChild, auxImplementsList);
				if (update)
				{
					implementsList = auxImplementsList;
				}

			}
			if (!update && typeParameters != null)
			{
				List<TypeParameter> auxTypeParams = new LinkedList<TypeParameter>(typeParameters);
				update = replaceChildNodeInList(oldChild, newChild, auxTypeParams);
				if (update)
				{
					typeParameters = auxTypeParams;
				}
			}
		}
		return update;
	}

	@Override
	public ClassOrInterfaceDeclaration clone() throws CloneNotSupportedException
	{
		return new ClassOrInterfaceDeclaration(clone(getJavaDoc()), getModifiers(), clone(getAnnotations()), interface_, getName(), clone(getTypeParameters()), clone(getExtends()), clone(getImplements()), clone(getMembers()));
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions()
	{
		Map<String, SymbolDefinition> result = super.getTypeDefinitions();
		if (typeParameters != null)
		{
			for (TypeParameter tp : typeParameters)
			{
				result.put(tp.getSymbolName(), tp);
			}
		}
		return result;
	}

}
