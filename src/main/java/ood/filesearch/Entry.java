package ood.filesearch;

import java.time.LocalDateTime;

public interface Entry {
    String getName();

    String getUser();

    LocalDateTime getCreated();

    boolean isDirectory();

    Type getType();

    public enum Type {
        FILE, DIR
    }
}
