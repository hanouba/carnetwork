package com.carnetwork.pvs;

import com.carnetwork.hansen.mvp.model.http.NetWorkChangReceiver;

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

    /**单词规律
     * 在本题中，我们需要判断字符与字符串之间是否恰好一一对应。即任意一个字符都对应着唯一的字符串，
     * 任意一个字符串也只被唯一的一个字符对应。在集合论中，这种关系被称为「双射」。
     *
     * 想要解决本题，我们可以利用哈希表记录每一个字符对应的字符串，以及每一个字符串对应的字符。
     * 然后我们枚举每一对字符与字符串的配对过程，不断更新哈希表，如果发生了冲突，则说明给定的输入不满足双射关系。
     *
     * 在实际代码中，我们枚举 \textit{pattern}pattern 中的每一个字符，利用双指针来均摊线性地找到该字符在
     * \textit{str}str 中对应的字符串。每次确定一个字符与字符串的组合，我们就检查是否出现冲突，最后我们再检查两字符串是否比较完毕即可。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/word-pattern/solution/dan-ci-gui-lu-by-leetcode-solution-6vqv/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Test
    public void leetcodewordPattern() {
        String parent = "abba";  String str = "dog fad bad dog";
        Map<String, Character> str2ch = new HashMap<String, Character>();
        Map<Character,String> ch2str = new HashMap<Character, String>();
        int m = str.length();
        int i = 0;
        for (int p = 0; p < parent.length(); p++) {
//            得到parent每个字符
            char ch = parent.charAt(p);
            System.out.println("parent的每个字符--"+ch);
            if (i >= m) {
                System.out.println("错误--");
            }
            int j = i;
            System.out.println("索引j--"+j );
            while (j< m && str.charAt(j) != ' ') {
                j ++;
                System.out.println("索引自增j--"+j );
            }
            String tmp = str.substring(i, j);
            System.out.println("str的截取字符tmp--"+tmp);
            System.out.println("parent字符ch--"+ch);
            if (str2ch.containsKey(tmp) && str2ch.get(tmp) != ch) {
//                如果str2ch里面key对应的值不是等于 parent里面的字符 就直接false
                System.out.println("错误22--");
            }
            if (ch2str.containsKey(ch) && !tmp.equals(ch2str.get(ch))) {
                //如果ch2str里面key对应的值不是等于 字符串中的字符里面的字符 就直接false
                System.out.println("错误33--");
            }

            str2ch.put(tmp,ch);
            ch2str.put(ch,tmp);

            i = j + 1;

            System.out.println("j + 1--"+i);
        }


    }

    /**
     * 亲密字符串
     * 给定两个由小写字母构成的字符串 A 和 B ，只要我们可以通过交换 A 中的两个字母得到与 B 相等的结果，就返回 true ；否则返回 false
     *
     * 如果交换 A[i] 和 A[j] 可以证明 A 和 B 是亲密字符串，那么就有 A[i] == B[j] 以及 A[j] == B[i]。
     * 这意味着在 A[i], A[j], B[i], B[j] 这四个自由变量中，只存在两种情况：A[i] == A[j] 或 A[i] != A[j]
     *
     * 在 A[i] == A[j] == B[i] == B[j] 的情况下，字符串 A 与 B 相等。因此，如果 A == B，
     * 我们应当检查每个索引 i 以寻找具有相同值的两个匹配。
     *
     * 在 A[i] == B[j], A[j] == B[i], (A[i] != A[j]) 的情况下，其余索引是相匹配的。
     * 所以如果 A 和 B 只有两个不匹配的索引（记作 i 和 j），我们应该检查并确保等式 A[i] == B[j] 和 A[j] == B[i] 成立。
     *
     * A必须交换俩字母
     *
     */
    @Test
    public void buddyStrings() {
        String A = "abcd";String B = "abcd";
        boolean tet = tet(A, B);
            System.out.println("错误33--"+tet);

    }

    public boolean tet(String A, String  B) {
        if (A.length() != B.length()) return false;
        if (A.equals(B)) {
            int[] count = new int[26];
            for (int i = 0; i < A.length(); ++i)
                count[A.charAt(i) - 'a']++;
            System.out.println("结果 --" );
            for (int c: count) {
                System.out.println("结果 c--"+c );
                if (c > 1)
                    return true;
            }
            return false;
        } else {
            int first = -1, second = -1;
            for (int i = 0; i < A.length(); ++i) {
                if (A.charAt(i) != B.charAt(i)) {
                    if (first == -1)
                        first = i;
                    else if (second == -1)
                        second = i;
                    else
                        return false;
                }
            }

            System.out.println("结果 second--" + second );
            System.out.println("结果 first--" + A.charAt(first) );
            System.out.println("结果 B.charAt(second)--" + B.charAt(second) );
            System.out.println("结果  A.charAt(second) --" +  A.charAt(second)  );
            System.out.println("结果  B.charAt(first) --" +  B.charAt(first)  );
            return (second != -1 && A.charAt(first) == B.charAt(second) &&
                    A.charAt(second) == B.charAt(first));
        }
    }
}