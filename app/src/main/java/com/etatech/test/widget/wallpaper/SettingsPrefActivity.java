package com.etatech.test.widget.wallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.loader.content.CursorLoader;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityWallpaperSettingBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.pavelsikun.seekbarpreference.PersistValueListener;

import rx.functions.Action1;

public class SettingsPrefActivity extends BaseActivity<ActivityWallpaperSettingBinding> implements ColorPickerDialogListener {

    private SettingsPref setting;
    public static int PICK_IMAGE = 1;
    // private  AdView adView;

    @Override
    public ActivityWallpaperSettingBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_wallpaper_setting);
    }

    @Override
    public void init() {
        initData();
        initView();
        initClick();
    }

    private void initData() {
        setting = new SettingsPref();
    }

    private void initView() {
        binding.cpvParticleColor.setColor(setting.getParticleColor());
        binding.cpvLineColor.setColor(setting.getLineColor());
        binding.cpvBackgroundColor.setColor(setting.getBackgroundColor());
        binding.switchTouchEffect.setChecked(setting.getTouchEffect() == 1);
        binding.tvBackgroundImageInfo.setText("Selected image: " + setting.getBackgroundImagePath());

        binding.sbvParticleCount.setCurrentValue(setting.getParticleCount());
        binding.sbvParticleCount.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                setting.setParticleCount(value);
                return true;
            }
        });

        binding.sbvLineLength.setCurrentValue(setting.getLineLength());
        binding.sbvLineLength.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                setting.setLineLength(value);
                return true;
            }
        });

        binding.sbvParticleVelocity.setCurrentValue(setting.getParticleVelocity());
        binding.sbvParticleVelocity.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                setting.setParticleVelocity(value);
                return true;
            }
        });

        binding.sbvParticleSize.setCurrentValue(setting.getParticleSize());
        binding.sbvParticleSize.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                setting.setParticleSize(value);
                return true;
            }
        });

        binding.sbvLineThickness.setCurrentValue(setting.getLineThickness());
        binding.sbvLineThickness.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                setting.setLineThickness(value);
                return true;
            }
        });

        binding.switchTouchEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setting.setTouchEffect(isChecked ? 1 : 0);
            }
        });
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.layoutSave, new Action1() {
            @Override
            public void call(Object o) {
                Intent intent = new Intent();
                String cls = LiveWallpaperAndroid.class.getCanonicalName();
                intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getPackageName(), cls));

                startActivity(intent);
            }
        });

        ClickUtil.setOnClick(binding.cpvParticleColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setDialogId(R.id.cpv_particle_color)
                        .setColor(setting.getParticleColor())
                        .setShowAlphaSlider(true)
                        .show(SettingsPrefActivity.this);
            }
        });

        ClickUtil.setOnClick(binding.cpvLineColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setDialogId(R.id.cpv_line_color)
                        .setColor(setting.getLineColor())
                        .setShowAlphaSlider(true)
                        .show(SettingsPrefActivity.this);
            }
        });

        ClickUtil.setOnClick(binding.cpvBackgroundColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setDialogId(R.id.cpv_background_color)
                        .setColor(setting.getBackgroundColor())
                        .setShowAlphaSlider(true)
                        .show(SettingsPrefActivity.this);
            }
        });

        ClickUtil.setOnClick(binding.layoutBackgroundImage, new Action1() {
            @Override
            public void call(Object o) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

    }

    private void initAd() {
        // MobileAds.initialize(this, "ca-app-pub-3690357492073975~5025810258");
        // adView = (AdView) findViewById(R.id.adView9);
        // AdRequest adRequest = new AdRequest.Builder().build();
        // adView.loadAd(adRequest);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case R.id.cpv_particle_color:
                binding.cpvParticleColor.setColor(color);
                setting.setParticleColor(color);
                break;
            case R.id.cpv_line_color:
                binding.cpvLineColor.setColor(color);
                setting.setLineColor(color);
                break;
            case R.id.cpv_background_color:
                binding.cpvBackgroundColor.setColor(color);
                setting.setBackgroundColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Log.d("Intent data: ", data.toString());
            String path = getRealPathFromURI(data.getData());
            // path = path.replace(Environment.getExternalStorageDirectory().toString(), "");
            binding.tvBackgroundImageInfo.setText("Selected image: " + path);
            setting.setBackgroundImagePath(path);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}