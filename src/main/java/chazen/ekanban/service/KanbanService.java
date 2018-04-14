package chazen.ekanban.service;


import chazen.ekanban.entity.Kanban;

import java.util.List;

public interface KanbanService {
    public List<Kanban> getKanbanUnderProject(int projectId);
    public int saveKanban(Kanban kanban);
    public int deleteKanban(int kanbanId);
    public int updateKanban(Kanban kanban);
    public Kanban getKanbanById(int kanbanId);
    public int updateKanbanHeight(int kanbanId,int kanbanHeight);
}
