package com.example.keanuppgift2v4;

import com.example.keanuppgift2v4.util.CryptoHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Keanuppgift2v4Application {

	public static void main(String[] args) {
		// tests the encryption
		try {
			CryptoHelper helper = new CryptoHelper();
			String sample = "Hello encryption!";
			String locked = helper.encrypt(sample);
			String unlocked = helper.decrypt(locked);

			System.out.println("original: " + sample);
			System.out.println("encrypted: " + locked);
			System.out.println("decrypted: " + unlocked);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(Keanuppgift2v4Application.class, args);
	}
}
