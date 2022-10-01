-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: pollinatorconservation
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `clade`
--

DROP TABLE IF EXISTS `clade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clade` (
  `idClade` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`idClade`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clade`
--

LOCK TABLES `clade` WRITE;
/*!40000 ALTER TABLE `clade` DISABLE KEYS */;
INSERT INTO `clade` VALUES (1,'Amborellal'),(2,'Nymphaeal'),(3,'Austrobaileyal'),(4,'Chloranthal'),(5,'Magnólida'),(6,'Ceratophyllal'),(7,'Eudicotiledónea'),(8,'Monocotilodónea');
/*!40000 ALTER TABLE `clade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pollinator`
--

DROP TABLE IF EXISTS `pollinator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pollinator` (
  `scientificName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `genericName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text NOT NULL,
  `idFamily` int NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`scientificName`),
  KEY `FK_Pollinator_Family_idx` (`idFamily`),
  KEY `FK_Pollinator_User_idx` (`username`),
  CONSTRAINT `FK_Pollinator_Familiy` FOREIGN KEY (`idFamily`) REFERENCES `family` (`idFamily`),
  CONSTRAINT `FK_Pollinator_User` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pollinator`
--

LOCK TABLES `pollinator` WRITE;
/*!40000 ALTER TABLE `pollinator` DISABLE KEYS */;
/*!40000 ALTER TABLE `pollinator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family` (
  `idFamily` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `idOrder` int DEFAULT NULL,
  `idClade` int DEFAULT NULL,
  PRIMARY KEY (`idFamily`),
  KEY `FK_Family_Clade_idx` (`idClade`),
  KEY `FK_Family_Order_idx` (`idOrder`),
  CONSTRAINT `FK_Family_Clade` FOREIGN KEY (`idClade`) REFERENCES `clade` (`idClade`),
  CONSTRAINT `FK_Family_Order` FOREIGN KEY (`idOrder`) REFERENCES `order` (`idOrder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `idOrder` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`idOrder`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'Hymenoptera'),(2,'Diptera'),(3,'Lepidoptera'),(4,'Coleoptera'),(5,'Aves'),(6,'Mamíferos');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floweringplant`
--

DROP TABLE IF EXISTS `floweringplant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `floweringplant` (
  `scientificName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `genericName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text NOT NULL,
  `idFamily` int NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`scientificName`),
  KEY `FK_FloweringPlant_Family_idx` (`idFamily`),
  KEY `FK_FloweringPlant_User_idx` (`username`),
  CONSTRAINT `FK_FloweringPlant_Family` FOREIGN KEY (`idFamily`) REFERENCES `family` (`idFamily`),
  CONSTRAINT `FK_FloweringPlant_User` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floweringplant`
--

LOCK TABLES `floweringplant` WRITE;
/*!40000 ALTER TABLE `floweringplant` DISABLE KEYS */;
/*!40000 ALTER TABLE `floweringplant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `idRole` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Investigador científico'),(2,'Administrador');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `paternalSurname` varchar(50) NOT NULL,
  `maternalSurname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `professionalLicenseNumber` varchar(45) DEFAULT NULL,
  `idRole` int NOT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_User_Role_idx` (`idRole`),
  CONSTRAINT `FK_User_Role` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-30 19:14:10
