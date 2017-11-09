package com.appsgit.employeeprovider.database;

/**
 * Created on 11/4/17.
 */

public class Emoployee {
    int id;
    String name;
    int age;
    String email;
    String phone;
    String address;

    public int getId() {
        return id;
    }

    public Emoployee setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Emoployee setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Emoployee setAge(int age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Emoployee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Emoployee setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Emoployee setAddress(String address) {
        this.address = address;
        return this;
    }
}
