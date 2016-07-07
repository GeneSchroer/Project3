-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: project3
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `project3`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `project3` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `project3`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `Id` int(11) NOT NULL,
  `DateOpened` date DEFAULT NULL,
  `Client` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Client` (`Client`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`Client`) REFERENCES `client` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2016-07-06',222222222);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `accountdetails`
--

DROP TABLE IF EXISTS `accountdetails`;
/*!50001 DROP VIEW IF EXISTS `accountdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `accountdetails` AS SELECT 
 1 AS `Id`,
 1 AS `DateOpened`,
 1 AS `Client`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Address`,
 1 AS `ZipCode`,
 1 AS `Telephone`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `boughtstocks`
--

DROP TABLE IF EXISTS `boughtstocks`;
/*!50001 DROP VIEW IF EXISTS `boughtstocks`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `boughtstocks` AS SELECT 
 1 AS `AccountId`,
 1 AS `StockSymbol`,
 1 AS `NumShares`,
 1 AS `PricePerShare`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `Email` char(32) DEFAULT NULL,
  `Rating` int(11) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `CreditCardNumber` char(16) DEFAULT NULL,
  `BrokerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `BrokerId` (`BrokerId`),
  CONSTRAINT `client_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `person` (`SSN`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `client_ibfk_2` FOREIGN KEY (`BrokerId`) REFERENCES `employee` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('s@s.c',1,111111111,'1111111111111111',3),('vicdu@cs.sunysb.edu',1,222222222,'5678123456781234',3);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `clientinfo`
--

DROP TABLE IF EXISTS `clientinfo`;
/*!50001 DROP VIEW IF EXISTS `clientinfo`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `clientinfo` AS SELECT 
 1 AS `ClientId`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Address`,
 1 AS `ZipCode`,
 1 AS `Telephone`,
 1 AS `Email`,
 1 AS `Rating`,
 1 AS `CreditCardNumber`,
 1 AS `BrokerId`,
 1 AS `City`,
 1 AS `State`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `ID` int(11) NOT NULL,
  `SSN` int(11) DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `HourlyRate` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `SSN` (`SSN`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`SSN`) REFERENCES `person` (`SSN`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (2,789123456,'2006-02-02',50),(3,123456789,'2011-01-01',60);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `employeeinfo`
--

DROP TABLE IF EXISTS `employeeinfo`;
/*!50001 DROP VIEW IF EXISTS `employeeinfo`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `employeeinfo` AS SELECT 
 1 AS `SSN`,
 1 AS `lastName`,
 1 AS `firstName`,
 1 AS `address`,
 1 AS `zipCode`,
 1 AS `telephone`,
 1 AS `id`,
 1 AS `startDate`,
 1 AS `hourlyRate`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `fullorder`
--

DROP TABLE IF EXISTS `fullorder`;
/*!50001 DROP VIEW IF EXISTS `fullorder`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `fullorder` AS SELECT 
 1 AS `NumShares`,
 1 AS `PricePerShare`,
 1 AS `Id`,
 1 AS `DateTime`,
 1 AS `Percentage`,
 1 AS `PriceType`,
 1 AS `OrderType`,
 1 AS `TransactionId`,
 1 AS `Fee`,
 1 AS `FinalDateTime`,
 1 AS `FinalPricePerShare`,
 1 AS `AccountId`,
 1 AS `BrokerId`,
 1 AS `StockId`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `hasstock`
--

DROP TABLE IF EXISTS `hasstock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hasstock` (
  `AccountId` int(11) NOT NULL,
  `StockSymbol` char(5) NOT NULL,
  `NumShares` int(11) DEFAULT NULL,
  PRIMARY KEY (`AccountId`,`StockSymbol`),
  KEY `StockSymbol` (`StockSymbol`),
  CONSTRAINT `hasstock_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `account` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasstock_ibfk_2` FOREIGN KEY (`StockSymbol`) REFERENCES `stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasstock`
--

LOCK TABLES `hasstock` WRITE;
/*!40000 ALTER TABLE `hasstock` DISABLE KEYS */;
INSERT INTO `hasstock` VALUES (1,'GM',75),(1,'IBM',20);
/*!40000 ALTER TABLE `hasstock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `StockSymbol` char(5) NOT NULL,
  `DateTime` datetime NOT NULL,
  `PricePerShare` float DEFAULT NULL,
  PRIMARY KEY (`StockSymbol`,`DateTime`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`StockSymbol`) REFERENCES `stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('F','2016-07-06 18:40:08',9),('GM','2016-07-06 18:37:53',25.25),('IBM','2016-07-06 18:39:37',91.41);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `ZipCode` int(11) NOT NULL,
  `City` char(30) NOT NULL,
  `State` char(20) NOT NULL,
  PRIMARY KEY (`ZipCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Test','Alabama'),(11111,'test','Alabama'),(11563,'Lyn','Alabama'),(11790,'Stony Brook','New York'),(11794,'Stony Brook','NY'),(11970,'Test','New York'),(93536,'Los Angeles','CA');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `NumShares` int(10) unsigned DEFAULT NULL,
  `PricePerShare` float DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `DateTime` datetime DEFAULT NULL,
  `percentage` double(2,0) DEFAULT NULL,
  `PriceType` char(20) DEFAULT NULL,
  `OrderType` char(5) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (75,NULL,1,'2016-07-06 20:02:12',NULL,'Market','Buy'),(20,NULL,2,'2016-07-06 20:02:26',NULL,'Market','Buy');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER BeginTrailingHistory
AFTER INSERT ON Orders FOR EACH ROW
BEGIN
	IF(NEW.PriceType = 'Trailing Stop') THEN
		INSERT INTO trailhistory(OrderId, DateTime, PricePerShare)
        VALUES(NEW.Id, NEW.DateTime, NEW.PricePerShare);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `SSN` int(11) NOT NULL,
  `LastName` char(20) NOT NULL,
  `FirstName` char(20) NOT NULL,
  `Address` char(20) DEFAULT NULL,
  `ZipCode` int(11) DEFAULT NULL,
  `telephone` char(13) DEFAULT NULL,
  PRIMARY KEY (`SSN`),
  KEY `ZipCode` (`ZipCode`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`ZipCode`) REFERENCES `location` (`ZipCode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (111111111,'s','s','111 s',11790,'1111111111'),(123456789,'Smith','David','123 College Road',11790,'516-215-2345'),(222222222,'Du','Victor','456 Fortune Road',11790,'516-632-4360'),(789123456,'Warren','David','456 Sunken Street',11794,'631-632-9987');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER Add_Location_Row
Before insert  on person for each row
begin
if(not exists(select L.* from Location L where L.zipcode = new.zipcode)) then

     insert into location(zipcode, city, state)
     values(new.zipcode, null, null);
     end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `soldstocks`
--

DROP TABLE IF EXISTS `soldstocks`;
/*!50001 DROP VIEW IF EXISTS `soldstocks`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `soldstocks` AS SELECT 
 1 AS `AccountId`,
 1 AS `StockSymbol`,
 1 AS `NumShares`,
 1 AS `PricePerShare`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `StockSymbol` char(20) NOT NULL,
  `CompanyName` char(20) NOT NULL,
  `Type` char(20) NOT NULL,
  `PricePerShare` float DEFAULT NULL,
  `NumShares` int(11) DEFAULT NULL,
  PRIMARY KEY (`StockSymbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES ('F','Ford','Automotive',9,750),('GM','General Motors','Automotive',25.25,25),('IBM','IBM','Computer',91.41,480);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER NewStockHistory
AFTER INSERT ON Stock FOR EACH ROW
BEGIN
	INSERT INTO History(StockSymbol, DateTime, PricePerShare)
		VALUES(New.StockSymbol, NOW(), New.PricePerShare);
	
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER UpdateSpecialStopOrders
BEFORE UPDATE ON Stock FOR EACH ROW

IF NEW.PricePerShare < OLD.PricePerShare THEN
BEGIN

	DECLARE done INT DEFAULT FALSE; #used to end the loop used later
	DECLARE rightNow DATETIME;	#the current dateTime, used in case a stop order must be executed
	DECLARE pT CHAR(20); #price type of the selected order
	DECLARE oId, tId INTEGER; # order and transaction Ids of the selected order
	DECLARE HiddenStopPrice FLOAT; #Hidden Stop price to be compared against
	DECLARE TrailStopPrice FLOAT;	#Trailing stop price to compare against
	DECLARE per DOUBLE;				#percentage used for calculations
	DECLARE NumberOfShares INT;		# The NumShares of the selected order, used to calculate a transaction's fee
	DECLARE OrderCursor CURSOR FOR (
		SELECT 	O.Id, Trns.Id, O.PricePerShare, O.Percentage, O.PriceType, O.NumShares
		FROM 	Orders O, Trade Trd, Transaction Trns
		WHERE 	Trd.StockId = NEW.StockSymbol
		AND		Trd.OrderId = O.Id
		AND		Trd.TransactionId = Trns.Id
		AND		(O.PriceType = 'Hidden Stop' OR O.PriceType = 'Trailing Stop')
		AND		Trns.DateTime Is NULL);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN OrderCursor;
	read_loop:LOOP	
	
		FETCH OrderCursor INTO oId, tId, HiddenStopPrice, per, pT, NumberOfShares;
		#if there are no more rows in our query, end the loop
		IF DONE THEN
			LEAVE read_loop;
		END IF;
		SET rightNow=NOW();
		#if the order is a hidden stop...
		IF pT='Hidden Stop' THEN
			#...and the stock price drops below the stop price, ...
			IF NEW.PricePerShare< HiddenStopPrice THEN
				#... then execute the order, 
				# setting the execution time to now and the final price per share to the stop price
				UPDATE Transaction T 
				SET T.DateTime=rightNow, T.PricePerShare=HiddenStopPrice, T.Fee=(NumberOfShares * HiddenStopPrice * 0.05) 
				WHERE T.Id = tId;
            
				#also, add the stocks back into the market
				UPDATE Stock S
				SET S.NumShares=(S.NumShares + NumberOfShares)
				WHERE S.StockSymbol = NEW.StockSymbol;
            
			END IF;
		END IF;
    
		#If the order is a trailing stop
		IF pT='Trailing Stop' THEN
			#first get the  current trailing stop price
			SET TrailStopPrice = 
				(SELECT PricePerShare
				FROM TrailHistory
				WHERE OrderId = oId
				ORDER BY DateTime DESC
				LIMIT 1);
        
			#If the new Stock price is less than the current Trailing stop price...
			IF StockPrice < TrailStopPrice THEN
				#Insert a new row into TrailHistory
				INSERT INTO TrailHistory (OrderId, DateTime, PricePerShare)
				VALUES(oId, rightNow, TrailStopPrice);
				#Update the transaction with the final price and time
				UPDATE Transaction T 
				SET T.DateTime=rightNow, T.PricePerShare = TrailStopPrice, T.Fee = (NumberOfShares * TrailStopPrice * 0.05) 
				WHERE T.Id = tId;
				#And add the shares back into the market
				UPDATE Stock S
				SET S.NumShares = (S.NumShares + NumberOfShares)
				WHERE S.StockSymbol = NEW.StockSymbol;
            
			END IF;
		END IF;

	END LOOP;    
	CLOSE OrderCursor;
	END;
END IF */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER UpdateTrailingHistory
AFTER UPDATE ON STOCK FOR EACH ROW
IF NEW.PricePerShare > OLD.PricePerShare THEN
	BEGIN
	
		DECLARE rightNow DATETIME; 		# Current DateTime
		DECLARE done INT DEFAULT FALSE;
		DECLARE pT CHAR(20);			# Price Type
		DECLARE oId, tId INTEGER;		# Order Id and Transaction Id
		DECLARE pps FLOAT;				# Price Per Share
		DECLARE stopPercent DOUBLE;		# Stop Percentage
		DECLARE currentStopPrice FLOAT;	# Current Stop Price
		DECLARE OrderCursor CURSOR FOR	# List of Trailing Stop Orders
			(SELECT 	O.Id, Trns.Id, O.PricePerShare, O.Percentage
			FROM 		Orders O, Trade Trd, Transaction Trns
			WHERE 		Trd.StockId = NEW.StockSymbol
			AND			O.Id = Trd.OrderId
			AND			Trd.TransactionId = Trns.Id
			AND			O.PriceType = 'Trailing Stop'
			AND			Trns.DateTime IS NULL);
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

		OPEN OrderCursor;
		read_loop:LOOP	
			FETCH OrderCursor INTO oId, tId, pps, stopPercent;
			SET rightNow=NOW();
			IF done THEN
				LEAVE read_loop;
			END IF;
    
			SET currentStopPrice = (
				SELECT T.PricePerShare
				FROM TrailHistory T
				WHERE T.orderId=oId
				ORDER BY T.DateTime DESC
				LIMIT 1);
			IF NEW.PricePerShare > (currentStopPrice + (currentStopPrice * (stopPercent/100)) ) THEN
				INSERT INTO TrailHistory (OrderId, DateTime, PricePerShare)
				VALUES(oId, rightNow, (NEW.PricePerShare * (stopPercent/100) ) );
			END IF;
		END LOOP;    
		CLOSE OrderCursor;
	
	END;
END IF */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER UpdateStockHistory
AFTER UPDATE ON Stock FOR EACH ROW
BEGIN
	IF (Old.PricePerShare<>New.PricePerShare OR Old.StockSymbol<>New.StockSymbol) THEN

		INSERT INTO History(StockSymbol, DateTime, PricePerShare)
		VALUES(New.StockSymbol, NOW(), New.PricePerShare);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade` (
  `AccountId` int(11) NOT NULL,
  `BrokerId` int(11) NOT NULL,
  `TransactionId` int(11) NOT NULL,
  `OrderId` int(11) NOT NULL,
  `StockId` char(20) NOT NULL,
  PRIMARY KEY (`AccountId`,`BrokerId`,`TransactionId`,`OrderId`,`StockId`),
  KEY `BrokerId` (`BrokerId`),
  KEY `TransactionId` (`TransactionId`),
  KEY `OrderId` (`OrderId`),
  KEY `StockId` (`StockId`),
  CONSTRAINT `trade_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `account` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `trade_ibfk_2` FOREIGN KEY (`BrokerId`) REFERENCES `employee` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `trade_ibfk_3` FOREIGN KEY (`TransactionId`) REFERENCES `transaction` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `trade_ibfk_4` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `trade_ibfk_5` FOREIGN KEY (`StockId`) REFERENCES `stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (1,3,1,1,'GM'),(1,3,2,2,'IBM');
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER UpdateSharesAvailable
AFTER INSERT ON Trade FOR EACH ROW
BEGIN 
	DECLARE StockNumShares, OrderNumShares INTEGER;
    DECLARE OrderType CHAR(5);	#Order Type for this trade
    DECLARE PriceType CHAR(20);	#Price Type for this trade
    DECLARE UserShares INTEGER;
    #Cursor for the order table this Trade relation refers to.
    DECLARE OrderCursor CURSOR FOR
		(SELECT O.NumShares, O.OrderType, O.PriceType 
		FROM Orders O
		WHERE O.Id = NEW.OrderId);
    
    #Set OrderNumShares, OrderType, and PriceType
    OPEN OrderCursor;
	FETCH OrderCursor INTO OrderNumShares, OrderType, PriceType;
    
    
    
    SET StockNumShares= 
		(SELECT S.NumShares
        FROM Stock S
        WHERE S.StockSymbol = NEW.StockId);
    SET UserShares=
    (SELECT H.NumShares
    FROM HasStock H 
    WHERE H.AccountId = NEW.AccountId AND H.StockSymbol = NEW.StockId);
    
    #If you bought stocks, move stocks from the market to your holdings.
    IF OrderType='Buy' THEN
		#'Move' stocks out of the market
		UPDATE Stock S
        SET S.NumShares=(StockNumShares-OrderNumShares) 
        WHERE S.StockSymbol = NEW.StockId;
        
        IF UserShares IS NULL THEN
        
			#Insert a new row into HasStock if it does not already exist
			INSERT HasStock(AccountId, StockSymbol, NumShares)
			VALUES(NEW.AccountId, NEW.StockId, OrderNumShares);
        
			#Update the user's stock holdings
		ELSE
			UPDATE HasStock SET NumShares = NumShares + OrderNumShares
			WHERE AccountId = NEW.AccountId AND StockSymbol = NEW.StockId;
        END IF;
    ELSEIF OrderType='Sell' THEN
		IF PriceType='Market' OR PriceType='Market On Close' THEN
			UPDATE Stock S
            SET S.NumShares=(StockNumShares+OrderNumShares) 
			WHERE S.StockSymbol = NEW.StockId;
        END IF;
        # Update the user's stock holdings
        UPDATE HasStock SET NumShares = (NumShares-OrderNumShares)
        WHERE AccountId = New.AccountId AND Stocksymbol = NEW.StockId;
        
        # Delete the row from HasStock 
        # if the user does not have any more of that type of share
        DELETE FROM HasStock
        WHERE AccountId = New.AccountId AND StockSymbol = NEW.StockId AND NumShares = 0;
        
        
    END IF;
    CLOSE OrderCursor;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `trailhistory`
--

DROP TABLE IF EXISTS `trailhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trailhistory` (
  `OrderId` int(11) NOT NULL,
  `DateTime` datetime NOT NULL,
  `PricePerShare` float DEFAULT NULL,
  PRIMARY KEY (`OrderId`,`DateTime`),
  CONSTRAINT `trailhistory_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trailhistory`
--

LOCK TABLES `trailhistory` WRITE;
/*!40000 ALTER TABLE `trailhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `trailhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `Id` int(11) NOT NULL,
  `Fee` float DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `PricePerShare` float DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,94.6875,'2016-07-06 20:02:12',25.25),(2,91.41,'2016-07-06 20:02:26',91.41);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `UserName` char(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Password` char(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `UserType` char(20) DEFAULT NULL,
  `Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES ('DSmith','password','Representative',3),('VDu','password','Customer',222222222),('WSmith','password','Manager',2),('s','s','Customer',111111111);
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `project3`
--

USE `project3`;

--
-- Final view structure for view `accountdetails`
--

/*!50001 DROP VIEW IF EXISTS `accountdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `accountdetails` AS (select `account`.`Id` AS `Id`,`account`.`DateOpened` AS `DateOpened`,`account`.`Client` AS `Client`,`person`.`LastName` AS `LastName`,`person`.`FirstName` AS `FirstName`,`person`.`Address` AS `Address`,`person`.`ZipCode` AS `ZipCode`,`person`.`telephone` AS `Telephone` from (`account` join `person` on((`account`.`Client` = `person`.`SSN`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `boughtstocks`
--

/*!50001 DROP VIEW IF EXISTS `boughtstocks`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `boughtstocks` AS (select `a`.`Id` AS `AccountId`,`s`.`StockSymbol` AS `StockSymbol`,sum(`o1`.`NumShares`) AS `NumShares`,`s`.`PricePerShare` AS `PricePerShare` from (((`account` `a` join `orders` `o1`) join `trade` `trd`) join `stock` `s`) where ((`trd`.`AccountId` = `a`.`Id`) and (`trd`.`StockId` = `s`.`StockSymbol`) and (`o1`.`Id` = `trd`.`OrderId`) and (`o1`.`OrderType` = 'Buy')) group by `a`.`Id`,`s`.`StockSymbol`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `clientinfo`
--

/*!50001 DROP VIEW IF EXISTS `clientinfo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `clientinfo` AS select `p`.`SSN` AS `ClientId`,`p`.`LastName` AS `LastName`,`p`.`FirstName` AS `FirstName`,`p`.`Address` AS `Address`,`p`.`ZipCode` AS `ZipCode`,`p`.`telephone` AS `Telephone`,`c`.`Email` AS `Email`,`c`.`Rating` AS `Rating`,`c`.`CreditCardNumber` AS `CreditCardNumber`,`c`.`BrokerId` AS `BrokerId`,`l`.`City` AS `City`,`l`.`State` AS `State` from ((`person` `p` join `client` `c` on((`p`.`SSN` = `c`.`Id`))) join `location` `l`) where (`l`.`ZipCode` = `p`.`ZipCode`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employeeinfo`
--

/*!50001 DROP VIEW IF EXISTS `employeeinfo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employeeinfo` AS select `p`.`SSN` AS `SSN`,`p`.`LastName` AS `lastName`,`p`.`FirstName` AS `firstName`,`p`.`Address` AS `address`,`p`.`ZipCode` AS `zipCode`,`p`.`telephone` AS `telephone`,`e`.`ID` AS `id`,`e`.`StartDate` AS `startDate`,`e`.`HourlyRate` AS `hourlyRate` from (`person` `p` join `employee` `e`) where (`p`.`SSN` = `e`.`SSN`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `fullorder`
--

/*!50001 DROP VIEW IF EXISTS `fullorder`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fullorder` AS (select `o`.`NumShares` AS `NumShares`,`o`.`PricePerShare` AS `PricePerShare`,`o`.`Id` AS `Id`,`o`.`DateTime` AS `DateTime`,`o`.`percentage` AS `Percentage`,`o`.`PriceType` AS `PriceType`,`o`.`OrderType` AS `OrderType`,`trns`.`Id` AS `TransactionId`,`trns`.`Fee` AS `Fee`,`trns`.`DateTime` AS `FinalDateTime`,`trns`.`PricePerShare` AS `FinalPricePerShare`,`trd`.`AccountId` AS `AccountId`,`trd`.`BrokerId` AS `BrokerId`,`trd`.`StockId` AS `StockId` from ((`orders` `o` join `trade` `trd`) join `transaction` `trns`) where ((`o`.`Id` = `trd`.`OrderId`) and (`trd`.`TransactionId` = `trns`.`Id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `soldstocks`
--

/*!50001 DROP VIEW IF EXISTS `soldstocks`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `soldstocks` AS (select `a`.`Id` AS `AccountId`,`s`.`StockSymbol` AS `StockSymbol`,sum(`o2`.`NumShares`) AS `NumShares`,`s`.`PricePerShare` AS `PricePerShare` from (((`account` `a` join `orders` `o2`) join `trade` `trd`) join `stock` `s`) where ((`trd`.`AccountId` = `a`.`Id`) and (`trd`.`StockId` = `s`.`StockSymbol`) and (`o2`.`Id` = `trd`.`OrderId`) and (`o2`.`OrderType` = 'Sold')) group by `a`.`Id`,`s`.`StockSymbol`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-06 21:47:07
