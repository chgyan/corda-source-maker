package com.tc.complier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc.complier.dao.CordaModelDao;
import com.tc.complier.entity.CordaModelEntity;
import com.tc.complier.service.ModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ModelServiceImpl extends ServiceImpl<CordaModelDao, CordaModelEntity> implements ModelService {

    @Resource
    private CordaModelDao cordaModelDao;

    @Override
    public Page<CordaModelEntity> queryListByPage(Integer offset, Integer limit, String name, String sort, Boolean order) {
        QueryWrapper<CordaModelEntity> wrapper = new QueryWrapper<CordaModelEntity>();
        if (StringUtils.isNoneBlank(sort) && null != order) {
            wrapper.orderBy(true, order, sort);
        }
        if (StringUtils.isNoneBlank(name)) {
            wrapper.like("name", name);
        }
        Page<CordaModelEntity> page = new Page<>(offset, limit);
        return this.page(page, wrapper);
    }
}
