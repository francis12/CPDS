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

    public static void main (String[] args) {
        String[] base = {"2","5","8"};
        StringBuffer sb = new StringBuffer();

        for(String item : base) {
            for(int i=0;i<10;i++) {
                sb.append(item + item + i + " ");
            }
            for(int i=0;i<10;i++) {
                String tmp = item + i + item;
                if (!tmp.equals(item + item + item)) {
                    sb.append(tmp + " ");
                }
            }

            for(int i=0;i<10;i++) {
                String tmp = i + item  + item;
                if (!tmp.equals(item + item + item)) {
                    sb.append(tmp + " ");
                }
            }
        }
        System.out.println(sb.toString());
    }
}