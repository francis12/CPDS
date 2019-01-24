package com.ssc.util;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ssc.model.TCFFCPRIZE;
import com.ssc.model.TcffcPrizeConverter;
import org.apache.commons.io.FileUtils;

public class LotteryUtil {

	//public static  final  String noPath =  "C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator;
	public static  final  String noPath = "D:" + File.separator  + "log" + File.separator;
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LotteryUtil.class);

	//定位abc-aed-c转成aac,aec等
	/*public static List<LotteryDetail> convertStr2DetailList(String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String[] srcArray = src.split("-");
		List<List<String>> srcList = new ArrayList<List<String>>();
		for (String item : srcArray) {
			List<String> dimValueItem = new ArrayList<String>();
			for (char c : item.toCharArray()) {
				dimValueItem.add(String.valueOf(c));
			}
			srcList.add(dimValueItem);
		}

		List<List<String>> recursiveResult = new ArrayList<List<String>>();
		// 递归实现笛卡尔积
		recursive(srcList, recursiveResult, 0, new ArrayList<String>());

		System.out.println("递归实现笛卡尔乘积: 共 " + recursiveResult.size() + " 个结果");
		for (List<String> list : recursiveResult) {
			for (String string : list) {
				System.out.print(string + " ");
			}
			System.out.println();
		}

		List<LotteryDetail> result = new ArrayList<LotteryDetail>();
		if (srcArray.length == 2) {
			//二星
			for (List<String> list : recursiveResult) {
				LotteryDetail detail  = new LotteryDetail();
				detail.setNum2(list.get(0));
				detail.setNum1(list.get(1));
				result.add(detail);
			}
		} else if (srcArray.length == 3) {
			for (List<String> list : recursiveResult) {
				LotteryDetail detail  = new LotteryDetail();
				detail.setNum3(list.get(0));
				detail.setNum2(list.get(1));
				detail.setNum1(list.get(2));
				result.add(detail);
			}
		}
		return result;
	}*/
	/**
	 * 递归实现dimValue中的笛卡尔积，结果放在result中
	 * @param dimValue 原始数据
	 * @param result 结果数据
	 * @param layer dimValue的层数
	 * @param curList 每次笛卡尔积的结果
	 */
	private static void recursive (List<List<String>> dimValue, List<Set<String>> result, int layer, Set<String> curList) {
		if (layer < dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				recursive(dimValue, result, layer + 1, curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					Set<String> list = new HashSet<String>(curList);
					list.add(dimValue.get(layer).get(i));
					recursive(dimValue, result, layer + 1, list);
				}
			}
		} else if (layer == dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				result.add(curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					Set<String> list = new HashSet<>(curList);
					list.add(dimValue.get(layer).get(i));
					result.add(list);
				}
			}
		}
	}
	//排列-定位
	public static void permutation(char[]ss,int i){
		if(ss==null||i<0 ||i>ss.length){
			return;
		}
		if(i==ss.length){
			System.out.println(new String(ss));
		}else{
			for(int j=i;j<ss.length;j++){
				char temp=ss[j];//交换前缀,使之产生下一个前缀
				ss[j]=ss[i];
				ss[i]=temp;
				permutation(ss,i+1);
				temp=ss[j]; //将前缀换回来,继续做上一个的前缀排列.
				ss[j]=ss[i];
				ss[i]=temp;
			}
		}
	}
	//组合-- 任选
	public static void combiantion(char chs[]){
		if(chs==null||chs.length==0){
			return ;
		}
		List<Character> list=new ArrayList();
		for(int i=1;i<=chs.length;i++){
			combine(chs,0,i,list);
		}
	}
	//从字符数组中第begin个字符开始挑选number个字符加入list中
	public static void combine(char []cs,int begin,int number,List<Character> list){
		if(number==0){
			System.out.println(list.toString());
			return ;
		}
		if(begin==cs.length){
			return;
		}
		list.add(cs[begin]);
		combine(cs,begin+1,number-1,list);
		list.remove((Character)cs[begin]);
		combine(cs,begin+1,number,list);
	}
	//cqssc
	public static String getNextAwardNo(String no, String caipiao) throws ParseException {
		String nextNo = "";
		//171010051,postfix001-120
		if("chongqing".equals(caipiao)) {
			String dateStr = no.substring(0, 8);
			int postNo = Integer.valueOf(no.substring(8));
			if(postNo != 120) {
				nextNo = ("000" + (postNo + 1));
				nextNo = nextNo.substring(nextNo.length() - 3);
				return dateStr + nextNo;
			} else {
				Date curDate = DateUtils.String2Date(dateStr, "yyyyMMdd");
				nextNo = DateUtils.date2String(DateUtils.addDate(1, curDate), "yyyyMMdd") + "001";
			}
		} else if ("n198_60s".equals(caipiao) || "rd60s".equals(caipiao)) {
			//201710190726
			String dateStr = no.substring(0, 8);
			int postNo = Integer.valueOf(no.substring(8));
			nextNo = ("0000" + (postNo + 1));
			nextNo = nextNo.substring(nextNo.length() - 4);
			nextNo = dateStr + nextNo;
		} else if ("flb90s".equals(caipiao) ) {
			//201710190726
			String dateStr = no.substring(0, 8);
			int postNo = Integer.valueOf(no.substring(8));
			nextNo = ("000" + (postNo + 1));
			nextNo = nextNo.substring(nextNo.length() - 3);
			nextNo = dateStr + nextNo;
		}
		return nextNo;
	}

	public static int compareCQAwardNO(String no1, String no2) throws ParseException {
		Date date1 = DateUtils.String2Date(no1.substring(0, 8), "yyyyMMdd");
		Date date2 = DateUtils.String2Date(no2.substring(0, 8), "yyyyMMdd");
		if(date1.compareTo(date2) != 0) {
			return date1.compareTo(date2);
		} else {
			int postFix1 = Integer.valueOf(no1.substring(no1.length() -3));
			int postFix2 =  Integer.valueOf(no2.substring(no2.length() -3));
			if (postFix1 > postFix2) {
				return  1;
			} else if(postFix1 == postFix2) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	//201710200456
	public static int compare19860AwardNO(String no1, String no2) throws ParseException {
		Date date1 = DateUtils.String2Date(no1.substring(0, 8), "yyyyMMdd");
		Date date2 = DateUtils.String2Date(no2.substring(0, 8), "yyyyMMdd");
		if(date1.compareTo(date2) != 0) {
			return date1.compareTo(date2);
		} else {
			int postFix1 = Integer.valueOf(no1.substring(no1.length() -4));
			int postFix2 =  Integer.valueOf(no2.substring(no2.length() -4));
			if (postFix1 > postFix2) {
				return  1;
			} else if(postFix1 == postFix2) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public  static  void writeTmpTxt2PrizeFile(String caipiao, String id){
		try {
			//冲掉上次方案，防止赚投误取
			FileUtils.write(new File(noPath + caipiao + id + ".txt"), "等待前台刷新方案中..."  + "\r",false);
		} catch (IOException e) {
			log.error("write tmp file data er", e);
		}
	}
	public static  String genPy3NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "0123789";
			case 1: return "0123489";
			case 2: return "0123459";
			case 3: return "0123456";
			case 4: return "1234567";
			case 5: return "2345678";
			case 6: return "3456789";
			case 7: return "0456789";
			case 8: return "0156789";
			case 9: return "0126789";
		}
		return  result;
	}

	//返回前3后4个数字包括自己共8个数字
	public static  String genPyPost4NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "78901234";
			case 1: return "89012345";
			case 2: return "90123456";
			case 3: return "01234567";
			case 4: return "12345678";
			case 5: return "23456789";
			case 6: return "34567890";
			case 7: return "45678901";
			case 8: return "56789012";
			case 9: return "67890123";
		}
		return  result;
	}
	//与genPyPost4NumStr对应
	public static boolean judgeIsmatchBetweenPost4(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("78901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("89012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("90123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("01234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("12345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("23456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("34567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("45678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("56789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("67890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}

	//返回前3后4个数字包括自己共8个数字
	public static  String genPy4NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "678901234";
			case 1: return "789012345";
			case 2: return "890123456";
			case 3: return "901234567";
			case 4: return "012345678";
			case 5: return "123456789";
			case 6: return "234567890";
			case 7: return "345678901";
			case 8: return "456789012";
			case 9: return "567890123";
		}
		return  result;
	}
	//与genPy4NumStr对应
	public static boolean judgeIsmatchBetween4(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("678901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("789012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("890123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("901234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("012345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("123456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("234567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("345678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("456789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("567890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}
	//3,0     6,5
//返回前4后4个数字包括自己共9个数字
	public static  String genPy4AllNumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "678901234";
			case 1: return "789012345";
			case 2: return "890123456";
			case 3: return "901234567";
			case 4: return "012345678";
			case 5: return "123456789";
			case 6: return "234567890";
			case 7: return "345678901";
			case 8: return "456789012";
			case 9: return "567890123";
		}
		return  result;
	}
	//与genPy4AllNumStr对应
	public static boolean judgeIsmatchBetweenAll4(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("678901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("789012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("890123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("901234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("012345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("123456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("234567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("345678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("456789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("567890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}



	//比较两个数相差不超过3
	public static boolean judgeIsmatchBetween3(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("7890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("8901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("9012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("0123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("1234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("2345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("3456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("4567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("5678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("6789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}

	//a*bc玩法转成通用注数
	public static String convertCha2Normal(String src1, String src2) {
		return "";
	}

	//根据当前期生成下期五星计划
	public static List<TCFFCPRIZE> genNextWuXingPrizeByGen2(TCFFCPRIZE genPrize) {
		int startPost = genPrize.getOnlineNum()-5000;
		int endPost = genPrize.getOnlineNum() + 5000;
		List<TCFFCPRIZE> result = createPrizeNumList2(startPost, endPost, genPrize.getTime());
		return result;

	}

	//根据当前期生成下期五星计划
	public static List<TCFFCPRIZE> genNextWuXingPrizeByGen(TCFFCPRIZE genPrize) {
		int startPost = genPrize.getOnlineNum() - 10000;
		int endPost = genPrize.getOnlineNum() + 10000;
		List<TCFFCPRIZE> result = createPrizeNumList2(startPost, endPost, genPrize.getTime());
		return result;

	}

	public static List<TCFFCPRIZE> genNextWuXingPrizeByGen(TCFFCPRIZE genPrize, int size) {
		int startPost = genPrize.getOnlineNum() - size/2;
		int endPost = genPrize.getOnlineNum() + size/2;

		List<TCFFCPRIZE> result = new ArrayList<>();
		for(int i = startPost; i<= endPost;i++) {
			TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
			tcffcprize.setTime(genPrize.getTime());
			tcffcprize.setOnlineNum(i);
			tcffcprize.setLotteryCode("TCFFC");
			TCFFCPRIZE convertResult = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
			result.add(convertResult);
		}
		return result;

	}


	//生成投注号码
	public static List<TCFFCPRIZE> createPrizeNumList2(int preStart, int preEnd, Date time) {
		List<TCFFCPRIZE> toPrizeNumList = new ArrayList<>();
		int startSub = new BigDecimal(preStart).divide(new BigDecimal(10000), 0 , RoundingMode.HALF_UP).intValue();
		int endSub = new BigDecimal(preEnd).divide(new BigDecimal(10000), 0 , RoundingMode.HALF_UP).intValue();
		for(int j = startSub; j <= endSub; j++) {
			for(int i = 0; i<9999; i++) {
				int online = j * 10000 + i;
				TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
				tcffcprize.setTime(time);
				tcffcprize.setOnlineNum(online);
				tcffcprize.setLotteryCode("TCFFC");
				TCFFCPRIZE convertResult = TcffcPrizeConverter.convert2TCFFCPrize(tcffcprize);
				toPrizeNumList.add(convertResult);
			}
		}
		return toPrizeNumList;
	}

	//从文件中获取号码
	public static void genNumsFromFile(File file) {
		try {
			List<String> noList = FileUtils.readLines(file);

			Map<String, Integer> cntMap = new HashMap<>();
			noList.stream().forEach( item -> {
				String fourStr = item.substring(0,4);
				if (cntMap.get(fourStr) != null) {
					cntMap.put(fourStr, cntMap.get(fourStr) + 1);
				} else {
					cntMap.put(fourStr, 1);
				}
			});
			Map<String, Integer> map = sortMapByValue(cntMap, "1");
			System.out.println("过滤后4星共：" + map.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 1:asc, -1:desc
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap, String type) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
				oriMap.entrySet());
		if("1".equals(type)) {
			Collections.sort(entryList, new MapValueComparator());
		} else {
			Collections.sort(entryList, new MapValueComparator2());
		}

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	static class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

		@Override
		public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
			return me1.getValue().compareTo(me2.getValue());
		}
	}
	static class MapValueComparator2 implements Comparator<Map.Entry<String, Integer>> {

		@Override
		public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
			return me2.getValue().compareTo(me1.getValue());
		}
	}



	public static Map<String, Double> sortMapByDoubleValue(Map<String, Double> oriMap, String type) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Double> sortedMap = new LinkedHashMap<>();
		List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(
				oriMap.entrySet());
		if("1".equals(type)) {
			Collections.sort(entryList, new MapDoubleValueComparator());
		} else {
			Collections.sort(entryList, new MapDoubleValueComparator2());
		}

		Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
		Map.Entry<String, Double> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	static class MapDoubleValueComparator implements Comparator<Map.Entry<String, Double>> {

		@Override
		public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
			return me1.getValue().compareTo(me2.getValue());
		}
	}
	static class MapDoubleValueComparator2 implements Comparator<Map.Entry<String, Double>> {

		@Override
		public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
			return me2.getValue().compareTo(me1.getValue());
		}
	}
	//计算分分彩相隔期数
	public static int calTcffcNoDistance(TCFFCPRIZE startNo, TCFFCPRIZE endNo) {
		Long secDistance = DateUtils.calculateSeconds(endNo.getTime(), startNo.getTime());
		int minDistance = (secDistance.intValue()/60);
		return minDistance;
	}
	//计算分分彩相隔期数
	public static int calTcffcNoDistanceByNo(TCFFCPRIZE startNo, TCFFCPRIZE endNo) {
		int minDistance = 0;
		String startNoStr = startNo.getNo().substring(9);
		String startDateStr = startNo.getNo().substring(0,8);
		String endNoStr = endNo.getNo().substring(9);
		String endDateStr = endNo.getNo().substring(0,8);

		if(startDateStr.equals(endDateStr)) {
			minDistance = Integer.valueOf(endNoStr) - Integer.valueOf(startNoStr);
		} else {
			int dateDistance = Integer.valueOf(endDateStr) - Integer.valueOf(startDateStr);
			minDistance = (1440*dateDistance+Integer.valueOf(endNoStr)) - Integer.valueOf(startNoStr);
		}
		return minDistance;
	}
	static  List<String> allSiXinNums = new ArrayList<>();
	static {
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				for(int k = 0; k<10; k++) {
					for(int p = 0; p<10; p++) {
						String item = "" + i + j + k + p;
						allSiXinNums.add(item);
					}
				}
			}
		}
	}

	//胆码组4星(出现2,3,4)
	public static List<String> genNumfromDan(List<String> danList, boolean isToJudgeDan, boolean isToJudge0, boolean isToJudge4, boolean isToJudge3, boolean isToJudge2) {
		List<String> result = new ArrayList<>();
		for (String siXinItem : allSiXinNums) {
			int matchCnt = 0;
			//计算胆匹配的次数
			for (String danItem : danList) {
				char[] array =  siXinItem.toCharArray();
				for (char item : array) {
					if ((""+ item).equals(danItem)) {
						matchCnt++;
					}
				}
			}
			boolean isDanCntMatch = false;
			if (matchCnt ==2 || matchCnt == 3 || matchCnt == 4) {
				isDanCntMatch = true;
			}
			//0同
			boolean is0SameMatch = false;
			if (calCntInStrList(siXinItem, "0") <= 1
					&& calCntInStrList(siXinItem, "1") <= 1
					&& calCntInStrList(siXinItem, "2") <= 1
					&& calCntInStrList(siXinItem, "3") <= 1
					&& calCntInStrList(siXinItem, "4") <= 1
					&& calCntInStrList(siXinItem, "5") <= 1
					&& calCntInStrList(siXinItem, "6") <= 1
					&& calCntInStrList(siXinItem, "7") <= 1
					&& calCntInStrList(siXinItem, "8") <= 1
					&& calCntInStrList(siXinItem, "9") <= 1
			) {
				is0SameMatch = true;
			}
			//4同
			boolean is4SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 4
					|| calCntInStrList(siXinItem, "1") == 4
					|| calCntInStrList(siXinItem, "2") == 4
					|| calCntInStrList(siXinItem, "3") == 4
					|| calCntInStrList(siXinItem, "4") == 4
					|| calCntInStrList(siXinItem, "5") == 4
					|| calCntInStrList(siXinItem, "6") == 4
					|| calCntInStrList(siXinItem, "7") == 4
					|| calCntInStrList(siXinItem, "8") == 4
					|| calCntInStrList(siXinItem, "9") == 4
			) {
				is4SameMatch = true;
			}

			//3同
			boolean is3SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 3
					|| calCntInStrList(siXinItem, "1") == 3
					|| calCntInStrList(siXinItem, "2") == 3
					|| calCntInStrList(siXinItem, "3") == 3
					|| calCntInStrList(siXinItem, "4") == 3
					|| calCntInStrList(siXinItem, "5") == 3
					|| calCntInStrList(siXinItem, "6") == 3
					|| calCntInStrList(siXinItem, "7") == 3
					|| calCntInStrList(siXinItem, "8") == 3
					|| calCntInStrList(siXinItem, "9") == 3
			) {
				is3SameMatch = true;
			}

			//2同
			boolean is2SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 2
					|| calCntInStrList(siXinItem, "1") == 2
					|| calCntInStrList(siXinItem, "2") == 2
					|| calCntInStrList(siXinItem, "3") == 2
					|| calCntInStrList(siXinItem, "4") == 2
					|| calCntInStrList(siXinItem, "5") == 2
					|| calCntInStrList(siXinItem, "6") == 2
					|| calCntInStrList(siXinItem, "7") == 2
					|| calCntInStrList(siXinItem, "8") == 2
					|| calCntInStrList(siXinItem, "9") == 2
			) {
				is2SameMatch = true;
			}

			boolean jundgeResult = true;
			if (isToJudgeDan) {
				jundgeResult = jundgeResult & isDanCntMatch;
			}
			if (isToJudge0) {
				jundgeResult = jundgeResult & isDanCntMatch;
			}
			if (isToJudge4) {
				jundgeResult = jundgeResult & isDanCntMatch;
			}
			if (isToJudge3) {
				jundgeResult = jundgeResult & isDanCntMatch;
			}
			if (isToJudge2) {
				jundgeResult = jundgeResult & isDanCntMatch;
			}

			if(jundgeResult) {
				result.add(siXinItem);
			}
		}

		Collections.sort(result);
		return result;
	}

	//胆码组4星(出现2,3,4)
	public static List<String> genNumfromDan5(List<String> danList, boolean isToJudgeDan, boolean isToJudge0, boolean isToJudge4, boolean isToJudge3, boolean isToJudge2) {
		List<String> result = new ArrayList<>();
		for (String siXinItem : allSiXinNums) {
			int matchCnt = 0;
			//计算胆匹配的次数
			for (String danItem : danList) {
				char[] array =  siXinItem.toCharArray();
				for (char item : array) {
					if ((""+ item).equals(danItem)) {
						matchCnt++;
					}
				}
			}
			boolean isDanCntMatch = false;
			if (matchCnt ==2 || matchCnt == 3 || matchCnt == 4) {
				isDanCntMatch = true;
			}
			//0同
			boolean is0SameMatch = false;
			if (calCntInStrList(siXinItem, "0") <= 1
					&& calCntInStrList(siXinItem, "1") <= 1
					&& calCntInStrList(siXinItem, "2") <= 1
					&& calCntInStrList(siXinItem, "3") <= 1
					&& calCntInStrList(siXinItem, "4") <= 1
					&& calCntInStrList(siXinItem, "5") <= 1
					&& calCntInStrList(siXinItem, "6") <= 1
					&& calCntInStrList(siXinItem, "7") <= 1
					&& calCntInStrList(siXinItem, "8") <= 1
					&& calCntInStrList(siXinItem, "9") <= 1
			) {
				is0SameMatch = true;
			}
			//4同
			boolean is4SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 4
					|| calCntInStrList(siXinItem, "1") == 4
					|| calCntInStrList(siXinItem, "2") == 4
					|| calCntInStrList(siXinItem, "3") == 4
					|| calCntInStrList(siXinItem, "4") == 4
					|| calCntInStrList(siXinItem, "5") == 4
					|| calCntInStrList(siXinItem, "6") == 4
					|| calCntInStrList(siXinItem, "7") == 4
					|| calCntInStrList(siXinItem, "8") == 4
					|| calCntInStrList(siXinItem, "9") == 4
			) {
				is4SameMatch = true;
			}

			//3同
			boolean is3SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 3
					|| calCntInStrList(siXinItem, "1") == 3
					|| calCntInStrList(siXinItem, "2") == 3
					|| calCntInStrList(siXinItem, "3") == 3
					|| calCntInStrList(siXinItem, "4") == 3
					|| calCntInStrList(siXinItem, "5") == 3
					|| calCntInStrList(siXinItem, "6") == 3
					|| calCntInStrList(siXinItem, "7") == 3
					|| calCntInStrList(siXinItem, "8") == 3
					|| calCntInStrList(siXinItem, "9") == 3
			) {
				is3SameMatch = true;
			}

			//2同
			boolean is2SameMatch = false;
			if (calCntInStrList(siXinItem, "0") == 2
					|| calCntInStrList(siXinItem, "1") == 2
					|| calCntInStrList(siXinItem, "2") == 2
					|| calCntInStrList(siXinItem, "3") == 2
					|| calCntInStrList(siXinItem, "4") == 2
					|| calCntInStrList(siXinItem, "5") == 2
					|| calCntInStrList(siXinItem, "6") == 2
					|| calCntInStrList(siXinItem, "7") == 2
					|| calCntInStrList(siXinItem, "8") == 2
					|| calCntInStrList(siXinItem, "9") == 2
			) {
				is2SameMatch = true;
			}


			if(isDanCntMatch && is0SameMatch) {
				result.add(siXinItem);
			}
		}

		Collections.sort(result);
		return result;
	}

	public static int calCntInStrList(String list, String src) {
		int matchCnt = 0;
		//计算胆匹配的次数
		char[] strArr = list.toCharArray();
		for (char charItem : strArr) {
			String item = "" + charItem;
			if (src.equals(item)) {
				matchCnt++;
			}
		}
		return matchCnt;
	}


	public static void startFetchPrizeDataFromQQ() {
		String s = new SimpleDateFormat("HHmmss").format(new Date());
		String hh = s.substring(0, 2);
		String mm = s.substring(2, 4);
		String ss = s.substring(4, 6);
		int minute = 0;
		minute = Integer.parseInt(mm);
		int second = Integer.parseInt(ss);
		fetchPrizeDataFromQQ(minute, second);
	}
	static int preMinute = 0;
	static int preOnlineNum = 0;
	public static void fetchPrizeDataFromQQ(int minute,int second) {
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL("http://mma.qq.com/cgi-bin/im/online&callback");
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
			int code = conn.getResponseCode();

			//调用成功
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				String result = buffer.toString();
				result = result.substring(12);
				result = result.substring(0, result.length() - 1);
				JSONObject jsonObject = JSONObject.parseObject(result);
				Integer onlineNum = (Integer) jsonObject.get("c");
				Integer adjsutNum = onlineNum - preOnlineNum;
				if (adjsutNum != 0 || (adjsutNum == 0 && (minute - preMinute == 1 && second >= 55))) {
					//人数有变化或者时间相隔大于1分50秒
					TCFFCPRIZE tcffcprize = TcffcPrizeConverter.convert2TCFFCPrizeFromOnlineNum(onlineNum, new Date(), adjsutNum);
					String logInfo = tcffcprize.getNo() + "开奖号码:" + tcffcprize.getPrize() + ",在线人数:" + tcffcprize.getOnlineNum() + ",波动数:" + tcffcprize.getAdjustNum();
					log.info(logInfo);

					//预测20180726-1046:57953 zhuan(1)zhuan
					String genStr = "预测" + tcffcprize.getNo() + " zhuan(" + tcffcprize.getGe() + ")zhuan ";
					int sdn = tcffcprize.getAdjustNum();
					int ge = sdn%10;
					int shi = sdn%100/10;
					String prizeLh = tcffcprize.getShi() > tcffcprize.getGe()?"龙":"虎";
					String bdLh = (shi>ge?"龙":"虎");

					/*log.info("开奖龙虎为：" + prizeLh + ",十个波动龙虎为:" +  bdLh + "\r\n");

					if(prizeLh.equals(bdLh)) {
						log.info("开奖十:" + tcffcprize.getShi() + ",个：" + tcffcprize.getGe());
					}*/
					//FileUtils.writeStringToFile(new File("latest.txt"), genStr, false);
					//FileUtils.writeStringToFile(new File("latest.txt"), "waiting", false);


				} else {
					Thread.sleep(10);
					return ;
				}
				preMinute = minute;
				preOnlineNum = onlineNum;
			}
		} catch (Exception e) {
			log.error("fetchPrizeDataFromQQ error", e);
		}
		return;
	}
	public static void main(String[] args) {
		System.out.println((int)(0.98*10));

		System.out.println(LotteryUtil.getRandomNums(10,7));
	}
	public static String getRandomNums(int srcNum, int dstNum) {
		StringBuffer result = new StringBuffer();
		int[] nums = new int[dstNum];
		int lucky;
		for (int i = 0; i < nums.length; i++) {
			do {
				lucky = (int)Math.floor(Math.random()*(srcNum+1)) ;
			} while (isExist(nums, lucky));
			nums[i] = lucky;
		}
		for (int i : nums) {
			result.append(i + " ");
		}
		return result.toString();
	}

	// 判断是否已经在数组中存在该元素
	public static boolean isExist(int[] array, int key){
		for (int i = 0; i < array.length; i++) {
			if (array[i] == key) {
				return true;
			}
		}
		return false;
	}
}
