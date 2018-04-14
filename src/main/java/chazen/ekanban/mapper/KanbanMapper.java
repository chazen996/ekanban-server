package chazen.ekanban.mapper;

import chazen.ekanban.entity.Kanban;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KanbanMapper {
    public List<Kanban> getKanbanUnderProject(int projectId);

    @Insert("insert into kanban(kanban_name,kanban_description,project_id,kanban_height) values(#{kanbanName},#{kanbanDescription},#{projectId},#{kanbanHeight})")
    public int saveKanban(Kanban kanban);

    @Delete("delete from kanban where kanban_id=#{kanbanId}")
    public int deleteKanban(int kanbanId);

    @Update("update kanban set kanban_name=#{kanbanName},kanban_description=#{kanbanDescription}")
    public int updateKanban(Kanban kanban);

    @Select("select * from kanban where kanban_id=#{kanbanId}")
    public Kanban getKanbanById(int kanbanId);

    @Update("update kanban set kanban_height=#{kanbanHeight} where kanban_id=#{kanbanId}")
    public int updateKanbanHeight(@Param("kanbanId")int kanbanId,@Param("kanbanHeight")int kanbanHeight);
}
