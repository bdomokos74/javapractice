package ood.filesearch;

import java.util.*;
import java.util.function.Predicate;

/*
Task description:
Design Unix File Search API to search file with different arguments like "name", "size" ...
The design should be maintainable and easy to add new constraints.
 */
public final class Criteria {
    private List<Predicate<Entry>> predicateList = new ArrayList<>();

    private Criteria() {
    }

    public static class Builder {
        private String name;
        private String user;
        private Long minSize;
        private Long maxSize;
        private Entry.Type type;
        private List<Criteria> orExpr;
        public Builder() {
        }

        Builder addName(String name) {
            this.name = name;
            return this;
        }
        Builder addUser(String user) {
            this.user = user;
            return this;
        }
        Builder addMinSize(long size) {
            this.minSize = size;
            return this;
        }
        Builder addMaxSize(long size) {
            this.maxSize = size;
            return this;
        }
        Builder addType(Entry.Type type) {
            this.type = type;
            return this;
        }

        Builder addOrExpression(Criteria... lst) {
            orExpr = Arrays.asList(lst);
            return this;
        }

        public Criteria build() {
            Criteria criteria = new Criteria();
            if (name != null) {
                criteria.addPredicate( e -> e.getName().equals(name));
            }
            if (user != null) {
                criteria.addPredicate( e -> e.getUser().equals(user));
            }
            if (type != null) {
                criteria.addPredicate( e -> e.getType().equals(type));
            }
            if (minSize != null) {
                criteria.addPredicate( e -> !e.isDirectory() && ((File)e).getSize()>=minSize);
            }
            if (maxSize != null) {
                criteria.addPredicate( e -> !e.isDirectory() && ((File)e).getSize()<=maxSize);
            }
            if (orExpr != null) {
                criteria.addPredicate( e -> orExpr.stream().anyMatch(
                        orExprItem -> orExprItem.criteriaMatch(e))
                );
            }
            return criteria;
        }
    }

    private void addPredicate(Predicate<Entry> pred) {
        predicateList.add(pred);
    }

    public List<Entry> find(Directory dir) {
        List<Entry> result = new ArrayList<>();

        Deque<Directory> q = new ArrayDeque<>();
        q.add(dir);
        if (predicateList.stream().allMatch(p -> p.test(dir))) {
            result.add(dir);
        }
        while(!q.isEmpty()) {
            Directory d = q.removeFirst();
            for (Entry e : d.getEntries()) {
                if (criteriaMatch(e)) {
                    result.add(e);
                }
                if (e.isDirectory()) {
                    q.add((Directory) e);
                }
            }
        }
        return result;
    }

    private boolean criteriaMatch(Entry e) {
        return predicateList.stream().allMatch(p -> p.test(e));
    }
}
