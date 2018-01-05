package com.ds.zxm;

import com.ds.zxm.service.BetService;
import com.ds.zxm.service.TecentOnlineService;
import com.ds.zxm.util.DsUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.ds.zxm.mapper")
public class Application implements EmbeddedServletContainerCustomizer {
    private static Logger logger = Logger.getLogger(Application.class);

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    /**
     * Start
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        BetService betService = (BetService)context.getBean("betService");
        TecentOnlineService tecentOnlineService = (TecentOnlineService)context.getBean("tecentOnlineService");



        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    betService.updateLotteryStatus("chongqing");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            public void run() {
                try {
                    betService.updateLotteryStatus("n198_60s");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable3 = new Runnable() {
            public void run() {
                try {
                    betService.updateLotteryStatus("rd60s");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable4 = new Runnable() {
            public void run() {
                try {
                    betService.updateLotteryStatus("flb90s");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable authRunnable = new Runnable() {
            public void run() {
                DsUtil.init();
            }
        };

        Runnable qq5star = new Runnable() {
            public void run() {
                try {
                    List<String> qq5start = tecentOnlineService.generateNextPrizeNo();
                    for(String num : qq5start) {
                        System.out.print(num + " ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
       /* service.scheduleAtFixedRate(runnable, 1, 12, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(runnable2, 1, 12, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(runnable3, 1, 12, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(runnable4, 1, 12, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(authRunnable, 0, 30, TimeUnit.MINUTES);
        service.scheduleAtFixedRate(qq5star, 1, 5, TimeUnit.SECONDS);*/
        logger.info("SpringBoot Start Success");
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

    }
}
