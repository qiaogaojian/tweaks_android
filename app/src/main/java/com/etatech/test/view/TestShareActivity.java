package com.etatech.test.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestShareBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.ui.ImageUtil;
import com.sdbean.megashare.share.ShareListener;
import com.sdbean.megashare.share.ShareManager;
import com.sdbean.megashare.share.SharePlatform;
import com.sdbean.megashare.share.WebContent;
import com.sdbean.megashare.util.PlatformHelper;

import rx.functions.Action1;


public class TestShareActivity extends BaseActivity<ActivityTestShareBinding> {
    WebContent content;

    @Override
    public ActivityTestShareBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestShareActivity.this, R.layout.activity_test_share);
    }

    @Override
    public void init() {
        final ShareListener mListener = new ShareListener() {
            @Override
            public void onComplete(SharePlatform.Platform platform) {
                Toast.makeText(TestShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(SharePlatform.Platform platform, String errorInfo) {
                Toast.makeText(TestShareActivity.this, "分享失败" + errorInfo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SharePlatform.Platform platform) {
                Toast.makeText(TestShareActivity.this, "分享取消", Toast.LENGTH_SHORT).show();

            }
        };

        ClickUtil.setOnClick(binding.btnLine1, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_line1: // 文本
                Toast.makeText(TestShareActivity.this, "share text to line", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareText(TestShareActivity.this, binding.etShare.getText().toString(), ShareManager.ShareChannel.Line, mListener);
            }
        });
        ClickUtil.setOnClick(binding.btnFacebook1, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_facebook1: // 文本+网页链接
                content = new WebContent.Builder("https://www.google.com/")
                        .hashTag("#test")
                        .quote("werewolf")
                        .thumbImage(ImageUtil.viewToBitmap(binding.ivShare1))
                        .title("天天狼人")
                        .build();
                Toast.makeText(TestShareActivity.this, "share web to facebook", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareWebpage(TestShareActivity.this, content, ShareManager.ShareChannel.FacebookClient, mListener);
            }
        });
        ClickUtil.setOnClick(binding.btnTwitter1, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_twitter1: // 文本+网页链接
                content = new WebContent.Builder("https://www.google.com/")
                        .hashTag("#test")
                        .quote("werewolf")
                        .thumbImage(ImageUtil.viewToBitmap(binding.ivShare1))
                        .title("天天狼人")
                        .build();
                Toast.makeText(TestShareActivity.this, "share web to facebook", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareWebpage(TestShareActivity.this, content, ShareManager.ShareChannel.TwitterClient, mListener);
            }
        });
        ClickUtil.setOnClick(binding.btnLine2, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_line2: // 图片
                Toast.makeText(TestShareActivity.this, "share image to line", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareImage(TestShareActivity.this, ImageUtil.viewToBitmap(binding.ivShare1), ShareManager.ShareChannel.Line, mListener);
            }
        });
        ClickUtil.setOnClick(binding.btnLine3, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_line2: // 图片
                Toast.makeText(TestShareActivity.this, "share image to line", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareImage(TestShareActivity.this, ImageUtil.viewToBitmap(binding.ivShare2), ShareManager.ShareChannel.Line, mListener);
            }
        });
        ClickUtil.setOnClick(binding.btnFacebook2, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_facebook2:// 图片
                Toast.makeText(TestShareActivity.this, "share image to facebook", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareImage(TestShareActivity.this, ImageUtil.viewToBitmap(binding.ivShare1), ShareManager.ShareChannel.FacebookClient, mListener);

            }
        });
        ClickUtil.setOnClick(binding.btnTwitter2, new Action1() {
            @Override
            public void call(Object o) {
                // case R.id.btn_twitter2:// 图片
                Toast.makeText(TestShareActivity.this, "share image to twitter", Toast.LENGTH_SHORT).show();
                ShareManager.getInstance().shareImage(TestShareActivity.this, ImageUtil.viewToBitmap(binding.ivShare1), ShareManager.ShareChannel.TwitterInnerApp, mListener);
            }
        });


    }
}

