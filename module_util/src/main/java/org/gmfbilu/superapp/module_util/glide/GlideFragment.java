package org.gmfbilu.superapp.module_util.glide;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * https://muyangmin.github.io/glide-docs-cn/
 *
 * Glide 支持拉取，解码和展示视频快照，图片，和GIF动画。开发者甚至可以插入和替换成自己喜爱的任何网络栈。默认情况下，Glide使用的是一个定制化的基于HttpUrlConnection的栈，但同时也提供了与Google Volley和Square OkHttp快速集成的工具库
 */
public class GlideFragment extends BaseFragment {

    private AppCompatImageView iv;

    public static GlideFragment newInstance() {
        Bundle args = new Bundle();
        GlideFragment fragment = new GlideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        iv = view.findViewById(R.id.iv);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_glide;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
