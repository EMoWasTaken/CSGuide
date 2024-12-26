package org.data;

import java.util.*;

public class Tag {
    public static List<Tag> tags = new ArrayList<>();

    public String name;
    public float popularity = 0.5f;

    public Tag(String name) {
        tags.add(this);
        this.name = name;
    }

    public static Tag findByName(String name) {
        for (Tag t : tags)
            if (t.name.equals(name))
                return t;
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
