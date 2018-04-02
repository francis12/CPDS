/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-10-17 13:48:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_bet`
-- ----------------------------
DROP TABLE IF EXISTS `t_bet`;
CREATE TABLE `t_bet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `seq_no` varchar(512) DEFAULT NULL COMMENT '流水号',
  `gen_id` varchar(50) DEFAULT NULL,
  `prize_no` varchar(50) DEFAULT NULL COMMENT '中奖期号',
  `start_no` varchar(50) DEFAULT NULL COMMENT '开始期号',
  `end_no` varchar(50) DEFAULT NULL COMMENT '结束期号',
  `status` varchar(50) DEFAULT NULL COMMENT '状态:1.等待开奖 2.未中奖 3.已中奖',
  `bet_type` varchar(50) DEFAULT NULL COMMENT '投注类型:3.三星后3',
  `bet_no` mediumtext NOT NULL COMMENT '投注号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_start_no` (`start_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='投注记录表';




-- ----------------------------
-- Table structure for `t_bet_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_bet_record`;
CREATE TABLE `t_bet_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bet_website` varchar(10) NOT NULL COMMENT '投注网站',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `schedule_no` varchar(6) NOT NULL COMMENT '倍投顺序',
  `seq_no` varchar(512) NOT NULL COMMENT '流水号',
  `bet_no` varchar(50) DEFAULT NULL COMMENT '投注期号',
  `status` varchar(50) DEFAULT NULL COMMENT '状态:1.等待开奖 2.未中奖 3.已中奖',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='网站投注记录表';

;

-- ----------------------------
-- Records of t_bet_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `t_case`
-- ----------------------------
DROP TABLE IF EXISTS `t_case`;
CREATE TABLE `t_case` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码' ,
`seqno`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号' ,
`start_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开始期号' ,
`end_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结束期号' ,
`amt`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '本金' ,
`result`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回测结果' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='回测表'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of t_case
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `t_case_result`
-- ----------------------------
DROP TABLE IF EXISTS `t_case_result`;
CREATE TABLE `t_case_result` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码' ,
`seqno`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号' ,
`start_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开始期号' ,
`end_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结束期号' ,
`result`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区间回测结果' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='区间回测结果表'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of t_case_result
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `t_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery`;
CREATE TABLE `t_lottery` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码' ,
`lottery_name`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '创建者' ,
`modifier`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '修改者' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `lottery_code` (`lottery_code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='彩票实体表'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of t_lottery
-- ----------------------------
BEGIN;
INSERT INTO `t_lottery` VALUES ('2', 'RDSSC', '瑞典时时彩', 'admin', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for `t_lottery_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_detail`;
CREATE TABLE `t_lottery_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `no` varchar(50) NOT NULL COMMENT '期号',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `num1` varchar(1) DEFAULT NULL COMMENT '第一位 个位',
  `num2` varchar(1) DEFAULT NULL COMMENT '第二位 十位',
  `num3` varchar(1) DEFAULT NULL COMMENT '第三位 百位',
  `num4` varchar(1) DEFAULT NULL COMMENT '第四位 千位',
  `num5` varchar(1) DEFAULT NULL COMMENT '第五位 万位',
  `num6` varchar(1) DEFAULT NULL COMMENT '第六位',
  `num7` varchar(1) DEFAULT NULL COMMENT '第七位',
  `num8` varchar(1) DEFAULT NULL COMMENT '第八位',
  `alias_no` varchar(50) NOT NULL COMMENT '期号别名',
  `lottery_date` varchar(8) DEFAULT NULL COMMENT '开奖日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `no` (`no`) USING BTREE,
  UNIQUE KEY `alias_no` (`alias_no`)
) ENGINE=InnoDB AUTO_INCREMENT=178222 DEFAULT CHARSET=utf8 COMMENT='开奖详情表';



