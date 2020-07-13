-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: WaGle
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BookReport`
--

DROP TABLE IF EXISTS `BookReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BookReport` (
  `brSeqno` int(11) NOT NULL,
  `Post_pSeqno` int(11) NOT NULL,
  `Suggestion_sSeqno` int(11) NOT NULL,
  `SuggestionQuestion_sqSeqno` int(11) NOT NULL,
  `brTitle` varchar(45) DEFAULT NULL,
  `brContent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`brSeqno`),
  KEY `fk_Suggestion_Post1_idx` (`Post_pSeqno`),
  KEY `fk_BookReport_Suggestion1_idx` (`Suggestion_sSeqno`),
  KEY `fk_BookReport_SuggestionQuestion1_idx` (`SuggestionQuestion_sqSeqno`),
  CONSTRAINT `fk_BookReport_Suggestion1` FOREIGN KEY (`Suggestion_sSeqno`) REFERENCES `suggestion` (`sSeqno`),
  CONSTRAINT `fk_BookReport_SuggestionQuestion1` FOREIGN KEY (`SuggestionQuestion_sqSeqno`) REFERENCES `suggestionquestion` (`sqSeqno`),
  CONSTRAINT `fk_Suggestion_Post1` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BookReport`
--

LOCK TABLES `BookReport` WRITE;
/*!40000 ALTER TABLE `BookReport` DISABLE KEYS */;
/*!40000 ALTER TABLE `BookReport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Post`
--

DROP TABLE IF EXISTS `Post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Post` (
  `pSeqno` int(11) NOT NULL,
  `WagleUser_wuSeqno` int(11) NOT NULL,
  `pType` varchar(45) DEFAULT NULL,
  `pValidation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pSeqno`),
  KEY `fk_Post_WagleUser1_idx` (`WagleUser_wuSeqno`),
  CONSTRAINT `fk_Post_WagleUser1` FOREIGN KEY (`WagleUser_wuSeqno`) REFERENCES `wagleuser` (`wuSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Post`
--

LOCK TABLES `Post` WRITE;
/*!40000 ALTER TABLE `Post` DISABLE KEYS */;
/*!40000 ALTER TABLE `Post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PostContent`
--

DROP TABLE IF EXISTS `PostContent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PostContent` (
  `pcSeqno` int(11) NOT NULL,
  `Post_pSeqno` int(11) NOT NULL,
  `pcTitle` varchar(45) DEFAULT NULL,
  `pcContent` varchar(45) DEFAULT NULL,
  `pcDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pcSeqno`),
  KEY `fk_PostContent_Post1_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_PostContent_Post1` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostContent`
--

