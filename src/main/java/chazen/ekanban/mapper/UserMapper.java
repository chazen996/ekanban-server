package chazen.ekanban.mapper;

import chazen.ekanban.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/* 此处需要使用@Mapper注解，也可通过Application入口文件@MapperScan注解统一设置 */
@Repository
public interface UserMapper {

    @Select("select * from sys_user where username = #{username}")
    public SysUser findUserByUsername(String username);

    /* 关于如何在插入数据后原数据返回的方法：接口Mapper中返回类型为void，但在与其对应的Service中
     * 返回类型为SysUser,同时在mapper.xml中使用keyProperty="id"（此时插入数据的id会自动返回并填充）
     * 再将获取到id后的数据原样返回即可* */
    public void save(SysUser user);

    @Select("select * from sys_user where email_address = #{emailAddress}")
    public SysUser findUserByEmailAddress(String emailAddress);

    public  SysUser findUserBySecretIdentity(SysUser user);

    @Update("update sys_user set password=#{password} where username=#{username}")
    public int updatePassword(SysUser user);

    @Select("select * from sys_user where id=#{userId}")
    public SysUser findUserById(int userId);
}
