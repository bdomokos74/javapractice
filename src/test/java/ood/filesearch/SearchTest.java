package ood.filesearch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    Directory root;
    Directory d1;
    Directory d2;
    Directory d3;
    File f1;

    @BeforeEach
    void setUp() {
        root = Directory.createRoot();
        d1 = root.createDir("d1", "bali");
        d2 = d1.createDir("d2", "kami");
        d3 = root.createDir("d3", "boro");

        f1 = d1.createFile("test.txt", "bali");
        f1.setSize(1);

        File f1_1 = d1.createFile("test2.txt", "bali");
        f1_1.setSize(80);

        File f1_2 = d2.createFile("test3.txt", "bali");
        f1_2.setSize(1000);

        File f2 = d2.createFile("proba.png", "kami");
        f2.setSize(30);

        File f3 = d3.createFile("pikmin.txt", "kami");
        f3.setSize(50);

        File f4 = d3.createFile("duplo.doc", "boro");
        f4.setSize(100);
    }

    String toStringRep(List<Entry> lst) {
        return lst.stream().map(Object::toString).collect(Collectors.joining("|"));
    }

    @Test
    void testGetEntries() {
        List<Entry> entries = d1.getEntries();
        assertEquals("/d1/d2|/d1/test.txt|/d1/test2.txt", toStringRep(entries));
    }

    @Test
    void testFind_all() {
        Criteria crit = new Criteria.Builder().build();
        List<Entry> lst = crit.find(d1);
        assertEquals("/d1|/d1/d2|/d1/test.txt|/d1/test2.txt|/d1/d2/test3.txt|/d1/d2/proba.png", toStringRep(lst));
    }

    @Test
    void testFind_name_user_type() {
        Criteria crit = new Criteria.Builder()
                .addName("test.txt")
                .addUser("bali")
                .addType(Entry.Type.FILE)
                .build();

        List<Entry> result = crit.find(root);
        assertEquals("/d1/test.txt", toStringRep(result));
    }

    @Test
    void testFind_name_user_type_missing() {
        Criteria crit = new Criteria.Builder()
                .addName("test.txt")
                .addUser("kami")
                .addType(Entry.Type.FILE)
                .build();

        List<Entry> result = crit.find(root);
        assertEquals(0, result.size());
    }

    @Test
    void testFind_minmax() {
        Criteria crit = new Criteria.Builder()
                .addMinSize(10)
                .addMaxSize(100)
                .build();
        List<Entry> result = crit.find(root);
        assertEquals("/d1/test2.txt|/d3/pikmin.txt|/d3/duplo.doc|/d1/d2/proba.png", toStringRep(result));
    }

    @Test
    void testFind_orExpression() {
        Criteria crit = new Criteria.Builder()
                .addOrExpression(
                        new Criteria.Builder()
                                .addMinSize(100)
                                .build(),
                        new Criteria.Builder()
                                .addMaxSize(10)
                                .build()
                ).build();
        List<Entry> result = crit.find(root);
        assertEquals("/d1/test.txt|/d3/duplo.doc|/d1/d2/test3.txt", toStringRep(result));
    }
}