
-- CREATE DATABASE IF NOT EXISTS lsz_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `pms_product_attribute_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `attribute_count` int(11) DEFAULT '0' COMMENT '属性数量',
  `param_count` int(11) DEFAULT '0' COMMENT '参数数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='产品属性分类表';

INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (1, '服装-T恤', 2, 5);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (2, '服装-裤装', 2, 4);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (3, '手机数码-手机通讯', 2, 4);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (4, '配件', 0, 0);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (5, '居家', 0, 0);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (6, '洗护', 0, 0);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (10, '测试分类', 0, 0);
INSERT INTO `pms_product_attribute_category`(`id`, `name`, `attribute_count`, `param_count`) VALUES (11, '服装-鞋帽', 3, 0);

