package com.freedomcoder.fide.gradle.maven;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* renamed from: ny */
public abstract class NamedSingletone<T extends NamedSingletone<?>> {

    /* renamed from: DW */
    private static final Map<String, Long> f22915DW = new HashMap();

    /* renamed from: j6 */
    private static final Map<String, NamedSingletone<?>> f22916j6 = new HashMap();

    /* renamed from: Ws */
    public String f22917Ws;

    /* access modifiers changed from: protected */
    /* renamed from: DW */
    public abstract T create(String str);

    /* renamed from: J0 */
    public T getSingletone(String str) {
        if (f22916j6.containsKey(str) && f22915DW.containsKey(str) && new File(str).lastModified() == f22915DW.get(str).longValue()) {
            return (T) f22916j6.get(str);
        }
        try {
            T DW = create(str);
            DW.f22917Ws = str;
            f22916j6.put(str, DW);
            f22915DW.put(str, Long.valueOf(new File(str).lastModified()));
            return DW;
        } catch (Exception e) {
            e.printStackTrace();
            return (T) this;
        }
    }
}
