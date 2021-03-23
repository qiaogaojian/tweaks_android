package com.etatech.test.java.blockchain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
public class Block {
    public String mPreviousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
    private long mTimeStamp;
    private int nonce;
    public String mCurHash;

    public Block(String previousHash) {
        this.mPreviousHash = previousHash;
        this.mTimeStamp = new Date().getTime();
        this.mCurHash = calculateHashCode();
    }

    public String calculateHashCode() {
        String keepCotent = mPreviousHash + merkleRoot + mTimeStamp + nonce;
        String hashCode = Encrypt.SHA256(keepCotent);
        return hashCode;
    }

    public void mineBlock(int difficulty) {
        merkleRoot = Encrypt.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!mCurHash.substring(0, difficulty).equals(target)) {
            nonce++;
            mCurHash = calculateHashCode();
        }
        System.out.println("Block Mined: " + mCurHash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        if (mPreviousHash != "0") {
            if (transaction.processTransaction() != true) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
