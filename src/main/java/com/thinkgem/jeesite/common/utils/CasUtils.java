package com.thinkgem.jeesite.common.utils;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;


/**
 * Created with Eclipse.
 * User: 王泽宇
 * Date: 16-11-12
 * Time: 下午10:02
 * 工具类
 */
public class CasUtils {
	
	/**
	   * 根据内容类型判断文件扩展名
	   *
	   * @param contentType 内容类型
	   * @return
	   */
	 public static String getFileexpandedName(String contentType) {
	    String fileEndWitsh = "";
	    if ("image/jpeg".equals(contentType))
	      fileEndWitsh = ".jpg";
	    else if ("audio/mpeg".equals(contentType))
	      fileEndWitsh = ".mp3";
	    else if ("audio/amr".equals(contentType))
	      fileEndWitsh = ".amr";
	    else if ("video/mp4".equals(contentType))
	      fileEndWitsh = ".mp4";
	    else if ("video/mpeg4".equals(contentType))
	      fileEndWitsh = ".mp4";
	    return fileEndWitsh;
	 }
	
	
	//自定义过滤规则，将文本中出现的四字节UTF-8字符过滤或转化为自定义类型
	public static String convertUTF8_MB4(String nickName){
		byte[] b_text = nickName.getBytes();
		for (int i = 0; i < b_text.length; i++)
		{
		   if((b_text[i] & 0xF8)== 0xF0){
		       for (int j = 0; j < 4; j++) {
		        b_text[i+j] =0x5f;
		       }
		       i+=3;
		   }
		}
		String ret = new String(b_text);
		System.out.println("emoj表情:"+ret);
		return ret;
	}

