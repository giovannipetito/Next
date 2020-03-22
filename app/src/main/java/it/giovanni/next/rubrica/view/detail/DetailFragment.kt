/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.giovanni.next.BR
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import it.giovanni.next.rubrica.core.App
import it.giovanni.next.rubrica.data.DetailFragmentMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.giovanni.next.R
import it.giovanni.next.databinding.DetailFragmentBinding
import it.giovanni.next.rubrica.extensions.*
import javax.inject.Inject

class DetailFragment : BottomSheetDialogFragment(), DetailView {

    @Inject
    lateinit var presenter: DetailPresenter
    lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.detail_fragment,
            container,
            false
        )
        binding.setVariable(BR.presenter, presenter)
        binding.executePendingBindings()
        presenter.init(
            this,
            (arguments?.getSerializable("mode")
                ?: DetailFragmentMode.ADD_CONTACT) as DetailFragmentMode
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onNavigatingBack()
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun shiftModeTo(mode: DetailFragmentMode) {
        arguments?.putSerializable("mode", mode)
        when (mode) {
            DetailFragmentMode.SHOW_CONTACT -> {
                binding.actionSecondary.hide()
                binding.actionPrimary.text = getString(R.string.action_edit)
                setFieldsEditable(false)
            }
            DetailFragmentMode.EDIT_CONTACT -> {
                binding.actionSecondary.hide()
                binding.actionPrimary.text = getString(R.string.action_confirm)
                setFieldsEditable(true)
                binding.editFirst.requestFocus()
            }
            DetailFragmentMode.ADD_CONTACT -> {
                binding.actionSecondary.show()
                binding.actionPrimary.text = getString(R.string.action_add)
                setFieldsEditable(true)
            }
        }
    }

    private fun setFieldsEditable(editable: Boolean) {
        binding.editFirst.editable(editable)
        binding.editLast.editable(editable)
        binding.editPhone.editable(editable)
    }

    override fun onFirstText(string: String?) {
        binding.editFirst.setText(string)
    }

    override fun onLastText(string: String?) {
        binding.editLast.setText(string)
    }

    override fun onPhoneText(string: String?) {
        binding.editPhone.setText(string)
    }

    override fun onFirstError(stringRes: Int) {
        binding.editFirst.error = if (stringRes == 0)
            null
        else
            getString(stringRes)
    }

    override fun onLastError(stringRes: Int) {
        binding.editLast.error = if (stringRes == 0)
            null
        else
            getString(stringRes)
    }

    override fun onPhoneError(stringRes: Int) {
        binding.editPhone.error = if (stringRes == 0)
            null
        else
            getString(stringRes)
    }

    override fun onRequestPermissions() {
        if (ContextCompat.checkSelfPermission(
                activity as AppCompatActivity,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                666
            )
        } else {
            presenter.onRequestPermissionsResult(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            666 -> {
                presenter.onRequestPermissionsResult(
                    grantResults.isNotEmpty()
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED
                )
            }
        }
    }

    override fun navigateTo(where: Int) {
        this.goto(where)
    }

    override fun navigateBack() {
        this.back()
    }
}