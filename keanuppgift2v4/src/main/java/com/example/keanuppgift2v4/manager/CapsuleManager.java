package com.example.keanuppgift2v4.manager;

import com.example.keanuppgift2v4.model.AccountData;
import com.example.keanuppgift2v4.model.SecureContainer;
import com.example.keanuppgift2v4.repository.AccountDataRepository;
import com.example.keanuppgift2v4.repository.SecureContainerRepository;
import com.example.keanuppgift2v4.util.CryptoHelper;
import com.example.keanuppgift2v4.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class CapsuleManager {

    @Autowired
    private SecureContainerRepository containerRepository;

    @Autowired
    private AccountDataRepository accountRepository;

    @Autowired
    private CryptoHelper crypto;

    @Autowired
    private JwtHelper jwtHelper;

    // creates container for user
    public void createCapsule(Map<String, String> messageBody, String authHeader) throws Exception {
        // gets the users email from the token
        String email = jwtHelper.extractEmail(authHeader);

        AccountData user = accountRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("couldn't find user for email: " + email);
        }

        String messages = messageBody.get("messages");
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("no message found.");
        }

        String encrypt = crypto.encrypt(messages);

        // builds entity
        SecureContainer secureContainer = new SecureContainer();
        secureContainer.setEncryptedNote(encrypt);
        secureContainer.setOwner(user);
        secureContainer.setCreatedAt(LocalDateTime.now());

        containerRepository.save(secureContainer);
    }



   // reads all timecapsules and displays the messages
    public List<Map<String, String>> readAllCapsules(String authHeader) throws Exception {
        // extracts user from the token
        String email = jwtHelper.extractEmail(authHeader);
        AccountData user = accountRepository.findByEmail(email);

        List<SecureContainer> containers = containerRepository.findByOwnerId(user.getId());
        List<Map<String, String>> result = new ArrayList<>();

        for (SecureContainer container : containers) {
            String decrypt = crypto.decrypt(container.getEncryptedNote());
            Map<String, String> item = new HashMap<>();
            item.put("decryptedMessage", decrypt);
            item.put("createdAt", container.getCreatedAt().toString());
            result.add(item);
        }
        return result;
    }
}
