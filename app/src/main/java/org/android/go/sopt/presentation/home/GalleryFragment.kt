package org.android.go.sopt.presentation.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.home.viewmodel.GalleryViewModel
import org.android.go.sopt.util.ContentUriRequestBody

class GalleryFragment : Fragment() {
    private var _binding : FragmentGalleryBinding? = null
    private val binding : FragmentGalleryBinding get() = requireNotNull(_binding) { "FragmentGalleryBinding error - null" } //값을 사용하는 애
    private val viewModel by viewModels<GalleryViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri: Uri? ->
        binding.ivImage.load(imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnGallery.setOnClickListener {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
            btnUpload.setOnClickListener {
                val title = etTitle.text.toString()
                val singer = etSinger.text.toString()
                viewModel.uploadMusic("1", title, singer)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}