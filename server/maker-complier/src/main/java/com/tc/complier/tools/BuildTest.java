package com.tc.complier.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import com.tc.complier.utils.compiler.BuildUtils;
import com.tc.complier.utils.compiler.CompilerUtils;
import com.tc.complier.utils.compiler.CreateJarUtils;
import org.apache.commons.io.FileUtils;

public class BuildTest {
    //资源文件根路径
    static String basePath = "D:\\tc_chen\\work_test\\source_marker";
    //生成jar文件路径
    static String jarFilePath = "D:\\tc_chen\\work_test\\source_marker\\dist";

    static String jarReyOnPath = "D:\\tc_chen\\work_test\\auto_source\\lib";
    static String jarFileName = "traceability.1.0.0";

    public static void main(String[] args) {
        BuildUtils.build(basePath,jarReyOnPath,jarFileName,jarFilePath);
    }
}
