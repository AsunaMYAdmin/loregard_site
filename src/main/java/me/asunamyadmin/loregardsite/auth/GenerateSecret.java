package me.asunamyadmin.loregardsite.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class GenerateSecret {
    static void main() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try (Scanner scanner = new Scanner(System.in)) {
            String password = scanner.nextLine();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            System.out.println(password + ":" + encodedPassword);
        }
    }
}
