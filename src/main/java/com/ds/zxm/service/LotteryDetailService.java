package com.ds.zxm.service;

import com.ds.zxm.mapper.LotteryDetailMapper;
import com.ds.zxm.mapper.StrategyDAO;
import com.ds.zxm.mapper.StrategyDetailDAO;
import com.ds.zxm.mapper.TecentOnlineDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.ArraysUtil;
import com.ds.zxm.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class LotteryDetailService {

    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;
    @Autowired
    private TecentOnlineDAO tecentOnlineDAO;
    @Autowired
    private StrategyDAO strategyDAO;
    @Autowired
    private StrategyDetailDAO strategyDetailDAO;

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryDetailService.class);
    static List<String> prizes;

    static {
        prizes = new ArrayList<>();
        prizes.add("0");
        prizes.add("3");
        prizes.add("6");
        prizes.add("9");
    }


    public void batchGdwDetails(String time) {

        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.setOrderByClause(" time asc");
        tecentOnlineDOCondition.createCriteria().andTimeLike(time + "%");
        List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(tecentOnlineDOCondition);
        for (int i = 2; i < tecentOnlineDOList.size(); i++) {


            Long pre2Num = tecentOnlineDOList.get(i - 2).getOnlineNum();
            Long preNum = tecentOnlineDOList.get(i - 1).getOnlineNum();

            //
            String num = "";
            Long preNum43 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            Long pre2Num43 = Long.valueOf((pre2Num + "").substring((pre2Num + "").length() - 4, (pre2Num + "").length() - 3));

            Long num4 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));

            LotteryDetail lotteryDetail = new LotteryDetail();
            log.info(tecentOnlineDOList.get(i).getTime() + "finished");
            lotteryDetailMapper.insert(lotteryDetail);
        }
    }

    private boolean isPrized(LotteryDetail detail) {
        return false;
    }

    public Map<String, Object> testBatchLotteryDetails(String time) {

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause(" alias_no asc");
        lotteryDetailCondition.createCriteria().andAliasNoLike(time + "%");
        List<LotteryDetail> list = lotteryDetailMapper.queryAllTestData(lotteryDetailCondition);

        /*for (LotteryDetail item : list) {
            if (isPrized(item)) {
                prizeList.add(item);
            }
        }*/
        Map<String, Integer> result = new HashMap<>();
        List<Integer> disList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            String curNo = list.get(i).getAliasNo();
            String preNo = list.get(i - 1).getAliasNo();

            int distance = Integer.valueOf(curNo.substring(curNo.length() - 4)) - Integer.valueOf(preNo.substring(preNo.length() - 4)) - 1;

            timeList.add(curNo);
            disList.add(distance);
            // result.put(curNo, distance);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", timeList);
        map.put("series", disList);
        return map;
    }

    public static Map<String, TradeSchedule> scheMap = null;
