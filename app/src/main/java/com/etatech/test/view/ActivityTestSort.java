package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.UserInfoAdapter;
import com.etatech.test.bean.UserInfo;
import com.etatech.test.databinding.ActivityTestSortBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ListUtils;
import com.etatech.test.utils.StringUtil;
import com.etatech.test.utils.ui.ClickUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.etatech.test.utils.rxbus.Action1;

public class ActivityTestSort extends BaseActivity<ActivityTestSortBinding> {
    private List<UserInfo> userList;
    private List<String>   userListOrigin;
    private List<String>   userListSorted;

    private UserInfoAdapter userInfoAdapter;
    private UserInfoAdapter userInfoAdapterSorted;

    @Override
    public ActivityTestSortBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_sort);
    }

    @Override
    public void init() {
        userInfoAdapter = new UserInfoAdapter();
        userInfoAdapterSorted = new UserInfoAdapter();
        binding.listUserOrigin.setAdapter(userInfoAdapter);
        binding.listUserSorted.setAdapter(userInfoAdapterSorted);
        binding.listUserOrigin.setLayoutManager(new LinearLayoutManager(this));
        binding.listUserSorted.setLayoutManager(new LinearLayoutManager(this));

        initData();
        initClick();
    }

    private void initData() {
        userList = new ArrayList<UserInfo>();
        userListOrigin = new ArrayList<String>();
        userListSorted = new ArrayList<String>();

        UserInfo user1 = null;
        UserInfo user2 = null;
        UserInfo user3 = null;
        UserInfo user4 = null;
        UserInfo user5 = null;
        UserInfo user6 = null;
        UserInfo user7 = null;
        UserInfo user8 = null;
        try
        {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

            user1 = new UserInfo(3, "bbb", formater.parse("19801201"), 1, 1.2f, 'a');
            user2 = new UserInfo(0, "126", formater.parse("19001011"), 03, 3.6f, 'c');
            user3 = new UserInfo(12, "5", formater.parse("19730821"), 15, 9.32f, 'f');
            user4 = new UserInfo(465, "1567", formater.parse("20120126"), 20, 12.56f, '0');
            user5 = new UserInfo(2006, "&4m", formater.parse("20100508"), 100, 165.32f, '5');
            user6 = new UserInfo(5487, "hf67", formater.parse("20161230"), 103, 56.32f, 'm');
            user7 = new UserInfo(5487, "jigg", formater.parse("20001016"), 103, 56.32f, 'm');
            user8 = new UserInfo(5487, "jigg", formater.parse("19870725"), 103, 56.32f, 'm');
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        userList.add(user7);
        userList.add(user8);

        String title = String.format("%s\t%s\t%s\t%s\t%s\t%s",
                                     StringUtil.fillStringLeft("userId", 7, ' '),
                                     StringUtil.fillStringLeft("username", 9, ' '),
                                     StringUtil.fillStringLeft("birthDate", 12, ' '),
                                     StringUtil.fillStringLeft("age", 5, ' '),
                                     StringUtil.fillStringLeft("fRate", 7, ' '),
                                     StringUtil.fillStringLeft("ch" + "", 3, ' '));

        userListOrigin.add(title);
        for (int i = 0; i < userList.size(); i++)
        {
            userListOrigin.add(userList.get(i).toString());
        }
        userInfoAdapter.setUserInfoList(userListOrigin);
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.btnSort1, new Action1() {
            @Override
            public void accept(Object o) {
                sort1();
                refreshList();
            }
        });
        ClickUtil.setOnClick(binding.btnSort2, new Action1() {
            @Override
            public void accept(Object o) {
                sort2();
                refreshList();
            }
        });
        ClickUtil.setOnClick(binding.btnSort3, new Action1() {
            @Override
            public void accept(Object o) {
                sort3();
                refreshList();
            }
        });
        ClickUtil.setOnClick(binding.btnSort4, new Action1() {
            @Override
            public void accept(Object o) {
                sort4();
                refreshList();
            }
        });
        ClickUtil.setOnClick(binding.btnSort5, new Action1() {
            @Override
            public void accept(Object o) {
                sort5();
                refreshList();
            }
        });
        ClickUtil.setOnClick(binding.btnSort6, new Action1() {
            @Override
            public void accept(Object o) {
                sort6();
                refreshList();
            }
        });
    }

    // 按userId升序、username降序、birthDate升序排序
    private void sort1() {
        String[]  sortNameArr = {"userId", "username", "birthDate"};
        boolean[] isAscArr    = {true, false, true};
        ListUtils.sort(userList, sortNameArr, isAscArr);
        binding.tvRule.setText("Sort1排序规则:\n\n按按userId升序、username降序、birthDate升序排序（如果userId相同，则按照username降序,如果username相同，则按照birthDate升序）");
    }

    // 按userId、username、birthDate都升序排序
    private void sort2() {
        ListUtils.sort(userList, true, "userId", "username", "birthDate");
        binding.tvRule.setText("Sort2排序规则:\n\n按userId、username、birthDate排序（如果userId相同，则按照username升序,如果username相同，则按照birthDate升序）");
    }

    // 按userId、username都倒序排序
    private void sort3() {
        ListUtils.sort(userList, false, "userId", "username");
        binding.tvRule.setText("Sort3排序规则:\n\n按userId和username倒序（如果userId相同，则按照username倒序）");
    }

    // 按username、birthDate都升序排序
    private void sort4() {
        ListUtils.sort(userList, true, "username", "birthDate");
        binding.tvRule.setText("Sort4排序规则:\n\n按username、birthDate升序（如果username相同，则按照birthDate升序）");
    }

    // 按birthDate倒序排序
    private void sort5() {
        ListUtils.sort(userList, false, "birthDate");
        binding.tvRule.setText("Sort5排序规则:\n\n按birthDate倒序");
    }

    // 按fRate升序排序
    private void sort6() {
        ListUtils.sort(userList, true, "fRate");
        binding.tvRule.setText("Sort6排序规则:\n\n按fRate升序");
    }

    private void refreshList() {
        userListSorted.clear();
        String title = String.format("%s\t%s\t%s\t%s\t%s\t%s",
                                     StringUtil.fillStringLeft("userId", 7, ' '),
                                     StringUtil.fillStringLeft("username", 9, ' '),
                                     StringUtil.fillStringLeft("birthDate", 12, ' '),
                                     StringUtil.fillStringLeft("age", 5, ' '),
                                     StringUtil.fillStringLeft("fRate", 7, ' '),
                                     StringUtil.fillStringLeft("ch" + "", 3, ' '));

        userListSorted.add(title);
        for (int i = 0; i < userList.size(); i++)
        {
            userListSorted.add(userList.get(i).toString());
        }
        userInfoAdapterSorted.setUserInfoList(userListSorted);
    }
}
