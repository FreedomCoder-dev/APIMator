package org.walkmod.javalang;

public class CleanerTokenManager extends ASTParserTokenManager {

	public CleanerTokenManager(JavaCharStream stream) {
		super(stream);
	}

	protected Token jjFillToken() {
		Token token = super.jjFillToken();
		token.beginLine = 0;
		token.endLine = 0;
		token.beginColumn = 0;
		token.endColumn = 0;
		return token;
	}
}
