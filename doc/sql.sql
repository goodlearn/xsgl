CREATE TABLE `institute` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `institute_update_date` (`update_date`),
  KEY `institute_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院'

CREATE TABLE `professionInfo` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `institute_id` VARCHAR(64) NOT NULL COMMENT '所属院系',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `profession_update_date` (`update_date`),
  KEY `profession_del_flag` (`del_flag`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='专业'


CREATE TABLE `classInfo` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `profession_id` VARCHAR(64) NOT NULL COMMENT '所属专业',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `class_update_date` (`update_date`),
  KEY `class_del_flag` (`del_flag`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='班级'

CREATE TABLE `student` ( 
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `class_id` VARCHAR(64) NOT NULL COMMENT '所属班级',
  `no` VARCHAR(64) DEFAULT NULL COMMENT '学号',
  `name` VARCHAR(100) NOT NULL COMMENT '姓名',
  `sushe` VARCHAR(64)  DEFAULT NULL COMMENT '宿舍号',
  `zizhucard` VARCHAR(64) DEFAULT NULL COMMENT '资助卡号',
  `sex` CHAR(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `mingzhu` VARCHAR(64)   DEFAULT '0' COMMENT '民族',
  `political` VARCHAR(64) DEFAULT NULL  COMMENT '政治面貌',
  `idcard` VARCHAR(64) NOT NULL COMMENT '身份证号',
  `birth` VARCHAR(64)   DEFAULT NULL COMMENT '出生日期',
  `originaschool` VARCHAR(64)  DEFAULT NULL COMMENT '原毕业学校',
  `originaperson` VARCHAR(64)  DEFAULT NULL COMMENT '原毕业中学班主任',
  `jiguan` VARCHAR(64) DEFAULT NULL  COMMENT '籍贯',
  `jiguansheng` VARCHAR(64) DEFAULT NULL COMMENT '户籍所在省',
  `jiguanshi` VARCHAR(64) DEFAULT NULL COMMENT '户籍所在市',
  `jiguanxian` VARCHAR(64) DEFAULT NULL  COMMENT '户籍所在区县',
  `jianguandetail` VARCHAR(255) DEFAULT NULL COMMENT '户籍详细地址',
  `nowjiguansheng` VARCHAR(64) DEFAULT NULL COMMENT '现家庭所在省',
  `nowjiguanshi` VARCHAR(64) DEFAULT NULL COMMENT '现家庭所在市',
  `nowjiguanxian` VARCHAR(64) DEFAULT NULL  COMMENT '现家庭所在区县',
  `nowjianguandetail` VARCHAR(255) DEFAULT NULL COMMENT '现家庭详细地址',
  `hujixingzhi` VARCHAR(64) DEFAULT NULL  COMMENT '户籍性质',
  `pingkunzm` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否有贫困证明或低保证',
  `shifouxszxj` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否享受国家助学金',
  `zxjnum` VARCHAR(64) DEFAULT NULL COMMENT '助学金月发放标准（元）',
  `stutype` VARCHAR(64) DEFAULT NULL COMMENT '学生类型',
  `shengyuantype` VARCHAR(64) DEFAULT NULL COMMENT '生源类别',
  `fathername` VARCHAR(64) DEFAULT NULL COMMENT '父亲姓名',
  `fatheridcard` VARCHAR(64) DEFAULT NULL COMMENT '父亲身份证号',
  `fatherculture` VARCHAR(64) DEFAULT NULL COMMENT '父亲文化程度',
  `fatherworkunit` VARCHAR(64) DEFAULT NULL COMMENT '父亲工作单位',
  `fatherphone` VARCHAR(64) DEFAULT NULL COMMENT '父亲电话',
  `mothername` VARCHAR(64) DEFAULT NULL COMMENT '母亲姓名',
  `motheridcard` VARCHAR(64) DEFAULT NULL COMMENT '母亲身份证号',
  `motherculture` VARCHAR(64) DEFAULT NULL COMMENT '母亲文化程度',
  `motherworkunit` VARCHAR(64) DEFAULT NULL COMMENT '母亲工作单位',
  `motherphone` VARCHAR(64) DEFAULT NULL COMMENT '母亲电话',
  `famliynum` VARCHAR(64) DEFAULT NULL COMMENT '家庭人口',
  `isblzxdk` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否办理助学贷款',
  `phone` VARCHAR(100) NOT NULL COMMENT '电话',
  `ispartjun` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否参军及参军时间',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `student_update_date` (`update_date`),
  KEY `student_del_flag` (`del_flag`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='学生'

