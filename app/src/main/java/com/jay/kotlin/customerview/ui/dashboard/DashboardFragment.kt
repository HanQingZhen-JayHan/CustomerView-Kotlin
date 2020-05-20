package com.jay.kotlin.customerview.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.ui.adapter.GridLayoutDecoration
import com.jay.kotlin.customerview.ui.adapter.ItemAdapter
import com.jay.kotlin.customerview.ui.adapter.ItemEntity
import com.jay.kotlin.customerview.utils.ScreenUtils

class DashboardFragment : Fragment(), ItemAdapter.ClickListener {

    private lateinit var vewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        rv = root.findViewById(R.id.rv)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private lateinit var rv: RecyclerView
    private fun setupRecyclerView() {
        rv.adapter = ItemAdapter(vewModel.data, this)
        rv.layoutManager = GridLayoutManager(context,2)
        //rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val myDivider = GridLayoutDecoration(ScreenUtils.dp2px(context,10f).toInt())
        rv.addItemDecoration(myDivider)
    }

    override fun onClick(item: ItemEntity?) {
        startActivity(Intent(activity, item?.cls))
    }
}