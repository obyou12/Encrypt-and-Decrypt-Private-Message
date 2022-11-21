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
import java.security.Key;
import java.util.Base64;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.IOException; 
import java.util.Arrays;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;

// This class is about MAC and it will input user's messagea and key from the sender. With those things, it will ecrypyed so that Bob would know
// if the message got attack or not. Using MAC to authenticate
public class Final_MAC
{
    private KeyGenerator keyGener; 
    private SecretKeySpec secertKey;
    private Key stringKey; 
    private Mac mac_method;
    private byte[] msg; 

    // It is using DES and Hash sha256 with MAC> 
    public Final_MAC() throws NoSuchAlgorithmException{

        this.keyGener = KeyGenerator.getInstance("DES");
        this.stringKey = keyGener.generateKey();
        this.mac_method = Mac.getInstance("HmacSHA256");
        this.msg = null; 
    }

    public byte[] getMsgInByte()
    {
        return msg;
    }

    // this is main input messagea and key from Abe and Bob. It will use AES to encrypted it. 
    public String inputFile(String message, String key_MAC) throws UnsupportedEncodingException,Exception
    {
        MessageDigest sha = null; 
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        try
        {
            msg = key_MAC.getBytes("UTF-8");
            // since SHA needs to 256. 
            sha = MessageDigest.getInstance("SHA-256");
            msg = Arrays.copyOf(sha.digest(msg), 16);
            secertKey = new SecretKeySpec(Arrays.copyOf((sha.digest(msg)), 16), "AES");

            // testing 
            //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secertKey);
        }
        catch(NoSuchAlgorithmException e){

            e.printStackTrace();
        }

        // using key and mac to get answers. 
        mac_method = null;

        try{
            // using Hash SHA 256. 
            mac_method = Mac.getInstance("HmacSHA256");
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        try{
            // init into MAC
            mac_method.init(secertKey);
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        // storge outcome in bytes array
        byte[] bytes = message.getBytes();      
        byte[] macResult = mac_method.doFinal(bytes);
  
        // change back to string. 
        return new String((Base64.getEncoder().encode(macResult)));

    }
}