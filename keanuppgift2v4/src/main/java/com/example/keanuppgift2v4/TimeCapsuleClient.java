package com.example.keanuppgift2v4;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class TimeCapsuleClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final OkHttpClient client = new OkHttpClient();

    private static String token = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== TIME CAPSULE CLI ===");
            System.out.println("1) Register");
            System.out.println("2) Login");
            System.out.println("3) Add Encrypted Message");
            System.out.println("4) View Decrypted Messages");
            System.out.println("5) Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> register(scanner);
                    case 2 -> login(scanner);
                    case 3 -> addMessage(scanner);
                    case 4 -> seeMessages();
                    case 5 -> {
                        System.out.println("Exiting");
                        return;
                    }
                    default -> System.out.println("Not a valid choice");
                }
            } catch (IOException ex) {
                System.out.println("Error contacting server: " + ex.getMessage());
            }
        }
    }



    private static void register(Scanner scanner) throws IOException {
        System.out.println("[Registration]");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        // builds json
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("email", email);
        object.addProperty("age", age);
        object.addProperty("address", address);
        object.addProperty("password", pass);

        RequestBody body = RequestBody.create(
                object.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/register")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("[Server] " + response.body().string());
        }
    }



    private static void login(Scanner scanner) throws IOException {
        System.out.println("[Login]");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        JsonObject object = new JsonObject();
        object.addProperty("email", email);
        object.addProperty("password", pass);

        RequestBody body = RequestBody.create(
                object.toString(),
                MediaType.parse("application/json")
        );
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/login")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonObject data = JsonParser.parseString(response.body().string()).getAsJsonObject();
                token = data.get("token").getAsString();
                System.out.println("Login successful! Token stored.");
            } else {
                System.out.println("Login failed: " + response.message());
            }
        }
    }


    private static void addMessage(Scanner scanner) throws IOException {
        if (token == null) {
            System.out.println("You need to log in first!");
            return;
        }
        System.out.println("Add Message");
        System.out.print("Message content: ");
        String msg = scanner.nextLine();

        JsonObject object = new JsonObject();
        object.addProperty("messages", msg);

        RequestBody body = RequestBody.create(
                object.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/messages/message")
                .post(body)
                .addHeader("Authorization", "bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Server " + response.body().string());
        }
    }

    private static void seeMessages() throws IOException {
        if (token == null) {
            System.out.println("You need to log in first!");
            return;
        }
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/messages/messages")
                .get()
                .addHeader("Authorization", "bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                var array = JsonParser.parseString(response.body().string()).getAsJsonArray();
                System.out.println("--- Your Decrypted Messages ---");
                for (var elem : array) {
                    var object = elem.getAsJsonObject();
                    String decrypted = object.get("decryptedMessage").getAsString();
                    String created = object.get("createdAt").getAsString();
                    System.out.println("message: " + decrypted);
                    System.out.println("created: " + created);
                    System.out.println("------------");
                }
            } else {
                System.out.println("Request failed: " + response.message());
            }
        }
    }
}
