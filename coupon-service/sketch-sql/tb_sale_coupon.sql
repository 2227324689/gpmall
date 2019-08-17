/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gpmall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-16 00:52:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sale_coupon
-- ----------------------------
DROP TABLE IF EXISTS `tb_sale_coupon`;
CREATE TABLE `tb_sale_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `type` tinyint(4) NOT NULL COMMENT '// 1 满减券 2 通用0元减价券',
  `rule_id` bigint(20) NOT NULL COMMENT '优惠金额',
  `is_visible` bit(1) DEFAULT b'1' COMMENT '是否可用',
  `count` int(10) DEFAULT NULL COMMENT '优惠券可生成的优惠码数量',
  `grab_start_time` datetime DEFAULT NULL COMMENT '抢券开始时间  备用',
  `grab_end_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '优惠券有效期',
  `end_time` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
