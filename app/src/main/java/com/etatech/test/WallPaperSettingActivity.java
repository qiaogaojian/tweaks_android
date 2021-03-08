package com.etatech.test;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.loader.content.CursorLoader;

import com.etatech.test.databinding.ActivityWallpaperSettingBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.pavelsikun.seekbarpreference.PersistValueListener;

import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

public class WallPaperSettingActivity extends BaseActivity<ActivityWallpaperSettingBinding> implements ColorPickerDialogListener {

    public static int PICK_IMAGE = 1;
    // private  AdView adView;

    public static final String LINE_COLOR = "lineColor";
    public static final String PARTICLE_COLOR = "particleColor";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String PARTICLE_COUNT = "particle_count";
    public static final String LINE_LENGTH = "line_length";
    public static final String PARTICLE_VELOCITY = "particle_velocity";
    public static final String PARTICLE_SIZE = "particle_size";
    public static final String LINE_THICK = "line_thick";
    public static final String TOUCH_EFFECT = "touch_effect";
    public static final String BACKGROUND_IMAGE_PATH = "background_image_path";

    @Override
    public ActivityWallpaperSettingBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_wallpaper_setting);
    }

    @Override
    public void init() {
        initView();
        initClick();
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.layoutSave, new Action1() {
            @Override
            public void call(Object o) {
                Intent intent = new Intent();
                String pkg = LiveWallpaperAndroid.class.getPackage().getName();
                String cls = LiveWallpaperAndroid.class.getCanonicalName();
                intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(pkg, cls));

                startActivity(intent);
            }
        });

        ClickUtil.setOnClick(binding.cpvParticleColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setAllowPresets(false)
                        .setDialogId(R.id.cpv_particle_color)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .show(WallPaperSettingActivity.this);
            }
        });

        ClickUtil.setOnClick(binding.cpvLineColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setAllowPresets(false)
                        .setDialogId(R.id.cpv_line_color)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .show(WallPaperSettingActivity.this);
            }
        });

        ClickUtil.setOnClick(binding.cpvBackgroundColor, new Action1() {
            @Override
            public void call(Object o) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setAllowPresets(false)
                        .setDialogId(R.id.cpv_background_color)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .show(WallPaperSettingActivity.this);
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

    private void initView() {
        binding.sbvParticleCount.setCurrentValue(Tools.getShare().getInt(PARTICLE_COUNT, 30));
        binding.sbvParticleCount.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                Tools.getShare().edit().putInt(PARTICLE_COUNT, value).commit();
                return true;
            }
        });

        binding.sbvLineLength.setCurrentValue(Tools.getShare().getInt(LINE_LENGTH, 500));
        binding.sbvLineLength.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                Tools.getShare().edit().putInt(LINE_LENGTH, value).commit();
                return true;
            }
        });

        binding.sbvParticleVelocity.setCurrentValue(Tools.getShare().getInt(PARTICLE_VELOCITY, 500));
        binding.sbvParticleVelocity.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                Tools.getShare().edit().putInt(PARTICLE_VELOCITY, value).commit();
                return true;
            }
        });

        binding.sbvParticleSize.setCurrentValue(Tools.getShare().getInt(PARTICLE_SIZE, 500));
        binding.sbvParticleSize.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                Tools.getShare().edit().putInt(PARTICLE_SIZE, value).commit();
                return true;
            }
        });

        binding.sbvLineThickness.setCurrentValue(Tools.getShare().getInt(LINE_THICK, 500));
        binding.sbvLineThickness.setOnValueSelectedListener(new PersistValueListener() {
            @Override
            public boolean persistInt(int value) {
                Tools.getShare().edit().putInt(LINE_THICK, value).commit();
                return true;
            }
        });

        binding.switchTouchEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Tools.getShare().edit().putBoolean(TOUCH_EFFECT, isChecked).commit();
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
                Tools.getShare().edit().putInt(PARTICLE_COLOR, color).commit();
                break;
            case R.id.cpv_line_color:
                binding.cpvLineColor.setColor(color);
                Tools.getShare().edit().putInt(LINE_COLOR, color).commit();
                break;
            case R.id.cpv_background_color:
                binding.cpvBackgroundColor.setColor(color);
                Tools.getShare().edit().putInt(BACKGROUND_COLOR, color).commit();
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
            String s = getRealPathFromURI(data.getData());
            s = s.replace(Environment.getExternalStorageDirectory().toString(), "");
            Tools.getShare().edit().putString(BACKGROUND_IMAGE_PATH, s).commit();
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