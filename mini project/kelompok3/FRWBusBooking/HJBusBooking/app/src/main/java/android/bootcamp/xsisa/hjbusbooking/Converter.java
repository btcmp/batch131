package android.bootcamp.xsisa.hjbusbooking;

/**
 * Created by XSIS-NB on 1/23/2018.
 */

public class Converter {
    public static  String convertIntegerToCurrency(int value, String kurs){
        String currency = kurs + " ";
        String valueC = Integer.toString(value);

        String tempBuffer = "";
        int separate = 0;
        for(int c = valueC.length(); c>0; c--){
            separate++;
            tempBuffer += valueC.substring(c-1, c);
            if (separate == 3 && c-1 >0){
                tempBuffer += ".";
                separate = 0;
            }
        }
        String reverse = new StringBuffer(tempBuffer).reverse().toString();
        currency += reverse + ",00";

        return currency;
    }
}
