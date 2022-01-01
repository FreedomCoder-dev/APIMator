package org.walkmod.javalang.ast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.comparators.CompilationUnitComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.body.ClassOrInterfaceDeclaration;
import org.walkmod.javalang.ast.body.TypeDeclaration;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.merger.MergeEngine;
import org.walkmod.merger.Mergeable;
import org.walkmod.javalang.ast.body.ModifierSet;

public final class CompilationUnit extends Node implements Mergeable<CompilationUnit> {

	private PackageDeclaration pakage;

	private List<ImportDeclaration> imports;

	private List<TypeDeclaration> types;

	private List<Comment> comments;

	private URI uri;

	private boolean withSymbols = false;

	private ClassOrInterfaceDeclaration mainclass;

	public CompilationUnit() {}

	public CompilationUnit(PackageDeclaration pakage, List<ImportDeclaration> imports, List<TypeDeclaration> types, List<Comment> comments) {
		setPackage(pakage);
		setImports(imports);
		setTypes(types);
		setComments(comments);
	}

	public CompilationUnit(int beginLine, int beginColumn, int endLine, int endColumn, PackageDeclaration pakage, List<ImportDeclaration> imports, List<TypeDeclaration> types, List<Comment> comments) {
		super(beginLine, beginColumn, endLine, endColumn);
		setPackage(pakage);
		setImports(imports);
		setTypes(types);
		setComments(comments);
	}

	public void setMainclass(ClassOrInterfaceDeclaration mainclass) {
		this.mainclass = mainclass;
	}

	public ClassOrInterfaceDeclaration getMainclass() {
		return mainclass;
	}
	public CompilationUnit addImportAll(String type) {
		ImportDeclaration meth = new ImportDeclaration();
		meth.setName(new NameExpr(type));
		meth.setAsterisk(true);
		List<ImportDeclaration> types = getImports();
		if (types == null) types = new ArrayList<ImportDeclaration>();
		types.add(meth);
		setImports(types);
		return this;
	}

	public CompilationUnit addImport(String type) {
		ImportDeclaration meth = new ImportDeclaration();
		meth.setName(new NameExpr(type));
		List<ImportDeclaration> types = getImports();
		if (types == null) types = new ArrayList<ImportDeclaration>();
		types.add(meth);
		setImports(types);
		return this;
	}
	public ClassOrInterfaceDeclaration makeClass(String name) {
		ClassOrInterfaceDeclaration meth = new ClassOrInterfaceDeclaration(this);
		meth.setModifiers(ModifierSet.PUBLIC);
		meth.setName(name);
		List<TypeDeclaration> types = getTypes();
		if (types == null) types = new ArrayList<TypeDeclaration>();
		types.add(meth);
		setTypes(types);
		setMainclass(meth);
		return meth;
	}

	public void setURI(URI uri) {
		this.uri = uri;
	}

	public URI getURI() {
		return uri;
	}

	public void save(File basedir) {
		File fldr = new File(basedir, getPackage().getName().toString().replace(".", "/"));
		fldr.mkdirs();
		File code = new File(fldr, getMainclass().getName() + ".java");
		if (true) try {
			FileWriter wr = new FileWriter(code);
			wr.append(getPrettySource('	', 0, 1));
			wr.flush();
			wr.close();
		} catch (IOException e) {}
	}

	@Override
	public List<Node> getChildren() {
		List<Node> aux = new LinkedList<Node>();
		if (pakage != null) {
			aux.add(pakage);
		}
		if (imports != null) {
			aux.addAll(imports);
		}
		if (types != null) {
			aux.addAll(types);
		}
		if (comments != null) {
			aux.addAll(comments);
		}
		return aux;
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (child instanceof ImportDeclaration) {
				if (imports != null) {
					List<ImportDeclaration> auxImports = new LinkedList<ImportDeclaration>(imports);
					result = auxImports.remove(child);
					this.imports = auxImports;
				}
			} else if (child instanceof TypeDeclaration) {
				if (types != null) {
					List<TypeDeclaration> typesAux = new LinkedList<TypeDeclaration>(types);
					result = typesAux.remove(child);
					types = typesAux;
				}
			} else if (child instanceof Comment) {
				if (comments != null) {
					List<Comment> commentAux = new LinkedList<Comment>(comments);
					result = commentAux.remove(child);
					comments = commentAux;
				}
			} else if (child == pakage) {
				pakage = null;
				result = true;
			}
		}
		if (result) {
			updateReferences(result);
		}
		return result;
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

