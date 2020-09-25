package com.example.foragentss.security;

import android.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyCastor {

    public static SecretKey convertIntoSecretKey(String text){
        byte[] encodedKey     = Base64.decode(text, Base64.DEFAULT);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DESede");
        return originalKey;
    }
}
