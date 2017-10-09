import com.ds.zxm.Application;
import com.ds.zxm.service.LotteryService;
import com.ds.zxm.thread.LotteryUpdateThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class SpringTest{
  
    @Resource
	LotteryService lotteryService;
    @Autowired
    LotteryUpdateThread lotteryUpdateThread;
   /* @Autowired
    protected ApplicationContext ctx;*/


    @Test  
    public void test() {}
    
    @Test
    public void testLottery() {
    	try {
			lotteryService.fetchLotteryInfo("RDSSC", "08-10-2017-0000", 3);
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
    
   /* @Test
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
        		ILotteryStrategy last2LotteryStrategy = (ILotteryStrategy)ctx.getBean("Last2LotteryStrategy");
        		last2LotteryStrategy.runStrategy("20170604-0031", "20170605-0061", sr);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testLotteryStrategyWithNos() {
    	try {
    		ILotteryStrategy last2LotteryStrategy = (ILotteryStrategy)ctx.getBean("Last2LotteryStrategy");
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
    }*/
}