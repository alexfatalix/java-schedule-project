package dao;

import static org.junit.Assert.*;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import model.Lesson;

import lombok.SneakyThrows;


public class LessonDaoTest extends DataSourceBasedDBTestCase {
    private JdbcDataSource ds;
    
    private LessonDao dao = new LessonDao(getDataSource());

    public void testFindById() {
        Lesson lesson = dao.findById(Long.valueOf(1L));
        assertNotNull(lesson);
        assertEquals(Long.valueOf(1L), lesson.getId());
        assertEquals("SKU_lesson_1", lesson.getSku());
    }
    
    public void testFindByIdNull() {
        Lesson lesson = dao.findById(Long.valueOf(5L));
        assertNull(lesson);
    }

    @SneakyThrows
    public void testCreate() {
        
        Lesson lesson = Lesson.builder().sku("SKU002").build();
        
        lesson = dao.create(lesson);
        assertNotNull(lesson);
        assertNotNull(lesson.getId());
        
        assertEquals(5, getConnection().createDataSet().getTable("lesson").getRowCount());
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
                .getClassLoader().getResourceAsStream("dataset/lesson.xml"));
    }

}
