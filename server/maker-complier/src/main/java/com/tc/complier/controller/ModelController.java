package com.tc.complier.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.complier.entity.CordaModelEntity;
import com.tc.complier.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Resource
    ModelService modelService;

    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    public String get(long id){
        return JSONObject.toJSONString(modelService.getById(id));
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public String add(CordaModelEntity cordaModelEntity){
        try {
            modelService.save(cordaModelEntity);
            return "true";
        }catch (Exception e){
            e.printStackTrace();
            return "Exception error";
        }
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public String update(CordaModelEntity cordaModelEntity){
        try {
            modelService.updateById(cordaModelEntity);
            return "true";
        }catch (Exception e){
            e.printStackTrace();
            return "Exception error";
        }
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Integer offset, Integer limit, String name, String sort,
                       Boolean order){
        return  JSONObject.toJSONString(modelService.queryListByPage(offset, limit, name, sort, order));
    }

}
