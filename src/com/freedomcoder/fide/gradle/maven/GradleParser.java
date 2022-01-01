package com.freedomcoder.fide.gradle.maven;

/* renamed from: ir */

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleParser extends NamedSingletone<GradleParser> {

    @Override
    public GradleParser create(String str) {
        return null;
    }

    public static class Repository {
        public String name;

        public Repository(String name) {
            this.name = name;
        }
    }

    public static class MavenModule {

        private static final Pattern regex = Pattern.compile("([^:]+):([^:]+):([\\d\\w\\.\\-\\+]+)");
        /* renamed from: DW */
        public String id = "";
        /* renamed from: Hw */
        public String version = "+";
        /* renamed from: j6 */
        public String group = "";
        /* renamed from: v5 */
        public String scope = "";
        private String value;

        public MavenModule(String str) {
            Matcher matcher = regex.matcher(str);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Can only parse mvn: urls " + str);
            }
            final MatchResult match = matcher.toMatchResult();

            //System.out.println(
//				match.group(3)
            //);

            group = match.group(1);
            id = match.group(2);
            version = match.group(3);
            value = str;
        }

        public MavenModule() {
            // super(i);
        }

        public MavenModule(MavenModule gVar, String str) {
            //super(gVar.f22610FH);
            this.version = str;
            this.group = gVar.group;
            this.id = gVar.id;
            this.scope = gVar.scope;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MavenModule)) {
                return false;
            }
            MavenModule gVar = (MavenModule) obj;
            return gVar.group.equals(this.group) && gVar.id.equals(this.id) && gVar.version.equals(this.version) && gVar.scope.equals(this.scope);
        }

        /* renamed from: j6 */
        public String moduleName() {
            return this.group + ":" + this.id;
        }

        public int hashCode() {
            return this.group.hashCode() + this.id.hashCode();
        }

        public String toString() {
            return this.group + ":" + this.id + ":" + this.version + "(" + scope + ")";
        }
    }
}
