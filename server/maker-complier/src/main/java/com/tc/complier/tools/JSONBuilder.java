package com.tc.complier.tools;

import com.alibaba.fastjson.JSONObject;
import com.tc.complier.model.FieldModel;

import java.util.LinkedList;
import java.util.List;

public class JSONBuilder {

    public static void main(String[] args){
//        traceability();
        iou();


    }

    private static void traceability(){
        List<FieldModel> list = new LinkedList<FieldModel>();
        list.add(new FieldModel("Party", "viewer", true, false, 0, false));
        list.add(new FieldModel("String", "serialNumber", false, false, 0, true));
        list.add(new FieldModel("String", "platfromId", false, false, 0, true));
        list.add(new FieldModel("String", "orderId", false, false, 0, true));
        list.add(new FieldModel("String", "identityId", false, false, 0, true));
        list.add(new FieldModel("String", "expressId", false, false, 0, true));
        list.add(new FieldModel("String", "buyerName", false, false, 0, true));
        list.add(new FieldModel("String", "buyerPhone", false, false, 0, true));
        list.add(new FieldModel("String", "lat", false, false, 0, true));
        list.add(new FieldModel("String", "lng", false, false, 0, true));
        list.add(new FieldModel("String", "locationImg", false, false, 0, true));
        list.add(new FieldModel("String", "productImg", false, false, 1000, true));
        list.add(new FieldModel("String", "productImgSign", false, false, 1000, true));
        list.add(new FieldModel("Long", "updateTime", false, false, 0, false));
        System.out.println(JSONObject.toJSON(list));
    }

    private static void iou(){
        List<FieldModel> list = new LinkedList<FieldModel>();
        list.add(new FieldModel("String", "currencyType", false, false, 0, true));
        list.add(new FieldModel("Long", "amount", false, false, 0, false));
        System.out.println(JSONObject.toJSON(list));
    }
}