	public List<Comment> getComments() {
		return comments;
	}

	public List<ImportDeclaration> getImports() {
		return imports;
	}

	public PackageDeclaration getPackage() {
		return pakage;
	}

	public List<TypeDeclaration> getTypes() {
		return types;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setImports(List<ImportDeclaration> imports) {
		this.imports = imports;
		setAsParentNodeOf(imports);
	}

	public CompilationUnit setPackage(String pakage) {
		setPackage(new PackageDeclaration(new NameExpr(pakage)));
		return this;
	}

	public void setPackage(PackageDeclaration pakage) {
		if (this.pakage != null) {
			updateReferences(this.pakage);
		}
		this.pakage = pakage;
		setAsParentNodeOf(pakage);
	}

	public void setTypes(List<TypeDeclaration> types) {
		this.types = types;
		setAsParentNodeOf(types);
	}

	@Override
	public Comparator<?> getIdentityComparator() {
		return new CompilationUnitComparator();
	}

	@Override
	public void merge(CompilationUnit remoteCU, MergeEngine configuration) {
		List<ImportDeclaration> resultImports = new LinkedList<ImportDeclaration>();
		configuration.apply(getImports(), remoteCU.getImports(), resultImports, ImportDeclaration.class);
		setImports(resultImports);

		List<TypeDeclaration> resultTypes = new LinkedList<TypeDeclaration>();
		configuration.apply(getTypes(), remoteCU.getTypes(), resultTypes, TypeDeclaration.class);
		setTypes(resultTypes);

		List<Comment> resultComments = new LinkedList<Comment>();
		configuration.apply(getComments(), remoteCU.getComments(), resultComments, Comment.class);
		setComments(resultComments);

	}

	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (pakage == oldChild) {
			setPackage((PackageDeclaration) newChild);
			updated = true;
		}
		if (!updated && imports != null) {
			List<ImportDeclaration> auxImports = new LinkedList<ImportDeclaration>(imports);
			updated = replaceChildNodeInList(oldChild, newChild, auxImports);
			if (updated) {
				imports = auxImports;
			}
		}
		if (!updated && types != null) {
			List<TypeDeclaration> auxTypes = new LinkedList<TypeDeclaration>(types);
			updated = replaceChildNodeInList(oldChild, newChild, auxTypes);
			if (updated) {
				types = auxTypes;
			}
		}
		if (!updated && comments != null) {
			List<Comment> auxComments = new LinkedList<Comment>(comments);
			updated = replaceChildNodeInList(oldChild, newChild, auxComments);
			if (updated) {
				comments = auxComments;
			}
		}

		return updated;
	}

	public boolean hasEqualFileName(CompilationUnit other) {
		boolean samePackage = getPackage() == null && other.getPackage() == null;
		samePackage = samePackage 
		|| (getPackage() != null && other.getPackage() != null && getPackage().equals(other.getPackage()));

		boolean sameType = getTypes() == null && other.getTypes() == null;
		sameType = sameType || (getTypes() != null && other.getTypes() != null 
		&& getTypes().get(0).getName().equals(other.getTypes().get(0).getName()));

		return (samePackage && sameType);
	}

	public String getQualifiedName() {
		String name = "";
		if (getPackage() != null) {
			name = getPackage().getName().toString();
		}
		if (getTypes() != null && !getTypes().isEmpty()) {
			if (getPackage() != null) {
				name = name + ".";
			}
			name = name + getTypes().get(0).getName();
		}

		return name;
	}

	public String getFileName() {
		String path = "";
		if (getPackage() != null) {
			NameExpr packageName = getPackage().getName();
			String packPath = packageName.toString().replace('.', File.separatorChar);
			path = path + packPath + File.separator + getSimpleName() + ".java";
		} else {
			path = path + getSimpleName() + ".java";
		}
		return path;
	}

	public String getSimpleName() {
		if (getTypes() != null) {
			return getTypes().get(0).getName();
		} else {
			return "package-info";
		}
	}

	@Override
	public CompilationUnit clone() throws CloneNotSupportedException {
		return new CompilationUnit(clone(pakage), clone(imports), clone(types), clone(comments));
	}

	public boolean withSymbols() {
		return withSymbols;
	}

	public void withSymbols(boolean isCompilable) {
		this.withSymbols = isCompilable;
	}

}
