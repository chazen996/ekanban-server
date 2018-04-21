package chazen.ekanban.service.impl;


import chazen.ekanban.entity.Card;
import chazen.ekanban.mapper.CardMapper;
import chazen.ekanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService{

    @Autowired
    CardMapper cardMapper;

    @Override
    public int createCard(Card card) {
        return cardMapper.createCard(card);
    }

    @Override
    public int deleteCardUnderSprintButOnKanban(int sprintId) {
        return cardMapper.deleteCardUnderSprintButOnKanban(sprintId);
    }

    @Override
    public int changeCardStatusUnderSprint(String cardStatus, int sprintId) {
        return cardMapper.changeCardStatusUnderSprint(cardStatus,sprintId);
    }

    @Override
    public int deleteCardUnderSprint(int sprintId) {
        return cardMapper.deleteCardUnderSprint(sprintId);
    }

    @Override
    public int deleteCardByCardId(int cardId) {
        return cardMapper.deleteCardByCardId(cardId);
    }

    @Override
    public int updateCard(Card card) {
        return cardMapper.updateCard(card);
    }

    @Override
    public List<Card> getCardUnderKanban(int kanbanId) {
        return cardMapper.getCardUnderKanban(kanbanId);
    }

    @Override
    public Card getCardById(int cardId) {
        return cardMapper.getCardById(cardId);
    }

    @Override
    public int checkCurrentPositionCardNumber(Card card) {
        return cardMapper.checkCurrentPositionCardNumber(card);
    }

    @Override
    public int moveCard(Card card) {
        return cardMapper.moveCard(card);
    }

    @Override
    public int deleteCardUnderKanban(int kanbanId) {
        return cardMapper.deleteCardUnderKanban(kanbanId);
    }

}
