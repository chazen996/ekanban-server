package chazen.ekanban.mapper;

import chazen.ekanban.entity.Card;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;


@Repository
public interface CardMapper {
    @Insert("insert into card(card_type,card_description,card_content,assigned_person,project_id,sprint_id,kanban_id,card_status) values(#{cardType},#{cardDescription},#{cardContent},#{assignedPerson},#{projectId},#{sprintId},#{kanbanId},#{cardStatus})")
    public int createCard(Card card);
}
