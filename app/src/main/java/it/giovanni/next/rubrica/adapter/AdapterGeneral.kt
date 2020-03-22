/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class AdapterGeneral(

    // generic list of items
    private var items: List<*>,

    // layout resource id of items (this adapter is not meant to manage multiple types
    private val layout: Int,

    // main binding variable
    private val binding: Int,

    // lifecycle owner of the binding
    private val lifecycle: LifecycleOwner?,

    // optional binding variables (needed if xml defines multiple variables in it)
    private val optional: Map<Int, Any>? = null

) : RecyclerView.Adapter<ViewHolderGeneral>() {

    fun setItems(items: List<*>) {
        this.items = items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        if (lifecycle != null)
            binding.lifecycleOwner = lifecycle
        return ViewHolderGeneral(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderGeneral, position: Int) {
        val viewDataBinding = holder.viewDataBinding

        // main binding assignment
        viewDataBinding.setVariable(binding, items[position])

        optional?.let {
            val keys = it.keys
            for (key in keys) {
                // optional binding variables assignment
                viewDataBinding.setVariable(key, it[key])
            }
        }

        // xml binding execution
        viewDataBinding.executePendingBindings()
    }
}