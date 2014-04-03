package chirp.service.representations;

/**
 * Known Link rel types defined by IANA.
 * 
 * @see https://www.iana.org/assignments/link-relations/link-relations.xhtml
 */
public class LinkRel {
	/**
	 * Refers to a resource that is the subject of the link's context.
	 */
	public static final String ABOUT = "about";

	/**
	 * Refers to a substitute for this context
	 */
	public static final String ALTERNATE = "alternate";

	/**
	 * Refers to an appendix.
	 */
	public static final String APPENDIX = "appendix";

	/**
	 * Refers to a collection of records, documents, or other materials of
	 * historical interest.
	 */
	public static final String ARCHIVES = "archives";

	/**
	 * Refers to the context's author.
	 */
	public static final String AUTHOR = "author";

	/**
	 * Gives a permanent link to use for bookmarking purposes.
	 */
	public static final String BOOKMARK = "bookmark";

	/**
	 * Designates the preferred version of a resource (the IRI and its
	 * contents).
	 */
	public static final String CANONICAL = "canonical";

	/**
	 * Refers to a chapter in a collection of resources.
	 */
	public static final String CHAPTER = "chapter";

	/**
	 * The target IRI points to a resource which represents the collection
	 * resource for the context IRI.
	 */
	public static final String COLLECTION = "collection";

	/**
	 * Refers to a table of contents.
	 */
	public static final String CONTENTS = "contents";

	/**
	 * Refers to a copyright statement that applies to the link's context.
	 */
	public static final String COPYRIGHT = "copyright";

	/**
	 * The target IRI points to a resource where a submission form can be
	 * obtained.
	 */
	public static final String CREATE_FORM = "create-form";

	/**
	 * Refers to a resource containing the most recent item(s) in a collection
	 * of resources.
	 */
	public static final String CURRENT = "current";

	/**
	 * Refers to a resource providing information about the link's context.
	 */
	public static final String DESCRIBED_BY = "describedby";

	/**
	 * The relationship A 'describes' B asserts that resource A provides a
	 * description of resource B. There are no constraints on the format or
	 * representation of either A or B, neither are there any further
	 * constraints on either resource.
	 */
	public static final String DESCRIBES = "describes";

	/**
	 * Refers to a list of patent disclosures made with respect to material for
	 * which 'disclosure' relation is specified.
	 */
	public static final String DISCLOSURE = "disclosure";

	/**
	 * Refers to a resource whose available representations are byte-for-byte
	 * identical with the corresponding representations of the context IRI.
	 */
	public static final String DUPLICATE = "duplicate";

	/**
	 * Refers to a resource that can be used to edit the link's context.
	 */
	public static final String EDIT = "edit";

	/**
	 * The target IRI points to a resource where a submission form for editing
	 * associated resource can be obtained.
	 */
	public static final String EDIT_FORM = "edit-form";

	/**
	 * Refers to a resource that can be used to edit media associated with the
	 * link's context.
	 */
	public static final String EDIT_MEDIA = "edit-media";

	/**
	 * Identifies a related resource that is potentially large and might require
	 * special handling.
	 */
	public static final String ENCLOSURE = "enclosure";

	/**
	 * An IRI that refers to the furthest preceding resource in a series of
	 * resources.
	 */
	public static final String FIRST = "first";

	/**
	 * Refers to a glossary of terms.
	 */
	public static final String GLOSSARY = "glossary";

	/**
	 * Refers to context-sensitive help.
	 */
	public static final String HELP = "help";

	/**
	 * Refers to a resource hosted by the server indicated by the link context.
	 */
	public static final String HOSTS = "hosts";

	/**
	 * Refers to a hub that enables registration for notification of updates to
	 * the context.
	 */
	public static final String HUB = "hub";

	/**
	 * Refers to an icon representing the link's context.
	 */
	public static final String ICON = "icon";

	/**
	 * Refers to an index.
	 */
	public static final String INDEX = "index";

	/**
	 * The target IRI points to a resource that is a member of the collection
	 * represented by the context IRI.
	 */
	public static final String ITEM = "item";

	/**
	 * An IRI that refers to the furthest following resource in a series of
	 * resources.
	 */
	public static final String LAST = "last";

	/**
	 * Points to a resource containing the latest (e.g., current) version of the
	 * context.
	 */
	public static final String LATEST_VERSION = "latest-version";

	/**
	 * Refers to a license associated with this context.
	 */
	public static final String LICENSE = "license";

	/**
	 * Refers to further information about the link's context, expressed as a
	 * LRDD ("Link-based Resource Descriptor Document") resource. See [RFC6415]
	 * for information about processing this relation type in host-meta
	 * documents. When used elsewhere, it refers to additional links and other
	 * metadata. Multiple instances indicate additional LRDD resources. LRDD
	 * resources MUST have an "application/xrd+xml" representation, and MAY have
	 * others.
	 */
	public static final String LRDD = "lrdd";

