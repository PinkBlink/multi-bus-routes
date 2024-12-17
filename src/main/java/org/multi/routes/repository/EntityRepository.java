package org.multi.routes.repository;

import org.multi.routes.model.BaseEntity;

import java.util.List;

public interface EntityRepository<T extends BaseEntity> {
    List<T> getAll();
}
