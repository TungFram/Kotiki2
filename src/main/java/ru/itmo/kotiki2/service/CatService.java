package ru.itmo.kotiki2.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki2.repository.CatRepository;
import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;
import ru.itmo.kotiki2.service.interfaces.ICatService;
import ru.itmo.kotiki2.view.CatView;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
class CatService implements ICatService {

    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository CatRepository) {
        this.catRepository = CatRepository;
    }

    public ModelCat createCat(@NonNull ModelCat cat) throws Exception {
        return catRepository.saveAndFlush(cat);
    }

    public Optional<ModelCat> findCatById(int id) {
        return catRepository.findById(id);
    }

    public boolean isCatExist(int id) {
        return catRepository.existsById(id);
    }

    public boolean isCatExist(ModelCat cat) {
        return catRepository.findById(cat.getId()).isPresent();
    }

    public List<ModelCat> findAllCats() {
        return catRepository.findAll();
    }

    public void deleteById(int id) {
        Optional<ModelCat> cat = catRepository.findById(id);
        if (cat.isEmpty())
            return;
    
        catRepository.delete(cat.get());
    }

    public void deleteAll() {
        catRepository.deleteAll();
    }



    public ModelCat changeOwnerOfCat(int idOfCat, ModelOwner newOwner) throws Exception {
        Optional<ModelCat> foundedCat = findCatById(idOfCat);
        if (foundedCat.isEmpty())
            throw new Exception("Can't find cat");

        ModelCat newCat = foundedCat.get().toBuilder().withOwner(newOwner).build();
        return catRepository.saveAndFlush(newCat);
    }

    public ModelCat changeNameOfCat(int idOfCat, String name) throws Exception {
        Optional<ModelCat> foundedCat = findCatById(idOfCat);
        if (foundedCat.isEmpty())
            throw new Exception("Can't find cat");

        ModelCat cat = foundedCat.get().toBuilder().withName(name).build();
        return catRepository.saveAndFlush(cat);
    }

    public void friendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;

        ModelCat firstCat = findCatById(idOfFirstCat).orElse(null);
        ModelCat secondCat = findCatById(idOfSecondCat).orElse(null);
        if (firstCat == null || secondCat == null)
            return;

        ModelCat foundedFirstCat = secondCat.getFriends().stream()
                .filter(cat -> cat.getId() == idOfFirstCat).findFirst().orElse(null);
        ModelCat foundedSecondCat = firstCat.getFriends().stream()
                .filter(cat -> cat.getId() == idOfSecondCat).findFirst().orElse(null);

        if (foundedFirstCat == null && foundedSecondCat != null ||
                foundedFirstCat != null && foundedSecondCat == null) {
            throw new Exception("Friendship isn't bidirectional");
        }

        if (foundedFirstCat != null && foundedSecondCat != null) {
            return;
        }

        firstCat = firstCat.toBuilder().withFriend(secondCat).build();
        secondCat = secondCat.toBuilder().withFriend(firstCat).build();

        catRepository.save(firstCat);
        catRepository.saveAndFlush(secondCat);
    }

    public void unfriendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;

        ModelCat firstCat = findCatById(idOfFirstCat).orElse(null);
        ModelCat secondCat = findCatById(idOfSecondCat).orElse(null);
        if (firstCat == null || secondCat == null)
            return;

        List<ModelCat> friendsOfFirstCat = getFriendsOfCat(idOfFirstCat);
        List<ModelCat> friendsOfSecondCat = getFriendsOfCat(idOfSecondCat);

        ModelCat foundedFirstCat = friendsOfSecondCat.stream()
                .filter(cat -> cat.getId() == idOfFirstCat).findFirst().orElse(null);
        ModelCat foundedSecondCat = friendsOfFirstCat.stream()
                .filter(cat -> cat.getId() == idOfSecondCat).findFirst().orElse(null);

        if (foundedFirstCat == null && foundedSecondCat != null ||
                foundedFirstCat != null && foundedSecondCat == null) {
            throw new Exception("Friendship isn't bidirectional");
        }

        if (foundedFirstCat == null && foundedSecondCat == null) {
            return;
        }

        friendsOfFirstCat.remove(secondCat);
        friendsOfSecondCat.remove(firstCat);
        firstCat = firstCat.toBuilder().clearFriends().withFriends(friendsOfFirstCat).build();
        secondCat = secondCat.toBuilder().clearFriends().withFriends(friendsOfSecondCat).build();

        catRepository.save(firstCat);
        catRepository.saveAndFlush(secondCat);
    }

    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception {
        Optional<ModelCat> cat = findCatById(idOfCat);
        if (cat.isEmpty())
            throw new Exception("Invalid cat");

        return cat.get().getFriends();
    }
    
    public CatView cat2View(@NonNull ModelCat cat) {
        return new CatView(
                cat.getId(), 
                cat.getName(), 
                cat.getDateOfBirth(), 
                cat.getType(), 
                cat.getColor(), 
                cat.getOwner(),
                cat.getFriends());
    }
}