	/**
	 * The Target IRI points to a Memento, a fixed resource that will not change
	 * state anymore.
	 */
	public static final String MEMENTO = "memento";

	/**
	 * Refers to a resource that can be used to monitor changes in an HTTP
	 * resource.
	 */
	public static final String MONITOR = "monitor";

	/**
	 * Refers to a resource that can be used to monitor changes in a specified
	 * group of HTTP resources.
	 */
	public static final String MONITOR_GROUP = "monitor-group";

	/**
	 * Indicates that the link's context is a part of a series, and that the
	 * next in the series is the link target.
	 */
	public static final String NEXT = "next";

	/**
	 * Refers to the immediately following archive resource.
	 */
	public static final String NEXT_ARCHIVE = "next-archive";

	/**
	 * Indicates that the context’s original author or publisher does not
	 * endorse the link target.
	 */
	public static final String NOFOLLOW = "nofollow";

	/**
	 * Indicates that no referrer information is to be leaked when following the
	 * link.
	 */
	public static final String NOREFERRER = "noreferrer";

	/**
	 * The Target IRI points to an Original Resource.
	 */
	public static final String ORIGINAL = "original";

	/**
	 * Indicates a resource where payment is accepted.
	 */
	public static final String PAYMENT = "payment";

	/**
	 * Points to a resource containing the predecessor version in the version
	 * history.
	 */
	public static final String PREDECESSOR_VERSION = "predecessor-version";

	/**
	 * Indicates that the link target should be preemptively cached.
	 */
	public static final String PREFETCH = "prefetch";

	/**
	 * Indicates that the link's context is a part of a series, and that the
	 * previous in the series is the link target.
	 */
	public static final String PREV = "prev";

	/**
	 * Refers to a resource that provides a preview of the link's context.
	 */
	public static final String PREVIEW = "preview";

	/**
	 * Refers to the previous resource in an ordered series of resources.
	 * Synonym for "prev".
	 */
	public static final String PREVIOUS = "previous";

	/**
	 * Refers to the immediately preceding archive resource.
	 */
	public static final String PREV_ARCHIVE = "prev-archive";

	/**
	 * Refers to a privacy policy associated with the link's context.
	 */
	public static final String PRIVACY_POLICY = "privacy-policy";

	/**
	 * Identifying that a resource representation conforms to a certain profile,
	 * without affecting the non-profile semantics of the resource
	 * representation.
	 */
	public static final String PROFILE = "profile";

	/**
	 * Identifies a related resource.
	 */
	public static final String RELATED = "related";

	/**
	 * Identifies a resource that is a reply to the context of the link.
	 */
	public static final String REPLIES = "replies";

	/**
	 * Refers to a resource that can be used to search through the link's
	 * context and related resources.
	 */
	public static final String SEARCH = "search";

	/**
	 * Refers to a section in a collection of resources.
	 */
	public static final String SECTION = "section";

	/**
	 * Conveys an identifier for the link's context.
	 */
	public static final String SELF = "self";

	/**
	 * Indicates a URI that can be used to retrieve a service document.
	 */
	public static final String SERVICE = "service";

	/**
	 * Refers to the first resource in a collection of resources.
	 */
	public static final String START = "start";

	/**
	 * Refers to a stylesheet.
	 */
	public static final String STYLESHEET = "stylesheet";

	/**
	 * Refers to a resource serving as a subsection in a collection of
	 * resources.
	 */
	public static final String SUBSECTION = "subsection";

	/**
	 * Points to a resource containing the successor version in the version
	 * history.
	 */
	public static final String SUCCESSOR_VERSION = "successor-version";

	/**
	 * Gives a tag (identified by the given address) that applies to the current
	 * document.
	 */
	public static final String TAG = "tag";

	/**
	 * Refers to the terms of service associated with the link's context.
	 */
	public static final String TERMS_OF_SERVICE = "terms-of-service";

	/**
	 * The Target IRI points to a TimeGate for an Original Resource.
	 */
	public static final String TIMEGATE = "timegate";

	/**
	 * The Target IRI points to a TimeMap for an Original Resource.
	 */
	public static final String TIMEMAP = "timemap";

	/**
	 * Refers to a resource identifying the abstract semantic type of which the
	 * link's context is considered to be an instance.
	 */
	public static final String TYPE = "type";

	/**
	 * Refers to a parent document in a hierarchy of documents.
	 */
	public static final String UP = "up";

	/**
	 * Points to a resource containing the version history for the context.
	 */
	public static final String VERSION_HISTORY = "version-history";

	/**
	 * Identifies a resource that is the source of the information in the link's
	 * context.
	 */
	public static final String VIA = "via";

	/**
	 * Points to a working copy for this resource.
	 */
	public static final String WORKING_COPY = "working-copy";

	/**
	 * Points to the versioned resource from which this working copy was
	 * obtained.
	 */
	public static final String WORKING_COPY_OF = "working-copy-of";

}
