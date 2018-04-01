package chazen.ekanban.mapper;


import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {

    public List<Project> getProjects(int userId);

    public int saveProject(Project project);

    @Insert("insert into user_project(user_id,project_id) values(#{userId},#{projectId})")
    public int saveUserProject(@Param("userId") int userId, @Param("projectId") int projectId);

    @Delete("delete from project where project_id=#{projectId}")
    public int deleteProject(int projectId);

    @Delete("delete from user_project where project_id=#{projectId}")
    public int deleteUserProject(int projectId);

    @Select("select * from project where project_id=#{projectId}")
    public Project getProject(int projectId);

    @Update("update project set project_name=#{projectName},project_description=#{projectDescription} where project_id=#{projectId}")
    public int updateProject(Project project);

    public List<SysUser> getAllUserUnderProject(int projectId);

    @Select("select count(*) from user_project where user_id=#{userId} and project_id=#{projectId}")
    public int confirmTargetUserProjectExits(@Param("projectId") int projectId,@Param("userId") int userId);
}
