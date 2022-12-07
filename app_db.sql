/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : next

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/11/2022 15:40:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action` (
  `id` bigint NOT NULL COMMENT ' ',
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `method` enum('ALL','GET','POST','PUT','DELETE','PATCH','OPTIONS','HEAD') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `permit_all` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL DEFAULT 'VALID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统功能';

-- ----------------------------
-- Records of sys_action
-- ----------------------------
BEGIN;
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (140167759693611008, '/sys/action', 'ALL', '系统功能管理', 0, 'VALID');
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (143127930975813632, '/sys/menu', 'ALL', '系统菜单管理', 0, 'VALID');
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (143139884427640832, '/sys/property/categories', 'ALL', '参数类型管理', 0, 'VALID');
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (143139969001586688, '/sys/properties', 'ALL', '系统参数管理', 0, 'VALID');
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (143140058881327104, '/sys/roles', 'ALL', '角色管理', 0, 'VALID');
INSERT INTO `sys_action` (`id`, `resource`, `method`, `description`, `permit_all`, `status`) VALUES (143140137797156864, '/sys/users', 'ALL', '用户管理', 0, 'VALID');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL,
  `uid` bigint NOT NULL COMMENT '操作员ID',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `operation` enum('CREATE','UPDATE','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '操作指令',
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '操作对象',
  `oid` json DEFAULT NULL COMMENT '对象主键',
  `content` json DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_clazz` (`clazz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL COMMENT ' ',
  `parent_id` bigint NOT NULL DEFAULT '0',
  `label` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `order` int DEFAULT NULL,
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL DEFAULT 'VALID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (140416512530841600, 0, '系统管理', '系统管理', 'AiOutlineSetting', '', NULL, 1, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (140606123509022720, 140416512530841600, '系统功能', '系统功能', NULL, '/sys/action', './sys/Action', 1, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (140606307425058816, 140416512530841600, '系统菜单', '系统菜单', NULL, '/sys/menu', './sys/Menu', 2, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (143129379252207616, 140416512530841600, '参数类型', '参数类型', NULL, '/sys/property/categories', './sys/PropertyCategories', 3, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (143130889742712832, 140416512530841600, '系统参数', '系统参数', NULL, '/sys/properties', './sys/Properties', 4, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (143131111843692544, 140416512530841600, '角色管理', '角色管理', NULL, '/sys/roles', './sys/Roles', 5, 'VALID');
INSERT INTO `sys_menu` (`id`, `parent_id`, `label`, `title`, `icon`, `url`, `component`, `order`, `status`) VALUES (143131272363900928, 140416512530841600, '用户管理', '用户管理', NULL, '/sys/users', './sys/Users', 6, 'VALID');
COMMIT;

-- ----------------------------
-- Table structure for sys_persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `sys_persistent_logins`;
CREATE TABLE `sys_persistent_logins` (
  `uid` bigint DEFAULT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_persistent_logins
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_properties
-- ----------------------------
DROP TABLE IF EXISTS `sys_properties`;
CREATE TABLE `sys_properties` (
  `id` bigint NOT NULL,
  `category` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '参数类型',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '参数名',
  `value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '参数值',
  `properties` json DEFAULT NULL COMMENT '扩展属性',
  `sort_order` int DEFAULT '0' COMMENT '参考排序',
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT 'VALID' COMMENT '状态',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`category`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_properties
-- ----------------------------
BEGIN;
INSERT INTO `sys_properties` (`id`, `category`, `name`, `value`, `properties`, `sort_order`, `status`, `note`) VALUES (359040721228922880, 'FILE_TYPE', 'GIF', 'GIF', NULL, 0, 'VALID', '');
INSERT INTO `sys_properties` (`id`, `category`, `name`, `value`, `properties`, `sort_order`, `status`, `note`) VALUES (364097906363334656, 'FILE_TYPE', 'JPG', 'JPG', NULL, 0, 'VALID', '');
INSERT INTO `sys_properties` (`id`, `category`, `name`, `value`, `properties`, `sort_order`, `status`, `note`) VALUES (364097947324907520, 'FILE_TYPE', 'PNG', 'PNG', NULL, 0, 'VALID', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_property_categories
-- ----------------------------
DROP TABLE IF EXISTS `sys_property_categories`;
CREATE TABLE `sys_property_categories` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '属性类型',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '属性类型名',
  `note` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT 'VALID' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统属性类型表';

-- ----------------------------
-- Records of sys_property_categories
-- ----------------------------
BEGIN;
INSERT INTO `sys_property_categories` (`id`, `name`, `note`, `status`) VALUES ('FILE_TYPE', '文件类型', '', 'VALID');
INSERT INTO `sys_property_categories` (`id`, `name`, `note`, `status`) VALUES ('UNIT', '单位', NULL, 'VALID');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities` (
  `role_id` bigint NOT NULL,
  `authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  PRIMARY KEY (`role_id`,`authority`) USING BTREE,
  KEY `idx_user` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_role_authorities
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_authorities` (`role_id`, `authority`) VALUES (140228410537410560, 'ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_members
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_members`;
CREATE TABLE `sys_role_members` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_role_members
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_members` (`user_id`, `role_id`) VALUES (1, 140228410537410560);
INSERT INTO `sys_role_members` (`user_id`, `role_id`) VALUES (1, 364903511734878208);
COMMIT;

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` bigint NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL DEFAULT 'VALID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (140228410537410560, '系统管理员', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (364903511734878208, '游客', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (365090215666122752, '领导', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (365090240060194816, '会计', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (365090278861701120, '仓管', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (365090304719585280, '人事', 'VALID');
INSERT INTO `sys_roles` (`id`, `name`, `status`) VALUES (365090361212665856, '综合办', 'VALID');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_authorities`;
CREATE TABLE `sys_user_authorities` (
  `uid` bigint NOT NULL,
  `authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  PRIMARY KEY (`uid`,`authority`),
  KEY `idx_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_user_authorities
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_authorities` (`uid`, `authority`) VALUES (1, 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` bigint NOT NULL,
  `account` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '用户账号',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '用户名称',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `email` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `status` enum('VALID','INVALID') CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL DEFAULT 'VALID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
BEGIN;
INSERT INTO `sys_users` (`id`, `account`, `username`, `password`, `email`, `status`) VALUES (1, 'admin', '系统管理员', '$2a$10$FSNBAj8tkfOTMCxLiy6DnOEIwvVfeIJTKqMNUnqHbfKt80aalPbhS', 'admin@next.com', 'VALID');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
