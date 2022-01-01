package com.freedomcoder.fide.gradle.maven;

import com.freedomcoder.fide.gradle.utils.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* renamed from: ot */
public class FetchUtils {
    /* access modifiers changed from: private */

    /* renamed from: j6 */
    public static final String[] google_android_repo = {"google_m2repository", "android_m2repository"};
    /* renamed from: FH */
    public final ExecutorService f23989FH = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    /* renamed from: DW */
    private final List<C5502e> f23988DW = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: Hw *
    public C5497d f23990Hw;
    /* access modifiers changed from: private */
    /* renamed from: VH */
    public String f23991VH;
    /* access modifiers changed from: private */

    /* renamed from: Zo */
    public int f23992Zo;
    /* access modifiers changed from: private */

    /* renamed from: gn */
    public String f23993gn;
    /* renamed from: v5 */
    public int f23995v5;
    /* access modifiers changed from: private */
    /* renamed from: u7 */
    private long f23994u7;

    /* renamed from: DW */
    private boolean m40514DW(int i) {
        return i >= 200 && i < 300;
    }

    /* access modifiers changed from: private */
    /* renamed from: j6 */
    public void downloadFile(String remote, String local, boolean z) throws IOException {
    }/*
        long j;
        boolean z2;
        int i;
        byte[] bArr;
        long j2;
        long j3;
        long j4;
        FetchUtils otVar;
        Map headerFields;
        List list;
        List list2;
        FetchUtils otVar2 = this;
        String str3 = remote;
        String str4 = local;
        if (!Thread.interrupted()) {
            File file = new File(str4);
            long j5 = 0;
            long length = (!z || !file.exists()) ? 0 : file.length();
            int i2 = 0;
            if (length > 0) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
                try {
                    httpURLConnection.setRequestMethod("HEAD");
                    httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                    Map headerFields2 = httpURLConnection.getHeaderFields();
                    if (!otVar2.m40514DW(httpURLConnection.getResponseCode()) || headerFields2 == null || (list2 = (List) headerFields2.get("content-Length")) == null || list2.isEmpty()) {
                        j = -1;
                    } else {
                        try {
                            j = Long.parseLong((String) list2.get(0));
                        } catch (NumberFormatException unused) {
                            j = -1;
                        }
                    }
                } finally {
                    httpURLConnection.disconnect();
                }
            } else {
                j = -1;
            }
            if (length != j) {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str3).openConnection();
                try {
                    httpURLConnection2.setRequestProperty("Accept-Encoding", "identity");
                    if (j <= 0 || length <= 0) {
                        i = httpURLConnection2.getResponseCode();
                        z2 = false;
                    } else {
                        httpURLConnection2.setRequestProperty("Range", "bytes=" + length + "-");
                        i = httpURLConnection2.getResponseCode();
                        z2 = i == 206;
                    }
                    if (otVar2.m40514DW(i)) {
                        if (j == -1 && (headerFields = httpURLConnection2.getHeaderFields()) != null && (list = (List) headerFields.get("content-Length")) != null && !list.isEmpty()) {
                            try {
                                j = Long.parseLong((String) list.get(0));
                            } catch (NumberFormatException unused2) {
                                j = -1;
                            }
                        }
                        if (!z2) {
                            length = 0;
                        }
                        System.currentTimeMillis();
                        if (!z2) {
                            new File(str4).delete();
                        }
                        new File(str4).getParentFile().mkdirs();
                        FileOutputStream fileOutputStream = new FileOutputStream(str4, z2);
                        try {
                            byte[] bArr2 = new byte[32768];
                            long currentTimeMillis = System.currentTimeMillis();
                            int i3 = -1;
                            long j6 = 0;
                            long j7 = currentTimeMillis;
                            while (true) {
                                int read = httpURLConnection2.getInputStream().read(bArr2);
                                if (read <= 0) {
                                    FetchUtils otVar3 = otVar2;
                                    try {
                                        fileOutputStream.close();
                                        httpURLConnection2.disconnect();
                                        return;
                                    } catch (Throwable th) {
                                        th = th;
                                        httpURLConnection2.disconnect();
                                        throw th;
                                    }
                                } else if (!Thread.interrupted()) {
                                    fileOutputStream.write(bArr2, i2, read);
                                    long j8 = j5 + ((long) read);
                                    long currentTimeMillis2 = System.currentTimeMillis();
                                    long j9 = j8;
                                    long j10 = currentTimeMillis2 - j7;
                                    if (j10 > 5000) {
                                        byte[] bArr3 = bArr2;
                                        long j11 = j9 - j6;
                                        j2 = length;
                                        byte[] bArr4 = bArr3;
                                        long j12 = currentTimeMillis2 - currentTimeMillis;
                                        bArr = bArr4;
                                        try {
                                            PrintStream printStream = System.out;
                                            long j13 = currentTimeMillis2;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("Last 5 secs Average input bytes/sec: ");
                                            double d = (double) j11;
                                            Double.isNaN(d);
                                            double d2 = (double) j10;
                                            Double.isNaN(d2);
                                            sb.append((d * 1000.0d) / d2);
                                            printStream.println(sb.toString());
                                            PrintStream printStream2 = System.out;
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append("Total Average input bytes/sec: ");
                                            j3 = j9;
                                            double d3 = (double) j3;
                                            Double.isNaN(d3);
                                            double d4 = (double) j12;
                                            Double.isNaN(d4);
                                            sb2.append((d3 * 1000.0d) / d4);
                                            printStream2.println(sb2.toString());
                                            j6 = j3;
                                            j7 = j13;
                                            j4 = -1;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileOutputStream.close();
                                            throw th;
                                        }
                                    } else {
                                        j3 = j9;
                                        bArr = bArr2;
                                        j2 = length;
                                        j4 = -1;
                                    }
                                    if (j != j4) {
                                        int i4 = (int) (((j2 + j3) * 100) / j);
                                        if (i3 != i4) {
                                            otVar = this;
                                            try {
                                                otVar.m40530j6(i4);
                                                i3 = i4;
                                            } catch (Throwable th3) {
                                                th = th3;
                                                fileOutputStream.close();
                                                throw th;
                                            }
                                        } else {
                                            otVar = this;
                                        }
                                    } else {
                                        otVar = this;
                                    }
                                    j5 = j3;
                                    otVar2 = otVar;
                                    length = j2;
                                    bArr2 = bArr;
                                    i2 = 0;
                                } else {
                                    FetchUtils otVar4 = otVar2;
                                    throw new InterruptedException();
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            FetchUtils otVar5 = otVar2;
                            fileOutputStream.close();
                            throw th;
                        }
                    } else {
                        FetchUtils otVar6 = otVar2;
                        throw new IOException("HTTP connection to " + str3 + " failed with response code " + i);
                    }
                } catch (Throwable th5) {
                    th = th5;
                    FetchUtils otVar7 = otVar2;
                    httpURLConnection2.disconnect();
                    throw th;
                }
            }
        } else {
            FetchUtils otVar8 = otVar2;
            throw new InterruptedException();
        }
    }

    /* access modifiers changed from: private */

