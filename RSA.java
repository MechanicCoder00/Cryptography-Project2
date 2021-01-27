/*
Author: Scott Tabaka
Instructor: Dr. Mark Hauschild
CmpSci 4732 Summer 2019
Project #2
Due Date: 7/25/2019

Purpose: Measures the time taken to encrypt and decrypt a 1k file and a 1M file using RSA encryption algorithm.
*/

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.security.SecureRandom;
import java.security.*;


public class RSA
{
    private static KeyPair keyPair;
    private static final int BLOCKSIZE = 200;
    private static int decryptBlockSize = 0;

    private static String encrypt(String strToEncrypt) throws IOException
    {
        String[] myArray;
        myArray = stringSplit(strToEncrypt, BLOCKSIZE);
        try
        {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048,secureRandom);
            keyPair = keyPairGenerator.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            for (int i=0;i<myArray.length;i++)
            {

                myArray[i] = Base64.getEncoder().encodeToString(cipher.doFinal(myArray[i].getBytes(StandardCharsets.UTF_8)));
            }
            decryptBlockSize = myArray[0].length();
            return String.join("", myArray);
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    private static String decrypt(String strToDecrypt) throws IOException
    {
        String[] myArray;
        myArray = stringSplit(strToDecrypt, decryptBlockSize);
        try
        {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            for (int i=0;i<myArray.length;i++)
            {
                myArray[i] = new String(cipher.doFinal(Base64.getDecoder().decode(myArray[i])));
            }
            return String.join("", myArray);
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static String fileToString(File file) throws Exception
    {
        BufferedReader br=new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        builder.append(br.readLine());
        while((br.readLine())!=null)
        {
            builder.append(br.readLine());
        }
        return builder.toString();
    }

    private static void encrypt_decryptRSA(File file) throws Exception
    {
        String originalString = fileToString(file);
//        System.out.println("\nMy Message: " + originalString);
        String encryptedString = encrypt(originalString);
        String decryptedString = decrypt(encryptedString);
//        System.out.println("My decrypted Message: " + decryptedString);
    }

    private static String[] stringSplit(String original,int blocks) throws IOException
    {
        int strlen=original.length();
        int nelements;
        if((strlen%blocks)==0)
        {
            nelements = (strlen/blocks);
        }
        else
        {
            nelements = ((strlen/blocks)+1);
        }
        String[] myArray = new String[nelements];
        ByteArrayInputStream bis = new ByteArrayInputStream(original.getBytes());
        byte[] buffer = new byte[blocks];
        String result;
        int i=0;
        while ((bis.read(buffer)) > 0)
        {
            result = "";
            for (byte b : buffer) {
                result += (char) b;
            }
            Arrays.fill(buffer, (byte) 0);
            myArray[i] = result;
            i++;
        }
        return myArray;
    }


    public static void main(String[] args) throws Exception
    {
        long startTime, stopTime, elapsedTime, totalTime = 0;
        double totAvgtime, totalTime2, timesRun2;
        int timesRun = 10;
        File fileS = new File("./src/smallfile.txt");
        File fileL = new File("./src/largefile.txt");

        for (int i = 0; i < timesRun; i++)
        {
            startTime = System.currentTimeMillis();

            encrypt_decryptRSA(fileS);

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("RSA encrypt/decrypt of 1k file");
        System.out.println("Total time(ms):" + totalTime + " || Times run:" + timesRun + " || Average time(ms):" + totAvgtime);

        totalTime = 0;

        for (int i = 0; i < timesRun; i++)
        {
            startTime = System.currentTimeMillis();

            encrypt_decryptRSA(fileL);

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("RSA encrypt/decrypt of 1M file");
        System.out.println("Total time(ms):" + totalTime + " || Times run:" + timesRun + " || Average time(ms):" + totAvgtime);
    }
}
