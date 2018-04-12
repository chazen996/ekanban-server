package chazen.ekanban.entity;

public class Swimlane {
    private String swimlaneId;
    private String swimlaneName;
    private int position;
    private String groupId;
    private int height;
    private String acrossColumn;
    private int columnPosition;
    private int groupMemberNumber;
    private int kanbanId;

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(String swimlaneId) {
        this.swimlaneId = swimlaneId;
    }

    public String getSwimlaneName() {
        return swimlaneName;
    }

    public void setSwimlaneName(String swimlaneName) {
        this.swimlaneName = swimlaneName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAcrossColumn() {
        return acrossColumn;
    }

    public void setAcrossColumn(String acrossColumn) {
        this.acrossColumn = acrossColumn;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getGroupMemberNumber() {
        return groupMemberNumber;
    }

    public void setGroupMemberNumber(int groupMemberNumber) {
        this.groupMemberNumber = groupMemberNumber;
    }

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }
}
