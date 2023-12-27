package cn.cnic.faird.admin.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommonUtils {

    /**
     * download file
     */
    public static void downloadFile(HttpServletResponse response, String filePath) {
        downloadFile(response, filePath, null);
    }

    /**
     * download with fileName
     */
    public static void downloadFile(HttpServletResponse response, String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("UTF-8");
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 OutputStream os = response.getOutputStream()) {
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode
                        (StringUtils.isBlank(fileName) ? filePath.substring(filePath.lastIndexOf('/') + 1).replaceAll("水印.pdf", ".pdf") : fileName, "UTF-8"));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                byte[] buffer = new byte[2048];
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("文档不存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * uuid(32位的)
     *
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ENGLISH);
    }

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * folder to zip
     */
    public static void toZip(String srcDir, OutputStream out) throws RuntimeException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
            File sourceFile = new File(srcDir);
            compress(sourceFile, zipOutputStream, sourceFile.getName());
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        }
    }

    /**
     * folder compress
     */
    public static void compress(File sourceFile, ZipOutputStream zos, String name) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                zos.putNextEntry(new ZipEntry(name + "/"));
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    compress(file, zos, name + "/" + file.getName());
                }
            }
        }
    }
}
