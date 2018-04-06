package chazen.ekanban.service.impl;


import chazen.ekanban.entity.Card;
import chazen.ekanban.mapper.CardMapper;
import chazen.ekanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService{

    @Autowired
    CardMapper cardMapper;

    @Override
    public int createCard(Card card) {
        return cardMapper.createCard(card);
    }
}
