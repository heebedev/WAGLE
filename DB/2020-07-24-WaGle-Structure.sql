-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: 192.168.0.82    Database: WaGle
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
-- Table structure for table `Board`
--

DROP TABLE IF EXISTS `Board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Board` (
  `bSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `bName` varchar(45) DEFAULT NULL,
  `Moim_mSeqno` int(11) DEFAULT NULL,
  `bDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `bOrder` int(11) DEFAULT NULL,
  PRIMARY KEY (`bSeqno`),
  KEY `fk_Board_Moim_idx` (`Moim_mSeqno`),
  CONSTRAINT `fk_Board_Moim` FOREIGN KEY (`Moim_mSeqno`) REFERENCES `moim` (`mSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BookReport`
--

DROP TABLE IF EXISTS `BookReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BookReport` (
  `brSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `Suggestion_sSeqno` int(11) NOT NULL,
  `User_uSeqno` int(11) NOT NULL,
  `brContent` varchar(45) DEFAULT NULL,
  `brValidation` varchar(45) NOT NULL DEFAULT '1',
  `brDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`brSeqno`),
  KEY `fk_BookReport_Suggestion_idx` (`Suggestion_sSeqno`),
  KEY `fk_BookReport_User_idx` (`User_uSeqno`),
  CONSTRAINT `fk_BookReport_Suggestion` FOREIGN KEY (`Suggestion_sSeqno`) REFERENCES `suggestion` (`sSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BookReport_User` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Choice`
--

DROP TABLE IF EXISTS `Choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Choice` (
  `cSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `cAnswer` int(11) DEFAULT NULL,
  `Vote_vSeqno` int(11) NOT NULL,
  `MoimUser_muSeqno` int(11) NOT NULL,
  PRIMARY KEY (`cSeqno`),
  KEY `fk_Choice_VoteSeqno_idx` (`Vote_vSeqno`),
  KEY `fk_Choice_MoimUserSeqno_idx` (`MoimUser_muSeqno`),
  CONSTRAINT `fk_Choice_MoimUserSeqno` FOREIGN KEY (`MoimUser_muSeqno`) REFERENCES `moimuser` (`muSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Choice_VoteSeqno` FOREIGN KEY (`Vote_vSeqno`) REFERENCES `vote` (`vSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Moim`
--

DROP TABLE IF EXISTS `Moim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Moim` (
  `mSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `mName` varchar(45) DEFAULT NULL,
  `mSubject` varchar(45) DEFAULT NULL,
  `mImage` varchar(45) DEFAULT NULL,
  `mIntro` varchar(45) DEFAULT NULL,
  `mValidation` varchar(45) DEFAULT NULL,
  `User_uSeqno` int(11) NOT NULL,
  PRIMARY KEY (`mSeqno`),
  KEY `fk_Moim_mUserSeq` (`User_uSeqno`),
  CONSTRAINT `fk_Moim_mUserSeq` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MoimAdminister`
--

DROP TABLE IF EXISTS `MoimAdminister`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MoimAdminister` (
  `maSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `maGrade` varchar(45) DEFAULT NULL,
  `maDate` datetime DEFAULT NULL,
  `maValidation` varchar(45) DEFAULT NULL,
  `Moim_mSeqno` int(11) DEFAULT NULL,
  `MoimUser_muSeqno` int(11) DEFAULT NULL,
  PRIMARY KEY (`maSeqno`),
  KEY `fk_MoimAdminister_MoimSeqno_idx` (`Moim_mSeqno`),
  KEY `fk_MoimAdminister_MoimUser_idx` (`MoimUser_muSeqno`),
  CONSTRAINT `fk_MoimAdminister_MoimSeqno` FOREIGN KEY (`Moim_mSeqno`) REFERENCES `moim` (`mSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_MoimAdminister_MoimUser` FOREIGN KEY (`MoimUser_muSeqno`) REFERENCES `moimuser` (`muSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MoimUser`
--

DROP TABLE IF EXISTS `MoimUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MoimUser` (
  `muSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `Moim_mSeqno` int(11) DEFAULT NULL,
  `User_uSeqno` int(11) DEFAULT NULL,
  `muWagleNum` int(11) DEFAULT '0',
  `muWagleReport` int(11) DEFAULT '0',
  `muWagleSuggestion` int(11) DEFAULT '0',
  `muWagleScore` int(11) DEFAULT '0',
  `date` datetime NOT NULL,
  `muValidation` varchar(45) NOT NULL DEFAULT '1',
  PRIMARY KEY (`muSeqno`),
  UNIQUE KEY `muSeqno_UNIQUE` (`muSeqno`),
  KEY `fk_MoimUser_UserSeqno_idx` (`User_uSeqno`),
  KEY `fk_MoimUser_mSeqno_idx` (`Moim_mSeqno`),
  CONSTRAINT `fk_MoimUser_UserSeqno` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_MoimUser_mSeqno` FOREIGN KEY (`Moim_mSeqno`) REFERENCES `moim` (`mSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Post`
--

DROP TABLE IF EXISTS `Post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Post` (
  `pSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `User_uSeqno` int(11) NOT NULL,
  `Moim_mSeqno` int(11) NOT NULL,
  `pType` varchar(45) DEFAULT NULL,
  `pValidation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pSeqno`),
  KEY `fk_Post_UseruSeqno_idx` (`User_uSeqno`),
  KEY `fk_Post_MoimmSeqno_idx` (`Moim_mSeqno`),
  CONSTRAINT `fk_Post_UseruSeqno` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PostContent`
--

DROP TABLE IF EXISTS `PostContent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PostContent` (
  `pcSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `Post_pSeqno` int(11) NOT NULL,
  `pcTitle` varchar(45) DEFAULT NULL,
  `pcContent` varchar(45) DEFAULT NULL,
  `pcDate` datetime DEFAULT NULL,
  PRIMARY KEY (`pcSeqno`),
  KEY `fk_PostContent_PostSeqno_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_PostContent_PostSeqno` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PostFile`
--

DROP TABLE IF EXISTS `PostFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PostFile` (
  `pfSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `Post_pSeqno` int(11) NOT NULL,
  `pfImageName` varchar(45) DEFAULT NULL,
  `pfDate` datetime DEFAULT NULL,
  PRIMARY KEY (`pfSeqno`),
  KEY `fk_PostFile_PostSeqno_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_PostFile_PostSeqno` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Suggestion`
--

DROP TABLE IF EXISTS `Suggestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Suggestion` (
  `sSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `WagleCreate_wcSeqno` int(11) NOT NULL,
  `sType` varchar(45) NOT NULL DEFAULT 'Q',
  `sContent` text NOT NULL,
  `sValidation` varchar(45) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sSeqno`),
  KEY `fk_Suggestion_WagleCreate_idx` (`WagleCreate_wcSeqno`),
  CONSTRAINT `fk_Suggestion_WagleCreate` FOREIGN KEY (`WagleCreate_wcSeqno`) REFERENCES `waglecreate` (`wcSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `uSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `uId` varchar(45) NOT NULL,
  `uPassword` varchar(45) DEFAULT NULL,
  `uEmail` varchar(45) DEFAULT NULL,
  `uLoginType` varchar(45) DEFAULT NULL,
  `uName` varchar(45) DEFAULT NULL,
  `uImageName` varchar(150) DEFAULT NULL,
  `uBirthDate` varchar(45) DEFAULT NULL,
  `uDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserBackup`
--

DROP TABLE IF EXISTS `UserBackup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserBackup` (
  `ubSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `uSeqno` int(11) DEFAULT NULL,
  `uId` varchar(45) DEFAULT NULL,
  `uPassword` varchar(45) DEFAULT NULL,
  `uEmail` varchar(45) DEFAULT NULL,
  `uLoginType` varchar(45) DEFAULT NULL,
  `uName` varchar(45) DEFAULT NULL,
  `uImageName` varchar(45) DEFAULT NULL,
  `uBirthDate` varchar(45) DEFAULT NULL,
  `uDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ubSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Vote`
--

DROP TABLE IF EXISTS `Vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vote` (
  `vSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `vNumber` int(11) DEFAULT NULL,
  `vName` varchar(45) DEFAULT NULL,
  `vVotes` int(11) DEFAULT '0',
  `vValidation` int(11) DEFAULT '1',
  `Post_pSeqno` int(11) NOT NULL,
  PRIMARY KEY (`vSeqno`),
  KEY `fk_Vote_PostSeqno_idx` (`Post_pSeqno`),
  CONSTRAINT `fk_Vote_PostSeqno` FOREIGN KEY (`Post_pSeqno`) REFERENCES `post` (`pSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WagleBook`
--

DROP TABLE IF EXISTS `WagleBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleBook` (
  `wbSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `wbTitle` varchar(45) DEFAULT NULL,
  `wbWriter` varchar(45) DEFAULT NULL,
  `wbMaxPage` int(11) DEFAULT NULL,
  `wbIntro` varchar(45) DEFAULT NULL,
  `wbData` varchar(45) DEFAULT NULL,
  `wbImage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wbSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WagleCreate`
--

DROP TABLE IF EXISTS `WagleCreate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleCreate` (
  `wcSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `Moim_mSeqno` int(11) NOT NULL,
  `User_uSeqno` int(11) NOT NULL,
  `WagleBook_wbSeqno` int(11) NOT NULL,
  `wcName` varchar(45) DEFAULT NULL,
  `wcType` varchar(45) DEFAULT NULL,
  `wcStartDate` varchar(45) DEFAULT NULL,
  `wcEndDate` varchar(45) DEFAULT NULL,
  `wcDueDate` varchar(45) DEFAULT NULL,
  `wcLocate` varchar(45) DEFAULT NULL,
  `wcEntryFee` varchar(45) DEFAULT NULL,
  `wcWagleDetail` text,
  `wcWagleAgreeRefund` text,
  `wcValidation` varchar(45) DEFAULT '1',
  PRIMARY KEY (`wcSeqno`),
  KEY `fk_WagleCreate_WagleMoim1_idx` (`Moim_mSeqno`),
  KEY `fk_WagleCreate_User_idx` (`User_uSeqno`),
  KEY `fk_WagleCreate_WagleBook1_idx` (`WagleBook_wbSeqno`),
  CONSTRAINT `fk_WagleCreate_User` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_WagleCreate_WagleBook1` FOREIGN KEY (`WagleBook_wbSeqno`) REFERENCES `waglebook` (`wbSeqno`),
  CONSTRAINT `fk_WagleCreate_WagleMoim1` FOREIGN KEY (`Moim_mSeqno`) REFERENCES `moim` (`mSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WaglePayment`
--

DROP TABLE IF EXISTS `WaglePayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WaglePayment` (
  `wpSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `wcSeqno` int(11) NOT NULL,
  `wpItem` varchar(45) DEFAULT NULL,
  `wpPrice` int(11) DEFAULT NULL,
  `wpDate` datetime DEFAULT NULL,
  `wpValidation` int(11) DEFAULT '1',
  PRIMARY KEY (`wpSeqno`),
  KEY `fk_WaglePayment_wcSeqno_idx` (`wcSeqno`),
  CONSTRAINT `fk_WaglePayment_wcSeqno` FOREIGN KEY (`wcSeqno`) REFERENCES `waglecreate` (`wcSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WagleProgress`
--

DROP TABLE IF EXISTS `WagleProgress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleProgress` (
  `wpSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `WagleUser_wuSeqno` int(11) NOT NULL,
  `WagleCreate_wcSeqno` int(11) NOT NULL,
  `wpReadPage` int(11) DEFAULT '0',
  PRIMARY KEY (`wpSeqno`),
  KEY `fk_WagleProgress_WagleUser1_idx` (`WagleUser_wuSeqno`),
  KEY `fk_WagleProgress_WagleCreate_idx` (`WagleCreate_wcSeqno`),
  CONSTRAINT `fk_WagleProgress_WagleCreate` FOREIGN KEY (`WagleCreate_wcSeqno`) REFERENCES `waglecreate` (`wcSeqno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_WagleProgress_WagleUser1` FOREIGN KEY (`WagleUser_wuSeqno`) REFERENCES `wagleuser` (`wuSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WagleUser`
--

DROP TABLE IF EXISTS `WagleUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WagleUser` (
  `wuSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `User_uSeqno` int(11) NOT NULL,
  `wcSeqno` int(11) NOT NULL,
  `wuDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `wuValidation` varchar(45) DEFAULT '1',
  PRIMARY KEY (`wuSeqno`),
  KEY `fk_WagleUser_User1_idx` (`User_uSeqno`),
  KEY `fk_WagleUser_wcSeqno_idx` (`wcSeqno`),
  CONSTRAINT `fk_WagleUser_User1` FOREIGN KEY (`User_uSeqno`) REFERENCES `user` (`uSeqno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-24 10:39:17
