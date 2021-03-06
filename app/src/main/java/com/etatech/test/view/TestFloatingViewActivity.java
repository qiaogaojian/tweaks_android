package com.etatech.test.view;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.etatech.spine.SpineBaseFragment;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestFloatViewBinding;
import com.etatech.test.service.FloatingService;
import com.etatech.test.spine.SpineBoyAdapter;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.view.custom.floatmenu.FloatItem;
import com.etatech.test.view.custom.floatmenu.FloatLogoMenu;
import com.etatech.test.view.custom.floatmenu.FloatMenuView;

import java.util.ArrayList;

import rx.functions.Action1;

public class TestFloatingViewActivity extends BaseActivity<ActivityTestFloatViewBinding> {
    private TextView mOpenFloat;
    public static final int REQUEST_CODE = 114;

    @Override
    public ActivityTestFloatViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_float_view);
    }

    @Override
    public void init() {

        ClickUtil.setOnClick(binding.btnOpenFloatMenuOut, new Action1() {
            @Override
            public void call(Object o) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(getApplicationContext())) {
                        //启动Activity让用户授权
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 100);
                    } else {
                        startFloatingService();
                    }
                } else {
                    startFloatingService();
                }
            }
        });

        ClickUtil.setOnClick(binding.btnOpenFloatMenu, new Action1() {
            @Override
            public void call(Object o) {
                showFloatView(TestFloatingViewActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    startFloatingService();
                } else {
                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startFloatingService() {
        startService(new Intent(TestFloatingViewActivity.this, FloatingService.class));
    }

    private FloatLogoMenu mFloatMenu;
    private int[] menuIcons = new int[]{R.drawable.floating_icon2, R.drawable.floating_icon3, R.drawable.floating_icon4};
    private ArrayList<FloatItem> itemList = new ArrayList<>();
    private String[] MENU_ITEMS = {"", "", ""};

    private void showFloatView(final Activity activity) {
        for (int i = 0; i < menuIcons.length; i++) {
            itemList.add(new FloatItem(MENU_ITEMS[i], BitmapFactory.decodeResource(this.getResources(), menuIcons[i])));
        }

        mFloatMenu = new FloatLogoMenu.Builder()
                .withActivity(activity)
                .logo(BitmapFactory.decodeResource(getResources(), R.drawable.floating_icon1))
                .drawCicleMenuBg(true)
                .backMenuColor(0xB2000000)
                .setBgDrawable(activity.getResources().getDrawable(R.drawable.floating_view_bg))
                .setFloatItems(itemList)
                .showWithListener(new FloatMenuView.OnMenuClickListener() {
                    @Override
                    public void onItemClick(int position, String title) {
                        Toast.makeText(activity, "position " + position + " title:" + title + " is clicked.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void dismiss() {

                    }
                });
    }
}
