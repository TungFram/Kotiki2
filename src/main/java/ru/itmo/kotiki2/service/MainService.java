package ru.itmo.kotiki2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import ru.itmo.kotiki2.enums.CatColor;
import ru.itmo.kotiki2.enums.CatType;

import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;
import ru.itmo.kotiki2.view.CatView;
import ru.itmo.kotiki2.view.OwnerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MainService {

    private final OwnerService ownerService;
    private final CatService catService;

    @Autowired
    public MainService(OwnerService ownerService, CatService catService) {
        this.ownerService = ownerService;
        this.catService = catService;
    }
    
    public ModelCat registerCat(
            String name,
            LocalDate dateBirth,
            CatType type,
            CatColor color,
            int idOfOwner,
            List<ModelCat> friends) throws Exception {

        ModelOwner owner = ownerService.findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't register cat because his owner doesn't registered.");
        if (friends == null)
            friends = new ArrayList<>();

        ModelCat cat = ModelCat.createBuilder()
                .withName(name)
                .withDateOfBirth(dateBirth)
                .withType(type)
                .withColor(color)
                .withOwner(owner) //<--
                .withFriends(friends)
                .build();

        ModelOwner updatedOwner = ownerService.addCat(owner, cat);

        return catService.createCat(cat);
    }

    public ModelOwner registerOwner(
            String name,
            String surname,
            String mail,
            LocalDate dateBirth,
            List<ModelCat> cats) throws Exception {
        if (name == null)
            throw new Exception("Person must have a name!");
        if (cats == null)
            cats = new ArrayList<>();

        ModelOwner owner = ModelOwner.createBuilder()
                .withName(name)
                .withSurname(surname)
                .withMail(mail)
                .withDateOfBirth(dateBirth)
                .withCats(cats)
                .build();

        return ownerService.createOwner(owner);
    }

    public List<ModelCat> getCatsOfOwner(int idOfOwner) throws Exception {
        return ownerService.findCatsOfOwner(idOfOwner);
    }

    public List<ModelCat> getFriendsOfCat(int idOfCat) throws Exception {
        return catService.getFriendsOfCat(idOfCat);
    }

    public void friendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.friendCats(idOfFirstCat, idOfSecondCat);
    }

    public void unfriendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.unfriendCats(idOfFirstCat, idOfSecondCat);
    }

    public void friendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;

        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.friendCats(leftId, rightId);
            }
        }
    }

    public void unfriendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;

        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.unfriendCats(leftId, rightId);
            }
        }
    }

    public ModelOwner changeMailOfOwner(int idOfOwner, String mail) throws Exception {
        return ownerService.changeMail(idOfOwner, mail);
    }

    public ModelCat changeNameOfCat(int idOfCat, String name) throws Exception {
        return catService.changeNameOfCat(idOfCat, name);
    }

    public ModelCat changeOwnerOfCat(int idOfCat, int idOfNewOwner) throws Exception {
        if (!catService.isCatExist(idOfCat))
            throw new Exception("Cat not found");
        
        ModelOwner newOwner = ownerService.getOwnerById(idOfNewOwner);
        ModelCat cat = getCatById(idOfCat);
        
        ownerService.deleteCat(cat.getOwner().getId(), cat);
        return catService.changeOwnerOfCat(idOfCat, newOwner);
    }

    
    public boolean isCatExist(int id) {
        return catService.isCatExist(id);
    }

    public boolean isCatExist(ModelCat cat) {
        return catService.isCatExist(cat);
    }
    
    public ModelCat findCatById(int id) {
        return catService.findCatById(id).orElse(null);
    }

    public ModelCat getCatById(int id) {
        return catService.findCatById(id).orElseThrow();
    }

    public List<ModelCat> findCatsById(List<Integer> ids) {
        List<ModelCat> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findCatById(id));
        }

        return res;
    }

    
    public boolean isOwnerExist(int id) {
        return ownerService.isOwnerExist(id);
    }

    public boolean isOwnerExist(ModelOwner owner) {
        return ownerService.isOwnerExist(owner);
    }
    
    public ModelOwner findOwnerById(int id) {
        return ownerService.findOwnerById(id);
    }

    public ModelOwner getOwnerById(int id) {
        return ownerService.getOwnerById(id);
    }

    public List<ModelOwner> findOwnersById(List<Integer> ids) {
        List<ModelOwner> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findOwnerById(id));
        }

        return res;
    }
    
    public List<ModelCat> getAllCats() {
        return catService.findAllCats();
    }

    public List<ModelOwner> getAllOwners() {
        return ownerService.findAllOwners();
    }

    public void deleteCatById(int id) {
        catService.deleteById(id);
    }

    public void deleteOwnerById(int id) {
        ownerService.deleteById(id);
    }

    public void deleteCatsById(List<Integer> ids) {
        for(int id : ids) {
            catService.deleteById(id);
        }
    }

    public void deleteOwnersById(List<Integer> ids) {
        for(int id : ids) {
            ownerService.deleteById(id);
        }
    }

    public void deleteAllOwners() {
        ownerService.deleteAll();
    }

    public void deleteAllCats() {
        catService.deleteAll();
    }
    
    public OwnerView convertOwner2View(ModelOwner owner) {
        return ownerService.owner2View(owner);
    }
    
    public CatView convertCat2View(ModelCat cat) { 
        return catService.cat2View(cat);
    }
    
    public List<OwnerView> convertOwnersToViews(List<ModelOwner> owners) {
        List<OwnerView> res = new ArrayList<>();
        for (ModelOwner owner : owners)
            res.add(ownerService.owner2View(owner));
        return res;
    }

    public List<CatView> convertCatsToViews(List<ModelCat> cats) {
        List<CatView> res = new ArrayList<>();
        for (ModelCat cat : cats)
            res.add(catService.cat2View(cat));
        return res;
    }
}













