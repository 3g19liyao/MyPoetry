package com.example.mypoetry.mine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MineLikeFragment: Fragment() {

    var userData: MutableLiveData<User> = MutableLiveData()
    lateinit var mineViewModel: MineViewModel
    val tabs = arrayOf("诗词","美句")
    lateinit var tabLayout: TabLayout
    lateinit var pager: ViewPager2
    val fragmentList = arrayListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.minelike_fragment,container,false)

        tabLayout = view.findViewById(R.id.tab_layout)
        pager = view.findViewById(R.id.pager)
        tabLayout.addTab(tabLayout.newTab().setText(tabs[0]))
        fragmentList.add(PoetLikeFragment())
        tabLayout.addTab(tabLayout.newTab().setText(tabs[1]))
        fragmentList.add(SayingLikeFragment())

        pager.adapter = activity?.supportFragmentManager?.let {
            FragmentAdapter(it,fragmentList,LifecycleRegistry(this).apply {
            currentState = Lifecycle.State.RESUMED
        })
        }

        pager.offscreenPageLimit = 2
        pager.setCurrentItem(0,false)

        TabLayoutMediator(tabLayout, pager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> {
                    tab.text = tabs[0]
                }
                1 -> {
                    tab.text = tabs[1]
                }
            }
        }.attach()

        for (i in 0 until pager.adapter?.itemCount!!) {
            val tab = tabLayout.getTabAt(i)//获得每一个tab
            tab?.setCustomView(R.layout.tab_text_layout)//给每一个tab设置view
            if (i == 0) {
                val tv = tab?.customView!!.findViewById(R.id.tab_text) as TextView
                initTableText(true,tv,19f,0.9F,tabs[i])
            }else{
                val tv = tab?.customView!!.findViewById(R.id.tab_text) as TextView
                initTableText(false,tv,15f,0.5F,tabs[i])
            }
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView!!.findViewById(R.id.tab_text) as TextView
                initTableText(true,tv,19f,0.9F)
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView!!.findViewById(R.id.tab_text) as TextView
                initTableText(false,tv,15f,0.5F)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        mineViewModel.Query()
        this.activity?.let {
            mineViewModel.userData.observe(it, Observer {
                userData.value = it
            })
        }

        return view
    }

    private fun initTableText(isSelected: Boolean, tv: TextView, textSize: Float, alpha: Float) {
        tv?.isSelected = isSelected//tab是否被选中
        tv.textSize = textSize
        tv.alpha =alpha
    }

    private fun initTableText(isSelected: Boolean, tv: TextView, textSize: Float, alpha: Float,s:String) {
        tv?.isSelected = isSelected//tab是否被选中
        tv.textSize = textSize
        tv.alpha =alpha
        tv.text = s

    }

}
