package com.jymun.usanchaengyeo.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
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

    init {
        initViews()
    }

    private fun initViews() = binding.apply {
        initAddressTextInput()

        root.setOnClickListener {
            addressTextInputLayout.visibility = View.VISIBLE
            addressEditText.requestFocus()
        }
    }

    private fun initAddressTextInput() = binding.apply {
        addressTextInputLayout.visibility = View.INVISIBLE
        addressEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                addressTextContainer.visibility = View.INVISIBLE
                stateTextView.visibility = View.INVISIBLE

                showKeyboard()
            } else {
                submitAddress(address)
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
}