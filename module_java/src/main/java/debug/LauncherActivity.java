package debug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_java.MainActivity;

public class LauncherActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
