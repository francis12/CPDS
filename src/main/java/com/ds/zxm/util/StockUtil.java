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

		List<String> list = new ArrayList<>();
		for(int i=1;i<62; i++) {
			list.add("s" + i);
		}
		StockUtil.batchInsertorUpdateuserList(list);
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
