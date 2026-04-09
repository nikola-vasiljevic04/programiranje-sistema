package com.ecotravel.factory;

public class StandardReport implements AdventureReport {
    @Override
    public String generate() {
        return "STANDARD-REPORT: Osnovni detalji o ruti i vodiču.";
    }
}