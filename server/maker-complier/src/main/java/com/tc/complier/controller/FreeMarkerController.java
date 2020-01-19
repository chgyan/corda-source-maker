package com.tc.complier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tc.complier.common.GenEvent;
import com.tc.complier.model.FieldModel;
import com.tc.complier.service.BuildService;
import com.tc.complier.service.GenService;
import com.tc.complier.utils.CommonUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("freemarker")
public class FreeMarkerController {

    Logger logger = LoggerFactory.getLogger(FreeMarkerController.class);

    @Autowired
    GenService genService;

    @Autowired
    BuildService buildService;

    @RequestMapping("gen")
    public String gen(String tableName, String packageName, String command, String tableInfo, boolean complier, String jarName){
        String result = "";
        if(genSourceCode(tableName, packageName, command, tableInfo)){
            result = "代码生成成功 |"; //用 | 来充当换行符，后续再前端可以随意根据需要替换
            if(complier && StringUtils.isEmpty(jarName)){
                result += "jarName为NULL，停止编译生成jar包";
            }else{
                if(complier(jarName)){
                    result += "编译成功";
                }else{
                    result += "编译失败，请查看日志";
                }
            }
        }else{
            result = "代码生成失败, 请查看日志";
        }

        return result;
    }

    private boolean genSourceCode(String tableName, String packageName, String command, String tableInfo) {
        boolean success = false;
        try {
            Map<String, Object> root = new HashMap<>();
            // 获取表个相关讯息Map
            root.put("packageName", packageName);
            root.put("command", command);
            tableName = tableName.toLowerCase();
            root.put("tableName", tableName);
            String entityName = CommonUtil.underlineToHump(CommonUtil.cap_first(tableName));
            root.put("entityName", entityName);
            List<FieldModel> list = JSONObject.parseArray(tableInfo, FieldModel.class);
            root.put("fields", list);

            //参与者
            List<String> participants = new ArrayList();
            for (FieldModel fieldModel : list) {
                if (fieldModel.isParticipant()) {
                    participants.add(fieldModel.getName());
                }
            }
            root.put("participants", participants);

            success = genService.gen(root);
        } catch (Exception e) {
            logger.error("生成代码出现异常", e);
        }

        return success;
    }

    private boolean complier(String jarName){
        return buildService.build(jarName);
    }
}