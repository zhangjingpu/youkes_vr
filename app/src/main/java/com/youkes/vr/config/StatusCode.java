/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.config;

import java.util.HashMap;
import java.util.Map;

public class StatusCode {
	
	public static int Error_Unkown=-1;
	public static int Error_File_Upload_Size_Large=-2;
	
	//success only one
	public static int Api_OK=0;
	public static int Api_Not_Logined=1;
	public static int Api_Too_Quick=2;

	public static int Api_QQ_First_Login = 9;
	
	//public static int Regist_OK=100;
	public static int Regist_F_Unkown=101;
	public static int Regist_F_UserIdEmpty=102;
	public static int Regist_F_UserIdShort=103;
	public static int Regist_F_UserIdLong=104;
	public static int Regist_F_UserIdRegex=105;
	
	public static int Regist_F_UserPwdEmpty=111;
	public static int Regist_F_UserPwdShort=112;
	public static int Regist_F_UserPwdLong=113;
	public static int Regist_F_UserPwdRegex=114;
	
	public static int Regist_F_RePwdEmpty=121;
	public static int Regist_F_RePwdNotSame=122;
	public static int Regist_F_UserIdExist=123;
	public static int Regist_F_DbError=124;
	public static int Regist_F_VerifyError=125;
	public static int Regist_F_VerifyAlready=126;

	public static int Regist_Error_IP_Limit = 128;

	public static final int Login_F_Unkown=201;
	public static final int Login_F_UserIdEmpty=202;
	public static final int Login_F_UserIdShort=203;
	public static final int Login_F_UserIdLong=204;
	public static final int Login_F_UserIdRegex=205;
	
	public static final int Login_F_UserPwdEmpty=211;
	public static final int Login_F_UserPwdShort=212;
	public static final int Login_F_UserPwdLong=213;
	public static final int Login_F_UserPwdRegex=214;
	
	public static final int Login_F_UserNotExist=221;
	public static final int Login_F_PwdWrong=222;
	public static final int Login_F_SessionCreate = 223;
	
	public static final int Search_F_Unkown=301;
	
	
	public static final int Share_Like_User_Not_Login=401;
	public static final int Share_Like_Id_Not_Right=402;
	public static final int Share_Like_Type_Not_Right=403;
	public static final int Share_Like_Error_Db=404;
	
	
	public static final int Add_Friend_User_Not_Login=501;
	public static final int Add_Friend_From_Not_Exist=502;
	public static final int Add_Friend_To_Empty=503;
	public static final int Add_Friend_To_Not_Exist=504;
	public static final int Add_Friend_Error_Db=505;
	public static final int Add_Friend_Too_Many=506;
	
	public static final int Remove_Friend_User_Not_Login=601;
	public static final int Remove_Friend_From_Not_Exist=602;
	public static final int Remove_Friend_To_Not_Exist=603;
	public static final int Remove_Friend_Error_Db=604;
	public static final int Remove_Friend_To_Empty=605;
	
	
	public static final int Upload_File_Not_Exist=701;
	public static final int Upload_File_Error_Unkown=702;
	
	
	
	
	public static final int Set_User_Field_Error_Unkown=801;
	public static final int Set_User_Field_Error_Not_Login=802;
	public static final int Set_User_Field_Error_Db=803;
	public static final int Set_User_Field_Error_Empty = 804;
	public static final int Set_User_Field_Error_Too_Long = 805;
	

	public static final int User_Sign_Error_Unkown=901;
	public static final int User_Sign_Error_Not_Login=902;
	public static final int User_Sign_Error_Db=903;
	

	public static final int Upload_Photo_Error_Unkown=1001;
	public static final int Upload_Photo_Not_Exist=1002;
	public static final int Upload_Photo_Not_Logined=1003;
	public static final int Upload_Photo_Image_Not_Allowed=1004;
	
	
	public static final int Weibo_Add_Error_Unkown=1101;
	public static final int Weibo_Add_Error_Not_Logined=1102;
	public static final int Weibo_Add_No_Content=1103;
	public static final int Weibo_Add_Content_Short=1104;
	public static final int Weibo_Add_Content_Long=1105;
	public static final int Weibo_Add_Error_Db=1106;
	public static final int Weibo_Upload_Not_Exist=1107;
	public static final int Weibo_Upload_Not_Allowed=1108;
	public static final int Weibo_Upload_Error_Unkown=1109;
	public static final int Weibo_Add_Last_Same=1110;
	
	
	public static final int Weibo_Remove_Error_Unkown=1201;
	public static final int Weibo_Remove_Error_Not_Logined=1202;
	public static final int Weibo_Remove_No_Id=1203;
	
	//public static final int Weibo_Add_No_Content=1103;
	
