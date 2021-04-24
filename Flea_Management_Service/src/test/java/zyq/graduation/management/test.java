package zyq.graduation.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 周杨清
 * @date 2021/4/13 11:39
 */
public class test {

    static HashMap<Integer,String> numMap=new HashMap<>();
    static HashMap<Integer,String> num2Map=new HashMap<>();
    static{
        numMap.put(1,"十");
        numMap.put(2,"百");
        numMap.put(3,"千");
        numMap.put(4,"万");
        numMap.put(5,"十万");
        numMap.put(6,"百万");
        numMap.put(7,"千万");
        numMap.put(8,"亿");
        numMap.put(9,"十亿");
        numMap.put(10,"百亿");
        numMap.put(11,"千亿");
        numMap.put(12,"兆");
        numMap.put(13,"十兆");
        numMap.put(14,"百兆");

        num2Map.put(1,"一");
        num2Map.put(2,"二");
        num2Map.put(3,"三");
        num2Map.put(4,"四");
        num2Map.put(5,"五");
        num2Map.put(6,"六");
        num2Map.put(7,"七");
        num2Map.put(8,"八");
        num2Map.put(9,"九");
    }

    public static StringBuilder numTranslate(String num){
        Integer nowNum=0;
        StringBuilder result= new StringBuilder();
        StringBuffer number = new StringBuffer(num).reverse();
        num=number.toString();
        String[] split = num.split("");
        List<String> strings = Arrays.asList(split);
        if (split[split.length-1].equals("-")){
            result.append("负");
//            strings.set(split.length-1,"t");
//            strings.remove();
        }
        ArrayList<Integer> numList = new ArrayList<>();
        for (String string : strings) {
            if (string.equals("-")){

            }else
                numList.add(Integer.parseInt(string));
        }

        for (int i=numList.size()-1;i>=0;i--){
            nowNum=numList.get(i);
            if (nowNum!=0){
                if (i%4==1&i==numList.size()-1){
                    result.append(numMap.get(i)==null?"":numMap.get(i));
                }else
                    result.append(num2Map.get(nowNum)).append(numMap.get(i)==null?"":numMap.get(i));
            }else {
                result.append("0");
            }
        }
        while(result.indexOf("万")!=result.lastIndexOf("万")){
            result.deleteCharAt(result.indexOf("万"));
        }
        while(result.indexOf("亿")!=result.lastIndexOf("亿")){
            result.deleteCharAt(result.indexOf("亿"));
        }
        while(result.indexOf("兆")!=result.lastIndexOf("兆")){
            result.deleteCharAt(result.indexOf("兆"));
        }
        for (int i=0;i<result.length();i++){
            if (i<result.length()-1){
                if(result.charAt(i)=='0'&result.charAt(i+1)!='0'){
                    result.replace(i,i+1,"零");
                }
            }

        }
        Boolean judge=true;
        while(judge){
            if (result.indexOf("0")==-1){
                judge=false;
            }else
                result.deleteCharAt(result.indexOf("0"));

        }
        for (int i=0;i<result.length();i++){
            if (result.charAt(i)=='0'){
                result.deleteCharAt(i);
            }
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(numTranslate("100000"));;
    }

}
