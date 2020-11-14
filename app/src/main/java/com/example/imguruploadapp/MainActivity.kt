package com.example.imguruploadapp

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    val REQUEST_TAKE_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.uploadButton)
        val icon = findViewById<ImageView>(R.id.photo)

        val file = File(applicationContext.cacheDir, "ankylo.jpg")
        val image = UploadPojo("Ankylo", "The dinosaur", "Sharik", file)

        button.setOnClickListener { UploadService().uploadImage(image) }
        icon.setOnClickListener { selectImage() }
    }

    private fun selectImage() {
        val options = arrayOf("Take photo", "Cancel")

        val builder = AlertDialog.Builder(this)
            .setTitle("Choose your photo")
            .setItems(options) { dialogInterface: DialogInterface, which: Int ->
                run {
                    when (options[which]) {
                        "Take photo" -> {
                            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(takePicture, REQUEST_TAKE_PHOTO)
                        }
                        else -> {
                            dialogInterface.dismiss()
                        }
                    }
                }
            }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_CANCELED) {
            when(requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    if (resultCode == Activity.RESULT_OK) {
                        val selectedImage = data?.extras?.get("data") as Bitmap
                        val file = convertBitmapToFile(selectedImage)
                        if(file != null) {
                            val image = UploadPojo("Photo From Camera", "Some description", "Sample", file)
                            UploadService().uploadImage(image)
                        }

                    }
                }
            }
        }
    }

    private fun convertBitmapToFile(bitmap: Bitmap):File? {
            return try {
                val file = File(applicationContext.cacheDir, "filename")
                file.createNewFile()

                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos)
                val bitmapData = bos.toByteArray()

                val fos = FileOutputStream(file)
                fos.write(bitmapData)
                fos.flush()
                fos.close()
                file
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

    }
}
