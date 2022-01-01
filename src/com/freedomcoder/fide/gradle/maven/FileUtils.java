package com.freedomcoder.fide.gradle.maven;

import com.freedomcoder.fide.gradle.utils.StreamUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* renamed from: qc */
public class FileUtils {

    /* renamed from: j6 */
    public static boolean m41303j6(long j, long j2) {
        long abs = Math.abs(j - j2);
        if (abs <= 1000) {
            return true;
        }
        if (abs % 3600000 <= 0) {
            return abs <= 46800000;
        } else if ((abs - 1000) % 3600000 == 0) {
            return abs <= 46800000;
        } else return (1000 + abs) % 3600000 == 0 && abs <= 46800000;
    }

    /* renamed from: DW */
    public static boolean m41266DW(String str) {
        FileInputStream fileInputStream = null;
        if (isJavaOrClassInsideJar(str)) {
            return false;
        }
        long length = new File(str).length();
        int i = 8000 < length ? 8000 : (int) length;
        try {
            fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[i];
            new DataInputStream(fileInputStream).readFully(bArr);
            for (byte b : bArr) {
                if (b == 0) {
                    fileInputStream.close();
                    return true;
                }
            }
            fileInputStream.close();
            return false;
        } catch (IOException unused) {
            return false;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
            }
//            throw th;
        }
        return false;
    }

    /* renamed from: FH *
    public static Reader m41268FH(String str) {
        if (isExistingFileNotJar(str)) {
            return new FileReader(str);
        }
        if (isJavaOrClassInsideJar(str)) {
            return m41290gW(str);
        }
        throw new IOException();
    }

    /* renamed from: j6 *
    public static boolean m41304j6(String str, String str2) {
        Reader FH;
        String readLine;
        try {
            Pattern compile = Pattern.compile(str);
            FH = m41268FH(str2);
            BufferedReader bufferedReader = new BufferedReader(FH);
            do {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    FH.close();
                    return false;
                }
            } while (!compile.matcher(readLine).find());
            FH.close();
            return true;
        } catch (IOException unused) {
            return false;
        } catch (Throwable th) {
            FH.close();
            throw th;
        }
    }

    /* renamed from: er */
    private static String findJarInPath(String str) {
        if (isExistingJar(str)) {
            return str;
        }
        do {
            str = getParentDir(str);
            if (str == null) {
                return null;
            }
        } while (!isExistingJar(str));
        return str;
    }

    /* renamed from: yS */
    private static boolean isExistingJar(String str) {
        return new File(str).isFile() && (str.endsWith(".jar") || str.endsWith(".zip"));
    }

    /* renamed from: gW *
    private static Reader m41290gW(String str) {
        final Reader j6 = f24400j6.mo19896j6(findJarInPath(str), m41262BT(str), (String) null);
        return new Reader() {
            public int read(char[] cArr, int i, int i2) {
                return j6.read(cArr, i, i2);
            }

            public void close() {
                FileUtils.f24400j6.mo19898j6();
            }
        };
    }

    /* renamed from: BT */
    private static String m41262BT(String str) {
        String er = findJarInPath(str);
        if (er.length() == str.length()) {
            return "";
        }
        return str.substring(er.length() + 1);
    }

    /* renamed from: j6 */
    public static String m41297j6(Reader reader) throws IOException {
        char[] cArr = new char[4096];
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int read = reader.read(cArr);
            if (read <= 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(cArr, 0, read);
        }
    }

    /* renamed from: j6 */
    public static void m41302j6(String str, Reader reader) throws IOException {
        FileWriter fileWriter = new FileWriter(str, false);
        fileWriter.append(m41297j6(reader));
        fileWriter.close();
    }

    /* renamed from: Hw */
    public static boolean m41273Hw(String str) {
        return str.equals("/");
    }

    /* renamed from: v5 */
    public static String getParentDir(String str) {
        if (str.length() == 0 || str.equals("/")) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf == 0) {
            return "/";
        }
        if (lastIndexOf == -1) {
            return null;
        }
        return str.substring(0, lastIndexOf);
    }

    /* renamed from: Zo */
    public static String getFileName(String str) {
        return str.substring(str.lastIndexOf('/') + 1);
    }

    /* renamed from: VH */
    public static boolean isExistsOrCompilableInsideJar(String str) {
        return new File(str).exists() || isJavaOrClassInsideJar(str);
    }

    /* renamed from: gn */
    public static boolean isJavaOrClassInsideJar(String str) {
        return findJarInPath(str) != null && isClassOrJavaFile(str);
    }

    /* renamed from: u7 */
    public static boolean isNotJavaOrClassInsideJar(String str) {
        return findJarInPath(str) != null && !isClassOrJavaFile(str);
    }

    public static boolean isClassOrJavaFile(String str) {
        return str.endsWith(".class") || str.endsWith(".java");
    }

    /* renamed from: tp */
    public static boolean isInJar(String str) {
        return findJarInPath(str) != null;
    }

    /* renamed from: EQ */
    public static boolean isDirNotInsideJar(String str) {
        return isDir(str) || isNotJavaOrClassInsideJar(str);
    }

    /* renamed from: we */
    public static boolean isDir(String str) {
        return new File(str).isDirectory();
    }

    /* renamed from: J0 */
    public static boolean isExistingFileNotJar(String str) {
        return new File(str).isFile() && !isExistingJar(str);
    }

    /* renamed from: J8 */
    public static boolean m41275J8(String str) {
        return (isExistingFileNotJar(str) || isJavaOrClassInsideJar(str)) && !isExistingJar(str);
    }

    /* renamed from: Ws */
    public static List<String> listFiles(String str) {
        ArrayList arrayList = new ArrayList();
        for (String next : m41277QX(str)) {
            if (isDirNotInsideJar(next)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    /* renamed from: QX */
    public static List<String> m41277QX(String str) {
        String str2;
        if (isInJar(str)) {
            // List<String> j6 = f24400j6.mo19897j6(findJarInPath(str), m41262BT(str));
            // return j6 == null ? new ArrayList() : j6;
        }
        String[] list = new File(str).list();
        if (list == null) {
            String j62 = m41296j6(new File(str));
            if (!j62.endsWith(File.separator)) {
                j62 = j62 + File.separator;
            }
            String[] strArr = {getSdcard()};
            ArrayList arrayList = new ArrayList();
            for (String file : strArr) {
                String j63 = m41296j6(new File(file));
                if (j63.startsWith(j62) && !j63.equals(j62)) {
                    int indexOf = j63.indexOf(File.separator, j62.length() + 1);
                    if (indexOf < 0) {
                        indexOf = j63.length();
                    }
                    arrayList.add(j63.substring(0, indexOf));
                }
            }
            return arrayList;
        }
        String[] strArr2 = new String[list.length];
        if (str.equals("/")) {
            str2 = "/";
        } else {
            str2 = str + File.separator;
        }
        for (int i = 0; i < strArr2.length; i++) {
            strArr2[i] = str2 + list[i];
        }
        return Arrays.asList(strArr2);
    }

    /* renamed from: DW */
    public static String m41264DW(String str, String str2) {
        if (str.equals(str2)) {
            return "";
        }
        return str2.substring(str.length() + 1);
    }

    /* renamed from: FH */
    public static boolean m41270FH(String str, String str2) {
        if (str2 != null) {
            if (!str2.equals(str)) {
                if (str2.startsWith(str + "/")) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /* renamed from: j6 */
    public static String m41298j6(String str, String str2, String str3) {
        return str3 + str.substring(str2.length());
    }

    /* renamed from: Hw */
    public static void m41272Hw(String str, String str2) throws IOException {
        File file = new File(str2);
        if (file.exists()) {
            throw new IOException(str2 + " already exists");
        } else if (!new File(str).renameTo(file)) {
            throw new IOException(str + " could not be renamed");
        }
    }

    /* renamed from: XL */
    public static void m41283XL(String str) throws IOException {
        if (!new File(str).mkdir()) {
            throw new IOException(str + " could not be created");
        }
    }

    /* renamed from: aM */
    public static void m41288aM(String str) throws IOException {
        if (!new File(str).exists()) {
            new File(str).createNewFile();
            return;
        }
        throw new IOException(str + " already exists");
    }

    /* renamed from: v5 */
    public static void m41312v5(String str, String str2) throws IOException {
        if (!new File(str).exists()) {
            FileWriter fileWriter = new FileWriter(str);
            fileWriter.write(str2);
            fileWriter.close();
            return;
        }
        throw new RuntimeException(str + " already exists");
    }

    /* renamed from: j3 */
    public static void delete(String str) {
        delete(new File(str));
    }

    /* renamed from: DW */
    private static void delete(File file) {
        String[] list;
        if (file.isDirectory() && (list = file.list()) != null) {
            for (String file2 : list) {
                delete(new File(file, file2));
            }
        }
        if (!file.delete()) {
            throw new RuntimeException(file.getPath() + " could not be deleted");
        }
    }

    /* renamed from: Mr */
    public static boolean m41276Mr(String str) {
        return new File(str).mkdirs();
    }

    /* renamed from: U2 */
    public static long m41278U2(String str) {
        return new File(str).lastModified();
    }

    /* renamed from: a8 */
    public static long m41287a8(String str) {
        return new File(str).length();
    }

    /* renamed from: lg */
    public static String m41305lg(String str) {
        String Zo = getFileName(str);
        int lastIndexOf = Zo.lastIndexOf(46);
        if (lastIndexOf < 0) {
            return "";
        }
        return Zo.substring(lastIndexOf + 1);
    }

    /* renamed from: Zo */
    public static String m41286Zo(String str, String str2) {
        return m41270FH(str, str2) ? str2.substring(str.length() + 1) : str2;
    }

    /* renamed from: VH */
    public static String m41280VH(String str, String str2) {
        return m41308u7(str, str2);
    }

    /* renamed from: u7 */
    private static String m41308u7(String str, String str2) {
        String replace = str2.replace("\\\\", "/").replace("\\", "/");
        if (!replace.contains("/")) {
            return str + "/" + replace;
        }
        if (replace.startsWith("/")) {
            replace = ".." + replace;
        }
        String j6 = m41296j6(new File(str, replace));
        return new File(j6).exists() ? j6 : str2;
    }

    /* renamed from: j6 */
    public static String m41296j6(File file) {
        ArrayList<String> arrayList = new ArrayList<>();
        String absolutePath = file.getAbsolutePath();
        for (String str : absolutePath.split("/|\\\\")) {
            if (str.length() != 0 && !".".equals(str)) {
                if (!"..".equals(str)) {
                    arrayList.add(str);
                } else if (arrayList.isEmpty()) {
                    return absolutePath;
                } else {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String append : arrayList) {
            sb.append("/");
            sb.append(append);
        }
        if (absolutePath.endsWith("/")) {
            sb.append("/");
        }
        return sb.toString();
    }

    /* renamed from: DW */
    public static String getSdcard() {
        File externalStorageDirectory = new File("/sdcard/");//Environment.getExternalStorageDirectory();
        return externalStorageDirectory.exists() ? externalStorageDirectory.getPath() : "/mnt/sdcard";
    }

    /* renamed from: Zo *
    public static String m41284Zo() {
        File externalCacheDir = f24399DW.getExternalCacheDir();
        if (externalCacheDir == null) {
            externalCacheDir = f24399DW.getCacheDir();
        }
        return externalCacheDir.getPath();
    }

    /* renamed from: gn */
    public static String m41291gn(String str, String str2) {
        for (String v5 = getParentDir(str); v5 != null; v5 = getParentDir(v5)) {
            if (getFileName(v5).equals(str2)) {
                return v5;
            }
        }
        return null;
    }

    /* renamed from: rN */
    public static boolean m41306rN(String str) {
        return str.startsWith("/") && isDir(getParentDir(str));
    }

    /* renamed from: j6 */
    public static int m41294j6(String str, int i, String... strArr) {
        int i2 = 0;
        if (isDir(str)) {
            try {
                for (String j6 : m41277QX(str)) {
                    i2 += m41294j6(j6, i, strArr);
                    if (i2 >= i) {
                        return i2;
                    }
                }
            } catch (Exception unused) {
            }
            return i2;
        } else if (!isExistingFileNotJar(str)) {
            return 0;
        } else {
            for (String endsWith : strArr) {
                if (str.endsWith(endsWith)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    /* renamed from: j6 */
    public static void unzip(InputStream inputStream, String str, boolean z) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                String str2 = str + File.separator + nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    new File(str2).mkdirs();
                } else if (z || !new File(str2).isFile()) {
                    new File(str2).getParentFile().mkdirs();
                    FileOutputStream fileOutputStream = new FileOutputStream(str2);
                    StreamUtils.transfer(zipInputStream, fileOutputStream);
                    fileOutputStream.close();
                }
            } else {
                return;
            }
        }
    }
}
