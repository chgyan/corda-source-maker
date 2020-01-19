package com.tc.complier.service.gen.impl;

import com.tc.complier.common.GenEvent;
import com.tc.complier.utils.CommonUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.context.event.EventListener;

import java.io.File;

public abstract class AbstractGenService {

    protected final Configuration freeMarkerConfiguration;

    protected final String modelName;

    protected AbstractGenService(String modelName, Configuration freeMarkerConfiguration) {
        this.modelName = modelName;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    public void genEvent(GenEvent event) throws Exception{
        // 获取模板文件
        Template template = freeMarkerConfiguration.getTemplate(CommonUtil.cap_first(modelName) +".ftl");
        CommonUtil.printFile(event.getRoot(), template, event.getBaseSavePath() + File.separator + modelName,
                event.getEntityName() + CommonUtil.cap_first(modelName) + ".java");

    }
}
