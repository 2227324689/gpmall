/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gpmall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-16 00:52:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sale_coupon_code
-- ----------------------------
DROP TABLE IF EXISTS `tb_sale_coupon_code`;
CREATE TABLE `tb_sale_coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '优惠码',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 未领取 1 已领取未使用 2 冻结中 3 已使用 4 已过期',
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '关联的订单id',
  `consume_time` datetime DEFAULT NULL COMMENT '使用时间',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
