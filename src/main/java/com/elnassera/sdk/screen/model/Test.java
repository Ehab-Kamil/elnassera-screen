package com.elnassera.sdk.screen.model;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.sun.jna.Native;

/*
 * read before use:
 * 1 About the possible reasons why the T card cannot be searched：
 *   1.1. Express is turned on, causing the udp port to be occupied, please close the express software and try again
 *   1.2. SDK and T card are not in the same local area network, you can try to specify ip search, see how to use: https://docapi.vnnox
 *   .com/web/#/7?page_id=609

 * 2 About the possible problems and solutions of using the sdk dynamic library：
 *
 *     2.1. Under linux, if lib***.so file too short occurs, this kind of error may be that the soft link is invalid. You can try to
 *     delete the original file and try ln -s to regenerate the soft link. For example: ./libcurl.so: file too short, first rm libcurl
 *     .so, then ln -s libcurl.so.4.3.0 libcurl.so.
 *     2.2. For java projects, when using JNA to load the so library, please use the absolute path. for example:
 *     //winodws
 *     ViplexCore instance = (ViplexCore) Native.loadLibrary("D:\\ViplexCore3.3.0.01_x64\\bin\\viplexcore.dll",ViplexCore.class);
 *     //or linux
 *     ViplexCore instance = (ViplexCore) Native.loadLibrary("home/user/desktop/ViplexCore_3.3.0.01_CentOS/bin/libviplexcore.so",
 *     ViplexCore.class);
 *
 *     2.3. For java projects, if you want to use a relative path, please add the sdk dynamic library path to the classpath environment
 *     variable. There are the following methods (you can also search for related tutorials to add them), for example:
 *       2.3.1. Command line use:         javac -cp "C:\Users\HiWin10\Downloads\ViplexCore3.3.0.01_x64\bin"   (详见SDK demo/java/下runDemo
 *       .sh或者runDemo.bat文件)
 *       2.3.2. Import environment variables like:   export CLASSPATH=$CLASSPATH:home/user/ViplexCore3.3.0.01_x64/
 *       2.3.3. For the springboot project: java -Xbootclasspath/a:/home/nova/novasdk/
 * */
public class Test {

	static Boolean g_bAPIReturn = false;
	static int g_code = 0;
	static String g_sn = "MZSA71304W0040003623"; //BZSA07313J0350000997

	static void waitAPIReturn() throws InterruptedException {
		while(!g_bAPIReturn) {
			Thread.sleep(1000);
		}
		g_bAPIReturn = false;
	}

