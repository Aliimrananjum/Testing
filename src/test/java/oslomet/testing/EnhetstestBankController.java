package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void hentBetalinger_loggetInn(){
        List<Transaksjon> Transing = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(0001, "01010110523" ,500.0, "2022-01-01", "Test" , "Ja", "12345678888");
        Transing.add(enTransaksjon);
        when(sjekk.loggetInn()).thenReturn("01010110523");


    }
    @Test
    public void registrerBetaling_loggetInn(){

        Transaksjon enTransaksjon = new Transaksjon(0001, "01010110523" ,500.0, "2022-01-01", "Test" , "Ja", "12345678888");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        String resultat = bankController.registrerBetaling(enTransaksjon);
        assertEquals("OK",resultat);
    }

    @Test
    public void registrerBetaling_ikkeLoggetInn(){

        Transaksjon enTransaksjon = new Transaksjon(0001, "01010110523" ,500.0, "2022-01-01", "Test" , "Ja", "12345678888");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn(){
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_loggetInn(){

        List<Transaksjon> Transing = new ArrayList<>();
        Transaksjon enTransaksjon = new Transaksjon(0001, "01010110523" ,500.0, "2022-01-01", "Test" , "Ja", "12345678888");
        Transing.add(enTransaksjon);
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", Transing);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(enKonto);

        Konto resultat = bankController.hentTransaksjoner("01010110523","2022-01-01","2022-01-01");

        assertEquals(Transing, resultat.getTransaksjoner());
    }

    @Test
    public void hentTransaksjoner_IkkeloggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("01010110523","2022-01-01","2022-01-01");

        assertNull(resultat);

    }
    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }
}

