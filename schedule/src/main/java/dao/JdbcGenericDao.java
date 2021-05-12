package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import model.Entity;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public abstract class JdbcGenericDao<E extends Entity> implements Dao<E> {

    protected final DataSource dataSource;

    /**
     * 
     * @param id
     * @return null if specified id not found
     */
    @SneakyThrows
    @Override
    public E findById(Long id) {
        try (Connection con = dataSource.getConnection();
                PreparedStatement st = con.prepareStatement(getFindByIdSql())) {
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {

                EntityMapper<E> entityMapper = getEntityMapper();
                return entityMapper.fromResultSet(resultSet);
            }
        }
        return null;

    }

    protected abstract String getCreateSql();

    protected abstract String getFindByIdSql();

    protected abstract EntityMapper<E> getEntityMapper();

    @Override
    @SneakyThrows
    public E create(E entity) {
        try (Connection con = dataSource.getConnection();
                PreparedStatement st = con.prepareStatement(getCreateSql(),
                        Statement.RETURN_GENERATED_KEYS)) {

            getEntityMapper().fillStatement(st, entity);

            int rows = st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
            return entity;
        }
    }

}
