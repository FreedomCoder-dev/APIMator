package org.walkmod.javalang.tags;

public class TagsParserTokenManager implements TagsParserConstants {

	public java.io.PrintStream debugStream = System.out;

	public void setDebugStream(java.io.PrintStream ds) {
		debugStream = ds;
	}

	private final int jjStopStringLiteralDfa_0(int pos, long active0) {
		switch(pos) {
			case 0:
					if ((active0 & 0xffffeL) != 0L) {
						jjmatchedKind = 28;
						return 0;
					}
					return -1;
			case 1:
					if ((active0 & 0xffffeL) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 1;
						return 0;
					}
					return -1;
			case 2:
					if ((active0 & 0xffffeL) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 2;
						return 0;
					}
					return -1;
			case 3:
					if ((active0 & 0x100L) != 0L) return 0;
					if ((active0 & 0xffefeL) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 3;
						return 0;
					}
					return -1;
			case 4:
					if ((active0 & 0x4006L) != 0L) return 0;
					if ((active0 & 0xfbef8L) != 0L) {
						if (jjmatchedPos != 4) {
							jjmatchedKind = 28;
							jjmatchedPos = 4;
						}
						return 0;
					}
					return -1;
			case 5:
					if ((active0 & 0x40018L) != 0L) return 0;
					if ((active0 & 0xbbee2L) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 5;
						return 0;
					}
					return -1;
			case 6:
					if ((active0 & 0x10e60L) != 0L) return 0;
					if ((active0 & 0xab082L) != 0L) {
						if (jjmatchedPos != 6) {
							jjmatchedKind = 28;
							jjmatchedPos = 6;
						}
						return 0;
					}
					return -1;
			case 7:
					if ((active0 & 0xa2000L) != 0L) return 0;
					if ((active0 & 0x9682L) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 7;
						return 0;
					}
					return -1;
			case 8:
					if ((active0 & 0x9682L) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 8;
						return 0;
					}
					return -1;
			case 9:
					if ((active0 & 0x82L) != 0L) return 0;
					if ((active0 & 0x9600L) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 9;
						return 0;
					}
					return -1;
			case 10:
					if ((active0 & 0x9400L) != 0L) return 0;
					if ((active0 & 0x200L) != 0L) {
						jjmatchedKind = 28;
						jjmatchedPos = 10;
						return 0;
					}
					return -1;
			default:
					return -1;
		}
	}

	private final int jjStartNfa_0(int pos, long active0) {
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}

	private int jjStopAtPos(int pos, int kind) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private int jjMoveStringLiteralDfa0_0() {
		switch(curChar) {
			case 42:
					return jjStopAtPos(0, 22);
			case 64:
					return jjMoveStringLiteralDfa1_0(0xffffeL);
			case 123:
					return jjStopAtPos(0, 20);
			case 125:
					return jjStopAtPos(0, 21);
			default:
					return jjMoveNfa_0(1, 0);
		}
	}

