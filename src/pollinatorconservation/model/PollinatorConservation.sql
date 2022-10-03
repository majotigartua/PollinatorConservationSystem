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
INSERT INTO `clade` VALUES (1,'Amborellales'),(2,'Nymphaeales'),(3,'Austrobaileyales'),(4,'Chloranthales'),(5,'Magnolianae'),(6,'Ceratophyllales'),(7,'Eudicotyledoneae'),(8,'Monocotyledoneae');
/*!40000 ALTER TABLE `clade` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` VALUES (4,'Xyeloidea',1,NULL),(5,'Tenthredinoidea',1,NULL),(6,'Pamphiloidea',1,NULL),(7,'Cephoidea',1,NULL),(8,'Siricoidea',1,NULL),(9,'Xiphydrioidea',1,NULL),(10,'Orussoidea',1,NULL),(11,'Apocrita',1,NULL),(12,'Ptychopteromorpha',2,NULL),(13,'Culicomorpha',2,NULL),(14,'Blephariceromorpha',2,NULL),(15,'Bibionomorpha',2,NULL),(16,'Psychodomorpha',2,NULL),(17,'Tipuloidea',2,NULL),(18,'Stratiomyomorpha',2,NULL),(19,'Xylophagomorpha',2,NULL),(20,'Tabanomorpha',2,NULL),(21,'Nemestrinoidea',2,NULL),(22,'Asiloidea',2,NULL),(23,'Empidoidea',2,NULL),(24,'Aschiza',2,NULL),(25,'Phoroidea',2,NULL),(26,'Syrphoidea',2,NULL),(27,'Hippoboscoidea',2,NULL),(28,'Muscoidea',2,NULL),(29,'Oestroidea',2,NULL),(30,'Acalyptratae',2,NULL),(31,'Zeugloptera',3,NULL),(32,'Aglossata',3,NULL),(33,'Heterobathmiina',3,NULL),(34,'Glossata',3,NULL),(35,'Archostemata',4,NULL),(36,'Adephaga',4,NULL),(37,'Myxophaga',4,NULL),(38,'Polyphaga',4,NULL),(39,'Amborella',NULL,1),(40,'Hydatellaceae',NULL,2),(41,'Cabombaceae',NULL,2),(42,'Nymphaeaceae',NULL,2),(43,'Austrobaileyaceae',NULL,3),(44,'Schisandraceae',NULL,3),(45,'Trimeniaceae',NULL,3),(46,'Sarcandra',NULL,4),(47,'Chloranthus',NULL,4),(48,'Ascarina',NULL,4),(49,'Hedyosmum',NULL,4),(50,'Magnoliidae',NULL,5),(51,'Lauraceae',NULL,5),(52,'Canellales',NULL,5),(53,'Piperales',NULL,5),(54,'Ceratophyllum',NULL,6),(55,'Ranunculales',NULL,7),(56,'Sabiaceae',NULL,7),(57,'Proteales',NULL,7),(58,'Trochodendraceae',NULL,7),(59,'Buxales',NULL,7),(60,'Gunnerales',NULL,7),(61,'Dilleniales',NULL,7),(62,'Saxifragales',NULL,7),(63,'Fabidae',NULL,7),(64,'Malvidae',NULL,7),(65,'Santalales',NULL,7),(66,'Berberidopsidales',NULL,7),(67,'Caryophyllales',NULL,7),(68,'Cornales',NULL,7),(69,'Ericales',NULL,7),(70,'Lamiidae',NULL,7),(71,'Campanulidae',NULL,7),(72,'Acorales',NULL,8),(73,'Alismatales',NULL,8),(74,'Petrosaviales',NULL,8),(75,'Dioscoreales',NULL,8),(76,'Pandanales',NULL,8),(77,'Liliales',NULL,8),(78,'Asparagales',NULL,8),(79,'Arecales',NULL,8),(80,'Commelinales',NULL,8),(81,'Zingiberales',NULL,8),(82,'Poales',NULL,8);
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
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
  PRIMARY KEY (`scientificName`),
  KEY `FK_FloweringPlant_Family_idx` (`idFamily`),
  CONSTRAINT `FK_FloweringPlant_Family` FOREIGN KEY (`idFamily`) REFERENCES `family` (`idFamily`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floweringplant`
--

LOCK TABLES `floweringplant` WRITE;
/*!40000 ALTER TABLE `floweringplant` DISABLE KEYS */;
INSERT INTO `floweringplant` VALUES ('Amborella trichopoda','Amborella','Es un arbusto de gran porte o arbolito, algo trepador, siempreverde, de hasta 8 m. de altura, tomento de pelos uniseriados multicelulares (a veces unicelulares). Sus hojas son alternas, espiraladas a dísticas en la madurez, pecioladas, sin estípulas, de margen ondulado a dentado, a veces pinnatífidas, pinnatinervias, las venas conexas cerca del margen, estomas paracíticos a anomocíticos, sólo en la superficie abaxial.',39);
/*!40000 ALTER TABLE `floweringplant` ENABLE KEYS */;
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
INSERT INTO `order` VALUES (1,'Hymenoptera'),(2,'Diptera'),(3,'Lepidoptera'),(4,'Coleoptera');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
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
  PRIMARY KEY (`scientificName`),
  KEY `FK_Pollinator_Family_idx` (`idFamily`),
  CONSTRAINT `FK_Pollinator_Familiy` FOREIGN KEY (`idFamily`) REFERENCES `family` (`idFamily`)
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
  `password` varchar(250) NOT NULL,
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
INSERT INTO `user` VALUES ('alvarobarradas','Álvaro','Barradas','Fernández','0a235d331f06ae9bac43c586347e8359359126444ace11c99ebf8cfc51e09435',NULL,2),('majotigartua','María José','Torres','Igartua','f1221aa6a7dc3a16cacfae6a0dc1c2e8843733d3af47067e3da6df4bc2a49402','TOIJ010607AB8',1),('oscarcarsi','Óscar Iván','Olivares','Carsi','03e5f10b676430b589ac4b9d1a9126c6c218e058469f888b7843e131ae2a3a3d',NULL,2),('sebastianbello','Sebastián','Bello','Trejo','cfd1cb5a2be87c7c6fb0d1e43b28ba6c382842be054f7ee33f69570cc1572eb6',NULL,1);
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

-- Dump completed on 2022-10-02 18:20:32
