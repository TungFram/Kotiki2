package ru.itmo.kotiki2.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.service.MainService;
import ru.itmo.kotiki2.view.CatView;
import ru.itmo.kotiki2.view.OwnerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatController {

    private final MainService service;

    @Autowired
    public CatController(MainService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CatView registerCat(@RequestBody @NonNull CatView cat) throws Exception {
        return service.convertCat2View(service.registerCat(
                cat.getName(),
                cat.getDateOfBirth(),
                cat.getType(),
                cat.getColor(),
                cat.getOwner().getId(),
                cat.getFriends()));
    }
    
    @GetMapping("/all")
    public List<CatView> getAllCats() {
        return service.convertCatsToViews(service.getAllCats());
    }

    @GetMapping("/getByID/{idOfCat}")
    public CatView findCatById(@PathVariable int idOfCat) {
        return !service.isCatExist(idOfCat) 
                ? null 
                : service.convertCat2View(service.getCatById(idOfCat));
    }

    @PutMapping("/updateCat/{idOfCat}")
    public CatView updateCat(@RequestBody CatView newCatView, @PathVariable int idOfCat) throws Exception {
        if (!service.isCatExist(idOfCat))
            throw new Exception("Cat doesn't exist!");
        
        ModelCat foundedCat = service.getCatById(idOfCat);
        
        if (!Objects.equals(foundedCat.getName(), newCatView.getName()))
            return service.convertCat2View(service.changeNameOfCat(idOfCat, newCatView.getName()));
        
        if (foundedCat.getOwner().getId() != newCatView.getOwner().getId())
            return service.convertCat2View(service.changeOwnerOfCat(idOfCat, newCatView.getOwner().getId()));
        
        return null;
    }

    @DeleteMapping("/deleteCatById/{idOfCat}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatById(@PathVariable int idOfCat) {
        service.deleteCatById(idOfCat);
    }
    
    @DeleteMapping("/deleteAllCats")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCats() {
        service.deleteAllCats();
    }


    @GetMapping("/itsOwner/{idOfCat}")
    public OwnerView getOwnerOfCat(@PathVariable int idOfCat) throws Exception {
        if (!service.isCatExist(idOfCat))
            throw new Exception("Cat doesn't exist!");
        
        ModelCat foundedCat = service.getCatById(idOfCat);
        
        return service.convertOwner2View(foundedCat.getOwner());
    }

    @GetMapping("/friendsOfCat/{idOfCat}")
    public List<CatView> getFriendsOfCat(@PathVariable int idOfCat) throws Exception {
        if (!service.isCatExist(idOfCat))
            throw new Exception("Cat doesn't exist!");
        
        return service.convertCatsToViews(service.getFriendsOfCat(idOfCat));
    }

    @PostMapping("/friendPairCats/{idOfFirstCat}_{idOfSecondCat}")
    public void friendPairCats(@PathVariable int idOfFirstCat, @PathVariable int idOfSecondCat) throws Exception {
        service.friendCats(idOfFirstCat, idOfSecondCat);
    }

    @PostMapping("/unfriendPairCats/{idOfFirstCat}_{idOfSecondCat}")
    public void unfriendPairCats(@PathVariable int idOfFirstCat, @PathVariable int idOfSecondCat) throws Exception {
        service.unfriendCats(idOfFirstCat, idOfSecondCat);
    }

    @GetMapping("/allFriends")
    public Map<CatView, List<CatView>> getAllFriends() {
        Map<CatView, List<CatView>> res = new HashMap<>();
        
        for (ModelCat cat : service.getAllCats()) {
            res.put(service.convertCat2View(cat), service.convertCatsToViews(cat.getFriends()));
        }
        
        return res;
    }
}
