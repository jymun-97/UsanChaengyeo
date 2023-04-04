package com.jymun.usanchaengyeo.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.databinding.EditAddressViewBinding

class EditAddressView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = EditAddressViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var address: Address? = null
        set(value) = with(binding) {
            field = value
            value?.let {
                addressTextContainer.visibility = View.VISIBLE
                stateTextView.visibility = View.INVISIBLE
                addressTextView.text = it.addressText
                subAddressTextView.text = it.subAddressText
            } ?: run {
                addressTextContainer.visibility = View.INVISIBLE
                stateTextView.visibility = View.VISIBLE
            }
        }
    var hasFocused = false

    init {
        initViews()
    }

    private fun initViews() = binding.apply {
        initAddressTextInput()
        setOnClickListener {}
    }

    private fun initAddressTextInput() = binding.apply {
        addressTextInputLayout.visibility = View.INVISIBLE
        addressEditText.setOnFocusChangeListener { _, hasFocus ->
            hasFocused = hasFocus
            if (hasFocus) {
                addressTextContainer.visibility = View.INVISIBLE
                stateTextView.visibility = View.INVISIBLE

                showKeyboard()
            } else {
                submitAddress(
                    stateText = addressEditText.text.toString().ifEmpty {
                        addressTextInputLayout.hint.toString()
                    }
                )
            }
        }
    }

    private fun showKeyboard() {
        val imm: InputMethodManager = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.showSoftInput(binding.addressEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun submitAddress(
        newAddress: Address? = null,
        stateText: String? = null
    ) = binding.apply {

        addressTextInputLayout.visibility = View.INVISIBLE
        stateTextView.text = stateText ?: stateTextView.text
        address = newAddress
    }

    companion object {
        @BindingAdapter("app:search_keyword")
        @JvmStatic
        fun setKeyword(view: EditAddressView, keyword: String?) {
            val preKeyword = view.binding.addressEditText.text.toString()
            if (preKeyword != keyword) {
                view.binding.addressEditText.setText(keyword ?: "")
            }
        }

        @BindingAdapter("app:search_keywordAttrChanged")
        @JvmStatic
        fun setKeywordInverseBindingListener(
            view: EditAddressView,
            listener: InverseBindingListener?
        ) {
            view.binding.addressEditText.addTextChangedListener {
                listener?.onChange()
            }
        }

        @InverseBindingAdapter(
            attribute = "app:search_keyword",
            event = "app:search_keywordAttrChanged"
        )
        @JvmStatic
        fun getKeyword(view: EditAddressView): String {
            return view.binding.addressEditText.text.toString()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) = with(binding) {
        root.setOnClickListener {
            addressTextInputLayout.visibility = View.VISIBLE
            addressEditText.requestFocus()

            l?.onClick(this@EditAddressView)
        }
    }
}