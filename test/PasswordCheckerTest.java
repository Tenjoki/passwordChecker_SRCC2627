import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void checkDefaultSuccessTC01(){
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
        checker.setMaxLength(20); // Forzamos un límite máximo para probar la frontera
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

    // TC13: Contraseñas idénticas que coinciden correctamente
    @Test
    public void checkMatchSuccessTC13(){
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC14: Contraseñas diferentes que no coinciden
    @Test
    public void checkMatchFailTC14(){
        int resultado = checker.check("Abcdefg1", "Other123");
        assertEquals(PasswordChecker.ERR_NO_MATCH, resultado);
    }

    //Control de parámetros y excepciones en los setters
    //(TC15 a TC18)

    // TC15: Longitud mínima válida que se configura y recupera correctamente
    @Test
    public void setMinLengthSuccessTC15(){
        checker.setMinLength(8);
        assertEquals(8, checker.getMinLength());
    }

    // TC16: Longitud mínima inválida (< 0) que lanza IllegalArgumentException
    @Test
    public void setMinLengthFailTC16(){
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(-1);
        });
        assertEquals(PasswordChecker.ERR_INVALID_MIN_LENGTH, error.getMessage());
    }

    // TC17: Longitud máxima válida mayor que la mínima configurada correctamente
    @Test
    public void setMaxLengthSuccessTC17(){
        checker.setMinLength(5);
        checker.setMaxLength(20);
        assertEquals(20, checker.getMaxLength());
    }

    // TC18: Longitud máxima inválida menor que la mínima que lanza IllegalArgumentException
    @Test
    public void setMaxLengthFailTC18(){
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(8);
            checker.setMaxLength(4);
        });
        assertEquals(PasswordChecker.ERR_INVALID_MAX_LENGTH, error.getMessage());
    }

    //Verificación de estados opcionales desactivados
    // (TC19 a TC21)

    // TC19: Validación exitosa con la comprobación de dígitos desactivada por defecto
    @Test
    public void uncheckedDigitsSuccessTC19(){
        int resultado = checker.check("Abcdefgh", "Abcdefgh");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC20: Validación exitosa con la comprobación de minúsculas desactivada por defecto
    @Test
    public void uncheckedLowersSuccessTC20(){
        int resultado = checker.check("ABCDEFGH1", "ABCDEFGH1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TC21: Validación exitosa con la comprobación de mayúsculas desactivada por defecto
    @Test
    public void uncheckedUppersSuccessTC21(){
        int resultado = checker.check("abcdefgh1", "abcdefgh1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }
}
