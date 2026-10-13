import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordCheckerTest {
    private PasswordChecker checker;

    @BeforeEach
    public void setUp() {
        checker = new PasswordChecker();
    }

    //Descubrimos los límites de fábrica porque no se han indicado
    @Test
    public void discoverDefaultLimits(){
        checker.getMinLength();
        checker.getMaxLength();
        boolean checkDigits = checker.getCheckDigits();
        boolean checkUppers = checker.getCheckUppers();
        boolean checkLowers = checker.getCheckLowers();

        System.out.println("Minimum length: " + checker.getMinLength());
        System.out.println("Maximum length: " + checker.getMaxLength());
        System.out.println("Check digits: " + checkDigits);
        System.out.println("Check uppers: " + checkUppers);
        System.out.println("Check lowers: " + checkLowers);

    }

    // Validación de reglas básicas y códigos de error del metodo check()
    // (TC01 a TC14)

    // TC01: Contraseña válida por defecto (no vacía)
    @Test
    public void checkBlankSuccessTC01(){
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC02: Contraseña vacía o en blanco
    @Test
    public void checkBlankFailTC02(){
        int resultado = checker.check("","");
        assertEquals(PasswordChecker.ERR_BLANK, resultado);
    }

    // TC03: Límite inferior válido (8 caracteres)
    @Test
    public void checkLengthMinLimitTC03(){
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC04: Frontera inferior inválida (7 caracteres)
    @Test
    public void checkLengthUnderLimitTC04(){
        int resultado = checker.check("Abcdef1", "Abcdef1" );
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }

    // TC05: Límite superior válido (20 caracteres)
    @Test
    public void checkLengthMaxLimitTC05(){
        int resultado = checker.check(
                "AbcdefghijAbcdefghi1", "AbcdefghijAbcdefghi1" );
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC06: Frontera superior inválida (21 caracteres)
    @Test
    public void checkLengthBeyondLimitTC06(){
        int resultado = checker.check(
                "AbcdefghijAbcdefghi12", "AbcdefghijAbcdefghi12" );
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }

    // TC07: Dígitos habilitados y contraseña con dígitos válidos
    @Test
    public void checkDigitsSuccessTC07(){
        checker.setCheckDigits(true); //Inicializar el control (false por defecto)
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC08: Dígitos habilitados pero contraseña sin dígitos
    @Test
    public void checkDigitsFailTC08(){
        checker.setCheckDigits(true);
        int resultado = checker.check("Abcdefgh", "Abcdefgh");
        assertEquals(PasswordChecker.ERR_DIGITS, resultado);
    }

    // TC09: Minúsculas habilitadas y contraseña con minúsculas válidas
    @Test
    public void checkLowerCaseSuccessTC09(){
        checker.setCheckLowers(true);
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC10: Minúsculas habilitadas pero contraseña sin minúsculas
    @Test
    public void checkLowerCaseFailTC10(){
        checker.setCheckLowers(true);
        int resultado = checker.check("ABCDEFGH1", "ABCDEFGH1");
        assertEquals(PasswordChecker.ERR_LOWERS, resultado);
    }

    // TC11: Mayúsculas habilitadas y contraseña con mayúsculas válidas
    @Test
    public void checkUpperCaseSuccessTC11(){
        checker.setCheckUppers(true);
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC12: Mayúsculas habilitadas pero contraseña sin mayúsculas
    @Test
    public void checkUpperCaseFailTC12(){
        checker.setCheckUppers(true);
        int resultado = checker.check("abcdefgh1", "abcdefgh1");
        assertEquals(PasswordChecker.ERR_UPPERS, resultado);
    }

}
