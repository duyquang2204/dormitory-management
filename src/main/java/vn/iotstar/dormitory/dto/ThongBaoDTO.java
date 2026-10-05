package vn.iotstar.dormitory.dto;

import vn.iotstar.dormitory.entity.ThongBao;
import java.util.List;

public class ThongBaoDTO {
    private List<TodoItem> todos;
    private List<ThongBao> updates;
    private long unreadUpdatesCount;

    public static class TodoItem {
        private String title;
        private String link;
        private String iconClass;

        public TodoItem(String title, String link, String iconClass) {
            this.title = title;
            this.link = link;
            this.iconClass = iconClass;
        }

        public String getTitle() { return title; }
        public String getLink() { return link; }
        public String getIconClass() { return iconClass; }
    }

    public ThongBaoDTO() {}

    public List<TodoItem> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoItem> todos) {
        this.todos = todos;
    }

    public List<ThongBao> getUpdates() {
        return updates;
    }

    public void setUpdates(List<ThongBao> updates) {
        this.updates = updates;
    }

    public long getUnreadUpdatesCount() {
        return unreadUpdatesCount;
    }

    public void setUnreadUpdatesCount(long unreadUpdatesCount) {
        this.unreadUpdatesCount = unreadUpdatesCount;
    }

    public long getTotalCount() {
        long todoCount = (todos != null) ? todos.size() : 0;
        return todoCount + unreadUpdatesCount;
    }
}
