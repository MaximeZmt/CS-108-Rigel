package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Represents a polynomial function
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Polynomial {
    private final double[] coefficients;
    private Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }

    /**
     * Creates a polynomial with the given coefficients
     *
     * @param coefficientN coefficient of the biggest power
     * @param coefficients coefficients
     * @return a polynomial (Polynomial)
     * @throws IllegalArgumentException if coefficientN equals to 0
     */
    public static Polynomial of(double coefficientN, double... coefficients){
        Preconditions.checkArgument(coefficientN != 0);
        double[] newArray = new double[coefficients.length + 1];
        newArray[0] = coefficientN;
        System.arraycopy(coefficients, 0, newArray, 1, coefficients.length);
        return new Polynomial(newArray);
    }

    /**
     * Compute the value of the polynomial with the given variable
     *
     * @param x the variable of the polynomial
     * @return the value of the polynomial (double)
     */
    public double at(double x){
        double returnValue = coefficients[0];
        for(int n = 1; n < coefficients.length; ++n){
            returnValue = returnValue * x + coefficients[n];
        }
        return returnValue;
    }

    /**
     * @see Object#toString() 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; ++i){
            if(coefficients[i] != 0){
               if(i > 0 && coefficients[i] > 0){ //append a plus, if it is not the first term and if positive
                    sb.append("+");
               }
               if (Math.abs(coefficients[i]) != 1){ //append the value of the coefficient
                   sb.append(coefficients[i]);
               }else if (coefficients[i] == -1){ //if coefficient is negative, append a minus
                   sb.append("-");
               }
               if (i < (coefficients.length - 1)){ //append the x value if power is at least one
                   sb.append("x");
                   if (i < (coefficients.length - 2)){ //write the power of the x value if at least two
                       sb.append("^").append(coefficients.length - 1 - i);
                   }
               }
            }
        }
        return sb.toString();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Try to call hashCode in Polynomial");
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Try to call equals in Polynomial");
    }
}
