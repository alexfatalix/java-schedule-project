package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import model.Lesson;

import lombok.SneakyThrows;

public class LessonDao extends JdbcGenericDao<Lesson> {

    public LessonDao(DataSource dataSource) {
        super(dataSource);
    }

    private static final String SQL_FIND_BY_ID = "SELECT id, sku FROM lesson WHERE id = ?";

    private static final String SQL_CREATE = "INSERT INTO lesson (sku) VALUES (?)";

    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }

    @Override
    protected EntityMapper<Lesson> getEntityMapper() {
        
        return new EntityMapper<Lesson>() {
            
            @Override
            @SneakyThrows
            public Lesson fromResultSet(ResultSet resultSet) {
                Lesson lesson = Lesson.builder().id(resultSet.getLong("id"))
                      .sku(resultSet.getString("sku")).build();
                return lesson;
            }

            @Override
            @SneakyThrows
            public void fillStatement(PreparedStatement statement,
                    Lesson entity) {
                statement.setString(1, entity.getSku());
            }
        };
    }

}
