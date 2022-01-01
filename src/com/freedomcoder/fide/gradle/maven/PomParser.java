package com.freedomcoder.fide.gradle.maven;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/* renamed from: iw */
public class PomParser extends NamedSingletone<PomParser> {

    /* renamed from: j6 */
    public List<GradleParser.MavenModule> dependencies = new ArrayList();

    public PomParser() {
    }

    private PomParser(String str) {
        try {
            System.out.println("solving " + str);
            FileInputStream fileInputStream = new FileInputStream(str);
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileInputStream);
            fileInputStream.close();
            NodeList elementsByTagName = parse.getElementsByTagName("dependency");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                try {
                    Element element = (Element) elementsByTagName.item(i);
                    if ("plugin".equals(element.getParentNode().getNodeName())) continue;
                    String groupId = findElement(element, "groupId");
                    String artifactId = findElement(element, "artifactId");
                    String version = findElement(element, "version");
                    String optional = findElement(element, "optional");
                    String scope = findElement(element, "scope");
                    if (groupId.length() > 0 && artifactId.length() > 0 && !groupId.contains("$") && !artifactId.contains("$") && !"test".equals(scope) && !"true".equals(optional) && !"provided".equals(scope) && !"system".equals(scope)) {
                        version = (version.length() == 0 || version.contains("$")) ? "+" : version;
                        GradleParser.MavenModule module = new GradleParser.MavenModule();
                        this.dependencies.add(module);
                        module.group = groupId;
                        module.id = artifactId;
                        module.version = version;
                        System.out.println(scope);
                        module.scope = scope;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: j6 */
    public PomParser create(String str) {
        return new PomParser(str);
    }

    /* renamed from: j6 */
    private String findElement(Element element, String str) {
        Node item = element.getElementsByTagName(str).item(0);
        if (item == null) {
            return "";
        }
        return item.getTextContent();
    }
}
