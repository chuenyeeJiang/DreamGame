package com.chuenyee.tool;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

public class MyJsonHandle {
    static Logger logger = LoggerFactory.getLogger(MyJsonHandle.class);


    /*public static void  debug(JSONObject jsonObject) {
        logger.debug("***************************************************");
        logger.debug("                  My Json Debug                    ");
        logger.debug("***************************************************");

        //LinkedList<String> result = new LinkedList<String>();

        json_stack.push(jsonObject);
        node_key_stack.push("root");

        while (!json_stack.empty()) {
            node_temp_root = json_stack.pop();
            temp_key = node_key_stack.pop();

            if(node_temp_root!=null&&!node_temp_root.isEmpty())
            if(node_temp_root.keySet().contains(temp_key)) {
                leve+="-";
            }

            logger.debug(leve+temp_key);
            *//*      result.add(node_key_stack.pop());*//*
            if (node_temp_root != null)
                if (!node_temp_root.isEmpty()) {
                    Iterator<String> iterator =
                            node_temp_root.
                                    keySet().
                                    iterator();

                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        result.add(leve + key);
                        node_key_stack.push(key);
                        JSONObject node = null;
                        try {
                            node = (JSONObject) node_temp_root.get(key);

                        } catch (ClassCastException e) {
                        }
                        json_stack.push(node);
                    }
                }

        }
      *//*  for(String curStr : result)
        logger.debug(curStr);
        }*//*
    }*/

    public static void debug(JSONObject jsonObject,Boolean leveNum) {
        logger.debug("***************************************************");
        logger.debug("                  My Json Debug                    ");
        logger.debug("***************************************************");

       // dp_look(jsonObject,"-");
        if(leveNum) {
            dp_look(jsonObject, 1);
        }else {
            dp_look(jsonObject,"-");
        }
    }

    public static  Object  getResult(JSONObject jsonObject,String path){
        int index = -1;
        int next_index= path.length();
        Object cur_value = jsonObject;
        String key = null;

        /*
           循环体
         */
        while (path.indexOf('.',(index+1))!=-1) {

            //获取下一帧
            next_index=path.indexOf(".",index+1);

            //截取key值
            key = path.substring((index+1),next_index);

            //获取当前对象
            cur_value = ((JSONObject)cur_value).get(key);

            //指针跳转
            index=next_index;
        }

        /*
            出口
         */
        //获取最终值
        key = path.substring((next_index+1),path.length());
        cur_value = ((JSONObject)cur_value).get(key);

        return cur_value;

    }

    private static void  dp_querry(){


    }



    private static void dp_look(JSONObject jsonObject,String leve) {
        leve+=leve.charAt(0);
        if (!jsonObject.isEmpty() && jsonObject != null) {
            Iterator<String> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                logger.debug(leve+next);
                try {
                    dp_look((JSONObject) jsonObject.get(next),leve);
                }catch(java.lang.ClassCastException e){
                }
            }
        }
    }
    private static void dp_look(JSONObject jsonObject,int leve) {
        leve++;
        if (!jsonObject.isEmpty() && jsonObject != null) {
            Iterator<String> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                logger.debug(leve+next);
                try {
                    dp_look((JSONObject) jsonObject.get(next),leve);
                }catch(java.lang.ClassCastException e){
                }
            }
        }
    }
}




