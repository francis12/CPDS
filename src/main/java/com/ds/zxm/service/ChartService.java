package com.ds.zxm.service;

import com.alibaba.fastjson.JSONObject;
import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TCFFCPRIZECondition;
import com.ds.zxm.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Service
public class ChartService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    Logger log = Logger.getLogger(ChartService.class);


    //                "2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30","2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30","2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30"
//[1,2,1,2],
//                [2,3,2,3],
//                        [3,4,3,4],
//                        [4,3,3,4],
//                        [3,4,3,4],
//                        [1,2,1,2],
//                        [2,3,2,3],
//                        [5,6,5,6],
//                        [4,3,3,4],
//                        [3,4,3,4],
//                        [1,2,1,2],
//                        [2,3,2,3],
//                        [3,4,3,4],
//                        [4,3,3,4],
//                        [3,4,3,4]

    /**
     *
     * @param limit
     * @param type:0:波动单双，1：个位单双
     * @return
     * @throws ParseException
     */
    public Map<String, Object> queryTecentOnlineData2Option(Integer limit, String queryDateStr, int type) throws ParseException {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

        if (limit != 0 && limit != null) {
            Date date = DateUtils.getBaiduCurTime();
            Date minTime = DateUtils.addMinutes(-limit, date);
            criteria.andTimeGreaterThanOrEqualTo(minTime);
        }
        if(StringUtils.isNotEmpty(queryDateStr)) {
            Date queryDate = DateUtils.String2Date(queryDateStr, "yyyyMMdd");
            criteria.andLotteryDateEqualTo(queryDate);
        }

        tcffcprizeCondition.setOrderByClause("time asc");

        List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        List<String> noList = new ArrayList<>();
        List<Integer> adjustList = new ArrayList<>();
        List<Integer[]> adjustDsList = new ArrayList();

        if(null != list && list.size() > 0) {
            for(TCFFCPRIZE item : list) {
                noList.add(item.getNo());
                int last1Adjust = 0;
                if(type == 0) {
                    last1Adjust = item.getAdjustNum()%10;
                } else if (type == 1) {
                    last1Adjust = item.getGe();
                }
                //0：双，1：单
                adjustList.add(last1Adjust%2==0?0:1);
            }
            //log.info("dstype:" + dsType + ", array:" + adjustList.toString());
            //转成K线数组
            adjustDsList = this.convert2KArray(adjustList);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", noList);
        map.put("series", adjustDsList);
        return map;
    }

    /**
     *
     *  通过区间查询
     * @param :0:波动单双，1：个位单双
     * @return
     * @throws ParseException
     */
    public Map<String, Object> queryTecentOnlineDataByInterval2Option(String startNo, String endNo) throws ParseException {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();
        criteria.andNoBetween(startNo, endNo);
        tcffcprizeCondition.setOrderByClause("time asc");

        List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        List<String> noList = new ArrayList<>();
        List<Integer> adjustList = new ArrayList<>();
        List<Integer[]> adjustDsList = new ArrayList();

        if(null != list && list.size() > 0) {
            for(TCFFCPRIZE item : list) {
                noList.add(item.getNo());
                int last1Adjust = 0;
                    last1Adjust = item.getAdjustNum()%10;
                //0：双，1：单
                adjustList.add(last1Adjust%2==0?0:1);
            }
            //log.info("dstype:" + dsType + ", array:" + adjustList.toString());
            //转成K线数组
            adjustDsList = this.convert2KArray(adjustList);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", noList);
        map.put("series", adjustDsList);
        return map;
    }

    private List<Integer[]> adjustList(List<Integer[]> list, int adjsut) {

        return null;
    }
    public Map<String, Object> queryBdDxData(Integer limit, String queryDateStr) throws ParseException {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Map<String, Object> map = null;
        List<String> noList = new ArrayList<>();
        List<Integer[]> adjustDsList = new ArrayList();

        try {
            TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

            if (limit != 0 && limit != null) {
                Date date = DateUtils.getBaiduCurTime();
                Date minTime = DateUtils.addMinutes(-limit, date);
                criteria.andTimeGreaterThanOrEqualTo(minTime);
            }
            if(StringUtils.isNotEmpty(queryDateStr)) {
                Date queryDate = DateUtils.String2Date(queryDateStr, "yyyyMMdd");
                criteria.andLotteryDateEqualTo(queryDate);
            }

            tcffcprizeCondition.setOrderByClause("time asc");

            List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

            List<Integer> adjustList = new ArrayList<>();

            StringBuffer allBdSb = new StringBuffer();
            if(null != list && list.size() > 0) {
                for(TCFFCPRIZE item : list) {
                    noList.add(item.getNo());
                    int last1Adjust = 0;
                    int last1=last1Adjust%10;
                    if(last1>=-9&&last1<=-5){

                    }else if(last1>=-4&&last1<=0){

                    }else if(last1>=0&&last1<=4){

                    }else if(last1>=5&&last1<=9){

                    }
                }
                //转成K线数组
                adjustDsList = this.convert2KArrayWithZf(adjustList);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        map = new HashMap<>();
        map.put("xAxis", noList);
        map.put("series", adjustDsList);
        return map;
    }
        //type:0 - 波动个位双， 1 - 开奖个位双 ， 2 - 波动十位双， 3 - 开奖百位双 ， 4 - 波动千位双， 5 - 波动千位双 -6 波动个位小(0,1,2,3,4,-6,-7,-8,-9,-0) -7 波动大小-正负分开-8wan
    public Map<String, Object> queryTecentOnlineDanData2Option(Integer limit, String queryDateStr, int type) throws ParseException {
        if(7==type) {
            return this.queryBdDxData(limit, queryDateStr);
        }
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        Map<String, Object> map = null;
        List<String> noList = new ArrayList<>();
        List<Integer[]> adjustDsList = new ArrayList();

        try {
            TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

            if (limit != 0 && limit != null) {
                Date date = DateUtils.getBaiduCurTime();
                Date minTime = DateUtils.addMinutes(-limit, date);
                criteria.andTimeGreaterThanOrEqualTo(minTime);
            }
            if(StringUtils.isNotEmpty(queryDateStr)) {
                Date queryDate = DateUtils.String2Date(queryDateStr, "yyyyMMdd");
                criteria.andLotteryDateEqualTo(queryDate);
            }

            tcffcprizeCondition.setOrderByClause("time asc");

            List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

            List<Integer> adjustList = new ArrayList<>();

            StringBuffer allBdSb = new StringBuffer();
            if(null != list && list.size() > 0) {
                for(TCFFCPRIZE item : list) {
                    noList.add(item.getNo());
                    int last1Adjust = 0;
                    if(type == 0) {
                        last1Adjust = item.getAdjustNum()%10;
                    }else if (type == 1) {
                        last1Adjust = item.getGe();
                    }else if (type == 2) {
                        last1Adjust = (item.getAdjustNum()%100)/10;
                    }else if (type == 3) {
                        last1Adjust = (item.getAdjustNum()%1000)/100;
                    }else if (type == 4) {
                        last1Adjust = (item.getAdjustNum()%10000)/1000;
                    }else if (type == 5) {
                        last1Adjust = (item.getAdjustNum()%100000)/10000;
                    }else if (type == 6) {
                        int geAdjust = item.getAdjustNum()%10;
                        if((0<=geAdjust&&geAdjust<=4)||(-9<=geAdjust&&geAdjust<=-6)) {
                            last1Adjust=0;
                        } else {
                            last1Adjust=1;
                        }
                    }else if (type == 8) {
                        last1Adjust = item.getWan();
                    }
                    //0：双，1：单
                    adjustList.add(last1Adjust%2==0?0:1);
                }
                //转成K线数组
                adjustDsList = this.convert2KArray(adjustList);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        map = new HashMap<>();
        map.put("xAxis", noList);
        map.put("series", adjustDsList);
        return map;
    }
    public static void main(String[] args) {
        System.out.println((307996740%100000)/10000);
    }
    //正常数据转成K线图数据
    // 开盘，收盘，最低，最高
    //0为正，1位单。开双为正
    private List<Integer[]> convert2KArray(List<Integer> list) {
        if(null == list || list.size() == 0){
            return null;
        }
        List<Integer[]> result = new ArrayList<>();
        int preSp = 0;
        int size = list.size();
        for(int i=0;i < size; i++) {
            int curKp = preSp;
            int curSp = 0;
            int max,min;
            int adjustDs = list.get(i);
            if(adjustDs == 0) {
                curSp = curKp + 1;
                max = curSp;
                min = curKp;
            } else {
                curSp = curKp - 1;
                max = curKp;
                min = curSp;
            }
            Integer[] cur = {curKp, curSp, min, max};
            result.add(cur);
            //preKp = curKp;
            preSp = curSp;
        }
        return result;
    }
    //-2:-9->-5
    //-1:-4->-1
    //0:0
    //1->1->4
    //2->5->9
    private List<Integer[]> convert2KArrayWithZf(List<Integer> list) {
        if(null == list || list.size() == 0){
            return null;
        }
        List<Integer[]> result = new ArrayList<>();
        //正负分离
        Integer fDCnt = 0;
        int fxCnt = 0;
        int zDCnt = 0;
        int zXCnt = 0;

        for(Integer item : list) {
            if(item >=-9 && item <=-5) {
                fDCnt++;
            } else if (item >=-4 && item <=0) {
                fxCnt++;
            } else if (item >=0 && item <=4) {
                zXCnt++;
            } else if (item >=5 && item <=9) {
                zDCnt++;
            }
        }

        int preSp = 0;
        int size = list.size();
        for(int i=0;i < size; i++) {
            int curKp = preSp;
            int curSp = 0;
            int max,min;
            int adjustDs = list.get(i);
            if(adjustDs == 0) {
                curSp = curKp + 1;
                max = curSp;
                min = curKp;
            } else {
                curSp = curKp - 1;
                max = curKp;
                min = curSp;
            }
            Integer[] cur = {curKp, curSp, min, max};
            result.add(cur);
            //preKp = curKp;
            preSp = curSp;
        }
        return result;
    }
}
