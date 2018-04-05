package chazen.ekanban.service;



import chazen.ekanban.entity.Sprint;

import java.util.List;

public interface SprintService {
    public List<Sprint> getSprintUnderProject(int projectId);
    public int createSprint(Sprint sprint);
}
