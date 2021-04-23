package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestTxLiveBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.txlive.PlayDao;
import com.etatech.test.utils.txlive.PushDao;
import com.etatech.test.utils.ui.ClickUtil;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx1.TedRxPermission;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import rx.functions.Action1;

public class TestTxLiveActivity extends BaseActivity<ActivityTestTxLiveBinding> {
    public final String mLiveUrl = "rtmp://192.168.1.141:1935/live/home";

    @Override
    public ActivityTestTxLiveBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_tx_live);
    }

    @Override
    public void init() {
        initTXLive();
        initClick();
    }

    private void initTXLive() {
        TedRxPermission.with(this)
                .setDeniedMessage("如果你拒绝权限,将无法使用当前功能.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA)
                .request()
                .subscribe(new Action1<TedPermissionResult>() {
                    @Override
                    public void call(TedPermissionResult tedPermissionResult) {
                        if (tedPermissionResult.isGranted()) {
                            PushDao.getInstance().initPushDao(TestTxLiveActivity.this);
                            PlayDao.getInstance().initPlayDao(TestTxLiveActivity.this, binding.txPlayView);

                            getLifecycle().addObserver(PushDao.getInstance());
                        } else {
                            Toast.makeText(TestTxLiveActivity.this,
                                    "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.btnTestPush, new Action1() {
            @Override
            public void call(Object o) {
                PushDao.getInstance().preview(binding.txPushPreview);
                PushDao.getInstance().startOwnPushing(mLiveUrl);
            }
        });
        ClickUtil.setOnClick(binding.btnTestPull, new Action1() {
            @Override
            public void call(Object o) {
                PlayDao.getInstance().playAction(mLiveUrl);
            }
        });
    }
}