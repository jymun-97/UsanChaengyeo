package com.jymun.usanchaengyeo.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jymun.usanchaengyeo.databinding.DialogRequestPermissionBinding

class RequestPermissionDialog(
    private val onPositiveButtonClicked: () -> Unit
) : DialogFragment() {

    private var _binding: DialogRequestPermissionBinding? = null
    private val binding: DialogRequestPermissionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogRequestPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            titleTextView.text = TITLE_REQUEST_ALL_PERMISSIONS
            contentTextView.text = CONTENT_REQUEST_ALL_PERMISSIONS
            positiveTextButton.setOnClickListener {
                onPositiveButtonClicked()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TITLE_REQUEST_ALL_PERMISSIONS = "위치 접근 권한"
        private const val CONTENT_REQUEST_ALL_PERMISSIONS =
            "현재 위치 정보를 얻기 위해서 권한이 필요합니다.\n보다 편리한 이용을 원하시다면 접근 권한을 허용해주세요."
    }
}