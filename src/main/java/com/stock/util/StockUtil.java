package com.stock.util;

import org.apache.commons.lang.StringUtils;

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
}
