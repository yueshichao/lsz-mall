CREATE TABLE `cms_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `pic` varchar(500) DEFAULT NULL COMMENT '专题主图',
  `product_count` int(11) DEFAULT NULL COMMENT '关联产品数量',
  `recommend_status` int(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `collect_count` int(11) DEFAULT NULL,
  `read_count` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `album_pics` varchar(1000) DEFAULT NULL COMMENT '画册图片用逗号分割',
  `description` varchar(1000) DEFAULT NULL,
  `show_status` int(1) DEFAULT NULL COMMENT '显示状态：0->不显示；1->显示',
  `content` text,
  `forward_count` int(11) DEFAULT NULL COMMENT '转发数',
  `category_name` varchar(200) DEFAULT NULL COMMENT '专题分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='专题表';

INSERT INTO `cms_subject` VALUES ('1', '1', 'polo衬衫的也时尚', null, null, null, '2018-11-11 13:26:55', null, null, null, null, null, null, null, null, '服装专题');
INSERT INTO `cms_subject` VALUES ('2', '2', '大牌手机低价秒', null, null, null, '2018-11-12 13:27:00', null, null, null, null, null, null, null, null, '手机专题');
INSERT INTO `cms_subject` VALUES ('3', '2', '晓龙845新品上市', null, null, null, '2018-11-13 13:27:05', null, null, null, null, null, null, null, null, '手机专题');
INSERT INTO `cms_subject` VALUES ('4', '1', '夏天应该穿什么', null, null, null, '2018-11-01 13:27:09', null, null, null, null, null, null, null, null, '服装专题');
INSERT INTO `cms_subject` VALUES ('5', '1', '夏季精选', null, null, null, '2018-11-06 13:27:18', null, null, null, null, null, null, null, null, '服装专题');
INSERT INTO `cms_subject` VALUES ('6', '2', '品牌手机降价', null, null, null, '2018-11-07 13:27:21', null, null, null, null, null, null, null, null, '手机专题');



CREATE TABLE `cms_prefrence_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `pic` varbinary(500) DEFAULT NULL COMMENT '展示图片',
  `sort` int(11) DEFAULT NULL,
  `show_status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='优选专区';

INSERT INTO `cms_prefrence_area` VALUES ('1', '让音质更出众', '音质不打折 完美现场感', null, null, '1');
INSERT INTO `cms_prefrence_area` VALUES ('2', '让音质更出众22', '让音质更出众22', null, null, null);
INSERT INTO `cms_prefrence_area` VALUES ('3', '让音质更出众33', null, null, null, null);
INSERT INTO `cms_prefrence_area` VALUES ('4', '让音质更出众44', null, null, null, null);