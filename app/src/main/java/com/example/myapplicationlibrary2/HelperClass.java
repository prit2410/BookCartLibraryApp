package com.example.myapplicationlibrary2;


public class HelperClass {

    String name, email, username, password, bookname, hold;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getBookname() {
        return bookname;
    }

    public void setBookname(String name) {
        this.bookname = bookname;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }

    public HelperClass(String name, String email, String username, String password, String bookname, String hold) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bookname = bookname;
        this.hold = hold;
    }

//    public HelperClass(String bookname) {
//        this.bookname = bookname;
//    }
}