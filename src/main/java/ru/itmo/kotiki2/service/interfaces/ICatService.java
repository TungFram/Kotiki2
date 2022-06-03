package ru.itmo.kotiki2.service.interfaces;

import ru.itmo.kotiki2.model.ModelCat;

import java.util.List;
import java.util.Optional;

public interface ICatService {
    
    public ModelCat createCat(ModelCat cat) throws Exception;
    
    public Optional<ModelCat> findCatById(int id);
    public List<ModelCat> findAllCats();

    public void deleteById(int id);
    public void deleteAll();

    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception;

    public void friendCats(int idOfFirstCat, int idOfSecondCat)
            throws Exception;
    public void unfriendCats(int idOfFirstCat, int idOfSecondCat)
            throws Exception;
}
