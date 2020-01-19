package com.tc.complier.service.gen.impl;

import com.tc.complier.common.GenEvent;
import com.tc.complier.utils.CommonUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SchemaService extends AbstractGenService {

    @Autowired
    protected SchemaService(Configuration freeMarkerConfiguration1) {
        super("schema", freeMarkerConfiguration1);
    }

    @Override
    @EventListener
    public void genEvent(GenEvent event) throws Exception{
        super.genEvent(event);
        // schemaV1模板
        Template template = freeMarkerConfiguration.getTemplate(CommonUtil.cap_first(modelName) +"V1.ftl");
        CommonUtil.printFile(event.getRoot(), template, event.getBaseSavePath() + File.separator + modelName,
                event.getEntityName() + CommonUtil.cap_first(modelName) + "V1.java");

    }
}
