package chazen.ekanban.mapper;

import chazen.ekanban.entity.Card;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    @Select("select * from card where kanban_id=#{kanbanId}")
    public List<Card> getCardUnderKanban(int kanbanId);

    @Select("select * from card where card_id=#{cardId}")
    public Card getCardById(int cardId);
}
