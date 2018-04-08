package chazen.ekanban.service.impl;


import chazen.ekanban.entity.Kanban;
import chazen.ekanban.mapper.KanbanMapper;
import chazen.ekanban.service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanServiceImpl implements KanbanService{

    @Autowired
    KanbanMapper kanbanMapper;

    @Override
    public List<Kanban> getKanbanUnderProject(int projectId) {
        return kanbanMapper.getKanbanUnderProject(projectId);
    }

    @Override
    public int saveKanban(Kanban kanban) {
        return kanbanMapper.saveKanban(kanban);
    }

    @Override
    public int deleteKanban(int kanbanId) {
        return kanbanMapper.deleteKanban(kanbanId);
    }

    @Override
    public int updateKanban(Kanban kanban) {
        return kanbanMapper.updateKanban(kanban);
    }

    @Override
    public Kanban getKanbanById(int kanbanId) {
        return kanbanMapper.getKanbanById(kanbanId);
    }
}
