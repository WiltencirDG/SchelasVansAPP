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


    boolean validateLogin(String username, String password){

        if(username.isEmpty()){
            return false;
        }
        if(password.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    boolean validateNome(String nome){
        if(nome.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validatePhone(String phone){
        if(phone.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validateAddress(String address){
        if(address.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validateNumber(String number){
        if(number.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validateBairro(String bairro){
        if(bairro.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validateCidade(String cidade){
        if(cidade.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    boolean validateEmpty(String data){
        if(data.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
