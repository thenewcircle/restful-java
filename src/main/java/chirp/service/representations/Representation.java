package chirp.service.representations;

import static chirp.service.representations.LinkRel.ALTERNATE;
import static chirp.service.representations.LinkRel.EDIT;
import static chirp.service.representations.LinkRel.RELATED;
import static chirp.service.representations.LinkRel.SELF;
import static chirp.service.representations.LinkRel.UP;
import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.PUBLIC_ONLY;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.JaxbAdapter;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * A convenience class for creating hyper-linked XML/JSON representations. All
 * methods to add new links are marked final so that they may safely be called
 * from a constructor. getLinks() and returns a copy so that it may be
 * overridden by a subclass which can append it's own custom links to the
 * output.
 */
@JsonAutoDetect(fieldVisibility = PUBLIC_ONLY, getterVisibility = PUBLIC_ONLY, isGetterVisibility = PUBLIC_ONLY)
public class Representation {

	private Collection<Link> links = new LinkedHashSet<Link>();

	@XmlJavaTypeAdapter(JaxbAdapter.class)
	@XmlElement(name = "link")
	@JsonProperty("_links")
	@JsonSerialize(using = JsonLinksSerializer.class)
	@JsonDeserialize(using = JsonLinksDeserializer.class)
	public Collection<Link> getLinks() {
		Collection<Link> extraLinks = this.includeLinks();
		int size = links.size() + extraLinks.size();
		Collection<Link> result = new ArrayList<Link>(size);
		result.addAll(extraLinks);
		result.addAll(links);
		return result;
	}

	@JsonSetter("_links")
	protected void setLinks(Collection<Link> links) {
		this.links.clear();
		this.links.addAll(links);
	}

	public Collection<Link> getLinks(String rel) {
		ArrayList<Link> results = new ArrayList<Link>(links.size());
		for (Link link : links) {
			String r = link.getRel();
			if (rel.equals(r)) {
				results.add(link);
			}
		}
		return links;
	}

	public Link getLink(String rel) {
		for (Link link : links) {
			String r = link.getRel();
			if (rel.equals(r)) {
				return link;
			}
		}
		return null;
	}

	/**
	 * Provides a list of hyperlinks to automatically include in the response
	 * from {@link #getLinks()}. The default implementation adds zero links.
	 * Subclasses should override. This is useful when including last-minute
	 * links populated with @InjectedLink. Otherwise the easier way to include
	 * links is to call {@link #link(Link)} from the subclass constructor.
	 */
	protected Collection<Link> includeLinks() {
		return Collections.emptyList();
	}

	public final void link(Link link) {
		links.add(link);
	}

	public final void link(String rel, URI uri) {
		Link link = Link.fromUri(uri).rel(rel).build();
		link(link);
	}

	public final void link(String rel, String path, Object... args) {
		Link link = Link.fromPath(path).rel(rel).build(args);
		link(link);
	}

	public final void linkSelf(String path, Object... args) {
		link(SELF, path, args);
	}

	public final void linkSelf(URI uri) {
		link(SELF, uri);
	}

	public final void linkCanonical(String path, Object... args) {
		link(SELF, path, args);
	}

	public final void linkCanonical(URI uri) {
		link(SELF, uri);
	}

	public final void linkAlternate(MediaType type, String path, Object... args) {
		Link link = Link.fromPath(path).rel(ALTERNATE).build(args);
		link(link);
	}

	public final void linkAlternate(MediaType type, URI uri) {
		Link link = Link.fromUri(uri).rel(ALTERNATE).build();
		link(link);
	}

	public final void linkEdit(String path, Object... args) {
		link(EDIT, path, args);
	}

	public final void linkEdit(URI uri) {
		link(EDIT, uri);
	}

	public final void linkUp(String path, Object... args) {
		link(UP, path, args);
	}

	public final void linkUp(URI uri) {
		link(UP, uri);
	}

	public final void linkRelated(String title, String path, Object... args) {
		Link link = Link.fromUri(path).rel(RELATED).build(args);
		link(link);
	}

	public final void linkRelated(String title, URI uri) {
		Link link = Link.fromUri(uri).rel(RELATED).build();
		link(link);
	}
	
}
