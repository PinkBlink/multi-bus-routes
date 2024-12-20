package org.multi.routes.model;

public abstract class BaseEntity {
    private static int idCounter = 21231;
    private int id;

    public BaseEntity() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();
}