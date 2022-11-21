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

import java.util.*;
import javax.crypto.*;
import java.security.*;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

// This class is going to RSA and with public and private key. It will encrypted or decrypted the message. Most of input and out are string. There are helplers
// functions to get right data. 
public class Final_RSA {

    private KeyPairGenerator keyGen;
    private KeyPair createKey;
 
    // container 
    Final_RSA() 
    {
        keyGen = null;
        createKey = null;
    }

    // It has to input name of method the user going to use and size of key. It is set as 2048 bit. 
    public Final_RSA(String type, int size) throws NoSuchAlgorithmException{
        this.keyGen = KeyPairGenerator.getInstance(type);
        this.keyGen.initialize(size);
        // it creates key
        createKey = this.keyGen.generateKeyPair();
    }

    // bob publ key is string 
    // encrypt(sercert key from Abe, Public key from Bob)
    public byte[] getEncrypt(String ras_Abe_Key, String publicKeyBob) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, 
    NoSuchPaddingException, NoSuchAlgorithmException {
        
        //Using RSA with padding.
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Since cipher.init cannot take a string. It has to change back to publickey.  
        cipher.init(Cipher.ENCRYPT_MODE, changeBackToKey(publicKeyBob));
    
        return cipher.doFinal(ras_Abe_Key.getBytes());
    }


    // this function will change the public key string to form X509 and return publcKey
    private PublicKey changeBackToKey(String unChangepublicKey){
        PublicKey publicKey = null;

        try{

            // creating x509 object and  change string to X509
            X509EncodedKeySpec x509Obj = new X509EncodedKeySpec(Base64.getDecoder().decode(unChangepublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // input data input public key. 
            publicKey = keyFactory.generatePublic(x509Obj);

            return publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
    
    // EncrptMessage is decrtypted the message with private key that provided. 
    public String getDecrypt(String encryp_RAS_Key, String privateBobKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
     NoSuchAlgorithmException, NoSuchPaddingException
    {
        // String needs to change back to byte. 
        byte[] changToByteKey = Base64.getDecoder().decode(encryp_RAS_Key.getBytes());

        // Using functions to return Decrypt into a string
        return changeToStringDecrypt(changToByteKey, changeToPrivateKey(privateBobKey));
    }

    // using this function to change return  a string to for message. 
    private String changeToStringDecrypt(byte[] key, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
    BadPaddingException, IllegalBlockSizeException {

        // Get cipher to Decrypt the privatkey to string. 
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(key));
    }

    // Since the string need to change back PKCS8, using this function to change it. 
    private PrivateKey changeToPrivateKey(String privateBobKey){

        PrivateKey changePrivateKey = null;

        // PKCS8 object created.
        PKCS8EncodedKeySpec pkcs8Obj= new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateBobKey.getBytes()));
        KeyFactory keyFactory = null;
        
        try {
            // type of key.
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            // geting private key.
            changePrivateKey = keyFactory.generatePrivate(pkcs8Obj);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return changePrivateKey;
    }

    // get each public key for Abe and Bob. (Format: PKCS#8)
    public Key getPubKey(){
        return createKey.getPublic();
    }

    // get each private key for Abe and Bob. (Format: X.509)
    public Key getPriKey(){
        return createKey.getPrivate();
    }

}
    