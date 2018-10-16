/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 学生信息Entity
 * @author wzy
 * @version 2018-09-07
 */
public class Student extends DataEntity<Student> {
	
	private static final long serialVersionUID = 1L;
	private String classId;		// 所属班级
	private String no;		// 学号
	private String name;		// 姓名
	private String sushe;		// 宿舍号
	private String zizhucard;		// 资助卡号
	private String sex;		// 性别
	private String mingzhu;		// 民族
	private String political;		// 政治面貌
	private String idcard;		// 身份证号
	private String birth;		// 出生日期
	private String originaschool;		// 原毕业学校
	private String originaperson;		// 原毕业中学班主任
	private String jiguan;		// 籍贯
	private String jiguansheng;		// 户籍所在省
	private String jiguanshi;		// 户籍所在市
	private String jiguanxian;		// 户籍所在区县
	private String jianguandetail;		// 户籍详细地址
	private String nowjiguansheng;		// 现家庭所在省
	private String nowjiguanshi;		// 现家庭所在市
	private String nowjiguanxian;		// 现家庭所在区县
	private String nowjianguandetail;		// 现家庭详细地址
	private String hujixingzhi;		// 户籍性质
	private String pingkunzm;		// 是否有贫困证明或低保证
	private String shifouxszxj;		// 是否享受国家助学金
	private String zxjnum;		// 助学金月发放标准（元）
	private String stutype;		// 学生类型
	private String shengyuantype;		// 生源类别
	private String fathername;		// 父亲姓名
	private String fatheridcard;		// 父亲身份证号
	private String fatherculture;		// 父亲文化程度
	private String fatherworkunit;		// 父亲工作单位
	private String fatherphone;		// 父亲电话
	private String mothername;		// 母亲姓名
	private String motheridcard;		// 母亲身份证号
	private String motherculture;		// 母亲文化程度
	private String motherworkunit;		// 母亲工作单位
	private String motherphone;		// 母亲电话
	private String famliynum;		// 家庭人口
	private String isblzxdk;		// 是否办理助学贷款
	private String phone;		// 电话
	private String ispartjun;		// 是否参军及参军时间
	private String score;//学生分数
	private String pwd;//学生密码
	private String tie;//是否绑定微信
	
	private Classinfo classInfo;//所属班级
	private SysWxInfo sysWxInfo;//绑定微信信息
	private String professClassName;//临时班级专业名称
	private List<String> class_ids = null;//临时所属班级（多个）
	
	//临时信息
	private boolean headImgWx = false;
	private String headImgWxUrl;
	
	
	public String getHeadImgWxUrl() {
		return headImgWxUrl;
	}

	public void setHeadImgWxUrl(String headImgWxUrl) {
		this.headImgWxUrl = headImgWxUrl;
	}

	public boolean isHeadImgWx() {
		return headImgWx;
	}

	public void setHeadImgWx(boolean headImgWx) {
		this.headImgWx = headImgWx;
	}


	public SysWxInfo getSysWxInfo() {
		return sysWxInfo;
	}

	public void setSysWxInfo(SysWxInfo sysWxInfo) {
		this.sysWxInfo = sysWxInfo;
	}

	public String getProfessClassName() {
		return professClassName;
	}

