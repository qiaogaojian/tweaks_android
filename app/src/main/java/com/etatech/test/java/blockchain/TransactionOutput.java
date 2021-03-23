package com.etatech.test.java.blockchain;

import java.security.PublicKey;

/**
 * Created by QiaoGaojian
 * Date:  2021/3/23
 * Desc:
 */
public class TransactionOutput {
    public String id;
    public PublicKey receiver;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey receiver, float value, String parentTransactionId) {
        this.receiver = receiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = Encrypt.SHA256(Encrypt.getStringFromKey(receiver) + value + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == receiver;
    }
}
