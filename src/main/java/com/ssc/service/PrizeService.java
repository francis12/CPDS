package com.ssc.service;

import com.ssc.mapper.TCFFCPRIZEDAO;
import com.ssc.model.*;
import com.ssc.util.DateUtils;
import com.ssc.util.LotteryUtil;
import com.ssc.model.*;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Service
public class PrizeService {

    @Resource
    private TCFFCPRIZEDAO tcffcprizedao;
    @Resource
    private TcffcGenNumsService tcffcGenNumsService;
    Logger log = Logger.getLogger(PrizeService.class);


    public List<TCFFCPRIZE> queryLatestPrize(Integer limit) throws ParseException {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

        Date date = DateUtils.getBaiduCurTime();
        Date minTime = DateUtils.addMinutes(-limit, date);
        criteria.andTimeGreaterThanOrEqualTo(minTime);
        tcffcprizeCondition.setOrderByClause("time desc");
        List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);
        if(null !=list && list.size() > 1) {
            TCFFCPRIZE base1 = list.get(0);
            TCFFCPRIZE base2 = list.get(1);
            base1.setIsMatch("Y");
            base2.setIsMatch("Y");
            boolean isPreMatch = false;
            for(int i=2;i<list.size();i++) {
                TCFFCPRIZE cur = list.get(i);
                if(isPreMatch) {
                    if(cur.getGe() == base2.getGe()) {
                        list.get(i-1).setIsMatch("Y");
                        cur.setIsMatch("Y");
                        isPreMatch = false;
                    }else {
                        if(cur.getGe() == base1.getGe()) {
                            isPreMatch = true;
                        }else {
                            isPreMatch = false;
                        }
                    }
                } else {
                    if(cur.getGe() == base1.getGe()) {
                        isPreMatch = true;
                    }
                }
            }
        }
        return list;
    }

    public List<MissedPrizeList> getLatestPrizeMissCnt(Integer limit) throws ParseException {
        return this.getLatestPrizeMissCnt(limit, "hou4", WF_BDW);
    }

    static final String WF_BDW = "bdw";
    static final String WF_ZHU3 = "zhu3";
    static final String WF_ZHU6 = "zhu6";

    //后4连续二星

    static int[][] lxErXin = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9}, {0, 9}};
    static int[] lxXin = {0,1,2,3,4,5,6,7,8,9};

    static int[][] ErXinAll = {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9},
            {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9},
             {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8}, {2, 9},
             {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9},
             {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9},
            {5, 6}, {5, 7}, {5, 8}, {5, 9},
            {6, 7}, {6, 8}, {6, 9}, {7, 8}, {7, 9},  {8, 9}

    };

    int zhuLimit = 1000;

    public Map<String, List<TCFFCPRIZE>>  getLatestGeNextPrize(Integer limit, String type) throws ParseException {
        List<TCFFCPRIZE> list = queryLatestPrizeList(limit);

        Map<String, List<TCFFCPRIZE>> result = new HashMap<>();
        String preGeNum = null;
        for(TCFFCPRIZE item : list) {
            String curGeNum = null;
            if("ge".equals(type)) {
                curGeNum = item.getGe() + "";
            } else if("shi".equals(type)) {
                curGeNum = item.getShi() + "";
            } else if("bai".equals(type)) {
                curGeNum = item.getBai() + "";
            } else if("qian".equals(type)) {
                curGeNum = item.getQian() + "";
            } else if("wan".equals(type)) {
                curGeNum = item.getWan() + "";
            }
            if(StringUtils.isNotEmpty(preGeNum)) {
                List<TCFFCPRIZE> existList = result.get(preGeNum);
                if(existList == null || existList.size() == 0) {
                    existList = new ArrayList<>();
                }
                existList.add(item);
                result.put(preGeNum, existList);
            }
            preGeNum = curGeNum;
        }
        result.entrySet().stream().forEach( itemEntry -> {
                    List<TCFFCPRIZE> itemList = itemEntry.getValue();
                    Collections.sort(itemList, new Comparator<TCFFCPRIZE>() {
                        @Override
                        public int compare(TCFFCPRIZE o1, TCFFCPRIZE o2) {
                            return  o2.getNo().compareTo(o1.getNo());
                        }
                    });
                }
        );
        return result;
    }
    //codeLimit:计算遗漏排行
    public MissedPrizeResult getLatestPrizeMissCntByorder(Integer limit, String type, Integer latest1, Integer latest2, Integer coldLimit) throws ParseException {
        MissedPrizeResult missedPrizeResult = new MissedPrizeResult();
        List<MissedPrizeList> list = this.getLatestPrizeMissCnt(limit, type, WF_BDW);
        Collections.sort(list, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        missedPrizeResult.setList(list);
        //组6
        List<MissedPrizeList> zhuList = this.getLatestPrizeMissCnt(zhuLimit, "", WF_ZHU6);
        Collections.sort(zhuList, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        missedPrizeResult.setZhuList(zhuList);
        //组3
        List<MissedPrizeList> zhu3List = this.getLatestPrizeMissCnt(zhuLimit, "", WF_ZHU3);
        Collections.sort(zhu3List, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        missedPrizeResult.setZhu3List(zhu3List);
        //计算组3排行
        List<MissedPrizeList> zhu3ColdList = this.getLatestPrizeMissCnt(zhuLimit, "", WF_ZHU3);
        this.calColdOrderList(zhu3ColdList, 5);
        missedPrizeResult.setZhu3ColdList(zhu3ColdList);

        List<MissedPrizeList> lates30List = this.getLatestPrizeMissCnt(latest1, type, WF_BDW);
        List<MissedPrizeList> lates60List = this.getLatestPrizeMissCnt(latest2, type, WF_BDW);
        Collections.sort(lates30List, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        Collections.sort(lates60List, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });

        List<PrizeLrModelList> lrResultList = new ArrayList<>();
        List<PrizeLrModel> prizeLr30List = new ArrayList<>();
        for (int i = 0; i < lates30List.size(); i++) {
            PrizeLrModel item = new PrizeLrModel();
            item.setNum(lates30List.get(i).getNum());
            item.setOrder(i + 1);
            prizeLr30List.add(item);
        }
        PrizeLrModelList prizeLr30 = new PrizeLrModelList();
        prizeLr30.setLatestNum(latest1);
        prizeLr30.setList(prizeLr30List);
        lrResultList.add(prizeLr30);

        List<PrizeLrModel> prizeLr60List = new ArrayList<>();
        for (int i = 0; i < lates60List.size(); i++) {
            PrizeLrModel item = new PrizeLrModel();
            item.setNum(lates60List.get(i).getNum());
            item.setOrder(i + 1);
            prizeLr60List.add(item);
        }
        PrizeLrModelList prizeLr60 = new PrizeLrModelList();
        prizeLr60.setLatestNum(latest2);
        prizeLr60.setList(prizeLr60List);
        lrResultList.add(prizeLr60);

        missedPrizeResult.setLrList(lrResultList);

        //计算遗漏排行
        List<MissedPrizeList> coldLimitList = this.getLatestPrizeMissCnt(coldLimit, type, WF_BDW);
        this.calColdOrderList(coldLimitList, 1);
        missedPrizeResult.setColdList(coldLimitList);
        return missedPrizeResult;
    }

    private void calColdOrderList(List<MissedPrizeList> coldLimitList, int count) {
        for (MissedPrizeList missedPrizeListItem : coldLimitList) {
            List<MissedPrizeModel> missedList = missedPrizeListItem.getList();
            Collections.sort(missedList, new Comparator<MissedPrizeModel>() {
                @Override
                public int compare(MissedPrizeModel o1, MissedPrizeModel o2) {
                    return o2.getMissCnt().compareTo(o1.getMissCnt());
                }
            });
            missedPrizeListItem.setList(missedList.subList(0, count));
        }
        Collections.sort(coldLimitList, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o1.getNum().compareTo(o2.getNum());
            }
        });
    }

    private List<TCFFCPRIZE> queryLatestPrizeList(Integer limit) {
        return this.queryLatestPrizeList(limit, "time asc");
    }

    private List<TCFFCPRIZE> queryLatestPrizeList(Integer limit, String orderBy) {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

        Date date = DateUtils.getBaiduCurTime();
        Date minTime = DateUtils.addMinutes(-limit, date);
        criteria.andTimeGreaterThanOrEqualTo(minTime);
        tcffcprizeCondition.setOrderByClause(orderBy);
        return tcffcprizedao.selectByCondition(tcffcprizeCondition);
    }

    public void getZhu3Zhu6MissData(Integer limit) {
        List<TCFFCPRIZE> list = queryLatestPrizeList(limit);


    }

    //tjLx:统计类型：bdw,zhu6
    public List<MissedPrizeList> getLatestPrizeMissCnt(Integer limit, String type, String tjLx) throws ParseException {
        Date date = DateUtils.getBaiduCurTime();
        String curNo = TcffcPrizeConverter.genNofromTime(date);
        List<TCFFCPRIZE> list = queryLatestPrizeList(limit);
        Map<String, List<MissedPrizeModel>> missedMap = new HashMap<>();

        String[] zhuArr = {"qian3", "zhong3", "hou3"};
        if ("bdw".equals(tjLx)) {
            TCFFCPRIZE pre = null;
            for (TCFFCPRIZE item : list) {
                if ("hou4tou2".equals(type)) {
                    for (int[] arrItem : lxErXin) {
                        genCurNoMissedModel(pre,missedMap, item, arrItem, type);
                    }
                }else if  ("xin4tou2".equals(type)) {
                    for (int[] arrItem : ErXinAll) {
                        genCurNoMissedModel(pre,missedMap, item, arrItem, type);
                    }
                }
                else {
                    for (int i = 0; i < 10; i++) {
                        genCurNoMissedModel(pre,missedMap, item, new int[]{i}, type);
                    }
                }

                pre = item;
            }
        } else if ("zhu6".equals(tjLx) || "zhu3".equals(tjLx)) {
            for (TCFFCPRIZE item : list) {
                for (String zhu : zhuArr) {
                    this.genCurNoZhu3Zhu6MissedModel(missedMap, item, tjLx, zhu);
                }
            }
        }
        //处理不定位遗漏数据
        List<MissedPrizeList> result = new ArrayList<>();
        //map转list
        for (Map.Entry<String, List<MissedPrizeModel>> entry : missedMap.entrySet()) {
            MissedPrizeList missedPrizeList = new MissedPrizeList();
            String num = entry.getKey();
            List<MissedPrizeModel> missList = entry.getValue();
            missList.sort(new Comparator<MissedPrizeModel>() {
                @Override
                public int compare(MissedPrizeModel o1, MissedPrizeModel o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
            missedPrizeList.setNum(num);
            missedPrizeList.setList(missList);
            List<MissedPrizeModel> latestList = missedPrizeList.getList();
            if (null != latestList && latestList.size() > 1) {
                String lastNo = latestList.get(0).getNo();
                if (curNo.equals(lastNo)) {
                    missedPrizeList.setCurMissedCnt(0);
                } else {
                    TCFFCPRIZE start = new TCFFCPRIZE();
                    start.setNo(lastNo);
                    TCFFCPRIZE end = new TCFFCPRIZE();
                    end.setNo(curNo);
                    int latestDistance = LotteryUtil.calTcffcNoDistanceByNo(start, end);
                    missedPrizeList.setCurMissedCnt(latestDistance);
                }
            } else {
                missedPrizeList.setCurMissedCnt(0);
            }

            //addlrData

            result.add(missedPrizeList);
        }
        Collections.sort(result, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        return result;
    }

    private void sortMapByMissedSize() {


    }

    private void genCurNoMissedModel(TCFFCPRIZE pre,Map<String, List<MissedPrizeModel>> result, TCFFCPRIZE item, int[] nums, String type) {
        List<MissedPrizeModel> missedList = null;
        int wan = item.getWan();
        int qian = item.getQian();
        int bai = item.getBai();
        int shi = item.getShi();
        int ge = item.getGe();

        boolean isPrized = false;
        int prizeCnt = 0;
        boolean preIsPrized = false;
        for (int num : nums) {
            if ("wuxin".equals(type)) {
                if (wan == num || qian == num || bai == num
                        || shi == num || ge == num) {
                    isPrized = true;
                    if (wan == num) {
                        prizeCnt++;
                    }
                    if (qian == num) {
                        prizeCnt++;
                    }
                    if (bai == num) {
                        prizeCnt++;
                    }
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (ge == num) {
                        prizeCnt++;
                    }
                }

            }
            else if ("hou4p".equals(type) && pre != null) {
                int preWan = pre.getWan();
                int preQian = pre.getQian();
                int preBai = pre.getBai();
                int preShi = pre.getShi();
                int preGe = pre.getGe();
                if ((wan - preWan != num) && (qian - preQian != num) && (bai - preBai != num) && (shi - preShi != num) && (ge - preGe != num)
                    && (wan - preWan != -(10-num)) && (qian - preQian != -(10-num)) && (bai - preBai != -(10-num)) && (shi - preShi != -(10-num)) && (ge - preGe != -(10-num))
                ) {
                    isPrized = true;
                }

            }else if ("hou4".equals(type)) {
                if (qian == num || bai == num
                        || shi == num || ge == num) {
                    isPrized = true;
                    if (qian == num) {
                        prizeCnt++;
                    }
                    if (bai == num) {
                        prizeCnt++;
                    }
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (ge == num) {
                        prizeCnt++;
                    }
                }
            } else if ("hou3".equals(type)) {
                if (bai == num
                        || shi == num || ge == num) {
                    isPrized = true;
                    if (bai == num) {
                        prizeCnt++;
                    }
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (ge == num) {
                        prizeCnt++;
                    }
                }
            } else if ("qian3".equals(type)) {
                if (wan == num || qian == num || bai == num
                ) {
                    isPrized = true;
                    if (wan == num) {
                        prizeCnt++;
                    }
                    if (qian == num) {
                        prizeCnt++;
                    }
                    if (bai == num) {
                        prizeCnt++;
                    }
                }
            } else if ("zhong3".equals(type)) {
                if (shi == num || qian == num || bai == num
                ) {
                    isPrized = true;
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (qian == num) {
                        prizeCnt++;
                    }
                    if (bai == num) {
                        prizeCnt++;
                    }
                }
            } else if ("qian2".equals(type)) {
                if (wan == num || qian == num) {
                    isPrized = true;
                    if (wan == num) {
                        prizeCnt++;
                    }
                    if (qian == num) {
                        prizeCnt++;
                    }
                }
            } else if ("hou2".equals(type)) {
                if (shi == num || ge == num) {
                    isPrized = true;
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (ge == num) {
                        prizeCnt++;
                    }
                }
            }else if ("hou4tou2".equals(type)) {
                if (qian == num ||bai == num ||shi == num || ge == num) {
                    isPrized = true;
                    if (qian == num) {
                        prizeCnt++;
                    }
                    if (bai == num) {
                        prizeCnt++;
                    }
                    if (shi == num) {
                        prizeCnt++;
                    }
                    if (ge == num) {
                        prizeCnt++;
                    }
                }
            }else if ("xin4tou2".equals(type)) {
                if (wan == num || qian == num ||bai == num ||shi == num || ge == num) {
                    if(preIsPrized) {
                        isPrized = true;
                    }
                    preIsPrized = true;
                }
            }
        }

        //中
        if (isPrized) {
            MissedPrizeModel mpm = new MissedPrizeModel();
            mpm.setNo(item.getNo());
            mpm.setPrize(true);
            mpm.setPrize(item.getPrize());
            mpm.setTime(item.getTime());
            mpm.setPrizeCnt(prizeCnt);
            String numStr = "";
            for (int itemStr : nums) {
                numStr = numStr + itemStr + "";
            }
            if (result.get(numStr) != null) {
                List<MissedPrizeModel> tmpList = result.get(numStr);
                MissedPrizeModel lastPrizedNo = tmpList.get(tmpList.size() - 1);
                TCFFCPRIZE start = new TCFFCPRIZE();
                start.setNo(lastPrizedNo.getNo());
                start.setTime(lastPrizedNo.getTime());
                TCFFCPRIZE end = new TCFFCPRIZE();
                end.setNo(mpm.getNo());
                end.setTime(mpm.getTime());

                int distantce = LotteryUtil.calTcffcNoDistanceByNo(start, end);
                mpm.setMissCnt(distantce - 1);
                tmpList.add(mpm);
                missedList = tmpList;
            } else {
                mpm.setMissCnt(0);
                missedList = new ArrayList<>();
                missedList.add(mpm);
            }
            result.put(numStr, missedList);
        }
    }

    private void genCurNoZhu3Zhu6MissedModel(Map<String, List<MissedPrizeModel>> result, TCFFCPRIZE item, String zhuType, String type) {
        List<MissedPrizeModel> missedList = null;
        int wan = item.getWan();
        int qian = item.getQian();
        int bai = item.getBai();
        int shi = item.getShi();
        int ge = item.getGe();

        boolean isPrized = false;
        int prizeCnt = 0;
        if ("qian3".equals(type)) {
            if ("zhu6".equals(zhuType)) {
                if (wan != qian && wan != bai && qian != bai) {
                    isPrized = true;
                }
            } else if ("zhu3".equals(zhuType)) {
                if ((wan == qian || wan == bai || qian == bai) && (wan != qian || qian != bai)) {
                    isPrized = true;
                }
            }
        } else if ("zhong3".equals(type)) {
            if ("zhu6".equals(zhuType)) {
                if (shi != qian && shi != bai && qian != bai) {
                    isPrized = true;
                }
            } else if ("zhu3".equals(zhuType)) {
                if ((shi == qian || shi == bai || qian == bai) && (shi != qian || bai != qian)) {
                    isPrized = true;
                }
            }
        } else if ("hou3".equals(type)) {
            if ("zhu6".equals(zhuType)) {
                if (shi != ge && shi != bai && ge != bai) {
                    isPrized = true;
                }
            } else if ("zhu3".equals(zhuType)) {
                if ((shi == ge || shi == bai || ge == bai) && (shi != ge || bai != ge)) {
                    isPrized = true;
                }
            }
        }
        //中
        if (isPrized) {
            MissedPrizeModel mpm = new MissedPrizeModel();
            mpm.setNo(item.getNo());
            mpm.setPrize(true);
            mpm.setPrize(item.getPrize());
            mpm.setTime(item.getTime());
            mpm.setPrizeCnt(prizeCnt);
            if (result.get(type + zhuType) != null) {
                List<MissedPrizeModel> tmpList = result.get(type + zhuType);
                MissedPrizeModel lastPrizedNo = tmpList.get(tmpList.size() - 1);
                TCFFCPRIZE start = new TCFFCPRIZE();
                start.setNo(lastPrizedNo.getNo());
                start.setTime(lastPrizedNo.getTime());
                TCFFCPRIZE end = new TCFFCPRIZE();
                end.setNo(mpm.getNo());
                end.setTime(mpm.getTime());

                int distantce = LotteryUtil.calTcffcNoDistanceByNo(start, end);
                mpm.setMissCnt(distantce - 1);
                tmpList.add(mpm);
                missedList = tmpList;
            } else {
                mpm.setMissCnt(0);
                missedList = new ArrayList<>();
                missedList.add(mpm);
            }
            result.put(type + zhuType, missedList);
        }
    }
}
