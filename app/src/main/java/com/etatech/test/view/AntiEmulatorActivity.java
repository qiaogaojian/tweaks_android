package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestAntiEmulatorBinding;
import com.etatech.test.utils.BaseActivity;
import com.sdbean.antiemulator.AntiEmulator;
import com.sdbean.antiemulator.AntiEmulatorAsync;

public class AntiEmulatorActivity extends BaseActivity<ActivityTestAntiEmulatorBinding> {

    @Override
    public ActivityTestAntiEmulatorBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_anti_emulator);
    }

    @Override
    public void init() {
//        if (AntiEmulator.checkEmulator(AntiEmulatorActivity.this)) {
//            binding.tvResult.setText(Html.fromHtml(AntiEmulator.checkEmulatorLog(AntiEmulatorActivity.this)));
//        } else {
//            binding.tvResult.setText("This phone is not Emulator.");
//        }
        AntiEmulatorAsync.checkEmulator(AntiEmulatorActivity.this, new AntiEmulatorAsync.CheckEmulatorListener() {
            @Override
            public void onResult(boolean isEmulator) {
                if (isEmulator) {
                    binding.tvResult.setText("This phone is Emulator.");
                } else {
                    binding.tvResult.setText("This phone is not Emulator.");
                }
            }
        });
    }
}
