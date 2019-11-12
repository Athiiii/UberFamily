package com.example.uberfamiliy.Service;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.uberfamiliy.model.User;

import java.io.ByteArrayOutputStream;


/**
 * Creates a user with parameters
 */
public class CreateUser {
    private static CreateUser createUser;

    private CreateUser() {

    }

    public static CreateUser getInstance() {
        if (createUser == null) {
            createUser = new CreateUser();
        }
        return createUser;
    }

    public User createUser(String fullName, String username, String password, Bitmap image) {
        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);
        user.setImage(convertImageToString(image));
        return user;
    }

    private String convertImageToString(Bitmap image) {
        byte[] byteArray = null;
        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
