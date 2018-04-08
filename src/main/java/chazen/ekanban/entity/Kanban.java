package chazen.ekanban.entity;

public class Kanban {
    private int kanbanId;
    private String kanbanName;
    private String kanbanDescription;
    private int projectId;
    private int kanbanHeight;

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getKanbanName() {
        return kanbanName;
    }

    public void setKanbanName(String kanbanName) {
        this.kanbanName = kanbanName;
    }

    public String getKanbanDescription() {
        return kanbanDescription;
    }

    public void setKanbanDescription(String kanbanDescription) {
        this.kanbanDescription = kanbanDescription;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getKanbanHeight() {
        return kanbanHeight;
    }

    public void setKanbanHeight(int kanbanHeight) {
        this.kanbanHeight = kanbanHeight;
    }
}
