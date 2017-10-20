package com.ds.zxm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ds.zxm.model.LotteryDetail;

public class LotteryUtil {

	//定位abc-aed-c转成aac,aec等
	public static List<LotteryDetail> convertStr2DetailList(String src) {
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
	}
	/** 
     * 递归实现dimValue中的笛卡尔积，结果放在result中 
     * @param dimValue 原始数据 
     * @param result 结果数据 
     * @param layer dimValue的层数 
     * @param curList 每次笛卡尔积的结果 
     */  
    private static void recursive (List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList) {  
        if (layer < dimValue.size() - 1) {  
            if (dimValue.get(layer).size() == 0) {  
                recursive(dimValue, result, layer + 1, curList);  
            } else {  
                for (int i = 0; i < dimValue.get(layer).size(); i++) {  
                    List<String> list = new ArrayList<String>(curList);  
                    list.add(dimValue.get(layer).get(i));  
                    recursive(dimValue, result, layer + 1, list);  
                }  
            }  
        } else if (layer == dimValue.size() - 1) {  
            if (dimValue.get(layer).size() == 0) {  
                result.add(curList);  
            } else {  
                for (int i = 0; i < dimValue.get(layer).size(); i++) {  
                    List<String> list = new ArrayList<String>(curList);  
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
				Date curDate = DateUtils.String2Date(dateStr, "yyMMdd");
				nextNo = DateUtils.date2String(DateUtils.addDate(1, curDate), "yyMMdd") + "001";
			}
		} else if ("n198_60s".equals(caipiao) || "rd60s".equals(caipiao)) {
			//201710190726
			String dateStr = no.substring(0, 8);
			int postNo = Integer.valueOf(no.substring(8));
			nextNo = ("0000" + (postNo + 1));
			nextNo = nextNo.substring(nextNo.length() - 4);
			nextNo = dateStr + nextNo;
		}
		return nextNo;
	}

	public static int compareCQAwardNO(String no1, String no2) throws ParseException {
		Date date1 = DateUtils.String2Date(no1.substring(0, 6), "yyMMdd");
		Date date2 = DateUtils.String2Date(no2.substring(0, 6), "yyMMdd");
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
 	    public static void main(String args[]) throws Exception {
	        
	        //permutation("135".toCharArray(),0); 
	    	//convertStr2DetailList("345-012-7");
			while (true) {
				Thread.sleep(5000);
				File file = new File("C:\\test.txt");
				new FileOutputStream(file).write(new Date().toString().getBytes());

			}
	    }  
}
