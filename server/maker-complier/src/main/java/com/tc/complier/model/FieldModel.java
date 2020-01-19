package com.tc.complier.model;

//TODO 括号的内容认为是需要再controller层去判定的
public class FieldModel {
    //类型
    private String type;
    //名称[目前暂定有2个关键字owner, sign)
    private String name;
    //是否是参与者，参与者需要进行签名确认。类型必须是Party类型
    private boolean participant;
    //是否为NULL(不为空的可以加valid判定)
    private boolean nullable;
    //字段长度(只有大于0才有意义. ==0表示用的默认值)
    private int length = 0;
    //是否需要做签名(默认Party不签名, 除了String之外的签名还需要修改模板代码）
    private boolean sign = false;

    public FieldModel() {
    }

    public FieldModel(String type, String name, boolean participant, boolean nullable, int length, boolean sign) {
        this.type = type;
        this.name = name;
        this.participant = participant;
        this.nullable = nullable;
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParticipant() {
        return participant;
    }

    public void setParticipant(boolean participant) {
        this.participant = participant;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}
