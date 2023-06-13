package com.c23ps422.reclothes

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

//    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
//
//    private lateinit var photoUri: Uri
//    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            Log.i("ReqGrant", "Permission granted")
//            shouldShowCamera.value = true
//        } else {
//            Log.i("ReqGrant", "Permission denied")
//        }
//    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("ReqGrant", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.i("ReqGrant", "Show camera permissions dialog")

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            ReClothesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReClothesApp(
                        outputDirectory = outputDirectory,
                        cameraExecutor = cameraExecutor
                    )
//                    if (shouldShowCamera.value) {
//                        CameraView(
//                            outputDirectory = outputDirectory,
//                            executor = cameraExecutor,
//                            onImageCaptured = ::handleImageCapture,
//                            onError = { Log.e("err", "View error: ", it) }
//                        )
//                    }
//
//                    if (shouldShowPhoto.value) {
//                        Image(
//                            painter = rememberImagePainter(photoUri),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
                }
            }
            requestCameraPermission()
        }
    }

//    private fun handleImageCapture(uri: Uri) {
//        shouldShowCamera.value = false
//
//        photoUri = uri
//        shouldShowPhoto.value = true
//
//        val photoFile = File(uri.path)
//        Log.d("pfile", photoFile.toString())
//        val base64String = convertFileToBase64(photoFile)
//        Log.d("Base", base64String)
//    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

//    @Throws(IOException::class)
//    private fun convertFileToBase64(file: File): String {
//        val inputStream = FileInputStream(file)
//        val bytes = ByteArray(file.length().toInt())
//        inputStream.read(bytes)
//        inputStream.close()
//        return Base64.encodeToString(bytes, Base64.DEFAULT)
//    }

    companion object{
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }
}
