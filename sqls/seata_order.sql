-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 172.18.63.11    Database: seata_order
-- ------------------------------------------------------
-- Server version       8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `seata_state_inst`
--

DROP TABLE IF EXISTS `seata_state_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seata_state_inst` (
  `id` varchar(48) NOT NULL COMMENT 'id',
  `machine_inst_id` varchar(128) NOT NULL COMMENT 'state machine instance id',
  `name` varchar(128) NOT NULL COMMENT 'state name',
  `type` varchar(20) DEFAULT NULL COMMENT 'state type',
  `service_name` varchar(128) DEFAULT NULL COMMENT 'service name',
  `service_method` varchar(128) DEFAULT NULL COMMENT 'method name',
  `service_type` varchar(16) DEFAULT NULL COMMENT 'service type',
  `business_key` varchar(48) DEFAULT NULL COMMENT 'business key',
  `state_id_compensated_for` varchar(50) DEFAULT NULL COMMENT 'state compensated for',
  `state_id_retried_for` varchar(50) DEFAULT NULL COMMENT 'state retried for',
  `gmt_started` datetime(3) NOT NULL COMMENT 'start time',
  `is_for_update` tinyint(1) DEFAULT NULL COMMENT 'is service for update',
  `input_params` text COMMENT 'input parameters',
  `output_params` text COMMENT 'output parameters',
  `status` varchar(2) NOT NULL COMMENT 'status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
  `excep` blob COMMENT 'exception',
  `gmt_updated` datetime(3) DEFAULT NULL COMMENT 'update time',
  `gmt_end` datetime(3) DEFAULT NULL COMMENT 'end time',
  PRIMARY KEY (`id`,`machine_inst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seata_state_inst`
--

LOCK TABLES `seata_state_inst` WRITE;
/*!40000 ALTER TABLE `seata_state_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `seata_state_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seata_state_machine_def`
--

DROP TABLE IF EXISTS `seata_state_machine_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seata_state_machine_def` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT 'name',
  `tenant_id` varchar(32) NOT NULL COMMENT 'tenant id',
  `app_name` varchar(32) NOT NULL COMMENT 'application name',
  `type` varchar(20) DEFAULT NULL COMMENT 'state language type',
  `comment_` varchar(255) DEFAULT NULL COMMENT 'comment',
  `ver` varchar(16) NOT NULL COMMENT 'version',
  `gmt_create` datetime(3) NOT NULL COMMENT 'create time',
  `status` varchar(2) NOT NULL COMMENT 'status(AC:active|IN:inactive)',
  `content` text COMMENT 'content',
  `recover_strategy` varchar(16) DEFAULT NULL COMMENT 'transaction recover strategy(compensate|retry)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seata_state_machine_def`
--

LOCK TABLES `seata_state_machine_def` WRITE;
/*!40000 ALTER TABLE `seata_state_machine_def` DISABLE KEYS */;
/*!40000 ALTER TABLE `seata_state_machine_def` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seata_state_machine_inst`
--

DROP TABLE IF EXISTS `seata_state_machine_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seata_state_machine_inst` (
  `id` varchar(128) NOT NULL COMMENT 'id',
  `machine_id` varchar(32) NOT NULL COMMENT 'state machine definition id',
  `tenant_id` varchar(32) NOT NULL COMMENT 'tenant id',
  `parent_id` varchar(128) DEFAULT NULL COMMENT 'parent id',
  `gmt_started` datetime(3) NOT NULL COMMENT 'start time',
  `business_key` varchar(48) DEFAULT NULL COMMENT 'business key',
  `start_params` text COMMENT 'start parameters',
  `gmt_end` datetime(3) DEFAULT NULL COMMENT 'end time',
  `excep` blob COMMENT 'exception',
  `end_params` text COMMENT 'end parameters',
  `status` varchar(2) DEFAULT NULL COMMENT 'status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
  `compensation_status` varchar(2) DEFAULT NULL COMMENT 'compensation status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
  `is_running` tinyint(1) DEFAULT NULL COMMENT 'is running(0 no|1 yes)',
  `gmt_updated` datetime(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unikey_buz_tenant` (`business_key`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seata_state_machine_inst`
--

LOCK TABLES `seata_state_machine_inst` WRITE;
/*!40000 ALTER TABLE `seata_state_machine_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `seata_state_machine_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL DEFAULT '',
  `product_id` bigint NOT NULL,
  `count` int NOT NULL DEFAULT '1',
  `price` int NOT NULL DEFAULT '0',
  `status` tinyint NOT NULL DEFAULT '0',
  `create_time` int NOT NULL,
  `update_time` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='order table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
INSERT INTO `t_order` VALUES (1,'1',1,10,20,1,1693194072,1693194072),(2,'1',1,1,2,0,1693292781,1693292781);
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tcc_fence_log`
--

DROP TABLE IF EXISTS `tcc_fence_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tcc_fence_log` (
  `xid` varchar(128) NOT NULL COMMENT 'global id',
  `branch_id` bigint NOT NULL COMMENT 'branch id',
  `action_name` varchar(64) NOT NULL COMMENT 'action name',
  `status` tinyint NOT NULL COMMENT 'status(tried:1;committed:2;rollbacked:3;suspended:4)',
  `gmt_create` datetime(3) NOT NULL COMMENT 'create time',
  `gmt_modified` datetime(3) NOT NULL COMMENT 'update time',
  PRIMARY KEY (`xid`,`branch_id`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tcc_fence_log`
--

LOCK TABLES `tcc_fence_log` WRITE;
/*!40000 ALTER TABLE `tcc_fence_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tcc_fence_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`),
  KEY `ix_log_created` (`log_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-29 16:51:29