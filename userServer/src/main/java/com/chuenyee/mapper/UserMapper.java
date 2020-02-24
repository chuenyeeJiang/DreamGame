package com.chuenyee.mapper;

import java.util.List;

import com.chuenyee.pojo.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
	// 增
	@Insert(value = "insert into t_user(id,username,password) values(uuid(),#{username}, #{password})")
	public void insert(User user) throws Exception;

//	// 刪
//	@Delete(value = "call deleteAppuserById(#{appuserid})")
//	public boolean deleteById(String appuserid) throws Exception;

	// 改
	@Update(value = "update t_user set username=#{username}, password=#{password} where id=#{id}")
	public boolean update(User user) throws Exception;

	@Update(value = "update t_user set head_portrait_path=#{headportrait} where id=#{id}")
	public void saveHeadPortrait(User user) throws Exception;

	// 查
	@Select(value ="select * from t_user where id=#{id}")
	public User findById(String id) throws Exception;
	
	@Select(value = "select password from t_user where username = #{username}")
	public String findPasswordByUsername(String username) throws Exception;

	@Select(value = "select * from t_user where username = #{username}")
	public User findByName(String username) throws Exception;


	@Select(value = "select id from t_user where username=#{username} ")
	public String getId(String username) throws Exception;

	@Select(value = "select * from t_user")
	public List<User> findAll() throws Exception;

	@Select(value = "select head_portrait_path from t_user where username =#{username}")
	public String getHeadPortrait(String username) throws Exception;
	
}
