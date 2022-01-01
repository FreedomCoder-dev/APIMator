package org.walkmod.javalang.javadoclinks;

public interface JavadocLinkParserConstants {

	int EOF = 0;
	int OPENPAR = 1;
	int CLOSEPAR = 2;
	int SEPARATOR = 3;
	int COMMA = 4;
	int DOT = 5;
	int IDENTIFIER = 6;
	int LETTER = 7;
	int PART_LETTER = 8;
	int SPACE = 9;

	int DEFAULT = 0;

	String[] tokenImage = { "<EOF>", "\"(\"", "\")\"", "\"#\"", "\",\"", "\".\"", "<IDENTIFIER>", "<LETTER>", "<PART_LETTER>", "<SPACE>" };

}
