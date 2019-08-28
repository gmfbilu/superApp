package org.gmfbilu.superapp.module_view.textView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_view.R;

public class TextViewFragment extends BaseFragment {

    private TextView tv1, tv2, tv3, tv4,tv5,tv5_2;

    public static TextViewFragment newInstance() {
        Bundle args = new Bundle();
        TextViewFragment fragment = new TextViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv5_2 = view.findViewById(R.id.tv5_2);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_textview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        setTV1();
        setTV2();
        setTV3();
        setTV4();
        setTV5();
    }

    /**
     * 使用的html标签的形式
     */
    private void setTV1() {
        String str1 = "今天<font color='#FF0000'><big>天气不错</big></font>";
        tv1.setText(Html.fromHtml(str1));
    }

    /**
     * 系统自带的SpannableString
     * ForegroundColorSpan是文字颜色。如果要为文字添加背景颜色，可替换为BackgroundColorSpan
     * 第二个参数：2为文本颜色改变的起始位置，spannableString.length()为文本颜色改变的结束位置
     * 最后一个参数为布尔型，可以传入以下四种：
     * 1.Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终止下标，包括起始下标
     * 2.Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终止下标，同时包括起始下标和终止下标
     * 3.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终止下标，但都不包括起始下标和终止下标
     * 4.Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终止下标，包括终止下标
     */
    private void setTV2() {
        SpannableString spannableString = new SpannableString("今天天气不错");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 2, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv2.setText(spannableString);
    }

    /**
     * SpannableStringBuilder和SpannableString很相似
     * 1.Object what：根据传入的具体 Object 对象来标记 Span 范围的Text
     * 2.int start：Span 的开始位置index
     * 3.int end：Span的结束位置index，并不包括这个位置
     * 4.int flags：标记位
     * <p>
     * setSpan 方法的 Object 参数可以传入各种Span，不同的Span对应不同的样式
     * AbsoluteSizeSpan(int size) ：设置字体大小，参数是绝对数值.false，size单位为px，true，size单位为dip（默认为false）
     * RelativeSizeSpan(float proportion) ：设置字体大小，参数是相对于默认字体大小的倍数，proportion>1就是放大(zoom in)，proportion<1就是缩小(zoom out)
     * BackgroundColorSpan(int color)：背景色
     * ForegroundColorSpan(int color)：前景色，也就是字体的着色
     * TypefaceSpan(String family)：字体，参数是字体的名字比如“sans"，"sans-serif"等
     * StyleSpan(Typeface style)：字体风格，比如粗体，斜体，参数是android.graphics.Typeface里面定义的常量，如Typeface.BOLD，Typeface.ITALIC等等
     * StrikethroughSpan：设置删除线
     * UnderlineSpan：设置下划线
     * ClickableSpan：设置点击事件监听，需要重写 onClick 方法.如果需要同时改变字体的颜色，以及做一些其它改变（如上面的各个分项），还可以重写 updateDrawState 方法
     */
    private void setTV3() {
        String str = new String("获取更多帮助，请拨打客服电话400-888-8888");
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(str);
        // 设置字体大小
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(AppUtils.sp2pxPaint(14), false);
        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(AppUtils.sp2pxPaint(24), false);
        spannableBuilder.setSpan(sizeSpan, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder.setSpan(sizeSpan2, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 单独设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));
        spannableBuilder.setSpan(colorSpan, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 在设置点击事件、同时设置字体颜色
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                ToastUtil.show("拨打电话");
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                // paint.setColor(Color.parseColor("#3072F6"));
            }
        };
        spannableBuilder.setSpan(clickableSpanTwo, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置点击生效
        tv3.setMovementMethod(LinkMovementMethod.getInstance());
        tv3.setText(spannableBuilder);
        // 去掉点击后文字的背景色
        // tv3.setHighlightColor(Color.parseColor("#00000000"));
    }

    private void setTV4() {
        String str = new String("您当前帐户流水超过等值10000CNY,为了保障您和他人的资产安全，根据国际反洗钱金融行动特别工作组(FATF)相关规定，您需要先前往KYC认证才能进行合规交易。");
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(str);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#999999"));
        spannableBuilder.setSpan(colorSpan1, 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#333333"));
        spannableBuilder.setSpan(colorSpan2, 11, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(Color.parseColor("#999999"));
        spannableBuilder.setSpan(colorSpan3, 19, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan4 = new ForegroundColorSpan(Color.parseColor("#333333"));
        spannableBuilder.setSpan(colorSpan4, 36, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan5 = new ForegroundColorSpan(Color.parseColor("#999999"));
        spannableBuilder.setSpan(colorSpan5, 56, 60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan6 = new ForegroundColorSpan(Color.parseColor("#333333"));
        spannableBuilder.setSpan(colorSpan6, 61, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan1 = new StyleSpan(Typeface.BOLD);
        spannableBuilder.setSpan(styleSpan1, 11, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan2 = new StyleSpan(Typeface.BOLD);
        spannableBuilder.setSpan(styleSpan2, 36, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan3 = new StyleSpan(Typeface.BOLD);
        spannableBuilder.setSpan(styleSpan3, 61, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableBuilder.setSpan(underlineSpan, 61, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv4.setText(spannableBuilder);

    }


    private void setTV5(){
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX,AppUtils.sp2pxTextView(R.dimen.sp25));
        tv5_2.setTextSize(TypedValue.COMPLEX_UNIT_PX,AppUtils.sp2pxTextView(R.dimen.sp15));
    }
}
