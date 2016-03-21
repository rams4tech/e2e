CREATE DATABASE `youtubereviews` /*!40100 DEFAULT CHARACTER SET utf8 */;


CREATE TABLE `mobilephone` (
  `mobileId` int(11) NOT NULL AUTO_INCREMENT,
  `moibleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mobileId`),
  UNIQUE KEY `moibleName` (`moibleName`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


CREATE TABLE `review` (
  `videoId` varchar(255) NOT NULL,
  `channelTitle` varchar(255) DEFAULT NULL,
  `publishedDate` datetime DEFAULT NULL,
  `reviewDescription` varchar(255) DEFAULT NULL,
  `reviewTitle` varchar(255) DEFAULT NULL,
  `mobileId` int(11) NOT NULL,
  PRIMARY KEY (`videoId`),
  KEY `FK91B3E378536856FB` (`mobileId`),
  CONSTRAINT `FK91B3E378536856FB` FOREIGN KEY (`mobileId`) REFERENCES `mobilephone` (`mobileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
