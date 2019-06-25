package net.fayola.hydraulica;

import android.util.Log;

/***
 *     • Calculation for Cylindrical Force
 *         ◦ https://sciencing.com/calculate-pneumatic-cylinder-force-4897627.html
 *         ◦ (airP-atmP)*ca
 *         ◦ airP = userInputAirP
 *         ◦ atmp = 14.696
 *         ◦ ca=r^2 * pi
 *         ◦ r=unserInputR
 */


public class CylindricalForce {
    public static String TAG = MainActivity.TAG+"::CylyndricalForce";

    public static double check(double airP, double r){
        double ca = Math.pow(r,2.0)*Math.PI;
        double atmP = 14.696;
        double result = (airP-atmP)*ca;
        Log.d(TAG,"Cylindrical force of pressure:" + airP + " and " + "radius:" + r + " = " + result);
        return result;
    }
}
