package com.freedomcoder.apigen.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static JSONObject parseJsonFile(File from) {
        try {
            FileInputStream stream = new FileInputStream(from);
            try {
                String result = StreamUtils.convertStreamToString(stream);
                try {
                    return new JSONObject(result);
                } catch (JSONException e) {
                }
            } catch (IOException e) {
            }
            StreamUtils.closeStream(stream);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static String calculateMD5(File updateFile) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {

            return null;
        }

        InputStream is;
        try {
            is = new FileInputStream(updateFile);
        } catch (FileNotFoundException e) {

            return null;
        }

        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);

            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            throw new RuntimeException("Unable to process file for MD5", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {

            }
        }
    }
}
