package com.example.mymovies.user;

public class Vigenere {

	 private String Keyword = LowerToUpper("vulpe");

	    //This function generates the key in
	//a cyclic manner until it's length isi'nt
	//equal to the length of original text
	    private String generateKey(String str, String key)
	    {
	        int x = str.length();

	        for (int i = 0; ; i++)
	        {
	            if (x == i)
	                i = 0;
	            if (key.length() == str.length())
	                break;
	            key+=(key.charAt(i));
	        }
	        return key;
	    }

	    //This function returns the encrypted text
	//generated with the help of the key
	    private String cipherText(String str, String key)
	    {
	        String cipher_text="";

	        for (int i = 0; i < str.length(); i++)
	        {
	            // converting in range 0-25
	            int x = (str.charAt(i) + key.charAt(i)) %26;

	            // convert into alphabets(ASCII)
	            x += 'A';

	            cipher_text+=(char)(x);
	        }
	        return cipher_text;
	    }

	    //This function decrypts the encrypted text
	//and returns the original text
	 public  String originalText(String cipher_text2)
	    {
	        String orig_text="";
            String cipher_text = LowerToUpper(cipher_text2);

	        for (int i = 0 ; i < cipher_text.length() &&
	                i < Keyword.length(); i++)
	        {
	            // converting in range 0-25
	            int x = (cipher_text.charAt(i) -
	                    Keyword.charAt(i) + 26) %26;

	            // convert into alphabets(ASCII)
	            x += 'A';
	            orig_text+=(char)(x);
	        }
	        return orig_text;
	    }

	    //This function will convert the lower case character to Upper case
	    private String LowerToUpper(String s)
	    {
	        StringBuffer str =new StringBuffer(s);
	        for(int i = 0; i < s.length(); i++)
	        {
	            if(Character.isLowerCase(s.charAt(i)))
	            {
	                str.setCharAt(i, Character.toUpperCase(s.charAt(i)));
	            }
	        }
	        s = str.toString();
	        return s;
	    }
	    public String encryptPass(String password){
	        String key = generateKey(LowerToUpper(password),Keyword);
	        return cipherText(LowerToUpper(password),key);
	    }

	    //Driver code
		public static void main(String[] args) {
//			  String Str = "GEEKSFORGEEKS";
//			   Vigenere encoder = new Vigenere();
//			 System.out.println(encoder.encryptPass("Ioan123"));
//
//			   String str = LowerToUpper(Str);
//			  String keyword = LowerToUpper(Keyword);
//
//			  String key = generateKey(str, keyword);
//			 String cipher_text = cipherText(str, key);
//
//	        System.out.println("Ciphertext : "
//	                + cipher_text + "\n");
//
//	        System.out.println("Original/Decrypted Text : "
//	                + originalText(cipher_text, key));
//			String pass = new Vigenere().encryptPass("Password12");
//			System.out.println(pass);
//			System.out.println(new Vigenere().originalText(pass));
//		}
		}
	    
}
