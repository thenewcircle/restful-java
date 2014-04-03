package chirp.service.representations;

import static org.codehaus.jackson.JsonToken.END_ARRAY;
import static org.codehaus.jackson.JsonToken.END_OBJECT;
import static org.codehaus.jackson.JsonToken.FIELD_NAME;
import static org.codehaus.jackson.JsonToken.START_ARRAY;
import static org.codehaus.jackson.JsonToken.START_OBJECT;
import static org.codehaus.jackson.JsonToken.VALUE_STRING;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * JSON support for {@link javax.ws.rs.core.Link}, using <a
 * href="http://stateless.co/hal_specification.html">"application/hal+json"</a>
 * style encoding.
 */
public class JsonLinksDeserializer extends JsonDeserializer<Collection<Link>> {

	@Override
	public Collection<Link> deserialize(JsonParser jp,
			DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		List<Link> results = new ArrayList<Link>();
		while (true) {
			JsonToken token = jp.nextToken();
			if (token.equals(END_OBJECT))
				break;
			else if (!token.equals(FIELD_NAME)) {
				ctxt.mappingException(Link.class);
			}
			String rel = jp.getText();
			token = jp.nextToken();
			if (START_OBJECT.equals(token)) {
				parseLink(jp, ctxt, results, rel);
			} else if (START_ARRAY.equals(token)) {
				parseLinkArray(jp, ctxt, results, rel);
			} else if (VALUE_STRING.equals(token)) {
				String href = jp.getText();
				Link link = Link.fromUri(href).rel(rel).build();
				results.add(link);
			} else {
				ctxt.mappingException(Link.class);
			}
		}
		return results;
	}

	private void parseLinkArray(JsonParser jp, DeserializationContext ctxt,
			List<Link> results, String rel) throws JsonParseException,
			IOException {
		while (true) {
			JsonToken token = jp.nextToken();
			if (END_ARRAY.equals(token)) {
				break;
			} else if (START_OBJECT.equals(token)) {
				parseLink(jp, ctxt, results, rel);
			} else {
				ctxt.mappingException(Link.class);
			}
		}
	}

	private void parseLink(JsonParser jp, DeserializationContext ctxt,
			List<Link> results, String rel) throws JsonParseException,
			IOException {
		Map<String, String> params = new LinkedHashMap<String, String>();
		while (true) {
			JsonToken token = jp.nextToken();
			if (END_OBJECT.equals(token)) {
				break;
			} else if (FIELD_NAME.equals(token)) {
			} else if (VALUE_STRING.equals(token)) {
				String key = jp.getCurrentName();
				String value = jp.getText();
				params.put(key, value);
			} else {
				ctxt.mappingException(Link.class);
			}
		}
		String href = params.remove("href");
		if (href == null) {
			ctxt.mappingException(Link.class);
		}
		Builder builder = Link.fromUri(href);
		builder.rel(rel);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			builder.param(key, value);
		}
		Link link = builder.build();
		results.add(link);
	}

}
