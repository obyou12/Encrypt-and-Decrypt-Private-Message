/**
 * Name:        You, Jin Young You 
 * ID:          013361049
 * Professr:    Dr: Tingting Chen 
 * Class:       4600.01
 * Project:     Final Project. 
 * 
 * Due Date:    December 11,2020
 */

package cryptosystem_method;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// Final_AES is going to get data and key to get encrypted and decrypted fucntions. 
public class Final_AES {

    // Decration for secret key and an array for key.
    private static SecretKeySpec secretKey;
    private static byte[] key;

 
    // This fucnction will get a message and public key from the user. It will change message into encrypted message by using user's key.
    public String encrypt(String message, String publicKey) 
    {
        try
        {
            // The data is changed vy using this fucntion so that Cipher object can be use. 
            setKeyValue(publicKey);

            // Created cipher object and it is using AES and typed is PKCS5. 
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Change to back to string by using Base64. It is UTF-8 formate so it is change back to string.
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
        } 
        // in case, it doesn't work.
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    // It will decrypted the message with private key. 
    public String decrypt(String message, String rsa_key) 
    {
        try
        {
             // The data is changed vy using this fucntion so that Cipher object can be use
            setKeyValue(rsa_key);

             // Created cipher object and it is using AES and typed is PKCS5
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // return into the data. 
            return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
        } 
        // if something went wrong, it will give out an error. 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

        // This fucntion will get input from the user's key into  sercrte key. SecretKeySpec cannot pass just string; therefore, it has to change string to different data type.
        public void setKeyValue(String userKey) 
        {
            MessageDigest sha_1 = null;
            try {
                // Changing to UTF-8 user's data.
                key = userKey.getBytes("UTF-8");
    
                // It is using SHA-1 method for user. It is secure hash algorithm 1. It produces hash value. 40 bits long.
                sha_1 = MessageDigest.getInstance("SHA-1");
                key = sha_1.digest(key);
                key = Arrays.copyOf(key, 16); 
                
                // with SHA-1 key and int AES. 
                secretKey = new SecretKeySpec(key, "AES");
            } 
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } 
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    
}