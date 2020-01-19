package com.tc.complier.utils.compiler;

/**
* @author zhangchengping
* @PackageName:com.demo.wms.tasks.web.listener
* @ClassName:PackageUtil
* @Description: 生成jar文件
* @date 2019-06-06 22:59
* @Version 1.0
*/


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class CreateJarUtils {

    /**
     * @param rootPath    class文件根目录
     * @param targetPath  需要将jar存放的路径
     * @param jarFileName jar文件的名称
     * @Description: 根据class生成jar文件
     * @Author zhangchengping
     * @Date 2019-06-06 23:56
     */
    public static void createTempJar(String rootPath, String targetPath, String jarFileName) throws IOException {
        if (!new File(rootPath).exists()) {
            throw new IOException(String.format("%s路径不存在", rootPath));
        }
        if (StringUtils.isBlank(jarFileName)) {
            throw new NullPointerException("jarFileName为空");
        }
        //生成META-INF文件
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
        //manifest.getMainAttributes().putValue("Main-Class", "Show");//指定Main Class
        //创建临时jar
        File jarFile = File.createTempFile("edwin-", ".jar", new File(System.getProperty("java.io.tmpdir")));
        JarOutputStream out = new JarOutputStream(new FileOutputStream(jarFile), manifest);
        createTempJarInner(out, new File(rootPath), "");
        out.flush();
        out.close();
        //程序结束后，通过以下代码删除生成的jar文件
       /* Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                jarFile.delete();
            }
        });*/
        //生成目标路径
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) targetFile.mkdirs();
        File targetJarFile = new File(targetPath + File.separator + jarFileName + ".jar");
        if (targetJarFile.exists() && targetJarFile.isFile()) targetJarFile.delete();
        FileUtils.moveFile(jarFile, targetJarFile);
        //jarFile.renameTo(new File(""));
    }

    /**
     * @param out  文件输出流
     * @param f    文件临时File
     * @param base 文件基础包名
     * @return void
     * @Description: 生成jar文件
     * @Author zhangchengping
     * @Date 2019-06-07 00:02
     */
    private static void createTempJarInner(JarOutputStream out, File f,
                                           String base) throws IOException {

        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (base.length() > 0) {
                base = base + "/";
            }
            for (int i = 0; i < fl.length; i++) {
                createTempJarInner(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new JarEntry(base));
            FileInputStream in = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int n = in.read(buffer);
            while (n != -1) {
                out.write(buffer, 0, n);
                n = in.read(buffer);
            }
            in.close();
        }
    }
}
