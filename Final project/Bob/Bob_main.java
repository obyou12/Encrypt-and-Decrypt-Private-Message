/**
 * Name:        You, Jin Young You 
 * ID:          013361049
 * Professr:    Dr: Tingting Chen 
 * Class:       4600.01
 * Project:     Final Project. 
 * 
 * Due Date:    December 11,2020
 */

/***
 *          This is Bob_main.java and he is a receiver. Bob get data from Transmitted_Data.txt and it was send by Abe. Bob_main.java is going to decrypted all of information that he got from 
 *      Abe. First, it is going to check authenticate by using MAC. MAC key and message from Abe and it is using same MAC class in order to check if there are nothing wrong with the message. 
 *      After, Bob needs to get AES_key in order to open encrpyted message from Abe. By using encrypted RSA key from Transmitted_Data.txt and using Bob's own private key to run RSA. Finally,
 *      RSA will out decrypted key. With the key, it use AES object to dencrpyed the message. 


 *      *********************************                      PLEASE RUN THE PROGRAM IN ORDER.                                    ***********************************************
 *      *********************************                               1. main.java                                               ***********************************************
 *      *********************************                               2. Abe_main.java                                           ***********************************************
 *      *********************************                               3. Bob_main.java                                           ***********************************************
 */

 

package Bob;

import java.util.*;

import javax.annotation.processing.FilerException;
import javax.crypto.*;
import java.security.*;
import cryptosystem_method.*;
import java.io.File;  // Import the File class
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;

public class Bob_main {
    public static void main(String[] args) throws Exception, NoSuchAlgorithmException
    {

        // Creating Map object. It is easy to storage in with speical key which is the name of a string and value is save in string. 
        Map<String, String> saveTransData_Map = new HashMap<String,String>();

        //1. Read the from Transmitted_Data.txt and it will input data into map so that it can easy to get the data. 
        Scanner sc = new Scanner(new File("C:\\Users\\John\\Desktop\\Final project\\Transmitted_Data.txt"));
        savingDataToMap(sc, saveTransData_Map);
        
        //2. Check if hash is same as before. 
        Final_MAC bob_Mac = new Final_MAC();

        // Checking authentication by using MAC. 
        System.out.println("Is this key and message from Abe by using MAC? " + CheckingData(bob_Mac, saveTransData_Map));

        //3. It is creating RSA with 2048 bits. 
        Final_RSA getBob_Key = new Final_RSA("RSA", 2048);

        // Get Bob's own private key from his folder. 
        Scanner pathPrivateKey = new Scanner(new File("C:\\Users\\John\\Desktop\\Final project\\Bob\\PrivateKeyBob.key"));
        String saveBobPrivateKey = pathPrivateKey.nextLine();

        // Get RSA_key by using RSA decrypted with RSA key and Bob's private key.
        String finalRSA_key = getBob_Key.getDecrypt(saveTransData_Map.get("encryp_RSA_Key"), saveBobPrivateKey);
        System.out.println("This is RSA key for Abe: " + finalRSA_key);

        //4. Create AES and decrypted with encrypted message and a final RSA key.
        Final_AES bob_AES = new Final_AES();
        String messageFromAbe = bob_AES.decrypt(saveTransData_Map.get("encr_Message_AES_Abe"), finalRSA_key);
        System.out.println("This is the message from Abe: " + messageFromAbe);

    }

    // This function will pass Map object and get transmitted data from Abe. 
    public static void savingDataToMap(Scanner sc, Map<String,String> tranData_Map)
    {
        tranData_Map.put("encr_Message_AES_Abe",sc.nextLine());
        tranData_Map.put("encryp_RSA_Key",sc.nextLine());
        tranData_Map.put("keyFor_MAC",sc.nextLine());
        tranData_Map.put("inputStingForMAC",sc.nextLine());
        tranData_Map.put("final_MAC_ABE",sc.nextLine());

    }

    // This function will tell if the message got hacked or not by using MAC. 
    public static boolean CheckingData(Final_MAC macObje, Map<String,String> transData) throws Exception
    {
        String outputMac = macObje.inputFile(transData.get("inputStingForMAC"), transData.get("keyFor_MAC"));

        if(outputMac.equals(transData.get("final_MAC_ABE")))
            return true;

        return false;
    }
}