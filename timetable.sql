/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MariaDB
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : timetable

 Target Server Type    : MariaDB
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 03/09/2020 20:58:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教室位置',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `classroom_location_unique`(`location`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名字',
  `teacher` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '老师名字',
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程id',
  `week_period` tinyint(4) NOT NULL COMMENT '周学时',
  `period` tinyint(4) NOT NULL COMMENT '学时',
  `score` float(3, 0) NOT NULL COMMENT '学分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_time
-- ----------------------------
DROP TABLE IF EXISTS `course_time`;
CREATE TABLE `course_time`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course` int(11) NOT NULL COMMENT '课程id',
  `class_room` int(11) NOT NULL COMMENT '教室id',
  `day_of_week` tinyint(1) NOT NULL COMMENT '一周的第几天',
  `week` tinyint(1) NOT NULL COMMENT '第几周',
  `start_time` tinyint(1) NOT NULL COMMENT '开始时间段',
  `length` tinyint(1) NOT NULL COMMENT '持续时间段',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coursetime_course`(`course`) USING BTREE,
  INDEX `coursetime_day_of_week`(`day_of_week`) USING BTREE,
  INDEX `coursetime_week`(`week`) USING BTREE,
  INDEX `coursetime_start_time`(`start_time`) USING BTREE,
  INDEX `fk_coursetime_class_room_id`(`class_room`) USING BTREE,
  CONSTRAINT `fk_coursetime_class_room_id` FOREIGN KEY (`class_room`) REFERENCES `classroom` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_coursetime_course_id` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 373 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for other_course
-- ----------------------------
DROP TABLE IF EXISTS `other_course`;
CREATE TABLE `other_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名字',
  `score` float(3, 0) NULL DEFAULT NULL COMMENT '学分',
  `week` tinyint(4) NULL DEFAULT NULL COMMENT '第几周',
  `teacher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '老师',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `week`(`week`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NULL DEFAULT NULL,
  `extra` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'json存储的用户额外信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_profile_user`(`user`) USING BTREE,
  CONSTRAINT `fk_profile_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` bigint(20) NOT NULL,
  `name` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `last_active` datetime(0) NULL DEFAULT NULL,
  `join_time` datetime(0) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `bot` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_account_unique`(`account`) USING BTREE,
  UNIQUE INDEX `user_student_id_unique`(`student_id`) USING BTREE,
  INDEX `enable`(`enable`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_course
-- ----------------------------
DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_usercourse_user_id`(`user`) USING BTREE,
  INDEX `fk_usercourse_course_id`(`course`) USING BTREE,
  CONSTRAINT `fk_usercourse_course_id` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_usercourse_user_id` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_other_course
-- ----------------------------
DROP TABLE IF EXISTS `user_other_course`;
CREATE TABLE `user_other_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `other_course` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_course`(`user`) USING BTREE,
  INDEX `fk_other_course`(`other_course`) USING BTREE,
  CONSTRAINT `fk_other_course` FOREIGN KEY (`other_course`) REFERENCES `other_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_course` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
