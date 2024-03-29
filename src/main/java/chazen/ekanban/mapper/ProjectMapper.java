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

    @Delete("delete from user_project where user_id=#{userId} and project_id=#{projectId}")
    public int removeUserFromProject(@Param("userId") int userId,@Param("projectId") int projectId);

    @Select("select * from sys_user where username like CONCAT('%',#{username},'%')  ")
    public List<SysUser> getUserLikeTheUsername(String username);

    @Select("select * from project where project_id=#{projectId}")
    public Project getTargetProject(int projectId);

    @Select("select count(*) from user_project where project_id=#{projectId}")
    public int getTargetProjectUserAmount(int projectId);

    @Update("update project set created_by=#{userId} where project_id=#{projectId}")
    public int changeProjectControlRight(@Param("userId")int userId,@Param("projectId")int projectId);

    @Select("select * from project where project_id in (select project_id from kanban where kanban_id=#{kanbanId})")
    public Project getProjectByKanbanId(int kanbanId);

    @Delete("delete from kanban where project_id=#{projectId}")
    public int deleteKanbanUnderProject(int projectId);
}
