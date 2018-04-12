package chazen.ekanban.service.impl;


import chazen.ekanban.entity.KanbanColumn;
import chazen.ekanban.entity.Swimlane;
import chazen.ekanban.mapper.KanbanColumnMapper;
import chazen.ekanban.service.KanbanColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanKanbanColumnServiceImpl implements KanbanColumnService {

    @Autowired
    KanbanColumnMapper kanbanColumnMapper;

    @Override
    public List<KanbanColumn> getColumns(int kanbanId) {
        return kanbanColumnMapper.getColumns(kanbanId);
    }

    @Override
    public KanbanColumn getColumn(String columnId) {
        return kanbanColumnMapper.getColumn(columnId);
    }

    @Override
    public int saveColumn(KanbanColumn column) {
        return kanbanColumnMapper.saveColumn(column);
    }

    @Override
    public List<Swimlane> getSwimlanes(int kanbanId) {
        return kanbanColumnMapper.getSwimlanes(kanbanId);
    }

    @Override
    public Swimlane getSwimlane(String swimlaneId) {
        return kanbanColumnMapper.getSwimlane(swimlaneId);
    }

    @Override
    public int saveSwimlane(Swimlane swimlane) {
        return kanbanColumnMapper.saveSwimlane(swimlane);
    }

    @Override
    public int deleteSwimlane(String swimlaneId) {
        return kanbanColumnMapper.deleteSwimlane(swimlaneId);
    }

    @Override
    public int deleteColumn(String columnId) {
        return kanbanColumnMapper.deleteColumn(columnId);
    }

    @Override
    public int updateSwimlane(Swimlane swimlane) {
        return kanbanColumnMapper.updateSwimlane(swimlane);
    }

    @Override
    public int updateColumn(KanbanColumn kanbanColumn) {
        return kanbanColumnMapper.updateColumn(kanbanColumn);
    }
}
