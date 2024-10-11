package com.krzywdek19.learningApp.request;

public record ChangePasswordRequest(String oldPassword, String newPassword){
}
