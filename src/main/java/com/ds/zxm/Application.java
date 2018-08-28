package com.ds.zxm;

import com.ds.zxm.service.LotteryGenService;
import com.ds.zxm.service.LotteryPrizeScheduleService;
import com.ds.zxm.service.TjSSCScanService;
import com.ds.zxm.util.DateUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@EnableAutoConfiguration
@SpringBootApplication
@WebAppConfiguration
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
        LotteryPrizeScheduleService lotteryPrizeScheduleService = (LotteryPrizeScheduleService)context.getBean("lotteryPrizeScheduleService");
        LotteryGenService lotteryGenService = (LotteryGenService)context.getBean("lotteryGenService");
        TjSSCScanService tjSSCScanService = (TjSSCScanService)context.getBean("tjSSCScanService");

        //启动过程中初始化最近一个半小时数据
        lotteryPrizeScheduleService.batchFetchTCFFCData(1,6);
        //qq官网采集
        Runnable tcPrizeSchedule = new Runnable() {
            public void run() {
                lotteryPrizeScheduleService.startFetchQQprize();
            }
        };

        Date curTime = new Date();
        Runnable orgPrizeSchedule = new Runnable() {
            public void run() {
                lotteryPrizeScheduleService.fetchTcffcPrizeFrom77Org();
            }
        };

        Runnable initCurNo = new Runnable() {
            public void run() {
                lotteryGenService.initCurNO();
            }
        };
        Runnable tjsscScan = new Runnable() {
            public void run() {
                tjSSCScanService.scanTjsscPrizeData();
            }
        };

        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(tcPrizeSchedule, 1, 10, TimeUnit.MILLISECONDS);
       // service.scheduleAtFixedRate(tjsscScan,1,5,TimeUnit.SECONDS);
        //service.execute(tjsscScan);

       //service.execute(orgPrizeSchedule);
        logger.info("SpringBoot Start Success");
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

    }
}
