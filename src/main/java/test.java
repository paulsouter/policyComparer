/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author paulo
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }

     public int solution(int A, int B) {
         int result = -1;
         int length = 0;
         String resultString = "";
         String AString = String.valueOf(A);
         String BString = String.valueOf(B);
         if (AString.length() + BString.length() >= 10){
             return -1;
         }
         for(int i =0; i< AString.length() && i< BString.length(); i++){
             resultString += AString.charAt(i);
             resultString += BString.charAt(i);
             
         }
         
         if(AString.length() > BString.length()){
             resultString += AString.substring(BString.length());
         }
           if(BString.length() > AString.length()){
             resultString += BString.substring(AString.length());
         }
         result = Integer.parseInt(resultString);
         if (result >= 1000000000){
             return -1;
         }
         return result;
    }
     
}