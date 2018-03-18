package chazen.ekanban.service;

import chazen.ekanban.entity.SysUser;

public interface UserService {

    public SysUser findUserByUsername(String username);

    public SysUser save(SysUser user);
}
