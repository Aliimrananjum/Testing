package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn(){

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKonti()).thenReturn(konti);
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_ikkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        assertNull(resultat);

    }
    @Test
    public void registrerKonto_loggetInn(){

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.registrerKonto((any(Konto.class)))).thenReturn("OK");
        String resultat = adminKontoController.registrerKonto(konto1);
        assertEquals("OK",resultat);
    }

    @Test
    public void registrerKonto_ikkeLoggetInn(){

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.registrerKonto(konto1);
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void endreKonto_loggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKonto(konto1)).thenReturn("OK");
        String resultat = adminKontoController.endreKonto(konto1);
        assertEquals("OK",resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.endreKonto(konto1);
        assertEquals("Ikke innlogget",resultat);
    }



    @Test
    public void slettKonto_loggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKonto(anyString())).thenReturn("OK");
        String resultat = adminKontoController.slettKonto("01010110523");
        assertEquals("OK",resultat);
    }
    @Test
    public void slettKonto_ikkeLoggetInn(){
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.slettKonto("01010110523");
        assertEquals("Ikke innlogget",resultat);
    }

}
