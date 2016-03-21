package org.thinkadv.enxita.youtubereviewsapp.controllers;

import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.thinkadv.enxita.orm.DAOUtils;
import org.thinkadv.enxita.youtubereviewsapp.model.MobilePhone;
import org.thinkadv.enxita.youtubereviewsapp.model.Review;
import org.thinkadv.enxita.youtubereviewsapp.youtubeapi.Search;

@Path("reviews")
public class MobilePhoneReviewsController {

	private static final Logger logger = Logger
			.getLogger(MobilePhoneReviewsController.class);

	@GET
	@Path("/{mobilePhoneName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMobilePhoneReviews(
			@PathParam("mobilePhoneName") String mobilePhoneName) {
		Set<Review> reviewList = null;
		String output = null;
		try {
			reviewList = Search.getInstance().fetchReviews(mobilePhoneName);
			ObjectMapper responseMapper = new ObjectMapper();
			output = responseMapper.writeValueAsString(reviewList);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			output = "Error fetching the results:" + e.getMessage();
			return Response.status(500).entity(output).build();
		}
		return Response.status(200).entity(output).build();
	}

	@GET
		@Path("/mobileslist")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getMobilesList() {
			List<MobilePhone> mobilesList = null;
			String output = null;
			try {
				mobilesList = DAOUtils.getInstance().getMobilePhoneList();
				ObjectMapper responseMapper = new ObjectMapper();
				output = responseMapper.writeValueAsString(mobilesList);
			} catch (Exception e) {
				logger.error("Error:" + e.getMessage());
				output = "Error fetching the results:" + e.getMessage();
				return Response.status(500).entity(output).build();
			}
			return Response.status(200).entity(output).build();
		}
	
	@DELETE
	@Path("/delete/{videoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReviewByVideoid(
			@PathParam("videoId") String videoId) {
		String output = null;
		try {
			DAOUtils.getInstance().deleteReviewByVideoId(videoId);
			output = "Deleted Successfully";
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			output = "Error fetching the results:" + e.getMessage();
			return Response.status(500).entity(output).build();
		}
		return Response.status(200).entity(output).build();
	}
}
