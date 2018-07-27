package com.example.mapets.testamalgama.entity;

public class Perro {

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Perro(String raza) {

        this.raza = raza;
    }

    private String raza;
}