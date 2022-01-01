package com.freedomcoder.fide.gradle.utils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StreamUtils {

    private static final String TAG = "Streams";

    public static String convertStreamToString(InputStream is) throws IOException {
        InputStreamReader r = new InputStreamReader(is);
        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024];
        try {
            for (int n; (n = r.read(buffer)) != -1; )
                sw.write(buffer, 0, n);
        } finally {
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.toString();
    }

    public static void closeStream(Object oin) {
        if (oin != null)
            try {
                if (oin instanceof InputStream)
                    ((InputStream) oin).close();
                if (oin instanceof OutputStream)
                    ((OutputStream) oin).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void transfer(InputStream in, OutputStream out) {
        try {
            byte[] dataBuffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
    }

    public static void transferAndClose(InputStream in, OutputStream out) {
        try {
            byte[] dataBuffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    public static String transferCalcMD5(InputStream in, OutputStream out) {
        try {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                //HyperLog.e(TAG, "Exception while getting digest", e);
                return null;
            }

            byte[] dataBuffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                digest.update(dataBuffer, 0, bytesRead);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            // handle exception
        }
        return null;
    }

    public static String transferCalcMD5AndClose(InputStream in, OutputStream out) {
        try {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                //HyperLog.e(TAG, "Exception while getting digest", e);
                return null;
            }

            byte[] dataBuffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            // handle exception
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }
}
