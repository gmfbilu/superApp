package debug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_googlelibrary.MainActivity;

/**
 * Created by gmfbilu on 2018/3/19.
 */

public class LauncherActivity extends BaseActivity {



    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
