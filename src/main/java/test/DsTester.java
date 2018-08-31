package test;

import org.apache.commons.lang.StringUtils;

public class DsTester {

    public static void main2(String[] arg) {
        try {
            System.err.println("测试->将emoji表情替换成*");
            String text = "This is a smiley \uD83C\uDFA6 face\uD860\uDD5D \uD860\uDE07 \uD860\uDEE2 \uD863\uDCCA \uD863\uDCCD \uD863\uDCD2 \uD867\uDD98 ";
            System.out.println(text);
            System.out.println(text.length());
            System.out.println(text.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "*"));
            System.out.println((text));
            //输出结果
            //This is a smiley �� face�� �� �� �� �� �� ��
            //45
            //This is a smiley * face�� �� �� �� �� �� ��
            //This is a smiley * face* * * * * * *

            System.err.println("测试->替换四个字节的字符 '\\xF0\\x9F\\x98\\x84\\xF0\\x9F）的解决方案 ��");
            String title = "ff的范德萨分��������Llfldakf;dsk。f������������daslfjdsa;lfkjdsd'j'l'f'k'd'j'sa'l'k";
            System.out.println((title));
            //输出结果：ff的范德萨分Llfldakf;dsk。fdaslfjdsa;lfkjdsd'j'l'f'k'd'j'sa'l'k

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    static A a2 = new A();
    public static void main(String[] args) {
        A a1 = new A();

        System.out.println(a1.i);
        System.out.println(a2.i);
    }
    public static void main22(String[] arg) {
        /*int src = 22;
        int longCnt = 0;
        int huCnt = 0;
        int heCnt = 0;

        for(int i=-99;i<100;i++){
           int dst = src + i;
           if(dst<0) {
               dst = dst + 100;
           }
           int shiDst = dst%100/10;
           int geDst = dst%10;
           if(shiDst>geDst) {
               longCnt++;
           }else if(shiDst<geDst) {
               huCnt++;
           }else {
               heCnt++;
           }
        }
        System.out.println("龙：" + longCnt +",虎:" + huCnt + ",和：" + heCnt);*/

        String src = "0";
        int oneCnt=0;
        StringBuffer oneSb = new StringBuffer();

        int zCnt=0;
        StringBuffer zSb = new StringBuffer();

        int tCnt=0;
        StringBuffer tSb = new StringBuffer();

        int threeCnt=0;
        StringBuffer threeSb = new StringBuffer();

        int fourCnt=0;
        StringBuffer fourSb = new StringBuffer();

        int fiveCnt=0;
        StringBuffer fiveSb = new StringBuffer();

        for(int i =0;i<1000;i++) {
            String item = "000"+ String.valueOf(i);
            String subStr = item.substring(item.length()-3);
            int cnt = StringUtils.countMatches(subStr, src);
            if(cnt == 0) {
                zCnt++;
                zSb.append(subStr+ " ");
            }else if(cnt == 1) {
                oneCnt++;
                oneSb.append(subStr+ " ");
            }else if(cnt == 2) {
                tCnt++;
                tSb.append(subStr+ " ");
            }else if(cnt == 3) {
                threeCnt++;
                threeSb.append(subStr+ " ");
            }else if(cnt == 4) {
                fourCnt++;
                fourSb.append(subStr+ " ");
            }else if(cnt == 5) {
                fiveCnt++;
                fiveSb.append(subStr+ " ");
            }
        }

        System.out.println("zhong1:"+oneCnt + ",zhong2:" + tCnt + ",zhong3:"+threeCnt + ",zhong0:" + zCnt);
    }
}
