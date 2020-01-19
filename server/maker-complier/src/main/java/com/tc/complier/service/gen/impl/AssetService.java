package com.tc.complier.service.gen.impl;

import com.tc.complier.common.GenEvent;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AssetService extends AbstractGenService {

    @Autowired
    protected AssetService(Configuration freeMarkerConfiguration1) {
        super("asset", freeMarkerConfiguration1);
    }

    @Override
    @EventListener
    public void genEvent(GenEvent event) throws Exception{
        super.genEvent(event);
    }
}
