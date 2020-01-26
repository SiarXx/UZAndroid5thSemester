package com.example.uzapp.fragments


import android.Manifest
import android.hardware.Camera
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uzapp.R
import com.example.uzapp.tools.CameraPreview
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_picture.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PictureFragment : Fragment(),View.OnClickListener {
    lateinit var timerArea: TextView
    lateinit var mCamera: Camera
    lateinit var mCameraPreview: CameraPreview
    lateinit var preview: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_picture, container, false)
        preview = view.cameraPreview
        timerArea = view.timerArea
        val backBtn = view.backPictureBtn
        backBtn.setOnClickListener(this)
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        mCamera = getCameraInstance()!!
                        mCameraPreview = CameraPreview(context!!, mCamera)

                        preview.addView(mCameraPreview)

                        mCamera.startPreview()

                        startPhoto()
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // permission is denied permenantly, navigate user to app settings
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()

        return view
    }
    private fun getCameraInstance(): Camera? {
        var camera: Camera? = null
        try {
            camera = Camera.open()
        } catch (e: Exception) {
            // cannot get camera or does not exist
        }

        return camera
    }
    private fun startPhoto(){
        object : CountDownTimer(5000, 1000) {

            override fun onFinish() {
                timerArea.text = "Picture Taken!"
                mCamera.takePicture(null, null, mPicture)
            }

            override fun onTick(millisUntilFinished: Long) {
                timerArea.text = "Photo will be taken in " + millisUntilFinished/1000
            }

        }.start()
    }

    var mPicture: Camera.PictureCallback = Camera.PictureCallback { data, camera ->
        val pictureFile = getOutputMediaFile() ?: return@PictureCallback
        try {
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
        } catch (e: FileNotFoundException) {

        } catch (e: IOException) {
        }
    }

    private fun getOutputMediaFile():File? {
        val mediaStorageDir = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyCameraApp")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory")
                return null
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(Date())
        val mediaFile:File
        mediaFile = File(
            mediaStorageDir.path + File.separator
                    + "IMG_" + timeStamp + ".jpg"
        )

        return mediaFile
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.backPictureBtn -> Navigation.findNavController(v).popBackStack()
        }
    }

}
