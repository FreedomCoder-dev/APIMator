package com.freedomcoder.fide.gradle.maven;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/* renamed from: iu */
public class ArtifactVersion {

    /* renamed from: DW */
    private Integer secondNumber;

    /* renamed from: FH */
    private Integer thirdNumber;

    /* renamed from: Hw */
    private Integer numberAfterMinus;

    /* renamed from: Zo */
    private ItemsManipulator items;

    /* renamed from: j6 */
    private Integer firstNumber;

    /* renamed from: v5 */
    private String fourthPart;

    public ArtifactVersion(String str) {
        create(str);
    }

    /* renamed from: j6 */
    public static int compare(String str, String str2) {
        return new ArtifactVersion(str).compareTo(new ArtifactVersion(str2));
    }

    /* renamed from: j6 */
    private static Integer parseNumber(StringTokenizer stringTokenizer) {
        try {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.length() > 1) {
                if (nextToken.startsWith("0")) {
                    throw new NumberFormatException("Number part has a leading 0: '" + nextToken + "'");
                }
            }
            return Integer.valueOf(nextToken);
        } catch (NoSuchElementException unused) {
            throw new NumberFormatException("Number is invalid");
        }
    }

    public int hashCode() {
        return this.items.hashCode() + 11;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ArtifactVersion) && compareTo((ArtifactVersion) obj) == 0;
    }

    /* renamed from: j6 */
    public int compareTo(ArtifactVersion iuVar) {
        if (iuVar instanceof ArtifactVersion) {
            return this.items.compareTo(iuVar.items);
        }
        return compareTo(new ArtifactVersion(iuVar.toString()));
    }

    /* renamed from: j6 */
    public final void create(String str) {
        String beforeMinus;
        String afterMinus;
        this.items = new ItemsManipulator(str);
        int indexOfMinus = str.indexOf("-");
        boolean isDigital = false;
        boolean failure = true;
        if (indexOfMinus < 0) {
            beforeMinus = str;
            afterMinus = null;
        } else {
            beforeMinus = str.substring(0, indexOfMinus);
            afterMinus = str.substring(indexOfMinus + 1);
        }
        if (afterMinus != null) {
            try {
                if (afterMinus.length() != 1) {
                    if (afterMinus.startsWith("0")) {
                        this.fourthPart = afterMinus;
                    }
                }
                this.numberAfterMinus = Integer.valueOf(afterMinus);
            } catch (NumberFormatException unused) {
                this.fourthPart = afterMinus;
            }
        }
        if (beforeMinus.contains(".") || beforeMinus.startsWith("0")) {
            StringTokenizer stringTokenizer = new StringTokenizer(beforeMinus, ".");
            try {
                this.firstNumber = parseNumber(stringTokenizer);
                if (stringTokenizer.hasMoreTokens()) {
                    this.secondNumber = parseNumber(stringTokenizer);
                }
                if (stringTokenizer.hasMoreTokens()) {
                    this.thirdNumber = parseNumber(stringTokenizer);
                }
                if (stringTokenizer.hasMoreTokens()) {
                    this.fourthPart = stringTokenizer.nextToken();
                    isDigital = Pattern.compile("\\d+").matcher(this.fourthPart).matches();
                }
                if (!beforeMinus.contains("..") && !beforeMinus.startsWith(".") && !beforeMinus.endsWith(".")) {
                    failure = isDigital;
                }
            } catch (NumberFormatException unused2) {
            }
            if (failure) {
                this.fourthPart = str;
                this.firstNumber = null;
                this.secondNumber = null;
                this.thirdNumber = null;
                this.numberAfterMinus = null;
                return;
            }
            return;
        }
        try {
            this.firstNumber = Integer.valueOf(beforeMinus);
        } catch (NumberFormatException unused3) {
            this.fourthPart = str;
            this.numberAfterMinus = null;
        }
    }

    public String toString() {
        return this.items.toString();
    }
}
