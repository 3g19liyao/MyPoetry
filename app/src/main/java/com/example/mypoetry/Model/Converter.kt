package com.example.mypoetry.Model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.decode
import android.util.Log
import com.example.mypoetry.application.BaseApplication
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Converter {

    fun bitmapToFile(bitmap: Bitmap): File{
        //将Bitmap类型的图片转化成file类型，便于上传到服务器
        //将Bitmap类型的图片转化成file类型，便于上传到服务器
        val dir =
            File(BaseApplication.context.getExternalFilesDir(null)?.getPath().toString() + "Apk")
        if (!dir.exists()) {
            dir.mkdir()
        }
        //创建文件
        //创建文件
        val file = File("$dir/apk")
        if (!file.exists()) {
            file.createNewFile()
        }
        val bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
        bos.flush()
        bos.close()
        return file
    }

    fun stringToBitmap(string: String) : Bitmap{
        Log.d("Tag",string)
        var bitmap: Bitmap? = null
        try {
            val bitmapArray: ByteArray
            bitmapArray = decode(string, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(
                bitmapArray, 0,
                bitmapArray.size
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap!!

    }
    fun bitmapToString(bitmap: Bitmap): String{
        //将Bitmap转换成字符串
        var string: String? = null
        val bStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bStream)
        val bytes = bStream.toByteArray()
        string = Base64.encodeToString(bytes, Base64.DEFAULT)
        return string
    }
}