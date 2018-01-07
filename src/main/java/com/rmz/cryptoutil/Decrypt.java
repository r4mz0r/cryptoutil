package com.rmz.cryptoutil;

/**
 * Created by SBT-Mehdiev-RR on 26.07.2017.
 */
public class Decrypt {

    public Decrypt() {
    }

    public static String Decrypt(String password) {
        String pswd = new CryptoUtil().decrypt(password);
        System.out.println(pswd);
        return pswd;
    }
}
