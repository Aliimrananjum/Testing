package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_LoggetInn() {
        List<Kunde> kunder = new ArrayList<>();
        // Legg til eksempelkunder i listen 'kunder'
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKunder()).thenReturn(kunder);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertNull(resultat);
    }

    @Test
    public void lagreKunde_LoggetInn() {
        Kunde nyKunde = new Kunde();
        // Sett opp nyKunde her
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(nyKunde)).thenReturn("OK");

        String resultat = adminKundeController.lagreKunde(nyKunde);

        assertEquals("OK", resultat);
    }

    @Test
    public void endreKunde_LoggetInn() {
        Kunde eksisterendeKunde = new Kunde();
        // Sett opp eksisterendeKunde her
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(eksisterendeKunde)).thenReturn("OK");

        String resultat = adminKundeController.endre(eksisterendeKunde);

        assertEquals("OK", resultat);
    }

    @Test
    public void endreKunde_IkkeLoggetInn() {
        Kunde eksisterendeKunde = new Kunde();
        // Sett opp eksisterendeKunde her
        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.endre(eksisterendeKunde);

        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_LoggetInn() {
        String personnummerTilSletting = "01010110523";
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde(personnummerTilSletting)).thenReturn("OK");

        String resultat = adminKundeController.slett(personnummerTilSletting);

        assertEquals("OK", resultat);
    }
    @Test
    public void slettKunde_IkkeLoggetInn() {
        String personnummerTilSletting = "01010110523";
        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.slett(personnummerTilSletting);

        assertEquals("Ikke logget inn", resultat);
    }

}
