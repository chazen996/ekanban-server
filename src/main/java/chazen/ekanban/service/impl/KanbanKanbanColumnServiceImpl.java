package chazen.ekanban.service.impl;


import chazen.ekanban.entity.KanbanColumn;
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
}
