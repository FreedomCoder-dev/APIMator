package org.walkmod.javalang.tags;

public interface TagsParserConstants {

	int EOF = 0;
	int LINKPLAIN = 1;
	int LINK = 2;
	int VALUE = 3;
	int PARAM = 4;
	int RETURN = 5;
	int THROWS = 6;
	int EXCEPTION = 7;
	int SEE = 8;
	int SERIALFIELD = 9;
	int SERIALDATA = 10;
	int SERIAL = 11;
	int INHERITDOC = 12;
	int DOCROOT = 13;
	int CODE = 14;
	int DEPRECATED = 15;
	int AUTHOR = 16;
	int LITERAL = 17;
	int SINCE = 18;
	int VERSION = 19;
	int OPENBRACE = 20;
	int CLOSEBRACE = 21;
	int ASTERISK = 22;
	int IDENTIFIER = 23;
	int LETTER = 24;
	int PART_LETTER = 25;
	int NAMECHAR = 26;
	int OPERATION = 27;
	int WORD = 28;
	int SPACE = 29;
	int BEGIN = 30;

	int DEFAULT = 0;

	String[] tokenImage = { "<EOF>", "\"@linkplain\"", "\"@link\"", "\"@value\"", "\"@param\"", "\"@return\"", "\"@throws\"", "\"@exception\"", "\"@see\"", "\"@serialField\"", "\"@serialData\"", "\"@serial\"", "\"@inheritDoc\"", "\"@docRoot\"", "\"@code\"", "\"@deprecated\"", "\"@author\"", "\"@literal\"", "\"@since\"", "\"@version\"", "\"{\"", "\"}\"", "\"*\"", "<IDENTIFIER>", "<LETTER>", "<PART_LETTER>", "<NAMECHAR>", "<OPERATION>", "<WORD>", "<SPACE>", "<BEGIN>" };

}
