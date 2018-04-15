package chazen.ekanban.entity;

public class TobeDeletedCardList {
    private Integer[] cardIdList;
    private int kanbanId;

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }

    public Integer[] getCardIdList() {
        return cardIdList;
    }

    public void setCardIdList(Integer[] cardIdList) {
        this.cardIdList = cardIdList;
    }

}
