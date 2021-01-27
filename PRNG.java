/*
Author: Scott Tabaka
Instructor: Dr. Mark Hauschild
CmpSci 4732 Summer 2019
Project #2
Due Date: 7/25/2019

Purpose: Tests bad a good PRNGs in relation to PI.
*/

import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;

public class PRNG
{
    private static final int SAMPLESIZE = 100000;

    private static void prngBad()
    {
        Random rand = new Random();

        BigInteger x = BigInteger.valueOf(rand.nextInt(1000));
        BigInteger y = BigInteger.valueOf(rand.nextInt(1000));
        BigInteger a = BigInteger.valueOf(371);
        BigInteger c = BigInteger.valueOf(5);
        BigInteger m = BigInteger.valueOf(1048576);

        int gcd=0;
        double count=0;
        double counttot=0;
        double ratio=0;

        for(int i=0;i<SAMPLESIZE; i++)
        {
            x = ((a.multiply(x)).add(c)).mod(m);
            y = ((a.multiply(y)).add(c)).mod(m);
            gcd = x.gcd(y).intValue();

            if(gcd==1)
            {
                count += 1;
            }
            counttot += 1;
        }

        ratio = Math.sqrt((6.0*counttot)/count);

        System.out.println("Bad LCG PRNG");
        System.out.println("PI          : " + Math.PI);
        System.out.println("PRNG Test   : " + ratio + "\n");
    }

    private static void prngGood()
    {
        int gcd=0;
        double count=0;
        double counttot=0;
        double ratio=0;

        for(int i=0;i<SAMPLESIZE; i++)
        {
            SecureRandom rand = new SecureRandom();

            BigInteger x = BigInteger.valueOf(rand.nextInt(1000000));
            BigInteger y = BigInteger.valueOf(rand.nextInt(1000000));

            gcd = x.gcd(y).intValue();

            if(gcd==1)
            {
                count += 1;
            }
            counttot += 1;
        }

        ratio = Math.sqrt((6.0*counttot)/count);

        System.out.println("Cryptographically Secure PRNG");
        System.out.println("PI          : " + Math.PI);
        System.out.println("PRNG Test   : " + ratio);
    }

    public static void main(String[] args)
    {
        prngBad();
        prngGood();
    }
}
