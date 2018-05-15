package com.ds.zxm.util;

import java.util.ArrayList;
import java.util.List;


public class MathsUtil {

    public static void main(String[] args) {
        /*List<String> data = new ArrayList<String>();
        for(int i=0; i <10;i++) {
            for(int j=0; j < 10; j++) {
                data.add(i + "" + j);
            }
        }
        MathsUtil t = new MathsUtil();
        t.combinerSelect(data, new ArrayList<String>(), data.size(), 20);*/

        List<String> data = new ArrayList<String>();
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        MathsUtil t = new MathsUtil();

            t.combinerSelect(data, new ArrayList<String>(), data.size(),2);
    }

    /**
     * 步骤：：每次递归时，把原始数据和满足条件的工作空间复制一份，所有的操作均在复制文件中进行，目的就是保证不破坏原始数据，
     *      从而可以让一轮递归结束后可以正常进行下一轮。
     *      其次，把数据的第一个元素添加到工作空间中，判断工作空间的大小，如果小于k,则需要继续递归，但此时，传入递归函数的
     *      参数需要注意：假设当前插入的节点的下标是i,因为是顺序插入的,所以i之前的所有数据都应该舍去，只传入i之后的未使用过的数据。
     *      因此在传参之前，应该对copydata作以处理；当大于k的时候，则表明已经找到满足条件的第一种情况，然后只需修改该情况的最后一个结果即可。
     *      如：找到abc时，则只需替换c为d即可完成该轮递归。
     *
     * @param data  原始数据
     * @param workSpace 自定义一个临时空间，用来存储每次符合条件的值
     * @param k     C(n,k)中的k
     */
    public <E> void combinerSelect(List<E> data, List<E> workSpace, int n, int k) {
        List<E> copyData;
        List<E> copyWorkSpace;

        if(workSpace.size() == k) {
            for(Object c : workSpace)
                System.out.print(c+",");
            System.out.println();
        }

        for(int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<E>(data);
            copyWorkSpace = new ArrayList<E>(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for(int j = i; j >=  0; j--)
                copyData.remove(j);
            combinerSelect(copyData, copyWorkSpace, n, k);
        }

    }
}

