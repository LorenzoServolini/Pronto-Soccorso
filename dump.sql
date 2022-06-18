CREATE DATABASE  IF NOT EXISTS `archivioarrivi` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `archivioarrivi`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: archivioarrivi
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `arrivi`
--

DROP TABLE IF EXISTS `arrivi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arrivi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nominativo` varchar(45) DEFAULT NULL,
  `codice` varchar(10) DEFAULT NULL,
  `saturazione` int(10) DEFAULT '-1',
  `battiti` int(10) DEFAULT '-1',
  `pressione` varchar(10) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `ora` varchar(15) DEFAULT NULL,
  `trasporto` varchar(10) DEFAULT NULL,
  `triagista` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arrivi`
--

LOCK TABLES `arrivi` WRITE;
/*!40000 ALTER TABLE `arrivi` DISABLE KEYS */;
INSERT INTO `arrivi` VALUES (1,'Marco Alpha','SC01G',98,102,'150/80','Incidente stradale: trauma cervicale, assenza emorragie, paz. stabile','2021-09-12','10:28','Ambulanza','Cristina Beta'),(2,'Lucia Gamma','KC04R',88,63,'90/60','TIA, paz. intubato','2021-09-12','10:31','Pegaso','Cristina Beta'),(3,'Andrea Delta','QC08V',100,80,'100/90','Dolore addominale acuto regione ombelicale','2021-09-12','11:03','Ambulanza','Giuseppe Epsilon'),(4,'Carla Zeta','Bianco',99,71,'110/80','Frattura composta radio','2021-09-12','11:17','Privato','Giuseppe Epsilon'),(5,'Chiara Mi','Rosso',94,124,'80/50','Emorragia interna','2021-09-12','20:37','Privato','Giuseppe Epsilon'),(6,'Graziella Eta','Bianco',98,66,'100/80','Malessere generale','2021-09-15','15:45','Privato','Cristina Beta'),(7,'Giuseppe Theta','Rosso',40,51,'80/60','Edema polmonare','2021-09-16','23:44','Privato','Jacopo Iota'),(8,'Alessandra Kappa','SC15G',99,78,'90/90','Febbre alta (40.5 °C)','2021-09-17','06:34','Ambulanza','Cristina Beta'),(9,'Maurizio Lambda','SC01R',95,53,'80/50','Precipitato da > 3 metri, varie fratture, pneumotorace, segno del procione','2021-09-17','17:05','Ambulanza','Jacopo Iota'),(10,'Mario Ni','Verde',100,71,'90/80','Microfrattura III metatarso','2021-09-17','17:09','Privato','Jacopo Iota'),(11,'Alessio Csi','Verde',99,84,'100/90','Mal di testa','2021-09-17','21:13','Privato','Jacopo Iota');
/*!40000 ALTER TABLE `arrivi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-13 19:53:13
