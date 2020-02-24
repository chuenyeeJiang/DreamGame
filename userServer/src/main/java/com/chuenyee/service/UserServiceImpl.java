package com.chuenyee.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chuenyee.tool.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.mapper.UserMapper;
import com.chuenyee.pojo.User;
import com.chuenyee.service.api.UserService;
import com.chuenyee.service.api.Encryption;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService {


	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	// 加密接口
	Encryption encryption = new JcParseMD5();;
    // 分页信息 接口
	PageInfo user_pageInfo;

	// 验证
	Verification verification = new  Verification();

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	/*
	 * public void insert(User user) throws Exception { ApplicationContext
	 * cfg = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	 * UserMapper userMapper=(UserMapper) cfg.getBean("userMapper");
	 * // TODO Auto-generated method stub
	 * System.out.println("saveUser...run");
	 * System.out.println("user��"+user);
	 * System.out.println("userMapper��"+userMapper);
	 * userMapper.insert(user); }
	 */

	/*
	 * public boolean deleteUser(int userid) throws Exception { // TODO
	 * Auto-generated method stub // return userMapper.delete(userid);
	 * return false; }
	 */

	// 用戶信息修改
	public boolean updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		// return userMapper.update(user);
		return false;
	}

	/*
	 * public User findUserById(int userid) throws Exception { // TODO
	 * Auto-generated method stub User
	 * user=userMapper.findById(userid); return user; }
	 */

	// 登陆
	/*
	 * public String login(String username) throws Exception { // TODO
	 * Auto-generated method stub String password =
	 * userMapper.findPasswordByUsername(username); return password; }
	 */

	// 新-登陆Test
	public ModelAndView login(Map<String, Object> request_map,String requestView,String reView) throws Exception {
		System.out.println("login!");
		ModelAndView mv = new ModelAndView();
//		String loginView = request.getHeader("name");    获取发送请求的请求头，有待研究
//		String loginView = "login.html";
		logger.info("=====================登录检验!");
		logger.debug("请求信息集合:"+request_map.keySet());

		//获取请求信息
		String username = (String)request_map.get("username");
		String password = (String)request_map.get("password");

		//String validatecode = (String)request_map.get("validatecode");
		// 获取正确验证码
		//String str_validatecode = (String) redisTemplate.opsForValue().get("Validatecode");


		//用户名密码UTF-8转码
				username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
				password = new String(password.getBytes("ISO-8859-1"), "UTF-8");



		//logger.debug("获取正确验证码：" + str_validatecode);

		// 默认跳转至登录界面
		mv.setViewName(requestView);
		logger.debug("默认跳转至登录界面：" + requestView);



		// 验证用户密码是否为空
		if(verification.isNull(username)){
			mv.addObject("log_error", "用户名不能为空!");
			logger.debug("用户名不能为空!");
			return mv;
		}

		if(verification.isNull(password)){
			mv.addObject("log_error", "密码不能为空!");
			logger.debug("密码不能为空!");
			return mv;
		}

		// 验证
//		if(verification.isNull(validatecode)){
//			mv.addObject("log_error", "验证码不能为空!");
//			logger.debug("验证码不能为空!");
//			return mv;
//		}

//		if(!verification.toLowerCase(str_validatecode, validatecode)){
//			mv.addObject("log_error", "验证码错误!");
//			logger.debug("验证码错误!");
//			return mv;
//		}

			// 用MD5转化并核验密码
			password = encryption.parseStrToMd5L32(password);

			// 查询用户密码
			String repassword = userMapper.findPasswordByUsername(username);

			if (verification.isNull(repassword)) {
				mv.addObject("log_error", "用户不存在!");
				logger.debug("用户不存在!");
				return mv;
			}

			// 登陆验证
			if (!verification.compare(repassword,password)) {
				mv.addObject("log_error", "密码错误!");
				logger.debug("密码错误!");
			}

			User user = getUser(username);
			int grade = user.getGrade();

			//SSO单点登录
			String accessToken = encryption.parseStrToMd5L32(username+System.currentTimeMillis());
			redisTemplate.opsForValue().set(accessToken, user.getId());



			mv.addObject("accessToken", accessToken);

			mv.setViewName(reView);

			logger.debug("success!");
			logger.info("登录成功!");
		    /* String password = userMapper.findPasswordByUsername(username); */
		    return mv;
	}

	// 注销
	public ModelAndView logout(String accessToken, String loginView) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(loginView);

		redisTemplate.opsForValue().set(accessToken,null);
		redisTemplate.expire(accessToken,0, TimeUnit.SECONDS);
		redisTemplate.delete(accessToken);

		return mv;
	}

	/*
	 * // 注册 public String regis(User user) throws Exception { if
	 * (userMapper.findPasswordByUsername(user.getUsername()) != null) {
	 * return "用户名已存在"; } else { userMapper.insert(user); return "1"; } }
	 */
	// 注册
	public ModelAndView regis(Map<String, Object> request_map,String requestView,String reView)
			throws Exception {
		//获取请求信息
				String regisUsername = (String)request_map.get("regisUsername");
				String regisPassword = (String)request_map.get("regisPassword");
//				String validatecode = (String)request_map.get("validatecode");

		ModelAndView mv = new ModelAndView();


		/*
		 * 封装注册用户信息
		 */
		User user = new User();
		user.setUsername(regisUsername);
		logger.debug("加密前:"+regisPassword);
		// MD5加密
		regisPassword = encryption.parseStrToMd5L32(regisPassword);
		logger.debug("加密后(md5):"+regisPassword);

		user.setPassword(regisPassword);

		// 验证码取值
		String str_validatecode = (String) redisTemplate.opsForValue().get("Validatecode");
		System.out.println("session_validatecode:" + str_validatecode);

		// 验证判断
//		if (!regisValidatecode.equalsIgnoreCase(session_validatecode)) {
//			reslut = "验证码错误!";
//			System.out.println(reslut);
//		}

		// 注册验证
	    if (!verification.isNull(userMapper.findPasswordByUsername(user.getUsername()))) {
			mv.addObject("regis_error", "用户已存在!");
			mv.setViewName(requestView);
			return mv;
		}

			userMapper.insert(user);
			// 登陆
			String accessToken = encryption.parseStrToMd5L32(regisUsername+System.currentTimeMillis());

			//获取用户id
			User reUser = getUser(regisUsername);

			redisTemplate.opsForValue().set(accessToken,reUser.getId());

			mv.addObject("accessToken", accessToken);
			mv.setViewName(reView);
			return mv;

	}

	// 验证用户是否存在,并获取用户
	public User getUser(String username) throws Exception {
		// TODO Auto-generated method stub
		User user = userMapper.findByName(username);
		return user;
	}

	// 用户列表
	public List<User> getUsers() throws Exception {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}

	// 分页查询
	public ModelAndView tabQuery(HttpServletRequest request,
			HttpServletResponse response, Integer pn, String reView) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView(reView);
		System.out.println("userMapper:"+userMapper);
		PageHelper.startPage(pn, 5);
		List<User> list = null;
		try {
			list = getUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageInfo user_pageInfo = new PageInfo(list, 5);
		/* request.setAttribute("Customers", list); */
		mv.addObject("user_pageInfo", user_pageInfo);
		mv.addObject("page", "userSelect");

		return mv;
	}

	//判断是否已经登陆
	public boolean isLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(username!=null)
		{
			return true;
		}
		return false;
	}



	//redis获取用户名
	public String getUsernameByRedis(String accessToken)  throws Exception{
		// TODO Auto-generated method stub

		logger.info("====getusernameByRedis");
		//令牌获取id
		System.out.println("accessToken："+accessToken);
		logger.debug("accessToken："+accessToken);
		String userid = (String) redisTemplate.opsForValue().get(accessToken);
		System.out.println("userid："+userid);
		logger.debug("userid："+userid);
		//id获取用户名
		String username = userMapper.findById(userid).getUsername();
		System.out.println("username：" + username);
		logger.debug("username：" + username);
		return username;
	}

	@Override
	public String getHeadPortrait(String username) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getHeadPortrait(username);
	}

	@Override
	public Boolean saveHeadPortrait(User user) throws Exception {
		// TODO Auto-generated method stub
		try{
			userMapper.saveHeadPortrait(user);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public String getIdByUsernmae(String usernmae){
		try {
			return userMapper.getId(usernmae);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
}
