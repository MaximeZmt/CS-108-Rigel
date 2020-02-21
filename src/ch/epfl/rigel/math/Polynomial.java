package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Represents a polynomial function
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Polynomial {
    private double[] coefficients;
    private Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }

    /**
     * Creates a polynomial with the given coefficients
     *
     * @param coefficientN  coefficient of the biggest power
     * @param coefficients  coefficients
     * @return a polynomial
     */
    public static Polynomial of(double coefficientN, double... coefficients){ //TODO check
        Preconditions.checkArgument(coefficientN!=0);
        double[] newArray = new double[coefficients.length+1];
        newArray[0] = coefficientN;
        System.arraycopy(coefficients,0,newArray,1,coefficients.length);
        return new Polynomial(newArray);
    }

    /**
     * Compute the value of the polynomial with the given variable
     *
     * @param x  the variable of the polynomial
     * @return the value of the polynomial
     */
    public double at(double x){
        double returnValue = coefficients[0];
        for(int n=1 ; n<coefficients.length ; ++n){
            returnValue = returnValue*x+coefficients[n];
        }
        return returnValue;
    }

    @Override
    public String toString() { //TODO use stringBuilder
        String s = "";
        for (int i=0 ; i<coefficients.length ; ++i){
            if(coefficients[i]!=0){
               if(i>0 && coefficients[i]>0){
                    s = s + "+";
               }
               if (Math.abs(coefficients[i])!=1){
                   s=s+coefficients[i];
               }else if (coefficients[i]==-1){
                   s = s + "-";
               }

               if (i<(coefficients.length-1)){
                   s = s + "x";
                   if (i<(coefficients.length-2)){
                       s = s + "^" + (coefficients.length-1-i);
                   }
               }
            }
        }
        return s;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Try to call hashCode in Polynomial");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Try to call equals in Polynomial");
    }
}