	public static final int User_Set_Loc_Not_Logined=1301;
	public static final int User_Set_Loc_Lat_Lng_Not_Right=1302;
	
	
	public static final int File_Upload_Error_Unkown=1401;
	public static final int File_Upload_Error_Type=1402;
	
	
	public static final int Share_Album_Add_Error_Unkown=1501;
	public static final int Share_Album_Add_Error_Not_Logined=1502;
	public static final int Share_Album_Add_No_Content=1503;
	public static final int Share_Album_Add_Content_Short=1504;
	public static final int Share_Album_Add_Content_Long=1505;
	public static final int Share_Album_Add_Error_Db=1506;
	public static final int Share_Album_Upload_Not_Exist=1507;
	public static final int Share_Album_Upload_Not_Allowed=1508;
	public static final int Share_Album_Upload_Error_Unkown=1509;
	public static final int Share_Album_Add_Last_Same=1510;
	
	
	public static final int Group_Create_Error_Unkown=1601;
	public static final int Group_Create_Error_Not_Logined=1602;
	public static final int Group_Create_Error_UserId_Empty=1603;
	public static final int Group_Create_Error_AccessKey_Empty=1604;
	public static final int Group_Create_Error_Name_Empty=1605;
	public static final int Group_Create_Error_Name_Short=1606;
	public static final int Group_Create_Error_Name_Long=1607;
	
	
	private static final Map<Integer, String> hashMap;
	
