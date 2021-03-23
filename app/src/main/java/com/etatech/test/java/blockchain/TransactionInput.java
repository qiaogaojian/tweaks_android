package com.etatech.test.java.blockchain;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
public class TransactionInput {
    public String transactionOutputId;      // TransactionOutputs -> transactionId
    public TransactionOutput UTXO;          // Contains the Unspent transaction output

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}
