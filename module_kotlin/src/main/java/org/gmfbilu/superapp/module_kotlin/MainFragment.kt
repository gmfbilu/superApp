package org.gmfbilu.superapp.module_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.gmfbilu.superapp.lib_base.base.BaseFragment
import org.gmfbilu.superapp.module_kotlin.sunflower.GardenActivity

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.bt) {
           startActivity(Intent(_mActivity,GardenActivity::class.java))
        }
    }

    override fun findViewById_setOnClickListener(view: View?) {
        view?.findViewById<View>(R.id.bt)?.setOnClickListener(this)
    }

    override fun setLayout(): Int {
        return R.layout.module_kotlin_fragment_main
    }

}