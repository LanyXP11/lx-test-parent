

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '订单名称',
  `order_createtime` datetime DEFAULT NULL COMMENT '下单时间',
  `order_state` int(11) DEFAULT NULL COMMENT '订单状态 0 已经未支付 1已经支付 2已退单',
  `order_money` double(10,0) DEFAULT NULL COMMENT '订单价格',
  `commodity_id` int(10) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------

