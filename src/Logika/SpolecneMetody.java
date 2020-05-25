package Logika;

import java.util.regex.Pattern;

public class SpolecneMetody
{
    /**
     * Metoda kontroluje zda je zadaný text číslem.
     * Pokuď ano vrátí true, pokuď ne, vrátí false
     * @param text - String
     * @return - true / false
     */
    public static boolean jeToCislo(String text)
    {
        if (text == null)
        {
            return false;
        }
        try
        {
            double cislo = Double.parseDouble(text);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    /**
     * Metoda kontroluje, že poslaný string má formát emailu
     * @param email - String
     * @return true / false
     */
    public static boolean jeToEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
        {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
