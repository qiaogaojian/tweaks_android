package com.etatech.test.java.blockchain;

import com.etatech.test.utils.StringUtil;

import java.security.*;
import java.util.ArrayList;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
public class Transaction {
    public String transactionId;    // 交易的哈希
    public PublicKey sender;        // 发送者公钥
    public PublicKey receiver;      // 接收者公钥
    public float value;
    public byte[] signature;        // 避免其他人动自己的余额

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // 交易数量

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.receiver = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        sequence++; // 自动增加以获得唯一的哈希值
        String keepContent = Encrypt.getStringFromKey(sender) + receiver.toString() + value + sequence;
        return Encrypt.SHA256(keepContent);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = Encrypt.getStringFromKey(sender) + Encrypt.getStringFromKey(receiver) + value;
        signature = Encrypt.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = Encrypt.getStringFromKey(sender) + Encrypt.getStringFromKey(receiver) + value;
        return Encrypt.verifyECDSASig(sender, data, signature);
    }

    //Returns true if new transaction could be created.
    public boolean processTransaction() {
        if (verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        for (TransactionInput i : inputs) {
            i.UTXO = Blockchain.UTXOs.get(i.transactionOutputId);
        }

        if (getInputsValue() < Blockchain.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        float leftOver = getInputsValue() - value;
        transactionId = calculateHash();
        outputs.add(new TransactionOutput(this.receiver, value, transactionId));
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));

        for (TransactionOutput o : outputs) {
            Blockchain.UTXOs.put(o.id, o);
        }

        for (TransactionInput i : inputs) {
            if (i.UTXO == null) {
                continue;
            }
            Blockchain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    //returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; //if Transaction can't be found skip it
            total += i.UTXO.value;
        }
        return total;
    }

    //returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }
}
