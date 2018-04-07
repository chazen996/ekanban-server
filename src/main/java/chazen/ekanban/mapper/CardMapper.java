package chazen.ekanban.mapper;

import chazen.ekanban.entity.Card;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface CardMapper {
    public int createCard(Card card);

    @Delete("delete from card where kanban_id is not null and sprint_id=#{sprintId}")
    public int deleteCardUnderSprintButOnKanban(int sprintId);

    public int updateCard(Card card);

    @Update("update card set card_status=#{cardStatus} where sprint_id=#{sprintId}")
    public int changeCardStatusUnderSprint(@Param("cardStatus") String cardStatus,@Param("sprintId") int sprintId);

    @Delete("delete from card where sprint_id=#{sprintId}")
    public int deleteCardUnderSprint(int sprintId);

    @Delete("delete from card where card_id=#{cardId}")
    public int deleteCardByCardId(int cardId);
}
