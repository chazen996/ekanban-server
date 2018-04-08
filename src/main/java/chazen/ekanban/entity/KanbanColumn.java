package chazen.ekanban.entity;

import java.util.List;

public class KanbanColumn implements Comparable<KanbanColumn> {
    private int columnId;
    private String columnName;
    private int columnWidth;
    private String parentId;
    private int kanbanId;
    private int position;
    private List<KanbanColumn> subColumn;

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

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
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

    @Override
    public int compareTo(KanbanColumn column) {
        return this.getPosition() - column.getPosition();
    }
}
