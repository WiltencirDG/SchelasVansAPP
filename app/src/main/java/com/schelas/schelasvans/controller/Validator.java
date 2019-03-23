package com.schelas.schelasvans.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    Validator() {

    }

    boolean validateDados(String email, String username, String password, String confsenha){

        if(email.isEmpty()){
            return false;
        }
        if(username.isEmpty()){
            return false;
        }
        if(password.isEmpty()){
            return false;
        }
        if(confsenha.isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }

    boolean validatePass(String password, String confsenha){
        if (password.equals(confsenha)){
            return true;
        }else{
            return false;
        }

    }

    boolean validateEmail(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }else{
            return false;
        }
    }

}
