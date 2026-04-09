package com.ecotravel.factory;

// Dodajemo 'public' kako bi klasa bila vidljiva van ovog paketa
public class EcoReport implements AdventureReport {
    @Override
    public String generate() {
        return "ECO-REPORT: Ova avantura ispunjava sve ekološke standarde i koristi sertifikovane vodiče.";
    }
}