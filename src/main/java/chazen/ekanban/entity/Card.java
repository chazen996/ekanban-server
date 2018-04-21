package chazen.ekanban.entity;

public class Card {
    private int cardId;
    private String cardType;
    private String cardDescription;
    private String cardContent;
    private int assignedPersonId;
    private int projectId;
    private int sprintId;
    private int kanbanId;
    private String cardStatus;
    private String columnId;
    private int positionX;
    private int positionY;

    private SysUser assignedPerson;

    private Kanban kanban;

    public Kanban getKanban() {
        return kanban;
    }

    public void setKanban(Kanban kanban) {
        this.kanban = kanban;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public int getAssignedPersonId() {
        return assignedPersonId;
    }

    public void setAssignedPersonId(int assignedPersonId) {
        this.assignedPersonId = assignedPersonId;
    }

    public SysUser getAssignedPerson() {
        return assignedPerson;
    }

    public void setAssignedPerson(SysUser assignedPerson) {
        this.assignedPerson = assignedPerson;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }
}
