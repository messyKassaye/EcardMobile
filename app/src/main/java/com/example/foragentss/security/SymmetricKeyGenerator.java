package com.example.foragentss.security;


import android.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricKeyGenerator {

    public static String generateKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(168);
            SecretKey secretKey= keyGenerator.generateKey();
            String stringKey = null;
            if (secretKey != null) {
                stringKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
            }
            return stringKey;
        }catch (Exception e){
            System.out.println("ERRRO: "+e.getMessage());
            return null;
        }
    }
}
