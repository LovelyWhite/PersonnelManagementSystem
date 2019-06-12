/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : pmsystem

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-06-12 19:28:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(8) NOT NULL,
  `account` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` enum('普通','管理员','超级管理员') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '123', '1234', '普通');

-- ----------------------------
-- Table structure for people
-- ----------------------------
DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `sex` enum('男','女') DEFAULT NULL,
  `age` int(8) DEFAULT NULL,
  `title` enum('行政人员','教师','一般员工','退休人员','返聘人员','临时工') DEFAULT NULL,
  `politicalstatus` enum('中共党员','中共预备党员','共青团员','民革党员','民盟盟员','民建会员','民进会员','农工党党员','致公党党员','九三学社社员','台盟盟员','无党派人士','群众') DEFAULT NULL,
  `highestdegree` enum('初中','中专','高中','大专','本科','硕士','博士') DEFAULT NULL,
  `termtime` timestamp NULL DEFAULT NULL,
  `arrivetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3;

-- ----------------------------
-- Records of people
-- ----------------------------
INSERT INTO `people` VALUES ('1', '1', '男', '1', '教师', '中共预备党员', '高中', '2019-06-04 19:04:04', '2019-06-04 19:04:06');
INSERT INTO `people` VALUES ('2', '123', '男', '1', '教师', '中共预备党员', '大专', '2019-06-18 19:17:45', '2019-06-28 19:17:48');
