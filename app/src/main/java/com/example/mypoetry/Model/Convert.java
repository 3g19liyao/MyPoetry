package com.example.mypoetry.Model;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Convert {

//    public File saveFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
//        File dir=new File(context.getExternalFilesDir(null).getPath()+"Apk");
//        if (!dir.exists()){
//            dir.mkdir();
//        }
//        //创建文件
//        File file = new File(dir+"/"+"headImage");
//        if (!file.exists()){
//            file.createNewFile();
//        }
//
//        FileOutputStream fos = new FileOutputStream(file);
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//        bos.flush();
//        bos.close();
//        return myCaptureFile;
//
//    }


}
