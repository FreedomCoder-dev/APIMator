package com.freedomcoder.fide.gradle.maven;

import java.math.BigInteger;
import java.util.*;

/* renamed from: is */
class ItemsManipulator implements Comparable<ItemsManipulator> {

    /* renamed from: DW */
    private String sourceString;

    /* renamed from: FH */
    private ItemSequence sequence;

    /* renamed from: j6 */
    private String str;

    public ItemsManipulator(String str) {
        parse(str);
    }

    /* renamed from: j6 */
    private static Item makeItem(boolean isnumeric, String str) {
        return isnumeric ? new NumericItem(str) : new TypePart(str, false);
    }

    /* renamed from: j6 */
    public final void parse(String str) {
        int i;
        ItemSequence cVar;
        int i2;
        ItemSequence cVar2;
        this.str = str;
        this.sequence = new ItemSequence();
        String src = str.toLowerCase(Locale.ENGLISH);
        ItemSequence currSeq = this.sequence;
        Stack stack = new Stack();
        stack.push(currSeq);
        //ItemSequence tmpsequence2 = tmpSequence;
        int previousInc = 0;
        boolean wasDigit = false;
        for (int iteration = 0; iteration < src.length(); iteration++) {
            char charAt = src.charAt(iteration);
            if (charAt == '.') {
                if (iteration == previousInc) {
                    currSeq.add(NumericItem.zeroItem);
                } else {
                    currSeq.add(makeItem(wasDigit, src.substring(previousInc, iteration)));
                }
                previousInc = iteration + 1;
            } else if (charAt == '-') {
                if (iteration == previousInc) {
                    currSeq.add(NumericItem.zeroItem);
                } else {
                    currSeq.add(makeItem(wasDigit, src.substring(previousInc, iteration)));
                }
                previousInc = iteration + 1;
                ItemSequence cVar5 = new ItemSequence();
                currSeq.add(cVar5);
                stack.push(cVar5);
                currSeq = cVar5;
            } else if (Character.isDigit(charAt)) {
                if (wasDigit || iteration <= previousInc) {
                    ItemSequence cVar6 = currSeq;
                    i2 = previousInc;
                    cVar2 = cVar6;
                } else {
                    currSeq.add(new TypePart(src.substring(previousInc, iteration), true));
                    cVar2 = new ItemSequence();
                    currSeq.add(cVar2);
                    stack.push(cVar2);
                    i2 = iteration;
                }
                wasDigit = true;
                int i5 = i2;
                currSeq = cVar2;
                previousInc = i5;
            } else {
                if (!wasDigit || iteration <= previousInc) {
                    ItemSequence cVar7 = currSeq;
                    i = previousInc;
                    cVar = cVar7;
                } else {
                    currSeq.add(makeItem(true, src.substring(previousInc, iteration)));
                    cVar = new ItemSequence();
                    currSeq.add(cVar);
                    stack.push(cVar);
                    i = iteration;
                }
                wasDigit = false;
                int i6 = i;
                currSeq = cVar;
                previousInc = i6;
            }
        }
        if (src.length() > previousInc) {
            currSeq.add(makeItem(wasDigit, src.substring(previousInc)));
        }
        while (!stack.isEmpty()) {
            ((ItemSequence) stack.pop()).removeEmptyUntilSequence();
        }
        this.sourceString = this.sequence.toString();
    }

    /* renamed from: j6 */
    public int compareTo(ItemsManipulator isVar) {
        return this.sequence.compareTo(isVar.sequence);
    }

