package chazen.ekanban.service;

import chazen.ekanban.entity.SysUser;

public interface UserService {

    public SysUser findUserByUsername(String username);

    public SysUser save(SysUser user);

    public SysUser findUserByEmailAddress(String emailAddress);

    public SysUser findUserBySecretIdentity(SysUser user);

    public int updatePassword(SysUser user);

    public SysUser findUserById(int userId);
}
