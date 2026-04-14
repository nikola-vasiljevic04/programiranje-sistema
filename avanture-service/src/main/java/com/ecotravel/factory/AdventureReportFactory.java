package com.ecotravel.factory;

import org.springframework.stereotype.Component;

@Component
public class AdventureReportFactory {

    public AdventureReport createReport(double completenessScore) {
        if (completenessScore > 80.0) {
            return new EcoReport();
        } else {
            return new StandardReport();
        }
    }
}