package chazen.ekanban.service;



import chazen.ekanban.entity.Card;
import chazen.ekanban.entity.Sprint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SprintService {
    public List<Sprint> getSprintUnderProject(int projectId);
    public int createSprint(Sprint sprint);
    public List<Card> getCardUnderSprint(int sprintId);
    public int updateSprint(Sprint sprint);
    public int  deleteSprint(int sprintId);
    public List<Sprint> getTargetStatusSprints(int projectId, String sprintStatus);
    public List<Card> getCardUnderSprintButWithoutKanbanId(int sprintId);
}
