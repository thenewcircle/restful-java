package chirp.service.representations;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Link;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * This allows the Link used with JAX-RS and the Inject-Link to convert to JSON.  It's a replacement
 * for the need to use MyLink.
 */
public class JsonHalLinkSerializer extends JsonSerializer<List<Link>> {

	@Override
	public void serialize(List<Link> links, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
		for (Link link : links) {
			List<String> rels = link.getRels();
			Map<String, String> params = link.getParams();
			URI href = link.getUri();
			if(rels.isEmpty()) {
				rels=Collections.singletonList("other");
			}
			for (String rel : rels) {
				Map<String, String> newEntry = new LinkedHashMap<String, String>();
				for (Map.Entry<String, String> param : params.entrySet()) {
					String key = param.getKey();
					String value = param.getValue();
					if ("rel".equals(key))
						continue;
					newEntry.put(key, value);
				}
				if (href != null) {
					newEntry.put("href", href.toString());
				}
				Object oldEntry = jsonData.get(rel);
				if (oldEntry == null) {
					jsonData.put(rel, newEntry);
				} else if (oldEntry instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) oldEntry;
					list.add(newEntry);
				} else {
					List<Object> list = new ArrayList<>();
					list.add(oldEntry);
					list.add(newEntry);
					jsonData.put(rel, list);
				}
			}
		}
		provider.defaultSerializeValue(jsonData, jgen);
	}

}
