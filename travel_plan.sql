/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : travel_plan

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 18/07/2023 18:02:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `thing` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `place` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `plan_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1681117240211472387 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (123123123, '123', '2023-07-05', '2023-07-15 21:12:40', 1, '2023-07-15 21:12:52', '123', NULL);
INSERT INTO `plan` VALUES (1680486602450046978, '吃饭', '2023-07-16', '2023-07-16 15:56:43', 1678255957854343169, '2023-07-16 15:56:43', '家', '晚上');
INSERT INTO `plan` VALUES (1680487118164889602, '534534', '2023-07-07', '2023-07-16 15:58:46', 1678255957854343169, '2023-07-16 15:58:46', '534543', '534534');
INSERT INTO `plan` VALUES (1680487276076240897, '543543', '2023-07-07', '2023-07-16 15:59:23', 1678255957854343169, '2023-07-16 15:59:23', '53543', '534534');
INSERT INTO `plan` VALUES (1680487307000844290, '1234', '2023-07-07', '2023-07-16 15:59:31', 1678255957854343169, '2023-07-16 15:59:31', '534', '5435');
INSERT INTO `plan` VALUES (1680487405898338306, '54543', '2023-07-14', '2023-07-16 15:59:54', 1678255957854343169, '2023-07-16 15:59:54', '312', '32');
INSERT INTO `plan` VALUES (1681117240211472386, '432423', '2023-07-05', '2023-07-18 09:42:38', 1678255957854343169, '2023-07-18 09:42:38', '53454353', '543543');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone_num` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `email` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`user_name` ASC) USING BTREE,
  UNIQUE INDEX `user_email_uindex`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '202cb962ac59075b964b07152d234b70', '13161288127', 1, '2023-07-10 10:42:49', '2023-07-10 10:42:49', '1521955177@qq.com');
INSERT INTO `user` VALUES (1678255957854343169, 'user', '123', '202cb962ac59075b964b07152d234b70', '123', 1, '2023-07-10 12:12:55', '2023-07-10 12:12:55', '123');
INSERT INTO `user` VALUES (1678355168570544130, 'user', '1', 'c4ca4238a0b923820dcc509a6f75849b', '1', 1, '2023-07-10 18:47:09', '2023-07-10 18:47:09', '1');
INSERT INTO `user` VALUES (1680461708546969602, 'user', '456', '202cb962ac59075b964b07152d234b70', '123', 1, '2023-07-16 14:17:47', '2023-07-16 14:17:47', '789');

SET FOREIGN_KEY_CHECKS = 1;
