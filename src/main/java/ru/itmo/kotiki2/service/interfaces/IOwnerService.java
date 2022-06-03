package ru.itmo.kotiki2.service.interfaces;

import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;

import java.util.List;

public interface IOwnerService {
    
    public ModelOwner createOwner(ModelOwner entity) throws Exception;
    
    public ModelOwner findOwnerById(int id);
    public ModelOwner getOwnerById(int id);
    public List<ModelOwner> findAllOwners();
    
    public void deleteById(int id);
    public void deleteAll();
    
    public List<ModelCat> findCatsOfOwner(int idOfOwner) throws Exception;
    public ModelOwner addCat(ModelOwner owner, ModelCat cat) throws Exception;
    
}
