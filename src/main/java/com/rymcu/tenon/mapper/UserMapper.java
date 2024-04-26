package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int selectCountByNickname(@Param("nickname") String nickname);

    String selectMaxAccount();

    List<UserInfo> selectUsers(@Param("account") String account, @Param("email") String email, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("order") String order, @Param("sort") String sort);

    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    UserInfo selectUserInfoById(@Param("idUser") Long idUser);
}
