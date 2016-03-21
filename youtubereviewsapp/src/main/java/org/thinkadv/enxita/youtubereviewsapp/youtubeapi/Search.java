package org.thinkadv.enxita.youtubereviewsapp.youtubeapi;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.thinkadv.enxita.orm.DAOUtils;
import org.thinkadv.enxita.orm.ReviewsCache;
import org.thinkadv.enxita.youtubereviewsapp.model.Review;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

/**
 * Print a list of videos matching a search term.
 *
 */
public class Search {

	/**
	 * Define a global variable that identifies the name of a file that contains
	 * the developer's API key.
	 */
	private static final String PROPERTIES_FILENAME = "youtube.properties";

	private static final Logger logger = Logger.getLogger(Search.class);

	private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
	
	private static Search search = null;
	
	/**
	 * Define a global instance of a Youtube object, which will be used to make
	 * YouTube Data API requests.
	 */
	private YouTube youtube;

	private Properties properties = new Properties();

	private Search() {
		initialize();

	}
	
	public static Search getInstance() {
		if (search == null) {
			search = new Search();
		}
		
		return search;
	}

	private void initialize() {
		InputStream inStream = null;
		try {
			inStream = Search.class.getResourceAsStream("/"
					+ PROPERTIES_FILENAME);
			properties.load(inStream);

		} catch (IOException e) {
			logger.error("There was an error reading " + PROPERTIES_FILENAME
					+ ": " + e.getCause() + " : " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(inStream);
		}
	}

	public Set<Review> fetchReviews(String mobilePhoneName) throws Exception {

		Set<Review> reviewsList = new HashSet<Review>();

			if (StringUtils.isNotBlank(mobilePhoneName)) {
//				reviewsList = DAOUtils.getInstance().getReviewsForMobilePhone(mobilePhoneName);
				reviewsList = ReviewsCache.getInstance().getCacheMap().get(mobilePhoneName);
				if (reviewsList == null || reviewsList.size() == 0) {

					// This object is used to make YouTube Data API requests.
					// The last
					// argument is required, but since we don't need anything
					// initialized when the HttpRequest is initialized, we
					// override
					// the interface and provide a no-op function.
					youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT,
							Auth.JSON_FACTORY, new HttpRequestInitializer() {
								public void initialize(HttpRequest request)
										throws IOException {
								}
							}).setApplicationName("mobilephonereviewsrams")
							.build();
					// Define the API request for retrieving search results.
					YouTube.Search.List search = youtube.search().list(
							"id,snippet");
					// Set your developer key from the {{ Google Cloud Console
					// }} for
					// non-authenticated requests. See:
					// {{ https://cloud.google.com/console }}
					String apiKey = properties.getProperty("youtube.apikey");
					search.setKey(apiKey);
					search.setQ(mobilePhoneName);
					// Restrict the search results to only include videos. See:
					// https://developers.google.com/youtube/v3/docs/search/list#type
					search.setType("video");
					// To increase efficiency, only retrieve the fields that the
					// application uses.
					search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/channelTitle,snippet/publishedAt)");
					search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
					// Call the API and print results.
					SearchListResponse searchResponse = search.execute();
					List<SearchResult> searchResultList = searchResponse
							.getItems();
					if (searchResultList != null) {
						reviewsList = fetchSearchResults(
								searchResultList.iterator(), mobilePhoneName);
						CSVUtilities.writeToCSV(reviewsList, mobilePhoneName);
						reviewsList = CSVUtilities.readFromCSV(mobilePhoneName);
						DAOUtils.getInstance().saveMobilePhoneWithReviews(mobilePhoneName, reviewsList);
						ReviewsCache.getInstance().getCacheMap().put(mobilePhoneName, reviewsList);
					}

				}
			}
		return reviewsList;

	}

	private Set<Review> fetchSearchResults(
			Iterator<SearchResult> iteratorSearchResults, String query) throws ParseException {

		Set<Review> reviewsList = new HashSet<Review>();

		if (!iteratorSearchResults.hasNext()) {
			System.out.println(" There aren't any results for your query.");
		}

		Review review = null;

		while (iteratorSearchResults.hasNext()) {

			review = new Review();

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();

			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video")) {

				review.setVideoId(rId.getVideoId());
				review.setReviewTitle(singleVideo.getSnippet().getTitle());
				review.setReviewDescription(singleVideo.getSnippet()
						.getDescription());
				review.setChannelTitle(singleVideo.getSnippet()
						.getChannelTitle());
				review.setPublishedDate(Utilities.convertGoogleDate(singleVideo
						.getSnippet().getPublishedAt()));

				reviewsList.add(review);
			}
		}
		return reviewsList;
	}
	
	public String getFolderLocation() {
		return properties.getProperty("csv.filelocation");
	}
}
