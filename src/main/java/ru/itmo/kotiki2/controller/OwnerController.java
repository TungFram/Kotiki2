package ru.itmo.kotiki2.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki2.model.ModelOwner;
import ru.itmo.kotiki2.service.MainService;
import ru.itmo.kotiki2.view.CatView;
import ru.itmo.kotiki2.view.OwnerView;

import java.util.List;

@RestController
@RequestMapping(value = "/owners")
public class OwnerController {

    private final MainService service;

    @Autowired
    public OwnerController(MainService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerView registerOwner(@RequestBody @NonNull OwnerView owner) throws Exception {
        return service.convertOwner2View(service.registerOwner(
                owner.getName(),
                owner.getSurname(),
                owner.getMail(),
                owner.getDateOfBirth(),
                owner.getCats()));
    }

    @GetMapping("/all")
    public List<OwnerView> getAllOwners() {
        return service.convertOwnersToViews(service.getAllOwners());
    }

    @GetMapping("/getByID/{idOfOwner}")
    public OwnerView findCatById(@PathVariable int idOfOwner) {
        return !service.isOwnerExist(idOfOwner)
                ? null
                : service.convertOwner2View(service.getOwnerById(idOfOwner));
    }

    @PutMapping("/updateOwner/{idOfOwner}")
    public OwnerView updateOwner(@RequestBody OwnerView newOwnerView, @PathVariable int idOfOwner) throws Exception {
        if (!service.isOwnerExist(idOfOwner))
            throw new Exception("Owner doesn't exist!");

        ModelOwner foundedOwner = service.getOwnerById(idOfOwner);

        return service.convertOwner2View(
                service.changeMailOfOwner(idOfOwner, newOwnerView.getMail()));
    }

    @DeleteMapping("/deleteOwnerById/{idOfOwner}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnerById(@PathVariable int idOfOwner) {
        service.deleteOwnerById(idOfOwner);
    }

    @DeleteMapping("/deleteAllOwners")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllOwners() {
        service.deleteAllOwners();
    }
    
    @GetMapping("/hisCats/{idOfOwner}")
    public List<CatView> getCatsOfOwner(@PathVariable int idOfOwner) throws Exception {
        if (!service.isOwnerExist(idOfOwner))
            throw new Exception("Owner doesn't exist!");
        
        return service.convertCatsToViews(service.getCatsOfOwner(idOfOwner));
    }
}

















