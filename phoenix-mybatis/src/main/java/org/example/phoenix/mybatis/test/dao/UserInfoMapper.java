package org.example.phoenix.mybatis.test.dao;

import org.apache.ibatis.annotations.*;
import org.example.phoenix.mybatis.test.UserInfo;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Insert("upsert into USER_INFO (ID, NAME) values (#{user.id}, #{user.name})")
    void addUser(@Param("user") UserInfo userInfo);

    @Delete("delete from USER_INFO where ID = #{userId}")
    void deleteUser(@Param("userId") int userId);

    @Select("select * from USER_INFO where ID = #{userId}")
    @ResultMap("userResultMap")
    UserInfo getUserById(@Param("userId") int userId);


    @Select("select * from USER_INFO where NAME = #{userName}")
    @ResultMap("userResultMap")
    UserInfo getUserByName(@Param("userName") String userName);

    @Select("select * from USER_INFO")
    @ResultMap("userResultMap")
    List<UserInfo> getUsers();
}