LOCK TABLES `PostContent` WRITE;
/*!40000 ALTER TABLE `PostContent` DISABLE KEYS */;
/*!40000 ALTER TABLE `PostContent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PostFile`
--

DROP TABLE IF EXISTS `PostFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PostFile` (
  `pfSeqno` int(11) NOT NULL,
  `Post_pSeqno` int(11) NOT NULL,
  `pfImageName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pfSeqno`),
  KEY `fk_PostFile_Post1_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_PostFile_Post1` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostFile`
--

LOCK TABLES `PostFile` WRITE;
/*!40000 ALTER TABLE `PostFile` DISABLE KEYS */;
/*!40000 ALTER TABLE `PostFile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Suggestion`
--

DROP TABLE IF EXISTS `Suggestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Suggestion` (
  `sSeqno` int(11) NOT NULL,
  `Post_pSeqno` int(11) NOT NULL,
  `sIntroduction` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sSeqno`),
  KEY `fk_Suggestion_Post2_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_Suggestion_Post2` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Suggestion`
--

LOCK TABLES `Suggestion` WRITE;
/*!40000 ALTER TABLE `Suggestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Suggestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SuggestionQuestion`
--

DROP TABLE IF EXISTS `SuggestionQuestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SuggestionQuestion` (
  `sqSeqno` int(11) NOT NULL,
  `Suggestion_sSeqno` int(11) NOT NULL,
  `sqQuestion` varchar(45) DEFAULT NULL,
  `sqQuestionContent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sqSeqno`),
  KEY `fk_SuggestionQuestion_Suggestion1_idx` (`Suggestion_sSeqno`),
  CONSTRAINT `fk_SuggestionQuestion_Suggestion1` FOREIGN KEY (`Suggestion_sSeqno`) REFERENCES `suggestion` (`sSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SuggestionQuestion`
--

LOCK TABLES `SuggestionQuestion` WRITE;
/*!40000 ALTER TABLE `SuggestionQuestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `SuggestionQuestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `uSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `uEmail` varchar(45) DEFAULT NULL,
  `uPassword` varchar(45) DEFAULT NULL,
  `uName` varchar(45) DEFAULT NULL,
  `uImageName` varchar(45) DEFAULT NULL,
  `uBirthDat` varchar(45) DEFAULT NULL,
  `uDate` varchar(45) DEFAULT NULL,
  `uValidation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleAdminister`
--

DROP TABLE IF EXISTS `WagleAdminister`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleAdminister` (
  `waSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `waGrade` varchar(45) DEFAULT NULL,
  `waDate` varchar(45) DEFAULT NULL,
  `waValidation` varchar(45) DEFAULT NULL,
  `WagleUser_wuSeqno` int(11) NOT NULL,
  PRIMARY KEY (`waSeqno`),
  KEY `fk_WagleAdminister_WagleUser_idx` (`WagleUser_wuSeqno`),
  CONSTRAINT `fk_WagleAdminister_WagleUser` FOREIGN KEY (`WagleUser_wuSeqno`) REFERENCES `wagleuser` (`wuSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleAdminister`
--

LOCK TABLES `WagleAdminister` WRITE;
/*!40000 ALTER TABLE `WagleAdminister` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleAdminister` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleBook`
--

DROP TABLE IF EXISTS `WagleBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleBook` (
  `wbSeqno` int(11) NOT NULL,
  `wbTitle` varchar(45) DEFAULT NULL,
  `wbWriter` varchar(45) DEFAULT NULL,
  `wbMaxPage` varchar(45) DEFAULT NULL,
  `wbIntro` varchar(45) DEFAULT NULL,
  `wbData` varchar(45) DEFAULT NULL,
  `wbImage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wbSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleBook`
--

LOCK TABLES `WagleBook` WRITE;
/*!40000 ALTER TABLE `WagleBook` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleBook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleCreate`
--

DROP TABLE IF EXISTS `WagleCreate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleCreate` (
  `wcSeqno` int(11) NOT NULL,
  `WagleMoim_wmSeqno` int(11) NOT NULL,
  `WagleUser_wuSeqno` int(11) NOT NULL,
  `wcName` varchar(45) DEFAULT NULL,
  `wcType` varchar(45) DEFAULT NULL,
  `wcStartDate` varchar(45) DEFAULT NULL,
  `wcEndDate` varchar(45) DEFAULT NULL,
  `wcDueDate` varchar(45) DEFAULT NULL,
  `wcLocate` varchar(45) DEFAULT NULL,
  `wcEntryFee` varchar(45) DEFAULT NULL,
  `wcValidation` varchar(45) DEFAULT NULL,
  `WagleBook_wbSeqno` int(11) NOT NULL,
  PRIMARY KEY (`wcSeqno`),
  KEY `fk_WagleCreate_WagleMoim1_idx` (`WagleMoim_wmSeqno`),
  KEY `fk_WagleCreate_WagleUser1_idx` (`WagleUser_wuSeqno`),
  KEY `fk_WagleCreate_WagleBook1_idx` (`WagleBook_wbSeqno`),
  CONSTRAINT `fk_WagleCreate_WagleBook1` FOREIGN KEY (`WagleBook_wbSeqno`) REFERENCES `waglebook` (`wbSeqno`),
  CONSTRAINT `fk_WagleCreate_WagleMoim1` FOREIGN KEY (`WagleMoim_wmSeqno`) REFERENCES `waglemoim` (`wmSeqno`),
  CONSTRAINT `fk_WagleCreate_WagleUser1` FOREIGN KEY (`WagleUser_wuSeqno`) REFERENCES `wagleuser` (`wuSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleCreate`
--

LOCK TABLES `WagleCreate` WRITE;
/*!40000 ALTER TABLE `WagleCreate` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleCreate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleMoim`
--

DROP TABLE IF EXISTS `WagleMoim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleMoim` (
  `wmSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `wmName` varchar(45) DEFAULT NULL,
  `wmSubject` varchar(45) DEFAULT NULL,
  `wmImage` varchar(45) DEFAULT NULL,
  `wmIntro` varchar(45) DEFAULT NULL,
  `wmValidation` varchar(45) DEFAULT NULL,
  `WagleAdminister_waSeqno` int(11) NOT NULL,
  PRIMARY KEY (`wmSeqno`),
  KEY `fk_WagleMoim_WagleAdminister1_idx` (`WagleAdminister_waSeqno`),
  CONSTRAINT `fk_WagleMoim_WagleAdminister1` FOREIGN KEY (`WagleAdminister_waSeqno`) REFERENCES `wagleadminister` (`waSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleMoim`
--

LOCK TABLES `WagleMoim` WRITE;
/*!40000 ALTER TABLE `WagleMoim` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleMoim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleProgress`
--

DROP TABLE IF EXISTS `WagleProgress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleProgress` (
  `wpSeqno` int(11) NOT NULL,
  `WagleUser_wuSeqno` int(11) NOT NULL,
  `WagleCreate_wcSeqno` int(11) NOT NULL,
  `wpReadPage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wpSeqno`),
  KEY `fk_WagleProgress_WagleUser1_idx` (`WagleUser_wuSeqno`),
  KEY `fk_WagleProgress_WagleCreate1_idx` (`WagleCreate_wcSeqno`),
  CONSTRAINT `fk_WagleProgress_WagleCreate1` FOREIGN KEY (`WagleCreate_wcSeqno`) REFERENCES `waglecreate` (`wcSeqno`),
  CONSTRAINT `fk_WagleProgress_WagleUser1` FOREIGN KEY (`WagleUser_wuSeqno`) REFERENCES `wagleuser` (`wuSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleProgress`
--

LOCK TABLES `WagleProgress` WRITE;
/*!40000 ALTER TABLE `WagleProgress` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleProgress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WagleUser`
--

DROP TABLE IF EXISTS `WagleUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleUser` (
  `wuSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `User_uSeqno` int(11) NOT NULL,
  `WagleMoim_wmSeqno` int(11) NOT NULL,
  `wuWagleNum` varchar(45) DEFAULT NULL,
  `wuWagleBookReport` varchar(45) DEFAULT NULL,
  `wuWagleSuggestion` varchar(45) DEFAULT NULL,
  `wuWagleScore` varchar(45) DEFAULT NULL,
  `wuValidation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wuSeqno`),
  KEY `fk_WagleUser_User1_idx` (`User_uSeqno`),
  KEY `fk_WagleUser_WagleMoim1_idx` (`WagleMoim_wmSeqno`),
  CONSTRAINT `fk_WagleUser_User1` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`),
  CONSTRAINT `fk_WagleUser_WagleMoim1` FOREIGN KEY (`WagleMoim_wmSeqno`) REFERENCES `waglemoim` (`wmSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WagleUser`
--

LOCK TABLES `WagleUser` WRITE;
/*!40000 ALTER TABLE `WagleUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `WagleUser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-10 17:21:31
