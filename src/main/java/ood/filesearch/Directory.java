package ood.filesearch;

import java.util.ArrayList;
import java.util.List;

public final class Directory extends BaseEntry {
    private List<Entry> entries;
    public static Directory createRoot() {
        return new Directory("", "root", null);
    }

    Directory(String name, String user, Directory parent) {
        super(name, user, parent, Type.DIR);
        this.entries = new ArrayList<>();
    }

    public File createFile(String name, String user) {
        File f = new File(name, user, this);

        entries.add(f);
        return f;
    }

    public Directory createDir(String name, String user) {
        Directory d = new Directory(name, user, this);
        entries.add(d);
        return d;
    }

    List<Entry> getEntries() {
        return entries;
    }
}
