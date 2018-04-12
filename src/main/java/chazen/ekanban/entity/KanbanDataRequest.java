package chazen.ekanban.entity;

import java.util.List;

public class KanbanDataRequest {
    private String[] toBeDeletedColumn;
    private String[] toBeDeletedSwimlane;
    private List<KanbanColumn> columns;
    private List<Swimlane> swimlanes;
    private int kanbanId;

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String[] getToBeDeletedColumn() {
        return toBeDeletedColumn;
    }

    public void setToBeDeletedColumn(String[] toBeDeletedColumn) {
        this.toBeDeletedColumn = toBeDeletedColumn;
    }

    public String[] getToBeDeletedSwimlane() {
        return toBeDeletedSwimlane;
    }

    public void setToBeDeletedSwimlane(String[] toBeDeletedSwimlane) {
        this.toBeDeletedSwimlane = toBeDeletedSwimlane;
    }

    public List<KanbanColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<KanbanColumn> columns) {
        this.columns = columns;
    }

    public List<Swimlane> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(List<Swimlane> swimlanes) {
        this.swimlanes = swimlanes;
    }
}
