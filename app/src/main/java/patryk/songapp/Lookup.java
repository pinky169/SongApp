package patryk.songapp;

import android.text.TextUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import patryk.songapp.enums.Entity;
import patryk.songapp.enums.Sort;

/**
 * Request object for the iTunes lookup API.
 */
public class Lookup implements Serializable {

	private static final long serialVersionUID = 1479238701182L;

	private static final String API_ENDPOINT = "https://itunes.apple.com/lookup?";

	private static final String ID = "id";
	private static final String AMG_ARTIST_ID = "amgArtistId";
	private static final String AMG_ALBUM_ID = "amgAlbumId";
	private static final String AMG_VIDEO_ID = "amgVideoId";
	private static final String UPC = "upc";
	private static final String ISBN = "isbn";

	private final Map<String, Set<String>> map;
	private Entity entity;
	private int limit;
	private Sort sort;

	/**
	 * No-arg constructor.
	 */
	public Lookup() {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		map.put(ID, new HashSet<String>());
		map.put(AMG_ARTIST_ID, new HashSet<String>());
		map.put(AMG_ALBUM_ID, new HashSet<String>());
		map.put(AMG_VIDEO_ID, new HashSet<String>());
		map.put(UPC, new HashSet<String>());
		map.put(ISBN, new HashSet<String>());
		this.map = Collections.unmodifiableMap(map);
	}

	/**
	 * Execute this iTunes Lookup API request.
	 * 
	 * @return parsed {@link Response} from iTunes
	 * @throws RuntimeException
	 *             wrapping any {@link IOException} thrown performing the
	 *             request or parsing the response
	 * @see #execute(Connector)
	 */
	public Response execute() {
		return execute(URLConnector.INSTANCE);
	}

