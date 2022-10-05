package com.company;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class HashPassword {

    // first 32 bits of the square root of the first 8 primes
    public static final String[] hashVal =  {   "01101010000010011110011001100111",
                                                "10111011011001111010111010000101",
                                                "00111100011011101111001101110010",
                                                "10100101010011111111010100111010",
                                                "01010001000011100101001001111111",
                                                "10011011000001010110100010001100",
                                                "00011111100000111101100110101011",
                                                "01011011111000001100110100011001"

                                            };

    // first 32 bits of the fractional parts of the cube roots of the first 64 primes
    public static final String[] hashConst =    {   "01000010100010100010111110011000", "01110001001101110100010010010001", "10110101110000001111101111001111", "11101001101101011101101110100101",
                                                    "00111001010101101100001001011011", "01011001111100010001000111110001", "10010010001111111000001010100100", "10101011000111000101111011010101",
                                                    "11011000000001111010101010011000", "00010010100000110101101100000001", "00100100001100011000010110111110", "01010101000011000111110111000011",
                                                    "01110010101111100101110101110100", "10000000110111101011000111111110", "10011011110111000000011010100111", "11000001100110111111000101110100",
                                                    "11100100100110110110100111000001", "11101111101111100100011110000110", "00001111110000011001110111000110", "00100100000011001010000111001100",
                                                    "00101101111010010010110001101111", "01001010011101001000010010101010", "01011100101100001010100111011100", "01110110111110011000100011011010",
                                                    "10011000001111100101000101010010", "10101000001100011100011001101101", "10110000000000110010011111001000", "10111111010110010111111111000111",
                                                    "11000110111000000000101111110011", "11010101101001111001000101000111", "00000110110010100110001101010001", "00010100001010010010100101100111",
                                                    "00100111101101110000101010000101", "00101110000110110010000100111000", "01001101001011000110110111111100", "01010011001110000000110100010011",
                                                    "01100101000010100111001101010100", "01110110011010100000101010111011", "10000001110000101100100100101110", "10010010011100100010110010000101",
                                                    "10100010101111111110100010100001", "10101000000110100110011001001011", "11000010010010111000101101110000", "11000111011011000101000110100011",
                                                    "11010001100100101110100000011001", "11010110100110010000011000100100", "11110100000011100011010110000101", "00010000011010101010000001110000",
                                                    "00011001101001001100000100010110", "00011110001101110110110000001000", "00100111010010000111011101001100", "00110100101100001011110010110101",
                                                    "00111001000111000000110010110011", "01001110110110001010101001001010", "01011011100111001100101001001111", "01101000001011100110111111110011",
                                                    "01110100100011111000001011101110", "01111000101001010110001101101111", "10000100110010000111100000010100", "10001100110001110000001000001000",
                                                    "10010000101111101111111111111010", "10100100010100000110110011101011", "10111110111110011010001111110111", "11000110011100010111100011110010"
                                                };

    private static final BigInteger max_int = new BigInteger("2").pow(32);

    public static String SaltedPassword(String password){

        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "()/*!@#$%^&_-";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new SecureRandom();
        char[] salt = new char[16];

        salt[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        salt[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        salt[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        salt[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i < salt.length; i++) {
            salt[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        System.out.print("Salt: ");
        for (char value : salt) {
            System.out.print(value);
        }
        System.out.println();

        StringBuilder saltPass = new StringBuilder();
        saltPass.append(password);

        for (char c : salt) {
            saltPass.append(c);
        }

        return saltPass.toString();
    }

    public static int[] getAsciiCode(String string){

        int[] asciiCodes = new int[string.length()];

        for(int i = 0; i < string.length(); i++){
            asciiCodes[i] = string.charAt(i);
        }

        return asciiCodes;
    }

    public static String convertHexToBinary(String hexText){

        HashMap<Character, String> binValues = new HashMap<>();

        binValues.put('0', "0000");
        binValues.put('1', "0001");
        binValues.put('2', "0010");
        binValues.put('3', "0011");
        binValues.put('4', "0100");
        binValues.put('5', "0101");
        binValues.put('6', "0110");
        binValues.put('7', "0111");
        binValues.put('8', "1000");
        binValues.put('9', "1001");
        binValues.put('A', "1010");
        binValues.put('B', "1011");
        binValues.put('C', "1100");
        binValues.put('D', "1101");
        binValues.put('E', "1110");
        binValues.put('F', "1111");

        String binaryText = "";
        hexText = hexText.replaceAll("\\s+","");
        char[] hexArray = hexText.toUpperCase().toCharArray();

        for (int i = 0; i < hexText.length(); i++) {

            if (binValues.containsKey(hexArray[i])) {
                binaryText += binValues.get(hexArray[i]);
            }
            else {
                binaryText = "Invalid Hexadecimal String";
                return binaryText;
            }
        }

        return binaryText;
    }

    public static int convertBinaryToDecimal(String binary){

        BigInteger result = BigInteger.ZERO;

        result = result.add(new BigInteger(binary, 2));

        result = result.mod(max_int);

        return result.intValue();
    }

    public static String convertDecimalToBinary(int[] decimal){

        String binaryForm = "";

        for(int i = 0; i < decimal.length; i++) {

            StringBuilder binary = new StringBuilder();
            while (0 < decimal[i]) {
                binary.append(decimal[i] % 2);
                decimal[i] = decimal[i] / 2;
            }

            while(binary.length() < 8){
                binary.insert(binary.length(), "0");
            }

            binaryForm += binary.reverse();
        }

        return binaryForm;
    }

    private static String[] split(String string, int size) {

        return string.split("(?<=\\G.{" + size + "})");
    }

    // rotation bits to right rot-times
    public static String RoR(String s1, int rot){

        return s1.substring(s1.length() - rot) + s1.substring(0, s1.length() - rot);
    }

    // shifting bits to right shift-times
    public static String ShR(String s1, int shift){

        StringBuilder shr = new StringBuilder();
        shr.append(s1);
        for(int i = 0; i < shift; i++){
            shr.deleteCharAt(shr.length() - 1);
            shr.insert(0, "0");
        }

        return shr.toString();
    }

    public static String xor(String s1, String s2){

        StringBuilder xorString = new StringBuilder();
        int val;
        for(int i = 0; i < s1.length(); i++){
            val = (s1.charAt(i) - 48) ^ (s2.charAt(i) - 48);
            xorString.append((char) (val + 48));
        }

        return xorString.toString();
    }

    // bitwise logical SUM
    public static String logSum(String... str){

        BigInteger result = BigInteger.ZERO;

        for (String bit : str) {
            result = result.add(new BigInteger(bit, 2));
        }

        result = result.mod(max_int);

        StringBuilder res = new StringBuilder();
        res.append(result.toString(2));

        while(res.length() < 32){
            res.insert(0, "0");
        }

        return res.toString();
    }

    // bitwise logical AND
    public static String logAnd(String s1, String s2){

        StringBuilder and = new StringBuilder();

        if(s1.length() < s2.length()){
            System.out.println(s1);
            System.out.println(s2);
        }
        else if(s1.length() > s2.length()) {
            System.out.println(s1);
            System.out.println(s2);
        }

        for(int i = 0; i < s1.length(); i++){

            if((s1.charAt(i) == '1') && (s2.charAt(i) == '1')){
                and.append("1");
            }
            else{
                and.append("0");
            }
        }

        return and.toString();
    }

    // bitwise logical NOT
    public static String logNot(String s1){

        StringBuilder not = new StringBuilder();
        for(int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) == '1') {
                not.append("0");
            }
            else{
                not.append("1");
            }
        }

        return not.toString();
    }

    public static String HashedPassword(String password) {

        // add salt to password
        String saltedPass = SaltedPassword(password);
        System.out.println("Initial password: " + password);
        System.out.println("Salted password: " + saltedPass);
        // convert char to corresponding ascii-code
        int[] asciiPass = getAsciiCode(saltedPass);
        // convert input password into bit-string
        String bitPass = convertDecimalToBinary(asciiPass);
        String bitPassLength = Integer.toBinaryString(bitPass.length());


        // Step 1. => Padding Bits
        // first bit appended is "1"
        bitPass += "1";
        // next append "0" bits until condition is true
        while(bitPass.length() % 512 != 448){
            bitPass += "0";
        }

        // Step 2. => Padding Length
        StringBuilder lengthPadding = new StringBuilder();
        lengthPadding.append(bitPassLength);
        while(lengthPadding.length() < 64){
            lengthPadding.insert(0, "0");
        }
        bitPass += lengthPadding.toString();

        // Step 3. => Initialize Changing Variables
        // password is split into block of 512 bits each
        String[] chunks512 = split(bitPass, 512);


        String[] hash = hashVal;
        // Step 4. => Process Each Block of 512 Bits
        for (String chunk : chunks512) {

            String[] w = new String[64];
            String[] chunks32 = split(chunk, 32);
            String s0, s1, ch, aux1, aux2, maj;
            String a = hashVal[0];
            String b = hashVal[1];
            String c = hashVal[2];
            String d = hashVal[3];
            String e = hashVal[4];
            String f = hashVal[5];
            String g = hashVal[6];
            String h = hashVal[7];

            System.arraycopy(chunks32, 0, w, 0, chunks32.length);

            for(int i = 16; i < 64; i++){
                String xor1 = xor(RoR(w[i - 15], 7), RoR(w[i - 15], 18));
                s0 = xor(xor1, ShR(w[i - 15], 3));
                String xor2= xor(RoR(w[i - 2], 17), RoR(w[i - 2], 19));
                s1 =  xor(xor2, ShR(w[i - 2], 10));
                String sum1 = logSum(w[i - 16], s0);
                String sum2 = logSum(sum1, w[i - 7]);
                w[i] = logSum(sum2, s1);
            }

            // compress values
            for(int i = 0; i < 64; i++){
                String xor1 = xor(RoR(e, 6), RoR(e, 11));
                s1 = xor(xor1, RoR(e, 25));

                ch = xor(logAnd(e, f), logAnd(logNot(e), g));

                String sum1 = logSum(h, s1);
                String sum2 = logSum(sum1, ch);
                String sum3 = logSum(sum2, hashConst[i]);
                aux1 = logSum(sum3, w[i]);

                String xor2 = xor(RoR(a, 2), RoR(a, 13));
                s0 = xor(xor2, RoR(a, 22));

                String xor3 = xor(logAnd(a, b), logAnd(a, c));
                maj = xor(xor3, logAnd(b, c));
                aux2 = logSum(s0, maj);

                h = g;
                g = f;
                f = e;
                e = logSum(d, aux1);
                d = c;
                c = b;
                b = a;
                a = logSum(aux1, aux2);

            }

            hash[0] = logSum(hash[0], a);
            hash[1] = logSum(hash[1], b);
            hash[2] = logSum(hash[2], c);
            hash[3] = logSum(hash[3], d);
            hash[4] = logSum(hash[4], e);
            hash[5] = logSum(hash[5], f);
            hash[6] = logSum(hash[6], g);
            hash[7] = logSum(hash[7], h);
        }

        // converting hash values from binary to hex
        StringBuilder hexStr = new StringBuilder();

        for (String s : hash) {
            hexStr.append(new BigInteger(s, 2).toString(16));
        }

        return hexStr.toString();
    }

    public static void SteganographyImage(String path, String destPath, String hashedPassword){

        BufferedImage img = null;
        File f;

        try {
            f = new File(path + "/img.jpg");
            img = ImageIO.read(f);
        }
        catch (IOException e) {
            System.out.println(e);
        }

        BufferedImage newImg = img;

        int height = img.getHeight();
        int width = img.getWidth();

        // retrieving each pixel
        int[][] matrix = new int[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                matrix[i][j] = img.getRGB(i, j);
            }
        }

        // converting each pixel into bits
        String[][] bitImg = new String[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                bitImg[i][j] = Integer.toBinaryString(matrix[i][j]);
            }
        }

        String bitPass = convertHexToBinary(hashedPassword);
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                StringBuilder newBit = new StringBuilder();
                newBit.append(bitImg[i][j]);
                newBit.deleteCharAt(newBit.length() - 1);
                newBit.append(bitPass.charAt(j));
                bitImg[i][j] = newBit.toString();
            }
        }

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                matrix[i][j] = convertBinaryToDecimal(bitImg[i][j]);
            }
        }

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                newImg.setRGB(i, j, matrix[i][j]);
            }
        }

        try {
            f = new File(destPath + "/newImg.jpg");
            ImageIO.write(newImg, "jpg", f);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter password: ");
        String pass = sc.nextLine();

        String hashed = HashedPassword(pass);
        System.out.println("Hashed password: " + hashed);

        System.out.println("Enter image path: ");
        String path = sc.nextLine();

        System.out.println("Enter image path: ");
        String destPath = sc.nextLine();

        // Example
        // SteganographyImage("D:/FACULTATE UBB/ANUL III/SEMESTRUL II/Criptografie/Seminar/Seminar5", "C:/Users/ms12a/Desktop", hashed);
        SteganographyImage(path, destPath, hashed);
    }
}
