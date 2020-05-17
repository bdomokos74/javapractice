package ood.filesearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class BaseEntry implements Entry {
    protected Directory parent;
    protected String name;
    protected String user;
    protected LocalDateTime created;
    protected Type type;

    BaseEntry(String name, String user, Directory parent, Type type) {
        this.name = name;
        this.user = user;
        this.parent = parent;
        this.created = LocalDateTime.now();
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean isDirectory() {
        return type==Type.DIR;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    private List<String> getPath() {
        List<String> result = new ArrayList<>();
        BaseEntry e = this;
        while (e != null) {
            result.add(0, e.getName());
            e = e.parent;
        }
        return result;
    }

    @Override
    public String toString() {
        return String.join("/", getPath());
    }
}
