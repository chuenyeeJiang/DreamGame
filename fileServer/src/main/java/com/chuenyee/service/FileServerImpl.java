package com.chuenyee.service;

import com.chuenyee.service.api.FileServer;
import com.chuenyee.until.ConfigUtil;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

public class FileServerImpl implements FileServer {

    Logger logger = LoggerFactory.getLogger(FileServerImpl.class);

    static String  osName = null;
    static String  saveDir = null;
    static Calendar cal = Calendar.getInstance();
    static final int  YEAR = cal.get(Calendar.YEAR);   //年
    static final int  MONTH = cal.get(Calendar.MONTH); //月
    static final int  DATE = cal.get(Calendar.DATE);   //份
    static{
        /*
          根据操作系统 初始化存储路径
         */
        Properties properties = System.getProperties();
        osName= properties.getProperty("os.name");
        if(saveDir == null)
        if(osName!=null) {
            if (osName.contains("windows") || osName.contains("Windows")) {
                saveDir = "c://tempUpload";
            } else if (osName.contains("Linux") || osName.contains("linux")) {
                saveDir = "/usr/local/tempUpload";
            } else {
                throw new RuntimeException("该应用程序未能兼容此设备操作系统");
            }
        }else{
            throw new RuntimeException("获取操作系统信息错误！");
        }

          /*
          配置文件读取 存储路径
         */
        saveDir =  ConfigUtil.getProperty("file.saveDir");

    }
    public void setSave_path(String path){
        saveDir = path;
    }

    @Override
    public String upload(String string_file,String format) {
        UUID uuid = UUID.randomUUID();
        File target_file =null;
        OutputStream outputStream= null;
        try {
         /*
         创建临时文件
         */
           target_file = createFile(uuid,format);

          /*
          base64解码
          */
            byte[] decode = FileServerImpl.decode(string_file);

        /*
         写出文件
         */
         outputStream = new FileOutputStream(target_file);
        outputStream.write(decode);

        }catch (Exception e){
            return null;
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug("保存位置:"+target_file.getAbsolutePath());
        return YEAR+"//"+uuid+"."+format;
    }

    public String upload(MultipartFile file, String format) {

        UUID uuid = UUID.randomUUID();

        createFile(uuid,format);
        logger.info(saveDir+"//"+YEAR+"//"+uuid+"."+format);
        try {
            // 输出文件
            File outfile = new File(saveDir+"//"+YEAR+"//"+uuid+"."+format);
            file.transferTo(outfile);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return YEAR+"//"+uuid+"."+format;
    }

    public BufferedImage getIMG(String fileId){
        ImageIcon imgeIcon = new ImageIcon(saveDir+"//"+fileId);

        BufferedImage img = new BufferedImage(imgeIcon.getIconWidth(),
                imgeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g2 = img.getGraphics();
        g2.drawImage(imgeIcon.getImage(), 0, 0, null);;

       return img;
    }

    //创建临时文件
    private File createFile(UUID uuid,String format){
        File target_file = new File(saveDir+"//"+YEAR+"//"+uuid+"."+format);

        //创建目录
        File parentFile = target_file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //创建文件
        if (!target_file.exists()) {
            try {
                target_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         授权(待)
         */
        target_file.setWritable(true);
        target_file.setReadable(true);
        return  target_file;
    }

    //base64解码
    public static byte[] decode(String data){
        byte[] bytes = Base64.decode(data);
        for(int i=0;i<bytes.length;i++){
           if( bytes[i] <0)
           {
               bytes[i]+=256;
           }
        }

        return bytes;
    }

/*    //图片截取
    public byte[] Intercept(){
        BufferedImage


    }*/



}
