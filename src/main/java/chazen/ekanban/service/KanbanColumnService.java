package chazen.ekanban.service;


import chazen.ekanban.entity.KanbanColumn;

import java.util.List;

public interface KanbanColumnService {
    List<KanbanColumn> getColumns(int kanbanId);
}
