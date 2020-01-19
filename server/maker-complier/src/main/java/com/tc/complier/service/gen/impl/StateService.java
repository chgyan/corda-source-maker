package com.tc.complier.service.gen.impl;

import com.tc.complier.common.GenEvent;
import org.springframework.beans.factory.annotation.Autowired;
import freemarker.template.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StateService  extends AbstractGenService {

    @Autowired
    protected StateService(Configuration freeMarkerConfiguration1) {
        super("state", freeMarkerConfiguration1);
    }

    @Override
    @EventListener
    public void genEvent(GenEvent event) throws Exception{
        super.genEvent(event);
    }
}
