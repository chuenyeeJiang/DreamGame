package com.chuenyee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.chuenyee.pojo.Appuser;

@Mapper
public interface AppuserMapper {
	// 增
	@Insert(value = "call insertAppuser(#{username}, #{password})")
	public void insert(Appuser appuser) throws Exception;

	// 刪
	@Delete(value = "call deleteAppuserById(#{appuserid})")
	public boolean deleteById(String appuserid) throws Exception;
	
	// 改
	@Update(value = "call updateAppuser(#{appuserid},#{username},#{password})")
	public boolean update(Appuser appuser) throws Exception;

	@Update(value = "call saveheadPortrait(#{headportrait},#{username})")
	public void saveheadPortrait(Appuser appuser) throws Exception;

	// 查
	@Select(value ="call findAppuserById(#{appuserid})")
	public Appuser findById(String appuserid) throws Exception;
	
	@Select(value = " call findPasswordByUsername(#{username})")
	public String findPasswordByUsername(String username) throws Exception;

	@Select(value = "call findAppuserByName(#{username})")
	public Appuser findByName(String username) throws Exception;

	@Select(value = "call getGrade(#{username});")
	public int getGrade(String username) throws Exception;

	@Select(value = "call insertAppuser(#{username}, #{password})")
	public String getId(String username) throws Exception;

	@Select(value = "call findAppuserAll()")
	public List<Appuser> findAll() throws Exception;

	@Select(value = "call getheadPortrait(#{username})")
	public String getheadPortrait(String username) throws Exception;
	
}
