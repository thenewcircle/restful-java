package chirp.service.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import chirp.model.User;
import chirp.representations.UserRep;

@Provider
public class CsvMessageBodyReader implements MessageBodyReader {

  @Override
  public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    if (mediaType.toString().equals("text/csv") == false) return false;
    return UserRep.class.equals(type);
  }

  @Override
  public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
    String body = toString(entityStream);
    String[] parts = body.split(",");
    return new UserRep(parts[0], parts[1]);
  }

  public static String toString(InputStream is) throws IOException {
    return toString(new BufferedReader(new InputStreamReader(is)));
  }

  public static String toString(Reader reader) throws IOException {
    try {
      String line = "";
      StringBuilder builder = new StringBuilder();
      BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader);

      while ( (line=bufferedReader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }

      return builder.toString();

    } finally {
      reader.close();
    }
  }
}
