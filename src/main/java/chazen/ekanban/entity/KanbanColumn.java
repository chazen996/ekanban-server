package chazen.ekanban.entity;

import java.util.List;

public class KanbanColumn implements Comparable<KanbanColumn> {
    private String columnId;
    private String columnName;
    private int columnWidth;
    private String parentId;
    private int kanbanId;
    private int position;
    private List<KanbanColumn> subColumn;
    private String status;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<KanbanColumn> getSubColumn() {
        return subColumn;
    }

    public void setSubColumn(List<KanbanColumn> subColumn) {
        this.subColumn = subColumn;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(int kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(KanbanColumn column) {
        return this.getPosition() - column.getPosition();
    }
}
