package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

  @Test
  public void helloResourceMustSayHello() {
    String text = target("/greetings")
        .request()
        .get(String.class);
    
    assertEquals("Hello!", text);
  }

  @Test
  public void greetingTest() {
    String text = target("/greetings")
        .queryParam("name", "Jacob")
        .request()
        .get(String.class);
    
    assertEquals("Jacob", text);
  }

}
