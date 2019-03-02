package org.gmfbilu.superapp.module_kotlin

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import org.gmfbilu.superapp.lib_base.app.ARouterPath
import org.gmfbilu.superapp.lib_base.base.BaseActivity

@Route(path = ARouterPath.MODULE_KOTLIN)
class MainActivity : BaseActivity() {

    override fun findViewById_setOnClickListener(savedInstanceState: Bundle?) {
        if (findFragment<MainFragment>(MainFragment::class.java) == null) {
            loadRootFragment(R.id.lib_base__fl_container, MainFragment.newInstance())
        }
    }

    override fun setLayout(): Int {
        return R.layout.module_lib_base_fl_container
    }

    override fun onClick(v: View?) {
    }

}
