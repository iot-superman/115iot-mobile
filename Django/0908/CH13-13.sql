CREATE DATABASE  IF NOT EXISTS `ch3_13_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ch3_13_1`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: ch3-13-1
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group_permissions`
--

DROP TABLE IF EXISTS `auth_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group_permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add log entry',1,'add_logentry'),(2,'Can change log entry',1,'change_logentry'),(3,'Can delete log entry',1,'delete_logentry'),(4,'Can view log entry',1,'view_logentry'),(5,'Can add permission',2,'add_permission'),(6,'Can change permission',2,'change_permission'),(7,'Can delete permission',2,'delete_permission'),(8,'Can view permission',2,'view_permission'),(9,'Can add group',3,'add_group'),(10,'Can change group',3,'change_group'),(11,'Can delete group',3,'delete_group'),(12,'Can view group',3,'view_group'),(13,'Can add user',4,'add_user'),(14,'Can change user',4,'change_user'),(15,'Can delete user',4,'delete_user'),(16,'Can view user',4,'view_user'),(17,'Can add content type',5,'add_contenttype'),(18,'Can change content type',5,'change_contenttype'),(19,'Can delete content type',5,'delete_contenttype'),(20,'Can view content type',5,'view_contenttype'),(21,'Can add session',6,'add_session'),(22,'Can change session',6,'change_session'),(23,'Can delete session',6,'delete_session'),(24,'Can view session',6,'view_session'),(25,'Can add author',7,'add_author'),(26,'Can change author',7,'change_author'),(27,'Can delete author',7,'delete_author'),(28,'Can view author',7,'view_author'),(29,'Can add student',8,'add_student'),(30,'Can change student',8,'change_student'),(31,'Can delete student',8,'delete_student'),(32,'Can view student',8,'view_student'),(33,'Can add scorelist',9,'add_scorelist'),(34,'Can change scorelist',9,'change_scorelist'),(35,'Can delete scorelist',9,'delete_scorelist'),(36,'Can view scorelist',9,'view_scorelist'),(37,'Can add permissions',10,'add_permissions'),(38,'Can change permissions',10,'change_permissions'),(39,'Can delete permissions',10,'delete_permissions'),(40,'Can view permissions',10,'view_permissions'),(41,'Can add book',11,'add_book'),(42,'Can change book',11,'change_book'),(43,'Can delete book',11,'delete_book'),(44,'Can view book',11,'view_book');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(150) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_groups`
--

DROP TABLE IF EXISTS `auth_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`),
  CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_groups`
--

LOCK TABLES `auth_user_groups` WRITE;
/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_user_permissions`
--

DROP TABLE IF EXISTS `auth_user_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_user_permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_user_permissions`
--

