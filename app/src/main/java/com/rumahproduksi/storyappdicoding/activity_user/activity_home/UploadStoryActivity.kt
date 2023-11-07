package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiClient
import com.rumahproduksi.storyappdicoding.activity_user.model_user.StoryUploadModel
import com.rumahproduksi.storyappdicoding.activity_utils.database.MessagesUtils
import com.rumahproduksi.storyappdicoding.activity_utils.model_view.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import com.rumahproduksi.storyappdicoding.activity_utils.network.createCustomTempFile
import com.rumahproduksi.storyappdicoding.activity_utils.network.reduceFileImage
import com.rumahproduksi.storyappdicoding.activity_utils.network.uriToFile
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivityUploadStoryBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class UploadStoryActivity : AppCompatActivity() {

    private val binding : ActivityUploadStoryBinding by lazy {
        ActivityUploadStoryBinding.inflate(layoutInflater)
    }

    private var getFile: File? = null
    private var uploadJobs: Job = Job()
    private lateinit var uploadModel: StoryUploadModel
    private lateinit var prefersManager: PreferManager

    private lateinit var currentPhotoPath: String

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                MessagesUtils.setMessage(this, getString(R.string.not_permission))
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // isi konten
        prefersManager = PreferManager(this)

        val classRepository = RepositoryClass(ApiClient.getInstance())
        uploadModel = ViewModelProvider(
            this,
            FactoryViewModel(classRepository)
        )[StoryUploadModel::class.java]

        permissionGranted()
        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnUpload.setOnClickListener {
            if(getFile != null || !TextUtils.isEmpty(binding.edtDescription.text.toString())) {
                uploadStory(prefersManager.token)
            } else {
                MessagesUtils.setMessage(this, getString(R.string.warning_upload))
            }
        }

    }

    @Suppress("DEPRECATION")
    private fun uploadStory(auth: String) {
        setLoadingState(true)
        val file = reduceFileImage(getFile as File)
        val description = binding.edtDescription.text.toString().trim()
        lifecycle.coroutineScope.launchWhenResumed {
            if (uploadJobs.isActive) uploadJobs.cancel()
            uploadJobs = launch {
                uploadModel.uploadStory(auth, description, file).collect { result ->
                    when (result) {
                        is NetworkResults.Success -> {
                            setLoadingState(false)
                            MessagesUtils.setMessage(this@UploadStoryActivity, getString(R.string.success_upload_story))
                            startActivity(Intent(this@UploadStoryActivity, MainActivity::class.java))
                            finish()
                        }
                        is NetworkResults.Loading -> {
                            setLoadingState(true)
                        }
                        is NetworkResults.Error -> {
                            setLoadingState(false)
                            MessagesUtils.setMessage(this@UploadStoryActivity, getString(R.string.error_upload_story))
                        }
                    }
                }
            }
        }
    }

    private fun setLoadingState(loading: Boolean) {
        when (loading) {
            true -> {
                binding.btnUpload.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                binding.btnUpload.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun permissionGranted() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadStoryActivity,
                "com.rumahproduksi.storyappdicoding",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgPreview.setImageBitmap(result)
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@UploadStoryActivity)
            getFile = myFile

            binding.imgPreview.setImageURI(selectedImg)
        }
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}