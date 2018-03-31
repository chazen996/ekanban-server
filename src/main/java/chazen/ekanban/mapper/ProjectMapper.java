package chazen.ekanban.mapper;


import chazen.ekanban.entity.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