	public void setProfessClassName(String professClassName) {
		this.professClassName = professClassName;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTie() {
		return tie;
	}

	public void setTie(String tie) {
		this.tie = tie;
	}

	public List<String> getClass_ids() {
		return class_ids;
	}

	public void setClass_ids(List<String> class_ids) {
		this.class_ids = class_ids;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Classinfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(Classinfo classInfo) {
		this.classInfo = classInfo;
	}

	public Student() {
		super();
	}

	public Student(String id){
		super(id);
	}

	
	@Length(min=1, max=64, message="所属班级长度必须介于 1 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=64, message="学号长度必须介于 0 和 64 之间")
	@ExcelField(title="学号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	@ExcelField(title="姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="宿舍号长度必须介于 0 和 64 之间")
	@ExcelField(title="宿舍号", align=2, sort=3)
	public String getSushe() {
		return sushe;
	}

	public void setSushe(String sushe) {
		this.sushe = sushe;
	}
	
	@Length(min=0, max=64, message="资助卡号长度必须介于 0 和 64 之间")
	@ExcelField(title="中职资助卡号", align=2, sort=4)
	public String getZizhucard() {
		return zizhucard;
	}

	public void setZizhucard(String zizhucard) {
		this.zizhucard = zizhucard;
	}
	
	@Length(min=1, max=1, message="性别长度必须介于 1 和 1 之间")
	@ExcelField(title="性别", align=2, sort=5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=64, message="民族长度必须介于 0 和 64 之间")
	@ExcelField(title="民族", align=2, sort=6)
	public String getMingzhu() {
		return mingzhu;
	}

	public void setMingzhu(String mingzhu) {
		this.mingzhu = mingzhu;
	}
	
	@ExcelField(title="修读专业", align=2, sort=7)
	public String getProsseionName() {
		if(null == professClassName && null!=classInfo) {
			return classInfo.getName();
		}
		return professClassName;
	}
	
	public void setProsseionName(String prosseionName) {
		professClassName = prosseionName;
	}
	
	
	@Length(min=0, max=64, message="政治面貌长度必须介于 0 和 64 之间")
	@ExcelField(title="政治面貌", align=2, sort=8)
	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}
	
	@Length(min=1, max=64, message="身份证号长度必须介于 1 和 64 之间")
	@ExcelField(title="身份证号", align=2, sort=9)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Length(min=0, max=64, message="出生日期长度必须介于 0 和 64 之间")
	@ExcelField(title="出生日期", align=2, sort=10)
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	@Length(min=0, max=64, message="原毕业学校长度必须介于 0 和 64 之间")
	@ExcelField(title="原毕业学校", align=2, sort=11)
	public String getOriginaschool() {
		return originaschool;
	}

	public void setOriginaschool(String originaschool) {
		this.originaschool = originaschool;
	}
	
	@Length(min=0, max=64, message="原毕业中学班主任长度必须介于 0 和 64 之间")
	@ExcelField(title="原毕业中学班主任", align=2, sort=12)
	public String getOriginaperson() {
		return originaperson;
	}

	public void setOriginaperson(String originaperson) {
		this.originaperson = originaperson;
	}
	
	@Length(min=0, max=64, message="籍贯长度必须介于 0 和 64 之间")
	@ExcelField(title="籍贯", align=2, sort=13)
	public String getJiguan() {
		return jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}
	
	@Length(min=0, max=64, message="户籍所在省长度必须介于 0 和 64 之间")
	@ExcelField(title="户籍所在省", align=2, sort=14)
	public String getJiguansheng() {
		return jiguansheng;
	}

	public void setJiguansheng(String jiguansheng) {
		this.jiguansheng = jiguansheng;
	}
	
	@Length(min=0, max=64, message="户籍所在市长度必须介于 0 和 64 之间")
	@ExcelField(title="户籍所在市", align=2, sort=15)
	public String getJiguanshi() {
		return jiguanshi;
	}

	public void setJiguanshi(String jiguanshi) {
		this.jiguanshi = jiguanshi;
	}
	
	@Length(min=0, max=64, message="户籍所在区县长度必须介于 0 和 64 之间")
	@ExcelField(title="户籍所在区县", align=2, sort=16)
	public String getJiguanxian() {
		return jiguanxian;
	}

	public void setJiguanxian(String jiguanxian) {
		this.jiguanxian = jiguanxian;
	}
	
	@Length(min=0, max=255, message="户籍详细地址长度必须介于 0 和 255 之间")
	@ExcelField(title="户籍所在地详细地址", align=2, sort=17)
	public String getJianguandetail() {
		return jianguandetail;
	}

	public void setJianguandetail(String jianguandetail) {
		this.jianguandetail = jianguandetail;
	}
	
	
	@Length(min=0, max=64, message="现家庭所在省长度必须介于 0 和 64 之间")
	@ExcelField(title="现家庭所在省", align=2, sort=18)
	public String getNowjiguansheng() {
		return nowjiguansheng;
	}

	public void setNowjiguansheng(String nowjiguansheng) {
		this.nowjiguansheng = nowjiguansheng;
	}
	
	@Length(min=0, max=64, message="现家庭所在市长度必须介于 0 和 64 之间")
	@ExcelField(title="现家庭所在市", align=2, sort=19)
	public String getNowjiguanshi() {
		return nowjiguanshi;
	}

	public void setNowjiguanshi(String nowjiguanshi) {
		this.nowjiguanshi = nowjiguanshi;
	}
	
	@Length(min=0, max=64, message="现家庭所在区县长度必须介于 0 和 64 之间")
	@ExcelField(title="现家庭所在区县", align=2, sort=20)
	public String getNowjiguanxian() {
		return nowjiguanxian;
	}

	public void setNowjiguanxian(String nowjiguanxian) {
		this.nowjiguanxian = nowjiguanxian;
	}
	
	@Length(min=0, max=255, message="现家庭详细地址长度必须介于 0 和 255 之间")
	@ExcelField(title="现家庭详细地址", align=2, sort=21)
	public String getNowjianguandetail() {
		return nowjianguandetail;
	}

	public void setNowjianguandetail(String nowjianguandetail) {
		this.nowjianguandetail = nowjianguandetail;
	}
	
	@Length(min=0, max=64, message="户籍性质长度必须介于 0 和 64 之间")
	@ExcelField(title="户籍性质", align=2, sort=22)
	public String getHujixingzhi() {
		return hujixingzhi;
	}

	public void setHujixingzhi(String hujixingzhi) {
		this.hujixingzhi = hujixingzhi;
	}
	
	@Length(min=1, max=1, message="是否有贫困证明或低保证长度必须介于 1 和 1 之间")
	@ExcelField(title="是否有贫困证明或低保证", align=2, sort=23)
	public String getPingkunzm() {
		return pingkunzm;
	}

	public void setPingkunzm(String pingkunzm) {
		this.pingkunzm = pingkunzm;
	}
	
	@Length(min=1, max=1, message="是否享受国家助学金长度必须介于 1 和 1 之间")
	@ExcelField(title="是否享受国家助学金", align=2, sort=24)
	public String getShifouxszxj() {
		return shifouxszxj;
	}

	public void setShifouxszxj(String shifouxszxj) {
		this.shifouxszxj = shifouxszxj;
	}
	
	@Length(min=0, max=64, message="助学金月发放标准（元）长度必须介于 0 和 64 之间")
	@ExcelField(title="助学金月发放标准（元）", align=2, sort=25)
	public String getZxjnum() {
		return zxjnum;
	}

	public void setZxjnum(String zxjnum) {
		this.zxjnum = zxjnum;
	}
	
	@Length(min=0, max=64, message="学生类型长度必须介于 0 和 64 之间")
	@ExcelField(title="学生类型", align=2, sort=26)
	public String getStutype() {
		return stutype;
	}

	public void setStutype(String stutype) {
		this.stutype = stutype;
	}
	
	@Length(min=0, max=64, message="生源类别长度必须介于 0 和 64 之间")
	@ExcelField(title="生源类别", align=2, sort=27)
	public String getShengyuantype() {
		return shengyuantype;
	}

	public void setShengyuantype(String shengyuantype) {
		this.shengyuantype = shengyuantype;
	}
	
	@Length(min=0, max=64, message="父亲姓名长度必须介于 0 和 64 之间")
	@ExcelField(title="父亲姓名", align=2, sort=28)
	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	
	@Length(min=0, max=64, message="父亲身份证号长度必须介于 0 和 64 之间")
	@ExcelField(title="父亲身份证号", align=2, sort=29)
	public String getFatheridcard() {
		return fatheridcard;
	}

	public void setFatheridcard(String fatheridcard) {
		this.fatheridcard = fatheridcard;
	}
	
	@Length(min=0, max=64, message="父亲文化程度长度必须介于 0 和 64 之间")
	@ExcelField(title="父亲文化程度", align=2, sort=30)
	public String getFatherculture() {
		return fatherculture;
	}

	public void setFatherculture(String fatherculture) {
		this.fatherculture = fatherculture;
	}
	
	@Length(min=0, max=64, message="父亲工作单位长度必须介于 0 和 64 之间")
	@ExcelField(title="父亲工作单位长度", align=2, sort=31)
	public String getFatherworkunit() {
		return fatherworkunit;
	}

	public void setFatherworkunit(String fatherworkunit) {
		this.fatherworkunit = fatherworkunit;
	}
	
	@Length(min=0, max=64, message="父亲电话长度必须介于 0 和 64 之间")
	@ExcelField(title="父亲电话", align=2, sort=32)
	public String getFatherphone() {
		return fatherphone;
	}

	public void setFatherphone(String fatherphone) {
		this.fatherphone = fatherphone;
	}
	
	@Length(min=0, max=64, message="母亲姓名长度必须介于 0 和 64 之间")
	@ExcelField(title="母亲姓名", align=2, sort=33)
	public String getMothername() {
		return mothername;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
	}
	
	@Length(min=0, max=64, message="母亲身份证号长度必须介于 0 和 64 之间")
	@ExcelField(title="母亲身份证号", align=2, sort=34)
	public String getMotheridcard() {
		return motheridcard;
	}

	public void setMotheridcard(String motheridcard) {
		this.motheridcard = motheridcard;
	}
	
	@Length(min=0, max=64, message="母亲文化程度长度必须介于 0 和 64 之间")
	@ExcelField(title="母亲文化程度", align=2, sort=35)
	public String getMotherculture() {
		return motherculture;
	}

	public void setMotherculture(String motherculture) {
		this.motherculture = motherculture;
	}
	
	@Length(min=0, max=64, message="母亲工作单位长度必须介于 0 和 64 之间")
	@ExcelField(title="母亲工作单位", align=2, sort=36)
	public String getMotherworkunit() {
		return motherworkunit;
	}

	public void setMotherworkunit(String motherworkunit) {
		this.motherworkunit = motherworkunit;
	}
	
	@Length(min=0, max=64, message="母亲电话长度必须介于 0 和 64 之间")
	@ExcelField(title="母亲电话", align=2, sort=37)
	public String getMotherphone() {
		return motherphone;
	}

	public void setMotherphone(String motherphone) {
		this.motherphone = motherphone;
	}
	
	@Length(min=0, max=64, message="家庭人口长度必须介于 0 和 64 之间")
	@ExcelField(title="家庭人口", align=2, sort=38)
	public String getFamliynum() {
		return famliynum;
	}

	public void setFamliynum(String famliynum) {
		this.famliynum = famliynum;
	}
	
	@Length(min=1, max=1, message="是否办理助学贷款长度必须介于 1 和 1 之间")
	@ExcelField(title="是否办理助学贷款", align=2, sort=39)
	public String getIsblzxdk() {
		return isblzxdk;
	}

	public void setIsblzxdk(String isblzxdk) {
		this.isblzxdk = isblzxdk;
	}
	
	@Length(min=1, max=100, message="电话长度必须介于 1 和 100 之间")
	@ExcelField(title="电话", align=2, sort=40)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=1, message="是否参军及参军时间长度必须介于 1 和 1 之间")
	@ExcelField(title="是否参军", align=2, sort=41)
	public String getIspartjun() {
		return ispartjun;
	}

	public void setIspartjun(String ispartjun) {
		this.ispartjun = ispartjun;
	}
	
}