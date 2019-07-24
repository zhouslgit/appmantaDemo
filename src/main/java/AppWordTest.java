import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.nio.Buffer;

/**
 *
 * 改造appmanta
 * @author simon
 * 2019/7/22 14:20
 */
public class AppWordTest {




    /**
     * 应用下词 搜索
     *
     * @param Id
     * @throws IOException
     */
    public static String appWordGet(int Id) throws IOException {
        String result = null;
        Long start = System.currentTimeMillis();
        //System.out.println(start); String appId="appId:6099";
        //String appId="appId:3100";
        String appId = "appId:" + Id;
        File file = new File("G:\\appWord\\2.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader, 5 * 1024 * 1024);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            String[] data = str.split("\t");
            if (appId.equals(data[0])) {
                //System.out.println("data:"+str);
                System.out.println("word:" + data[1]);
                result = data[1];
                break;
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("搜索耗时：" + (end - start));

        bufferedReader.close();
        fileReader.close();
        return result;
    }


    /**
     * 应用下词 修改
     *
     * @param id
     */
    public static void appWordChange(int id) throws IOException {

        Long start = System.currentTimeMillis();
        String appId = "appId:" + id;
        File file = new File("G:\\appWord\\2.txt");//查询文件

        File newFile = new File("G:\\appWord\\temp.txt");//替换文件
        if (!newFile.exists()) {
            newFile.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile, true));

        BufferedReader bufferedReader = new BufferedReader( new FileReader(file), 5 * 1024 * 1024);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            String[] data = str.split("\t");
            if (appId.equals(data[0])) {
                System.out.println("word:" + data[1]);

                JSONArray jsonArray=JSONArray.parseArray(data[1]);
                jsonArray.add("jklagjlakjl");
                jsonArray.add("jklhh哈哈哈");
                jsonArray.add("ajkl'gajkljklj;");
               // String data="appId:"+appId+"\t"+jsonArray.toJSONString()+"\t\n";
                str=appId+"\t"+new String(jsonArray.toJSONString())+"\n";
            }

            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();


        String filePath=file.getPath();
        file.delete();
        newFile.renameTo(new File(filePath));


        Long end = System.currentTimeMillis();
        System.out.println("追加耗时：" + (end - start));
    }
    /**
     * 应用下词 修改 randomaccessfile
     *
     * @param id
     */
    public static void appWordChangeByRandom(int id) throws IOException {

        Long start = System.currentTimeMillis();
        String appId = "appId:" + id;
        File file = new File("G:\\appWord\\2.txt");//查询文件

//        File newFile = new File("G:\\appWord\\temp.txt");//替换文件
//        if (!newFile.exists()) {
//            newFile.createNewFile();
//        }
       // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile, true));
        BufferedReader bufferedReader = new BufferedReader( new FileReader(file), 5 * 1024 * 1024);
        String str = null;

        int position=0;//追加位置
        //int cover=0;
        int add=0;
        boolean flag=false;
        String content=null;
       // String end="";
        File tempFile=File.createTempFile("temp",null);
        tempFile.deleteOnExit();
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(tempFile));//写临时文件
        while ((str = bufferedReader.readLine()) != null) {
            if(flag&&null!=str){//追加内容
                //end+=str+"\n";
                bufferedWriter.write(str+"\n");
                bufferedWriter.flush();
            }
            String append=str;
            String[] data = str.split("\t");

            if (appId.equals(data[0])) {
                JSONArray jsonArray=JSONArray.parseArray(data[1]);
                jsonArray.add("lkagj");
                if(add!=0){
                content="\n"+appId+"\t"+(jsonArray.toJSONString())+"\n";
                }else {
                    content=appId+"\t"+(jsonArray.toJSONString())+"\n";
                }

                flag=true;
            }else{
                if(!flag){
                position+=append.length();//确定位置
                     add++;
                }
            }
        }

        int addPY=0;
        if((add-1>0)){
            addPY=add-1;
        }

        if(flag){//存在可以追加的应用
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            randomAccessFile.seek(position+addPY);//确定插入位置
            randomAccessFile.write(content.getBytes());

            //randomAccessFile.write(end.getBytes());

            BufferedReader br=new BufferedReader(new FileReader(tempFile));

            String fugai=null;
            while (null!=(fugai=br.readLine())){

                randomAccessFile.write((fugai+"\n").getBytes());
            }
            br.close();
            randomAccessFile.close();
        }else {//新建一个appID

         BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
         JSONArray jsonArray=new JSONArray();
         jsonArray.add("asdfasdfa");
         String newAppIdWord=appId+"\t"+jsonArray.toJSONString();
         bw.write(newAppIdWord);
         bw.newLine();
         bw.close();



        }


        bufferedReader.close();
        bufferedWriter.close();


        Long endT = System.currentTimeMillis();
        System.out.println("追加耗时：" + (endT - start));
    }


    public static void main(String[] args) throws IOException {

        //appWordGet(6099);
        //appWordChange(6099);
        //appWordGet(6099);


        appWordChangeByRandom(690);
    }

}
