package com.etatech.test.java.blockchain;

import com.google.gson.GsonBuilder;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */

public class Blockchain {
    public static int difficulty = 6;
    public static float minimumTransaction = 0.001f;
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String args[]) {
        //Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(Encrypt.getStringFromKey(walletA.mPrivateKey));
        System.out.println(Encrypt.getStringFromKey(walletA.mPublicKey));
        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.mPublicKey, walletB.mPublicKey, 5, null);
        transaction.generateSignature(walletA.mPrivateKey);
        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());
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