package chazen.ekanban.service;

import chazen.ekanban.entity.Project;

import java.util.List;

public interface ProjectService {
    public List<Project> getProjects(int userId);
    public int saveProject(Project project);
    public int saveUserProject(int userId,int projectId);
}
