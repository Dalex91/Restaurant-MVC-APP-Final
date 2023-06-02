package com.restaurant_mvc.app.utils;

import javax.swing.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utils {

    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.MD5_HASH);
            byte[] messageDigest = md.digest(input.getBytes());
            String hashText = (new BigInteger(1, messageDigest)).toString(16);
            while (hashText.length() < Constants.MD5_BITS_LIMIT)
                hashText = Constants.ZERO + hashText;
            return hashText;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateFieldsNotEmpty(JTextField... textFields) {
        for (JTextField textField : textFields) {
            if (textField.getText().trim().isEmpty())
                return false;
        }
        return true;
    }
}