/*002 001 1
        003 001 2
        004 001 4
        005 001 8
        006 001 16
        007 001 31
        008 001 60
        009 001 116
        001 001 225
        011 001 436
        012 001 846
        001 001 1641*/
    static {
        scheMap = new HashMap<String, TradeSchedule>();
        TradeSchedule tradeSchedule1 = new TradeSchedule();
        tradeSchedule1.setWinNo(2);
        tradeSchedule1.setLoseNo(1);
        tradeSchedule1.setMultiple(1);
        scheMap.put("1", tradeSchedule1);

        TradeSchedule tradeSchedule2 = new TradeSchedule();
        tradeSchedule2.setWinNo(3);
        tradeSchedule2.setLoseNo(1);
        tradeSchedule2.setMultiple(2);
        scheMap.put("2", tradeSchedule2);

        TradeSchedule tradeSchedule3 = new TradeSchedule();
        tradeSchedule3.setWinNo(4);
        tradeSchedule3.setLoseNo(1);
        tradeSchedule3.setMultiple(4);
        scheMap.put("3", tradeSchedule3);

        TradeSchedule tradeSchedule4 = new TradeSchedule();
        tradeSchedule4.setWinNo(5);
        tradeSchedule4.setLoseNo(1);
        tradeSchedule4.setMultiple(8);
        scheMap.put("4", tradeSchedule4);

        TradeSchedule tradeSchedule5 = new TradeSchedule();
        tradeSchedule5.setWinNo(6);
        tradeSchedule5.setLoseNo(1);
        tradeSchedule5.setMultiple(16);
        scheMap.put("5", tradeSchedule5);

        TradeSchedule tradeSchedule6 = new TradeSchedule();
        tradeSchedule6.setWinNo(7);
        tradeSchedule6.setLoseNo(1);
        tradeSchedule6.setMultiple(31);
        scheMap.put("6", tradeSchedule6);

        TradeSchedule tradeSchedule7 = new TradeSchedule();
        tradeSchedule7.setWinNo(8);
        tradeSchedule7.setLoseNo(1);
        tradeSchedule7.setMultiple(60);
        scheMap.put("7", tradeSchedule7);

        TradeSchedule tradeSchedule8 = new TradeSchedule();
        tradeSchedule8.setWinNo(9);
        tradeSchedule8.setLoseNo(1);
        tradeSchedule8.setMultiple(116);
        scheMap.put("8", tradeSchedule8);

        TradeSchedule tradeSchedule9 = new TradeSchedule();
        tradeSchedule9.setWinNo(10);
        tradeSchedule9.setLoseNo(1);
        tradeSchedule9.setMultiple(225);
        scheMap.put("9", tradeSchedule9);

    TradeSchedule tradeSchedule10 = new TradeSchedule();
    tradeSchedule10.setWinNo(1);
    tradeSchedule10.setLoseNo(1);
    tradeSchedule10.setMultiple(450);
    scheMap.put("10", tradeSchedule10);

    TradeSchedule tradeSchedule11 = new TradeSchedule();
    tradeSchedule11.setWinNo(12);
    tradeSchedule11.setLoseNo(1);
    tradeSchedule11.setMultiple(900);
    scheMap.put("11", tradeSchedule11);

    TradeSchedule tradeSchedule12 = new TradeSchedule();
    tradeSchedule12.setWinNo(1);
    tradeSchedule12.setLoseNo(1);
    tradeSchedule12.setMultiple(1800);
    scheMap.put("12", tradeSchedule12);
    }

    //个位匹配
    //String[] nums = {"1", "4", "3", "8", "9"};
    String[] threeAll = {"000","001","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016","017","018","019","020","021","022","023","024","025","026","027","028","029","030","031","032","033","034","035","036","037","038","039","040","041","042","043","044","045","046","047","048","049","050","051","052","053","054","055","056","057","058","059","060","061","062","063","064","065","066","067","068","069","070","071","072","073","074","075","076","077","078","079","080","081","082","083","084","085","086","087","088","089","090","091","092","093","094","095","096","097","098","099","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200","201","202","203","204","205","206","207","208","209","210","211","212","213","214","215","216","217","218","219","220","221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238","239","240","241","242","243","244","245","246","247","248","249","250","251","252","253","254","255","256","257","258","259","260","261","262","263","264","265","266","267","268","269","270","271","272","273","274","275","276","277","278","279","280","281","282","283","284","285","286","287","288","289","290","291","292","293","294","295","296","297","298","299","300","301","302","303","304","305","306","307","308","309","310","311","312","313","314","315","316","317","318","319","320","321","322","323","324","325","326","327","328","329","330","331","332","333","334","335","336","337","338","339","340","341","342","343","344","345","346","347","348","349","350","351","352","353","354","355","356","357","358","359","360","361","362","363","364","365","366","367","368","369","370","371","372","373","374","375","376","377","378","379","380","381","382","383","384","385","386","387","388","389","390","391","392","393","394","395","396","397","398","399","400","401","402","403","404","405","406","407","408","409","410","411","412","413","414","415","416","417","418","419","420","421","422","423","424","425","426","427","428","429","430","431","432","433","434","435","436","437","438","439","440","441","442","443","444","445","446","447","448","449","450","451","452","453","454","455","456","457","458","459","460","461","462","463","464","465","466","467","468","469","470","471","472","473","474","475","476","477","478","479","480","481","482","483","484","485","486","487","488","489","490","491","492","493","494","495","496","497","498","499","500","501","502","503","504","505","506","507","508","509","510","511","512","513","514","515","516","517","518","519","520","521","522","523","524","525","526","527","528","529","530","531","532","533","534","535","536","537","538","539","540","541","542","543","544","545","546","547","548","549","550","551","552","553","554","555","556","557","558","559","560","561","562","563","564","565","566","567","568","569","570","571","572","573","574","575","576","577","578","579","580","581","582","583","584","585","586","587","588","589","590","591","592","593","594","595","596","597","598","599","600","601","602","603","604","605","606","607","608","609","610","611","612","613","614","615","616","617","618","619","620","621","622","623","624","625","626","627","628","629","630","631","632","633","634","635","636","637","638","639","640","641","642","643","644","645","646","647","648","649","650","651","652","653","654","655","656","657","658","659","660","661","662","663","664","665","666","667","668","669","670","671","672","673","674","675","676","677","678","679","680","681","682","683","684","685","686","687","688","689","690","691","692","693","694","695","696","697","698","699","700","701","702","703","704","705","706","707","708","709","710","711","712","713","714","715","716","717","718","719","720","721","722","723","724","725","726","727","728","729","730","731","732","733","734","735","736","737","738","739","740","741","742","743","744","745","746","747","748","749","750","751","752","753","754","755","756","757","758","759","760","761","762","763","764","765","766","767","768","769","770","771","772","773","774","775","776","777","778","779","780","781","782","783","784","785","786","787","788","789","790","791","792","793","794","795","796","797","798","799","800","801","802","803","804","805","806","807","808","809","810","811","812","813","814","815","816","817","818","819","820","821","822","823","824","825","826","827","828","829","830","831","832","833","834","835","836","837","838","839","840","841","842","843","844","845","846","847","848","849","850","851","852","853","854","855","856","857","858","859","860","861","862","863","864","865","866","867","868","869","870","871","872","873","874","875","876","877","878","879","880","881","882","883","884","885","886","887","888","889","890","891","892","893","894","895","896","897","898","899","900","901","902","903","904","905","906","907","908","909","910","911","912","913","914","915","916","917","918","919","920","921","922","923","924","925","926","927","928","929","930","931","932","933","934","935","936","937","938","939","940","941","942","943","944","945","946","947","948","949","950","951","952","953","954","955","956","957","958","959","960","961","962","963","964","965","966","967","968","969","970","971","972","973","974","975","976","977","978","979","980","981","982","983","984","985","986","987","988","989","990","991","992","993","994","995","996","997","998","999"};
    //String[] nums = {"005","006","007","010","011","012","013","014","015","016","017","020","021","022","023","024","025","026","027","030","031","032","033","034","035","036","037","040","041","042","043","044","045","046","047","050","051","052","053","054","055","056","057","060","061","062","063","064","065","066","067","070","071","072","073","074","075","076","077","100","101","102","103","104","105","106","107","110","112","113","114","115","116","117","120","121","122","123","124","125","126","127","130","131","132","133","134","135","136","137","140","141","142","143","144","145","146","147","150","151","152","153","154","155","156","157","160","161","162","163","164","165","166","167","170","171","172","173","174","175","176","177","200","201","202","203","204","205","206","207","210","211","212","213","214","215","216","217","220","221","223","224","225","226","227","230","231","232","233","234","235","236","237","240","241","242","243","244","245","246","247","250","251","252","253","254","255","256","257","260","261","262","263","264","265","266","267","270","271","272","273","274","275","276","277","300","301","302","303","304","305","306","307","310","311","312","313","314","315","316","317","320","321","322","323","324","325","326","327","330","331","332","334","335","336","337","340","341","342","343","344","345","346","347","350","351","352","353","354","355","356","357","360","361","362","363","364","365","366","367","370","371","372","373","374","375","376","377","400","401","402","403","404","405","406","407","410","411","412","413","414","415","416","417","420","421","422","423","424","425","426","427","430","431","432","433","434","435","436","437","440","441","442","443","445","446","447","450","451","452","453","454","455","456","457","460","461","462","463","464","465","466","467","470","471","472","473","474","475","476","477","500","501","502","503","504","505","506","507","510","511","512","513","514","515","516","517","520","521","522","523","524","525","526","527","530","531","532","533","534","535","536","537","540","541","542","543","544","545","546","547","550","551","552","553","554","556","557","560","561","562","563","564","565","566","567","570","571","572","573","574","575","576","577","600","601","602","603","604","605","606","607","610","611","612","613","614","615","616","617","620","621","622","623","624","625","626","627","630","631","632","633","634","635","636","637","640","641","642","643","644","645","646","647","650","651","652","653","654","655","656","657","660","661","662","663","664","665","667","670","671","672","673","674","675","676","677","700","701","702","703","704","705","706","707","710","711","712","713","714","715","716","717","720","721","722","723","724","725","726","727","730","731","732","733","734","735","736","737","740","741","742","743","744","745","746","747","750","751","752","753","754","755","756","757","760","761","762","763","764","765","766","767","770","771","772","773","774","775","776"};

    //统计连中次数
    int lastWincnt = 12;
    int redDistance = 250;
    String toTz ="红白";
    public String runStrategy(String start, String end, String toTz, Integer lastCnt, Integer btqs) {

        List<String> toCheckList = new ArrayList<>();
        int[] randomGet = ArraysUtil.randomCommon(0,999,500);
        for(int i : randomGet) {
            String num = threeAll[i];
            toCheckList.add(num);
            System.out.print(num + ",");
        }
        System.out.println("<-- 投注号码");

        //随机获取500注
        if (!StringUtils.isEmpty(toTz)) {
            this.toTz = toTz;
        }
        if (null != lastCnt) {
            this.lastWincnt = lastCnt;
        }
        StrategyDO result = new StrategyDO();
        result.setSeqNo(UUID.randomUUID().toString().replace("-", ""));
        result.setStartNo(start);
        result.setEndNo(end);
        result.setLotteryCode("QQFFC");

        //批量验证数据
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andAliasNoBetween(start, end).andLotteryDateIsNotNull();
        List<LotteryDetail> list = lotteryDetailMapper.selectByCondition(lotteryDetailCondition);

        BigDecimal curProfit = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;

        BigDecimal totalThrow = BigDecimal.ZERO;
        //最大
        BigDecimal maxProfit = BigDecimal.ZERO;
        //最大
        BigDecimal minProfit = BigDecimal.ZERO;

        BigDecimal baseAmt = new BigDecimal("1");
        BigDecimal baseProfit = new BigDecimal("0.93");

        TradeSchedule curSchedule = null;
        curSchedule = scheMap.get("1");
        List<StrategyDetailDO> strategyDetailDOList = new ArrayList<>();

        int curWin = 0;
        List<LotteryDetail> p9List = new ArrayList<>();
        String redWhiteStatus = "";
        Map<String, Integer> winCnt = new HashMap<>();
        Map<String, Integer> looseCnt = new HashMap<>();

        for (LotteryDetail detail : list) {

            String num1 = detail.getNum1();
            String num2 = detail.getNum2();
            String num3 = detail.getNum3();
            String num4 = detail.getNum4();
            int nextNo = 1;
            boolean is2Tz = redWhiteStatus.endsWith(toTz);
            boolean isPrized = false;
            if (toCheckList.contains(num4+num3+num2)) {
                isPrized = true;
                curWin++;
                //连中
                if (curWin == lastWincnt) {
                    if(p9List.size() > 0) {
                        LotteryDetail pre = p9List.get(p9List.size() - 1);
                        try {
                            int distance = this.calQQffcDistance(detail.getAliasNo(), pre.getAliasNo());
                            if(distance >= redDistance) {
                                redWhiteStatus = redWhiteStatus + "红";
                            } else {
                                redWhiteStatus = redWhiteStatus + "白";
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(detail.getAliasNo() + "," + redWhiteStatus);
                    p9List.add(detail);
                }
            } else {
                if (null == winCnt.get("" + curWin)) {
                    winCnt.put("" + curWin, 1);
                } else {
                    winCnt.put("" + curWin, winCnt.get("" + curWin) + 1);
                }
                isPrized = false;
                curWin=0;
            }

            is2Tz = true;
            //基于投注的前提下统计的数据
            if(is2Tz) {
                //tz
                StrategyDetailDO strategyDetailDO = new StrategyDetailDO();
                strategyDetailDO.setSid(result.getSeqNo());
                strategyDetailDO.setLotteryCode("QQFFC");
                BigDecimal tzAmt = baseAmt.multiply(new BigDecimal(curSchedule.getMultiple()));
                totalThrow = totalThrow.add(tzAmt);
                if(isPrized) {
                    curProfit = curProfit.add(tzAmt.multiply(baseProfit));
                    totalProfit = totalProfit.add(tzAmt.multiply(baseProfit));
                    nextNo = curSchedule.getWinNo();
                    strategyDetailDO.setStatus("中");
                } else {
                    curProfit = curProfit.subtract(tzAmt);
                    totalProfit = totalProfit.subtract(tzAmt);
                    nextNo = curSchedule.getLoseNo();
                    strategyDetailDO.setStatus("挂");
                }
                maxProfit = maxProfit.compareTo(totalProfit) > 0 ? maxProfit : totalProfit;
                minProfit = minProfit.compareTo(totalProfit) < 0 ? minProfit : totalProfit;
                //达到最大倍投时，从第一期开始打；盈利回头
                if (nextNo == btqs + 1 ) {
                    nextNo = 1;
                }
                if (curProfit.compareTo(BigDecimal.ZERO) > 0) {
                    curProfit = BigDecimal.ZERO;
                    nextNo = 1;
                }
                curSchedule = scheMap.get("" + nextNo);
                strategyDetailDO.setNo(detail.getAliasNo());
                strategyDetailDO.setAmt(tzAmt);
                strategyDetailDO.setCurProfit(curProfit);
                strategyDetailDO.setTotalProfit(totalProfit);
                strategyDetailDOList.add(strategyDetailDO);
            }
        }
        result.setCurProfit(curProfit);
        result.setMaxProfit(maxProfit);
        result.setMinProfit(minProfit);
        result.setTotalAmt(totalThrow);

        for (int i = 1; i < p9List.size(); i++) {
            LotteryDetail p9current = p9List.get(i);
            LotteryDetail p9pre = p9List.get(i - 1);

            int p9distance = 0;
            try {
                p9distance = this.calQQffcDistance(p9current.getAliasNo(), p9pre.getAliasNo());
                System.out.println(p9current.getAliasNo() + "/" + p9distance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        strategyDAO.insert(result);
        batchInsertStartegyDetail(strategyDetailDOList);
        System.out.println("totalThrow:" + totalThrow + ",totalProfit:" + totalProfit + ", maxProfit:" + maxProfit + ",minProfit:" + minProfit);
        return null;
    }

    private void batchInsertStartegyDetail(List<StrategyDetailDO> list) {
        List<List<StrategyDetailDO>> groupedList = ArraysUtil.groupListByQuantity(list, 1000);
        groupedList.forEach(
                item -> {
                    strategyDetailDAO.insertBatch(item);
                }
        );
    }

    //极限推波验证
    //新增红白策略（连中9大于800为红）
    public void batchCheckData(String start, String end) throws ParseException {
        //个位匹配
        //String[] nums = {"1", "4", "3", "8", "9"};
        String[] nums = {"005','006','007','010','011','012','013','014','015','016','017','020','021','022','023','024','025','026','027','030','031','032','033','034','035','036','037','040','041','042','043','044','045','046','047','050','051','052','053','054','055','056','057','060','061','062','063','064','065','066','067','070','071','072','073','074','075','076','077','100','101','102','103','104','105','106','107','110','112','113','114','115','116','117','120','121','122','123','124','125','126','127','130','131','132','133','134','135','136','137','140','141','142','143','144','145','146','147','150','151','152','153','154','155','156','157','160','161','162','163','164','165','166','167','170','171','172','173','174','175','176','177','200','201','202','203','204','205','206','207','210','211','212','213','214','215','216','217','220','221','223','224','225','226','227','230','231','232','233','234','235','236','237','240','241','242','243','244','245','246','247','250','251','252','253','254','255','256','257','260','261','262','263','264','265','266','267','270','271','272','273','274','275','276','277','300','301','302','303','304','305','306','307','310','311','312','313','314','315','316','317','320','321','322','323','324','325','326','327','330','331','332','334','335','336','337','340','341','342','343','344','345','346','347','350','351','352','353','354','355','356','357','360','361','362','363','364','365','366','367','370','371','372','373','374','375','376','377','400','401','402','403','404','405','406','407','410','411','412','413','414','415','416','417','420','421','422','423','424','425','426','427','430','431','432','433','434','435','436','437','440','441','442','443','445','446','447','450','451','452','453','454','455','456','457','460','461','462','463','464','465','466','467','470','471','472','473','474','475','476','477','500','501','502','503','504','505','506','507','510','511','512','513','514','515','516','517','520','521','522','523','524','525','526','527','530','531','532','533','534','535','536','537','540','541','542','543','544','545','546','547','550','551','552','553','554','556','557','560','561','562','563','564','565','566','567','570','571','572','573','574','575','576','577','600','601','602','603','604','605','606','607','610','611','612','613','614','615','616','617','620','621','622','623','624','625','626','627','630','631','632','633','634','635','636','637','640','641','642','643','644','645','646','647','650','651','652','653','654','655','656','657','660','661','662','663','664','665','667','670','671','672','673','674','675','676','677','700','701','702','703','704','705','706','707','710','711','712','713','714','715','716','717','720','721','722','723','724','725','726','727','730','731','732','733','734','735','736','737','740','741','742','743','744','745','746','747','750','751','752','753','754','755','756','757','760','761','762','763','764','765','766','767','770','771','772','773','774','775','776"};
        String[] num4 = {"0","1", "2", "3", "4", "5" , "6", "7"};
        String[] num3 = {"0","1", "2", "3", "4", "5" , "6", "7"};
        String[] num2 = {"0","1", "2", "3", "4", "5" , "6", "7"};
        //批量验证数据
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andAliasNoBetween(start, end).andLotteryDateIsNotNull().andNum4In(Arrays.asList(num4)).andNum3In(Arrays.asList(num3)).andNum2In(Arrays.asList(num2));
        List<LotteryDetail> list = lotteryDetailMapper.selectByCondition(lotteryDetailCondition);

        //验证连中期数
        int pDistance = 9;
        int maxLooseDistance = 0;
        int maxWinDistance = 0;
        int curWin = 1;
        String redWhiteStatus = "";
        Map<String, Integer> winDistance = new HashMap<>();

        Map<String, Integer> winCnt = new HashMap<>();
        Map<String, Integer> looseCnt = new HashMap<>();

        List<LotteryDetail> p9List = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            LotteryDetail current = list.get(i);
            LotteryDetail pre = list.get(i - 1);

            int distance = this.calQQffcDistance(current.getAliasNo(), pre.getAliasNo());
            winDistance.put(current.getAliasNo(), distance);
            if (null == looseCnt.get("" + distance)) {
                looseCnt.put("" + distance, 1);
            } else {
                looseCnt.put("" + distance, looseCnt.get("" + distance) + 1);
            }
            maxLooseDistance = distance > maxLooseDistance ? distance : maxLooseDistance;
            if (distance == 0) {
                curWin++;
                if (curWin == pDistance) {
                    p9List.add(current);
                }
            } else {
                if (null == winCnt.get("" + curWin)) {
                    winCnt.put("" + curWin, 1);
                } else {
                    winCnt.put("" + curWin, winCnt.get("" + curWin) + 1);
                }
                maxWinDistance = curWin > maxWinDistance ? curWin : maxWinDistance;

                curWin = 1;
            }
        }

        for (int i = 1; i < p9List.size(); i++) {
            LotteryDetail p9current = p9List.get(i);
            LotteryDetail p9pre = p9List.get(i - 1);

            int p9distance = this.calQQffcDistance(p9current.getAliasNo(), p9pre.getAliasNo());
            System.out.println(p9current.getAliasNo() + "/" + p9distance);
        }

        System.out.println(String.format("(%s--%s)总共中：%s,最大连挂:%s,最大连中:%s", start, end, list.size(), maxLooseDistance, maxWinDistance));

        /*for (Map.Entry<String, Integer> entry : winDistance.entrySet()) {
            System.out.print( entry.getValue() + ",");
        }
        System.out.println("------");*/
    }

    private int calQQffcDistance(String curAliasNo, String preAliasNo) throws ParseException {
        int dayDistance = DateUtils.calculateDaysBetween(curAliasNo.substring(0, 8), preAliasNo.substring(0, 8));
        int noDistance = Integer.valueOf(curAliasNo.substring(9, 13)) - Integer.valueOf(preAliasNo.substring(9, 13));

        return dayDistance * 1440 + noDistance - 1;
    }

    public void batchLotteryDetails(String time) {

        TecentOnlineDOCondition tecentOnlineDOCondition = new TecentOnlineDOCondition();
        tecentOnlineDOCondition.setOrderByClause(" time asc");
        tecentOnlineDOCondition.createCriteria().andTimeLike(time + "%");
        List<TecentOnlineDO> tecentOnlineDOList = tecentOnlineDAO.selectByCondition(tecentOnlineDOCondition);
        for (int i = 2; i < tecentOnlineDOList.size(); i++) {
            Long pre2Num = tecentOnlineDOList.get(i - 2).getOnlineNum();
            Long preNum = tecentOnlineDOList.get(i - 1).getOnlineNum();
            //
            String num = "";
            Long preNum43 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            Long pre2Num43 = Long.valueOf((pre2Num + "").substring((pre2Num + "").length() - 4, (pre2Num + "").length() - 3));

            Long num4 = Long.valueOf((preNum + "").substring((preNum + "").length() - 4, (preNum + "").length() - 3));
            if (preNum43 - pre2Num43 >= -2 && preNum43 - pre2Num43 <= 2) {
                //adjust in 5
                if (2 <= num4 && num4 <= 7) {
                    num = ((num4 - 2) + " ")
                            + ((num4 - 1) + " ")
                            + ((num4 - 0) + " ")
                            + ((num4 + 1) + " ")
                            + ((num4 + 2) + " ");
                } else if (num4 == 1) {
                    num = "9 0 1 2 3";
                } else if (num4 == 0) {
                    num = "8 9 0 1 2";
                } else if (num4 == 8) {
                    num = "6 7 8 9 0";
                } else if (num4 == 9) {
                    num = "7 8 9 0 1";
                }
            } else {
                if (num4 == 0) {
                    num = "3 4 5 6 7";
                } else if (num4 == 1) {
                    num = "4 5 6 7 8";
                } else if (num4 == 2) {
                    num = "5 6 7 8 9";
                } else if (num4 == 3) {
                    num = "6 7 8 9 0";
                } else if (num4 == 4) {
                    num = "7 8 9 0 1";
                } else if (num4 == 5) {
                    num = "0 1 2 8 9";
                } else if (num4 == 6) {
                    num = "9 0 1 2 3";
                } else if (num4 == 7) {
                    num = "0 1 2 3 4";
                } else if (num4 == 8) {
                    num = "1 2 3 4 5";
                } else if (num4 == 9) {
                    num = "2 3 4 5 6";
                }
            }

            Long curNum = tecentOnlineDOList.get(i).getOnlineNum();
            LotteryDetail lotteryDetail = new LotteryDetail();
            lotteryDetail.setLotteryCode("QQFFC");
            lotteryDetail.setAliasNo(tecentOnlineDOList.get(i).getTime());
            lotteryDetail.setNo(num);
            String prizeNum = (curNum + "").substring((curNum + "").length() - 4, (curNum + "").length() - 3);
            lotteryDetail.setNum4(prizeNum);

            if (num.indexOf(prizeNum) >= 0) {
                lotteryDetail.setNum8("3");
            } else {
                lotteryDetail.setNum8("2");
            }
            log.info(tecentOnlineDOList.get(i).getTime() + "finished");
            lotteryDetailMapper.insert(lotteryDetail);
        }
    }

    public static void main(String[] args) {
        System.out.println("1 2 3".indexOf("2"));
    }

    public Map<String, Object> analysisQQffcData(String type, String start, String end) {
        Map<String, Object> map = new HashMap<>();
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateBetween(start, end);
        List<LotteryDetail> list = lotteryDetailMapper.selectByConditionWithH3(lotteryDetailCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            LotteryDetail next = list.get(i);
            LotteryDetail pre = list.get(i - 1);
            int nextNum = Integer.valueOf(next.getAliasNo().substring(next.getAliasNo().length() - 4));
            int preNum = Integer.valueOf(pre.getAliasNo().substring(pre.getAliasNo().length() - 4));
            timeList.add(next.getAliasNo());
            if (next.getAliasNo().substring(0, 8).equals(pre.getAliasNo().substring(0, 8))) {
                numList.add(nextNum - preNum);
            } else {
                numList.add(0);
            }
        }

        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }

    public Map<String, Object> analysis2Start2qzh3Data(String type, String start, String end) {
        Map<String, Object> map = new HashMap<>();
        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.setOrderByClause("alias_no asc ");
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateBetween(start, end);
        List<LotteryDetail> list = lotteryDetailMapper.selectByConditionWithH3(lotteryDetailCondition);
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            LotteryDetail next = list.get(i);
            LotteryDetail pre = list.get(i - 1);
            int nextNum = Integer.valueOf(next.getAliasNo().substring(next.getAliasNo().length() - 4));
            int preNum = Integer.valueOf(pre.getAliasNo().substring(pre.getAliasNo().length() - 4));
            timeList.add(next.getAliasNo());
            if (next.getAliasNo().substring(0, 8).equals(pre.getAliasNo().substring(0, 8))) {
                numList.add(nextNum - preNum);
            } else {
                numList.add(0);
            }
        }

        map.put("xAxis", timeList);
        map.put("series", numList);
        return map;
    }

    public void fetchQQffcData(Date startDate, Date endDate) throws Exception {
        while (startDate.compareTo(endDate) <= 0) {
            String date = DateUtils.date2String(startDate, "yyyyMMdd");
            this.fetchDateData(date);
            log.info("完成处理:" + date);
            startDate = DateUtils.addDate(1, startDate);
        }
    }

    public void fetchDateData(String date) throws Exception {
        try {
            Connection conn = Jsoup.connect("http://www.tx-ffc.com/txffc/kj-" + date + ".html");
            conn.timeout(3000);
            Document doc = conn.get();

            Elements els = null;
            els = doc.select(".klist .kj_list").select(".kj_code");
            log.info(date + " kj_code size:" + els.size());
            DigLotteryProc(els, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DigLotteryProc(Elements els, String date) {
        // 处理数字彩票,从中间抓取取双色球、大乐透和七乐彩的开奖结果，每个彩种在table中都是<tbody>的一行<tr>
        LotteryDetail lotteryDetail = null;
        List<LotteryDetail> toInsert = new ArrayList<>();

        LotteryDetailCondition lotteryDetailCondition = new LotteryDetailCondition();
        lotteryDetailCondition.createCriteria().andLotteryCodeEqualTo("QQFFC").andLotteryDateEqualTo(date);
        lotteryDetailMapper.deleteByCondition(lotteryDetailCondition);
        for (Element kjTitleElement : els) {
            Elements kjElements = kjTitleElement.select(".kj_title");
            for (Element row : kjElements) {

                String kjTime = row.getElementsByClass("a1").text();
                String curNo = row.getElementsByClass("a2").text();
                String kjNo = row.getElementsByClass("a3").text();
                try {
                    lotteryDetail = new LotteryDetail();
                    lotteryDetail.setLotteryCode("QQFFC");
                    lotteryDetail.setLotteryDate(date);
                    lotteryDetail.setTime(DateUtils.String2Date(date + kjTime, "yyyyMMddhh:mm"));
                    lotteryDetail.setNo(date + "-" + curNo.substring(0, curNo.length() - 1));
                    lotteryDetail.setAliasNo(date + "-" + curNo.substring(0, curNo.length() - 1));

                    lotteryDetail.setNum1(kjNo.substring(4));
                    lotteryDetail.setNum2(kjNo.substring(3, 4));
                    lotteryDetail.setNum3(kjNo.substring(2, 3));
                    lotteryDetail.setNum4(kjNo.substring(1, 2));
                    lotteryDetail.setNum5(kjNo.substring(0, 1));
                    toInsert.add(lotteryDetail);
                } catch (Exception e) {
                    log.error(date + curNo + " lotteryDetail  error, continue nextday", e);
                }
            }
        }
        this.batchCommit(toInsert);

    }

    private void batchCommit(List<LotteryDetail> list) {
        lotteryDetailMapper.insertBatch(list);
    }

}
