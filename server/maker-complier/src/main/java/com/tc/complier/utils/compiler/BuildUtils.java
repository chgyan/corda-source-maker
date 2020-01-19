package com.tc.complier.utils.compiler;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildUtils {

    static Logger logger = LoggerFactory.getLogger(BuildUtils.class);

    static String encoding = "utf-8";

    /**
     *
     *
     * @param basePath 源文件目录
     * @param jarReyOnPath lib包目录
     * @param jarFileName 生成jar文件名
     * @param jarFilePath 生成jar包目录
     * @return
     */
    public static boolean build(String basePath, String jarReyOnPath, String jarFileName, String jarFilePath) {
        String sourcePath = "";
        String classPath = "";
        try {
            // 将RMI需要使用的JAVA文件拷贝到制定目录中
            logger.info("分隔符:" + File.separator);
            logger.info("资源拷贝......");
            sourcePath = jarFilePath + File.separator + "source";
            copySource(sourcePath, basePath);//拷贝资源
            logger.info("资源拷贝结束");
            logger.info("编译资源......");
            //编译java文件
            classPath = jarFilePath + File.separator + "class";
            try {
                if(!CompilerUtils.compiler(sourcePath, classPath, basePath, encoding, jarReyOnPath)){
                    logger.info("编译资源失败");
                    return false;
                };
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("编译资源结束");
            logger.info("生成jar......");
            //生成jar文件
            CreateJarUtils.createTempJar(classPath, jarFilePath, jarFileName);
            logger.info("生成jar完成");
            //删除临时文件
            ExeSuccess(sourcePath, classPath);
            return true;
        } catch (IOException e) {
            logger.error("编译打包错误", e);
            deleteTempFile(sourcePath, classPath);

        } finally {
        }
        return false;
    }

    private static void ExeSuccess(String sourcePath, String classPath) {
        final String sourcedir = sourcePath;
        final String classdir = classPath;
        //程序结束后，通过以下代码删除生成的文件
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                deleteTempFile(sourcedir, classdir);
                logger.info("***************执行完毕**********************");
            }
        });
    }

    private static void deleteTempFile(String sourcePath, String classPath) {
        //程序结束后，通过以下代码删除生成的class 和java文件
        try {
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) {
                FileUtils.deleteDirectory(sourceFile);
            }
            File classFile = new File(classPath);
            if (classFile.exists()) {
                FileUtils.deleteDirectory(classFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copySource(String sourcePath, String basePath) throws IOException {
        Set<String> srcFiles = new HashSet<>();
        filterJavaDir(basePath, new File(basePath),srcFiles);
        for (String f : srcFiles) {
            String path = f.replace("/", File.separator);
            logger.info(path);
            File srcFile = new File(basePath + path);
            File targetFile = new File(sourcePath + path);
            FileUtils.copyDirectory(srcFile, targetFile, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    logger.info(pathname.getName());
                    return pathname.getName().endsWith(".java");
                }
            });
        }
    }

    private static void filterJavaDir(String basePath, File base, Set<String> set) throws IOException{
        if(base.isDirectory()){
            for(File file: base.listFiles()){
                if(file.isDirectory()) {
                    filterJavaDir(basePath, file, set);
                }else{
                    set.add(base.getPath().substring(basePath.length()));
                }
            }
        }
    }
}
