-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: kg_platform
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_code` varchar(100) NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_scope` varchar(20) NOT NULL COMMENT '角色范围',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'PLATFORM_ADMIN','平台管理员','PLATFORM','平台侧管理员角色',_binary '',_binary '\0','system','2026-04-24 11:14:47','system','2026-04-24 11:14:47'),(2,'OPERATOR','普通运营','PLATFORM','平台侧运营角色',_binary '',_binary '\0','system','2026-04-24 11:14:47','system','2026-04-24 11:14:47');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_tenant`
--

DROP TABLE IF EXISTS `sys_user_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_tenant` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  `identity_type` varchar(20) NOT NULL COMMENT '身份类型',
  `default_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否默认租户',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_tenant_identity` (`user_id`,`tenant_id`,`identity_type`),
  KEY `idx_tenant_user` (`tenant_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户租户关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_tenant`
--

LOCK TABLES `sys_user_tenant` WRITE;
/*!40000 ALTER TABLE `sys_user_tenant` DISABLE KEYS */;
INSERT INTO `sys_user_tenant` VALUES (1,1,1,'PLATFORM',_binary '',_binary '',_binary '\0','system','2026-04-23 23:04:42','system','2026-04-24 14:40:15');
/*!40000 ALTER TABLE `sys_user_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

LOCK TABLES `role_menu` WRITE;
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (1,1,1,_binary '',_binary '\0','system','2026-04-27 15:35:54','system','2026-04-27 15:35:54'),(2,1,2,_binary '',_binary '\0','system','2026-04-27 15:35:54','system','2026-04-27 15:35:54');
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tenant_role`
--

DROP TABLE IF EXISTS `user_tenant_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tenant_role` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_tenant_id` bigint NOT NULL COMMENT '用户租户关系ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_tenant_role` (`user_tenant_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户租户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tenant_role`
--

LOCK TABLES `user_tenant_role` WRITE;
/*!40000 ALTER TABLE `user_tenant_role` DISABLE KEYS */;
INSERT INTO `user_tenant_role` VALUES (1,1,1,_binary '',_binary '\0','system','2026-04-27 15:35:54','system','2026-04-27 15:35:54');
/*!40000 ALTER TABLE `user_tenant_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission_point`
--

DROP TABLE IF EXISTS `sys_permission_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission_point` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `permission_code` varchar(100) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(100) NOT NULL COMMENT '权限名称',
  `permission_type` varchar(20) NOT NULL COMMENT '权限类型',
  `permission_scope` varchar(20) NOT NULL COMMENT '权限范围',
  `bind_menu_id` bigint DEFAULT NULL COMMENT '绑定菜单ID',
  `api_path` varchar(255) DEFAULT NULL COMMENT 'API路径',
  `api_method` varchar(20) DEFAULT NULL COMMENT 'API请求方法',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission_point`
--

LOCK TABLES `sys_permission_point` WRITE;
/*!40000 ALTER TABLE `sys_permission_point` DISABLE KEYS */;
INSERT INTO `sys_permission_point` VALUES (1,'PLATFORM_USER_ADD','用户新增按钮','BUTTON','PLATFORM',2,NULL,NULL,'平台用户新增按钮权限点',_binary '',_binary '\0','system','2026-04-27 15:48:34','system','2026-04-27 15:48:34'),(2,'PLATFORM_USER_QUERY_API','用户分页查询接口','API','PLATFORM',NULL,'/users/pages','GET','平台用户分页查询接口权限点',_binary '',_binary '\0','system','2026-04-27 15:48:34','system','2026-04-27 15:48:34');
/*!40000 ALTER TABLE `sys_permission_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission_point`
--

DROP TABLE IF EXISTS `role_permission_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission_point` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_point_id` bigint NOT NULL COMMENT '权限点ID',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `last_modified_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission_point` (`role_id`,`permission_point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限点关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission_point`
--

LOCK TABLES `role_permission_point` WRITE;
/*!40000 ALTER TABLE `role_permission_point` DISABLE KEYS */;
INSERT INTO `role_permission_point` VALUES (1,1,1,_binary '',_binary '\0','system','2026-04-27 15:35:54','system','2026-04-27 15:35:54'),(2,1,2,_binary '',_binary '\0','system','2026-04-27 15:35:54','system','2026-04-27 15:35:54');
/*!40000 ALTER TABLE `role_permission_point` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-27 17:52:50
