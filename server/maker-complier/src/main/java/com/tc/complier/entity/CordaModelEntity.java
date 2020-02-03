package com.tc.complier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("corda_model")
public class CordaModelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 描述名称
     */
    @TableField
    private String name;

    /**
     * 表名
     */
    @TableField
    private String tableName;

    /**
     * java包名
     */
    @TableField
    private String packageName;

    /**
     * 数据描述(FieldModel的JSON)
     */
    @TableField
    private String tableInfo;

    /**
     * 命令
     */
    @TableField
    private String command;

    /**
     * 是否编译
     */
    @TableField
    private Boolean complier;

    /**
     * 编译的jar名称
     */
    @TableField
    private String jarName;
}
