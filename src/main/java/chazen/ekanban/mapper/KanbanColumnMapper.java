package chazen.ekanban.mapper;

import chazen.ekanban.entity.KanbanColumn;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KanbanColumnMapper {
    List<KanbanColumn> getColumns(int kanbanId);
}
