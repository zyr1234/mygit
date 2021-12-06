package cn.com.geovis.GeoIotAdaptor.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ResourceUtil {
    private static File temporaryDir;
    public static final String NATIVE_FOLDER_PATH_PREFIX = "sqliteDb";

    public ResourceUtil() {
    }

    public static File extractFileToTempDir(String path) throws IOException {
        initTemporaryDir();
        String[] parts = path.split("/");
        String filename = parts.length > 1 ? parts[parts.length - 1] : null;
        File temp = new File(temporaryDir, filename);

        try {
            InputStream is = ResourceUtil.class.getResourceAsStream(path);
            Throwable var5 = null;

            try {
                Files.copy(is, temp.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (is != null) {
                    if (var5 != null) {
                        try {
                            is.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        is.close();
                    }
                }

            }

            return temp;
        } catch (IOException var18) {
            temp.delete();
            throw var18;
        } catch (NullPointerException var19) {
            temp.delete();
            throw new FileNotFoundException("File " + path + " was not found inside JAR.");
        }
    }

    private static void initTemporaryDir() throws IOException {
        if (temporaryDir == null) {
            temporaryDir = createTempDirectory("sqliteDb");
            temporaryDir.deleteOnExit();
        }

    }

    private static File createTempDirectory(String prefix) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File generatedDir = new File(tempDir, prefix + System.nanoTime());
        if (!generatedDir.mkdir()) {
            throw new IOException("Failed to create temp directory " + generatedDir.getName());
        } else {
            return generatedDir;
        }
    }
}
