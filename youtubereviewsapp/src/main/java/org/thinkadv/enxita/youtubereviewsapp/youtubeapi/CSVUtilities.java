package org.thinkadv.enxita.youtubereviewsapp.youtubeapi;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.thinkadv.enxita.youtubereviewsapp.model.Review;

public class CSVUtilities {

	private static final Logger logger = Logger.getLogger(CSVUtilities.class);

	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final String[] FILE_HEADER = { "VideoId", "Review Title",
			"Review Description", "Channel Title", "Published Date" };

	public static final String currentDir = System.getProperty("user.dir")
			+ "/files";

	public static void writeToCSV(Set<Review> reviewsList, String phoneName)
			throws IOException {

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		String fileName = Search.getInstance().getFolderLocation() + "/" + phoneName + ".csv";
		// Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT
				.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			// initialize FileWriter object
			fileWriter = new FileWriter(fileName);

			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			// Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);

			// Write a new review object list to the CSV file
			for (Review review : reviewsList) {
				List<String> reviewItemList = new ArrayList<String>();
				reviewItemList.add(review.getVideoId());
				reviewItemList.add(review.getReviewTitle());
				reviewItemList.add(review.getReviewDescription());
				reviewItemList.add(review.getChannelTitle());
				reviewItemList.add(String.valueOf(review.getPublishedDate()));
				csvFilePrinter.printRecord(reviewItemList);
			}

		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				logger.error("Failed to flush or close the writer"
						+ e.getMessage());

			}
		}

	}

	public static Set<Review> readFromCSV(String mobilePhoneName)
			throws IOException, ParseException {

		Set<Review> reviewsList = new HashSet<Review>();

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		String fileName = Search.getInstance().getFolderLocation() + "/" + mobilePhoneName + ".csv";

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);

		try {

			// initialize FileReader object
			fileReader = new FileReader(fileName);

			// initialize CSVParser object
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				// Create a new student object and fill his data
				Review review = new Review();
				review.setVideoId(record.get(FILE_HEADER[0]));
				review.setReviewTitle(record.get(FILE_HEADER[1]));
				review.setReviewDescription(record.get(FILE_HEADER[2]));
				review.setChannelTitle(record.get(FILE_HEADER[3]));
				review.setPublishedDate(Utilities.convertStringToDate(record.get(FILE_HEADER[4])));
				reviewsList.add(review);
			}

		} finally {
			fileReader.close();
			csvFileParser.close();
		}
		return reviewsList;

	}

}
