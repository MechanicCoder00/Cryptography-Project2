/*
Author: Scott Tabaka
Instructor: Dr. Mark Hauschild
CmpSci 4732 Summer 2019
Project #2
Due Date: 7/25/2019

Purpose: Measures the time taken to encrypt and decrypt a 1k file and a 1M file using AES with a 128 bit key and a 256 bit key.
*/

import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.io.*;
import java.nio.charset.StandardCharsets;



public class AES
{
    private static SecretKey secretKey;

    public static void setKey(int keyBitSize)
    {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();


            keyGenerator.init(keyBitSize, secureRandom);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private static String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
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

    private static void encrypt_decryptAES(File file, int keyBitSize) throws Exception
    {
        String originalString = fileToString(file);
        setKey(keyBitSize);

        String encryptedString = AES.encrypt(originalString);
        String decryptedString = AES.decrypt(encryptedString);

//        System.out.println(originalString);
//        System.out.println(encryptedString);
//        System.out.println(decryptedString);
    }

    public static void main(String[] args) throws Exception
    {
        long startTime, stopTime, elapsedTime, totalTime = 0;
        double totAvgtime, totalTime2, timesRun2;
        int timesRun = 1000;
        File fileS = new File("./src/smallfile.txt");
        File fileL = new File("./src/largefile.txt");

        for (int i = 0; i < timesRun; i++)
        {
            encrypt_decryptAES(fileS,128);

            startTime = System.currentTimeMillis();
            encrypt_decryptAES(fileS,128);
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("AES 128 bit encrypt/decrypt of 1k file");
        System.out.println("Approximately " + (timesRun2/totAvgtime) + " encryptions/decryptions can be made in 1 sec.");
        totalTime = 0;

        for (int i = 0; i < timesRun; i++)
        {
            encrypt_decryptAES(fileS,256);

            startTime = System.currentTimeMillis();
            encrypt_decryptAES(fileS,256);
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("AES 256 bit encrypt/decrypt of 1k file");
        System.out.println("Approximately " + (timesRun2/totAvgtime) + " encryptions/decryptions can be made in 1 sec.");
        totalTime = 0;

        for (int i = 0; i < timesRun; i++)
        {
            encrypt_decryptAES(fileL,128);

            startTime = System.currentTimeMillis();
            encrypt_decryptAES(fileL,128);
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("AES 128 bit encrypt/decrypt of 1M file");
        System.out.println("Approximately " + (timesRun2/totAvgtime) + " encryptions/decryptions can be made in 1 sec.");
        totalTime = 0;

        for (int i = 0; i < timesRun; i++)
        {
            encrypt_decryptAES(fileL,256);

            startTime = System.currentTimeMillis();
            encrypt_decryptAES(fileL,256);
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
        }
        totalTime2 = totalTime;
        timesRun2 = timesRun;
        totAvgtime = totalTime2 / timesRun2;
        System.out.println("AES 256 bit encrypt/decrypt of 1M file");
        System.out.println("Approximately " + (timesRun2/totAvgtime) + " encryptions/decryptions can be made in 1 sec.");

    }
}
