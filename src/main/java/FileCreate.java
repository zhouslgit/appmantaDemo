import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author simon
 * 2019/7/22 14:19
 */
public class FileCreate {




    //生成测试数据文件
    public static void main(String[] args) throws IOException {
        int appId=100;
        for (int i = 1; i <=2 ; i++) {
            String name="G:\\appWord\\"+i+".txt";//2000文件 每个文件3000行信息
            File file=new File(name);
            FileWriter fileWriter=new FileWriter(file);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            System.out.println("创建文件"+i);
            for (int j = 1; j <=300; j++) {
                //每行1w+ word
//                JSONArray jsonArray=new JSONArray();
//                for (int k = 1; k <=10000; k++) {
//                    JSONObject jsonObject=new JSONObject();
//                    jsonObject.put("word",k);
//                    jsonObject.put("value",k+"=count+hot");
//                    jsonArray.add(jsonObject);
//                }
              JSONArray jsonArray=new JSONArray();
                for (int k =0; k <10 ; k++) {
                    jsonArray.add(appId+"keyword");
                }

                String data="appId:"+appId+"\t"+jsonArray.toJSONString()+"\n";
                 bufferedWriter.write(data);//写一行数据
                appId++;
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }
}
