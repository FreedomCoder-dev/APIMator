package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class StringLiteralExpr extends LiteralExpr {

	protected String value;

	public StringLiteralExpr() {}

	public StringLiteralExpr(String value) {
		this.value = escape(value);
	}

	public StringLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn, String value) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.value = escape(value);
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

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = escape(value);
	}

	private static String escape(String str) {
		if (str == null) {
			return null;
		}
		int sz;
		sz = str.length();
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);

			if (ch > 0xfff) {
				out.append("\\u" + hex(ch));
			} else if (ch > 0xff) {
				out.append("\\u0" + hex(ch));
			} else if (ch > 0x7f) {
				out.append("\\u00" + hex(ch));
			} else if (ch < 32) {
				switch(ch) {
					case '\b':
					case '\n':
					case '\t':
					case '\f':
					case '\r':
							out.append(ch);
							break;
					default:
							if (ch > 0xf) {
								out.append("\\u00" + hex(ch));
							} else {
								out.append("\\u000" + hex(ch));
							}
							break;
				}
			} else {
				out.append(ch);
			}
		}
		return out.toString();
	}

	private static String hex(char ch) {
		return Integer.toHexString(ch).toUpperCase();
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public StringLiteralExpr clone() throws CloneNotSupportedException {
		return new StringLiteralExpr(value);
	}

}
