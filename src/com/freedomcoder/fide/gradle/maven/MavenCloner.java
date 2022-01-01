package com.freedomcoder.fide.gradle.maven;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MavenCloner {
    public static final List<GradleParser.Repository> repos = new ArrayList<GradleParser.Repository>();

    static {
        GradleParser.Repository jetpack = new GradleParser.Repository("https://jcenter.bintray.com");
        GradleParser.Repository mavenCentral = new GradleParser.Repository("http://repo.maven.apache.org/maven2");
        GradleParser.Repository googlerepo = new GradleParser.Repository("https://dl.google.com/dl/android/maven2"); // broken

        repos.add(jetpack);
        repos.add(mavenCentral);
        repos.add(googlerepo);
    }

    public static boolean cloneModule(MavenResolver resolver, GradleParser.MavenModule m) {
        //boolean success = false;
        for (GradleParser.Repository repo : repos) {
            if (cloneModule(resolver, repo, m)) return true;
        }
        //return success;
        return false;
    }

    public static boolean cloneModule(MavenResolver resolver, GradleParser.Repository from, GradleParser.MavenModule m) {
        String remoteMetadata = MavenResolver.getRemoteMetadataPath(from, m);
        String localMetadata = MavenResolver.getLocalMetadataPath(from, m);

        try {
            System.out.println("Fetching metadata");
            download(remoteMetadata, localMetadata);
            String ver;
            if (FileUtils.isExistingFileNotJar(localMetadata) && (ver = new MetadataParser().getSingletone(localMetadata).getMatchingVersion(m.version)) != null) {
                String[] suffixes = new String[]{
                        ".pom",
                        ".aar",
                        ".jar"
                };
                boolean success = false;
                for (String suffix : suffixes) {
                    String remotefile = MavenResolver.getRemoteModulePath(from, m, ver, suffix);
                    String localfile = MavenResolver.getLocalModulePath(from, m, ver, suffix);
                    //if(new File(localfile).exists()) continue;
                    try {
                        download(remotefile, localfile);
                        success = true;
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                }
                return success;
            } else return false;
        } catch (IOException e) {//e.printStackTrace();
            return false;
        }
    }

    private static void download(String remoteMetadata, String localMetadata) throws IOException {/*
		 MultipartRequestor req = new MultipartRequestor(remoteMetadata);
		 req.writeResponse(new FileOutputStream(localMetadata));*/

        URL url = new URL(remoteMetadata);
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long completeFileSize = httpConnection.getContentLength();

        java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
        File file = new File(localMetadata);
        file.getParentFile().mkdirs();
        java.io.FileOutputStream fos = new java.io.FileOutputStream(
                file);
        java.io.BufferedOutputStream bout = new BufferedOutputStream(
                fos, 1024);
        byte[] data = new byte[4086];
        long downloadedFileSize = 0;
        int x = 0;
        int prevprogress = 0;
        int maxprogress = 50;
        while ((x = in.read(data, 0, 4086)) >= 0) {
            downloadedFileSize += x;

            // calculate progress
            final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize)) * ((double) maxprogress));

            // update progress bar
            for (; prevprogress < currentProgress; prevprogress++) {
                System.out.print('#');
            }

            bout.write(data, 0, x);
        }
        System.out.println(" " + prevprogress * 2 + '%' + "	" + file.getName());
        bout.close();
        in.close();
    }
}
