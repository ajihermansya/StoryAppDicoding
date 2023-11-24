package com.rumahproduksi.storyappdicoding.activity_user.activity_home.story
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.rumahproduksi.storyappdicoding.activity_model.ViewModelUploadStory
import com.rumahproduksi.storyappdicoding.activity_preferences.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.Results
import com.rumahproduksi.storyappdicoding.activity_remote.response.FileImages
import com.rumahproduksi.storyappdicoding.activity_remote.response.uriToFile
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.MainActivity
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.story.CameraActivity.Companion.CAMERAX_RESULT
import com.rumahproduksi.storyappdicoding.databinding.ActivityUploadStoryBinding
import timber.log.Timber

class UploadStoryActivity : AppCompatActivity() {


    private val viewModel by viewModels<ViewModelUploadStory> {
        FactoryViewModel.getInstance(this)
    }

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private lateinit var binding : ActivityUploadStoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBack.setOnClickListener {
            finish()
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnUpload.setOnClickListener { uploadImages() }
        binding.btnGallery.setOnClickListener { startGallery() }


    }

    private fun startGallery() {
        launcrGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcrGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImages()
        } else {
            Timber.tag("Photo Picker").d("No media selected")
        }
    }


    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCamera.launch(intent)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERA_IMAGE)?.toUri()
            showImages()
        }
    }

    private fun showImages() {
        currentImageUri?.let {
            Timber.tag("Image URI").d("showImage: %s", it)
            binding.imgPreview.setImageURI(it)
        }
    }


    private fun uploadImages() {
        val description = binding.edtDescription.text.toString()
        if (description.isBlank()) {
            showToast("Tambahkan Desripsi Gambar Terlebih dahulu")
            return
        }
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).FileImages()
            Timber.tag("Image File").d("showImage: %s", imageFile.path)

            val intent = Intent(this, MainActivity::class.java)

            viewModel.uploadImage(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Results.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Results.Success -> {
                            binding.progressBar.visibility = View.GONE
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                        is Results.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Upload Gambar Kembali", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } ?: showToast("Mamasukkan gambar terlebih dahulu!")
    }



    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}