package com.carnetwork.pvs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getValueNum() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "0");

        int keys = -1;
        Map<Integer, String> ma2 = new LinkedHashMap<Integer, String>();
        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object obj = entry.getValue();
            if (obj != null && obj.equals("1")) {
                keys = (int) entry.getKey();
                ma2.put(keys, "a");
            }
        }
        System.out.println(ma2.size());
    }

    @Test
    public void testS() {
        String name = "高新大道口qwe";



        StringBuilder builder = new StringBuilder(name);
        System.out.println(builder.length());

        if (builder.length() > 6) {
          String  newName = builder.substring(0, 6);
            String  newSecondName = builder.substring(6, builder.length());
            System.out.print("--" + newName);
            System.out.print("---" + newSecondName);
        } else {
            String   newName = builder.toString();
            System.out.print(newName);
        }


    }

    @Test
    public void test1() {
        int num = -1;
        int month = 7;
        int year = 2020;
        Calendar calendar = Calendar.getInstance();
        int newMonth =month ;
        if (num < 0) {
            newMonth = month - 1;
        } else {
            newMonth = month + 1;
        }

        if (newMonth == 0) {
            newMonth = 12;
            year--;
        } else if (newMonth == 13) {
            newMonth = 1;
            year++;
        }
       System.out.println("新的年月日"+year + "newMonth" + newMonth);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 05);
        System.out.println("新的年月日"+ "calendar" + calendar.getTime());



    }

    @Test
    public void test2() {
            //把字符串转成字符数组
            char[] strChar="501".toCharArray();
            String result="";
            for(int i=0;i<strChar.length;i++){
                //toBinaryString(int i)返回变量的二进制表示的字符串
                //toHexString(int i) 八进制
                //toOctalString(int i) 十六进制
                result +=Integer.toHexString(strChar[i]);
            }

        System.out.print("二进制结果" + result);

    }
    @Test
    public void testTime() {
        List<String> list = new ArrayList<>();
        //存储所有数据 id
        for (int i = 0; i < 10; i++) {
            list.add("adc"+i);
            System.out.println("list集合数据" + list.get(i));
        }
        //2 删除某条数据
        //根据值来删除

        System.out.println("移除adc2" + list.remove("adc2"));
        for (int i = 0; i < list.size(); i++) {

            System.out.println("list集合数据" + list.get(i));
        }
        //通过索引删除某条数据 索引从0开始计算的
        System.out.println("移除索引为1的数据" + list.remove(1));
        for (int i = 0; i < list.size(); i++) {

            System.out.println("list集合数据" + list.get(i));
        }

        for (int i = 0; i < 3; i++) {
            list.add("加载更多"+i);
            System.out.println("新增数据" + list.get(i));
        }

        for (int i = 0; i < list.size(); i++) {

            System.out.println("新增Hou数据" + list.get(i));
        }
    }


    @Test
    public void testJSON() {
        String selceted = "";

        for (int m = 0; m < 3; m++) {
                selceted += m+",";
        }
        System.out.println("selceted" + selceted);

        String[] split = selceted.split("\\,");
        for (int i = 0; i < split.length; i++) {
            System.out.println("selceted_split" + split[i]);

        }
    }

    @Test
    public void testlat() {
        String dms = "31°11′16.2";

            String[] lntArr = dms
                    .trim()
                    .replace("°", ";")
                    .replace("′", ";")
                    .replace("'", ";")
                    .replace("\"", "")
                    .split(";");
            Double result = 0D;
            for (int i = lntArr.length; i >0 ; i--) {
                double v = Double.parseDouble(lntArr[i-1]);
                if(i==1){
                    result=v+result;
                }else{
                    result=(result+v)/60;
                }
            }
            System.out.println("selceted_split" + result);
    }
    @Test
    public void testString() {

    }



}