package com.etatech.test.view;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestWheelPickerBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.wheelpicker.RecyclerWheelPicker;
import com.etatech.test.wheelpicker.TextViewWheelAdapter;
import com.etatech.test.wheelpicker.LinearLayoutX;

import rx.functions.Action1;

public class TestWheelPickerActivity extends BaseActivity<ActivityTestWheelPickerBinding> {

    @Override
    public ActivityTestWheelPickerBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_test_wheel_picker);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnWheelpicker1, new Action1() {
            @Override
            public void call(Object o) {
                textPicker();
            }
        });
        ClickUtil.setOnClick(binding.btnWheelpicker2, new Action1() {
            @Override
            public void call(Object o) {
                textPicker_BottomSheetDialog(binding.btnWheelpicker2);
            }
        });
        ClickUtil.setOnClick(binding.btnWheelpicker3, new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }

    private String selected1 = "text_0";
    Toast toast;

    public void textPicker() {
        RecyclerWheelPicker<String> picker = new RecyclerWheelPicker(this);
        picker.setMaxShowSize(3);
        picker.setOrientation(RecyclerWheelPicker.HORIZONTAL);
        picker.setSelectedAreaHeight(100);
        picker.setAdapter(new TextViewWheelAdapter<String>() {
            @Override
            protected String getWheelItemName(int position, String s) {
                return s;
            }

            @Override
            protected int getPositionByValue(String s) {
                return Integer.valueOf(s.replace("text_", ""));
            }

            @Override
            protected void onWheelSelected(RecyclerView.ViewHolder holder, int position, String s) {
                selected1 = s;
            }

            @Override
            protected String getWheelItemData(int position) {
                return "text_" + position;
            }

            @Override
            protected int getWheelItemCount() {
                return 10000;
            }
        });
        picker.setDefaultValue(selected1);
        new AlertDialog.Builder(this)
                .setView(picker).create().show();
    }

    private RecyclerWheelPicker<String> createTextPickerSimple() {
        RecyclerWheelPicker<String> picker = new RecyclerWheelPicker(this);
        picker.setMaxShowSize(7);
        picker.setOrientation(RecyclerWheelPicker.HORIZONTAL);
        picker.setSelectedAreaHeight(100);
        picker.setAdapter(new TextViewWheelAdapter<String>() {
            @Override
            protected String getWheelItemName(int position, String s) {
                return s;
            }

            @Override
            protected int getPositionByValue(String s) {
                return Integer.valueOf(s.replace("text_", ""));
            }

            @Override
            protected void onWheelSelected(RecyclerView.ViewHolder holder, int position, String s) {
                selected1 = s;
            }

            @Override
            protected String getWheelItemData(int position) {
                return "text_" + position;
            }

            @Override
            protected int getWheelItemCount() {
                return 10000;
            }
        });
        picker.setDefaultValue(selected1);
        return picker;
    }


    public void textPicker_BottomSheetDialog(final View v) {
        RecyclerWheelPicker<String> picker0 = createTextPickerSimple();
        RecyclerWheelPicker<String> picker1 = createTextPickerSimple();
        LinearLayout rootLayout = new LinearLayout(this);
        LinearLayoutX linearLayout = new LinearLayoutX(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(picker0, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT) {
            {
                this.weight = 1;
            }
        });
        linearLayout.addView(picker1, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT) {
            {
                this.weight = 1;
            }
        });
        rootLayout.addView(new AppCompatTextView(this) {
                               {
                                   setText(((Button) v).getText().toString());
                                   setGravity(Gravity.CENTER);
                                   setPadding(20,20,20,20);
                               }
                           }, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        rootLayout.addView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(rootLayout);
        dialog.show();
    }

    public void imagePicker(View v) {
        toast.setText("可以实现的，不想写了，太懒了~_~");
        toast.show();
    }


    public void emojiPicker(View v) {
        toast.setText("可以实现的，不想写了，太懒了~_~");
        toast.show();
    }

    public void multiLayoutPicker(View v) {
        toast.setText("可以实现的，不想写了，太懒了~_~");
        toast.show();
    }


}
