/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gpmall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-16 00:52:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sale_rule
-- ----------------------------
DROP TABLE IF EXISTS `tb_sale_rule`;
CREATE TABLE `tb_sale_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acti_id` bigint(20) DEFAULT NULL COMMENT '活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null',
  `item_id` bigint(20) DEFAULT NULL COMMENT '满足规则条件的商品id',
  `full_price` decimal(10,2) DEFAULT NULL,
  `discount_price` decimal(10,2) DEFAULT NULL,
  `discount_rate` decimal(10,2) DEFAULT NULL,
  `gift_item_id` bigint(20) DEFAULT NULL,
  `overlap` bit(1) DEFAULT b'0' COMMENT '是否可叠加规则',
  `desc` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
