package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

    @Test
    public void testGetHello() {
        final String hello = target("/greetings").request().get(String.class);
        assertEquals("Hello!", hello);
    }

    @Test
    public void helloResourceMustSayHelloWithName() {
        final String hello = target("/greetings/NewCircle").request().get(String.class);
        assertEquals("Hello, NewCircle!", hello);
    }

    @Test
    public void helloResourceMustSayHelloWithPathParam() {
        final String hello = target("/greetings/NewCircle").request().get(String.class);
        assertEquals("Hello, NewCircle!", hello);
    }

}
