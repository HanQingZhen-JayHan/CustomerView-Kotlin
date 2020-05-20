package com.jay.kotlin.customerview.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.ui.BaseFragment
import com.jay.kotlin.customerview.ui.adapter.GridLayoutDecoration
import com.jay.kotlin.customerview.ui.adapter.ItemAdapter
import com.jay.kotlin.customerview.ui.adapter.ItemEntity
import com.jay.kotlin.customerview.utils.ScreenUtils

class DashboardFragment : BaseFragment(), ItemAdapter.ClickListener {

    private lateinit var vewModel: DashboardViewModel
    override fun showLayout(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        setupRecyclerView()
        fileName = ""
    }

    private lateinit var rv: RecyclerView
    private fun setupRecyclerView() {
        view?.let {
            rv = it.findViewById(R.id.rv)
            rv.adapter = ItemAdapter(vewModel.data, this)
            rv.layoutManager = GridLayoutManager(context, 2)
            //rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val myDivider = GridLayoutDecoration(ScreenUtils.dp2px(context, 10f).toInt())
            rv.addItemDecoration(myDivider)
        }
    }

    override fun onClick(item: ItemEntity?) {
        startActivity(Intent(activity, item?.cls))
    }
}