package com.etatech.test.java.blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */

public class Blockchain {
    public static int difficulty = 6;
    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String args[]) {
        //add our blocks to the blockchain ArrayList:
        blockchain.add(new Block("Hi im the first block", "0"));
        System.out.println("Tring to Mine block 1...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).mCurHash));
        System.out.println("Tring to Mine block 2...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).mCurHash));
        System.out.println("Tring to Mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe Blockchar: ");
        System.out.println(blockchainJson);

    }

    public static boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.mCurHash.equals(currentBlock.calculateHashCode())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            if (!previousBlock.mCurHash.equals(currentBlock.mPreviousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.mCurHash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}