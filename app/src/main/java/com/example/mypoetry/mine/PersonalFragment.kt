package com.example.mypoetry.mine

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.Glide
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.ButtomDialog
import com.example.mypoetry.Model.Converter
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.application.BaseApplication
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.*

class PersonalFragment: Fragment() ,View.OnClickListener{

    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    lateinit var dialog: ButtomDialog
    lateinit var converter: Converter
    var bitmap: Bitmap? = null
    lateinit var sp: SharedPreferences

    lateinit var mineViewModel: MineViewModel
    lateinit var user: User
    lateinit var mainActivity: MainActivity
    lateinit var personHead: LinearLayout
    lateinit var name: EditText
    lateinit var introduce: EditText
    lateinit var place: EditText
    lateinit var rg: RadioGroup
    lateinit var show: TextView
    lateinit var head: CircleImageView
    lateinit var save: Button
    lateinit var man: RadioButton
    lateinit var woman: RadioButton
    lateinit var tele: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personal,container,false)

        personHead = view.findViewById(R.id.person_head)
        name = view.findViewById(R.id.name)
        introduce = view.findViewById(R.id.introduce)
        place = view.findViewById(R.id.place)
        rg = view.findViewById(R.id.rg)
        show = view.findViewById(R.id.show)
        head = view.findViewById(R.id.head)
        save = view.findViewById(R.id.save)
        man = view.findViewById(R.id.male)
        woman = view.findViewById(R.id.femle)
        tele = view.findViewById(R.id.per_tele)
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        mainActivity = activity as MainActivity
        converter = Converter()
        sp = BaseApplication.context.getSharedPreferences("data",Context.MODE_PRIVATE)
        val str = sp.getString("telephone","为定义")
        tele.setText(str)

        personHead.setOnClickListener(this)
        save.setOnClickListener(this)

        user = mainActivity.userData.value!!
        if(user.getName() != null) {
            name.setText(user.getName())
        }
        if(user.getgender() != null){
            if(user.getgender() == "男"){
                man.isChecked = true
                woman.isChecked = false
            }else{
                woman.isChecked = true
                man.isChecked = false
            }
        }

        /*if(user.getheadImage() != null){
            Log.d("Tag","YOUYOU")
            val url = user.getheadImage()!!.url
            Glide.with(BaseApplication.context).load("https://v0.api.upyun.com/bmob-cdn-30008/2021/08/08/64b519b16c814c2783fe7b6d9a5ceb01").into(head)
        }*/
       if(sp.getString(user.getTele()," ") != " "){
            Log.d("TagTag",sp.getString(user.getTele()," ")!!)
            bitmap = converter.stringToBitmap(sp.getString(user.getTele()," ")!!)
           head.setImageBitmap(bitmap)
        }

        introduce.setText(user.getintroduce())
        place.setText(user.getplace())


        return view
    }

    fun choosePhotos(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent,fromAlbum)
    }

    fun takePhotos(){
        outputImage = File(BaseApplication.context.externalCacheDir,"output_image.jpg")
        if(outputImage.exists()){
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(BaseApplication.context,"com.example.mypoetry.fileprovider",outputImage)
        }else{
            Uri.fromFile(outputImage)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent,takePhoto)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            takePhoto -> {
                if(resultCode == Activity.RESULT_OK){
                    bitmap = BitmapFactory.decodeStream(BaseApplication.context.contentResolver.openInputStream(imageUri))
                    head.setImageBitmap(rorateIfRequired(bitmap!!))
                }
            }

            fromAlbum -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    data.data?.let {
                        bitmap = getBitmapFromUri(it)!!
                        head.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = BaseApplication.context.contentResolver
        .openFileDescriptor(uri,"r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    private fun rorateIfRequired(bitmap: Bitmap): Bitmap{
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)
        return when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> rorateBitmap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rorateBitmap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rorateBitmap(bitmap,270)
            else -> bitmap
        }
    }

    private fun rorateBitmap(bitmap: Bitmap,degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        bitmap.recycle()
        return rotatedBitmap
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.person_head -> {
                val builder: ButtomDialog.Builder = ButtomDialog.Builder(context)
                //添加条目，可多个
                //添加条目，可多个
                builder.addMenu("相机", View.OnClickListener {
                    dialog.cancel()
                    takePhotos()
                    /*Intent intent = new Intent(getActivity(),TakePhotoActivity.class);
                                    intent.putExtra("choose",1);
                                    startActivity(intent);*/
                    //Toast.makeText(getContext(), "相机", Toast.LENGTH_SHORT).show();
                }).addMenu("相册", View.OnClickListener {
                    dialog.cancel()
                    choosePhotos()
                    /*Intent intent = new Intent(getActivity(),TakePhotoActivity.class);
                                    intent.putExtra("choose",2);
                                    startActivity(intent);*/
                    //Toast.makeText(getContext(), "相册", Toast.LENGTH_SHORT).show();
                })
                //下面这些设置都可不写
                //下面这些设置都可不写
                builder.setTitle("请选择方式") //添加标题

                builder.setCanCancel(true) //点击阴影时是否取消dialog，true为取消

                builder.setShadow(true) //是否设置阴影背景，true为有阴影

                builder.setCancelText("取消") //设置最下面取消的文本内容

                //设置点击取消时的事件
                //设置点击取消时的事件
                builder.setCancelListener(View.OnClickListener {
                    dialog.cancel()
                    Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show()
                })
                dialog = builder.create()
                dialog.show()
                //choosePhotos()
                //takePhotos()
            }
            R.id.save -> {
                user.setName(name.text.toString())
                user.setgender(if(man.isChecked) "男" else "女")
                user.setintroduce(introduce.text.toString())
                user.setplace(place.text.toString())
                if(bitmap != null){
                    Log.d("Tag","转换为${converter.bitmapToString(bitmap!!)}")
                    sp.edit().putString(user.getTele(),converter.bitmapToString(bitmap!!)).apply()
                }
                //user.setHead(converter.bitmapToString(bitmap))
                //user.setheadImage(BmobFile(converter.bitmapToFile(bitmap)) )
//                val bmobFile = BmobFile(converter.bitmapToFile(bitmap))
//                bmobFile.upload(object : UploadFileListener(){
//                    override fun done(e: BmobException?) {
//                        if(e == null){
//                            user.setheadImage(bmobFile)
//                            mineViewModel.update(user)
//                            Log.d("Tag","图片上传成功!")
//                        }else{
//                            Log.d("Tag","$e...图片上传失败!")
//                        }
//                    }
//
//                })
                mineViewModel.update(user)
                Toast.makeText(BaseApplication.context, "保存成功", Toast.LENGTH_SHORT).show();
            }
        }
    }


}