package com.sumzupp.backendapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final String TAG = "ZipUtils : ";

    private List<String> files = new ArrayList<>();

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public static void makeZipFile(String path){
        String zipFolder = path + ".zip";

        String sourceFolder = path;

        ZipUtils appZip = new ZipUtils();
        appZip.generateFileList(new File(sourceFolder), sourceFolder);
        appZip.zipIt(zipFolder, sourceFolder);
    }

    private void zipIt(String zipFolder, String sourceFolder) {
        byte[] buffer = new byte[1024];
        String source;

        FileOutputStream fileOutputStream = null;

        ZipOutputStream zipOutputStream = null;

        try {
            try {
                source = sourceFolder.substring(sourceFolder.lastIndexOf(File.separator) + 1, sourceFolder.length());
            } catch (Exception e) {
                source = sourceFolder;
            }

            fileOutputStream = new FileOutputStream(zipFolder);
            zipOutputStream = new ZipOutputStream(fileOutputStream);

            errorLogger.error(TAG + "Output to Zip : " + zipFolder);
            FileInputStream fileInputStream = null;

            for (String file : this.files) {
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zipOutputStream.putNextEntry(ze);

                try {
                    fileInputStream = new FileInputStream(sourceFolder + File.separator + file);

                    int length;

                    while ((length = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, length);
                    }
                } finally {
                    fileInputStream.close();
                }
            }

            zipOutputStream.closeEntry();
            errorLogger.error(TAG + "Folder successfully compressed");
        } catch (IOException e) {
            errorLogger.error(TAG + "Exception raised in zipping the folder : " + e);
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                errorLogger.error(TAG + "Exception raised in closing the FileOutputStream or ZipOutputStream : " + e);
            }
        }
    }

    private void generateFileList(File node, String sourceFolder) {
        if (node.isFile()) {
            files.add(generateZipEntry(node.toString(), sourceFolder));
        }

        if (node.isDirectory()) {
            String[] subNode = node.list();

            for (String filename : subNode) {
                generateFileList(new File(node, filename), sourceFolder);
            }
        }
    }

    private String generateZipEntry(String file, String sourceFolder) {
        return file.substring(sourceFolder.length() + 1, file.length());
    }

}