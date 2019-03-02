package debug

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.gmfbilu.superapp.lib_base.base.BaseActivity
import org.gmfbilu.superapp.module_kotlin.MainActivity

class LauncherActivity : BaseActivity() {

    override fun onClick(v: View?) {
    }

    override fun findViewById_setOnClickListener(savedInstanceState: Bundle?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setLayout(): Int {
        return 0
    }
}