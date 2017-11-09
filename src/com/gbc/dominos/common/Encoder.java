/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.common;

/**
 *
 * @author diepth
 */
public class Encoder {
    
    private static class permut {
        public int src;
        public int dst;
        
        public permut(int src, int dst) {
            this.src = src;
            this.dst = dst;
        }
    }
     
    private static final permut[] permut1 = {  new permut(0, 9), new permut(1, 3), new permut(2, 7), 
                                        new permut(4, 11), new permut(5, 6), new permut(8, 10),
                                        new permut(0, 0)};
    private static final permut[] permut2 = {  new permut(0, 5), new permut(1, 4), new permut(2, 3), 
                                        new permut(6, 10), new permut(7, 8), new permut(9, 11),
                                        new permut(0, 0)};
    
    
    private static final int[] key = {0,3,8,2,7,9,5,6,1,4};
    
    private static void permute12(char[] str, boolean en) {
    
        char swap;

        if (en) {

            for (int i = 0; permut1[i].src != 0 || permut1[i].dst != 0; i++) {
                swap = str[permut1[i].src];
                str[permut1[i].src] = str[permut1[i].dst];
                str[permut1[i].dst] = swap;
            }

            for (int i = 0; permut2[i].src != 0  || permut2[i].dst != 0; i++) {
                swap = str[permut2[i].src];
                str[permut2[i].src] = str[permut2[i].dst];
                str[permut2[i].dst] = swap;
            }
        }
        else
        {
            for (int i = 0; permut2[i].src != 0  || permut2[i].dst != 0; i++) {
                swap = str[permut2[i].src];
                str[permut2[i].src] = str[permut2[i].dst];
                str[permut2[i].dst] = swap;
            }

            for (int i = 0; permut1[i].src != 0 || permut1[i].dst != 0; i++) {
                swap = str[permut1[i].src];
                str[permut1[i].src] = str[permut1[i].dst];
                str[permut1[i].dst] = swap;
            }
        }
    }
    
    public static void encode(char[] in, int len, char[] out) {
        
        int k, a, b;
        
        k = in[6] - '0';

        out[6] = in[6];
        for (int i = 0; i < len; i++) {
            if (i != 6) {
                k = (k % 9) + 1;
                a = in[i] - '0';
                b = a + key[k];
                if (b >= 10)
                    b = b - 10;
                out[i] = (char) (b + '0');
                k = k + i + 1;
            }
        }

        permute12(out, true);
    }
    
    public static void decode(char[] in, int len, char[] out) {
        
        int k, a, b;
        
        permute12(in, false);

        k = in[6] - '0';
        
        out[6] = in[6];
        for (int i = 0; i < len; i++) {
            if (i != 6) {
                k = (k % 9) + 1;
                a = in[i] - '0';
                if (a >= key[k])
                    b = a - key[k];
                else
                    b = a + 10 - key[k];

                out[i] = (char) (b + '0');
                k = k + i + 1;
            }
        }
    }
}
