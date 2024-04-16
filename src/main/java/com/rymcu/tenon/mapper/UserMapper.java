package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created on 2024/4/13 15:03.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.mapper
 */
public interface UserMapper extends Mapper<User> {
    int updateLastOnlineTimeByAccount(@Param("account") String account);

    User selectByAccount(@Param("account") String account);

    int updateLastLoginTime(@Param("idUser") Long idUser);

    int insertUserRole(@Param("idUser") Long idUser, @Param("idRole") Long idRole);

    Integer selectCountByNickname(@Param("nickname") String nickname);

    String selectMaxAccount();
}
