package com.tc.complier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tc.complier.entity.CordaModelEntity;

public interface ModelService extends IService<CordaModelEntity> {

    Page<CordaModelEntity> queryListByPage(Integer offset, Integer limit, String name, String sort,
                                           Boolean order);

}
