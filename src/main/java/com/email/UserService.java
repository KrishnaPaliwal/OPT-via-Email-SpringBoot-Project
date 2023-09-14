package com.email;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private final Map<String, String> otpStore = new HashMap<>();

    public void generateAndStoreOTP(String email) {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // Generate a 4-digit OTP
        otpStore.put(email, String.valueOf(otp));
    }

    public String getStoredOTP(String email) {
        return otpStore.get(email);
    }

    public void removeOTP(String email) {
        otpStore.remove(email);
    }
}
