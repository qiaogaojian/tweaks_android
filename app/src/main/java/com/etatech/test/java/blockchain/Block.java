package com.etatech.test.java.blockchain;

import java.util.Date;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
class Block {
    public String mPreviousHash;
    private String mData;
    private long mTimeStamp;
    private int nonce;
    public String mCurHash;

    public Block(String data, String previousHash) {
        this.mData = data;
        this.mPreviousHash = previousHash;
        this.mTimeStamp = new Date().getTime();
        this.mCurHash = calculateHashCode();
    }

    public String calculateHashCode() {
        String keepCotent = mPreviousHash + mData + mTimeStamp + nonce;
        String hashCode = Encrypt.SHA256(keepCotent);
        return hashCode;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0','0');
        while (!mCurHash.substring(0,difficulty).equals(target)){
            nonce++;
            mCurHash = calculateHashCode();
        }
        System.out.println("Block Mined: " + mCurHash);
    }
}
