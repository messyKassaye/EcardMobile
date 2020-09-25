package com.example.foragentss.security;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class CardEncryptor {

    public static String crypto(SecretKey key, String inString, boolean decrypt){
        try{
            Cipher cipher = Cipher.getInstance("DESede");
            byte[] inputByte = inString.getBytes();
            if (decrypt){
                cipher.init(Cipher.DECRYPT_MODE, key);
                return new String (cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return new String (Base64.encode(cipher.doFinal(inputByte), Base64.DEFAULT));
            }
        }catch (Exception e){
            System.out.println("ERRORME: "+e.getMessage());
            return null;
        }
    }
}
