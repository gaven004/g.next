CREATE DATABASE  IF NOT EXISTS `next` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `next`;
-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: next
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_article`
--

DROP TABLE IF EXISTS `sys_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_article` (
  `id` bigint NOT NULL COMMENT '主键id',
  `column_id` bigint NOT NULL COMMENT '栏目ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `body` longtext NOT NULL COMMENT '内容',
  `redirecturl` varchar(255) DEFAULT '' COMMENT '静态URL',
  `create_by` bigint NOT NULL COMMENT '创建人',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_by` bigint DEFAULT NULL COMMENT '修改人',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `sort_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序权重，越高越排前',
  `state` enum('MODIFIED','PUBLISH','DOWN','DELETED') NOT NULL COMMENT '状态：编辑、发布、落画、删除',
  PRIMARY KEY (`id`),
  KEY `idx_column` (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_authorities`
--

DROP TABLE IF EXISTS `sys_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_authorities` (
  `uid` bigint NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`,`authority`),
  KEY `idx_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_authorities`
--

LOCK TABLES `sys_authorities` WRITE;
/*!40000 ALTER TABLE `sys_authorities` DISABLE KEYS */;
INSERT INTO `sys_authorities` VALUES (1,'ADMIN');
/*!40000 ALTER TABLE `sys_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_column`
--

DROP TABLE IF EXISTS `sys_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_column` (
  `id` bigint NOT NULL COMMENT '栏目ID',
  `cname` varchar(255) NOT NULL COMMENT '栏目名称',
  `curl` varchar(255) DEFAULT NULL COMMENT 'url地址标识',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父栏目',
  `state` enum('VALID','INVALID') NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统文章栏目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dict_category`
--

DROP TABLE IF EXISTS `sys_dict_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '字典类型名',
  `code` varchar(20) NOT NULL COMMENT '字典编码',
  `remark` varchar(20) NOT NULL COMMENT '备注',
  `state` enum('VALID','INVALID') DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_category`
--

LOCK TABLES `sys_dict_category` WRITE;
/*!40000 ALTER TABLE `sys_dict_category` DISABLE KEYS */;
INSERT INTO `sys_dict_category` VALUES (1,'文件类型','FILE TYPE','文件类型','VALID');
/*!40000 ALTER TABLE `sys_dict_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary`
--

DROP TABLE IF EXISTS `sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL COMMENT '字典类型ID',
  `category` varchar(20) NOT NULL COMMENT '字典类型',
  `code` varchar(20) NOT NULL COMMENT '字典编码',
  `name` varchar(20) NOT NULL COMMENT '字典名称',
  `property_1` varchar(20) DEFAULT NULL COMMENT '属性1',
  `property_2` varchar(20) DEFAULT NULL COMMENT '属性2',
  `property_3` varchar(20) DEFAULT NULL COMMENT '属性3',
  `property_4` varchar(20) DEFAULT NULL COMMENT '属性4',
  `state` enum('VALID','INVALID') DEFAULT NULL COMMENT '字典状态',
  `sort_order` int(11) DEFAULT NULL COMMENT '字典排序',
  `note` varchar(200) DEFAULT NULL COMMENT '字典备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary`
--

LOCK TABLES `sys_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` VALUES (1,NULL,'FILE TYPE','GIF','GIF','','','','','VALID',NULL,''),(2,NULL,'FILE TYPE','JPG','JPG','','','','','VALID',NULL,''),(3,NULL,'FILE TYPE','PNG','PNG','','','','','VALID',NULL,'');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '操作人ID',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operation` enum('CREATE','UPDATE','DELETE') NOT NULL COMMENT '操作指令',
  `clazz` varchar(255) NOT NULL COMMENT '操作对象',
  `oid` varchar(128) NOT NULL COMMENT '对象主键',
  `content` text COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_time` (`ctime`),
  KEY `idx_obj` (`clazz`,`oid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COMMENT='对象变更日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_persistent_logins`
--

DROP TABLE IF EXISTS `sys_persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_persistent_logins` (
  `uid` bigint DEFAULT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) DEFAULT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_users`
--

DROP TABLE IF EXISTS `sys_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_users` (
  `uid` bigint NOT NULL,
  `account` varchar(15) NOT NULL COMMENT '用户账号',
  `username` varchar(50) NOT NULL COMMENT '用户名称',
  `password` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `enabled` enum('VALID','INVALID') NOT NULL DEFAULT 'VALID',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idx_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_users`
--

LOCK TABLES `sys_users` WRITE;
/*!40000 ALTER TABLE `sys_users` DISABLE KEYS */;
INSERT INTO `sys_users` VALUES (1,'admin','系统管理员','$2a$10$FSNBAj8tkfOTMCxLiy6DnOEIwvVfeIJTKqMNUnqHbfKt80aalPbhS','admin@next.com','VALID');
/*!40000 ALTER TABLE `sys_users` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-11 16:37:12
