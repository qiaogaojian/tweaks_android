package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestSchemeBinding;
import com.etatech.test.utils.BaseActivity;

public class TestSchemeActivity extends BaseActivity<ActivityTestSchemeBinding> {
    private String TAG = "TestSchemeActivity";

    @Override
    public ActivityTestSchemeBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_scheme);
    }

    @Override
    public void init() {
        Uri data = getIntent().getData();
        String scheme = "host = " + data.getHost() + " path = " + data.getPath() + " query = " + data.getQuery();
        binding.tvParam.setText(scheme + logScheme(data));
        Log.e(TAG, scheme + logScheme(data));
    }

    private String logScheme(Uri uri) {
        StringBuilder sb = new StringBuilder();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            sb.append("\n\n url:\t\t " + url);
            // scheme部分
            String scheme = uri.getScheme();
            sb.append("\n scheme:\t\t " + scheme);

            // host部分
            String host = uri.getHost();
            sb.append("\n host:\t\t " + host);

            //port部分
            int port = uri.getPort();
            sb.append("\n port:\t\t " + port);

            // 访问路劲
            String path = uri.getPath();
            sb.append("\n path:\t\t " + path);

            // Query部分
            String query = uri.getQuery();
            sb.append("\n query:\t\t " + query);

            //获取指定参数值
            String type = uri.getQueryParameter("type");
            sb.append("\n type:\t\t " + type);
            String buffer = uri.getQueryParameter("buffer");
            sb.append("\n buffer:\t\t " + buffer);
        }
        return sb.toString();
    }
}