    /* renamed from: j6 */

    /* renamed from: j6 *
    public void m40534j6(String str) {
        if (!Thread.interrupted()) {
            try {
                C5632py j6 = C5633pz.m41249j6(Arrays.asList(new String[]{"/system/bin/chmod", "755", str}), (String) null, (Map<String, String>) null, true, (OutputStream) null, (byte[]) null);
                if (j6.mo23039j6() != 0) {
                    throw new IOException("Could not make " + str + " executable - exit code " + j6.mo23039j6());
                }
            } catch (IOException unused) {
                C5632py j62 = C5633pz.m41249j6(Arrays.asList(new String[]{"/system/xbin/chmod", "755", str}), (String) null, (Map<String, String>) null, true, (OutputStream) null, (byte[]) null);
                if (j62.mo23039j6() != 0) {
                    throw new IOException("Could not make " + str + " executable - exit code " + j62.mo23039j6());
                }
            }
        } else {
            throw new InterruptedException();
        }
    }

    /* renamed from: j6 */
    public String mo22561j6() {
        return this.f23991VH;
    }

    /* renamed from: DW *
    public boolean mo22557DW() {
        return this.f23990Hw != null;
    }

    /* renamed from: FH */
    public int mo22558FH() {
        return this.f23995v5;
    }

    /* renamed from: Hw */
    public int mo22559Hw() {
        return this.f23992Zo;
    }

    /* renamed from: v5 */
    public String mo22566v5() {
        return this.f23993gn;
    }

