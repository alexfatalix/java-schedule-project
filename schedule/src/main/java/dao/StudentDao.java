package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import model.Student;

import lombok.SneakyThrows;

public class StudentDao extends JdbcGenericDao<Student> {

    public StudentDao(DataSource dataSource) {
        super(dataSource);
    }

    private static final String SQL_FIND_BY_ID = "SELECT id, name FROM student WHERE id = ?";

    private static final String SQL_CREATE = "INSERT INTO student (name) VALUES (?)";



    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

    @Override
    protected EntityMapper<Student> getEntityMapper() {
        return new EntityMapper<Student>() {
            
            @Override
            @SneakyThrows
            public Student fromResultSet(ResultSet resultSet) {
                return Student.builder().id(resultSet.getLong("id"))
                        .name(resultSet.getString("name")).build();
            }

            @Override
            @SneakyThrows
            public void fillStatement(PreparedStatement statement,
                    Student entity) {
                statement.setString(1, entity.getName());
                
            }
        };
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }
}
