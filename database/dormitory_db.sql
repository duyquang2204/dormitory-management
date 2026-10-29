-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: dormitory_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bao_cao_thong_ke`
--

DROP TABLE IF EXISTS `bao_cao_thong_ke`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bao_cao_thong_ke` (
  `ma_bao_cao` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam` int DEFAULT NULL,
  `ngay_lap` date DEFAULT NULL,
  `so_luongsv` int DEFAULT NULL,
  `so_luong_sua_chua` int DEFAULT NULL,
  `so_luong_vi_pham` int DEFAULT NULL,
  `thang` int DEFAULT NULL,
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_nguoi_lap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_bao_cao`),
  KEY `FK5o122wjrvswoei9p3qw76is1h` (`ma_khu`),
  KEY `FK1fhebxctbd6g649edtmhhofll` (`ma_nguoi_lap`),
  CONSTRAINT `FK1fhebxctbd6g649edtmhhofll` FOREIGN KEY (`ma_nguoi_lap`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FK5o122wjrvswoei9p3qw76is1h` FOREIGN KEY (`ma_khu`) REFERENCES `khu` (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bao_cao_thong_ke`
--

LOCK TABLES `bao_cao_thong_ke` WRITE;
/*!40000 ALTER TABLE `bao_cao_thong_ke` DISABLE KEYS */;
/*!40000 ALTER TABLE `bao_cao_thong_ke` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chuyen_phong`
--

DROP TABLE IF EXISTS `chuyen_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuyen_phong` (
  `ma_chuyen_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_xu_ly` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong_moi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_chuyen_phong`),
  KEY `FKam0325u1jndcmn5m5awsunfmj` (`ma_phan_phong`),
  KEY `FK6hji132neohk75kmmfrtopjog` (`ma_phong_moi`),
  CONSTRAINT `FK6hji132neohk75kmmfrtopjog` FOREIGN KEY (`ma_phong_moi`) REFERENCES `phong` (`ma_phong`),
  CONSTRAINT `FKam0325u1jndcmn5m5awsunfmj` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuyen_phong`
--

LOCK TABLES `chuyen_phong` WRITE;
/*!40000 ALTER TABLE `chuyen_phong` DISABLE KEYS */;
/*!40000 ALTER TABLE `chuyen_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dang_kyktx`
--

DROP TABLE IF EXISTS `dang_kyktx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dang_kyktx` (
  `ma_dang_ky` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_minh_chung_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_the_sinh_vien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dien_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ly_do_tu_choi` text COLLATE utf8mb4_unicode_ci,
  `nam_hoc` int DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `que_quan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `truong_dai_hoc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_dang_ky`),
  KEY `FK1ds80lcl00j9hfcehel7twl20` (`ma_loai_phong`),
  KEY `FKh7urc5psuhfnxfg0tb7aq77dn` (`masv`),
  CONSTRAINT `FK1ds80lcl00j9hfcehel7twl20` FOREIGN KEY (`ma_loai_phong`) REFERENCES `loai_phong` (`ma_loai_phong`),
  CONSTRAINT `FKh7urc5psuhfnxfg0tb7aq77dn` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dang_kyktx`
--

LOCK TABLES `dang_kyktx` WRITE;
/*!40000 ALTER TABLE `dang_kyktx` DISABLE KEYS */;
INSERT INTO `dang_kyktx` VALUES ('DK4018410','','2026-10-22','Đã duyệt','LP08','SV0001','https://res.cloudinary.com/svyjms27/image/upload/v1792683510/hsfvsfbel50xbvyduibh.jpg',NULL,'https://res.cloudinary.com/svyjms27/image/upload/v1792683508/yvzjenrnqtzpnyhvf3wk.jpg','01234567890','Không ưu tiên','duyquang22042005@gmail.com','Nam','Nguyễn Duy Quang','Công nghệ thông tin',NULL,1,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','ĐH Công nghệ Kỹ thuật '),('DK4367196','','2026-10-22','Đã duyệt','LP08','SV0002','https://res.cloudinary.com/svyjms27/image/upload/v1792683864/wyzioskoq16hmfz1vagm.jpg',NULL,'https://res.cloudinary.com/svyjms27/image/upload/v1792683861/fxapdsjjnnvfls88q50g.jpg','01234567891','Không ưu tiên','quangduy22042005@gmail.com','Nam','Nguyễn Quang Duy','Công nghệ thông tin',NULL,1,'2005-04-12','Kon Tum','0378869291','ĐH Công nghệ Kỹ thuật '),('DK6348559','Mong muốn ở cùng bạn MSSV SV0001','2026-10-18','Từ chối','LP08',NULL,'/uploads/cccd_6afc4.jpg','/uploads/uuyen_tin_2b954.jpg','/uploads/the_sv_ee9b7.jpg','01234567880','Khuyết tật','ndquang22042005@gmail.com','Nam','Nguyễn Văn A','Công nghệ thông tin','Bạn SV0001 đang ở phòng 4 người, xin vui lòng đăng ký lại',1,'2006-02-11','Phường Thủ Đức, TP.HCM','0223253535','ĐH Công nghệ Kỹ thuật '),('DK9184644','','2026-10-22','T? ch?i','LP04',NULL,'https://res.cloudinary.com/svyjms27/image/upload/v1792678678/mv9qpyfz9insdd4a2ymy.jpg',NULL,'https://res.cloudinary.com/svyjms27/image/upload/v1792678674/qr4wutobkugupdtcoy4n.jpg','01234567877','Không ưu tiên','ndquang22042005@gmail.com','Nam','Nguyễn Văn A','Công nghệ thông tin','Trùng s? CCCD v?i sinh viên SV0002',1,'2005-05-12','Phường Thủ Đức, TP.HCM','0378869291','ĐH Công nghệ Kỹ thuật ');
/*!40000 ALTER TABLE `dang_kyktx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gia_han_hop_dong`
--

DROP TABLE IF EXISTS `gia_han_hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gia_han_hop_dong` (
  `ma_gia_han` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_bat_dau_moi` date DEFAULT NULL,
  `ngay_ket_thuc_moi` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_hop_dong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_gia_han`),
  KEY `FKl5erf0oboku2ud4i48d0g9op3` (`ma_hop_dong`),
  CONSTRAINT `FKl5erf0oboku2ud4i48d0g9op3` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gia_han_hop_dong`
--

LOCK TABLES `gia_han_hop_dong` WRITE;
/*!40000 ALTER TABLE `gia_han_hop_dong` DISABLE KEYS */;
/*!40000 ALTER TABLE `gia_han_hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `ma_hoa_don` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chi_so_dien_cu` double DEFAULT NULL,
  `chi_so_dien_moi` double DEFAULT NULL,
  `chi_so_nuoc_cu` double DEFAULT NULL,
  `chi_so_nuoc_moi` double DEFAULT NULL,
  `minh_chung_thanh_toan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam` int DEFAULT NULL,
  `ngay_tao` date DEFAULT NULL,
  `thang` int DEFAULT NULL,
  `tien_phong` double DEFAULT NULL,
  `tong_tien` double DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phuong_thuc_thanh_toan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tien_dien` double DEFAULT NULL,
  `tien_nuoc` double DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `don_gia_dien` double DEFAULT NULL,
  `don_gia_nuoc` double DEFAULT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `han_thanh_toan` date DEFAULT NULL,
  `loai_hoa_don` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_hoa_don`),
  KEY `FK98kur1iyt6w09bate6c1596hk` (`ma_phong`),
  KEY `FK2gjro9egb6sfmfd2ww27tsodj` (`masv`),
  CONSTRAINT `FK2gjro9egb6sfmfd2ww27tsodj` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FK98kur1iyt6w09bate6c1596hk` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES ('HDDN1837696_SV0001',0,200,0,10,NULL,2026,'2026-10-24',1,0,425000,'Đã thanh toán','A301',NULL,350000,75000,'SV0001',3500,15000,'Hóa đơn dịch vụ điện nước tháng 1/2026','2026-01-25','DIEN_NUOC'),('HDDN1837714_SV0002',0,200,0,10,NULL,2026,'2026-10-24',1,0,425000,'Đã thanh toán','A301',NULL,350000,75000,'SV0002',3500,15000,'Hóa đơn dịch vụ điện nước tháng 1/2026','2026-01-25','DIEN_NUOC'),('HDP4083239',NULL,NULL,NULL,NULL,NULL,2026,'2026-10-22',10,9600000,9600000,'Đã thanh toán','A301',NULL,0,0,'SV0001',NULL,NULL,'Tiền thuê chỗ ở KTX 01 năm (12 tháng)','2026-11-06','TIEN_PHONG'),('HDP4416478',NULL,NULL,NULL,NULL,NULL,2026,'2026-10-22',10,9600000,9600000,'Đã thanh toán','A301',NULL,0,0,'SV0002',NULL,NULL,'Tiền thuê chỗ ở KTX 01 năm (12 tháng)','2026-11-06','TIEN_PHONG');
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hop_dong`
--

DROP TABLE IF EXISTS `hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hop_dong` (
  `ma_hop_dong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dieu_khoan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gia_tien` double DEFAULT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `ngay_lap` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_nhan_vien_lap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yeu_cau_cuasv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_hop_dong`),
  KEY `FKpoahr4jjngsi7puqo9s7xwonh` (`ma_nhan_vien_lap`),
  KEY `FK4u03eto7ofpcubcvhusixs6mg` (`ma_phan_phong`),
  CONSTRAINT `FK4u03eto7ofpcubcvhusixs6mg` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`),
  CONSTRAINT `FKpoahr4jjngsi7puqo9s7xwonh` FOREIGN KEY (`ma_nhan_vien_lap`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hop_dong`
--

LOCK TABLES `hop_dong` WRITE;
/*!40000 ALTER TABLE `hop_dong` DISABLE KEYS */;
INSERT INTO `hop_dong` VALUES ('HD4083227',NULL,9600000,'2026-10-22','2027-10-22','2026-10-22','Còn hạn',NULL,'548c12e9-03b2-4512-99bb-fad26dc8acbb',NULL),('HD4416474',NULL,9600000,'2026-10-22','2027-10-22','2026-10-22','Còn hạn',NULL,'041a78e6-8706-4cc3-973b-3e5698247d19',NULL);
/*!40000 ALTER TABLE `hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ket_qua_nhan_vien`
--

DROP TABLE IF EXISTS `ket_qua_nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ket_qua_nhan_vien` (
  `ma_phan_cong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `manvsc` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `FK2shfip59haqk2mtvwd7s0sn97` (`manvsc`),
  KEY `FKmw6fayooyw4578wgsp83sgxos` (`ma_phan_cong`),
  CONSTRAINT `FK2shfip59haqk2mtvwd7s0sn97` FOREIGN KEY (`manvsc`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FKmw6fayooyw4578wgsp83sgxos` FOREIGN KEY (`ma_phan_cong`) REFERENCES `ket_qua_sua_chua` (`ma_phan_cong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ket_qua_nhan_vien`
--

LOCK TABLES `ket_qua_nhan_vien` WRITE;
/*!40000 ALTER TABLE `ket_qua_nhan_vien` DISABLE KEYS */;
INSERT INTO `ket_qua_nhan_vien` VALUES ('a9606a42-b787-41c2-9ba6-76a71646135e','06664612-d0d7-4293-8e97-41f3d9bbba00');
/*!40000 ALTER TABLE `ket_qua_nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ket_qua_sua_chua`
--

DROP TABLE IF EXISTS `ket_qua_sua_chua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ket_qua_sua_chua` (
  `ma_phan_cong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ket_qua` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_hoan_thanh` date DEFAULT NULL,
  `ngay_tiep_nhan` date DEFAULT NULL,
  `noi_dung_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `manvsc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_yeu_cau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hinh_anh_minh_chung` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_phan_cong`),
  UNIQUE KEY `UKe4l4j1f0m62vptw8t6xyjjjcd` (`ma_yeu_cau`),
  KEY `FKo5iyy2n5799poesfbnw7py4v2` (`manvsc`),
  CONSTRAINT `FKo5iyy2n5799poesfbnw7py4v2` FOREIGN KEY (`manvsc`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FKopstulyki7xnfeapvi4k8d4su` FOREIGN KEY (`ma_yeu_cau`) REFERENCES `yeu_cau_sua_chua` (`ma_yeu_cau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ket_qua_sua_chua`
--

LOCK TABLES `ket_qua_sua_chua` WRITE;
/*!40000 ALTER TABLE `ket_qua_sua_chua` DISABLE KEYS */;
INSERT INTO `ket_qua_sua_chua` VALUES ('a9606a42-b787-41c2-9ba6-76a71646135e',NULL,'Đã sửa xong','2026-10-24','2026-10-24','Đã sửa đèn xong',NULL,'e526803d-1536-4f7b-9a88-c98f0427c424','https://res.cloudinary.com/svyjms27/image/upload/v1792841092/oktcxeuqagv4xdugmgog.jpg');
/*!40000 ALTER TABLE `ket_qua_sua_chua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khu`
--

DROP TABLE IF EXISTS `khu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khu` (
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mo_ta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khu`
--

LOCK TABLES `khu` WRITE;
/*!40000 ALTER TABLE `khu` DISABLE KEYS */;
INSERT INTO `khu` VALUES ('A','Khu A - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 8 người','Khu A'),('B','Khu B - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 6 người','Khu B'),('C','Khu C - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 4 người cao cấp','Khu C');
/*!40000 ALTER TABLE `khu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_phong`
--

DROP TABLE IF EXISTS `loai_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_phong` (
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `don_gia` double DEFAULT NULL,
  `mo_ta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `so_nguoi_toi_da` int DEFAULT NULL,
  `ten_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_loai_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_phong`
--

LOCK TABLES `loai_phong` WRITE;
/*!40000 ALTER TABLE `loai_phong` DISABLE KEYS */;
INSERT INTO `loai_phong` VALUES ('LP04',1500000,'Phòng 4 người cao cấp: Điều hòa Inverter 24/24, quạt trần, bình nóng lạnh, wifi riêng, nệm êm ái',4,'Phòng 4 người'),('LP06',1000000,'Phòng 6 người tiện nghi: Có quạt trần, bình nóng lạnh, wifi tốc độ cao, tủ cá nhân (không có máy điều hòa)',6,'Phòng 6 người'),('LP08',800000,'Phòng 8 người tiết kiệm: Có quạt trần công suất lớn, wifi tốc độ cao, tủ cá nhân (không điều hòa, không nóng lạnh)',8,'Phòng 8 người');
/*!40000 ALTER TABLE `loai_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoi_dung`
--

DROP TABLE IF EXISTS `nguoi_dung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoi_dung` (
  `ma_nguoi_dung` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chuc_vu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten_dang_nhap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_nguoi_dung`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoi_dung`
--

LOCK TABLES `nguoi_dung` WRITE;
/*!40000 ALTER TABLE `nguoi_dung` DISABLE KEYS */;
INSERT INTO `nguoi_dung` VALUES ('06664612-d0d7-4293-8e97-41f3d9bbba00','Nhân viên sửa chữa','Trần Thợ Sửa','$2a$10$CmwPf2VWczreQJ3d9QNUxu0k1F05966pkYStQRvtUwyCsjIqRuxVO','0378869290','nhanvien','Hoạt động'),('0797a054-73c4-45d8-8154-4e40181c0374','Quản sinh','Nguyễn Quản Sinh','$2a$10$3XVzN0/uP1yhEeyji9Pt0.Fq8v/ZNbt3ckrPShCAuy86zm.sTjvIq','0378869291','quansinh','Hoạt động'),('1ace699f-9928-46a2-8bdb-d92d6aa0e131','Quản trị viên','Quản trị viên hệ thống','$2a$10$NFUu.2WGKXuqfN9rulGfN.GFJRGOfE4Pw02scqWQylVDCjFjWbLgW','0378869292','admin','Hoạt động');
/*!40000 ALTER TABLE `nguoi_dung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_xac_thuc`
--

DROP TABLE IF EXISTS `otp_xac_thuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_xac_thuc` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `da_su_dung` bit(1) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `loai_otp` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `otp_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `thoi_gian_het_han` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_xac_thuc`
--

LOCK TABLES `otp_xac_thuc` WRITE;
/*!40000 ALTER TABLE `otp_xac_thuc` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp_xac_thuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phan_phong`
--

DROP TABLE IF EXISTS `phan_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phan_phong` (
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_dang_ky` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_phan_phong`),
  UNIQUE KEY `UKn28vrbmklsr25j64t2bijmqp` (`ma_dang_ky`),
  KEY `FKmun4hrn6h8i8bwchsfag6mo2j` (`ma_phong`),
  KEY `FK5nv6g25gr8oa6jt3sm4dqnq3v` (`masv`),
  CONSTRAINT `FK3eb5is5gvqjao8aosh7mydmjh` FOREIGN KEY (`ma_dang_ky`) REFERENCES `dang_kyktx` (`ma_dang_ky`),
  CONSTRAINT `FK5nv6g25gr8oa6jt3sm4dqnq3v` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FKmun4hrn6h8i8bwchsfag6mo2j` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phan_phong`
--

LOCK TABLES `phan_phong` WRITE;
/*!40000 ALTER TABLE `phan_phong` DISABLE KEYS */;
INSERT INTO `phan_phong` VALUES ('041a78e6-8706-4cc3-973b-3e5698247d19','2026-10-22','2027-10-22','Đang ở','DK4367196','A301','SV0002'),('548c12e9-03b2-4512-99bb-fad26dc8acbb','2026-10-22','2027-10-22','Đang ở','DK4018410','A301','SV0001');
/*!40000 ALTER TABLE `phan_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `so_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tang` int DEFAULT NULL,
  `chi_so_dien_hien_tai` double DEFAULT NULL,
  `chi_so_nuoc_hien_tai` double DEFAULT NULL,
  PRIMARY KEY (`ma_phong`),
  KEY `FKe4rcyk79xtbu58p52nlydr2p` (`ma_khu`),
  KEY `FK378h0h60ooky42egxi2ckdqu` (`ma_loai_phong`),
  CONSTRAINT `FK378h0h60ooky42egxi2ckdqu` FOREIGN KEY (`ma_loai_phong`) REFERENCES `loai_phong` (`ma_loai_phong`),
  CONSTRAINT `FKe4rcyk79xtbu58p52nlydr2p` FOREIGN KEY (`ma_khu`) REFERENCES `khu` (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES ('A101','A-101','Trống','A','LP08','Nữ',1,NULL,NULL),('A102','A-102','Trống','A','LP08','Nữ',1,NULL,NULL),('A103','A-103','Trống','A','LP08','Nữ',1,NULL,NULL),('A104','A-104','Trống','A','LP08','Nữ',1,NULL,NULL),('A105','A-105','Trống','A','LP08','Nữ',1,NULL,NULL),('A106','A-106','Trống','A','LP08','Nữ',1,NULL,NULL),('A107','A-107','Trống','A','LP08','Nữ',1,NULL,NULL),('A108','A-108','Trống','A','LP08','Nữ',1,NULL,NULL),('A109','A-109','Trống','A','LP08','Nữ',1,NULL,NULL),('A110','A-110','Trống','A','LP08','Nữ',1,NULL,NULL),('A111','A-111','Trống','A','LP08','Nữ',1,NULL,NULL),('A112','A-112','Trống','A','LP08','Nữ',1,NULL,NULL),('A201','A-201','Trống','A','LP08','Nữ',2,NULL,NULL),('A202','A-202','Trống','A','LP08','Nữ',2,NULL,NULL),('A203','A-203','Trống','A','LP08','Nữ',2,NULL,NULL),('A204','A-204','Trống','A','LP08','Nữ',2,NULL,NULL),('A205','A-205','Trống','A','LP08','Nữ',2,NULL,NULL),('A206','A-206','Trống','A','LP08','Nữ',2,NULL,NULL),('A207','A-207','Trống','A','LP08','Nữ',2,NULL,NULL),('A208','A-208','Trống','A','LP08','Nữ',2,NULL,NULL),('A209','A-209','Trống','A','LP08','Nữ',2,NULL,NULL),('A210','A-210','Trống','A','LP08','Nữ',2,NULL,NULL),('A211','A-211','Trống','A','LP08','Nữ',2,NULL,NULL),('A212','A-212','Trống','A','LP08','Nữ',2,NULL,NULL),('A301','A-301','Đang ở','A','LP08','Nam',3,200,10),('A302','A-302','Trống','A','LP08','Nam',3,NULL,NULL),('A303','A-303','Trống','A','LP08','Nam',3,NULL,NULL),('A304','A-304','Trống','A','LP08','Nam',3,NULL,NULL),('A305','A-305','Trống','A','LP08','Nam',3,NULL,NULL),('A306','A-306','Trống','A','LP08','Nam',3,NULL,NULL),('A307','A-307','Trống','A','LP08','Nam',3,NULL,NULL),('A308','A-308','Trống','A','LP08','Nam',3,NULL,NULL),('A309','A-309','Trống','A','LP08','Nam',3,NULL,NULL),('A310','A-310','Trống','A','LP08','Nam',3,NULL,NULL),('A311','A-311','Trống','A','LP08','Nam',3,NULL,NULL),('A312','A-312','Trống','A','LP08','Nam',3,NULL,NULL),('A401','A-401','Trống','A','LP08','Nam',4,NULL,NULL),('A402','A-402','Trống','A','LP08','Nam',4,NULL,NULL),('A403','A-403','Trống','A','LP08','Nam',4,NULL,NULL),('A404','A-404','Trống','A','LP08','Nam',4,NULL,NULL),('A405','A-405','Trống','A','LP08','Nam',4,NULL,NULL),('A406','A-406','Trống','A','LP08','Nam',4,NULL,NULL),('A407','A-407','Trống','A','LP08','Nam',4,NULL,NULL),('A408','A-408','Trống','A','LP08','Nam',4,NULL,NULL),('A409','A-409','Trống','A','LP08','Nam',4,NULL,NULL),('A410','A-410','Trống','A','LP08','Nam',4,NULL,NULL),('A411','A-411','Trống','A','LP08','Nam',4,NULL,NULL),('A412','A-412','Trống','A','LP08','Nam',4,NULL,NULL),('B101','B-101','Trống','B','LP06','Nữ',1,NULL,NULL),('B102','B-102','Trống','B','LP06','Nữ',1,NULL,NULL),('B103','B-103','Trống','B','LP06','Nữ',1,NULL,NULL),('B104','B-104','Trống','B','LP06','Nữ',1,NULL,NULL),('B105','B-105','Trống','B','LP06','Nữ',1,NULL,NULL),('B106','B-106','Trống','B','LP06','Nữ',1,NULL,NULL),('B107','B-107','Trống','B','LP06','Nữ',1,NULL,NULL),('B108','B-108','Trống','B','LP06','Nữ',1,NULL,NULL),('B109','B-109','Trống','B','LP06','Nữ',1,NULL,NULL),('B110','B-110','Trống','B','LP06','Nữ',1,NULL,NULL),('B111','B-111','Trống','B','LP06','Nữ',1,NULL,NULL),('B112','B-112','Trống','B','LP06','Nữ',1,NULL,NULL),('B201','B-201','Trống','B','LP06','Nữ',2,NULL,NULL),('B202','B-202','Trống','B','LP06','Nữ',2,NULL,NULL),('B203','B-203','Trống','B','LP06','Nữ',2,NULL,NULL),('B204','B-204','Trống','B','LP06','Nữ',2,NULL,NULL),('B205','B-205','Trống','B','LP06','Nữ',2,NULL,NULL),('B206','B-206','Trống','B','LP06','Nữ',2,NULL,NULL),('B207','B-207','Trống','B','LP06','Nữ',2,NULL,NULL),('B208','B-208','Trống','B','LP06','Nữ',2,NULL,NULL),('B209','B-209','Trống','B','LP06','Nữ',2,NULL,NULL),('B210','B-210','Trống','B','LP06','Nữ',2,NULL,NULL),('B211','B-211','Trống','B','LP06','Nữ',2,NULL,NULL),('B212','B-212','Trống','B','LP06','Nữ',2,NULL,NULL),('B301','B-301','Còn trống','B','LP06','Nam',3,NULL,NULL),('B302','B-302','Trống','B','LP06','Nam',3,NULL,NULL),('B303','B-303','Trống','B','LP06','Nam',3,NULL,NULL),('B304','B-304','Trống','B','LP06','Nam',3,NULL,NULL),('B305','B-305','Trống','B','LP06','Nam',3,NULL,NULL),('B306','B-306','Trống','B','LP06','Nam',3,NULL,NULL),('B307','B-307','Trống','B','LP06','Nam',3,NULL,NULL),('B308','B-308','Trống','B','LP06','Nam',3,NULL,NULL),('B309','B-309','Trống','B','LP06','Nam',3,NULL,NULL),('B310','B-310','Trống','B','LP06','Nam',3,NULL,NULL),('B311','B-311','Trống','B','LP06','Nam',3,NULL,NULL),('B312','B-312','Trống','B','LP06','Nam',3,NULL,NULL),('B401','B-401','Trống','B','LP06','Nam',4,NULL,NULL),('B402','B-402','Trống','B','LP06','Nam',4,NULL,NULL),('B403','B-403','Trống','B','LP06','Nam',4,NULL,NULL),('B404','B-404','Trống','B','LP06','Nam',4,NULL,NULL),('B405','B-405','Trống','B','LP06','Nam',4,NULL,NULL),('B406','B-406','Trống','B','LP06','Nam',4,NULL,NULL),('B407','B-407','Trống','B','LP06','Nam',4,NULL,NULL),('B408','B-408','Trống','B','LP06','Nam',4,NULL,NULL),('B409','B-409','Trống','B','LP06','Nam',4,NULL,NULL),('B410','B-410','Trống','B','LP06','Nam',4,NULL,NULL),('B411','B-411','Trống','B','LP06','Nam',4,NULL,NULL),('B412','B-412','Trống','B','LP06','Nam',4,NULL,NULL),('C101','C-101','Trống','C','LP04','Nữ',1,NULL,NULL),('C102','C-102','Trống','C','LP04','Nữ',1,NULL,NULL),('C103','C-103','Trống','C','LP04','Nữ',1,NULL,NULL),('C104','C-104','Trống','C','LP04','Nữ',1,NULL,NULL),('C105','C-105','Trống','C','LP04','Nữ',1,NULL,NULL),('C106','C-106','Trống','C','LP04','Nữ',1,NULL,NULL),('C107','C-107','Trống','C','LP04','Nữ',1,NULL,NULL),('C108','C-108','Trống','C','LP04','Nữ',1,NULL,NULL),('C109','C-109','Trống','C','LP04','Nữ',1,NULL,NULL),('C110','C-110','Trống','C','LP04','Nữ',1,NULL,NULL),('C111','C-111','Trống','C','LP04','Nữ',1,NULL,NULL),('C112','C-112','Trống','C','LP04','Nữ',1,NULL,NULL),('C201','C-201','Trống','C','LP04','Nữ',2,NULL,NULL),('C202','C-202','Trống','C','LP04','Nữ',2,NULL,NULL),('C203','C-203','Trống','C','LP04','Nữ',2,NULL,NULL),('C204','C-204','Trống','C','LP04','Nữ',2,NULL,NULL),('C205','C-205','Trống','C','LP04','Nữ',2,NULL,NULL),('C206','C-206','Trống','C','LP04','Nữ',2,NULL,NULL),('C207','C-207','Trống','C','LP04','Nữ',2,NULL,NULL),('C208','C-208','Trống','C','LP04','Nữ',2,NULL,NULL),('C209','C-209','Trống','C','LP04','Nữ',2,NULL,NULL),('C210','C-210','Trống','C','LP04','Nữ',2,NULL,NULL),('C211','C-211','Trống','C','LP04','Nữ',2,NULL,NULL),('C212','C-212','Trống','C','LP04','Nữ',2,NULL,NULL),('C301','C-301','Trống','C','LP04','Nam',3,0,0),('C302','C-302','Trống','C','LP04','Nam',3,NULL,NULL),('C303','C-303','Trống','C','LP04','Nam',3,NULL,NULL),('C304','C-304','Trống','C','LP04','Nam',3,NULL,NULL),('C305','C-305','Trống','C','LP04','Nam',3,NULL,NULL),('C306','C-306','Trống','C','LP04','Nam',3,NULL,NULL),('C307','C-307','Trống','C','LP04','Nam',3,NULL,NULL),('C308','C-308','Trống','C','LP04','Nam',3,NULL,NULL),('C309','C-309','Trống','C','LP04','Nam',3,NULL,NULL),('C310','C-310','Trống','C','LP04','Nam',3,NULL,NULL),('C311','C-311','Trống','C','LP04','Nam',3,NULL,NULL),('C312','C-312','Trống','C','LP04','Nam',3,NULL,NULL),('C401','C-401','Trống','C','LP04','Nam',4,NULL,NULL),('C402','C-402','Trống','C','LP04','Nam',4,NULL,NULL),('C403','C-403','Trống','C','LP04','Nam',4,NULL,NULL),('C404','C-404','Trống','C','LP04','Nam',4,NULL,NULL),('C405','C-405','Trống','C','LP04','Nam',4,NULL,NULL),('C406','C-406','Trống','C','LP04','Nam',4,NULL,NULL),('C407','C-407','Trống','C','LP04','Nam',4,NULL,NULL),('C408','C-408','Trống','C','LP04','Nam',4,NULL,NULL),('C409','C-409','Trống','C','LP04','Nam',4,NULL,NULL),('C410','C-410','Trống','C','LP04','Nam',4,NULL,NULL),('C411','C-411','Trống','C','LP04','Nam',4,NULL,NULL),('C412','C-412','Trống','C','LP04','Nam',4,NULL,NULL);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinh_vien` (
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dien_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam_hoc` int DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `que_quan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_the_sinh_vien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dia_chi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten_phu_huynh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt_phu_huynh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `truong_dai_hoc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinh_vien`
--

LOCK TABLES `sinh_vien` WRITE;
/*!40000 ALTER TABLE `sinh_vien` DISABLE KEYS */;
INSERT INTO `sinh_vien` VALUES ('SV0001','01234567890','Không ưu tiên','Nam','Nguyễn Duy Quang','Công nghệ thông tin','$2a$10$esu/0hfaiQM7As4b352Lsun9zIs2vO/oFY8YhZlfMYwxK2W6ouiwG',2026,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','duyquang22042005@gmail.com','https://res.cloudinary.com/svyjms27/image/upload/v1792683508/yvzjenrnqtzpnyhvf3wk.jpg',NULL,NULL,NULL,'Hoạt động','ĐH Công nghệ Kỹ thuật '),('SV0002','01234567891','Không ưu tiên','Nam','Nguyễn Quang Duy','Công nghệ thông tin','$2a$10$1LIroPGI08/w2L1bpNn9uO6M8OBFGflBfB7x3j.RHt4y7mh/a9JnO',2026,'2005-04-12','Kon Tum','0378869291','quangduy22042005@gmail.com','https://res.cloudinary.com/svyjms27/image/upload/v1792683861/fxapdsjjnnvfls88q50g.jpg',NULL,NULL,NULL,'Hoạt động','ĐH Công nghệ Kỹ thuật ');
/*!40000 ALTER TABLE `sinh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tam_vang`
--

DROP TABLE IF EXISTS `tam_vang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tam_vang` (
  `ma_tam_vang` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `den_ngay` date DEFAULT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tu_ngay` date DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_tam_vang`),
  KEY `FKoci8ikwvyx41wkxdu72v8vd8c` (`ma_phong`),
  KEY `FKl3vltdq700bccdnrwgy220y33` (`masv`),
  CONSTRAINT `FKl3vltdq700bccdnrwgy220y33` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FKoci8ikwvyx41wkxdu72v8vd8c` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tam_vang`
--

LOCK TABLES `tam_vang` WRITE;
/*!40000 ALTER TABLE `tam_vang` DISABLE KEYS */;
/*!40000 ALTER TABLE `tam_vang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thong_bao`
--

DROP TABLE IF EXISTS `thong_bao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thong_bao` (
  `ma_thong_bao` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `da_doc` bit(1) NOT NULL,
  `link` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `loai_thong_bao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `nguoi_nhan` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `noi_dung` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `tieu_de` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_thong_bao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thong_bao`
--

LOCK TABLES `thong_bao` WRITE;
/*!40000 ALTER TABLE `thong_bao` DISABLE KEYS */;
INSERT INTO `thong_bao` VALUES ('16085525-ea05-499e-b17f-e1d2be7b4a52',_binary '','/quansinh/chat?maSV=SV0001','Chat','2026-10-08 15:49:49.480961','ROLE_QUAN_SINH','Em chào thầy','Tin nhắn mới từ Nguyễn Duy Quang'),('252b2c31-eaa0-4ead-a244-1bb2174335a6',_binary '','/quansinh/chat?maSV=SV0003','Chat','2026-10-19 16:09:05.059198','ROLE_QUAN_SINH','.','Tin nhắn mới từ Nguyễn Văn A'),('389d44f4-babe-48f5-87e7-dd9f149dce31',_binary '','/quansinh/chat?maSV=SV0001','Chat','2026-10-08 16:43:23.918252','ROLE_QUAN_SINH','em chào thầy','Tin nhắn mới từ Nguyễn Duy Quang'),('3983faa8-df17-4a2d-951a-973a5a931405',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-24 11:37:17.716298','SV0002','Bạn có hóa đơn điện nước tháng 1/2026 cần thanh toán trước ngày 25.','Hóa đơn điện nước mới'),('4418687d-cd26-4958-8149-0130da7cdeea',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-22 15:53:36.482741','SV0002','Bạn có hóa đơn tiền phòng KTX 01 năm với số tiền 9,600,000 đ. Thời hạn hoàn tất nộp trong vòng 15 ngày kể từ ngày vào ở (Hạn chót: 06/11/2026). Vui lòng thanh toán đúng hạn để tránh bị thu hồi phòng và buộc rời khỏi KTX.','Hóa đơn tiền phòng 01 năm mới'),('4967a85c-f216-4f33-b6a2-8d1c48df4a9b',_binary '','/quansinh/chat?maSV=SV0002','Chat','2026-10-24 11:31:19.688362','ROLE_QUAN_SINH','Em chào thầy','Tin nhắn mới từ Nguyễn Quang Duy'),('4ddb23fe-6e8a-4f22-8e9e-8aad17df688e',_binary '\0','/sinhvien/sua-chua','SuaChua','2026-10-24 11:33:27.950154','SV0001','Yêu cầu sửa chữa phòng A-301 đã được xử lý xong.','Yêu cầu sửa chữa đã hoàn thành'),('53bcccfa-8490-48ab-a3e4-082c7fbeac40',_binary '\0','/sinhvien/phong','DangKy','2026-10-22 15:53:36.488015','SV0002','Bạn đã được phân vào phòng A-301 (Khu A).','Đơn đăng ký phòng được duyệt'),('5406031e-c8a6-46c7-801f-a299b7554d8f',_binary '','/quansinh/chat?maSV=SV0003','Chat','2026-10-19 16:11:40.661809','ROLE_QUAN_SINH','.','Tin nhắn mới từ Nguyễn Văn A'),('5e24d35b-5ac4-434f-8218-083e299528ff',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-22 15:48:03.247929','SV0001','Bạn có hóa đơn tiền phòng KTX 01 năm với số tiền 9,600,000 đ. Thời hạn hoàn tất nộp trong vòng 15 ngày kể từ ngày vào ở (Hạn chót: 06/11/2026). Vui lòng thanh toán đúng hạn để tránh bị thu hồi phòng và buộc rời khỏi KTX.','Hóa đơn tiền phòng 01 năm mới'),('5e39c7ee-53c8-45d4-8d6a-d49ded6612e5',_binary '','/quansinh/sua-chua','SuaChua','2026-10-24 11:33:27.957574','ROLE_QUAN_SINH','Phòng A-301 đã sửa chữa xong.','Nhân viên đã hoàn thành sửa chữa'),('692c05b0-8ff2-4cad-a7d6-dfe77af0a214',_binary '\0','/sinhvien/phong','DangKy','2026-10-22 15:48:03.252577','SV0001','Bạn đã được phân vào phòng A-301 (Khu A).','Đơn đăng ký phòng được duyệt'),('79bfcbd7-7244-44c1-ac17-d0fd43ef88e1',_binary '\0','/sinhvien/dashboard','Chat','2026-10-24 11:32:27.926884','SV0002','chào em','Ban Quản Sinh đã phản hồi tin nhắn'),('9982eaaa-e492-4935-85b7-53ff8c8f5cc0',_binary '','/quansinh/sua-chua','SuaChua','2026-10-18 14:14:24.346115','ROLE_QUAN_SINH','Phòng C-301 đã sửa chữa xong.','Nhân viên đã hoàn thành sửa chữa'),('9f917b13-4648-484d-96a6-313f3424c5ca',_binary '','/quansinh/sua-chua','SuaChua','2026-10-08 14:59:15.339339','ROLE_QUAN_SINH','Phòng C-301 đã sửa chữa xong.','Nhân viên đã hoàn thành sửa chữa'),('a3e34496-c1c7-4bfb-8925-dfc140c74e35',_binary '\0','/quansinh/sua-chua','SuaChua','2026-10-08 14:55:05.434971','ROLE_QUAN_SINH','Có một yêu cầu sửa chữa mới từ phòng C-301','Yêu cầu sửa chữa mới'),('b2f9af09-f861-4589-8e6f-751f3ea99f4e',_binary '','/quansinh/chat?maSV=SV0003','Chat','2026-10-19 16:08:35.564139','ROLE_QUAN_SINH','em chào thầy','Tin nhắn mới từ Nguyễn Văn A'),('b47df81d-2a84-4f87-a15e-86c872fe1910',_binary '\0','/nhanvien/sua-chua','SuaChua','2026-10-08 14:56:41.969361','nhanvien','Phòng C-301: Hư đèn','Bạn được giao việc sửa chữa mới'),('c23e4029-5369-4299-87d2-644bfdebdf17',_binary '\0','/sinhvien/sua-chua','SuaChua','2026-10-24 11:32:20.043574','SV0001','Yêu cầu của bạn đã được giao cho nhân viên xử lý.','Yêu cầu sửa chữa đã được tiếp nhận'),('caa5a737-71e8-4f91-a42c-645562e03e2e',_binary '\0','/quansinh/chat?maSV=SV0003','Chat','2026-10-18 13:16:59.138992','ROLE_QUAN_SINH','Thầy ơi em chưa được tạo hợp đồng lưu trú','Tin nhắn mới từ Nguyễn Văn A'),('cf0621c1-946d-46c8-8476-73067be8ebd4',_binary '','/quansinh/sua-chua','SuaChua','2026-10-18 14:13:18.794051','ROLE_QUAN_SINH','Có một yêu cầu sửa chữa mới từ phòng C-301','Yêu cầu sửa chữa mới'),('d46a8fa6-2dde-467e-a45e-1e5dd8d7f1c0',_binary '','/quansinh/chat?maSV=SV0002','Chat','2026-10-08 15:50:10.096156','ROLE_QUAN_SINH','Em chào thầy','Tin nhắn mới từ Nguyễn Quang Duy'),('d6548ce8-e705-4e09-86d4-f7079302c04c',_binary '','/quansinh/sua-chua','SuaChua','2026-10-24 11:31:55.864697','ROLE_QUAN_SINH','Có một yêu cầu sửa chữa mới từ phòng A-301','Yêu cầu sửa chữa mới'),('e062bfba-76b6-4825-960e-3e62a5f70484',_binary '\0','/sinhvien/dashboard','Chat','2026-10-24 11:32:34.359398','SV0001','chào em','Ban Quản Sinh đã phản hồi tin nhắn'),('e62ff689-0953-4af0-b773-66e9b09ba1c6',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-24 11:37:17.696838','SV0001','Bạn có hóa đơn điện nước tháng 1/2026 cần thanh toán trước ngày 25.','Hóa đơn điện nước mới'),('eb2561e5-58f3-4d16-9db2-1d0f070e552d',_binary '','/quansinh/chat?maSV=SV0001','Chat','2026-10-24 11:31:32.502597','ROLE_QUAN_SINH','em chào thầy','Tin nhắn mới từ Nguyễn Duy Quang'),('f1792496-5e16-4f1d-aa5e-ce238d537e48',_binary '\0','/nhanvien/sua-chua','SuaChua','2026-10-24 11:32:20.037160','nhanvien','Phòng A-301: Hư đèn','Bạn được giao việc sửa chữa mới'),('fe78ce1a-e4a7-4c07-ae7b-82018fbd21d0',_binary '\0','/nhanvien/sua-chua','SuaChua','2026-10-18 14:13:54.558660','nhanvien','Phòng C-301: Đèn hư','Bạn được giao việc sửa chữa mới');
/*!40000 ALTER TABLE `thong_bao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tin_nhan`
--

DROP TABLE IF EXISTS `tin_nhan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tin_nhan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `da_doc` bit(1) DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nguoi_gui` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `noi_dung` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_nguoi_gui` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thoi_gian` datetime(6) DEFAULT NULL,
  `vai_tro_gui` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tin_nhan`
--

LOCK TABLES `tin_nhan` WRITE;
/*!40000 ALTER TABLE `tin_nhan` DISABLE KEYS */;
INSERT INTO `tin_nhan` VALUES (24,_binary '','SV0002','SV0002','Em chào thầy','Nguyễn Quang Duy','2026-10-24 11:31:19.658513','SINH_VIEN'),(25,_binary '','SV0001','SV0001','em chào thầy','Nguyễn Duy Quang','2026-10-24 11:31:32.499402','SINH_VIEN'),(26,_binary '\0','SV0002','QUAN_SINH','chào em','Ban Quản Sinh KTX','2026-10-24 11:32:27.926884','QUAN_SINH'),(27,_binary '\0','SV0001','QUAN_SINH','chào em','Ban Quản Sinh KTX','2026-10-24 11:32:34.356308','QUAN_SINH');
/*!40000 ALTER TABLE `tin_nhan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tra_phong`
--

DROP TABLE IF EXISTS `tra_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tra_phong` (
  `ma_tra_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ket_qua_kiem_ke` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_tra` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_tra_phong`),
  KEY `FKg0ygq3eho23ucm92lcc3nykbt` (`ma_phan_phong`),
  CONSTRAINT `FKg0ygq3eho23ucm92lcc3nykbt` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tra_phong`
--

LOCK TABLES `tra_phong` WRITE;
/*!40000 ALTER TABLE `tra_phong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tra_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vi_pham`
--

DROP TABLE IF EXISTS `vi_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vi_pham` (
  `ma_vi_pham` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dia_diem` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hinh_thuc_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_lap_bien_ban` date DEFAULT NULL,
  `ngay_vi_pham` date DEFAULT NULL,
  `noi_dung` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_vi_pham`),
  KEY `FKfvl4jr8ddtpe4yt4roohov11q` (`masv`),
  CONSTRAINT `FKfvl4jr8ddtpe4yt4roohov11q` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vi_pham`
--

LOCK TABLES `vi_pham` WRITE;
/*!40000 ALTER TABLE `vi_pham` DISABLE KEYS */;
/*!40000 ALTER TABLE `vi_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yeu_cau_sua_chua`
--

DROP TABLE IF EXISTS `yeu_cau_sua_chua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `yeu_cau_sua_chua` (
  `ma_yeu_cau` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hinh_anh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_gui` datetime(6) DEFAULT NULL,
  `noi_dung_su_co` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_yeu_cau`),
  KEY `FK9eajwoukoeyvs09sjt4g1rj95` (`ma_phong`),
  KEY `FK1lhkwgeq2t1ewtnppy29jslis` (`masv`),
  CONSTRAINT `FK1lhkwgeq2t1ewtnppy29jslis` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FK9eajwoukoeyvs09sjt4g1rj95` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yeu_cau_sua_chua`
--

LOCK TABLES `yeu_cau_sua_chua` WRITE;
/*!40000 ALTER TABLE `yeu_cau_sua_chua` DISABLE KEYS */;
INSERT INTO `yeu_cau_sua_chua` VALUES ('e526803d-1536-4f7b-9a88-c98f0427c424','https://res.cloudinary.com/svyjms27/image/upload/v1792840999/ywnr1imiomfrntuu8n97.jpg','2026-10-24 11:31:52.974551','Hư đèn','Đã hoàn thành','A301','SV0001');
/*!40000 ALTER TABLE `yeu_cau_sua_chua` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-28 23:53:32
