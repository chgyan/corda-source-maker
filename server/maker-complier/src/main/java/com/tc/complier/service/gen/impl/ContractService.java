package com.tc.complier.service.gen.impl;

import com.tc.complier.common.GenEvent;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContractService extends AbstractGenService {

    @Autowired
    protected ContractService(Configuration freeMarkerConfiguration1) {
        super("contract", freeMarkerConfiguration1);
    }

    @Override
    @EventListener
    public void genEvent(GenEvent event) throws Exception{
        super.genEvent(event);
    }
}
