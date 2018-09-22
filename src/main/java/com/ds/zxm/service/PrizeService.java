package com.ds.zxm.service;

import com.ds.zxm.mapper.TCFFCPRIZEDAO;
import com.ds.zxm.model.*;
import com.ds.zxm.util.DateUtils;
import com.ds.zxm.util.LotteryUtil;
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
        return list;
    }

    public List<MissedPrizeList> getLatestPrizeMissCnt(Integer limit)throws ParseException {
        return this.getLatestPrizeMissCnt(limit,"hou4");
    }
    //codeLimit:计算遗漏排行
    public MissedPrizeResult getLatestPrizeMissCntByorder(Integer limit, String type, Integer latest1, Integer latest2,Integer coldLimit) throws ParseException {
        MissedPrizeResult missedPrizeResult = new MissedPrizeResult();
        List<MissedPrizeList> list = this.getLatestPrizeMissCnt(limit, type);
        Collections.sort(list, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o2.getList().size() - o1.getList().size();
            }
        });
        missedPrizeResult.setList(list);

        List<MissedPrizeList> lates30List = this.getLatestPrizeMissCnt(latest1, type);
        List<MissedPrizeList> lates60List = this.getLatestPrizeMissCnt(latest2, type);
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
        for(int i=0;i<lates30List.size();i++) {
            PrizeLrModel item = new PrizeLrModel();
            item.setNum(lates30List.get(i).getNum());
            item.setOrder(i+1);
            prizeLr30List.add(item);
        }
        PrizeLrModelList prizeLr30 = new PrizeLrModelList();
        prizeLr30.setLatestNum(latest1);
        prizeLr30.setList(prizeLr30List);
        lrResultList.add(prizeLr30);

        List<PrizeLrModel> prizeLr60List = new ArrayList<>();
        for(int i=0;i<lates60List.size();i++) {
            PrizeLrModel item = new PrizeLrModel();
            item.setNum(lates60List.get(i).getNum());
            item.setOrder(i+1);
            prizeLr60List.add(item);
        }
        PrizeLrModelList prizeLr60 = new PrizeLrModelList();
        prizeLr60.setLatestNum(latest2);
        prizeLr60.setList(prizeLr60List);
        lrResultList.add(prizeLr60);

        missedPrizeResult.setLrList(lrResultList);

        //计算遗漏排行
        List<MissedPrizeList> coldLimitList = this.getLatestPrizeMissCnt(coldLimit, type);
        for(MissedPrizeList missedPrizeListItem : coldLimitList ) {
            List<MissedPrizeModel> missedList = missedPrizeListItem.getList();
            Collections.sort(missedList, new Comparator<MissedPrizeModel>() {
                @Override
                public int compare(MissedPrizeModel o1, MissedPrizeModel o2) {
                    return o2.getMissCnt().compareTo(o1.getMissCnt());
                }
            });
            missedPrizeListItem.setList(missedList.subList(0,1));
        }
        Collections.sort(coldLimitList, new Comparator<MissedPrizeList>() {
            @Override
            public int compare(MissedPrizeList o1, MissedPrizeList o2) {
                return o1.getNum().compareTo(o2.getNum());
            }
        });
        missedPrizeResult.setColdList(coldLimitList);
        return missedPrizeResult;
    }
    public List<MissedPrizeList> getLatestPrizeMissCnt(Integer limit, String type) throws ParseException {
        TCFFCPRIZECondition tcffcprizeCondition = new TCFFCPRIZECondition();
        TCFFCPRIZECondition.Criteria criteria = tcffcprizeCondition.createCriteria();

        Date date = DateUtils.getBaiduCurTime();
        String curNo = TcffcPrizeConverter.genNofromTime(date);
        Date minTime = DateUtils.addMinutes(-limit, date);
        criteria.andTimeGreaterThanOrEqualTo(minTime);
        tcffcprizeCondition.setOrderByClause("time asc");
        List<TCFFCPRIZE> list = tcffcprizedao.selectByCondition(tcffcprizeCondition);

        Map<String, List<MissedPrizeModel>> missedMap = new HashMap<>();
        for(TCFFCPRIZE item : list) {
            for(int i=0; i<10;i++) {
                genCurNoMissedModel(missedMap, item,i, type);
            }
        }
        List<MissedPrizeList> result = new ArrayList<>();
        //map转list
        for(Map.Entry<String, List<MissedPrizeModel>> entry : missedMap.entrySet()) {
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
            if(null != latestList && latestList.size()>1) {
                String lastNo = latestList.get(0).getNo();
                if(curNo.equals(lastNo)) {
                    missedPrizeList.setCurMissedCnt(0);
                } else {
                    TCFFCPRIZE start = new TCFFCPRIZE();
                    start.setNo(lastNo);
                    TCFFCPRIZE end = new TCFFCPRIZE();
                    end.setNo(curNo);
                    int latestDistance = LotteryUtil.calTcffcNoDistanceByNo(start, end);
                    missedPrizeList.setCurMissedCnt(latestDistance);
                }
            } else{
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

    private void sortMapByMissedSize(){


    }
    private void genCurNoMissedModel(Map<String, List<MissedPrizeModel>> result, TCFFCPRIZE item, int num, String type) {
        List<MissedPrizeModel> missedList = null;
        int wan = item.getWan();
        int qian = item.getQian();
        int bai = item.getBai();
        int shi = item.getShi();
        int ge = item .getGe();

        boolean isPrized = false;
        int prizeCnt = 0;
        if("wuxin".equals(type)) {
            if(wan== num || qian == num || bai == num
                    || shi ==num || ge == num) {
                isPrized = true;
                if(wan == num) {prizeCnt++;}
                if(qian == num) {prizeCnt++;}
                if(bai == num) {prizeCnt++;}
                if(shi == num) {prizeCnt++;}
                if(ge == num) {prizeCnt++;}
            }
        }else if("hou4".equals(type)) {
            if(qian == num || bai == num
                    || shi ==num || ge == num) {
                isPrized = true;
                if(qian == num) {prizeCnt++;}
                if(bai == num) {prizeCnt++;}
                if(shi == num) {prizeCnt++;}
                if(ge == num) {prizeCnt++;}
            }
        } else if("hou3".equals(type)) {
            if(bai == num
                    || shi ==num || ge == num) {
                isPrized = true;
                if(bai == num) {prizeCnt++;}
                if(shi == num) {prizeCnt++;}
                if(ge == num) {prizeCnt++;}
            }
        }
        else if("qian3".equals(type)) {
            if( wan ==num || qian == num || bai == num
                    ) {
                isPrized = true;
                if(wan == num) {prizeCnt++;}
                if(qian == num) {prizeCnt++;}
                if(bai == num) {prizeCnt++;}
            }
        }
        else if("zhong3".equals(type)) {
            if( shi ==num || qian == num || bai == num
                    ) {
                isPrized = true;
                if(shi == num) {prizeCnt++;}
                if(qian == num) {prizeCnt++;}
                if(bai == num) {prizeCnt++;}
            }
        }
        //中
        if(isPrized) {
            MissedPrizeModel mpm = new MissedPrizeModel();
            mpm.setNo(item.getNo());
            mpm.setPrize(true);
            mpm.setPrize(item.getPrize());
            mpm.setTime(item.getTime());
            mpm.setPrizeCnt(prizeCnt);
            if(result.get(""+ num) != null) {
                List<MissedPrizeModel> tmpList = result.get(""+num);
                MissedPrizeModel lastPrizedNo = tmpList.get(tmpList.size()-1);
                TCFFCPRIZE start = new TCFFCPRIZE();
                start.setNo(lastPrizedNo.getNo());
                start.setTime(lastPrizedNo.getTime());
                TCFFCPRIZE end = new TCFFCPRIZE();
                end.setNo(mpm.getNo());
                end.setTime(mpm.getTime());

                int distantce = LotteryUtil.calTcffcNoDistanceByNo(start, end);
                mpm.setMissCnt(distantce-1);
                tmpList.add(mpm);
                missedList = tmpList;
            } else {
                mpm.setMissCnt(0);
                missedList= new ArrayList<>();
                missedList.add(mpm);
            }
            result.put(""+num, missedList);
        }
    }
}
