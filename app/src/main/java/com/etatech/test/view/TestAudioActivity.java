package com.etatech.test.view;

import android.Manifest;
import androidx.databinding.DataBindingUtil;
import android.os.Environment;
import android.os.Bundle;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestAudioBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.GameConfig;
import com.etatech.test.utils.SoundManager;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx1.TedRxPermission;

import rx.functions.Action1;

public class TestAudioActivity extends BaseActivity<ActivityTestAudioBinding> {
    private String  rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/001/";
    private boolean soundState;
    private boolean musicState;

    @Override
    public ActivityTestAudioBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_audio);
    }

    @Override
    public void init() {

        if ("on".equals(SPUtils.getInstance().getString(GameConfig.OPERATION_SOUND_SWITCH, "on"))) {
            binding.togglePlaySound.setText("Sound: On");
            soundState = true;
        } else {
            binding.togglePlaySound.setText("Sound: Off");
            soundState = false;
        }

        if ("on".equals(SPUtils.getInstance().getString(GameConfig.MUSIC_BG_SWITCH, "on"))) {
            binding.togglePlayMusic.setText("Music: On");
            musicState = true;
        } else {
            binding.togglePlayMusic.setText("Music: Off");
            musicState = false;
        }

        ClickUtil.setOnClick(binding.togglePlaySound, new Action1() {
            @Override
            public void call(Object o) {
                if (soundState) {
                    binding.togglePlaySound.setText("Sound: Off");
                    SPUtils.getInstance().put(GameConfig.OPERATION_SOUND_SWITCH, "off");
                    soundState = false;
                } else {
                    binding.togglePlaySound.setText("Sound: On");
                    SPUtils.getInstance().put(GameConfig.OPERATION_SOUND_SWITCH, "on");
                    soundState = true;
                }
            }
        });

        ClickUtil.setOnClick(binding.togglePlayMusic, new Action1() {
            @Override
            public void call(Object o) {
                if (musicState) {
                    binding.togglePlayMusic.setText("Music: Off");
                    SPUtils.getInstance().put(GameConfig.MUSIC_BG_SWITCH, "off");
                    musicState = false;
                    SoundManager.getInstance().stopMusic();
                } else {
                    binding.togglePlayMusic.setText("Music: On");
                    SPUtils.getInstance().put(GameConfig.MUSIC_BG_SWITCH, "on");
                    musicState = true;
                }
            }
        });

        ClickUtil.setFastClick(binding.btnPlaySingle, new Action1() {
            @Override
            public void call(Object o) {
                SoundManager.getInstance().playSingle(rootPath + "sound" + ".mp3");
            }
        });

        ClickUtil.setFastClick(binding.btnPlayMulti, new Action1() {
            @Override
            public void call(Object o) {
                SoundManager.getInstance().playMulti(rootPath + "sound" + ".mp3");
            }
        });

        ClickUtil.setFastClick(binding.btnPlayMusic, new Action1() {
            @Override
            public void call(Object o) {
                SoundManager.getInstance().playMusic(rootPath + "music" + ".mp3");
            }
        });

        TedRxPermission.with(this)
                .setDeniedMessage("如果你拒绝权限,将无法播放声音和音乐.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request()
                .subscribe(new Action1<TedPermissionResult>() {
                    @Override
                    public void call(TedPermissionResult tedPermissionResult) {
                        if (tedPermissionResult.isGranted()) {
                            Toast.makeText(TestAudioActivity.this,
                                    "Permission Granted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TestAudioActivity.this,
                                    "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}