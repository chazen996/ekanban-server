package chazen.ekanban.service;

import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService {
    public List<Project> getProjects(int userId);
    public int saveProject(Project project);
    public int saveUserProject(int userId,int projectId);
    public int deleteProject(int projectId);
    public int deleteUserProject(int projectId);
    public Project getProject(int projectId);
    public int updateProject(Project project);
    public List<SysUser> getAllUserUnderProject(int projectId);
    public int confirmTargetUserProjectExits(int projectId,int userId);
    public int removeUserFromProject(int userId,int projectId);
    public List<SysUser> getUserLikeTheUsername(String username);
    public Project getTargetProject(int projectId);
    public  int getTargetProjectUserAmount(int projectId);
    public int changeProjectControlRight(int userId,int projectId);
    public Project getProjectByKanbanId(int kanbanId);
}
