package chazen.ekanban.service;



import chazen.ekanban.entity.Card;

public interface CardService {
    public int createCard(Card card);
    public int deleteCardUnderSprintButOnKanban(int sprintId);
    public int changeCardStatusUnderSprint(String cardStatus, int sprintId);
    public int deleteCardUnderSprint(int sprintId);
    public int deleteCardByCardId(int cardId);
    public int updateCard(Card card);
}
