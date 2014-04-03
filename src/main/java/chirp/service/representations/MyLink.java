package chirp.service.representations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This home-grown link works for both JSON and XML out of the box. Whereas
 * getting javax.ws.rs.Link to work with JSON is tricky. However I've found a
 * way to do that by with a custom JSON serializer that converts
 * javax.ws.rs.Link into JSON.  That replaces any need I have for MyLink
 * 
 * @see JsonLinksSerializer
 */
@XmlRootElement(name = "link")
@XmlType(propOrder = { "rel", "ref" })
@Deprecated
public class MyLink {

	private String rel;

	private URI ref;

	private MyLink() {
	}

	private MyLink(String rel, URI ref) {
		super();
		this.rel = rel;
		this.ref = ref;
	}

	@XmlAttribute
	public URI getRef() {
		return ref;
	}

	@SuppressWarnings("unused")
	private void setRef(URI ref) {
		this.ref = ref;
	}

	@XmlAttribute
	public String getRel() {
		return rel;
	}

	@SuppressWarnings("unused")
	private void setRel(String rel) {
		this.rel = rel;
	}

	public static MyLink link(String rel, String pattern, Object... args) {
		try {
			String urlPath = String.format(pattern, args);
			URI uri = new URI(urlPath);
			MyLink result = new MyLink(rel, uri);
			return result;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static MyLink link(String rel, URI uri) {
		MyLink result = new MyLink(rel, uri);
		return result;
	}

	public static MyLink link(Link link) {
		String rel = link.getRel();
		URI uri = link.getUri();
		MyLink result = new MyLink(rel, uri);
		return result;
	}

	public static List<MyLink> list(Link... links) {
		List<MyLink> result = new ArrayList<MyLink>(links.length);
		for (Link link : links) {
			MyLink item = MyLink.link(link);
			result.add(item);
		}
		return result;
	}

	public static List<MyLink> list(MyLink... links) {
		List<MyLink> result = new ArrayList<MyLink>(links.length);
		for (MyLink link : links) {
			result.add(link);
		}
		return result;
	}
}
