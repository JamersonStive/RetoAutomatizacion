//Librerías necesarias para la automatización usando el patrón screenplay
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Switch;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.targets.TargetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.Tasks.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

@ExtendWith(SerenityTest.class)
public class AutomatizacionBancolombia {

    // Pasos a Realizar en la automatización

    private static final Target CORPORATIVOS = Target.the("Corporativos option")
            .located(By.linkText("Corporativos"));
    private static final Target CAPITAL_INTELIGENTE = Target.the("Capital Inteligente option")
            .located(By.linkText("Capital Inteligente"));
    private static final Target ACTUALIDAD_ECONOMICA = Target.the("Actualidad económica option")
            .located(By.linkText("Actualidad económica"));
    private static final Target VER_MAS_PUBLICACIONES = Target.the("Ver más publicaciones")
            .located(By.linkText("Ver más publicaciones"));
    private static final Target TARGET_REPORT = Target.the("Target report")
            .locatedBy("//a[contains(text(), 'OPEP+ aumentará la oferta de petróleo en julio de 2021')]");
    private static final Target DESCARGAR_PDF_LINK = Target.the("Descargar PDF link")
            .locatedBy("//a[contains(text(), 'Descargue aquí el informe en PDF del sector petróleo de mayo del 2021')]");
    private static final Target REPORT_SECTORIAL_TEXT = Target.the("Reporte sectorial text")
            .located(By.xpath("//*[contains(text(), 'Reporte sectorial')]"));

    private Actor usuario = Actor.named("Usuario");

    @BeforeEach
    public void configurarActor() {
	
	// Configurar el driver de Selenium (en este caso, ChromeDriver)
        WebDriverManager.chromedriver().setup();

	// Inicializar el WebDriver
        WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
        usuario.can(BrowseTheWeb.with(driver));
    }

    @Test
     public void automatizacionBancolombia() {
        givenThat(usuario).wasAbleTo(Open.url("https://www.bancolombia.com/personas"));
        when(usuario).attemptsTo(
                Click.on(CORPORATIVOS),
                Click.on(CAPITAL_INTELIGENTE),
                Switch.toNewWindow(),
                Click.on(ACTUALIDAD_ECONOMICA),
                Click.on(VER_MAS_PUBLICACIONES).until(Text.of(TARGET_REPORT).asAString().contains("OPEP+ aumentará la oferta de petróleo en julio de 2021")),
                Click.on(TARGET_REPORT),
                Switch.toNewWindow(),
                should(seeThat(WebElementQuestion.of(DOWNLOAD_PDF_LINK), isVisible())),
                Click.on(DOWNLOAD_PDF_LINK)
        );
        then(usuario).should(seeThat(WebElementQuestion.of(REPORT_SECTORIAL_TEXT), isVisible()));
    }
}