    static
    {
    	hashMap = new HashMap<Integer, String>();
    	
    	hashMap.put(Error_File_Upload_Size_Large, "上传文件过大,1MB以内");
    	
    	hashMap.put(Api_OK, "操作成功");
    	hashMap.put(Api_Too_Quick, "用户操作过快");
    	hashMap.put(Api_Not_Logined, "您必须登录以完成操作");
    	
    	
    	hashMap.put(Regist_F_Unkown, "未知注册错误");
    	hashMap.put(Regist_F_UserIdEmpty, "用户名不能为空");
    	hashMap.put(Regist_F_UserIdShort, "用户名至少6个字符");
    	hashMap.put(Regist_F_UserIdLong, "用户名至多15个字符");
    	hashMap.put(Regist_F_UserIdRegex, "用户名只能为数字和小写字母");
    	
    	hashMap.put(Regist_F_UserPwdEmpty, "密码不能为空");
    	hashMap.put(Regist_F_UserPwdShort, "密码至少6个字符");
    	hashMap.put(Regist_F_UserPwdLong, "密码至多25个字符");
    	hashMap.put(Regist_F_UserPwdRegex, "密码只能为数字和字母和特殊字符");
    	
    	hashMap.put(Regist_F_RePwdEmpty, "确认密码不能为空");
    	hashMap.put(Regist_F_RePwdNotSame, "确认密码与密码必须相同");
    	hashMap.put(Regist_F_UserIdExist, "用户名已经存在");
    	hashMap.put(Regist_F_DbError, "注册数据库错误");
    	hashMap.put(Regist_F_VerifyError, "注册验证码不正确");
    	hashMap.put(Regist_F_VerifyAlready, "注册验证码已过期");
    	
    	hashMap.put(Login_F_Unkown, "未知登录错误");
    	hashMap.put(Login_F_UserIdEmpty, "用户名不能为空");
    	hashMap.put(Login_F_UserIdShort, "用户名至少6个字符");
    	hashMap.put(Login_F_UserIdLong, "用户名至多15个字符");
    	hashMap.put(Login_F_UserIdRegex, "用户名只能为数字和小写字母");
    	
    	hashMap.put(Login_F_UserPwdEmpty, "密码不能为空");
    	hashMap.put(Login_F_UserPwdShort, "密码至少6个字符");
    	hashMap.put(Login_F_UserPwdLong, "密码至多25个字符");
    	hashMap.put(Login_F_UserPwdRegex, "密码只能为数字和字母和特殊字符");
    	hashMap.put(Login_F_UserNotExist, "用户不存在");
    	hashMap.put(Login_F_PwdWrong, "用户密码错误");
    	
    	hashMap.put(Search_F_Unkown, "搜索未知错误");
    	
    	hashMap.put(Share_Like_User_Not_Login, "收藏必须先登录");
    	hashMap.put(Share_Like_Id_Not_Right, "收藏对象ID不正确");
    	hashMap.put(Share_Like_Type_Not_Right, "收藏对象类型不正确");
    	hashMap.put(Share_Like_Error_Db, "收藏数据库错误");
    	
    	hashMap.put(Add_Friend_User_Not_Login, "添加好友必须先登录");//Add_Friend_From_Not_Exist
    	hashMap.put(Add_Friend_From_Not_Exist, "添加好友时发起者不存在");
    	hashMap.put(Add_Friend_To_Empty, "添加好友对象为空");
    	hashMap.put(Add_Friend_To_Not_Exist, "添加好友时对象不存在");
    	hashMap.put(Add_Friend_Error_Db, "添加好友时数据库错误");
    	hashMap.put(Add_Friend_Too_Many, "最多只能添加32个好友");
    	
    	hashMap.put(Remove_Friend_User_Not_Login, "删除好友必须先登录");//Add_Friend_From_Not_Exist
    	hashMap.put(Remove_Friend_From_Not_Exist, "删除好友时发起者不存在");
    	hashMap.put(Remove_Friend_To_Empty, "删除好友对象为空");
    	hashMap.put(Remove_Friend_To_Not_Exist, "删除好友时对象不存在");
    	hashMap.put(Remove_Friend_Error_Db, "删除好友时数据库错误");
    	
    	
    	hashMap.put(User_Sign_Error_Unkown, "签名时发生未知错误");
    	hashMap.put(User_Sign_Error_Not_Login, "签名必须先登录");
    	
    	hashMap.put(Upload_Photo_Error_Unkown, "上传用户头像时发生未知错误");
    	hashMap.put(Upload_Photo_Not_Exist, "上传用户头像不存在");
    	hashMap.put(Upload_Photo_Not_Logined, "上传用户头像未登录");
    	hashMap.put(Upload_Photo_Image_Not_Allowed, "上传用户头像格式不正确");
    	
    	
    	hashMap.put(Set_User_Field_Error_Unkown, "设置用户域发生未知错误");
    	hashMap.put(Set_User_Field_Error_Not_Login, "设置用户域时未登录");
    	hashMap.put(Set_User_Field_Error_Db, "设置用户域时数据库错误");
    	hashMap.put(Set_User_Field_Error_Empty, "设置用户域为空");
    	hashMap.put(Set_User_Field_Error_Too_Long, "设置的用户域过长");
    	
    	
    	hashMap.put(Weibo_Add_Error_Unkown, "添加微博未知错误");
    	hashMap.put(Weibo_Add_No_Content, "添加微博时内容为空");
    	hashMap.put(Weibo_Add_Error_Not_Logined, "添加微博需登录");
    	hashMap.put(Weibo_Add_Content_Short, "添加微博内容过短");
    	hashMap.put(Weibo_Add_Content_Long, "添加微博内容过长");
    	
    	hashMap.put(Weibo_Upload_Not_Exist, "微博上传图片不存在");
    	hashMap.put(Weibo_Upload_Not_Allowed, "添加微博上传图片不允许");
    	hashMap.put(Weibo_Upload_Error_Unkown, "添加微博上传图片未知错误");
    	hashMap.put(Weibo_Add_Last_Same, "添加微博重复");
    	
    	
    	
    	
    	hashMap.put(Weibo_Remove_Error_Unkown, "删除微博未知错误");
    	hashMap.put(Weibo_Remove_Error_Not_Logined, "删除微博需登录");
    	hashMap.put(Weibo_Remove_No_Id, "删除微博号不存在");
    	
    	hashMap.put(User_Set_Loc_Not_Logined, "设置用户位置未登录");
    	hashMap.put(User_Set_Loc_Lat_Lng_Not_Right, "设置用户位置经纬度错误");
    	
    	
    	hashMap.put(File_Upload_Error_Unkown, "文件上传未知错误");
    	hashMap.put(File_Upload_Error_Type, "文件上传类型错误");
    	
    	
    	hashMap.put(Group_Create_Error_Unkown, "创建群组未知错误");
    	hashMap.put(Group_Create_Error_Not_Logined, "创建群组未登录");
    	hashMap.put(Group_Create_Error_UserId_Empty, "创建群组用户名为空");
    	hashMap.put(Group_Create_Error_AccessKey_Empty, "创建群组访问密钥为空");
    	hashMap.put(Group_Create_Error_Name_Empty, "创建群组名为空");
    	hashMap.put(Group_Create_Error_Name_Short, "创建群组名过短");
    	hashMap.put(Group_Create_Error_Name_Long, "创建群组名过长");
    	
    	
    	/*
    	 * public static final int Weibo_Add_Error_Unkown=1101;
			public static final int Weibo_Add_No_Content=1102;
    	 */
    	//public static final int Set_User_Field_Error_Unkown=801;
    	//public static final int Set_User_Field_Error_Not_Login=802;
    	//public static final int Set_User_Field_Error_Db=803;
    	
    	
    }
    
	public static String getMsg(int status) {
		if(hashMap.containsKey(status)){
		return hashMap.get(status);
		}
		
		return "未知错误:"+status;
	}
	
	
	
	
}
