package com.ds.zxm.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StockUtil {

	public static String judgeMarketFromStockCode(String stockCode) {
		if (StringUtils.isEmpty(stockCode)) {
			return "";
		}
		if (stockCode.startsWith("300") || stockCode.startsWith("00")) {
			return "sz";
		} else if (stockCode.startsWith("60")){
			return "sh";
		} else {
			return "";
		}
	}
	
	public static String appendMarket2StockCode(String stockCode) {
		return StockUtil.judgeMarketFromStockCode(stockCode) + stockCode;
	}

	public static void main(String[] args) {

		/*List<String> list = new ArrayList<>();
		for(int i=1;i<62; i++) {
			list.add("s" + i);
		}
		StockUtil.batchInsertorUpdateuserList(list);*/
		String result = StockUtil.mulBt("28,42,56,70,84,112,140,182,238,308,392,504,644,840,1078,1386,1778,2296,2940,3794,4872,6272,8064,10374,13356,17178", 2);
		System.out.println(result);
	}

	public static String mulBt(String src, int mul) {
		String[] splRst = src.split(",");
		StringBuilder sb = new StringBuilder();
		for(String item : splRst) {
			int itRes = Integer.valueOf(item) * mul;
			sb.append(itRes + ",");
		}
		return sb.toString();
	}
		public static void printlist(List<String>  list) {
		for (String item :list) {
			System.out.print(item + ",");
		}
		System.out.println("");
	}
		public static void batchInsertorUpdateuserList(List<String> toInsertList) {
		//新增或更新清洗后的数据
		//批量提新增数目
		int batchCnt = 100;
		if (toInsertList != null && toInsertList.size() > 0) {
			int groupNum = toInsertList.size() / batchCnt;
			int leftNum = toInsertList.size() % batchCnt;
			for (int i = 0; i <= groupNum; i++) {
				System.out.println("start " + i + " group");
				if (i == groupNum && leftNum != 0) {
					//最后一组
					StockUtil.printlist(toInsertList.subList(groupNum * batchCnt, toInsertList.size()));
				} else {
					StockUtil.printlist(toInsertList.subList(i * batchCnt, (i + 1) * batchCnt));
				}
			}
		}
	}
}
