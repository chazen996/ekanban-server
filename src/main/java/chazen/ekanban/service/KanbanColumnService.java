package chazen.ekanban.service;


import chazen.ekanban.entity.KanbanColumn;
import chazen.ekanban.entity.Swimlane;

import java.util.List;

public interface KanbanColumnService {
    List<KanbanColumn> getColumns(int kanbanId);
    KanbanColumn getColumn(String columnId);
    int saveColumn(KanbanColumn column);
    List<Swimlane> getSwimlanes(int kanbanId);
    Swimlane getSwimlane(String swimlaneId);
    int saveSwimlane(Swimlane swimlane);
    int deleteSwimlane(String swimlaneId);
    int deleteColumn(String columnId);
    int updateSwimlane(Swimlane swimlane);
    int updateColumn(KanbanColumn kanbanColumn);
    int deleteColumnUnderKanban(int kanbanId);
    int deleteSwimlaneUnderKanban(int kanbanId);
}
