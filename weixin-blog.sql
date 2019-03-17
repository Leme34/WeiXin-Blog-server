/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 111.230.68.228:3306
 Source Schema         : weixin-blog

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 17/03/2019 19:25:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `category_id` bigint(20) NULL DEFAULT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mark_size` bigint(20) NOT NULL DEFAULT 0,
  `vote_size` bigint(20) NOT NULL DEFAULT 0,
  `comment_size` bigint(20) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `image_list` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_blog_user`(`user_id`) USING BTREE,
  INDEX `fk_blog_category`(`category_id`) USING BTREE,
  CONSTRAINT `fk_blog_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_blog_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 2, 1, '初见💘 ', '', '2019/3/9风里雨里只为见你☔️ ', NULL, 2, 2, 6, '2019-03-16 22:10:26', NULL, '[{\"id\":\"5c8d037dc6544543cf5d6f06\",\"url\":\"http://www.thedark.cn:8081/view/5c8d037dc6544543cf5d6f06\"},{\"id\":\"5c8d038ec6544543cf5d6f07\",\"url\":\"http://www.thedark.cn:8081/view/5c8d038ec6544543cf5d6f07\"},{\"id\":\"5c8d039bc6544543cf5d6f08\",\"url\":\"http://www.thedark.cn:8081/view/5c8d039bc6544543cf5d6f08\"},{\"id\":\"5c8d03aac6544543cf5d6f09\",\"url\":\"http://www.thedark.cn:8081/view/5c8d03aac6544543cf5d6f09\"}]');
INSERT INTO `blog` VALUES (2, 2, 2, '春风十里不如你', '', '我喜欢春天的樱花，夏天的矢车菊，秋天的百里香，冬天的腊梅，还有每天的你💕 ', NULL, 1, 1, 0, '2019-03-17 18:04:33', NULL, '[{\"id\":\"5c8e1af6c654453a96105398\",\"url\":\"http://www.thedark.cn:8081/view/5c8e1af6c654453a96105398\"},{\"id\":\"5c8e1b1ac654453a96105399\",\"url\":\"http://www.thedark.cn:8081/view/5c8e1b1ac654453a96105399\"},{\"id\":\"5c8e1b26c654453a9610539a\",\"url\":\"http://www.thedark.cn:8081/view/5c8e1b26c654453a9610539a\"}]');

-- ----------------------------
-- Table structure for blog_mark
-- ----------------------------
DROP TABLE IF EXISTS `blog_mark`;
CREATE TABLE `blog_mark`  (
  `blog_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`blog_id`, `user_id`) USING BTREE,
  INDEX `fk_blog_mark_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_blog_mark_blog` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_blog_mark_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_mark
-- ----------------------------
INSERT INTO `blog_mark` VALUES (1, 1, '2019-03-16 22:35:17');
INSERT INTO `blog_mark` VALUES (1, 2, '2019-03-16 23:09:22');
INSERT INTO `blog_mark` VALUES (2, 2, '2019-03-17 18:06:33');

-- ----------------------------
-- Table structure for blog_vote
-- ----------------------------
DROP TABLE IF EXISTS `blog_vote`;
CREATE TABLE `blog_vote`  (
  `blog_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`blog_id`, `user_id`) USING BTREE,
  INDEX `fk_blog_vote_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_blog_vote_blog` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_blog_vote_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_vote
-- ----------------------------
INSERT INTO `blog_vote` VALUES (1, 1);
INSERT INTO `blog_vote` VALUES (1, 2);
INSERT INTO `blog_vote` VALUES (2, 2);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_category_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_category_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 1, '');
INSERT INTO `category` VALUES (2, 2, '');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `reply_user_id` bigint(20) UNSIGNED NULL DEFAULT 0,
  `blog_id` bigint(20) NULL DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pid`(`pid`) USING BTREE,
  INDEX `fk_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_reply_user_id`(`reply_user_id`) USING BTREE,
  INDEX `fk_comment_blog_id`(`blog_id`) USING BTREE,
  CONSTRAINT `fk_comment_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_pid` FOREIGN KEY (`pid`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (8, NULL, 2, NULL, 1, '我家小仙女最可爱～😘 ', '2019-03-16 23:44:50');
INSERT INTO `comment` VALUES (9, 8, 1, 0, 1, '还敲漂亮的💕 ', '2019-03-16 23:46:11');

-- ----------------------------
-- Table structure for comment_vote
-- ----------------------------
DROP TABLE IF EXISTS `comment_vote`;
CREATE TABLE `comment_vote`  (
  `user_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `comment_id`) USING BTREE,
  INDEX `fk_comment_vote_comment`(`comment_id`) USING BTREE,
  CONSTRAINT `fk_comment_vote_comment` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_vote_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_vote
-- ----------------------------
INSERT INTO `comment_vote` VALUES (1, 8);
INSERT INTO `comment_vote` VALUES (2, 8);
INSERT INTO `comment_vote` VALUES (1, 9);
INSERT INTO `comment_vote` VALUES (2, 9);

-- ----------------------------
-- Table structure for follower
-- ----------------------------
DROP TABLE IF EXISTS `follower`;
CREATE TABLE `follower`  (
  `user_id` bigint(20) NOT NULL,
  `follower_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `follower_id`) USING BTREE,
  CONSTRAINT `fk_follower_follower` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_follower_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follower
-- ----------------------------
INSERT INTO `follower` VALUES (1, 2);
INSERT INTO `follower` VALUES (2, 1);

-- ----------------------------
-- Table structure for notify
-- ----------------------------
DROP TABLE IF EXISTS `notify`;
CREATE TABLE `notify`  (
  `user1` bigint(20) NULL DEFAULT NULL,
  `user2` bigint(20) NULL DEFAULT NULL,
  `operation_id` int(11) NULL DEFAULT NULL,
  `is_new` tinyint(1) NULL DEFAULT NULL,
  `blog_id` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notify
-- ----------------------------
INSERT INTO `notify` VALUES (2, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` tinyint(1) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'USER');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_role` tinyint(1) NULL DEFAULT NULL,
  `bg_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role`(`user_role`) USING BTREE,
  CONSTRAINT `fk_role` FOREIGN KEY (`user_role`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '$2a$10$64vNX4AZWTC1gTi6IYWuZ.giN2TOee9knxbfHwTC5rKYU.VKhms5W', '12345678@qq.com', 'http://www.thedark.cn:8081/view/5c8cd6e6c6544543cf5d6efb', 1, 'http://www.thedark.cn:8081/view/5c8dd987c654453a96105395');
INSERT INTO `user` VALUES (2, 'synda', '$2a$10$AfMW5cPj7/oBJO80/oBpS.u2EZ2W6jcTWZWrvAaAnEP/WOjYnouDu', '255568684@qq.com', 'http://www.thedark.cn:8081/view/5c8cefdfc6544543cf5d6efc', 1, 'http://www.thedark.cn:8081/view/5c8e1c5fc654453a9610539b');

SET FOREIGN_KEY_CHECKS = 1;
