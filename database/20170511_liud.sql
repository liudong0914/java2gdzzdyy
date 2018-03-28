DROP TABLE IF EXISTS tk_textbook_info;

/*==============================================================*/
/* Table: tk_textbook_info                                      */
/*==============================================================*/
CREATE TABLE tk_textbook_info
(
   textbookid           INT(11) NOT NULL AUTO_INCREMENT,
   orderindex           INT(11) COMMENT '排序',
   textbookname         VARCHAR(100) COMMENT '教材名称',
   author               VARCHAR(50) COMMENT '作者',
   sketch               VARCHAR(100) COMMENT '缩略图',
   press                VARCHAR(100) COMMENT '出版社',
   unitid               INT(11) COMMENT '单位',
   STATUS               CHAR(1) COMMENT '状态',
   price                FLOAT(6,2) COMMENT '单价',
   discount             FLOAT COMMENT '折扣',
   sellprice            FLOAT(6,2) COMMENT '实际单价',
   sellcount            INT(11) COMMENT '售卖数量',
   createdate           CHAR(19) COMMENT '创建时间',
   phone                VARCHAR(20) COMMENT '订购电话',
   TYPE                 VARCHAR(200) COMMENT '教材范围和种类',
   PRIMARY KEY (textbookid)
);

ALTER TABLE tk_textbook_info COMMENT '教材信息表';

DROP TABLE IF EXISTS tk_textbook_buy;

/*==============================================================*/
/* Table: tk_textbook_buy                                       */
/*==============================================================*/
CREATE TABLE tk_textbook_buy
(
   textbookbuyid        INT(11) NOT NULL AUTO_INCREMENT,
   textbookid           INT(11) COMMENT '订购教材id',
   price                FLOAT(6,2) COMMENT '单机',
   discount             FLOAT COMMENT '折扣',
   sellprice            FLOAT(6,2) COMMENT '实际单价',
   createdate           CHAR(19),
   totalnum             INT(11) COMMENT '订购总数',
   totalprice           FLOAT(6,2) COMMENT '订购总价',
   buyuserid            INT(11) COMMENT '购买人id',
   recipientname        VARCHAR(50) COMMENT '收件人姓名',
   recipientphone       VARCHAR(200) COMMENT '收件人电话',
   recipientaddress     VARCHAR(300) COMMENT '收件人地址',
   isdelivery           VARCHAR(11) COMMENT '发货状态',
   PRIMARY KEY (textbookbuyid)
);

ALTER TABLE tk_textbook_buy COMMENT '教材订购记录';


alter table edu_course_info add column note varchar(100);

