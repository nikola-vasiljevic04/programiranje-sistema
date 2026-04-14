package com.ecotravel.factory;

public class EcoReport implements AdventureReport {
    @Override
    public String generate() {
        return "ECO-REPORT: Ova avantura ispunjava sve ekološke standarde i koristi sertifikovane vodiče.";
    }
}