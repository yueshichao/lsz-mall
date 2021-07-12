CREATE TABLE `sms_home_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) DEFAULT NULL,
  `brand_name` varchar(64) DEFAULT NULL,
  `recommend_status` int(1) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COMMENT='首页推荐品牌表';


INSERT INTO `sms_home_brand` VALUES ('1', '1', '万和', '1', '200');
INSERT INTO `sms_home_brand` VALUES ('2', '2', '三星', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('6', '6', '小米', '1', '300');
INSERT INTO `sms_home_brand` VALUES ('8', '5', '方太', '1', '100');
INSERT INTO `sms_home_brand` VALUES ('31', '49', '七匹狼', '0', '0');
INSERT INTO `sms_home_brand` VALUES ('32', '50', '海澜之家', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('33', '51', '苹果', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('34', '2', '三星', '0', '0');
INSERT INTO `sms_home_brand` VALUES ('35', '3', '华为', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('36', '4', '格力', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('37', '5', '方太', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('38', '1', '万和', '1', '0');
INSERT INTO `sms_home_brand` VALUES ('39', '21', 'OPPO', '1', '0');