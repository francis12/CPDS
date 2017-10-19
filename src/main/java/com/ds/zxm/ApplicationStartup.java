package com.ds.zxm;

import com.ds.zxm.util.DsUtil;
import com.sun.org.apache.bcel.internal.generic.DSUB;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DsUtil.init();
    }
}