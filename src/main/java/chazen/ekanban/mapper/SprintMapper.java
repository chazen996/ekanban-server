package chazen.ekanban.mapper;

import chazen.ekanban.entity.Card;
import chazen.ekanban.entity.Sprint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintMapper {
    public List<Sprint> getSprintUnderProject(int projectId);

    @Insert("insert into sprint(sprint_name,sprint_description,start_date,end_date,project_id) values(#{sprintName},#{sprintDescription},#{startDate},#{endDate},#{projectId})")
    public int createSprint(Sprint sprint);

    @Select("select * from card where sprint_id=#{sprintId}")
    public List<Card> getCardUnderSprint(int sprintId);

    public int updateSprint(Sprint sprint);

    @Delete("delete from sprint where sprint_id=#{sprintId}")
    public int  deleteSprint(int sprintId);
}
