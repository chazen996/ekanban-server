package chazen.ekanban.service;



import chazen.ekanban.entity.Card;

import java.util.List;

public interface CardService {
    public int createCard(Card card);
    public int deleteCardUnderSprintButOnKanban(int sprintId);
    public int changeCardStatusUnderSprint(String cardStatus, int sprintId);
    public int deleteCardUnderSprint(int sprintId);
    public int deleteCardByCardId(int cardId);
    public int updateCard(Card card);
    public List<Card> getCardUnderKanban(int kanbanId);
    public Card getCardById(int cardId);
    public int checkCurrentPositionCardNumber(Card card);
    public int moveCard(Card card);
    public int deleteCardUnderKanban(int kanbanId);
}