    public String toString() {
        return this.str;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ItemsManipulator) && this.sourceString.equals(((ItemsManipulator) obj).sourceString);
    }

    public int hashCode() {
        return this.sourceString.hashCode();
    }

    /* renamed from: is$b */
    private interface Item {
        /* renamed from: DW */
        boolean isEmpty();

        /* renamed from: j6 */
        int getTypeNumber();

        /* renamed from: j6 */
        int compareTo(Item bVar);
    }

    /* renamed from: is$a */
    private static class NumericItem implements Item {

        /* renamed from: j6 */
        public static final NumericItem zeroItem = new NumericItem();
        /* renamed from: DW */
        private static final BigInteger zero = new BigInteger("0");
        /* renamed from: FH */
        private final BigInteger current;

        private NumericItem() {
            this.current = zero;
        }

        public NumericItem(String str) {
            this.current = new BigInteger(str);
        }

        /* renamed from: j6 */
        public int getTypeNumber() {
            return 0;
        }

        /* renamed from: DW */
        public boolean isEmpty() {
            return zero.equals(this.current);
        }

        /* renamed from: j6 */
        public int compareTo(Item bVar) {
            if (bVar == null) {
                return zero.equals(this.current) ^ true ? 1 : 0;
            }
            switch (bVar.getTypeNumber()) {
                case 0:
                    return this.current.compareTo(((NumericItem) bVar).current);
                case 1:
                    return 1;
                case 2:
                    return 1;
                default:
                    throw new RuntimeException("invalid item: " + bVar.getClass());
            }
        }

        public String toString() {
            return this.current.toString();
        }
    }

    /* renamed from: is$d */
    private static class TypePart implements Item {
        private static final String[] types = {"alpha", "beta", "milestone", "rc", "snapshot", "", "sp"};

        /* renamed from: DW */
        private static final List<String> listOfTypes = Arrays.asList(types);
        /* renamed from: Hw */
        private static final String arrayDivider = String.valueOf(listOfTypes.indexOf(""));
        /* renamed from: FH */
        private static final Properties properties = new Properties();

        /* renamed from: j6 */

        static {
            properties.put("ga", "");
            properties.put("final", "");
            properties.put("cr", "rc");
        }

        /* renamed from: v5 */
        private final String current;

        public TypePart(String name, boolean isOneLetter) {
            if (isOneLetter && name.length() == 1) {
                char firstchar = name.charAt(0);
                if (firstchar != 'm') {
                    switch (firstchar) {
                        case 'a':
                            name = "alpha";
                            break;
                        case 'b':
                            name = "beta";
                            break;
                    }
                } else {
                    name = "milestone";
                }
            }
            this.current = properties.getProperty(name, name);
        }

        /* renamed from: j6 */
        public static String getStringOrdinal(String str) {
            int indexOf = listOfTypes.indexOf(str);
            if (indexOf != -1) {
                return String.valueOf(indexOf);
            }
            return listOfTypes.size() + "-" + str;
        }

        /* renamed from: j6 */
        public int getTypeNumber() {
            return 1;
        }

        /* renamed from: DW */
        public boolean isEmpty() {
            return getStringOrdinal(this.current).compareTo(arrayDivider) == 0;
        }

        /* renamed from: j6 */
        public int compareTo(Item bVar) {
            if (bVar == null) {
                return getStringOrdinal(this.current).compareTo(arrayDivider);
            }
            switch (bVar.getTypeNumber()) {
                case 0:
                    return -1;
                case 1:
                    return getStringOrdinal(this.current).compareTo(getStringOrdinal(((TypePart) bVar).current));
                case 2:
                    return -1;
                default:
                    throw new RuntimeException("invalid item: " + bVar.getClass());
            }
        }

        public String toString() {
            return this.current;
        }
    }

    /* renamed from: is$c */
    private static class ItemSequence extends ArrayList<Item> implements Item {
        private ItemSequence() {
        }

        /* renamed from: j6 */
        public int getTypeNumber() {
            return 2;
        }

        /* renamed from: DW */
        public boolean isEmpty() {
            return size() == 0;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: FH */
        public void removeEmptyUntilSequence() {
            for (int size = size() - 1; size >= 0; size--) {
                Item bVar = get(size);
                if (bVar.isEmpty()) {
                    remove(size);
                } else if (!(bVar instanceof ItemSequence)) {
                    return;
                }
            }
        }

        /* renamed from: j6 */
        public int compareTo(Item bVar) {
            int i;
            if (bVar != null) {
                switch (bVar.getTypeNumber()) {
                    case 0:
                        return -1;
                    case 1:
                        return 1;
                    case 2:
                        Iterator it = iterator();
                        Iterator it2 = ((ItemSequence) bVar).iterator();
                        do {
                            if (!it.hasNext() && !it2.hasNext()) {
                                return 0;
                            }
                            Item bVar2 = it.hasNext() ? (Item) it.next() : null;
                            Item bVar3 = it2.hasNext() ? (Item) it2.next() : null;
                            if (bVar2 != null) {
                                i = bVar2.compareTo(bVar3);
                                continue;
                            } else if (bVar3 == null) {
                                i = 0;
                                continue;
                            } else {
                                i = bVar3.compareTo(bVar2) * -1;
                                continue;
                            }
                        } while (i == 0);
                        return i;
                    default:
                        throw new RuntimeException("invalid item: " + bVar.getClass());
                }
            } else if (size() == 0) {
                return 0;
            } else {
                return get(0).compareTo(null);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = iterator();
            while (it.hasNext()) {
                Item bVar = (Item) it.next();
                if (sb.length() > 0) {
                    sb.append(bVar instanceof ItemSequence ? '-' : '.');
                }
                sb.append(bVar);
            }
            return sb.toString();
        }
    }
}
