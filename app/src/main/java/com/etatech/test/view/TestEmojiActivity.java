package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestEmojiBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.io.UnsupportedEncodingException;

import rx.functions.Action1;

import static java.lang.Character.isHighSurrogate;
import static java.lang.Character.isLowSurrogate;

public class TestEmojiActivity extends BaseActivity<ActivityTestEmojiBinding> {

    @Override
    public ActivityTestEmojiBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestEmojiActivity.this, R.layout.activity_test_emoji);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnTestEmoji, new Action1() {
            @Override
            public void call(Object o) {
                StringBuilder sb = new StringBuilder();
                try {
                    String name_utf8 = "小鲤鱼\uD83E\uDD80历\uD83E\uDD80险\uD83E\uDD80记\uD83E\uDD80";
                    String name_gbk = new String(name_utf8.getBytes("GBK"), "GBK");
                    String name_2312 = new String(name_utf8.getBytes("GBK"), "GB2312");

                    sb.append("截取前:\n");
                    sb.append(name_utf8);
                    sb.append("\n截取前8个字符后:\n");
                    sb.append(String.format("%s...", subEmojiString(name_utf8, 0, 7)));
                    binding.tvLog.setText(sb.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 分割emoji(4个字节)字符串(2个字符串)
    private String subEmojiString(String input, int start, int end) {
        int unicodeCount = codePointCount(input, start, end);             // 获取unicode个数
        int offsetIndex = input.offsetByCodePoints(start, unicodeCount); // 根据unicode个数获取偏移位置
        return input.substring(start, offsetIndex);
    }

    public static int codePointCount(CharSequence seq, int beginIndex, int endIndex) {
        int length = seq.length();
        if (beginIndex < 0 || endIndex > length || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        int n = endIndex - beginIndex;
        for (int i = beginIndex; i < endIndex; ) {
            if (isHighSurrogate(seq.charAt(i++)) && i < endIndex &&
                    isLowSurrogate(seq.charAt(i))) {
                n--;
                i++;
            }
        }
        return n;
    }

    public static int offsetByCodePoints(CharSequence seq, int index,
                                         int codePointOffset) {
        int length = seq.length();
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }

        int x = index;
        if (codePointOffset >= 0) {
            int i;
            for (i = 0; x < length && i < codePointOffset; i++) {
                if (isHighSurrogate(seq.charAt(x++)) && x < length &&
                        isLowSurrogate(seq.charAt(x))) {
                    x++;
                }
            }
            if (i < codePointOffset) {
                throw new IndexOutOfBoundsException();
            }
        } else {
            int i;
            for (i = codePointOffset; x > 0 && i < 0; i++) {
                if (isLowSurrogate(seq.charAt(--x)) && x > 0 &&
                        isHighSurrogate(seq.charAt(x - 1))) {
                    x--;
                }
            }
            if (i < 0) {
                throw new IndexOutOfBoundsException();
            }
        }
        return x;
    }
}