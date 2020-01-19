package com.tc.complier.service;

import com.tc.complier.utils.compiler.BuildUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BuildService {
    @Value("${corda.source.path}")
    private String path;

    @Value("${corda.build.lib.path}")
    private String jarReyOnPath;

    @Value("${corda.build.jar.path}")
    private String jarFilePath;

    public boolean build(String jarFileName){
        try {
            return BuildUtils.build(path, jarReyOnPath, jarFileName, jarFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
