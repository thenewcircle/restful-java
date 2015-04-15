package chirp.service.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import chirp.representations.ChirpRep;
import chirp.representations.UserRep;

@Provider
public class CsvMessageBodyWriter implements MessageBodyWriter {

  @Override
  public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return mediaType.toString().equals("text/csv");
  }

  @Override
  public long getSize(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
    if (t instanceof UserRep) {
      UserRep user = (UserRep)t;
      String csv = String.format("\"%s\",\"%s\"", user.getUsername(), user.getRealname());
      entityStream.write(csv.getBytes());

    } else if (t instanceof ChirpRep) {
      ChirpRep chirp = (ChirpRep)t;
      String csv = String.format("%s,\"%s\"", chirp.getId(), chirp.getContent());
      entityStream.write(csv.getBytes());
    
    } else if (t instanceof Collection) {
      Collection col = (Collection)t;
      for (Object o : col) {
        writeTo(o, o.getClass(), null, new Annotation[0], mediaType, httpHeaders, entityStream);
        entityStream.write("\n".getBytes());
      }
    }
  }
}
