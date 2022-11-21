/**
 * Name:        You, Jin Young You 
 * ID:          013361049
 * Professr:    Dr: Tingting Chen 
 * Class:       4600.01
 * Project:     Final Project. 
 * 
 * Due Date:    December 11,2020
 */

 /*         
            In Abe_main.java, Abe is a sender. It will send encrypted message, and different kind of key like AES and MAC. First, Abe needs to create a message. After that using AES with the message 
        and Abe's own private key for AES. It will encrypted the message with the key. With Bob's public key, it create encrypted key and also input AES_key so that it can send it 
        safely without attackers might see the key. When it get encrpyted message and key, it will use MAC to authenticate the whole thing. MAC needs to use a key, but MAC_key will share
        in the public. Fianlly, it will save on to Transmitted_Data.txt.

 *      *********************************                      PLEASE RUN THE PROGRAM IN ORDER.                                    ***********************************************
 *      *********************************                               1. main.java                                               ***********************************************
 *      *********************************                               2. Abe_main.java                                           ***********************************************
 *      *********************************                               3. Bob_main.java                                           ***********************************************
 */
 
package Abe;

import java.util.*;
import javax.crypto.*;
import java.security.*;
import cryptosystem_method.*;
import java.io.File;  
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Abe_main {

    final static int SIZE_NUMBER = 2048;
    public static void main(String[] args) throws Exception, NoSuchAlgorithmException {
        
        // It already have the public key from Bob and a private key for Abe himself by runing Main. Also it has already have own messaga that would sent to Bob. (RSA)

        //get Bob's public key from the folder
        Scanner path_GetBobPubKey = new Scanner(new File("C:\\Users\\John\\Desktop\\Final project\\Abe\\publiKeyFromBob.pub"));
        String getBobPubKey = readText(path_GetBobPubKey);

        // get the normal message from Abe. 
        Scanner scAbe = new Scanner(new File("C:\\Users\\John\\Desktop\\Final project\\Abe\\AbeMessage.txt"));
        String textFromAbe = readText(scAbe);
 

        //Creating AES object. It will need a message and key which output encrypted message.
        Final_AES Abe_AES = new Final_AES();

        //It is a secert key for AES. 
        // I might need to chang eht abe_Sercert key (maybe save). Also this can be my weakness of code. The attacker can see this because it is easy string.
        String keyFor_AES = "brother";

        System.out.println("===========================================================================\n");
        //Encrypted the message with AES key. It will give out encrypted message. 
        String encr_Message_AES_Abe = Abe_AES.encrypt(textFromAbe, keyFor_AES);
        System.out.println("This is AES encrpted message by using Abe's message and AES key:");
        System.out.println(encr_Message_AES_Abe);

        //Using RSA to encryp the key. The function required AES key and Bob's public key. 
        Final_RSA create_Key_RSA = new Final_RSA("RSA", SIZE_NUMBER);

        //Chaing into Base64 byte to string. 
        String encryp_RSA_Key = Base64.getEncoder().encodeToString(create_Key_RSA.getEncrypt(keyFor_AES, getBobPubKey));
        System.out.println("Encryp RSA Key:");
        System.out.println(encryp_RSA_Key);
        System.out.println("===========================================================================\n");

        
        //Using MAC to authenticate. Abe and Bob will use same the encrypted message and MAC key in order to do MAC. 

        //Creating an object for MAC. 
        Final_MAC abe_Mac = new Final_MAC();

        //this is a key for MAC which it will be in public. 
        String keyFor_MAC = "letterforyou";

        //combin the message and the RAS key in one string. With this string, it will input as MAC. 
        String inputStingForMAC  = encr_Message_AES_Abe + encryp_RSA_Key;

        //MAC object will output a final fianl string.
        String final_MAC_ABE = abe_Mac.inputFile(inputStingForMAC, keyFor_MAC);


        // In this part, it will write all data from this program and input in Transmitted_Data.txt. The fucintion (writeFiles) agruments are going to be like this:
        /*
            Each line is going to be in this order. 

            1. Encrypted RSA Message:       encr_Message_AES_Abe        (using AES)
            2. Encrypted RSA Key:           encryp_RSA_Key              (keyFor_AES, Bob's public key)
            3. MAC key:                     keyFor_MAC                  (created by Abe in this main)
            4. Input String for MAC:        inputStingForMAC            (encr_Message_AES_Abe + encryp_RSA_Key;)
            5. OutputMAC string:            final_MAC_ABE               (inputStingForMAC + keyFor_MAC)

         */

        // PLEASE CHANGE THE PATH IF YOU WANT TO RUN THIS PART. 
        String pathForTransmitted_Data = "C:\\Users\\John\\Desktop\\Final project\\Transmitted_Data.txt";
        writeFiles(pathForTransmitted_Data, encr_Message_AES_Abe, encryp_RSA_Key, keyFor_MAC, inputStingForMAC, final_MAC_ABE);

    }

    // In this fucntion will read the text input in a string. 
    public static String readText(Scanner sc)
    {
        List<String> lines = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        int i = 0;
        while (i < lines.size() - 1) {
            sb.append(lines.get(i));
            i++;
        }
        sb.append(lines.get(i));
 
        String res = sb.toString();

        return res;
    }

    // input text files into Transmitted_Data.txt
    public static void writeFiles(String path, String enc_AES_Message, String enc_String_Key,
        String key_MAC, String inputStingForMAC, String mac_Out_Come)
    {
        // the path ot Transmitted_Data.txt so that it can input encrpted the message.
        try {
            
            // Writer files. 
             FileWriter myWriter = new FileWriter(path);
             myWriter.write(enc_AES_Message);
             myWriter.write("\n");
             myWriter.write(enc_String_Key);
             myWriter.write("\n");
             myWriter.write(key_MAC);
             myWriter.write("\n");
             myWriter.write(inputStingForMAC);
             myWriter.write("\n");
             myWriter.write(mac_Out_Come);
             myWriter.close();


          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          
    }
}