package org.walkmod.javalang.tags;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.body.JavadocTag;

public class TagsParser implements TagsParserConstants {
	public static List<JavadocTag> parse(InputStream aInSt) throws ParseException {

		TagsParser parser = new TagsParser(aInSt);

		return parser.javadoc();
	}

	public List<JavadocTag> main(String[] args) throws ParseException, FileNotFoundException {
		return parse(new FileInputStream(args[0]));
	}

	public final List<JavadocTag> javadoc() throws ParseException {
		List<JavadocTag> result = new LinkedList();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SEE:
			case SERIALFIELD:
			case SERIALDATA:
			case SERIAL:
			case DEPRECATED:
			case AUTHOR:
			case SINCE:
			case VERSION:
			case OPENBRACE:
			case CLOSEBRACE:
			case ASTERISK:
			case IDENTIFIER:
			case NAMECHAR:
			case OPERATION:
			case WORD:
					description(result);
					break;
			default:
					jj_la1[0] = jj_gen;
        }
		jj_consume_token(0);
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public final String inlineTag() throws ParseException {
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case CODE:
					jj_consume_token(CODE);
					break;
			case DOCROOT:
					jj_consume_token(DOCROOT);
					break;
			case INHERITDOC:
					jj_consume_token(INHERITDOC);
					break;
			case LITERAL:
					jj_consume_token(LITERAL);
					break;
			default:
					jj_la1[1] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return token.image;
		}
		throw new Error("Missing return statement in function");
	}

	public final String blockTag() throws ParseException {
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case AUTHOR:
					jj_consume_token(AUTHOR);
					break;
			case DEPRECATED:
					jj_consume_token(DEPRECATED);
					break;
			case SERIALDATA:
					jj_consume_token(SERIALDATA);
					break;
			case SINCE:
					jj_consume_token(SINCE);
					break;
			case VERSION:
					jj_consume_token(VERSION);
					break;
			default:
					jj_la1[2] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return token.image;
		}
		throw new Error("Missing return statement in function");
	}

	public final String IDBlockTag() throws ParseException {
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case PARAM:
					jj_consume_token(PARAM);
					break;
			case THROWS:
					jj_consume_token(THROWS);
					break;
			case EXCEPTION:
					jj_consume_token(EXCEPTION);
					break;
			case RETURN:
					jj_consume_token(RETURN);
					break;
			default:
					jj_la1[3] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return token.image;
		}
		throw new Error("Missing return statement in function");
	}

	public final String NSInlineTag() throws ParseException {
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LINK:
					jj_consume_token(LINK);
					break;
			case LINKPLAIN:
					jj_consume_token(LINKPLAIN);
					break;
			case VALUE:
					jj_consume_token(VALUE);
					break;
			default:
					jj_la1[4] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return token.image;
		}
		throw new Error("Missing return statement in function");
	}

	public final String anyTag() throws ParseException {
		String result = "";
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case LITERAL:
					result = inlineTag();
					break;
			case SERIALDATA:
			case DEPRECATED:
			case AUTHOR:
			case SINCE:
			case VERSION:
					result = blockTag();
					break;
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
					result = IDBlockTag();
					break;
			case LINKPLAIN:
			case LINK:
			case VALUE:
					result = NSInlineTag();
					break;
			default:
					jj_la1[5] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public final String description(List<JavadocTag> tags) throws ParseException {
		String result = "";
		String arg1 = null;
		String arg2 = null;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case OPENBRACE:
					jj_consume_token(OPENBRACE);
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case LINKPLAIN:
						case LINK:
						case VALUE:
						case INHERITDOC:
						case DOCROOT:
						case CODE:
						case LITERAL:
								arg1 = inlines(tags);
								arg1 = "{ " + arg1;
								break;
						default:
								jj_la1[6] = jj_gen;
                    }
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case CLOSEBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								arg2 = description(tags);
								break;
						default:
								jj_la1[7] = jj_gen;
                    }
					break;
			case CLOSEBRACE:
			case ASTERISK:
			case IDENTIFIER:
			case NAMECHAR:
			case OPERATION:
			case WORD:
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case WORD:
								jj_consume_token(WORD);
								break;
						case NAMECHAR:
								jj_consume_token(NAMECHAR);
								break;
						case IDENTIFIER:
								jj_consume_token(IDENTIFIER);
								break;
						case ASTERISK:
								jj_consume_token(ASTERISK);
								break;
						case OPERATION:
								jj_consume_token(OPERATION);
								break;
						case CLOSEBRACE:
								jj_consume_token(CLOSEBRACE);
								break;
						default:
								jj_la1[8] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					arg1 = token.image;
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case CLOSEBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								arg2 = description(tags);
								break;
						default:
								jj_la1[9] = jj_gen;
                    }
					break;
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SEE:
			case SERIALFIELD:
			case SERIALDATA:
			case SERIAL:
			case DEPRECATED:
			case AUTHOR:
			case SINCE:
			case VERSION:
					blocks(tags);
					break;
			default:
					jj_la1[10] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		if (arg1 != null && arg2 != null) {
			result = arg1 + " " + arg2;
		} else if (arg1 == null) {
			result = arg2;
		} else {
			result = arg1;
		}
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public final String inlines(List<JavadocTag> tags) throws ParseException {
		String description = null;
		String ns = null;
		String tag = null;
		JavadocTag result = null;
		List<String> values = null;
		String words = null;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case LITERAL:
					tag = inlineTag();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case LINKPLAIN:
						case LINK:
						case VALUE:
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SERIALDATA:
						case INHERITDOC:
						case DOCROOT:
						case CODE:
						case DEPRECATED:
						case AUTHOR:
						case LITERAL:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								description = inlineDescription();
								break;
						default:
								jj_la1[11] = jj_gen;
                    }
					break;
			case LINKPLAIN:
			case LINK:
			case VALUE:
					tag = NSInlineTag();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case OPENBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case IDENTIFIER:
									case NAMECHAR:
									case OPERATION:
											ns = namespace();
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case LINKPLAIN:
												case LINK:
												case VALUE:
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SERIALDATA:
												case INHERITDOC:
												case DOCROOT:
												case CODE:
												case DEPRECATED:
												case AUTHOR:
												case LITERAL:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														description = inlineDescription();
														break;
												default:
														jj_la1[12] = jj_gen;
                                            }
											break;
									case OPENBRACE:
									case ASTERISK:
									case WORD:
											words = NSInlineDescription();
											break;
									default:
											jj_la1[13] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								break;
						default:
								jj_la1[14] = jj_gen;
                    }
					break;
			default:
					jj_la1[15] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		jj_consume_token(CLOSEBRACE);
		if (ns != null) {
			values = new LinkedList<String>();
			values.add(ns);
			if (description != null) {
				values.add(description);
			}
		} else if (description != null) {
			values = new LinkedList<String>();
			values.add(description);
		}
		if (tag != null) {
			result = new JavadocTag(tag, values, true);
			tags.add(result);
		}
		if (words != null) {
			words += " }";
		} else {
			words = "}";
		}
		{
			if (true) return words;
		}
		throw new Error("Missing return statement in function");
	}

	public final String namespace() throws ParseException {
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
					jj_consume_token(IDENTIFIER);
					break;
			case NAMECHAR:
					jj_consume_token(NAMECHAR);
					break;
			case OPERATION:
					jj_consume_token(OPERATION);
					break;
			default:
					jj_la1[16] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return token.image;
		}
		throw new Error("Missing return statement in function");
	}

	public final void blocks(List<JavadocTag> tags) throws ParseException {
		String name = null;
		String arg1 = null;
		String arg2 = null;
		String arg3 = null;
		String aux = null;
		JavadocTag tag = null;
		JavadocTag tag2 = null;
		List<String> values = null;
		List<JavadocTag> postTags = new LinkedList<JavadocTag>();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case SERIALDATA:
			case DEPRECATED:
			case AUTHOR:
			case SINCE:
			case VERSION:
					name = blockTag();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case CLOSEBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								arg1 = description(postTags);
								break;
						default:
								jj_la1[17] = jj_gen;
                    }
					break;
			case SERIAL:
					jj_consume_token(SERIAL);
					name = token.image;
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case IDENTIFIER:
									case NAMECHAR:
									case OPERATION:
											arg1 = namespace();
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														arg2 = description(postTags);
														break;
												default:
														jj_la1[18] = jj_gen;
                                            }
											break;
									case ASTERISK:
									case WORD:
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case WORD:
														jj_consume_token(WORD);
														break;
												case ASTERISK:
														jj_consume_token(ASTERISK);
														arg1 = token.image;
														break;
												default:
														jj_la1[19] = jj_gen;
														jj_consume_token(-1);
														throw new ParseException();
											}
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														if (aux != null) {
															arg1 += " " + aux;
														}
														break;
												default:
														jj_la1[20] = jj_gen;
                                            }
											break;
									case OPENBRACE:
											jj_consume_token(OPENBRACE);
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case LINKPLAIN:
												case LINK:
												case VALUE:
												case INHERITDOC:
												case DOCROOT:
												case CODE:
												case LITERAL:
														arg1 = inlines(postTags);
														break;
												default:
														jj_la1[21] = jj_gen;
                                            }
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														break;
												default:
														jj_la1[22] = jj_gen;
                                            }
											if (aux != null && arg1 != null) {
												arg1 += " " + aux;
											}
											break;
									case PARAM:
									case RETURN:
									case THROWS:
									case EXCEPTION:
									case SEE:
									case SERIALFIELD:
									case SERIALDATA:
									case SERIAL:
									case DEPRECATED:
									case AUTHOR:
									case SINCE:
									case VERSION:
											blocks(postTags);
											break;
									default:
											jj_la1[23] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								break;
						default:
								jj_la1[24] = jj_gen;
                    }
					break;
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
					name = IDBlockTag();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case IDENTIFIER:
											jj_consume_token(IDENTIFIER);
											arg1 = token.image;
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														arg2 = description(postTags);
														break;
												default:
														jj_la1[25] = jj_gen;
                                            }
											break;
									case ASTERISK:
									case NAMECHAR:
									case OPERATION:
									case WORD:
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case WORD:
														jj_consume_token(WORD);
														break;
												case NAMECHAR:
														jj_consume_token(NAMECHAR);
														break;
												case ASTERISK:
														jj_consume_token(ASTERISK);
														break;
												case OPERATION:
														jj_consume_token(OPERATION);
														break;
												default:
														jj_la1[26] = jj_gen;
														jj_consume_token(-1);
														throw new ParseException();
											}
											arg1 = token.image;
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														if (aux != null) {
															arg1 = arg1 + " " + aux;
														}
														break;
												default:
														jj_la1[27] = jj_gen;
                                            }
											break;
									case OPENBRACE:
											jj_consume_token(OPENBRACE);
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case LINKPLAIN:
												case LINK:
												case VALUE:
												case INHERITDOC:
												case DOCROOT:
												case CODE:
												case LITERAL:
														arg1 = inlines(postTags);
														break;
												default:
														jj_la1[28] = jj_gen;
                                            }
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														break;
												default:
														jj_la1[29] = jj_gen;
                                            }
											if (arg1 != null && aux != null) {
												arg1 = arg1 + " " + aux;
											} else if (arg1 == null) {
												arg1 = aux;
											}
											break;
									case PARAM:
									case RETURN:
									case THROWS:
									case EXCEPTION:
									case SEE:
									case SERIALFIELD:
									case SERIALDATA:
									case SERIAL:
									case DEPRECATED:
									case AUTHOR:
									case SINCE:
									case VERSION:
											blocks(postTags);
											break;
									default:
											jj_la1[30] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								break;
						default:
								jj_la1[31] = jj_gen;
                    }
					break;
			case SERIALFIELD:
					jj_consume_token(SERIALFIELD);
					name = token.image;
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case IDENTIFIER:
						case NAMECHAR:
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case NAMECHAR:
											jj_consume_token(NAMECHAR);
											arg1 = token.image;
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
															case IDENTIFIER:
																	jj_consume_token(IDENTIFIER);
																	arg2 = token.image;
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				arg3 = description(postTags);
																				break;
																		default:
																				jj_la1[32] = jj_gen;
                                                                    }
																	break;
															case ASTERISK:
															case NAMECHAR:
															case OPERATION:
															case WORD:
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case WORD:
																				jj_consume_token(WORD);
																				break;
																		case NAMECHAR:
																				jj_consume_token(NAMECHAR);
																				break;
																		case ASTERISK:
																				jj_consume_token(ASTERISK);
																				break;
																		case OPERATION:
																				jj_consume_token(OPERATION);
																				break;
																		default:
																				jj_la1[33] = jj_gen;
																				jj_consume_token(-1);
																				throw new ParseException();
																	}
																	arg2 = token.image;
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				aux = description(postTags);
																				if (aux != null) {
																					arg2 = arg2 + " " + aux;
																				}
																				break;
																		default:
																				jj_la1[34] = jj_gen;
                                                                    }
																	break;
															case OPENBRACE:
																	jj_consume_token(OPENBRACE);
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case LINKPLAIN:
																		case LINK:
																		case VALUE:
																		case INHERITDOC:
																		case DOCROOT:
																		case CODE:
																		case LITERAL:
																				inlines(postTags);
																				break;
																		default:
																				jj_la1[35] = jj_gen;
                                                                    }
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				description(postTags);
																				break;
																		default:
																				jj_la1[36] = jj_gen;
                                                                    }
																	break;
															case PARAM:
															case RETURN:
															case THROWS:
															case EXCEPTION:
															case SEE:
															case SERIALFIELD:
															case SERIALDATA:
															case SERIAL:
															case DEPRECATED:
															case AUTHOR:
															case SINCE:
															case VERSION:
																	blocks(postTags);
																	break;
															default:
																	jj_la1[37] = jj_gen;
																	jj_consume_token(-1);
																	throw new ParseException();
														}
														break;
												default:
														jj_la1[38] = jj_gen;
                                            }
											break;
									case IDENTIFIER:
											jj_consume_token(IDENTIFIER);
											arg1 = token.image;
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
															case IDENTIFIER:
																	jj_consume_token(IDENTIFIER);
																	arg2 = token.image;
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				arg3 = description(postTags);
																				break;
																		default:
																				jj_la1[39] = jj_gen;
                                                                    }
																	break;
															case ASTERISK:
															case NAMECHAR:
															case OPERATION:
															case WORD:
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case WORD:
																				jj_consume_token(WORD);
																				break;
																		case NAMECHAR:
																				jj_consume_token(NAMECHAR);
																				break;
																		case ASTERISK:
																				jj_consume_token(ASTERISK);
																				break;
																		case OPERATION:
																				jj_consume_token(OPERATION);
																				break;
																		default:
																				jj_la1[40] = jj_gen;
																				jj_consume_token(-1);
																				throw new ParseException();
																	}
																	arg2 = token.image;
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				aux = description(postTags);
																				if (aux != null) {
																					arg2 = arg2 + " " + aux;
																				}
																				break;
																		default:
																				jj_la1[41] = jj_gen;
                                                                    }
																	break;
															case OPENBRACE:
																	jj_consume_token(OPENBRACE);
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case LINKPLAIN:
																		case LINK:
																		case VALUE:
																		case INHERITDOC:
																		case DOCROOT:
																		case CODE:
																		case LITERAL:
																				arg2 = inlines(postTags);
																				break;
																		default:
																				jj_la1[42] = jj_gen;
                                                                    }
																	switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
																		case PARAM:
																		case RETURN:
																		case THROWS:
																		case EXCEPTION:
																		case SEE:
																		case SERIALFIELD:
																		case SERIALDATA:
																		case SERIAL:
																		case DEPRECATED:
																		case AUTHOR:
																		case SINCE:
																		case VERSION:
																		case OPENBRACE:
																		case CLOSEBRACE:
																		case ASTERISK:
																		case IDENTIFIER:
																		case NAMECHAR:
																		case OPERATION:
																		case WORD:
																				aux = description(postTags);
																				break;
																		default:
																				jj_la1[43] = jj_gen;
                                                                    }
																	if (arg2 != null && aux != null) {
																		arg2 = arg2 + " " + aux;
																	} else if (arg2 == null) {
																		arg2 = aux;
																	}
																	break;
															case PARAM:
															case RETURN:
															case THROWS:
															case EXCEPTION:
															case SEE:
															case SERIALFIELD:
															case SERIALDATA:
															case SERIAL:
															case DEPRECATED:
															case AUTHOR:
															case SINCE:
															case VERSION:
																	blocks(postTags);
																	break;
															default:
																	jj_la1[44] = jj_gen;
																	jj_consume_token(-1);
																	throw new ParseException();
														}
														break;
												default:
														jj_la1[45] = jj_gen;
                                            }
											break;
									case OPENBRACE:
											jj_consume_token(OPENBRACE);
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case LINKPLAIN:
												case LINK:
												case VALUE:
												case INHERITDOC:
												case DOCROOT:
												case CODE:
												case LITERAL:
														inlines(postTags);
														break;
												default:
														jj_la1[46] = jj_gen;
                                            }
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														description(postTags);
														break;
												default:
														jj_la1[47] = jj_gen;
                                            }
											break;
									case PARAM:
									case RETURN:
									case THROWS:
									case EXCEPTION:
									case SEE:
									case SERIALFIELD:
									case SERIALDATA:
									case SERIAL:
									case DEPRECATED:
									case AUTHOR:
									case SINCE:
									case VERSION:
											blocks(postTags);
											break;
									default:
											jj_la1[48] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								break;
						default:
								jj_la1[49] = jj_gen;
                    }
					break;
			case SEE:
					jj_consume_token(SEE);
					name = token.image;
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SEE:
						case SERIALFIELD:
						case SERIALDATA:
						case SERIAL:
						case DEPRECATED:
						case AUTHOR:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case CLOSEBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case IDENTIFIER:
									case NAMECHAR:
									case OPERATION:
											arg1 = namespace();
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														description(postTags);
														break;
												default:
														jj_la1[50] = jj_gen;
                                            }
											break;
									case CLOSEBRACE:
									case ASTERISK:
									case WORD:
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case WORD:
														jj_consume_token(WORD);
														break;
												case ASTERISK:
														jj_consume_token(ASTERISK);
														break;
												case CLOSEBRACE:
														jj_consume_token(CLOSEBRACE);
														break;
												default:
														jj_la1[51] = jj_gen;
														jj_consume_token(-1);
														throw new ParseException();
											}
											arg2 = token.image;
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														if (!"".equals(aux)) {
															arg2 = arg2 + " " + aux;
														}
														break;
												default:
														jj_la1[52] = jj_gen;
                                            }
											break;
									case OPENBRACE:
											jj_consume_token(OPENBRACE);
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case LINKPLAIN:
												case LINK:
												case VALUE:
												case INHERITDOC:
												case DOCROOT:
												case CODE:
												case LITERAL:
														arg2 = inlines(postTags);
														if (arg2 != null) {
															arg2 = "{ " + arg2;
														} else {
															arg2 = "{ ";
														}
														break;
												default:
														jj_la1[53] = jj_gen;
                                            }
											switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
												case PARAM:
												case RETURN:
												case THROWS:
												case EXCEPTION:
												case SEE:
												case SERIALFIELD:
												case SERIALDATA:
												case SERIAL:
												case DEPRECATED:
												case AUTHOR:
												case SINCE:
												case VERSION:
												case OPENBRACE:
												case CLOSEBRACE:
												case ASTERISK:
												case IDENTIFIER:
												case NAMECHAR:
												case OPERATION:
												case WORD:
														aux = description(postTags);
														if (aux != null) {
															arg2 = arg2 + " " + aux;
														}
														break;
												default:
														jj_la1[54] = jj_gen;
                                            }
											break;
									case PARAM:
									case RETURN:
									case THROWS:
									case EXCEPTION:
									case SEE:
									case SERIALFIELD:
									case SERIALDATA:
									case SERIAL:
									case DEPRECATED:
									case AUTHOR:
									case SINCE:
									case VERSION:
											blocks(postTags);
											break;
									default:
											jj_la1[55] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								break;
						default:
								jj_la1[56] = jj_gen;
                    }
					break;
			default:
					jj_la1[57] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		if (arg1 != null || arg2 != null || arg3 != null) {
			values = new LinkedList<String>();
			if (arg1 != null) {
				values.add(arg1);
			}
			if (arg2 != null) {
				values.add(arg2);
			}
			if (arg3 != null) {
				values.add(arg3);
			}
		}

		tag = new JavadocTag(name, values, false);
		tags.add(tag);
		if (tag2 != null) {
			tags.add(tag2);
		}
		tags.addAll(postTags);
	}

	public final String inlineDescription() throws ParseException {
		String result = "";
		String w = "";
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LINKPLAIN:
			case LINK:
			case VALUE:
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SERIALDATA:
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case DEPRECATED:
			case AUTHOR:
			case LITERAL:
			case SINCE:
			case VERSION:
			case ASTERISK:
			case IDENTIFIER:
			case NAMECHAR:
			case OPERATION:
			case WORD:
					result = textDescription();
					break;
			case OPENBRACE:
					jj_consume_token(OPENBRACE);
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case LINKPLAIN:
						case LINK:
						case VALUE:
						case PARAM:
						case RETURN:
						case THROWS:
						case EXCEPTION:
						case SERIALDATA:
						case INHERITDOC:
						case DOCROOT:
						case CODE:
						case DEPRECATED:
						case AUTHOR:
						case LITERAL:
						case SINCE:
						case VERSION:
						case OPENBRACE:
						case ASTERISK:
						case IDENTIFIER:
						case NAMECHAR:
						case OPERATION:
						case WORD:
								result = inlineDescription();
								break;
						default:
								jj_la1[58] = jj_gen;
                    }
					jj_consume_token(CLOSEBRACE);
					result = "{ " + result + " }";
					break;
			default:
					jj_la1[59] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LINKPLAIN:
			case LINK:
			case VALUE:
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SERIALDATA:
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case DEPRECATED:
			case AUTHOR:
			case LITERAL:
			case SINCE:
			case VERSION:
			case OPENBRACE:
			case ASTERISK:
			case IDENTIFIER:
			case NAMECHAR:
			case OPERATION:
			case WORD:
					w = inlineDescription();
					if (!"".equals(w)) {
						result = result + " " + w;
					}
					break;
			default:
					jj_la1[60] = jj_gen;
        }
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public final String textDescription() throws ParseException {
		String result = "";
		String w = "";
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case WORD:
					jj_consume_token(WORD);
					result = token.image;
					break;
			case NAMECHAR:
					jj_consume_token(NAMECHAR);
					result = token.image;
					break;
			case IDENTIFIER:
					jj_consume_token(IDENTIFIER);
					result = token.image;
					break;
			case ASTERISK:
					jj_consume_token(ASTERISK);
					result = token.image;
					break;
			case OPERATION:
					jj_consume_token(OPERATION);
					result = token.image;
					break;
			case LINKPLAIN:
			case LINK:
			case VALUE:
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SERIALDATA:
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case DEPRECATED:
			case AUTHOR:
			case LITERAL:
			case SINCE:
			case VERSION:
					result = anyTag();
					break;
			default:
					jj_la1[61] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public final String NSInlineDescription() throws ParseException {
		String result = "";
		String w = "";
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case WORD:
					jj_consume_token(WORD);
					result = token.image;
					break;
			case ASTERISK:
					jj_consume_token(ASTERISK);
					result = token.image;
					break;
			case OPENBRACE:
					jj_consume_token(OPENBRACE);
					result = token.image;
					break;
			default:
					jj_la1[62] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LINKPLAIN:
			case LINK:
			case VALUE:
			case PARAM:
			case RETURN:
			case THROWS:
			case EXCEPTION:
			case SERIALDATA:
			case INHERITDOC:
			case DOCROOT:
			case CODE:
			case DEPRECATED:
			case AUTHOR:
			case LITERAL:
			case SINCE:
			case VERSION:
			case OPENBRACE:
			case ASTERISK:
			case IDENTIFIER:
			case NAMECHAR:
			case OPERATION:
			case WORD:
					w = inlineDescription();
					if (!"".equals(w)) {
						result = result + " " + w;
					}
					break;
			default:
					jj_la1[63] = jj_gen;
        }
		{
			if (true) return result;
		}
		throw new Error("Missing return statement in function");
	}

	public TagsParserTokenManager token_source;
	JavaCharStream jj_input_stream;
	public Token token;
	public Token jj_nt;
	private int jj_ntk;
	private int jj_gen;
	private final int[] jj_la1 = new int[64];
	private static int[] jj_la1_0;
	static {
		jj_la1_init_0();
	}

	private static void jj_la1_init_0() {
		jj_la1_0 = new int[] { 0x1cfd8ff0, 0x27000, 0xd8400, 0xf0, 0xe, 0xff4fe, 0x2700e, 0x1cfd8ff0, 0x1ce00000, 0x1cfd8ff0, 0x1cfd8ff0, 0x1cdff4fe, 0x1cdff4fe, 0x1cd00000, 0x1cd00000, 0x2700e, 0xc800000, 0x1cfd8ff0, 0x1cfd8ff0, 0x10400000, 0x1cfd8ff0, 0x2700e, 0x1cfd8ff0, 0x1cdd8ff0, 0x1cdd8ff0, 0x1cfd8ff0, 0x1c400000, 0x1cfd8ff0, 0x2700e, 0x1cfd8ff0, 0x1cdd8ff0, 0x1cdd8ff0, 0x1cfd8ff0, 0x1c400000, 0x1cfd8ff0, 0x2700e, 0x1cfd8ff0, 0x1cdd8ff0, 0x1cdd8ff0, 0x1cfd8ff0, 0x1c400000, 0x1cfd8ff0, 0x2700e, 0x1cfd8ff0, 0x1cdd8ff0, 0x1cdd8ff0, 0x2700e, 0x1cfd8ff0, 0x49d8ff0, 0x49d8ff0, 0x1cfd8ff0, 0x10600000, 0x1cfd8ff0, 0x2700e, 0x1cfd8ff0, 0x1cfd8ff0, 0x1cfd8ff0, 0xd8ff0, 0x1cdff4fe, 0x1cdff4fe, 0x1cdff4fe, 0x1ccff4fe, 0x10500000, 0x1cdff4fe };
	}

	public TagsParser(java.io.InputStream stream) {
		this(stream, null);
	}

	public TagsParser(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source = new TagsParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	public void ReInit(java.io.InputStream stream) {
		ReInit(stream, null);
	}

	public void ReInit(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	public TagsParser(java.io.Reader stream) {
		jj_input_stream = new JavaCharStream(stream, 1, 1);
		token_source = new TagsParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	public TagsParser(TagsParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	public void ReInit(TagsParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 64; i++) jj_la1[i] = -1;
	}

	private Token jj_consume_token(int kind) throws ParseException {
		Token oldToken;
		if ((oldToken = token).next != null) token = token.next;

		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind) {
			jj_gen++;
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	public final Token getNextToken() {
		if (token.next != null) token = token.next;

		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	public final Token getToken(int index) {
		Token t = token;
		for (int i = 0; i < index; i++) {
			if (t.next != null) t = t.next;

			else t = t.next = token_source.getNextToken();
		}
		return t;
	}

	private int jj_ntk() {
		if ((jj_nt = token.next) == null) return (jj_ntk = (token.next = token_source.getNextToken()).kind);

		else return (jj_ntk = jj_nt.kind);
	}

	private final java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
	private int[] jj_expentry;
	private int jj_kind = -1;

	public ParseException generateParseException() {
		jj_expentries.clear();
		boolean[] la1tokens = new boolean[31];
		if (jj_kind >= 0) {
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 64; i++) {
			if (jj_la1[i] == jj_gen) {
				for (int j = 0; j < 32; j++) {
					if ((jj_la1_0[i] & (1 << j)) != 0) {
						la1tokens[j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 31; i++) {
			if (la1tokens[i]) {
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.add(jj_expentry);
			}
		}
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++) {
			exptokseq[i] = jj_expentries.get(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	public final void enable_tracing() {}

	public final void disable_tracing() {}

}
