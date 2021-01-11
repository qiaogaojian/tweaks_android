package com.etatech.test.view;

import android.Manifest;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestExportSvgBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.ui.ImageUtil;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx1.TedRxPermission;

import rx.functions.Action1;

public class TestExportSvgActivity extends BaseActivity<ActivityTestExportSvgBinding> {

    @Override
    public ActivityTestExportSvgBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestExportSvgActivity.this, R.layout.activity_test_export_svg);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnSave, new Action1() {
            @Override
            public void call(Object o) {
                TedRxPermission.with(TestExportSvgActivity.this)
                        .setDeniedMessage("如果你拒绝权限,将无法保存图片.")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .request()
                        .subscribe(new Action1<TedPermissionResult>() {
                            @Override
                            public void call(TedPermissionResult tedPermissionResult) {
                                if (tedPermissionResult.isGranted()) {
                                    ImageUtil.saveBmp2Gallery(ImageUtil.getBitmapFromVectorDrawable(TestExportSvgActivity.this, R.drawable.menu_switch),
                                            "menu_switch", TestExportSvgActivity.this);
                                } else {
                                    Toast.makeText(TestExportSvgActivity.this,
                                            "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
}
