
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL,
  `uid` bigint NOT NULL COMMENT '操作员ID',
  `ctime` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `operation` enum('CREATE','UPDATE','DELETE') NOT NULL COMMENT '操作指令',
  `clazz` varchar(255) DEFAULT NULL COMMENT '操作对象',
  `oid` json DEFAULT NULL COMMENT '对象主键',
  `content` json DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_ctime` (`ctime`),
  KEY `idx_clazz` (`clazz`)
) COMMENT='系统日志表';

-- ----------------------------
-- Table structure for sys_properties
-- ----------------------------
DROP TABLE IF EXISTS `sys_properties`;
CREATE TABLE `sys_properties` (
  `category` varchar(32) NOT NULL COMMENT '参数类型',
  `name` varchar(32) NOT NULL COMMENT '参数名',
  `value` varchar(32) DEFAULT NULL COMMENT '参数值',
  `properties` json DEFAULT NULL COMMENT '扩展属性',
  `status` enum('VALID','INVALID') DEFAULT 'VALID' COMMENT '状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`category`,`name`)
) COMMENT='系统参数表';

SET FOREIGN_KEY_CHECKS = 1;
