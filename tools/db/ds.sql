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
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码' ,
`seq_no`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号' ,
`prize_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中奖期号' ,
`start_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开始期号' ,
`end_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结束期号' ,
`status`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态:1.等待开奖 2.未中奖 3.已中奖' ,
`bet_type`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '投注类型:3.三星后3' ,
`bet_no`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '投注号码' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='投注记录表'
AUTO_INCREMENT=35

;


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
  `status` varchar(50) COMMENT '状态:1.等待开奖 2.未中奖 3.已中奖',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='网站投注记录表'
AUTO_INCREMENT=25

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
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`lottery_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码' ,
`no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期号' ,
`time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间' ,
`num1`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一位 个位' ,
`num2`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第二位 十位' ,
`num3`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三位 百位' ,
`num4`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第四位 千位' ,
`num5`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第五位 万位' ,
`num6`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第六位' ,
`num7`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第七位' ,
`num8`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第八位' ,
`alias_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期号别名' ,
`lottery_date`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开奖日期' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `no` (`no`) USING BTREE ,
UNIQUE INDEX `alias_no` (`alias_no`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='开奖详情表'
AUTO_INCREMENT=42614

;

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

-- ----------------------------
-- Records of t_stock_realtime_level
-- ----------------------------
BEGIN;
COMMIT;

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
ALTER TABLE `t_lottery_detail` AUTO_INCREMENT=42614;

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