    /**
     * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
     * 确的浮点数运算，包括加减乘除和四舍五入。
     */
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    private static Map<String, Object> cache = new HashMap<String, Object>();
    
    
    //两个字符串大小比较（确定字符串是数字）
    public static boolean getMaxNumber(String one,String two) {
    	Integer oneInt = Integer.valueOf(one);
    	Integer twoInt = Integer.valueOf(two);
    	if(oneInt.intValue() > twoInt.intValue()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    //验证数字
    public static boolean isNumeric(String str){ 
 	   Pattern pattern = Pattern.compile("[0-9]*"); 
 	   Matcher isNum = pattern.matcher(str);
 	   if( !isNum.matches() ){
 	       return false; 
 	   } 
 	   return true; 
 	}
    
	/**
	 * 是否
	 */
    public static String convertSure(Boolean param){
		if(param){
			return "是";
		}else{
			return "否";
		}
	}
	
	/**
	 * 性别
	 */
	public static String converGender(Boolean param){
		if(param){
			return "男";
		}else{
			return "女";
		}
	}
    
    /**
     * 取得目录下所有文件
     * @param path
     */
    public static File[] traverseFolder2(String path) {

    	File[] files = null;
        File file = new File(path);
        if (file.exists()) {
        	files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return files;
    }
    
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
      // 判断文件夹是否存在
      public static void judeDirExists(File file) {
           if (file.exists()) {
                 if (file.isDirectory()) {
                     System.out.println("dir exists");
                 } else {
                    System.out.println("the same name file exists, can not create dir");
                 }
             } else {
                 System.out.println("dir not exists, create it ...");
                 file.mkdir();
             }   
        }
    
      // 判断文件是否存在
      public static void judeFileExists(File file) {
             if (file.exists()) {
                 System.out.println("file exists");
             } else {
                 System.out.println("file not exists, create it ...");
                 try {
                     file.createNewFile();
                 } catch (IOException e) {
                    e.printStackTrace();
                 }
            }
     
        }
  

    public static void put(String key, Object value) {
        cache.put(key, value);
    }
 
    
    /**
     * 验证邮箱地址
     * @param number
     * @return
     */
    public static boolean emailAddress(String address)
	{
    	 String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    	
    	 boolean isMatch = Pattern.matches(regex, address);
		 return isMatch; 
	}
    
    /**
     * 验证手机号码
     * @param number
     * @return
     */
    public static boolean phoneNumber(String number)
	{
		String rgx = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		
		return isCorrect(rgx, number);
	}
	
	/**
	 * 验证身份证号码
	 * @param number
	 * @return
	 */
	public static boolean idCardNumber(String number)
	{
		String rgx = "^\\d{15}|^\\d{17}([0-9]|X|x)$";
		
		return isCorrect(rgx, number);
	}
	
	//正则验证
	private static boolean isCorrect(String rgx, String res)
	{
		Pattern p = Pattern.compile(rgx);
		
		Matcher m = p.matcher(res);
		
		return m.matches();
	}

    public static Object get(String key) {
        return cache.get(key);
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 百分号
     * @param value 值
     * @param scale 精度
     * @return
     */
    public static  String percent(double value,int scale){
        NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
        format.setMinimumFractionDigits(scale);// 设置小数位
        return format.format(value);
    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的类型转换(Float)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertsToFloat(double v){
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v){
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }
    /**
     * 提供精确的类型转换(Long)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v){
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }
    /**
     * 返回两个数中大的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }
    /**
     * 返回两个数中小的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }
    /**
     * 精确对比两个数字
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * 两个时间之间的天数
     * 格式：2014-02-02
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getRangerBetweenDate(String startDate,String endDate){
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(endDate);
            Date date2 = ft.parse(startDate);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quot;
    }

    //返回当前年月日
    public static String getNowDate()
    {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    //返回当前年份
    public static int getYear()
    {
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    //返回当前月份
    public static int getMonth()
    {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    //判断闰年
    public static boolean isLeap(int year)
    {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    //返回当月天数
    public static int getDays(int year, int month)
    {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //返回当月星期天数
    public static int getSundays(int year, int month)
    {
        int sundays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar setDate = Calendar.getInstance();
        //从第一天开始
        int day;
        for (day = 1; day <= getDays(year, month); day++)
        {
            setDate.set(Calendar.DATE, day);
            String str = sdf.format(setDate.getTime());
            if (str.equals("星期日"))
            {
                sundays++;
            }
        }
        return sundays;
    }

    
    /**
     * 是否同一天
     */
    public static boolean isSameDay(long date1,long date2) {
    	Date data1 = new Date(date1);
    	Date data2 = new Date(date2);
    	return isSameDay(data1,data2);
    }
    
    /**
     * 是否同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {  
        Calendar calDateA = Calendar.getInstance();  
        calDateA.setTime(date1);  
      
        Calendar calDateB = Calendar.getInstance();  
        calDateB.setTime(date2);  
      
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)  
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)  
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB  
                        .get(Calendar.DAY_OF_MONTH);  
    }  

    /**
     * 得到昨天的时间
     * @return
     */
    public static String getYesterdayDate(){
        Calendar calendar =  Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
        return yesterday;
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     * */
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

  

    /**
     * 克隆一个集合到另外一个集合
     * @param originalArray 原来的
     * @param newArray 新的
     * @param <T>
     * @return
     */
    public static <T> void cloneArray( List<T> originalArray,List<T> newArray){
        if(null == originalArray|| null == newArray)return;
        for(T array:originalArray){
            newArray.add(array);
        }
    }




   

 

    /**
     * 获取类的字段实例
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    private static Field getClassField(Class clazz, String fieldName) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String fieldName)，但是，失败了
            //getFields()获得某个类的所有的公共（public）的字段，包括父类。
            // getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，
            //但是不包括父类的申明字段。
            if (field.getName().equals(fieldName)) {
                return field;// define in this class
            }
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {// 简单的递归一下
            return getClassField(superclass, fieldName);
        }
        return null;
    }

    /**
     * get方法名
     * @param propertyName
     * @return
     */
    public static String getGetterName(String propertyName) {
        String method = "get" + propertyName.substring(0, 1).toUpperCase()
                + propertyName.substring(1);
        return method;
    }

 

    /**
     * md5验证
     * @param data
     * @return
     * @throws Exception
     */
    public static String md5Validate(String data)throws Exception{
        StringBuilder sb =new StringBuilder();
        MessageDigest md= MessageDigest.getInstance("md5");//sha
        byte[] bs=data.getBytes();
        byte[] mb=md.digest(bs);
        for(int i=0;i<mb.length;i++){
            //  **** **** **** **** **** **** **** ****
            //& 0000 0000 0000 0000 0000 0000 1111 1111
            //  0000 0000 0000 0000 0000 0000 **** ****
            //0~255
            //1~0x1 2~0x2 ... 9~0x9  10 ~0xa 11~ 0xb ... 15 ~0xf  16 ~0x10
            int v =mb[i]&0xFF;//0~255
            if(v<16){
                sb.append("0");
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }




    /**
     * 将字符串首字母转换为大写
     * @param beanName
     * @return
     */
    public static String toUpperCaseFirstChar(String beanName){
        String[] beanNames = beanName.split("\\.");
        String lastString = beanNames[beanNames.length-1];
        char[] firstChar = new char[1];
        firstChar[0] = lastString.charAt(0);
        String firstString  = new String(firstChar);
        String result = lastString.replaceFirst(firstString,firstString.toUpperCase());
        return result;
    }


    /**
     * 将字符串首字母转换为小写
     * @param beanName
     * @return
     */
    public static String toLowerCaseFirstChar(String beanName){
        String[] beanNames = beanName.split("\\.");
        String lastString = beanNames[beanNames.length-1];
        char[] firstChar = new char[1];
        firstChar[0] = lastString.charAt(0);
        String firstString  = new String(firstChar);
        String result = lastString.replaceFirst(firstString,firstString.toLowerCase());
        return result;
    }

    /**
     * 时间比较大小
     * @param before
     * @param after
     * @return
     * result==0 before与after相同
     * result<0 before比after早
     * result>0 before比after晚
     */
    public static int compareTime(Date before,Date after) throws Exception{
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1= Calendar.getInstance();
        Calendar c2= Calendar.getInstance();
        c1.setTime(before);
        c2.setTime(after);
        int result=c1.compareTo(c2);
        return  result;
    }

    /** 输出格式: 2006-4-16 */
    private static DateFormat dfDefault = DateFormat.getDateInstance(DateFormat.DEFAULT,Locale.CHINA);

    /** 输出格式: 2006年4月16日 星期六 */
    private static DateFormat dfFUll = DateFormat.getDateInstance(DateFormat.FULL);

    /** 输出格式: 06-4-16*/
    private static DateFormat dfSHORT =  DateFormat.getDateInstance(DateFormat.MEDIUM);

    /** 输出格式: 2006-01-01 00:00:00 */
    private static DateFormat sdfWithHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 输出格式: 2006-01-01*/
    private static DateFormat sdfWithYMD = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 输出格式: 2006-4-16
     * @param date
     * @return
     */
    public static String convertDate2DefaultString(Date date){
        return dfDefault.format(date);
    }

    /**
     * 输出格式: 2006年4月16日 星期六
     * @param date
     * @return
     */
    public static String convertDate2FullString(Date date){
        return  dfFUll.format(date);
    }

    /**
     * 输出格式: 06-4-16
     * @param date
     * @return
     */
    public static String convertDate2ShortString(Date date){
        return dfSHORT.format(date);
    }


    /**
     * 输出格式: 2006-01-01 00:00:00
     * @param date
     * @return
     */
    public static String convertDate2HMSString(Date date){
        return sdfWithHMS.format(date);
    }

    /**
     *输出格式: 2006-01-01
     * @param date
     * @return
     */
    public static String convertDate2YMDString(Date date){
        return sdfWithYMD.format(date);
    }

    /**
     * 输入格式 yyyy-MM-dd hh:mm:ss
     *输出格式: Sat Jan 04 09:33:33 CST 2014
     * @param date
     * @return
     * @throws Exception
     */
    public static Date convertString2HMSDate(String date)throws Exception{
        return  sdfWithHMS.parse(date);
    }

    /**
     * 输入格式 yyyy-MM-dd
     *输出格式: Sat Jan 04 09:33:33 CST 2014
     * @param date
     * @return
     * @throws Exception
     */
    public static Date convertString2YMDDate(String date)throws Exception{
        return  sdfWithYMD.parse(date);
    }

    
    // StringUtils
    /**
	 * 获得随机字符串
	 * 
	 * @param n 字符数
	 * @return 随机字符串
	 */
	public static String getRandomString(int n) {
		String[] strings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "0"};
		Random random;
		StringBuffer strBuf = new StringBuffer();

		random = new Random();
		for (int i = 0; i < n; i++) {
			strBuf.append(strings[random.nextInt(strings.length)]);
		}

		return strBuf.toString();
	}
	
	public static String trim(String str) {
		if (str == null) {
			str = "";
		} else {
			str = str.trim();
		}
		if (str.length() == 0) {
			return str;
		}

		if (str.charAt(0) == '"') {
			str = str.substring(1);
		}

		if (str.charAt(str.length() - 1) == '"') {
			str = str.substring(0, str.length() - 1);
		}

		return str;
	}

	public static int[] getIntArray(String str, String sep) {
		String[] prop = getStringList(str, sep);
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < prop.length; i++) {
			try {
				int r = Integer.parseInt(prop[i]);
				tmp.add(r);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		int[] ints = new int[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			ints[i] = tmp.get(i);
		}
		return ints;
	}
	
	public static String[] getStringList(String str, String sep) {
		str = trim(str);
		return str.split(sep);
	}
	
	public static String[] getStringList(String str) {
		str = trim(str);
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		String sep = ",";
		if (str.indexOf(':') >= 0) {
			sep = ":";
		}
		return str.split(sep);
	}
	
	/**
	 * 产生随机数字串
	 * 
	 * @param n 字符串长度
	 * @return
	 */
	public static String getRandomDigitalString(int n) {
		Random random = new Random();
		String value = String.valueOf(random.nextInt((int) Math.pow(10, n)));
		// 前补0
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < n; i++) {
			buf.append("0");
		}
		buf.replace(n - value.length(), n, value);
		return buf.toString();
	}

	/**
	 * double转换为字符串，保留小数点后round位，不足round位的忽略
	 * 
	 * @param value
	 * @param round
	 * @return
	 */
	public static String getDoubleRound(Double value, int round) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(round);
		return formatter.format(value);
	}


	/**
	 * 返回一个object数组字符串，","分隔
	 * 
	 * @param objects
	 * @return
	 */
	public static String getArrayString(Object[] objects) {
		if (objects == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			sb.append(objects[i]);
			if (i < objects.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 从content中查找第一个匹配的字符串
	 * 
	 * @param content
	 * @param regEx
	 * @return
	 */
	public static String parse(String content, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}

	public static double sjqz(double d) {
		double td = Math.floor(d);
		return td + ((new Random().nextDouble() < d - td) ? 1 : 0);
	}

	/**
	 * 包装commons lang StringUtils isEmpty
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	
	/** 
	 * 获得本月的开始时间 
	 * 
	 * @return 
	 */  
	public static Date getCurrentMonthStartTime() {  
		Calendar c = Calendar.getInstance();  
		Date now = null;  
		try {  
			c.set(Calendar.DATE, 1);  
			now = sdfWithYMD.parse(sdfWithYMD.format(c.getTime()));  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return now;  
	}  

	/** 
	 * 本月的结束时间 
	 * 
	 * @return 
	 */  
	public static Date getCurrentMonthEndTime() {  
		Calendar c = Calendar.getInstance();  
		Date now = null;  
		try {  
			c.set(Calendar.DATE, 1);  
			c.add(Calendar.MONTH, 1);  
			c.add(Calendar.DATE, -1);  
			now = sdfWithHMS.parse(sdfWithYMD.format(c.getTime()) + " 23:59:59");  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return now;  
	}  

	/** 
	 * 当前年的开始时间 
	 * 
	 * @return 
	 */  
	public static Date getCurrentYearStartTime() {  
		Calendar c = Calendar.getInstance();  
		Date now = null;  
		try {  
			c.set(Calendar.MONTH, 0);  
			c.set(Calendar.DATE, 1);  
			now = sdfWithYMD.parse(sdfWithYMD.format(c.getTime()));  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return now;  
	}  

	/** 
	 * 当前年的结束时间 
	 * 
	 * @return 
	 */  
	public static Date getCurrentYearEndTime() {  
		Calendar c = Calendar.getInstance();  
		Date now = null;  
		try {  
			c.set(Calendar.MONTH, 11);  
			c.set(Calendar.DATE, 31);  
			now = sdfWithHMS.parse(sdfWithYMD.format(c.getTime()) + " 23:59:59");  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return now;  
	} 
	
	/**
     * 指定日期加上天数后的日期
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusDay(int num,String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }
    
    /**
         * 当前日期加上天数后的日期
         * @param num 为增加的天数
         * @return
         */
        public static Date plusDay2(int num){
            Date d = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currdate = format.format(d);
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
            d = ca.getTime();
            return d;
        }

}