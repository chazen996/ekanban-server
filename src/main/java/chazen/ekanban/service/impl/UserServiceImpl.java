package chazen.ekanban.service.impl;

import chazen.ekanban.entity.SysUser;
import chazen.ekanban.mapper.UserMapper;
import chazen.ekanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Arrays.asList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public SysUser findUserByUsername(String username) {
        SysUser user = userMapper.findUserByUsername(username);
        if(user != null){
            user.setRoles(asList("ROLE_USER"));
        }
        return user;
    }

    @Override
    public SysUser save(SysUser user) {
        user.setLastPasswordResetDate(new Date());
        userMapper.save(user);
        return user;
    }

    public SysUser findUserByEmailAddress(String emailAddress){
        return userMapper.findUserByEmailAddress(emailAddress);
    }

    public SysUser findUserBySecretIdentity(SysUser user){
        return userMapper.findUserBySecretIdentity(user);
    }

    public int updatePassword(SysUser user){
        return userMapper.updatePassword(user);
    }

    @Override
    public SysUser findUserById(int userId) {
        return userMapper.findUserById(userId);
    }

}
