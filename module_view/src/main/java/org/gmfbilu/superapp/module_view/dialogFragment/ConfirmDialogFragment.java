package org.gmfbilu.superapp.module_view.dialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.lib_base.view.dialogFragment.BaseDialogFragment;
import org.gmfbilu.superapp.lib_base.view.dialogFragment.DialogFragmentViewHolder;
import org.gmfbilu.superapp.module_view.R;


public class ConfirmDialogFragment extends BaseDialogFragment {

    private String type;

    public static ConfirmDialogFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        type = bundle.getString("type");
    }

    @Override
    public int intLayoutId() {
        return R.layout.module_view_dialogfragment_confirm;
    }

    @Override
    public void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {
        if ("1".equals(type)) {
            holder.setText(R.id.title, "提示");
            holder.setText(R.id.message, "您已支付成功！");
        } else if ("2".equals(type)) {
            holder.setText(R.id.title, "警告");
            holder.setText(R.id.message, "您的账号已被冻结！");
        }
        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
