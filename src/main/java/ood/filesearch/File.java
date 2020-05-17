package ood.filesearch;

public final class File extends BaseEntry {
    private long size;

    File(String name, String user, Directory parent) {
        super(name, user, parent, Type.FILE);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
