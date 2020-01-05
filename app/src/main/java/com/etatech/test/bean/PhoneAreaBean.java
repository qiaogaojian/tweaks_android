package com.etatech.test.bean;

import java.util.List;

/**
 * Created by Michael
 * Data: 2020/1/5 15:46
 * Desc: 地区
 */
public class PhoneAreaBean
{
    /**
     * sign : 1
     * data : [{"code":"86","chineseName":"" + SourceProxy.getString(R.string.global_script_name_2_1398) + "","name":"China","symbol":"CN"},{"code":"852","chineseName":"" + SourceProxy.getString(R.string.global_script_name_2_1397) + "","name":"Hong Kong","symbol":"HK"},{"code":"853","chineseName":"" + SourceProxy.getString(R.string.global_script_name_2_1396) + "","name":"Macau","symbol":"MO"},{"code":"886","chineseName":"" + SourceProxy.getString(R.string.global_script_name_2_1395) + "","name":"Taiwan","symbol":"TW"}]
     */

    private String sign;
    private List<DataBean> data;

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public List<DataBean> getData()
    {
        return data;
    }

    public void setData(List<DataBean> data)
    {
        this.data = data;
    }

    public static class DataBean
    {
        /**
         * code : 86
         * chineseName : 中国
         * name : China
         * symbol : CN
         */

        private String code;
        private String chineseName;
        private String name;
        private String symbol;

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getChineseName()
        {
            return chineseName;
        }

        public void setChineseName(String chineseName)
        {
            this.chineseName = chineseName;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getSymbol()
        {
            return symbol;
        }

        public void setSymbol(String symbol)
        {
            this.symbol = symbol;
        }
    }
}

