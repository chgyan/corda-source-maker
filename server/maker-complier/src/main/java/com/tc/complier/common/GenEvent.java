package com.tc.complier.common;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class GenEvent extends ApplicationEvent {

    //基本名称
    private final String entityName;
    //保存的基本目录
    private final String baseSavePath;
    //template root
    private final Map<String, Object> root;


    public GenEvent(Object source, String entityName , String baseSavePath, Map<String, Object> root) {
        super(source);
        this.entityName = entityName;
        this.baseSavePath = baseSavePath;
        this.root = root;
    }

    public String getBaseSavePath() {
        return baseSavePath;
    }

    public Map<String, Object> getRoot() {
        return root;
    }

    public String getEntityName() {
        return entityName;
    }
}