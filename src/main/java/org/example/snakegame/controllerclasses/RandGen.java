package org.example.snakegame.controllerclasses;

import java.util.Random;

public class RandGen{
    private static Random generator = new Random();
    public static int randInt(int a, int b){
        return a<b ? generator.nextInt(a, b) : generator.nextInt(b, a);
    }
    public static int randInt(int a){
        return generator.nextInt(a);
    }
    public static double randDouble(double a, double b){
        return generator.nextDouble(a, b);
    }
    public static double randDouble(double a){
        return generator.nextDouble(a);
    }
    public static double randDouble(){
        return generator.nextDouble();
    }
    public static void randomize(){
        generator = new Random();
    }

}
