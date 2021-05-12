package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface EntityMapper<E> {
    
    E fromResultSet(ResultSet resultSet);
    
    void fillStatement(PreparedStatement statement, E entity);

}