	private int jjMoveStringLiteralDfa1_0(long active0) {
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}
		switch(curChar) {
			case 97:
					return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
			case 99:
					return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
			case 100:
					return jjMoveStringLiteralDfa2_0(active0, 0xa000L);
			case 101:
					return jjMoveStringLiteralDfa2_0(active0, 0x80L);
			case 105:
					return jjMoveStringLiteralDfa2_0(active0, 0x1000L);
			case 108:
					return jjMoveStringLiteralDfa2_0(active0, 0x20006L);
			case 112:
					return jjMoveStringLiteralDfa2_0(active0, 0x10L);
			case 114:
					return jjMoveStringLiteralDfa2_0(active0, 0x20L);
			case 115:
					return jjMoveStringLiteralDfa2_0(active0, 0x40f00L);
			case 116:
					return jjMoveStringLiteralDfa2_0(active0, 0x40L);
			case 118:
					return jjMoveStringLiteralDfa2_0(active0, 0x80008L);
			default:
					break;
		}
		return jjStartNfa_0(0, active0);
	}

	private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(0, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(1, active0);
			return 2;
		}
		switch(curChar) {
			case 97:
					return jjMoveStringLiteralDfa3_0(active0, 0x18L);
			case 101:
					return jjMoveStringLiteralDfa3_0(active0, 0x88f20L);
			case 104:
					return jjMoveStringLiteralDfa3_0(active0, 0x40L);
			case 105:
					return jjMoveStringLiteralDfa3_0(active0, 0x60006L);
			case 110:
					return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
			case 111:
					return jjMoveStringLiteralDfa3_0(active0, 0x6000L);
			case 117:
					return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
			case 120:
					return jjMoveStringLiteralDfa3_0(active0, 0x80L);
			default:
					break;
		}
		return jjStartNfa_0(1, active0);
	}

	private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(1, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(2, active0);
			return 3;
		}
		switch(curChar) {
			case 99:
					return jjMoveStringLiteralDfa4_0(active0, 0x2080L);
			case 100:
					return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
			case 101:
					if ((active0 & 0x100L) != 0L) return jjStartNfaWithStates_0(3, 8, 0);
					break;
			case 104:
					return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
			case 108:
					return jjMoveStringLiteralDfa4_0(active0, 0x8L);
			case 110:
					return jjMoveStringLiteralDfa4_0(active0, 0x40006L);
			case 112:
					return jjMoveStringLiteralDfa4_0(active0, 0x8000L);
			case 114:
					return jjMoveStringLiteralDfa4_0(active0, 0x80e50L);
			case 116:
					return jjMoveStringLiteralDfa4_0(active0, 0x30020L);
			default:
					break;
		}
		return jjStartNfa_0(2, active0);
	}

	private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(2, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(3, active0);
			return 4;
		}
		switch(curChar) {
			case 82:
					return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
			case 97:
					return jjMoveStringLiteralDfa5_0(active0, 0x10L);
			case 99:
					return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
			case 101:
					if ((active0 & 0x4000L) != 0L) return jjStartNfaWithStates_0(4, 14, 0);
					return jjMoveStringLiteralDfa5_0(active0, 0x21080L);
			case 104:
					return jjMoveStringLiteralDfa5_0(active0, 0x10000L);
			case 105:
					return jjMoveStringLiteralDfa5_0(active0, 0xe00L);
			case 107:
					if ((active0 & 0x4L) != 0L) {
						jjmatchedKind = 2;
						jjmatchedPos = 4;
					}
					return jjMoveStringLiteralDfa5_0(active0, 0x2L);
			case 111:
					return jjMoveStringLiteralDfa5_0(active0, 0x40L);
			case 114:
					return jjMoveStringLiteralDfa5_0(active0, 0x8000L);
			case 115:
					return jjMoveStringLiteralDfa5_0(active0, 0x80000L);
			case 117:
					return jjMoveStringLiteralDfa5_0(active0, 0x28L);
			default:
					break;
		}
		return jjStartNfa_0(3, active0);
	}

	private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(3, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(4, active0);
			return 5;
		}
		switch(curChar) {
			case 97:
					return jjMoveStringLiteralDfa6_0(active0, 0xe00L);
			case 101:
					if ((active0 & 0x8L) != 0L) return jjStartNfaWithStates_0(5, 3, 0);
					else if ((active0 & 0x40000L) != 0L) return jjStartNfaWithStates_0(5, 18, 0);
					return jjMoveStringLiteralDfa6_0(active0, 0x8000L);
			case 105:
					return jjMoveStringLiteralDfa6_0(active0, 0x80000L);
			case 109:
					if ((active0 & 0x10L) != 0L) return jjStartNfaWithStates_0(5, 4, 0);
					break;
			case 111:
					return jjMoveStringLiteralDfa6_0(active0, 0x12000L);
			case 112:
					return jjMoveStringLiteralDfa6_0(active0, 0x82L);
			case 114:
					return jjMoveStringLiteralDfa6_0(active0, 0x21020L);
			case 119:
					return jjMoveStringLiteralDfa6_0(active0, 0x40L);
			default:
					break;
		}
		return jjStartNfa_0(4, active0);
	}

	private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(4, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(5, active0);
			return 6;
		}
		switch(curChar) {
			case 97:
					return jjMoveStringLiteralDfa7_0(active0, 0x20000L);
			case 99:
					return jjMoveStringLiteralDfa7_0(active0, 0x8000L);
			case 105:
					return jjMoveStringLiteralDfa7_0(active0, 0x1000L);
			case 108:
					if ((active0 & 0x800L) != 0L) {
						jjmatchedKind = 11;
						jjmatchedPos = 6;
					}
					return jjMoveStringLiteralDfa7_0(active0, 0x602L);
			case 110:
					if ((active0 & 0x20L) != 0L) return jjStartNfaWithStates_0(6, 5, 0);
					break;
			case 111:
					return jjMoveStringLiteralDfa7_0(active0, 0x82000L);
			case 114:
					if ((active0 & 0x10000L) != 0L) return jjStartNfaWithStates_0(6, 16, 0);
					break;
			case 115:
					if ((active0 & 0x40L) != 0L) return jjStartNfaWithStates_0(6, 6, 0);
					break;
			case 116:
					return jjMoveStringLiteralDfa7_0(active0, 0x80L);
			default:
					break;
		}
		return jjStartNfa_0(5, active0);
	}

	private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(5, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(6, active0);
			return 7;
		}
		switch(curChar) {
			case 68:
					return jjMoveStringLiteralDfa8_0(active0, 0x400L);
			case 70:
					return jjMoveStringLiteralDfa8_0(active0, 0x200L);
			case 97:
					return jjMoveStringLiteralDfa8_0(active0, 0x8002L);
			case 105:
					return jjMoveStringLiteralDfa8_0(active0, 0x80L);
			case 108:
					if ((active0 & 0x20000L) != 0L) return jjStartNfaWithStates_0(7, 17, 0);
					break;
			case 110:
					if ((active0 & 0x80000L) != 0L) return jjStartNfaWithStates_0(7, 19, 0);
					break;
			case 116:
					if ((active0 & 0x2000L) != 0L) return jjStartNfaWithStates_0(7, 13, 0);
					return jjMoveStringLiteralDfa8_0(active0, 0x1000L);
			default:
					break;
		}
		return jjStartNfa_0(6, active0);
	}

	private int jjMoveStringLiteralDfa8_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(6, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(7, active0);
			return 8;
		}
		switch(curChar) {
			case 68:
					return jjMoveStringLiteralDfa9_0(active0, 0x1000L);
			case 97:
					return jjMoveStringLiteralDfa9_0(active0, 0x400L);
			case 105:
					return jjMoveStringLiteralDfa9_0(active0, 0x202L);
			case 111:
					return jjMoveStringLiteralDfa9_0(active0, 0x80L);
			case 116:
					return jjMoveStringLiteralDfa9_0(active0, 0x8000L);
			default:
					break;
		}
		return jjStartNfa_0(7, active0);
	}

	private int jjMoveStringLiteralDfa9_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(7, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(8, active0);
			return 9;
		}
		switch(curChar) {
			case 101:
					return jjMoveStringLiteralDfa10_0(active0, 0x8200L);
			case 110:
					if ((active0 & 0x2L) != 0L) return jjStartNfaWithStates_0(9, 1, 0);
					else if ((active0 & 0x80L) != 0L) return jjStartNfaWithStates_0(9, 7, 0);
					break;
			case 111:
					return jjMoveStringLiteralDfa10_0(active0, 0x1000L);
			case 116:
					return jjMoveStringLiteralDfa10_0(active0, 0x400L);
			default:
					break;
		}
		return jjStartNfa_0(8, active0);
	}

	private int jjMoveStringLiteralDfa10_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(8, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(9, active0);
			return 10;
		}
		switch(curChar) {
			case 97:
					if ((active0 & 0x400L) != 0L) return jjStartNfaWithStates_0(10, 10, 0);
					break;
			case 99:
					if ((active0 & 0x1000L) != 0L) return jjStartNfaWithStates_0(10, 12, 0);
					break;
			case 100:
					if ((active0 & 0x8000L) != 0L) return jjStartNfaWithStates_0(10, 15, 0);
					break;
			case 108:
					return jjMoveStringLiteralDfa11_0(active0, 0x200L);
			default:
					break;
		}
		return jjStartNfa_0(9, active0);
	}

	private int jjMoveStringLiteralDfa11_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L) return jjStartNfa_0(9, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(10, active0);
			return 11;
		}
		switch(curChar) {
			case 100:
					if ((active0 & 0x200L) != 0L) return jjStartNfaWithStates_0(11, 9, 0);
					break;
			default:
					break;
		}
		return jjStartNfa_0(10, active0);
	}

	private int jjStartNfaWithStates_0(int pos, int kind, int state) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			return pos + 1;
		}
		return jjMoveNfa_0(state, pos + 1);
	}

	static final long[] jjbitVec0 = { 0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL };
	static final long[] jjbitVec2 = { 0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL };
	static final long[] jjbitVec3 = { 0xfff0000000200002L, 0xffffffffffffdfffL, 0xfffff00f7fffffffL, 0x12000000007fffffL };
	static final long[] jjbitVec4 = { 0x0L, 0x0L, 0x420043c00000000L, 0xff7fffffff7fffffL };
	static final long[] jjbitVec5 = { 0x7fffffffffffffL, 0xffffffffffff0000L, 0xffffffffffffffffL, 0x401f0003ffc3L };
	static final long[] jjbitVec6 = { 0x0L, 0x400000000000000L, 0xfffffffbffffd740L, 0xfbfffffffff7fffL };
	static final long[] jjbitVec7 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0xfffffffffffffc03L, 0x33fffffffff7fffL };
	static final long[] jjbitVec8 = { 0xfffe00000000ffffL, 0xfffffffe027fffffL, 0xffL, 0x707ffffff0000L };
	static final long[] jjbitVec9 = { 0x7fffffe00000000L, 0xfffec000000007ffL, 0xffffffffffffffffL, 0x9c00c060002fffffL };
	static final long[] jjbitVec10 = { 0xfffffffd0000L, 0xe000L, 0x2003fffffffffL, 0x0L };
	static final long[] jjbitVec11 = { 0x23fffffffffffff0L, 0x3ff010000L, 0x23c5fdfffff99fe0L, 0xf0003b0000000L };
	static final long[] jjbitVec12 = { 0x36dfdfffff987e0L, 0x1c00005e000000L, 0x23edfdfffffbbfe0L, 0x2000300010000L };
	static final long[] jjbitVec13 = { 0x23edfdfffff99fe0L, 0x20003b0000000L, 0x3bfc718d63dc7e8L, 0x200000000000000L };
	static final long[] jjbitVec14 = { 0x3effdfffffddfe0L, 0x300000000L, 0x23effdfffffddfe0L, 0x340000000L };
	static final long[] jjbitVec15 = { 0x3fffdfffffddfe0L, 0x300000000L, 0x2ffbfffffc7fffe0L, 0x7fL };
	static final long[] jjbitVec16 = { 0x800dfffffffffffeL, 0x7fL, 0x200decaefef02596L, 0x3000005fL };
	static final long[] jjbitVec17 = { 0x1L, 0x7fffffffeffL, 0xf00L, 0x0L };
	static final long[] jjbitVec18 = { 0x6fbffffffffL, 0x3f0000L, 0xffffffff00000000L, 0x1ffffffffff003fL };
	static final long[] jjbitVec19 = { 0xffffffffffffffffL, 0xffffffff83ffffffL, 0xffffff07ffffffffL, 0x3ffffffffffffffL };
	static final long[] jjbitVec20 = { 0xffffffffffffff7fL, 0xffffffff3d7f3d7fL, 0x7f3d7fffffff3d7fL, 0xffff7fffff7f7f3dL };
	static final long[] jjbitVec21 = { 0xffffffff7f3d7fffL, 0x7ffff7fL, 0xffffffff00000000L, 0x1fffffffffffffL };
	static final long[] jjbitVec22 = { 0xffffffffffffffffL, 0x7f9fffffffffffL, 0xffffffff07fffffeL, 0x1c7ffffffffffL };
	static final long[] jjbitVec23 = { 0x3ffff0003dfffL, 0x1dfff0003ffffL, 0xfffffffffffffL, 0x18800000L };
	static final long[] jjbitVec24 = { 0xffffffff00000000L, 0xffffffffffffffL, 0x1ffffffffffL, 0x0L };
	static final long[] jjbitVec25 = { 0x1fffffffL, 0x1f3fffffff0000L, 0x0L, 0x0L };
	static final long[] jjbitVec26 = { 0xffffffffffffffffL, 0xfffffffffffL, 0x0L, 0x0L };
	static final long[] jjbitVec27 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffff0fffffffL, 0x3ffffffffffffffL };
	static final long[] jjbitVec28 = { 0xffffffff3f3fffffL, 0x3fffffffaaff3f3fL, 0x5fdfffffffffffffL, 0x1fdc1fff0fcf1fdcL };
	static final long[] jjbitVec29 = { 0x8000000000000000L, 0x8002000000100001L, 0x3ffff00000000L, 0x0L };
	static final long[] jjbitVec30 = { 0xe3fbbd503e2ffc84L, 0xffffffff000003e0L, 0xfL, 0x0L };
	static final long[] jjbitVec31 = { 0x1f3e03fe000000e0L, 0xfffffffffffffffeL, 0xfffffffee07fffffL, 0xffffffffffffffffL };
	static final long[] jjbitVec32 = { 0xfffe1fffffffffe0L, 0xffffffffffffffffL, 0xffffff00007fffL, 0xffff000000000000L };
	static final long[] jjbitVec33 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0x3fffffffffffffL, 0x0L };
	static final long[] jjbitVec34 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0x3fffffffffL, 0x0L };
	static final long[] jjbitVec35 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0x1fffL, 0x0L };
	static final long[] jjbitVec36 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0xfffffffffL, 0x0L };
	static final long[] jjbitVec37 = { 0x6L, 0x0L, 0x0L, 0x0L };
	static final long[] jjbitVec38 = { 0xffff3fffffffffffL, 0x7ffffffffffL, 0x0L, 0x0L };
	static final long[] jjbitVec39 = { 0x5f7ffdffa0f8007fL, 0xffffffffffffffdbL, 0x3ffffffffffffL, 0xfffffffffff80000L };
	static final long[] jjbitVec40 = { 0x3fffffffffffffffL, 0xffffffffffff0000L, 0xfffffffffffcffffL, 0x1fff0000000000ffL };
	static final long[] jjbitVec41 = { 0x18000000000000L, 0xffdf02000000e000L, 0xffffffffffffffffL, 0x1fffffffffffffffL };
	static final long[] jjbitVec42 = { 0x87fffffe00000010L, 0xffffffe007fffffeL, 0x7fffffffffffffffL, 0x631cfcfcfcL };
	static final long[] jjbitVec43 = { 0x0L, 0x0L, 0x420243cffffffffL, 0xff7fffffff7fffffL };
	static final long[] jjbitVec44 = { 0xffffffffffffffffL, 0x400ffffe0ffffffL, 0xfffffffbffffd740L, 0xfbfffffffff7fffL };
	static final long[] jjbitVec45 = { 0xffffffffffffffffL, 0xffffffffffffffffL, 0xfffffffffffffc7bL, 0x33fffffffff7fffL };
	static final long[] jjbitVec46 = { 0xfffe00000000ffffL, 0xfffffffe027fffffL, 0xbbfffffbfffe00ffL, 0x707ffffff0016L };
	static final long[] jjbitVec47 = { 0x7fffffe003f000fL, 0xffffc3ff01ffffffL, 0xffffffffffffffffL, 0x9ffffdffbfefffffL };
	static final long[] jjbitVec48 = { 0xffffffffffff8000L, 0xe7ffL, 0x3ffffffffffffL, 0x0L };
	static final long[] jjbitVec49 = { 0xf3fffffffffffffeL, 0xffcfff1f3fffL, 0xf3c5fdfffff99feeL, 0xfffcfb080399fL };
	static final long[] jjbitVec50 = { 0xd36dfdfffff987eeL, 0x1fffc05e003987L, 0xf3edfdfffffbbfeeL, 0x2ffcf00013bbfL };
	static final long[] jjbitVec51 = { 0xf3edfdfffff99feeL, 0x2ffc3b0c0398fL, 0xc3bfc718d63dc7ecL, 0x200ff8000803dc7L };
	static final long[] jjbitVec52 = { 0xc3effdfffffddfeeL, 0xffc300603ddfL, 0xf3effdfffffddfecL, 0xffc340603ddfL };
	static final long[] jjbitVec53 = { 0xc3fffdfffffddfecL, 0xffc300803dcfL, 0x2ffbfffffc7fffecL, 0xc0000ff5f847fL };
	static final long[] jjbitVec54 = { 0x87fffffffffffffeL, 0x3ff7fffL, 0x3bffecaefef02596L, 0x33ff3f5fL };
	static final long[] jjbitVec55 = { 0xc2a003ff03000001L, 0xfffe07fffffffeffL, 0x1ffffffffeff0fdfL, 0x40L };
	static final long[] jjbitVec56 = { 0x3c7f6fbffffffffL, 0x3ff03ffL, 0xffffffff00000000L, 0x1ffffffffff003fL };
	static final long[] jjbitVec57 = { 0xffffffff7f3d7fffL, 0x3fe0007ffff7fL, 0xffffffff00000000L, 0x1fffffffffffffL };
	static final long[] jjbitVec58 = { 0x1fffff001fdfffL, 0xddfff000fffffL, 0xffffffffffffffffL, 0x3ff388fffffL };
	static final long[] jjbitVec59 = { 0xffffffff03ff3800L, 0xffffffffffffffL, 0x3ffffffffffL, 0x0L };
	static final long[] jjbitVec60 = { 0xfff0fff1fffffffL, 0x1f3fffffffffc0L, 0x0L, 0x0L };
	static final long[] jjbitVec61 = { 0x80007c000000f000L, 0x8002fc0f00100001L, 0x3ffff00000000L, 0x7e21fff0000L };
	static final long[] jjbitVec62 = { 0x1f3efffe000000e0L, 0xfffffffffffffffeL, 0xfffffffee67fffffL, 0xffffffffffffffffL };
	static final long[] jjbitVec63 = { 0x10000000000006L, 0x0L, 0x0L, 0x0L };
	static final long[] jjbitVec64 = { 0x3L, 0x0L, 0x0L, 0x0L };
	static final long[] jjbitVec65 = { 0x0L, 0x800000000000000L, 0x0L, 0x0L };
	static final long[] jjbitVec66 = { 0x5f7ffdffe0f8007fL, 0xffffffffffffffdbL, 0x3ffffffffffffL, 0xfffffffffff80000L };
	static final long[] jjbitVec67 = { 0x18000f0000ffffL, 0xffdf02000000e000L, 0xffffffffffffffffL, 0x9fffffffffffffffL };
	static final long[] jjbitVec68 = { 0x87fffffe03ff0010L, 0xffffffe007fffffeL, 0x7fffffffffffffffL, 0xe0000631cfcfcfcL };

	private int jjMoveNfa_0(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 27;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (; ; ) {
			if (++jjround == 0x7fffffff) ReInitRounds();
			if (curChar < 64) {
				long l = 1L << curChar;
				do {
					switch(jjstateSet[--i]) {
						case 1:
								if ((0xfffffbfeffffd9ffL & l) != 0L) {
									if (kind > 28) kind = 28;
									jjCheckNAdd(0);
								} else if ((0x2400L & l) != 0L) {
									if (kind > 30) kind = 30;
									jjCheckNAddTwoStates(3, 4);
								} else if ((0x100000200L & l) != 0L) {
									if (kind > 29) kind = 29;
								}
								if ((0x400800000000L & l) != 0L) {
									if (kind > 26) kind = 26;
									jjCheckNAddStates(0, 5);
								} else if (curChar == 36) {
									if (kind > 23) kind = 23;
									jjCheckNAddStates(6, 14);
								}
								break;
						case 0:
								if ((0xfffffbfeffffd9ffL & l) == 0L) break;
								if (kind > 28) kind = 28;
								jjCheckNAdd(0);
								break;
						case 2:
								if ((0x2400L & l) == 0L) break;
								if (kind > 30) kind = 30;
								jjCheckNAddTwoStates(3, 4);
								break;
						case 3:
								if (curChar == 32) jjCheckNAddTwoStates(3, 4);
								break;
						case 4:
								if (curChar == 42) kind = 30;
								break;
						case 5:
								if (curChar != 36) break;
								if (kind > 23) kind = 23;
								jjCheckNAddStates(6, 14);
								break;
						case 6:
								if ((0x3ff00100fffc1ffL & l) == 0L) break;
								if (kind > 23) kind = 23;
								jjCheckNAdd(6);
								break;
						case 7:
								if (curChar != 36) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 8:
								if ((0x3ff00100fffc1ffL & l) == 0L) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 9:
								if ((0x400800000000L & l) == 0L) break;
								if (kind > 26) kind = 26;
								jjCheckNAddTwoStates(7, 9);
								break;
						case 10:
								if (curChar == 36) jjCheckNAddStates(18, 22);
								break;
						case 11:
								if ((0x3ff00100fffc1ffL & l) != 0L) jjCheckNAddStates(18, 22);
								break;
						case 12:
								if ((0x400800000000L & l) != 0L) jjCheckNAddStates(23, 26);
								break;
						case 13:
								if ((0x100002600L & l) != 0L) jjCheckNAddTwoStates(13, 14);
								break;
						case 14:
								if (curChar == 40) jjCheckNAddStates(27, 30);
								break;
						case 15:
								if ((0x100002600L & l) != 0L) jjCheckNAddStates(27, 30);
								break;
						case 16:
								if (curChar == 36) jjCheckNAddStates(31, 36);
								break;
						case 17:
								if ((0x3ff00100fffc1ffL & l) != 0L) jjCheckNAddStates(31, 36);
								break;
						case 18:
								if ((0x400800000000L & l) != 0L) jjCheckNAddStates(37, 41);
								break;
						case 19:
								if ((0x100002600L & l) != 0L) jjCheckNAddTwoStates(19, 20);
								break;
						case 20:
								if (curChar == 44) jjCheckNAddStates(42, 44);
								break;
						case 21:
								if ((0x100002600L & l) != 0L) jjCheckNAddStates(42, 44);
								break;
						case 22:
								if (curChar == 36) jjCheckNAddStates(45, 50);
								break;
						case 23:
								if ((0x3ff00100fffc1ffL & l) != 0L) jjCheckNAddStates(45, 50);
								break;
						case 24:
								if ((0x400800000000L & l) != 0L) jjCheckNAddStates(51, 55);
								break;
						case 25:
								if (curChar == 41 && kind > 27) kind = 27;
								break;
						case 26:
								if ((0x400800000000L & l) == 0L) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(0, 5);
								break;
						default:
								break;
					}
				} while (i != startsAt);
			} else if (curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch(jjstateSet[--i]) {
						case 1:
								if ((0xd7ffffffffffffffL & l) != 0L) {
									if (kind > 28) kind = 28;
									jjCheckNAdd(0);
								}
								if ((0x7fffffe87fffffeL & l) != 0L) {
									if (kind > 23) kind = 23;
									jjCheckNAddStates(6, 14);
								}
								break;
						case 0:
								if ((0xd7ffffffffffffffL & l) == 0L) break;
								if (kind > 28) kind = 28;
								jjCheckNAdd(0);
								break;
						case 5:
								if ((0x7fffffe87fffffeL & l) == 0L) break;
								if (kind > 23) kind = 23;
								jjCheckNAddStates(6, 14);
								break;
						case 6:
								if ((0x87fffffeaffffffeL & l) == 0L) break;
								if (kind > 23) kind = 23;
								jjCheckNAdd(6);
								break;
						case 7:
								if ((0x7fffffe87fffffeL & l) == 0L) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 8:
								if ((0x87fffffeaffffffeL & l) == 0L) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 10:
								if ((0x7fffffe87fffffeL & l) != 0L) jjCheckNAddStates(18, 22);
								break;
						case 11:
								if ((0x87fffffeaffffffeL & l) != 0L) jjCheckNAddStates(18, 22);
								break;
						case 16:
								if ((0x7fffffe87fffffeL & l) != 0L) jjCheckNAddStates(31, 36);
								break;
						case 17:
								if ((0x87fffffeaffffffeL & l) != 0L) jjCheckNAddStates(31, 36);
								break;
						case 22:
								if ((0x7fffffe87fffffeL & l) != 0L) jjCheckNAddStates(45, 50);
								break;
						case 23:
								if ((0x87fffffeaffffffeL & l) != 0L) jjCheckNAddStates(45, 50);
								break;
						default:
								break;
					}
				} while (i != startsAt);
			} else {
				int hiByte = curChar >> 8;
				int i1 = hiByte >> 6;
				long l1 = 1L << (hiByte & 077);
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch(jjstateSet[--i]) {
						case 1:
								if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
									if (kind > 28) kind = 28;
									jjCheckNAdd(0);
								}
								if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
									if (kind > 23) kind = 23;
									jjCheckNAddStates(6, 14);
								}
								break;
						case 0:
								if (!jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
								if (kind > 28) kind = 28;
								jjCheckNAdd(0);
								break;
						case 5:
								if (!jjCanMove_1(hiByte, i1, i2, l1, l2)) break;
								if (kind > 23) kind = 23;
								jjCheckNAddStates(6, 14);
								break;
						case 6:
								if (!jjCanMove_2(hiByte, i1, i2, l1, l2)) break;
								if (kind > 23) kind = 23;
								jjCheckNAdd(6);
								break;
						case 7:
								if (!jjCanMove_1(hiByte, i1, i2, l1, l2)) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 8:
								if (!jjCanMove_2(hiByte, i1, i2, l1, l2)) break;
								if (kind > 26) kind = 26;
								jjCheckNAddStates(15, 17);
								break;
						case 10:
								if (jjCanMove_1(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(18, 22);
								break;
						case 11:
								if (jjCanMove_2(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(18, 22);
								break;
						case 16:
								if (jjCanMove_1(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(31, 36);
								break;
						case 17:
								if (jjCanMove_2(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(31, 36);
								break;
						case 22:
								if (jjCanMove_1(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(45, 50);
								break;
						case 23:
								if (jjCanMove_2(hiByte, i1, i2, l1, l2)) jjCheckNAddStates(45, 50);
								break;
						default:
								break;
					}
				} while (i != startsAt);
			}
			if (kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 27 - (jjnewStateCnt = startsAt))) return curPos;
			try {
				curChar = input_stream.readChar();
			} catch (java.io.IOException e) {
				return curPos;
			}
		}
	}

	static final int[] jjnextStates = { 7, 9, 10, 12, 13, 14, 6, 7, 8, 9, 10, 11, 12, 13, 14, 7, 8, 9, 10, 11, 12, 13, 14, 10, 12, 13, 14, 15, 16, 18, 25, 16, 17, 18, 19, 20, 25, 16, 18, 19, 20, 25, 21, 22, 24, 19, 20, 22, 23, 24, 25, 19, 20, 22, 24, 25 };

	private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
		switch(hiByte) {
			case 0:
					return ((jjbitVec2[i2] & l2) != 0L);
			default:
				return (jjbitVec0[i1] & l1) != 0L;
		}
	}

	private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
		switch(hiByte) {
			case 0:
					return ((jjbitVec4[i2] & l2) != 0L);
			case 2:
					return ((jjbitVec5[i2] & l2) != 0L);
			case 3:
					return ((jjbitVec6[i2] & l2) != 0L);
			case 4:
					return ((jjbitVec7[i2] & l2) != 0L);
			case 5:
					return ((jjbitVec8[i2] & l2) != 0L);
			case 6:
					return ((jjbitVec9[i2] & l2) != 0L);
			case 7:
					return ((jjbitVec10[i2] & l2) != 0L);
			case 9:
					return ((jjbitVec11[i2] & l2) != 0L);
			case 10:
					return ((jjbitVec12[i2] & l2) != 0L);
			case 11:
					return ((jjbitVec13[i2] & l2) != 0L);
			case 12:
					return ((jjbitVec14[i2] & l2) != 0L);
			case 13:
					return ((jjbitVec15[i2] & l2) != 0L);
			case 14:
					return ((jjbitVec16[i2] & l2) != 0L);
			case 15:
					return ((jjbitVec17[i2] & l2) != 0L);
			case 16:
					return ((jjbitVec18[i2] & l2) != 0L);
			case 17:
					return ((jjbitVec19[i2] & l2) != 0L);
			case 18:
					return ((jjbitVec20[i2] & l2) != 0L);
			case 19:
					return ((jjbitVec21[i2] & l2) != 0L);
			case 20:
					return ((jjbitVec0[i2] & l2) != 0L);
			case 22:
					return ((jjbitVec22[i2] & l2) != 0L);
			case 23:
					return ((jjbitVec23[i2] & l2) != 0L);
			case 24:
					return ((jjbitVec24[i2] & l2) != 0L);
			case 25:
					return ((jjbitVec25[i2] & l2) != 0L);
			case 29:
					return ((jjbitVec26[i2] & l2) != 0L);
			case 30:
					return ((jjbitVec27[i2] & l2) != 0L);
			case 31:
					return ((jjbitVec28[i2] & l2) != 0L);
			case 32:
					return ((jjbitVec29[i2] & l2) != 0L);
			case 33:
					return ((jjbitVec30[i2] & l2) != 0L);
			case 48:
					return ((jjbitVec31[i2] & l2) != 0L);
			case 49:
					return ((jjbitVec32[i2] & l2) != 0L);
			case 77:
					return ((jjbitVec33[i2] & l2) != 0L);
			case 159:
					return ((jjbitVec34[i2] & l2) != 0L);
			case 164:
					return ((jjbitVec35[i2] & l2) != 0L);
			case 215:
					return ((jjbitVec36[i2] & l2) != 0L);
			case 216:
					return ((jjbitVec37[i2] & l2) != 0L);
			case 250:
					return ((jjbitVec38[i2] & l2) != 0L);
			case 251:
					return ((jjbitVec39[i2] & l2) != 0L);
			case 253:
					return ((jjbitVec40[i2] & l2) != 0L);
			case 254:
					return ((jjbitVec41[i2] & l2) != 0L);
			case 255:
					return ((jjbitVec42[i2] & l2) != 0L);
			default:
				return (jjbitVec3[i1] & l1) != 0L;
		}
	}

	private static final boolean jjCanMove_2(int hiByte, int i1, int i2, long l1, long l2) {
		switch(hiByte) {
			case 0:
					return ((jjbitVec43[i2] & l2) != 0L);
			case 2:
					return ((jjbitVec5[i2] & l2) != 0L);
			case 3:
					return ((jjbitVec44[i2] & l2) != 0L);
			case 4:
					return ((jjbitVec45[i2] & l2) != 0L);
			case 5:
					return ((jjbitVec46[i2] & l2) != 0L);
			case 6:
					return ((jjbitVec47[i2] & l2) != 0L);
			case 7:
					return ((jjbitVec48[i2] & l2) != 0L);
			case 9:
					return ((jjbitVec49[i2] & l2) != 0L);
			case 10:
					return ((jjbitVec50[i2] & l2) != 0L);
			case 11:
					return ((jjbitVec51[i2] & l2) != 0L);
			case 12:
					return ((jjbitVec52[i2] & l2) != 0L);
			case 13:
					return ((jjbitVec53[i2] & l2) != 0L);
			case 14:
					return ((jjbitVec54[i2] & l2) != 0L);
			case 15:
					return ((jjbitVec55[i2] & l2) != 0L);
			case 16:
					return ((jjbitVec56[i2] & l2) != 0L);
			case 17:
					return ((jjbitVec19[i2] & l2) != 0L);
			case 18:
					return ((jjbitVec20[i2] & l2) != 0L);
			case 19:
					return ((jjbitVec57[i2] & l2) != 0L);
			case 20:
					return ((jjbitVec0[i2] & l2) != 0L);
			case 22:
					return ((jjbitVec22[i2] & l2) != 0L);
			case 23:
					return ((jjbitVec58[i2] & l2) != 0L);
			case 24:
					return ((jjbitVec59[i2] & l2) != 0L);
			case 25:
					return ((jjbitVec60[i2] & l2) != 0L);
			case 29:
					return ((jjbitVec26[i2] & l2) != 0L);
			case 30:
					return ((jjbitVec27[i2] & l2) != 0L);
			case 31:
					return ((jjbitVec28[i2] & l2) != 0L);
			case 32:
					return ((jjbitVec61[i2] & l2) != 0L);
			case 33:
					return ((jjbitVec30[i2] & l2) != 0L);
			case 48:
					return ((jjbitVec62[i2] & l2) != 0L);
			case 49:
					return ((jjbitVec32[i2] & l2) != 0L);
			case 77:
					return ((jjbitVec33[i2] & l2) != 0L);
			case 159:
					return ((jjbitVec34[i2] & l2) != 0L);
			case 164:
					return ((jjbitVec35[i2] & l2) != 0L);
			case 215:
					return ((jjbitVec36[i2] & l2) != 0L);
			case 216:
					return ((jjbitVec63[i2] & l2) != 0L);
			case 220:
					return ((jjbitVec64[i2] & l2) != 0L);
			case 221:
					return ((jjbitVec65[i2] & l2) != 0L);
			case 250:
					return ((jjbitVec38[i2] & l2) != 0L);
			case 251:
					return ((jjbitVec66[i2] & l2) != 0L);
			case 253:
					return ((jjbitVec40[i2] & l2) != 0L);
			case 254:
					return ((jjbitVec67[i2] & l2) != 0L);
			case 255:
					return ((jjbitVec68[i2] & l2) != 0L);
			default:
				return (jjbitVec3[i1] & l1) != 0L;
		}
	}

	public static final String[] jjstrLiteralImages = { "", "\100\154\151\156\153\160\154\141\151\156", "\100\154\151\156\153", "\100\166\141\154\165\145", "\100\160\141\162\141\155", "\100\162\145\164\165\162\156", "\100\164\150\162\157\167\163", "\100\145\170\143\145\160\164\151\157\156", "\100\163\145\145", "\100\163\145\162\151\141\154\106\151\145\154\144", "\100\163\145\162\151\141\154\104\141\164\141", "\100\163\145\162\151\141\154", "\100\151\156\150\145\162\151\164\104\157\143", "\100\144\157\143\122\157\157\164", "\100\143\157\144\145", "\100\144\145\160\162\145\143\141\164\145\144", "\100\141\165\164\150\157\162", "\100\154\151\164\145\162\141\154", "\100\163\151\156\143\145", "\100\166\145\162\163\151\157\156", "\173", "\175", "\52", null, null, null, null, null, null, null, null };

	public static final String[] lexStateNames = { "DEFAULT" };
	static final long[] jjtoToken = { 0x1cffffffL };
	static final long[] jjtoSkip = { 0x60000000L };
	protected JavaCharStream input_stream;
	private final int[] jjrounds = new int[27];
	private final int[] jjstateSet = new int[54];
	protected char curChar;

	public TagsParserTokenManager(JavaCharStream stream) {
		if (JavaCharStream.staticFlag) throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
		input_stream = stream;
	}

	public TagsParserTokenManager(JavaCharStream stream, int lexState) {
		this(stream);
		SwitchTo(lexState);
	}

	public void ReInit(JavaCharStream stream) {
		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}

	private void ReInitRounds() {
		int i;
		jjround = 0x80000001;
		for (i = 27; i-- > 0; ) jjrounds[i] = 0x80000000;
	}

	public void ReInit(JavaCharStream stream, int lexState) {
		ReInit(stream);
		SwitchTo(lexState);
	}

	public void SwitchTo(int lexState) {
		if (lexState >= 1 || lexState < 0) throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);

		else curLexState = lexState;
	}

	protected Token jjFillToken() {
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		String im = jjstrLiteralImages[jjmatchedKind];
		curTokenImage = (im == null) ? input_stream.GetImage() : im;
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = Token.newToken(jjmatchedKind, curTokenImage);

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}

	int curLexState = 0;
	int defaultLexState = 0;
	int jjnewStateCnt;
	int jjround;
	int jjmatchedPos;
	int jjmatchedKind;

	public Token getNextToken() {
		Token matchedToken;
		int curPos = 0;

		EOFLoop: for (; ; ) {
			try {
				curChar = input_stream.BeginToken();
			} catch (java.io.IOException e) {
				jjmatchedKind = 0;
				matchedToken = jjFillToken();
				return matchedToken;
			}

			jjmatchedKind = 0x7fffffff;
			jjmatchedPos = 0;
			curPos = jjMoveStringLiteralDfa0_0();
			if (jjmatchedKind != 0x7fffffff) {
				if (jjmatchedPos + 1 < curPos) input_stream.backup(curPos - jjmatchedPos - 1);
				if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
					matchedToken = jjFillToken();
					return matchedToken;
				} else {
					continue EOFLoop;
				}
			}
			int error_line = input_stream.getEndLine();
			int error_column = input_stream.getEndColumn();
			String error_after = null;
			boolean EOFSeen = false;
			try {
				input_stream.readChar();
				input_stream.backup(1);
			} catch (java.io.IOException e1) {
				EOFSeen = true;
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
				if (curChar == '\n' || curChar == '\r') {
					error_line++;
					error_column = 0;
				}
				else error_column++;
			}
			if (!EOFSeen) {
				input_stream.backup(1);
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
			}
			throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
		}
	}

	private void jjCheckNAdd(int state) {
		if (jjrounds[state] != jjround) {
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}

	private void jjAddStates(int start, int end) {
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		} while (start++ != end);
	}

	private void jjCheckNAddTwoStates(int state1, int state2) {
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}

	private void jjCheckNAddStates(int start, int end) {
		do {
			jjCheckNAdd(jjnextStates[start]);
		} while (start++ != end);
	}

}
