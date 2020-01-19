package com.tc.complier.service;

import com.tc.complier.common.GenEvent;
import com.tc.complier.tools.BuildTest;
import com.tc.complier.utils.compiler.BuildUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

@Service
public class GenService {

    @Value("${corda.source.path}")
    private String path;

    @Resource
    private ApplicationContext applicationContext;

    public boolean gen( Map<String, Object> root){
        try {
            String packageName = (String)root.get("packageName");
            String baseSavePath = path + File.separator + packageName.replaceAll("\\.", "/");
            applicationContext.publishEvent(new GenEvent(this, (String) root.get("entityName"), baseSavePath, root));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
