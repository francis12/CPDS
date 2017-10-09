import java.util.ArrayList;
import java.util.List;  
  
import org.apache.log4j.LogManager;  
import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lottery.model.LotteryDO;
import com.lottery.service.ILotteryService;
import com.lottery.strategy.ILotteryStrategy;
import com.lottery.thread.LotteryUpdateThread;
import com.stock.model.StockDO;
import com.stock.service.IStockService;  
  
  
  
  
@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(locations = {"classpath:spring.xml"})  
public class SpringTest extends AbstractJUnit4SpringContextTests {  
  
    @Autowired  
    private IStockService service;  
    @Autowired()
    private ILotteryService lotteryService;
    @Autowired
    LotteryUpdateThread lotteryUpdateThread;
    @Autowired
    protected ApplicationContext ctx;
 
    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
 
    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
 
    protected ApplicationContext getContext() {
        return applicationContext;
    }
    
    @Test  
    public void test() {  

        
    	StockDO stock = service.queryStockDetailByStockCode("002751");
        System.out.println(stock.getStockName());

    	LotteryDO lottery = new LotteryDO();
    	lottery.setLotteryCode("RDSSC");
    	lottery.setLotteryName("瑞典时时彩");
    	lottery.setCreator("admin");
    	lottery.setModifier("admin");
    	
    	try {
			lotteryService.saveLottery(lottery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    
    @Test
    public void testLottery() {
    	try {
			lotteryService.fetchLotteryInfo("RDSSC", "22-06-2017-0000", 3);
			Thread.sleep(1000 * 60 * 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Test
    public void testUpdateLottery2Latest() {    
    	//junit 只管自己的执行，不会关心由junit启动的后台进程，也就是说junit执行完成后如果启动线程未执行完，则不会继续执行
    	//lotteryUpdateThread.run();
		try {
			lotteryService.fetch2LatestFromMaxRecord();
			Thread.sleep(1000 * 60 * 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testLotteryStrategy() {
    	try {
    		List<String> strS = new ArrayList<String>();
    		strS.add("012-012");
    		strS.add("012-345");
    		strS.add("012-678");
    		strS.add("345-012");
    		strS.add("345-345");
    		strS.add("345-678");
    		strS.add("678-012");
    		strS.add("678-345");
    		strS.add("678-678");
    		for (String sr : strS) {
        		ILotteryStrategy last2LotteryStrategy = (ILotteryStrategy)this.getBean("Last2LotteryStrategy");
        		last2LotteryStrategy.runStrategy("20170604-0031", "20170605-0061", sr);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testLotteryStrategyWithNos() {
    	try {
    		ILotteryStrategy last2LotteryStrategy = (ILotteryStrategy)this.getBean("Last2LotteryStrategy");
    		List<String> strS = new ArrayList<String>();
    		strS.add("012-012");
    		strS.add("012-345");
    		strS.add("012-678");
    		strS.add("345-012");
    		strS.add("345-345");
    		strS.add("345-678");
    		strS.add("678-012");
    		strS.add("678-345");
    		strS.add("678-678");
    		last2LotteryStrategy.runStrategy("20170602-0031", "20170603-0061", strS);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}