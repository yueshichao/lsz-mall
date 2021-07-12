CREATE TABLE `pms_member_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `member_level_id` bigint(20) DEFAULT NULL,
  `member_price` decimal(10,2) DEFAULT NULL COMMENT '会员价格',
  `member_level_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COMMENT='商品会员价格表';

INSERT INTO `pms_member_price` VALUES ('26', '7', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('27', '8', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('28', '9', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('29', '10', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('30', '11', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('31', '12', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('32', '13', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('33', '14', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('37', '18', '1', '500.00', null);
INSERT INTO `pms_member_price` VALUES ('44', '7', '2', '480.00', null);
INSERT INTO `pms_member_price` VALUES ('45', '7', '3', '450.00', null);
INSERT INTO `pms_member_price` VALUES ('52', '22', '1', null, null);
INSERT INTO `pms_member_price` VALUES ('53', '22', '2', null, null);
INSERT INTO `pms_member_price` VALUES ('54', '22', '3', null, null);
INSERT INTO `pms_member_price` VALUES ('58', '24', '1', null, null);
INSERT INTO `pms_member_price` VALUES ('59', '24', '2', null, null);
INSERT INTO `pms_member_price` VALUES ('60', '24', '3', null, null);
INSERT INTO `pms_member_price` VALUES ('112', '23', '1', '88.00', '黄金会员');
INSERT INTO `pms_member_price` VALUES ('113', '23', '2', '88.00', '白金会员');
INSERT INTO `pms_member_price` VALUES ('114', '23', '3', '66.00', '钻石会员');
INSERT INTO `pms_member_price` VALUES ('142', '31', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('143', '31', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('144', '31', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('148', '32', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('149', '32', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('150', '32', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('154', '33', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('155', '33', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('156', '33', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('175', '34', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('176', '34', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('177', '34', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('178', '30', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('179', '30', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('180', '30', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('192', '27', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('193', '27', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('194', '27', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('195', '28', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('196', '28', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('197', '28', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('198', '29', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('199', '29', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('200', '29', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('201', '26', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('202', '26', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('203', '26', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('246', '36', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('247', '36', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('248', '36', '3', null, '钻石会员');
INSERT INTO `pms_member_price` VALUES ('249', '35', '1', null, '黄金会员');
INSERT INTO `pms_member_price` VALUES ('250', '35', '2', null, '白金会员');
INSERT INTO `pms_member_price` VALUES ('251', '35', '3', null, '钻石会员');


CREATE TABLE `pms_product_ladder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL COMMENT '满足的商品数量',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `price` decimal(10,2) DEFAULT NULL COMMENT '折后价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COMMENT='产品阶梯价格表(只针对同商品)';

INSERT INTO `pms_product_ladder` VALUES ('1', '7', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('2', '8', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('3', '9', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('4', '10', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('5', '11', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('6', '12', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('7', '13', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('8', '14', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('12', '18', '3', '0.70', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('14', '7', '4', '0.60', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('15', '7', '5', '0.50', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('18', '22', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('20', '24', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('38', '23', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('48', '31', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('50', '32', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('52', '33', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('59', '34', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('60', '30', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('64', '27', '2', '0.80', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('65', '27', '3', '0.75', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('66', '28', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('67', '29', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('68', '26', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('83', '36', '0', '0.00', '0.00');
INSERT INTO `pms_product_ladder` VALUES ('84', '35', '0', '0.00', '0.00');


CREATE TABLE `pms_product_full_reduction` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `full_price` decimal(10,2) DEFAULT NULL,
  `reduce_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COMMENT='产品满减表(只针对同商品)';

INSERT INTO `pms_product_full_reduction` VALUES ('1', '7', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('2', '8', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('3', '9', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('4', '10', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('5', '11', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('6', '12', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('7', '13', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('8', '14', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('9', '18', '100.00', '20.00');
INSERT INTO `pms_product_full_reduction` VALUES ('10', '7', '200.00', '50.00');
INSERT INTO `pms_product_full_reduction` VALUES ('11', '7', '300.00', '100.00');
INSERT INTO `pms_product_full_reduction` VALUES ('14', '22', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('16', '24', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('34', '23', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('44', '31', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('46', '32', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('48', '33', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('55', '34', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('56', '30', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('59', '27', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('60', '28', '500.00', '50.00');
INSERT INTO `pms_product_full_reduction` VALUES ('61', '28', '1000.00', '120.00');
INSERT INTO `pms_product_full_reduction` VALUES ('62', '29', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('63', '26', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('78', '36', '0.00', '0.00');
INSERT INTO `pms_product_full_reduction` VALUES ('79', '35', '0.00', '0.00');


CREATE TABLE `pms_sku_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `sku_code` varchar(64) NOT NULL COMMENT 'sku编码',
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `low_stock` int(11) DEFAULT NULL COMMENT '预警库存',
  `pic` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `sale` int(11) DEFAULT NULL COMMENT '销量',
  `promotion_price` decimal(10,2) DEFAULT NULL COMMENT '单品促销价格',
  `lock_stock` int(11) DEFAULT '0' COMMENT '锁定库存',
  `sp_data` varchar(500) DEFAULT NULL COMMENT '商品销售属性，json格式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COMMENT='sku的库存';


INSERT INTO `pms_sku_stock` VALUES ('98', '27', '201808270027001', '2699.00', '97', null, null, null, null, '-24', '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('99', '27', '201808270027002', '2999.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('100', '27', '201808270027003', '2699.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('101', '27', '201808270027004', '2999.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('102', '28', '201808270028001', '649.00', '99', null, null, null, null, '-8', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('103', '28', '201808270028002', '699.00', '99', null, null, null, null, '-8', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('104', '28', '201808270028003', '649.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('105', '28', '201808270028004', '699.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('106', '29', '201808270029001', '5499.00', '99', null, null, null, null, '-8', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('107', '29', '201808270029002', '6299.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('108', '29', '201808270029003', '5499.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('109', '29', '201808270029004', '6299.00', '100', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('110', '26', '201806070026001', '3788.00', '499', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('111', '26', '201806070026002', '3999.00', '500', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('112', '26', '201806070026003', '3788.00', '500', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('113', '26', '201806070026004', '3999.00', '500', null, null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `pms_sku_stock` VALUES ('163', '36', '202002210036001', '100.00', '100', '25', null, null, null, '9', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('164', '36', '202002210036002', '120.00', '98', '20', null, null, null, '6', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('165', '36', '202002210036003', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('166', '36', '202002210036004', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('167', '36', '202002210036005', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('168', '36', '202002210036006', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('169', '36', '202002210036007', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('170', '36', '202002210036008', '100.00', '100', '20', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('171', '35', '202002250035001', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('172', '35', '202002250035002', '240.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('173', '35', '202002250035003', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('174', '35', '202002250035004', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('175', '35', '202002250035005', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('176', '35', '202002250035006', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('177', '35', '202002250035007', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `pms_sku_stock` VALUES ('178', '35', '202002250035008', '200.00', '100', '50', null, null, null, '0', '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');



CREATE TABLE `pms_product_attribute_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `product_attribute_id` bigint(20) DEFAULT NULL,
  `value` varchar(64) DEFAULT NULL COMMENT '手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8mb4 COMMENT='存储产品参数信息的表';

INSERT INTO `pms_product_attribute_value` VALUES ('1', '9', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('2', '10', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('3', '11', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('4', '12', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('5', '13', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('6', '14', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('7', '18', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('8', '7', '1', 'X');
INSERT INTO `pms_product_attribute_value` VALUES ('9', '7', '1', 'XL');
INSERT INTO `pms_product_attribute_value` VALUES ('10', '7', '1', 'XXL');
INSERT INTO `pms_product_attribute_value` VALUES ('11', '22', '7', 'x,xx');
INSERT INTO `pms_product_attribute_value` VALUES ('12', '22', '24', 'no110');
INSERT INTO `pms_product_attribute_value` VALUES ('13', '22', '25', '春季');
INSERT INTO `pms_product_attribute_value` VALUES ('14', '22', '37', '青年');
INSERT INTO `pms_product_attribute_value` VALUES ('15', '22', '38', '2018年春');
INSERT INTO `pms_product_attribute_value` VALUES ('16', '22', '39', '长袖');
INSERT INTO `pms_product_attribute_value` VALUES ('124', '23', '7', '米白色,浅黄色');
INSERT INTO `pms_product_attribute_value` VALUES ('125', '23', '24', 'no1098');
INSERT INTO `pms_product_attribute_value` VALUES ('126', '23', '25', '春季');
INSERT INTO `pms_product_attribute_value` VALUES ('127', '23', '37', '青年');
INSERT INTO `pms_product_attribute_value` VALUES ('128', '23', '38', '2018年春');
INSERT INTO `pms_product_attribute_value` VALUES ('129', '23', '39', '长袖');
INSERT INTO `pms_product_attribute_value` VALUES ('130', '1', '13', null);
INSERT INTO `pms_product_attribute_value` VALUES ('131', '1', '14', null);
INSERT INTO `pms_product_attribute_value` VALUES ('132', '1', '15', null);
INSERT INTO `pms_product_attribute_value` VALUES ('133', '1', '16', null);
INSERT INTO `pms_product_attribute_value` VALUES ('134', '1', '17', null);
INSERT INTO `pms_product_attribute_value` VALUES ('135', '1', '18', null);
INSERT INTO `pms_product_attribute_value` VALUES ('136', '1', '19', null);
INSERT INTO `pms_product_attribute_value` VALUES ('137', '1', '20', null);
INSERT INTO `pms_product_attribute_value` VALUES ('138', '1', '21', null);
INSERT INTO `pms_product_attribute_value` VALUES ('139', '2', '13', null);
INSERT INTO `pms_product_attribute_value` VALUES ('140', '2', '14', null);
INSERT INTO `pms_product_attribute_value` VALUES ('141', '2', '15', null);
INSERT INTO `pms_product_attribute_value` VALUES ('142', '2', '16', null);
INSERT INTO `pms_product_attribute_value` VALUES ('143', '2', '17', null);
INSERT INTO `pms_product_attribute_value` VALUES ('144', '2', '18', null);
INSERT INTO `pms_product_attribute_value` VALUES ('145', '2', '19', null);
INSERT INTO `pms_product_attribute_value` VALUES ('146', '2', '20', null);
INSERT INTO `pms_product_attribute_value` VALUES ('147', '2', '21', null);
INSERT INTO `pms_product_attribute_value` VALUES ('183', '31', '24', null);
INSERT INTO `pms_product_attribute_value` VALUES ('184', '31', '25', '夏季');
INSERT INTO `pms_product_attribute_value` VALUES ('185', '31', '37', '青年');
INSERT INTO `pms_product_attribute_value` VALUES ('186', '31', '38', '2018年夏');
INSERT INTO `pms_product_attribute_value` VALUES ('187', '31', '39', '短袖');
INSERT INTO `pms_product_attribute_value` VALUES ('198', '30', '24', null);
INSERT INTO `pms_product_attribute_value` VALUES ('199', '30', '25', '夏季');
INSERT INTO `pms_product_attribute_value` VALUES ('200', '30', '37', '青年');
INSERT INTO `pms_product_attribute_value` VALUES ('201', '30', '38', '2018年夏');
INSERT INTO `pms_product_attribute_value` VALUES ('202', '30', '39', '短袖');
INSERT INTO `pms_product_attribute_value` VALUES ('213', '27', '43', '黑色,蓝色');
INSERT INTO `pms_product_attribute_value` VALUES ('214', '27', '45', '5.8');
INSERT INTO `pms_product_attribute_value` VALUES ('215', '27', '46', '4G');
INSERT INTO `pms_product_attribute_value` VALUES ('216', '27', '47', 'Android');
INSERT INTO `pms_product_attribute_value` VALUES ('217', '27', '48', '3000ml');
INSERT INTO `pms_product_attribute_value` VALUES ('218', '28', '43', '金色,银色');
INSERT INTO `pms_product_attribute_value` VALUES ('219', '28', '45', '5.0');
INSERT INTO `pms_product_attribute_value` VALUES ('220', '28', '46', '4G');
INSERT INTO `pms_product_attribute_value` VALUES ('221', '28', '47', 'Android');
INSERT INTO `pms_product_attribute_value` VALUES ('222', '28', '48', '2800ml');
INSERT INTO `pms_product_attribute_value` VALUES ('223', '29', '43', '金色,银色');
INSERT INTO `pms_product_attribute_value` VALUES ('224', '29', '45', '4.7');
INSERT INTO `pms_product_attribute_value` VALUES ('225', '29', '46', '4G');
INSERT INTO `pms_product_attribute_value` VALUES ('226', '29', '47', 'IOS');
INSERT INTO `pms_product_attribute_value` VALUES ('227', '29', '48', '1960ml');
INSERT INTO `pms_product_attribute_value` VALUES ('228', '26', '43', '金色,银色');
INSERT INTO `pms_product_attribute_value` VALUES ('229', '26', '45', '5.0');
INSERT INTO `pms_product_attribute_value` VALUES ('230', '26', '46', '4G');
INSERT INTO `pms_product_attribute_value` VALUES ('231', '26', '47', 'Android');
INSERT INTO `pms_product_attribute_value` VALUES ('232', '26', '48', '3000');


CREATE TABLE `cms_subject_product_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COMMENT='专题商品关系表';

INSERT INTO `cms_subject_product_relation` VALUES ('1', '1', '12');
INSERT INTO `cms_subject_product_relation` VALUES ('2', '1', '13');
INSERT INTO `cms_subject_product_relation` VALUES ('3', '1', '14');
INSERT INTO `cms_subject_product_relation` VALUES ('4', '1', '18');
INSERT INTO `cms_subject_product_relation` VALUES ('5', '1', '7');
INSERT INTO `cms_subject_product_relation` VALUES ('6', '2', '7');
INSERT INTO `cms_subject_product_relation` VALUES ('7', '1', '22');
INSERT INTO `cms_subject_product_relation` VALUES ('29', '1', '23');
INSERT INTO `cms_subject_product_relation` VALUES ('30', '4', '23');
INSERT INTO `cms_subject_product_relation` VALUES ('31', '5', '23');
INSERT INTO `cms_subject_product_relation` VALUES ('41', '2', '26');
INSERT INTO `cms_subject_product_relation` VALUES ('42', '3', '26');
INSERT INTO `cms_subject_product_relation` VALUES ('43', '6', '26');


CREATE TABLE `sms_home_recommend_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_id` bigint(20) DEFAULT NULL,
  `subject_name` varchar(64) DEFAULT NULL,
  `recommend_status` int(1) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='首页推荐专题表';

INSERT INTO `sms_home_recommend_subject` VALUES ('14', '1', 'polo衬衫的也时尚', '1', '0');
INSERT INTO `sms_home_recommend_subject` VALUES ('15', '2', '大牌手机低价秒', '1', '0');
INSERT INTO `sms_home_recommend_subject` VALUES ('16', '3', '晓龙845新品上市', '1', '0');
INSERT INTO `sms_home_recommend_subject` VALUES ('17', '4', '夏天应该穿什么', '1', '0');
INSERT INTO `sms_home_recommend_subject` VALUES ('18', '5', '夏季精选', '1', '100');
INSERT INTO `sms_home_recommend_subject` VALUES ('19', '6', '品牌手机降价', '1', '0');