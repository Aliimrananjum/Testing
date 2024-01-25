package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    /*
    @Before
    public void initSession(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }
     */
    @Test
    public void test_loggInnAdmin(){
        String resultat = sikkerhetsController.loggInnAdmin("Admin", "Admin");
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_ikke_LoggInnAdmin(){
        String resultat = sikkerhetsController.loggInnAdmin("Bruker", "Bruker");
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_loggUt(){
        sikkerhetsController.loggUt();
        assertNull(session.getAttribute("Innlogget"));
    }

    @Test
    public void test_sjekkLoggetInn_OK(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");
        String resultat = sikkerhetsController.sjekkLoggInn("02018736112","HeiHeiHei");
        assertEquals("OK",resultat);
    }
    @Test
    public void test_sjekkLoggetInn_personnummer(){
        String resultat = sikkerhetsController.sjekkLoggInn("020187112","HeiHeiHei");
        assertEquals("Feil i personnummer",resultat);
    }
    @Test
    public void test_sjekkLoggetInn_personnummer_passord(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil");
        String resultat = sikkerhetsController.sjekkLoggInn("02018736112","Heiiheihei");
        assertEquals("Feil i personnummer eller passord",resultat);
    }
    @Test
    public void test_sjekkLoggetInn_passord(){
        String resultat = sikkerhetsController.sjekkLoggInn("02018736112","Heii");
        assertEquals("Feil i passord",resultat);
    }

    @Test
    public void test_LoggetInn_OK() {
        Map<String,Object> attributes = new HashMap<String,Object>();
        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());
        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
        // arrange
        session.setAttribute("Innlogget","12345678901");
        // act
        String resultat = sikkerhetsController.loggetInn();
        // assert
        assertEquals("12345678901", resultat);
    }


    @Test
    public void test_LoggetInn_Null() {
        // arrange
        session.setAttribute("Innlogget","12345678901");
        // act
        String resultat = sikkerhetsController.loggetInn();
        // assert
        assertEquals(null, resultat);
    }
}
