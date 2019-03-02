package org.gmfbilu.superapp.module_kotlin

import android.os.Bundle
import android.view.View
import org.gmfbilu.superapp.lib_base.base.BaseFragment
import org.gmfbilu.superapp.lib_base.utils.ToastUtil

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
        if (id == R.id.tv) {
            ToastUtil.show("hello")
        }
    }

    override fun findViewById_setOnClickListener(view: View?) {
        view?.findViewById<View>(R.id.tv)?.setOnClickListener(this)
    }

    override fun setLayout(): Int {
        return R.layout.module_kotlin_fragment_main
    }

}