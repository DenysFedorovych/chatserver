package org.bitbucket.micro.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This interface use for map many entity for my sql database.
 *
 * @param <T> your entity
 */
public interface CustomRowExtractor<T> {
    T extract(ResultSet rs) throws SQLException;
}
