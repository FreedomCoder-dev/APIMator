package com.freedomcoder.fide.gradle.maven;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* renamed from: iv */
public class MetadataParser extends NamedSingletone<MetadataParser> {

    /* renamed from: j6 */
    public List<String> versions = new ArrayList();

    public MetadataParser() {
    }

    private MetadataParser(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileInputStream);
            fileInputStream.close();
            Element element = (Element) parse.getElementsByTagName("versions").item(0);
            if (element != null) {
                NodeList elementsByTagName = element.getElementsByTagName("version");
                for (int i = 0; i < elementsByTagName.getLength(); i++) {
                    this.versions.add(elementsByTagName.item(i).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: j6 */
    public static boolean versionMatch(String str, String str2) {
        if (str2 == null || str2.equals(str)) {
            return true;
        }
        if (str2.contains("+")) {
            return str.startsWith(str2.substring(0, str2.indexOf('+')));
        }
        return false;
    }

    /* renamed from: j6 */
    public static String sortFindVersion(ArrayList<String> arrayList, String str) {
        Collections.sort(arrayList, new Comparator<String>() {
            /* renamed from: j6 */
            public int compare(String str, String str2) {
                return ArtifactVersion.compare(str, str2);
            }
        });
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (versionMatch(arrayList.get(size), str)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: j6 */
    public MetadataParser create(String str) {
        return new MetadataParser(str);
    }

    /* renamed from: FH */
    public String getMatchingVersion(String str) {
        if (str.contains(",")) {
            String substring = str.substring(1, str.indexOf(44));
            String substring2 = str.substring(str.indexOf(44) + 1, str.length() - 1);
            int i = 0;
            boolean brackStart = str.charAt(0) == '[';
            boolean brackEnd = str.charAt(str.length() - 1) == ']';
            int size = this.versions.size() - 1;
            if (substring2.length() > 0) {
                while (true) {
                    if (size < 0) {
                        break;
                    } else if (!versionMatch(this.versions.get(size), substring2)) {
                        size--;
                    } else if (!brackEnd) {
                        size--;
                    }
                }
            }
            if (substring.length() > 0) {
                while (true) {
                    if (i >= this.versions.size()) {
                        break;
                    } else if (!versionMatch(this.versions.get(i), substring)) {
                        i++;
                    } else if (!brackStart) {
                        i++;
                    }
                }
            }
            if (size >= i && i < this.versions.size()) {
                return this.versions.get(size);
            }
            if (i < this.versions.size()) {
                return this.versions.get(i);
            }
            return null;
        }
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
        }
        for (int size2 = this.versions.size() - 1; size2 >= 0; size2--) {
            if (versionMatch(this.versions.get(size2), str)) {
                return this.versions.get(size2);
            }
        }
        return null;
    }
}
