package dao;

import model.Entity;

public interface Dao<E extends Entity> {
    
    E findById(Long id);
    
    E create(E entity);

}
