package modelo.servicio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorServicio {

    //ver explicación abajo

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$";

    //minimo 6 caracteres, debe contener 1 letra y 1 numero
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,}$";

    private static final String CREDIT_CARD_REGEX = "^(\\d{10,19})$";

    public boolean esValidoMail(String email)
    {
        //compilar la regex a un objeto pattern
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        //crear un "matcher" para comparar expresiones
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean esValidoPass(String pass)
    {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    //validador básico, solo chequea cantidad de numeros
    public boolean esValidaTarjeta(String tarjetaNum)
    {
        Pattern pattern = Pattern.compile(CREDIT_CARD_REGEX);
        Matcher matcher = pattern.matcher(tarjetaNum);
        return matcher.matches();
    }


}
//    1. ^ - Start of the string
//    The caret (^) indicates the start of the string. This ensures that the email address must begin immediately after
//    the start.
//
//    2. [a-zA-Z0-9._%+-]+
//    This part matches the local part of the email address (the part before the @). It allows:
//
//      a-zA-Z: Uppercase and lowercase letters
//      0-9: Numbers
//      ._%+-: Special characters like dot (.), underscore (_), percent (%), plus (+), and hyphen (-)
//      +: The + outside the brackets means "one or more" of the preceding characters. So, at least one character
//      from this
//      group is required.
//
//    3. @
//    The literal @ symbol separates the local part of the email from the domain part.
//
//    4. [a-zA-Z0-9.-]+
//    This part matches the domain part of the email (e.g., example.com). It allows:
//
//      a-zA-Z: Uppercase and lowercase letters
//      0-9: Numbers
//      .-: A dot (.) or a hyphen (-) can appear in domain names.
//      Again, the + means "one or more" of these characters must be present in the domain part.
//
//    5. \\. (escaped dot)
//    The double backslash (\\.) represents a literal dot (.), which separates the domain name from the top-level
//    domain (e.g., .com).
//
//    6. [a-zA-Z]{2,6}
//    This part matches the top-level domain (TLD), such as .com, .org, or .net. It allows:
//
//      a-zA-Z: Uppercase and lowercase letters
//      {2,6}: The TLD must have between 2 and 6 characters (e.g., .com is 3 characters, .museum is 6 characters).
//
//    7. Optional additional domain level ((\\.[a-zA-Z]{2,})?): This part is optional and allows for country code TLDs
//    (like .ar, .uk, .co, etc.). The ? ensures that this part is optional, meaning it could be present or not.
//
//    8. $ - End of the string
//    The dollar sign ($) indicates the end of the string. This ensures that the string must end right after the
//    top-level domain.