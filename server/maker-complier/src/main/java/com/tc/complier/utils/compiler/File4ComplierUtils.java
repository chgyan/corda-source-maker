package com.tc.complier.utils.compiler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangchengping
 * @PackageName:com.demo.wms.tasks.web.listener
 * @ClassName:s
 * @Description: 文件处理类
 * @date 2019-06-06 19:07
 * @Version 1.0
 */
public class File4ComplierUtils {

    /**
     * @param sourceFilePath
     * @return java.util.List<java.io.File>
     * @Description: 获取目录下所有源文件
     * @Author zhangchengping
     * @Date 2019-06-06 19:47
     */
    public static List<File> getSourceFiles(String sourceFilePath) {

        List<File> sourceFileList = new ArrayList<>();
        try {
            getSourceFiles(new File(sourceFilePath), sourceFileList);
        } catch (Exception e) {
            e.printStackTrace();
            sourceFileList = null;
        }
        return sourceFileList;
    }

    /**
     * @param sourceFilePath
     * @return java.lang.String
     * @Description: 获取目录下所有的jar
     * @Author zhangchengping
     * @Date 2019-06-06 19:46
     */
    public static String getJarFiles(String sourceFilePath) {

        String jars = "";
        try {
            jars = getJarFiles(new File(sourceFilePath), jars);
        } catch (Exception e) {
            e.printStackTrace();
            jars = "";
        }
        return jars;
    }

    /**
     * 查找该目录下的所有的java文件
     *
     * @param sourceFile
     * @param sourceFileList
     * @throws Exception
     */
    private static void getSourceFiles(File sourceFile, List<File> sourceFileList) throws Exception {
        if (!sourceFile.exists()) {
            // 文件或者目录必须存在
            throw new IOException(String.format("%s目录不存在", sourceFile.getPath()));
        }
        if (null == sourceFileList) {
            // 若file对象为目录
            throw new NullPointerException("参数异常");
        }
        if (sourceFile.isDirectory()) {// 若file对象为目录
            File[] childrenDirectoryFiles = sourceFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            for (File file : sourceFile.listFiles()) {
                if (file.isDirectory()) {
                    getSourceFiles(file, sourceFileList);
                } else {
                    sourceFileList.add(file);
                }
            }
        } else {
            sourceFileList.add(sourceFile);
        }
    }

    /**
     * 查找该目录下的所有的jar文件
     *
     * @param sourceFile
     * @throws Exception
     */
    private static String getJarFiles(File sourceFile, String jars) throws Exception {
        if (!sourceFile.exists()) {
            // 文件或者目录必须存在
            throw new IOException("jar目录不存在");
        }
        if (!sourceFile.isDirectory()) {
            // 若file对象为目录
            throw new IOException("jar路径不为目录");
        }
        if (sourceFile.isDirectory()) {
            for (File file : sourceFile.listFiles()) {
                if (file.isDirectory()) {
                    getJarFiles(file, jars);
                } else {
                    jars = jars + file.getPath() + ";";
                }
            }
        } else {
            jars = jars + sourceFile.getPath() + ";";
        }
        return jars;
    }
}