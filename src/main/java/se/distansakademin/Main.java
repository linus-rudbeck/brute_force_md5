package se.distansakademin;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    private static String[] hashList = {
            "788d4feee88a6b50e37e6547a6ca7fa4",
            "c0717bbd02f1819950c4d6f269efef81",
            "900b8a3414a93a2efc286d866317503f",
            "b9b549591de5e8275873058a85a43f61",
            "1ff0ea9c8f5bafaab8cb01947f3c3166"
    };

    private static String FILE_LOCATION = "C:\\Users\\rudbe\\Desktop\\10-million-password-list-top-1000000.txt";

    public static void main(String[] args) {
        System.out.println("Starting!");

        findPasswordHashesFromFile();

        System.out.println("Done!");
    }

    private static void findPasswordHashesFromFile() {
        Scanner sc = openFile();

        processFile(sc);
    }

    private static Scanner openFile() {
        try {
            // pass the path to the file as a parameter
            File file = new File(FILE_LOCATION);
            return new Scanner(file);

        } catch (FileNotFoundException e) {
            System.out.println("File not found for reading");
        }

        return null;
    }

    private static void processFile(Scanner sc) {
        while (sc.hasNextLine()) {
            var password = sc.nextLine();
            var passwordHash = getMd5Hash(password);

            if (existsInList(passwordHash)) {
                System.out.println(passwordHash + ": " + password);
            }
        }
    }

    private static boolean existsInList(String item) {

        // Check if item is in list
        for (var listHash : hashList) {

            if (listHash.equals(item)) { // If so: return true
                return true;
            }

        }

        return false;
    }

    private static String getMd5Hash(String password) {
        password = password.trim(); // "  Linus " => "Linus"

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid MD Algorithm");
        }

        md.update(password.getBytes());

        var digest = md.digest();

        var hashBytes = Base64.getEncoder().encodeToString(digest);

        return DigestUtils.md5Hex(hashBytes);
    }
}