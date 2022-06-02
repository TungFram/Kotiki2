package ru.itmo.kotiki2.service.cat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki2.repository.CatRepository;
import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;

import java.util.List;
import java.util.Optional;

@Service
public class CatService implements ICatService{

    private final CatRepository CatRepository;

    @Autowired
    public CatService(CatRepository CatRepository) {
        this.CatRepository = CatRepository;
    }

    public ModelCat createCat(ModelCat cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat.");

        return CatRepository.saveAndFlush(cat);
    }

    public Optional<ModelCat> findCatById(int id) {
        return CatRepository.findById(id);
    }

    public boolean isCatExist(int id) {
        return CatRepository.existsById(id);
    }

    public List<ModelCat> findAllCats() {
        return CatRepository.findAll();
    }

    public void deleteById(int id) {
        Optional<ModelCat> cat = CatRepository.findById(id);
        if (cat.isEmpty())
            return;
    
        CatRepository.delete(cat.get());
    }

    public void deleteAll() {
        CatRepository.deleteAll();
    }



    public ModelCat changeOwnerOfCat(int idOfCat, ModelOwner newOwner) throws Exception {
        Optional<ModelCat> foundedCat = findCatById(idOfCat);
        if (foundedCat.isEmpty())
            throw new Exception("Can't find cat");

        ModelCat newCat = foundedCat.get().toBuilder().withOwner(newOwner).build();
        return CatRepository.saveAndFlush(newCat);
    }

    public ModelCat changeNameOfCat(int idOfCat, String name) throws Exception {
        Optional<ModelCat> foundedCat = findCatById(idOfCat);
        if (foundedCat.isEmpty())
            throw new Exception("Can't find cat");

        ModelCat cat = foundedCat.get().toBuilder().withName(name).build();
        return CatRepository.saveAndFlush(cat);
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

        CatRepository.save(firstCat);
        CatRepository.saveAndFlush(secondCat);
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

        CatRepository.save(firstCat);
        CatRepository.saveAndFlush(secondCat);
    }

    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception {
        Optional<ModelCat> cat = findCatById(idOfCat);
        if (cat.isEmpty())
            throw new Exception("Invalid cat");

        return cat.get().getFriends();
    }
}

