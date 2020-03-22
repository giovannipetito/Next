/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.view.master

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.giovanni.next.BR
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import it.giovanni.next.R
import it.giovanni.next.databinding.MasterFragmentBinding
import it.giovanni.next.rubrica.adapter.AdapterGeneral
import it.giovanni.next.rubrica.core.App
import it.giovanni.next.rubrica.data.MasterFragmentMode
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.extensions.*
import javax.inject.Inject

class MasterFragment : Fragment(), MasterView {

    @Inject
    lateinit var presenter: MasterPresenter
    lateinit var binding: MasterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.master_fragment,
            container,
            false
        )
        binding.setVariable(BR.presenter, presenter)
        binding.titleBar.presenter = presenter
        binding.searchBar.presenter = presenter
        binding.executePendingBindings()
        binding.recycler.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(
            binding.recycler.context,
            (binding.recycler.layoutManager as LinearLayoutManager).orientation
        )
        divider.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_inset,
                null
            ) as Drawable
        )
        binding.recycler.addItemDecoration(divider)
        presenter.init(
            this,
            (arguments?.getSerializable("mode") ?: MasterFragmentMode.ROOM) as MasterFragmentMode
        )
        return binding.root
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun onFullList(contacts: List<Contact>) {
        binding.recycler.post {
            binding.recycler.adapter = AdapterGeneral(
                contacts,
                R.layout.master_item_contact,
                BR.contact,
                binding.lifecycleOwner,
                mapOf(BR.presenter to presenter)
            )
        }
    }

    override fun onFilteredList(contacts: List<Contact>) {
        when (val adapter = binding.recycler.adapter) {
            is AdapterGeneral -> {
                adapter.setItems(contacts)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setBackNavigationFeatureEnabled(enabled: Boolean) {
        if (enabled) {
            binding.back.show()
            binding.back.setOnClickListener {
                navigateBack()
            }
        } else
            binding.back.gone()
    }

    override fun setTitle(res: Int) {
        binding.titleBar.listTitle.text = getString(res)
    }

    override fun setTitleBarVisible(visible: Boolean) {
        if (visible)
            binding.titleBar.root.show()
        else
            binding.titleBar.root.gone()
    }

    override fun setSearchBarVisible(visible: Boolean) {
        if (visible)
            binding.searchBar.root.show()
        else
            binding.searchBar.root.gone()
    }

    override fun setButtonAddVisible(visible: Boolean) {
        if (visible)
            binding.actionAdd.show()
        else
            binding.actionAdd.hide()
    }

    override fun updateListDependentViews(listIsEmpty: Boolean) {
        if (listIsEmpty) {
            binding.recycler.gone()
            binding.noDataIcon.show()
            binding.noDataCaption.show()
            binding.titleBar.filter.hide()
        } else {
            binding.recycler.show()
            binding.noDataIcon.gone()
            binding.noDataCaption.gone()
            binding.titleBar.filter.show()
        }
    }

    override fun navigateTo(where: Int) {
        this.goto(where)
    }

    override fun navigateBack() {
        this.back()
    }
}