-- ----------------------------
-- Table structure for `t_stock`
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`stock_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票代码' ,
`stock_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票名称' ,
`market`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证券市场' ,
`is_deleted`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '是否删除，N否，Y是' ,
`gmt_created`  datetime NOT NULL COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '更新时间' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '创建者' ,
`modifier`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '修改者' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `stock_code` (`stock_code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='股票实体表'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of t_stock
-- ----------------------------
BEGIN;
INSERT INTO `t_stock` VALUES ('1', '002751', '易尚展示', 'sz', 'N', '2017-06-16 17:09:28', '2017-06-16 17:09:31', 'system', 'system'), ('2', '002695', '煌上煌', 'sz', 'N', '2017-06-20 13:50:33', '2017-06-20 13:50:36', 'system', 'system');
COMMIT;

-- ----------------------------
-- Table structure for `t_stock_date`
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_date`;
CREATE TABLE `t_stock_date` (
`stock_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票代码' ,
`trade_date`  date NULL DEFAULT NULL COMMENT '交易日格式：yyyyMMDD' ,
`open`  double NULL DEFAULT NULL COMMENT '开盘价' ,
`close`  double NULL DEFAULT NULL COMMENT '收盘价' ,
`high`  double NULL DEFAULT NULL COMMENT '最高价' ,
`low`  double NULL DEFAULT NULL COMMENT '最低价' ,
`limit_up_price`  double NULL DEFAULT NULL COMMENT '涨停价' ,
`limit_down_price`  double NULL DEFAULT NULL COMMENT '跌停价' ,
`deal_amount`  double NULL DEFAULT NULL COMMENT '成交数' ,
`deal_money`  double NULL DEFAULT NULL COMMENT '成交金额' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '创建者' ,
`modifier`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '修改者' ,
UNIQUE INDEX `stock_code|trade_date` (`stock_code`, `trade_date`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='股票每日表'

;

-- ----------------------------
-- Records of t_stock_date
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `t_stock_realtime`
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_realtime`;
CREATE TABLE `t_stock_realtime` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`stock_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票代码' ,
`stock_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行情时间' ,
`cur_price`  double NULL DEFAULT NULL COMMENT '成交价' ,
`cur_amount`  double NULL DEFAULT NULL COMMENT '成交数' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '创建者' ,
`modifier`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'system' COMMENT '修改者' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `stock_code` (`stock_code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='股票实时行情表'
AUTO_INCREMENT=1

;


-- ----------------------------
-- Table structure for `t_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '彩票代码' ,
`gen_id`  varchar(32)  COMMENT '彩票生成类型' ,
`no`  varchar(32)  COMMENT '当前期' ,
`win_no`  varchar(32)  COMMENT '中下一期' ,
`lose_no`  varchar(32)  COMMENT '挂下一期' ,
`multiple`  varchar(32)  COMMENT '倍数' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='倍投计划表'
AUTO_INCREMENT=1

;
-- ----------------------------
-- Records of t_stock_realtime
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `t_stock_realtime_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_realtime_level`;
CREATE TABLE `t_stock_realtime_level` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`stock_realtime_id`  bigint(20) NOT NULL COMMENT '股票实时行情ID' ,
`trade_type`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买入:B,卖出：S' ,
`price`  double NULL DEFAULT NULL COMMENT '价格' ,
`amout`  double NULL DEFAULT NULL COMMENT '数目' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `stock_realtime_id` (`stock_realtime_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='股票各档行情表'
AUTO_INCREMENT=1

;

CREATE TABLE `t_tecent_online` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `time` varchar(30) NOT NULL COMMENT '时间',
  `online_num` bigint(10) DEFAULT NULL COMMENT '在线数',
  `adjust_num` varchar(10) DEFAULT NULL COMMENT '波动值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=173806 DEFAULT CHARSET=utf8 COMMENT='tecent在线人数表';

CREATE TABLE `t_tecent_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `start_time` varchar(20) DEFAULT NULL COMMENT '生效时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '失效时间',
  `start` varchar(10) DEFAULT NULL COMMENT '调整开始值',
  `end` varchar(10) DEFAULT NULL COMMENT '调整结束值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173808 DEFAULT CHARSET=utf8 COMMENT='tecent在线人数表';

INSERT INTO `t_tecent_time` VALUES (173790, '18:00:00', '18:59:59', '-40000', '00000');
INSERT INTO `t_tecent_time` VALUES (173791, '19:00:00', '19:59:59', '10000', '50000');
INSERT INTO `t_tecent_time` VALUES (173792, '20:00:00', '20:59:59', '10000', '50000');
INSERT INTO `t_tecent_time` VALUES (173793, '21:00:00', '21:59:59', '-50000', '-10000');
INSERT INTO `t_tecent_time` VALUES (173794, '22:00:00', '22:59:59', '-180000', '-140000');
INSERT INTO `t_tecent_time` VALUES (173795, '23:00:00', '23:59:59', '-360000', '-320000');
INSERT INTO `t_tecent_time` VALUES (173796, '00:00:00', '00:59:59', '-320000', '-280000');
INSERT INTO `t_tecent_time` VALUES (173797, '02:00:00', '02:59:59', '-110000', '-70000');
INSERT INTO `t_tecent_time` VALUES (173798, '04:00:00', '04:59:59', '-50000', '-10000');
INSERT INTO `t_tecent_time` VALUES (173799, '05:00:00', '05:59:59', '00000', '40000');
INSERT INTO `t_tecent_time` VALUES (173800, '10:00:00', '10:59:59', '50000', '90000');
INSERT INTO `t_tecent_time` VALUES (173801, '11:00:00', '11:59:59', '70000', '110000');
INSERT INTO `t_tecent_time` VALUES (173802, '12:00:00', '12:59:59', '70000', '110000');
INSERT INTO `t_tecent_time` VALUES (173803, '13:00:00', '13:59:59', '-110000', '-70000');
INSERT INTO `t_tecent_time` VALUES (173804, '17:00:00', '17:59:59', '-60000', '-20000');
INSERT INTO `t_tecent_time` VALUES (173805, '15:00:00', '15:59:59', '10000', '50000');
INSERT INTO `t_tecent_time` VALUES (173806, '14:00:00', '15:59:59', '-60000', '-20000');
INSERT INTO `t_tecent_time` VALUES (173807, '16:00:00', '16:59:59', '20000', '60000');


-- ----------------------------
-- Records of t_stock_realtime_level
-- ----------------------------

CREATE TABLE `t_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `start_no` varchar(50) NOT NULL COMMENT '开始期号',
  `end_no` varchar(50) NOT NULL COMMENT '结束期号',
  `total_amt` DECIMAL(12,4) NOT NULL COMMENT '总投注金额',
  `cur_profit` DECIMAL(12,4) NOT NULL COMMENT '盈利金额',
  `max_profit` DECIMAL(12,4) NOT NULL COMMENT '盈利金额',
  `min_profit` DECIMAL(12,4) NOT NULL COMMENT '盈利金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='策略表';

CREATE TABLE `t_strategy_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sid` bigint(20) NOT NULL  COMMENT 't_strategy id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `no` varchar(50) NOT NULL COMMENT '期号',
  `amt` DECIMAL(12,4) NOT NULL COMMENT '投注金额',
  `cur_profit` DECIMAL(12,4) NOT NULL COMMENT '当前盈利金额',
  `status` varchar(2) NOT NULL COMMENT '中挂结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='策略详情表';
BEGIN;
COMMIT;
CREATE TABLE `t_tcffc_prize` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `no` varchar(50) NOT NULL COMMENT '期号',
  `prize` varchar(10) NOT NULL COMMENT '开奖号',
  `wan` varchar(1) DEFAULT NULL COMMENT '万位',
  `qian` varchar(1) DEFAULT NULL COMMENT '千位',
  `bai` varchar(1) DEFAULT NULL COMMENT '百位',
  `shi` varchar(1) DEFAULT NULL COMMENT '十位',
  `ge` varchar(1) DEFAULT NULL COMMENT '个位',
	`online_num` INT NOT NULL COMMENT '在线总数',
	`adjust_num` INT NOT NULL COMMENT '波动数',
  `alias_no` varchar(50) NOT NULL COMMENT '期号别名',
  `lottery_date` varchar(8) DEFAULT NULL COMMENT '开奖日期',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='腾讯分分开奖详情表';
CREATE TABLE `t_cur_no` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `cur_no` varchar(50) DEFAULT NULL COMMENT '当前期',
  `next_no` varchar(50) DEFAULT NULL COMMENT '下一期',
  `next_sec` varchar(50) DEFAULT NULL COMMENT '开奖间隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='开奖期号表';


CREATE TABLE `t_gen_prize` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lottery_code` varchar(10) NOT NULL COMMENT '代码',
  `no` varchar(50) DEFAULT NULL COMMENT '期号',
  `gen_prize` varchar(50) DEFAULT NULL COMMENT '预测号码',
  `real_prize` varchar(50) DEFAULT NULL COMMENT '实际号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='预测记录表';


-- ----------------------------
-- Auto increment value for `t_bet`
-- ----------------------------
ALTER TABLE `t_bet` AUTO_INCREMENT=35;

-- ----------------------------
-- Auto increment value for `t_bet_record`
-- ----------------------------
ALTER TABLE `t_bet_record` AUTO_INCREMENT=25;

-- ----------------------------
-- Auto increment value for `t_case`
-- ----------------------------
ALTER TABLE `t_case` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `t_case_result`
-- ----------------------------
ALTER TABLE `t_case_result` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `t_lottery`
-- ----------------------------
ALTER TABLE `t_lottery` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for `t_lottery_detail`
-- ----------------------------
ALTER TABLE `t_lottery_detail` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `t_stock`
-- ----------------------------
ALTER TABLE `t_stock` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for `t_stock_realtime`
-- ----------------------------
ALTER TABLE `t_stock_realtime` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `t_stock_realtime_level`
-- ----------------------------
ALTER TABLE `t_stock_realtime_level` AUTO_INCREMENT=1;