	public static void testApi() throws InterruptedException {
		System.setProperty("jna.encoding", "UTF-8");
		ViplexCore instance = (ViplexCore) Native.loadLibrary("D:\\Novastar\\sdk\\bin\\viplexcore", ViplexCore.class);
		//当需要集成到项目中时，请修改上面加载库的位置为你下载的sdk库的绝对路径，windows下如：
		//ViplexCore instance = (ViplexCore) Native.loadLibrary("D:\\ViplexCore3.3.0.01_x64\\bin\\viplexcore.dll",ViplexCore.class);

		//当需要集成到项目中时，请修改此处加载库的位置为你下载的sdk库的绝对路径，linux下如：
		//ViplexCore instance = (ViplexCore) Native.loadLibrary("home/user/desktop/ViplexCore_3.3.0.01_CentOS/bin/libviplexcore.so",
		// ViplexCore.class);
		ViplexCore.CallBack callBack = new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				// TODO Auto-generated method stub
				g_code = code;
				String strCode = "\nViplexCore Demo code:" + code;
				String strData = "\nViplexCore Demo data:" + data;
				System.out.println(strCode);
				System.out.println(strData);
				g_bAPIReturn = true;
			}

		};

		String rootDir = System.getProperty("user.dir") + "/temp";
		rootDir = rootDir.replaceAll("\\\\", "/");
		String createProgram = "{\"name\":\"Demo\",\"width\":500,\"height\":500,\"tplID\":1,\"winInfo\":{\"height\":100,\"width\":100,"
				+ "\"left\":0,\"top\":0,\"zindex\":0,\"index\":0}}";
		String editProgram = "{\"programID\":1,\"pageID\":1,\"pageInfo\":{\"name\":\"节目\",\"widgetContainers\":[{\"audioGroup\":\"\","
				+ "\"backgroundColor\":\"#00000000\",\"backgroundDrawable\":\"\",\"contents\":{\"widgetGroups\":[],\"widgets\":[{\"id\":1,"
				+ "\"enable\":true,\"repeatCount\":1,\"layout\":{\"y\":\"0\",\"height\":\"100%\",\"x\":\"0\",\"width\":\"100%\"},"
				+ "\"backgroundColor\":\"#00000000\",\"backgroundDrawable\":\"\",\"backgroundMusic\":\"\",\"zOrder\":0,"
				+ "\"displayRatio\":\"FULL\",\"outAnimation\":{\"type\":0,\"duration\":0},"
				+ "\"dataSource\":\"62ea7e60c493c1f860ae256eef8ec89c.png\",\"type\":\"PICTURE\",\"constraints\":[{\"cron\":[],"
				+ "\"endTime\":\"4017-12-30T23:59:59Z+8:00\",\"startTime\":\"1970-01-01T00:00:00Z+8:00\"}],"
				+ "\"border\":{\"borderThickness\":\"2px,3px,5%,6\",\"style\":0,\"backgroundColor\":\"#ff000000\",\"name\":\"border\","
				+ "\"cornerRadius\":\"2%\",\"effects\":{\"headTailSpacing\":\"\",\"isHeadTail\":false,\"speedByPixelEnable\":true,"
				+ "\"speed\":0,\"animation\":\"CLOCK_WISE\"}},\"inAnimation\":{\"type\":0,\"duration\":1000},\"duration\":3605000,"
				+ "\"name\":\"test.png\",\"originalDataSource\":\"./test.png\",\"functionStorage\":\"\","
				+ "\"isSupportSpecialEffects\":false}]},\"enable\":true,\"id\":1,\"itemsSource\":\"\",\"layout\":{\"height\":\"1.0\","
				+ "\"width\":\"1.0\",\"x\":\"0.0\",\"y\":\"0.0\"},\"name\":\"widgetContainers1\",\"pickCount\":0,\"pickPolicy\":\"ORDER\","
				+ "\"zOrder\":0}]}}";
		String genrateProgram = String
				.format("{\"programID\":1,\"outPutPath\":\"%s/\",\"mediasPath\":[{\"oldPath\":\"test\",\"newPath\":\"test\"}]}", rootDir);
		String trasfromProgram = String
				.format("{\"sn\": \"%s\",\"iconPath\": \"\",\"iconName\": \"\",\"sendProgramFilePaths\": {\"programPath\": "
								+ "\"%s/program1\",\"mediasPath\": {\"./test.png\": \"test.png\"}},\"programName\": \"program1\","
								+ "\"deviceIdentifier\": \"Demo\",\"startPlayAfterTransferred\": true,\"insertPlay\": true}",
						g_sn, rootDir);

		Boolean bTestVideo = false;
		if (bTestVideo) {
			editProgram = "{\"programID\":1,\"pageID\":1,\"pageInfo\":{\"name\":\"Demo\","
					+ "\"widgetContainers\":[{\"contents\":{\"widgets\":[{\"constraints\":[{\"cron\":[\"0 0 0 ? * 1,2,3,4,5,6,7\"],"
					+ "\"endTime\":\"4017-12-30T23:59:59Z 8:00\",\"startTime\":\"1970-01-01T00:00:00Z 8:00\"}],\"duration\":5000,"
					+ "\"dataSource\":\"219c7dd3260d6bf98ebf61b6da440ffd.avi\",\"type\":\"VIDEO\",\"name\":\"test.vai\","
					+ "\"originalDataSource\":\"./test.avi\"}]},\"id\":1,\"name\":\"widgetContainers1\"}]}}";

			trasfromProgram = String
					.format("{\"sn\":\"%s\",\"iconPath\": \"\",\"iconName\": \"\",\"sendProgramFilePaths\": {\"programPath\": "
									+ "\"%s/program1\",\"mediasPath\": {\"./test.avi\": \"test.avi\"}},\"programName\": \"program1\","
									+ "\"deviceIdentifier\": \"Demo\",\"startPlayAfterTransferred\": true,\"insertPlay\": true}",
							g_sn, rootDir);
		}

		Boolean testText = false;
		if (testText) {
			editProgram = "{\"programID\":1,\"pageID\":1,\"pageInfo\":{\"name\":\"jiemu\","
					+ "\"widgetContainers\":[{\"contents\":{\"widgets\":[{\"constraints\":[{\"cron\":[\"0 0 0 ? * 1,2,3,4,5,6,7\"],"
					+ "\"endTime\":\"4017-12-30T23:59:59Z+8:00\",\"startTime\":\"1970-01-01T00:00:00Z+8:00\"}],\"duration\":5000,"
					+ "\"metadata\":{\"content\":{\"autoPaging\":true,\"backgroundMusic\":{\"duration\":0,\"isTextSync\":false},"
					+ "\"displayStyle\":{\"scrollAttributes\":{\"effects\":{\"animation\":\"MARQUEE_LEFT\",\"speed\":3}},"
					+ "\"type\":\"SCROLL\"},\"paragraphs\":[{\"backgroundColor\":\"#00000000\",\"horizontalAlignment\":\"CENTER\","
					+ "\"letterSpacing\":0,\"lineSpacing\":0,\"lines\":[{\"segs\":[{\"content\":\"简体繁體日本語한국어.English123\"}]}],"
					+ "\"verticalAlignment\":\"CENTER\"}],\"textAttributes\":[{\"backgroundColor\":\"#ff000000\","
					+ "\"attributes\":{\"font\":{\"family\":[\"Arial\"],\"isUnderline\":false,\"size\":20,\"style\":\"NORMAL\"},"
					+ "\"letterSpacing\":0,\"textColor\":\"#ffff0000\"}}]}},\"name\":\"text\",\"type\":\"ARCH_TEXT\"}]},\"id\":1,"
					+ "\"name\":\"widgetContainers1\"}]}}";
			trasfromProgram = String
					.format("{\"sn\": \"%s\",\"iconPath\": \"\",\"iconName\": \"\",\"sendProgramFilePaths\": {\"programPath\": "
									+ "\"%s/program1\",\"mediasPath\": {}},\"programName\": \"program1\",\"deviceIdentifier\": \"Demo\","
									+ "\"startPlayAfterTransferred\": true,\"insertPlay\": true}",
							g_sn, rootDir);
		}

		String companyInfo = "{\"company\":\"NovaStar\",\"phone\":\"029-68216000\",\"email\":\"hr@novastar.tech\"}";
		instance.nvSetDevLang("Java");
		System.out.println("nvInit(sdk 初始化):");
		System.out.println(instance.nvInit(rootDir, companyInfo));
		//System.out.println("ViplexCore Demo nvSearchTerminalAsync(搜索) begin... ");
		// instance.nvSearchTerminalAsync(callBack);
		Thread.sleep(3000);
		g_bAPIReturn = false;

		System.out.println("ViplexCore Demo nvSearchAppointIpAsync(指定IP搜索) begin... ");
		String requestData = "{\"ip\":\"192.168.41.1\"}";
		instance.nvSearchAppointIpAsync(requestData, callBack);
		// Thread.sleep(2000);

		System.out.println("ViplexCore Demo nvLoginAsync(登录) begin... ");
		String loginParam = String
				.format("{\"sn\":\"%s\",\"username\":\"admin\",\"rememberPwd\":1,\"password\":\"123456\",\"loginType\":0}", g_sn);
		instance.nvLoginAsync(loginParam, callBack);
		waitAPIReturn();
		if (g_code != 0) {
			System.out.println("ViplexCore Demo nvLoginAsync(登录) 失败！");
			return;
		}
		System.out.println("ViplexCore Demo nvCreateProgramAsync(创建节目) begin... ");
		instance.nvCreateProgramAsync(createProgram, callBack);
		waitAPIReturn();

		String requestDatapath = "{\"filePath\":\"./test.png\"}";
		System.out.println("ViplexCore Demo nvGetFileMD5Async(获取MD5) begin... ");
		instance.nvGetFileMD5Async(requestDatapath, callBack);
		waitAPIReturn();

		System.out.println("ViplexCore Demo nvSetPageProgramAsync(编辑节目) begin... ");
		instance.nvSetPageProgramAsync(editProgram, callBack);
		waitAPIReturn();

		System.out.println("ViplexCore Demo nvMakeProgramAsync(生成节目) begin... ");
		instance.nvMakeProgramAsync(genrateProgram, callBack);
		waitAPIReturn();

		System.out.println("ViplexCore Demo nvStartTransferProgramAsync(发送节目) begin... ");
		instance.nvStartTransferProgramAsync(trasfromProgram, callBack);
		Thread.sleep(10000);
		g_bAPIReturn = false;

		System.out.println("ViplexCore Demo nvGetProgramInfoAsync(获取节目信息) begin... ");
		String requestDatasn = String.format("{\"sn\":\"%s\"}", g_sn);
		instance.nvGetProgramInfoAsync(requestDatasn, callBack);
		waitAPIReturn();

		System.out.println("ViplexCore Demo nvSetScreenBrightnessAsync(设置音量) begin... ");
		String setVolumeParam = String.format("{\"sn\":\"%s\",\"screenBrightnessInfo\":{\"ratio\":70.0}}", g_sn);
		instance.nvSetScreenBrightnessAsync(setVolumeParam, callBack);
		waitAPIReturn();

		System.out.println("ViplexCore Demo nvGetVolumeAsync(获取音量) begin... ");
		String getVolumeParam = String.format("{\"sn\":\"%s\"}", g_sn);
		instance.nvGetVolumeAsync(getVolumeParam, callBack);
		waitAPIReturn();
	}

}
