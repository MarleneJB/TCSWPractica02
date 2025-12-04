package org.uv.tcswpractica02;

import java.util.List;


public interface IDAOGeneral<T, ID> {
    
    
    public boolean create(T pojo);
    
    
    public T delete(ID id);
    
    
    public T update(T pojo, ID id);
    
   
    public T findById(ID id);
    
   
    public List<T> findAll();
}