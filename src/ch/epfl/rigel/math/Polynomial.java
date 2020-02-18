package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

public final class Polynomial {
    private double[] coefficients;
    private Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }

    public static Polynomial of(double coefficientN, double... coefficients){ //TODO check
        Preconditions.checkArgument(coefficientN!=0);
        double[] newArray = new double[coefficients.length+1];
        newArray[0] = coefficientN;
        System.arraycopy(coefficients,0,newArray,1,coefficients.length);
        return new Polynomial(newArray);
    }

    public double at(double x){
        double returnValue = coefficients[0];
        for(int n=1;n<coefficients.length;n++){
            returnValue = returnValue*x+coefficients[n];
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return
    }
}
