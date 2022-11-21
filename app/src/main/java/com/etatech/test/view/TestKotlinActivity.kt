package com.etatech.test.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.etatech.test.R
import com.etatech.test.utils.ui.ClickUtil
import kotlinx.android.synthetic.main.activity_test_kotlin.*

class TestKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_kotlin)

//        btn1.setOnClickListener {
//            Toast.makeText(this, "You click Button 1", Toast.LENGTH_SHORT).show()
//        }
        ClickUtil.setOnClick(btn1) {
            Toast.makeText(this, "You click Button 1", Toast.LENGTH_SHORT).show()
        }
    }


}