	/**
	 * Execute this iTunes Lookup API request using the provided
	 * {@link Connector} implementation.
	 * 
	 * @param connector
	 *            {@link Connector} instance, not {@code null}
	 * @return parsed {@link Response} from iTunes
	 * @throws IllegalArgumentException
	 *             if argument {@code null}
	 * @throws RuntimeException
	 *             wrapping any {@link IOException} thrown performing the
	 *             request or parsing the response
	 * @see #execute()
	 */
	public Response execute(Connector connector) {
		if (connector == null) {
			throw new IllegalArgumentException("connector can not be null");
		}
		try {
			String response = connector.get(build());
			return Response.READER.readValue(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return modifiable {@link Set} containing all ids currently set in this
	 *         {@link Lookup}, never {@code null}
	 */
	public Set<String> getIds() {
		return map.get(ID);
	}

	/**
	 * @param id
	 *            an iTunes item id, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addId(String id) {
		if (id != null) {
			map.get(ID).add(id);
		}
		return this;
	}

	/**
	 * @param ids
	 *            a {@link Collection} of iTunes item ids, can be {@code null}
	 *            or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setIds(Collection<String> ids) {
		map.get(ID).clear();
		if (ids != null) {
			for (String id : ids) {
				addId(id);
			}
		}
		return this;
	}

	/**
	 * @return modifiable {@link Set} containing all AMG artist ids currently
	 *         set in this {@link Lookup}, never {@code null}
	 */
	public Set<String> getAmgArtistIds() {
		return map.get(AMG_ARTIST_ID);
	}

	/**
	 * @param amgArtistId
	 *            an All Music artist id, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addAmgArtistId(String amgArtistId) {
		if (amgArtistId != null) {
			map.get(AMG_ARTIST_ID).add(amgArtistId);
		}
		return this;
	}

	/**
	 * @param amgArtistIds
	 *            a {@link Collection} of All Music artist ids, can be
	 *            {@code null} or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setAmgArtistIds(Collection<String> amgArtistIds) {
		map.get(AMG_ARTIST_ID).clear();
		if (amgArtistIds != null) {
			for (String amgArtistId : amgArtistIds) {
				addAmgArtistId(amgArtistId);
			}
		}
		return this;
	}

	/**
	 * @return modifiable {@link Set} containing all AMG album ids currently set
	 *         in this {@link Lookup}, never {@code null}
	 */
	public Set<String> getAmgAlbumIds() {
		return map.get(AMG_ALBUM_ID);
	}

	/**
	 * @param amgAlbumId
	 *            an All Music album id, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addAmgAlbumId(String amgAlbumId) {
		if (amgAlbumId != null) {
			map.get(AMG_ALBUM_ID).add(amgAlbumId);
		}
		return this;
	}

	/**
	 * @param amgAlbumIds
	 *            a {@link Collection} of All Music album ids, can be
	 *            {@code null} or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setAmgAlbumId(Collection<String> amgAlbumIds) {
		map.get(AMG_ALBUM_ID).clear();
		if (amgAlbumIds != null) {
			for (String amgAlbumId : amgAlbumIds) {
				addAmgAlbumId(amgAlbumId);
			}
		}
		return this;
	}

	/**
	 * @return modifiable {@link Set} containing all AMG video ids currently set
	 *         in this {@link Lookup}, never {@code null}
	 */
	public Set<String> getAmgVideoIds() {
		return map.get(AMG_VIDEO_ID);
	}

	/**
	 * @param amgVideoId
	 *            an All Music video id, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addAmgVideoId(String amgVideoId) {
		if (amgVideoId != null) {
			map.get(AMG_VIDEO_ID).add(amgVideoId);
		}
		return this;
	}

	/**
	 * @param amgVideoIds
	 *            a {@link Collection} of All Music video ids, can be
	 *            {@code null} or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setAmgVideoIds(Collection<String> amgVideoIds) {
		map.get(AMG_VIDEO_ID).clear();
		if (amgVideoIds != null) {
			for (String amgVideoId : amgVideoIds) {
				addAmgVideoId(amgVideoId);
			}
		}
		return this;
	}

	/**
	 * @return modifiable {@link Set} containing all UPCs currently set in this
	 *         {@link Lookup}, never {@code null}
	 */
	public Set<String> getUpcs() {
		return map.get(UPC);
	}

	/**
	 * @param upc
	 *            a UPC, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addUpc(String upc) {
		if (upc != null) {
			map.get(UPC).add(upc);
		}
		return this;
	}

	/**
	 * @param upcs
	 *            a {@link Collection} of UPCs, can be
	 *            {@code null} or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setUpcs(Collection<String> upcs) {
		map.get(UPC).clear();
		if (upcs != null) {
			for (String upc : upcs) {
				addUpc(upc);
			}
		}
		return this;
	}

	/**
	 * @return modifiable {@link Set} containing all ISBNs currently set in this
	 *         {@link Lookup}, never {@code null}
	 */
	public Set<String> getIsbns() {
		return map.get(ISBN);
	}

	/**
	 * @param isbn
	 *            an ISBN, can be {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup addIsbn(String isbn) {
		if (isbn != null) {
			map.get(ISBN).add(isbn);
		}
		return this;
	}

	/**
	 * @param isbns
	 *            a {@link Collection} of ISBNs, can be
	 *            {@code null} or empty
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setIsbns(Collection<String> isbns) {
		map.get(ISBN).clear();
		if (isbns != null) {
			for (String isbn : isbns) {
				addIsbn(isbn);
			}
		}
		return this;
	}

	/**
	 * @return an {@link Entity}, or {@code null}
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            an {@link Entity}, or {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setEntity(Entity entity) {
		this.entity = entity;
		return this;
	}

	/**
	 * @return maximum number of results to include in the response, or 0 if not set
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the maximum number of results to include in the response
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	/**
	 * @return {@link Sort} instance, or {@code null}
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            a {@link Sort} instance, or {@code null}
	 * @return {@code this} instance for method chaining
	 */
	public Lookup setSort(Sort sort) {
		this.sort = sort;
		return this;
	}

	/**
	 * Create the request url for this {@link Lookup}
	 * 
	 * @return full request url {@link String} matching this {@link Lookup}
	 */
	public String build() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Set<String>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				String q = URLEncoder.encode(TextUtils.join(",", entry.getValue()));
				sb.append(entry.getKey()).append("=").append(q);
			}
		}
		if (entity != null) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append("entity").append("=").append(entity.getName());
		}
		if (limit > 0) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append("limit").append("=").append(limit);
		}
		if (sort != null) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append("sort").append("=").append(sort.toString());
		}
		return API_ENDPOINT + sb.toString();
	}

}
