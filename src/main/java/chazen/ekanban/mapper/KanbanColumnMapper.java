package chazen.ekanban.mapper;

import chazen.ekanban.entity.KanbanColumn;
import chazen.ekanban.entity.Swimlane;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KanbanColumnMapper {
    List<KanbanColumn> getColumns(int kanbanId);
    @Select("select * from kanban_column where column_id=#{columnId}")
    KanbanColumn getColumn(String columnId);
    @Insert("insert into kanban_column(column_id,column_name,column_width,parent_id,kanban_id,position,status) values(#{columnId},#{columnName},#{columnWidth},#{parentId},#{kanbanId},#{position},#{status})")
    int saveColumn(KanbanColumn column);
    @Delete("delete from kanban_column where column_id=#{columnId}")
    int deleteColumn(String columnId);
    @Update("update kanban_column set column_name=#{columnName},column_width=#{columnWidth},parent_id=#{parentId},position=#{position},status=#{status} where column_id=#{columnId}")
    int updateColumn(KanbanColumn kanbanColumn);

    @Select("select * from swimlane where kanban_id=#{kanbanId}")
    List<Swimlane> getSwimlanes(int kanbanId);
    @Select("select * from swimlane where swimlane_id=#{swimlaneId}")
    Swimlane getSwimlane(String swimlaneId);
    @Insert("insert into swimlane(swimlane_id,swimlane_name,position,group_id,height,across_column,column_position,group_member_number,kanban_id) values(#{swimlaneId},#{swimlaneName},#{position},#{groupId},#{height},#{acrossColumn},#{columnPosition},#{groupMemberNumber},#{kanbanId})")
    int saveSwimlane(Swimlane swimlane);
    @Delete("delete from swimlane where swimlane_id=#{swimlaneId}")
    int deleteSwimlane(String swimlaneId);
    @Update("update swimlane set swimlane_name=#{swimlaneName},position=#{position},group_id=#{groupId},height=#{height},across_column=#{acrossColumn},column_position=#{columnPosition},group_member_number=#{groupMemberNumber} where swimlane_id=#{swimlaneId}")
    int updateSwimlane(Swimlane swimlane);

    @Delete("delete from kanban_column where kanban_id=#{kanbanId}")
    int deleteColumnUnderKanban(int kanbanId);

    @Delete("delete from swimlane where kanban_id=#{kanbanId}")
    int deleteSwimlaneUnderKanban(int kanbanId);
}
