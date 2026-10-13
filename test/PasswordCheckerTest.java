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

        System.out.println("Minimum length: " + checker.getMinLength());
        System.out.println("Maximum length: " + checker.getMaxLength());
    }

    // Validación de reglas básicas y códigos de error del metodo check()
    // (TC01 a TC14)

    //TC01
    @Test
    public void checkBlankSuccessTC01(){
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    //TC02
    @Test
    public void checkBlankFailTC02(){
        int resultado = checker.check("","");
        assertEquals(PasswordChecker.ERR_BLANK, resultado);
    }

    // TC03: Límite inferior válido (8 caracteres)
    @Test
    public void checkLengthWithinMinLimitTC03(){
        int resultado = checker.check("Abcdefg1", "Abcdefg1");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    //TC04
    @Test
    public void checkLengthUnderLimitTC04(){
        int resultado = checker.check("Abcdef1", "Abcdef1" );
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }

    //TC05
    @Test
    public void checkLengthWithinMaxLimitTC05(){
        int resultado = checker.check(
                "AbcdefghijAbcdefghi1", "AbcdefghijAbcdefghi1" );
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    //TC06
    @Test
    public void checkLengthBeyondLimit(){
        int resultado = checker.check(
                "AbcdefghijAbcdefghi12", "AbcdefghijAbcdefghi12" );
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }
}
