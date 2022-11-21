/**
 * Name:        You, Jin Young You 
 * ID:          013361049
 * Professr:    Dr: Tingting Chen 
 * Class:       4600.01
 * Project:     Final Project. 
 * 
 * Due Date:    December 11,2020
 */

 /**
 *          This main is like a public server who gives out public and private keys. By running this main.java, it send out Abe (sender) and Bob (receiver) to public and private key.
 *      For public key, Abe will have Bob's public key and Bob will have Abe's public key as well. However, they will have own private key which they would not share with no one else. 
 *      Lastly, Abe (sender) is sending a message to Bob. 
 *          ALSO, PLEASE CHECK PATH OF FOLDERS AND FILES BECAUSE IT IS SET AS MY OWN COMPUTER.
 *          
 * 
 *      ********************************* PLEASE, YOU HAVE TO RUN THIS MAIN FIRST WITHOUT PUBLIC AND PRIVATE KEYS, IT WILL NOT RUN.***********************************************
 *      *********************************                   THIS IS ORDER HOW TO RUN THIS WHOLE PROGRAM.                           ***********************************************
 *      *********************************                               1. main.java                                               ***********************************************
 *      *********************************                               2. Abe_main.java                                           ***********************************************
 *      *********************************                               3. Bob_main.java                                           ***********************************************
 */

 
import java.util.*;
import javax.crypto.*;
import java.security.*;
import cryptosystem_method.*;
import java.io.File;  
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;

public class main {

    // Use a key 2048 because it is recommended key size of SSL.
    final static int SIZE_NUMBER = 2048;
    public static void main(String[] args) throws Exception{
        
        //1. The two parties have each other’s RSA public and private key by making objects RSA. 
        Final_RSA AbeRSA = new Final_RSA("RSA", SIZE_NUMBER);
        Final_RSA BobRSA = new Final_RSA("RSA", SIZE_NUMBER);

        
        //2.  Base64 can change byte array into a string. Also public key is X509 form and private key is PKCS8 form.
        String abePublKey = Base64.getEncoder().encodeToString(AbeRSA.getPubKey().getEncoded());
        String abePrivKey = Base64.getEncoder().encodeToString(AbeRSA.getPriKey().getEncoded());
        String bobPublKey = Base64.getEncoder().encodeToString(BobRSA.getPubKey().getEncoded());
        String bobPrivKey = Base64.getEncoder().encodeToString(BobRSA.getPriKey().getEncoded());

        //3. Exchange their public key each other by saving on their on foler.

        writeFiles("Abe", "publiKeyFromBob", "pub", bobPublKey);
        writeFiles("Bob", "publiKeyFromAbe", "pub", abePublKey);

        // Making a filer for Abe and Bob private keys 
        writeFiles("Abe", "PrivateKeyAbe", "key", abePrivKey);
        writeFiles("Bob", "PrivateKeyBob", "key", bobPrivKey);
    
    }

    // This writeFiles function is going to create a files for public and private key and save on to each folder. 
    // Also, this function required author of folder, name of file , and what kind of type for file. 
    public static void writeFiles(String authorOfFolder, String nameOfFile, String typeFiles , String key)
    {
        // PLEASE, IF YOU WANT TO CREATE ON FILS, YOU HAVE TO CHANGE THE PATH. 
        String pathForFolder = "C:\\Users\\John\\Desktop\\Final project\\" + authorOfFolder + "//" + nameOfFile + "." + typeFiles;

        try {

            // creating file
            File myObj = new File(pathForFolder);

            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }

            // Writer files. 
            FileWriter myWriter = new FileWriter(pathForFolder);

            myWriter.write(key);
            myWriter.close();

          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}