LOCK TABLES `auth_user_user_permissions` WRITE;
/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_admin_log`
--

DROP TABLE IF EXISTS `django_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_admin_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`),
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`),
  CONSTRAINT `django_admin_log_chk_1` CHECK ((`action_flag` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_content_type`
--

DROP TABLE IF EXISTS `django_content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_content_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (1,'admin','logentry'),(3,'auth','group'),(2,'auth','permission'),(4,'auth','user'),(5,'contenttypes','contenttype'),(7,'myapp','author'),(11,'myapp','book'),(10,'myapp','permissions'),(9,'myapp','scorelist'),(8,'myapp','student'),(6,'sessions','session');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_migrations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'contenttypes','0001_initial','2025-01-14 06:18:04.266801'),(2,'auth','0001_initial','2025-01-14 06:18:05.118653'),(3,'admin','0001_initial','2025-01-14 06:18:05.298003'),(4,'admin','0002_logentry_remove_auto_add','2025-01-14 06:18:05.308121'),(5,'admin','0003_logentry_add_action_flag_choices','2025-01-14 06:18:05.318472'),(6,'contenttypes','0002_remove_content_type_name','2025-01-14 06:18:05.442785'),(7,'auth','0002_alter_permission_name_max_length','2025-01-14 06:18:05.523432'),(8,'auth','0003_alter_user_email_max_length','2025-01-14 06:18:05.551586'),(9,'auth','0004_alter_user_username_opts','2025-01-14 06:18:05.560280'),(10,'auth','0005_alter_user_last_login_null','2025-01-14 06:18:05.629748'),(11,'auth','0006_require_contenttypes_0002','2025-01-14 06:18:05.634760'),(12,'auth','0007_alter_validators_add_error_messages','2025-01-14 06:18:05.643704'),(13,'auth','0008_alter_user_username_max_length','2025-01-14 06:18:05.730778'),(14,'auth','0009_alter_user_last_name_max_length','2025-01-14 06:18:05.815759'),(15,'auth','0010_alter_group_name_max_length','2025-01-14 06:18:05.838823'),(16,'auth','0011_update_proxy_permissions','2025-01-14 06:18:05.847639'),(17,'auth','0012_alter_user_first_name_max_length','2025-01-14 06:18:05.967821'),(18,'myapp','0001_initial','2025-01-14 06:18:06.438022'),(19,'sessions','0001_initial','2025-01-14 06:18:06.489710');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_session`
--

DROP TABLE IF EXISTS `django_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_expire_date_a5c62663` (`expire_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_author`
--

DROP TABLE IF EXISTS `myapp_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `aID` varchar(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_author`
--

LOCK TABLES `myapp_author` WRITE;
/*!40000 ALTER TABLE `myapp_author` DISABLE KEYS */;
INSERT INTO `myapp_author` VALUES (1,'a10002001','小明2'),(3,'a10002003','小虎'),(4,'b100001','John');
/*!40000 ALTER TABLE `myapp_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_book`
--

DROP TABLE IF EXISTS `myapp_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_book`
--

LOCK TABLES `myapp_book` WRITE;
/*!40000 ALTER TABLE `myapp_book` DISABLE KEYS */;
INSERT INTO `myapp_book` VALUES (2,'97872','C++'),(3,'97873','PHP'),(4,'97874','JAVA'),(5,'97875','Linux'),(6,'8004401','HTML'),(7,'8004402','CSS');
/*!40000 ALTER TABLE `myapp_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_book_authors`
--

DROP TABLE IF EXISTS `myapp_book_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_book_authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `myapp_book_authors_book_id_author_id_9ba1c0ce_uniq` (`book_id`,`author_id`),
  KEY `myapp_book_authors_author_id_995a5078_fk_myapp_author_id` (`author_id`),
  CONSTRAINT `myapp_book_authors_author_id_995a5078_fk_myapp_author_id` FOREIGN KEY (`author_id`) REFERENCES `myapp_author` (`id`),
  CONSTRAINT `myapp_book_authors_book_id_0838c8ea_fk_myapp_book_id` FOREIGN KEY (`book_id`) REFERENCES `myapp_book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_book_authors`
--

LOCK TABLES `myapp_book_authors` WRITE;
/*!40000 ALTER TABLE `myapp_book_authors` DISABLE KEYS */;
INSERT INTO `myapp_book_authors` VALUES (2,2,1),(5,5,3),(6,6,4),(7,7,4);
/*!40000 ALTER TABLE `myapp_book_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_permissions`
--

DROP TABLE IF EXISTS `myapp_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `passwd` varchar(100) NOT NULL,
  `level` varchar(2) NOT NULL,
  `cID_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cID_id` (`cID_id`),
  CONSTRAINT `myapp_permissions_cID_id_4e15bf93_fk_myapp_student_cID` FOREIGN KEY (`cID_id`) REFERENCES `myapp_student` (`cID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_permissions`
--

LOCK TABLES `myapp_permissions` WRITE;
/*!40000 ALTER TABLE `myapp_permissions` DISABLE KEYS */;
INSERT INTO `myapp_permissions` VALUES (1,'1111','1',1),(2,'2222','0',2),(3,'3333','1',3),(4,'4444','1',4),(5,'5555','1',5),(6,'6666','1',6),(7,'7777','0',7),(8,'8888','1',8),(9,'9999','0',9);
/*!40000 ALTER TABLE `myapp_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_scorelist`
--

DROP TABLE IF EXISTS `myapp_scorelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_scorelist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course` varchar(20) NOT NULL,
  `score` int NOT NULL,
  `cID_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `myapp_scorelist_cID_id_3a9e3c90_fk_myapp_student_cID` (`cID_id`),
  CONSTRAINT `myapp_scorelist_cID_id_3a9e3c90_fk_myapp_student_cID` FOREIGN KEY (`cID_id`) REFERENCES `myapp_student` (`cID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_scorelist`
--

LOCK TABLES `myapp_scorelist` WRITE;
/*!40000 ALTER TABLE `myapp_scorelist` DISABLE KEYS */;
INSERT INTO `myapp_scorelist` VALUES (1,'國文',100,1),(2,'國文',68,2),(3,'國文',78,3),(4,'國文',85,4),(5,'國文',80,5),(6,'國文',76,6),(7,'國文',90,7),(8,'國文',87,8),(11,'英文',67,1),(12,'英文',87,2),(13,'英文',88,3),(14,'英文',92,4),(15,'英文',55,5),(16,'英文',62,6),(17,'英文',65,7),(18,'英文',40,8),(19,'英文',89,9),(21,'數學',87,1),(22,'數學',52,2),(23,'數學',76,3),(24,'數學',56,4),(25,'數學',72,5),(26,'數學',80,6),(27,'數學',38,7),(28,'數學',68,8),(29,'數學',90,9);
/*!40000 ALTER TABLE `myapp_scorelist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myapp_student`
--

DROP TABLE IF EXISTS `myapp_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myapp_student` (
  `cID` int NOT NULL AUTO_INCREMENT,
  `cName` varchar(20) NOT NULL,
  `cSex` varchar(1) NOT NULL,
  `cBirthday` date DEFAULT NULL,
  `cEmail` varchar(100) NOT NULL,
  `cPhone` varchar(50) NOT NULL,
  `cAddr` varchar(255) NOT NULL,
  `cHeight` int DEFAULT NULL,
  `cWeight` int DEFAULT NULL,
  PRIMARY KEY (`cID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myapp_student`
--

LOCK TABLES `myapp_student` WRITE;
/*!40000 ALTER TABLE `myapp_student` DISABLE KEYS */;
INSERT INTO `myapp_student` VALUES (1,'簡奉君','F','1987-04-04','elven@superstar.com','0922988876','台北市濟洲北路12號',160,49),(2,'黃靖輪','M','1987-07-01','jinglun@superstar.com','0918181111','台北市敦化南路93號5樓',175,72),(3,'潘四敬','M','1987-08-11','sugie@superstar.com','0914530768','台北市中央路201號7樓',162,65),(4,'賴勝恩','M','1984-06-20','shane@superstar.com','0946820035','台北市建國路177號6樓',178,72),(5,'黎楚寧','F','1988-02-15','ivy@superstar.com','0920981230','台北市忠孝東路520號6樓',164,45),(6,'蔡中穎','M','1987-05-05','zhong@superstar.com','0951983366','台北市三民路1巷10號',172,75),(7,'徐佳螢','F','1985-08-30','lala@superstar.com','0918123456','台北市仁愛路100號',158,56),(8,'林雨媗','F','1986-12-10','crystal@superstar.com','0907408965','台北市民族路204號',166,48),(9,'林心儀','F','1988-12-01','peggy@superstar.com','0916456723','台北市建國北路10號',168,50);
/*!40000 ALTER TABLE `myapp_student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-20 10:41:17
