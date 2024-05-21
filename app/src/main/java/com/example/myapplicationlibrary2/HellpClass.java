package com.example.myapplicationlibrary2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HellpClass {
    String bookname;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String name) {
        this.bookname = bookname;
    }



    public HellpClass(String bookname) {
        this.bookname = bookname;
    }
}

