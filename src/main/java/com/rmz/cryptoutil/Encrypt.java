package com.rmz.cryptoutil;

/**
 * Created by SBT-Mehdiev-RR on 26.07.2017.
 */
public class Encrypt {

    public Encrypt() {
    }

    public static String Encrypt(String password) {
        String pswd = new CryptoUtil().encrypt(password);
        System.out.println(pswd);
        return pswd;
    }
}
