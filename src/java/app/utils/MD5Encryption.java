/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author admin
 */
public class MD5Encryption {

    public String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            password = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }

    public boolean verifyPassword(String inputPassword, String hashPassWord) {
        boolean result = false;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputPassword.getBytes());
            byte[] digest = md.digest();
            String myChecksum = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            result = hashPassWord.equals(myChecksum);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static void main(String[] args) {
        MD5Encryption md = new MD5Encryption();
        System.out.println(md.encryptPassword("@Zinaida123"));
    }
}
