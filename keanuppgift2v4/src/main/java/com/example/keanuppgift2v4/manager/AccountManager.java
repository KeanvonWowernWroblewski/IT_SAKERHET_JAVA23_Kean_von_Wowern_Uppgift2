package com.example.keanuppgift2v4.manager;

import com.example.keanuppgift2v4.model.AccountData;
import com.example.keanuppgift2v4.repository.AccountDataRepository;
import com.example.keanuppgift2v4.util.JwtHelper;
import com.example.keanuppgift2v4.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountManager {

    @Autowired
    private AccountDataRepository accountRepository;

    @Autowired
    private PasswordUtility passwordUtility;

    @Autowired
    private JwtHelper tokenMaker;

   // creates account with hashed password
    public void createAccount(Map<String, Object> body) {
        String nameVal = (String) body.get("name");
        String emailVal = (String) body.get("email");
        Integer ageVal = (Integer) body.get("age");
        String adressVal = (String) body.get("address");
        String rawPassword = (String) body.get("password");

        AccountData accountData = new AccountData();
        accountData.setName(nameVal);
        accountData.setEmail(emailVal);
        accountData.setAge(ageVal);
        accountData.setadress(adressVal);

        String hashed = passwordUtility.hashPassword(rawPassword);
        accountData.setPasswordHash(hashed);

        accountRepository.save(accountData);
    }



    // verifies user if jwt token is correct
    public String processLogin(Map<String, String> loginBody) {
        String emailVal = loginBody.get("email");
        String passwordVal = loginBody.get("password");

        AccountData found = accountRepository.findByEmail(emailVal);
        if (found == null) {
            throw new RuntimeException("Unknown user email.");
        }

        // checks password
        if (!passwordUtility.checkMatch(passwordVal, found.getPasswordHash())) {
            throw new RuntimeException("Invalid password.");
        }


        // generates token
        return tokenMaker.generateToken(emailVal);
    }
}
