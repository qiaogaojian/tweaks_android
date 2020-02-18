package com.etatech.test.bean;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class BtcPriceBean {


    /**
     * bid : 9720.05
     * ask : 9724.33
     * volume : {"BTC":"1118.954712312","USD":"10814718.474440210719","timestamp":1581996000000}
     * last : 9720.04
     */

    private float bid;         // 成交
    private float ask;         // 最高
    private VolumeBean volume;  // 量能
    private float last;        // 最低

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public VolumeBean getVolume() {
        return volume;
    }

    public void setVolume(VolumeBean volume) {
        this.volume = volume;
    }

    public float getLast() {
        return last;
    }

    public void setLast(float last) {
        this.last = last;
    }

    public static class VolumeBean {
        /**
         * BTC : 1118.954712312
         * USD : 10814718.474440210719
         * timestamp : 1581996000000
         */

        private String BTC;
        private String USD;
        private long timestamp;

        public String getBTC() {
            return BTC;
        }

        public void setBTC(String BTC) {
            this.BTC = BTC;
        }

        public String getUSD() {
            return USD;
        }

        public void setUSD(String USD) {
            this.USD = USD;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
