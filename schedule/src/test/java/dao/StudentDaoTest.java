package dao;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import model.Student;

import lombok.SneakyThrows;

public class StudentDaoTest extends DataSourceBasedDBTestCase {
    private JdbcDataSource ds;

    public void testFindById() {
        JdbcGenericDao<Student> dao = new StudentDao(getDataSource());
        Student student = dao.findById(Long.valueOf(1L));
        assertNotNull(student);
        assertEquals(Long.valueOf(1L), student.getId());
        assertEquals("Dimas", student.getName());
    }
    public void testFindByIdNull() {
        JdbcGenericDao<Student> dao = new StudentDao(getDataSource());
        Student student = dao.findById(Long.valueOf(5L));
        assertNull(student);
    }

    @SneakyThrows
    public void testCreate() {
        JdbcGenericDao<Student> dao = new StudentDao(getDataSource());
        
        Student student = Student.builder().name("Strange").build();
        
        student = dao.create(student);
        assertNotNull(student);
        assertNotNull(student.getId());
        
        assertEquals(5, getConnection().createDataSet().getTable("student").getRowCount());
    }

    @Override
    protected DataSource getDataSource() {
        if (ds == null) {
            String file = getClass().getClassLoader().getResource("schema.sql").getFile();
            
            ds = new JdbcDataSource();
            ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM '" + file + "'");
            ds.setUser("sa");
            ds.setPassword("");
        }
        return ds;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader().getResourceAsStream("dataset/student.xml"));
    }

}
