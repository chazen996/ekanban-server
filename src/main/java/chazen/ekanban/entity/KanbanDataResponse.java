package chazen.ekanban.entity;

import java.util.List;

public class KanbanDataResponse {
    private List<KanbanColumn> columns;
    private List<Swimlane> swimlanes;

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
