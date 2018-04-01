package chazen.ekanban.service.impl;

import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.SysUser;
import chazen.ekanban.mapper.ProjectMapper;
import chazen.ekanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> getProjects(int userId) {
        return projectMapper.getProjects(userId);
    }

    @Override
    public int saveProject(Project project) {
        return projectMapper.saveProject(project);
    }

    @Override
    public int saveUserProject(int userId, int projectId) {
        return projectMapper.saveUserProject(userId,projectId);
    }

    @Override
    public int deleteProject(int projectId) {
        return projectMapper.deleteProject(projectId);
    }

    @Override
    public int deleteUserProject(int projectId) {
        return projectMapper.deleteUserProject(projectId);
    }

    @Override
    public Project getProject(int projectId) {
        return projectMapper.getProject(projectId);
    }

    @Override
    public int updateProject(Project project) {
        return projectMapper.updateProject(project);
    }

    @Override
    public List<SysUser> getAllUserUnderProject(int projectId) {
        return projectMapper.getAllUserUnderProject(projectId);
    }

    @Override
    public int confirmTargetUserProjectExits(int projectId, int userId) {
        return projectMapper.confirmTargetUserProjectExits(projectId,userId);
    }
}
