package com.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DotService {


    public boolean validate(Double x, Double y, Double r) {
        return (x != null && y != null && r != 0 && (x >= -2 && x<= 2) && (y >= -5.0 && y <= 5.0) && (r > 0 && r <= 2));
    }

    public boolean checkArea(double x, double y, double r) {
        if (x >= 0 && y >= 0)
            return checkFirstQ(x, y, r);
        else if (x <= 0 && y <= 0)
            return checkThirdQ(x, y, r);
        else if (x >= 0 && y <= 0)
            return checkFourthQ(x, y, r);
        else
            return checkSecondQ(x, y, r);
    }

    private boolean checkFirstQ(double x, double y, double r) {
        return (x * x + y * y <= (r * r) / 4);
    }

    private boolean checkSecondQ(double x, double y, double r) {
        return (x >= -r && y <= r / 2);
    }

    private boolean checkThirdQ(double x, double y, double r) {
        return false;
    }

    private boolean checkFourthQ(double x, double y, double r) {
        return (y >= x - r / 2);
    }

}