    /* renamed from: j6 */
    public void mo22565j6(C5502e eVar) {
        this.f23988DW.add(eVar);
    }

    /* renamed from: DW */
    public void mo22556DW(C5502e eVar) {
        this.f23988DW.remove(eVar);
    }

    /* renamed from: Zo *
    public void mo22560Zo() {
        this.f23990Hw.cancel(true);
    }

    /* access modifiers changed from: private */
    /* renamed from: gn */
    public void m40523gn() {
        for (C5502e J0 : this.f23988DW) {
            J0.mo21385J0();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: u7 */
    public void m40543u7() {
        for (C5502e J8 : this.f23988DW) {
            J8.mo21386J8();
        }
    }

    /* renamed from: gn *
    public Runnable f24029gn;

    /* renamed from: v5 *
    private final List<String> f24031v5;

    public C5490a(Activity activity, List<String> list, List<String> list2, List<Boolean> list3, List<Boolean> list4, String str, Runnable runnable) {
        this.f24026Hw = list;
        this.f24031v5 = list2;
        this.f24028Zo = list3;
        this.f24027VH = list4;
        this.f24025FH = str;
        this.f24024DW = activity;
        this.f24029gn = runnable;
    }

    /* renamed from: j6 *
    public Void call() {
        mo22579DW();
        return null;
    }

    /* renamed from: DW *
    public void mo22579DW() {
        if (new File(this.f24025FH).isDirectory() || new File(this.f24025FH).mkdirs()) {
            IOException e = null;
            for (int i = 0; i < this.f24026Hw.size(); i++) {
                try {
                    String str = this.f24026Hw.get(i);
                    String str2 = this.f24031v5.get(i);
                    boolean z = true;
                    boolean z2 = this.f24028Zo != null && this.f24028Zo.get(i).booleanValue();
                    if (this.f24027VH == null || !this.f24027VH.get(i).booleanValue()) {
                        z = false;
                    }
                    File file = new File(new File(this.f24025FH, str2).getPath() + ".tmp");
                    int i2 = i * 100;
                    FetchUtils.this.setCaption("Downloading " + str2, i2 / this.f24026Hw.size(), 0);
                    FetchUtils.this.downloadFile(str, file.getPath(), z2);
                    if (z) {
                        FetchUtils.this.setCaption("Extracting " + str2, i2 / this.f24026Hw.size(), 0);
                        m40557DW(this.f24025FH + "/" + str2, file.getPath());
                        file.delete();
                        FetchUtils.this.setCaption("Removing old libraries", i2 / this.f24026Hw.size(), 50);
                        deletedir(this.f24025FH, str2);
                    }
                } catch (IOException e2) {
                    e = e2;
                }
            }
            if (e == null) {
                C2498f.m18314j6((Runnable) new Runnable() {
                    public void run() {
                        C5476ot.this.m40543u7();
                        C5490a.this.f24029gn.run();
                    }
                });
                return;
            }
            throw e;
        }
        throw new IOException("Could not create directory: " + this.f24025FH);
    }

    /* renamed from: j6 *
    private void deletedir(String str, String str2) {
        String str3 = "";
        String[] repo = FetchUtils.google_android_repo;
        int length = repo.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str4 = repo[i];
            if (str2.contains(str4)) {
                str3 = str4;
                break;
            }
            i++;
        }
        if (!str3.isEmpty()) {
            for (String next : FileUtils.listFiles(str)) {
                if (next.contains(str3) && !next.contains(str2)) {
                    FileUtils.delete(next);
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* renamed from: DW */
    private void m40557DW(String str, String str2) throws IOException {
        FileOutputStream fileOutputStream;
        ZipFile zipFile = new ZipFile(str2);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ArrayList arrayList = new ArrayList();
            while (entries.hasMoreElements()) {
                arrayList.add(entries.nextElement());
            }
            int i = 0;
            while (i < arrayList.size()) {
                if (!Thread.interrupted()) {
                    ZipEntry zipEntry = (ZipEntry) arrayList.get(i);
                    File file = new File(str, zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        if (!(file.exists() && zipEntry.getSize() != -1 && file.length() == zipEntry.getSize())) {
                            m40558FH(str, zipEntry.getName());
                            InputStream inputStream = zipFile.getInputStream(zipEntry);
                            try {
                                fileOutputStream = new FileOutputStream(file);
                                StreamUtils.transferAndClose(inputStream, fileOutputStream);

                            } catch (Throwable th) {
                                inputStream.close();
                                throw th;
                            }
                        }
                    }
//                        FetchUtils.this.m40530j6((i * 100) / arrayList.size());
                    i++;
                } else {
                    throw new InterruptedException();
                }
            }
            zipFile.close();
            new File(str2).delete();
        } catch (Throwable th2) {
            zipFile.close();
//                throw th2;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j6 *
    public void setCaption(final String str, final int i, final int i2) {
        this.f23994u7 = System.currentTimeMillis();
        C2498f.m18314j6((Runnable) new Runnable() {
            public void run() {
                String unused = C5476ot.this.f23993gn = str;
                int unused2 = C5476ot.this.f23995v5 = i;
                int unused3 = C5476ot.this.f23992Zo = i2;
                C5476ot.this.m40523gn();
            }
        });
    }

    /* renamed from: ot$d *
    private class C5497d extends FutureTask<Void> {
        /* access modifiers changed from: private */

        /* renamed from: DW *
        public Activity f24045DW;

        public C5497d(Activity activity, Callable<Void> callable) {
            super(callable);
            this.f24045DW = activity;
        }

        /* access modifiers changed from: protected *
        public void done() {
            if (!isCancelled()) {
                try {
                    get();
                    C2498f.m18314j6((Runnable) new Runnable() {
                        public void run() {
                            C5497d j6 = C5476ot.this.f23990Hw;
                            C5497d dVar = C5497d.this;
                            if (j6 == dVar) {
                                C5476ot.this.m40543u7();
                                C5497d unused = C5476ot.this.f23990Hw = null;
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    C1954e.m15853j6((Throwable) e);
                    C2498f.m18314j6((Runnable) new Runnable() {
                        public void run() {
                            C5497d j6 = C5476ot.this.f23990Hw;
                            C5497d dVar = C5497d.this;
                            if (j6 == dVar) {
                                C5476ot.this.m40543u7();
                                C5497d unused = C5476ot.this.f23990Hw = null;
                                C1970m.m15912j6(C5497d.this.f24045DW, "Download error", (Throwable) e);
                            }
                        }
                    });
                } catch (ExecutionException e2) {
                    C1954e.m15853j6((Throwable) e2);
                    C2498f.m18314j6((Runnable) new Runnable() {
                        public void run() {
                            C5497d j6 = C5476ot.this.f23990Hw;
                            C5497d dVar = C5497d.this;
                            if (j6 == dVar) {
                                C5476ot.this.m40543u7();
                                C5497d unused = C5476ot.this.f23990Hw = null;
                                if (!(e2.getCause() instanceof UnknownHostException) || e2.getCause().getMessage().contains(" ")) {
                                    C1970m.m15912j6(C5497d.this.f24045DW, "Download error", e2.getCause());
                                    return;
                                }
                                Activity j62 = C5497d.this.f24045DW;
                                C1970m.m15897j6(j62, "Download error", "Host not found: " + e2.getCause().getMessage());
                            }
                        }
                    });
                }
            } else {
                C2498f.m18314j6((Runnable) new Runnable() {
                    public void run() {
                        C5497d j6 = C5476ot.this.f23990Hw;
                        C5497d dVar = C5497d.this;
                        if (j6 == dVar) {
                            C5476ot.this.m40543u7();
                            C5497d unused = C5476ot.this.f23990Hw = null;
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j6 *
    public String m40527j6(byte[] bArr, int i) {
        String str = "";
        try {
            str = C2050v.m15997j6((Reader) new InputStreamReader(new ByteArrayInputStream(bArr)));
        } catch (IOException unused) {
        }
        String trim = str.trim();
        if (trim.length() != 0) {
            trim = trim + 10;
        }
        return trim + " ### binary exited with code " + i;
    }

    /* renamed from: ot$c *
    private class C5494c implements Callable<Void> {

        /* renamed from: DW *
        private final File f24040DW;
        /* access modifiers changed from: private */

        /* renamed from: FH *
        public Activity f24041FH;

        public C5494c(Activity activity, File file) {
            this.f24040DW = file;
            this.f24041FH = activity;
        }

        /* renamed from: j6 *
        public Void call() {
            File DW = C2418m.m17976DW();
            if (DW.isDirectory() || DW.mkdirs()) {
                FetchUtils.this.setCaption("Installing NDK support (might take several minutes)", 0, 0);
                String path = new File(DW, "busybox").getPath();
                FetchUtils.m40513DW(this.f24041FH, "busybox", path);
                File file = new File(this.f24040DW, "ndkinstall.sh");
                FetchUtils.m40513DW(this.f24041FH, "ndkinstall.sh", file.getPath());
                FetchUtils.this.m40534j6(path);
                List asList = Arrays.asList(new String[]{path, "ash", file.getPath(), C2418m.m17983VH()});
                if (!Thread.interrupted()) {
                    C5632py j6 = C5633pz.m41249j6(asList, DW.getPath(), (Map<String, String>) null, true, (OutputStream) null, (byte[]) null);
                    if (j6.mo23039j6() == 0) {
                        file.delete();
                        C2498f.m18314j6((Runnable) new Runnable() {
                            public void run() {
                                C5476ot.this.m40543u7();
                                C0913ab.m6261FH("NDK installed");
                                C1970m.m15889DW(C5494c.this.f24041FH, "NDK Support package can be uninstalled", "The NDK has been integrated into AIDE and the AIDE NDK Support package can now be safely uninstalled. Open Play Store to uninstall?", new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent("android.intent.action.VIEW");
                                        intent.setData(Uri.parse(C0913ab.m6266j6(C2498f.f10168j6, "com.aide.ndk", "aideuninstallNdkSupport")));
                                        intent.setFlags(1074266112);
                                        try {
                                            C2498f.m18322tp().startActivity(intent);
                                        } catch (ActivityNotFoundException unused) {
                                            C1970m.m15897j6(C2498f.m18322tp(), "Google Play Store", "Google Play Store App could not be opened. Not installed?");
                                        }
                                    }
                                }, (Runnable) null);
                            }
                        });
                        return null;
                    }
                    throw new IOException(FetchUtils.this.m40527j6(j6.mo23038DW(), j6.mo23039j6()));
                }
                throw new InterruptedException();
            }
            throw new IOException("Could not create directory: " + DW.getPath());
        }
    }

    /* renamed from: ot$a *
    private class C5490a implements Callable<Void> {

        /* renamed from: DW *
        private Activity f24024DW;

        /* renamed from: FH *
        private final String f24025FH;

        /* renamed from: Hw *
        private final List<String> f24026Hw;

        /* renamed from: VH *
        private final List<Boolean> f24027VH;

        /* renamed from: Zo *
        private final List<Boolean> f24028Zo;
        /* access modifiers changed from: private */

    /* renamed from: FH */
    private void m40558FH(String str, String str2) {
        new File(str, str2).getParentFile().mkdirs();
    }

    /* renamed from: FH *
    public Runnable f24034FH;

    /* renamed from: Hw *
    private List<GradleParser.MavenModule> f24035Hw;

    /* renamed from: v5 *
    private List<GradleParser.C5102m> f24037v5;

    public C5492b(Activity activity, List<GradleParser.MavenModule> list, List<GradleParser.C5102m> list2, Runnable runnable) {
        this.f24033DW = activity;
        this.f24034FH = runnable;
        this.f24035Hw = list;
        this.f24037v5 = list2;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:32|33|34|35|36|37|38|39|40|41|42|(3:81|48|79)|59|85) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:29|30|31|32|33|34|35|36|37|38|39|40|41|42|(3:81|48|79)|59|85) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00f6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00fb */
    /* renamed from: j6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Void call() {
            /*
                r17 = this;
                r0 = r17
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.List<ir$m> r2 = r0.f24037v5
                java.util.Iterator r2 = r2.iterator()
            L_0x000d:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x0023
                java.lang.Object r3 = r2.next()
                ir$m r3 = (p041.C5089ir.C5102m) r3
                boolean r4 = r1.contains(r3)
                if (r4 != 0) goto L_0x000d
                r1.add(r3)
                goto L_0x000d
            L_0x0023:
                java.util.List<ir$g> r2 = r0.f24035Hw
                java.util.Iterator r2 = r2.iterator()
                r3 = 0
                r5 = 0
                r6 = 0
            L_0x002c:
                boolean r7 = r2.hasNext()
                if (r7 == 0) goto L_0x0158
                java.lang.Object r7 = r2.next()
                ir$g r7 = (p041.C5089ir.C5096g) r7
                java.util.Iterator r8 = r1.iterator()
            L_0x003c:
                boolean r9 = r8.hasNext()
                if (r9 == 0) goto L_0x0143
                java.lang.Object r9 = r8.next()
                ir$m r9 = (p041.C5089ir.C5102m) r9
                java.lang.String r10 = p041.C5560pe.m40880j6((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7)     // Catch:{ IOException -> 0x013a }
                java.lang.String r11 = p041.C5560pe.m40868DW((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7)     // Catch:{ IOException -> 0x013a }
                ot r12 = p041.C5476ot.this     // Catch:{ IOException -> 0x013a }
                r12.downloadFile((java.lang.String) r10, (java.lang.String) r11, (boolean) r3)     // Catch:{ IOException -> 0x013a }
                java.io.File r10 = new java.io.File     // Catch:{ IOException -> 0x013a }
                r10.<init>(r11)     // Catch:{ IOException -> 0x013a }
                boolean r10 = r10.exists()     // Catch:{ IOException -> 0x013a }
                if (r10 == 0) goto L_0x0133
                iv r10 = new iv     // Catch:{ IOException -> 0x013a }
                r10.<init>()     // Catch:{ IOException -> 0x013a }
                ny r10 = r10.mo21500J0(r11)     // Catch:{ IOException -> 0x013a }
                iv r10 = (p041.C5111iv) r10     // Catch:{ IOException -> 0x013a }
                java.lang.String r11 = r7.f22612Hw     // Catch:{ IOException -> 0x013a }
                java.lang.String r10 = r10.mo21217FH(r11)     // Catch:{ IOException -> 0x013a }
                if (r10 == 0) goto L_0x012e
                java.lang.String r11 = ".pom"
                java.lang.String r11 = p041.C5560pe.m40881j6((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r11)     // Catch:{ IOException -> 0x013a }
                java.lang.String r12 = ".pom"
                java.lang.String r12 = p041.C5560pe.m40869DW((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r12)     // Catch:{ IOException -> 0x013a }
                java.lang.String r13 = ".aar"
                java.lang.String r13 = p041.C5560pe.m40881j6((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r13)     // Catch:{ IOException -> 0x013a }
                java.lang.String r14 = ".aar"
                java.lang.String r14 = p041.C5560pe.m40869DW((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r14)     // Catch:{ IOException -> 0x013a }
                java.lang.String r15 = ".jar"
                java.lang.String r15 = p041.C5560pe.m40881j6((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r15)     // Catch:{ IOException -> 0x013a }
                java.lang.String r4 = ".jar"
                java.lang.String r4 = p041.C5560pe.m40869DW((p041.C5089ir.C5102m) r9, (p041.C5089ir.C5096g) r7, (java.lang.String) r10, (java.lang.String) r4)     // Catch:{ IOException -> 0x013a }
                java.io.File r9 = new java.io.File     // Catch:{ IOException -> 0x013a }
                r9.<init>(r4)     // Catch:{ IOException -> 0x013a }
                boolean r9 = r9.exists()     // Catch:{ IOException -> 0x013a }
                if (r9 != 0) goto L_0x00ad
                java.io.File r9 = new java.io.File     // Catch:{ IOException -> 0x013a }
                r9.<init>(r14)     // Catch:{ IOException -> 0x013a }
                boolean r9 = r9.exists()     // Catch:{ IOException -> 0x013a }
                if (r9 == 0) goto L_0x00b8
            L_0x00ad:
                java.io.File r9 = new java.io.File     // Catch:{ IOException -> 0x013a }
                r9.<init>(r12)     // Catch:{ IOException -> 0x013a }
                boolean r9 = r9.exists()     // Catch:{ IOException -> 0x013a }
                if (r9 != 0) goto L_0x0129
            L_0x00b8:
                ot r9 = p041.C5476ot.this     // Catch:{ IOException -> 0x013a }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x013a }
                r3.<init>()     // Catch:{ IOException -> 0x013a }
                r16 = r1
                java.lang.String r1 = r7.f22614j6     // Catch:{ IOException -> 0x013c }
                r3.append(r1)     // Catch:{ IOException -> 0x013c }
                java.lang.String r1 = ":"
                r3.append(r1)     // Catch:{ IOException -> 0x013c }
                java.lang.String r1 = r7.f22611DW     // Catch:{ IOException -> 0x013c }
                r3.append(r1)     // Catch:{ IOException -> 0x013c }
                java.lang.String r1 = ":"
                r3.append(r1)     // Catch:{ IOException -> 0x013c }
                r3.append(r10)     // Catch:{ IOException -> 0x013c }
                java.lang.String r1 = r3.toString()     // Catch:{ IOException -> 0x013c }
                int r3 = r6 + 1
                int r6 = r6 * 100
                java.util.List<ir$g> r10 = r0.f24035Hw     // Catch:{ IOException -> 0x0125 }
                int r10 = r10.size()     // Catch:{ IOException -> 0x0125 }
                int r6 = r6 / r10
                r10 = 0
                r9.m40535j6((java.lang.String) r1, (int) r6, (int) r10)     // Catch:{ IOException -> 0x0123 }
                ot r1 = p041.C5476ot.this     // Catch:{ IOException -> 0x0123 }
                r9 = 1
                r1.downloadFile((java.lang.String) r11, (java.lang.String) r12, (boolean) r9)     // Catch:{ IOException -> 0x0127 }
                ot r1 = p041.C5476ot.this     // Catch:{ IOException -> 0x00f6 }
                r1.downloadFile((java.lang.String) r13, (java.lang.String) r14, (boolean) r9)     // Catch:{ IOException -> 0x00f6 }
            L_0x00f6:
                ot r1 = p041.C5476ot.this     // Catch:{ IOException -> 0x00fb }
                r1.downloadFile((java.lang.String) r15, (java.lang.String) r4, (boolean) r9)     // Catch:{ IOException -> 0x00fb }
            L_0x00fb:
                java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0127 }
                r1.<init>(r4)     // Catch:{ IOException -> 0x0127 }
                boolean r1 = r1.exists()     // Catch:{ IOException -> 0x0127 }
                if (r1 != 0) goto L_0x0111
                java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0127 }
                r1.<init>(r14)     // Catch:{ IOException -> 0x0127 }
                boolean r1 = r1.exists()     // Catch:{ IOException -> 0x0127 }
                if (r1 == 0) goto L_0x0138
            L_0x0111:
                java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0127 }
                r1.<init>(r12)     // Catch:{ IOException -> 0x0127 }
                boolean r1 = r1.exists()     // Catch:{ IOException -> 0x0127 }
                if (r1 == 0) goto L_0x0138
                r6 = r3
                r1 = r16
                r3 = 0
                r5 = 1
                goto L_0x002c
            L_0x0123:
                r9 = 1
                goto L_0x0127
            L_0x0125:
                r9 = 1
                r10 = 0
            L_0x0127:
                r6 = r3
                goto L_0x013e
            L_0x0129:
                r16 = r1
                r9 = 1
                r10 = 0
                goto L_0x0137
            L_0x012e:
                r16 = r1
                r9 = 1
                r10 = 0
                goto L_0x0137
            L_0x0133:
                r16 = r1
                r9 = 1
                r10 = 0
            L_0x0137:
                r3 = r6
            L_0x0138:
                r6 = r3
                goto L_0x013e
            L_0x013a:
                r16 = r1
            L_0x013c:
                r9 = 1
                r10 = 0
            L_0x013e:
                r1 = r16
                r3 = 0
                goto L_0x003c
            L_0x0143:
                r16 = r1
                r9 = 1
                r10 = 0
                boolean r1 = java.lang.Thread.interrupted()
                if (r1 != 0) goto L_0x0152
                r1 = r16
                r3 = 0
                goto L_0x002c
            L_0x0152:
                java.lang.InterruptedException r1 = new java.lang.InterruptedException
                r1.<init>()
                throw r1
            L_0x0158:
                ot$b$1 r1 = new ot$b$1
                r1.<init>(r5)
                com.aide.p036ui.C2498f.m18314j6((java.lang.Runnable) r1)
                r1 = 0
                return r1
            */
        throw new UnsupportedOperationException("Method not decompiled: p041.C5476ot.C5492b.call():java.lang.Void");
    }
    

    /* renamed from: ot$b *
    private class C5492b implements Callable<Void> {

        /* renamed from: DW *
        private Activity f24033DW;
        /* access modifiers changed from: private */

    /* renamed from: ot$e */
    public interface C5502e {
        /* renamed from: J0 */
        void mo21385J0();

        /* renamed from: J8 */
        void mo21386J8();
    }

}
