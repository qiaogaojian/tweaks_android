package com.etatech.test.bean;

import com.blankj.utilcode.util.StringUtils;
import com.etatech.test.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michael
 * Date:  2020/2/6
 * Func:
 */
public class UserInfo implements java.io.Serializable {

    private static final long serialVersionUID = -3522051445403971732L;

    private Integer userId;
    private String  username;
    private Date    birthDate;
    private Integer age;
    private float   fRate;
    private char    ch;

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBirthDatestr() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(getBirthDate());
    }

    public UserInfo(Integer userId, String username, Date birthDate, Integer age, float fRate, char ch) {
        super();
        this.userId = userId;
        this.username = username;
        this.birthDate = birthDate;
        this.age = age;
        this.fRate = fRate;
        this.ch = ch;
    }

    @Override
    public String toString() {

        return String.format("%s\t%s\t%s\t%s\t%s\t%s", StringUtil.fillStringLeft(userId.toString(), 7, ' '),
                             StringUtil.fillStringLeft(username, 9, ' '),
                             StringUtil.fillStringLeft(getBirthDatestr(), 12, ' '),
                             StringUtil.fillStringLeft(age.toString(), 5, ' '),
                             StringUtil.fillStringLeft(fRate + "", 7, ' '),
                             StringUtil.fillStringLeft(ch + "", 3, ' '));
    }
}
