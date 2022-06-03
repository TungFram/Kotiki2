package ru.itmo.kotiki2.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki2.repository.OwnerRepository;

import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;
import ru.itmo.kotiki2.service.interfaces.IOwnerService;
import ru.itmo.kotiki2.view.OwnerView;

import java.util.List;

@Service
@Transactional
class OwnerService implements IOwnerService {

    private final OwnerRepository OwnerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        OwnerRepository = ownerRepository;
    }

    public ModelOwner createOwner(@NonNull ModelOwner entity) throws Exception {
        return OwnerRepository.saveAndFlush(entity);
    }

    public ModelOwner getOwnerById(int id) {
        return OwnerRepository.findById(id).orElseThrow();
    }

    public ModelOwner findOwnerById(int id) {
        return OwnerRepository.findById(id).orElse(null);
    }
    
    public boolean isOwnerExist(ModelOwner owner) {
        return OwnerRepository.findById(owner.getId()).isPresent();
    }

    public boolean isOwnerExist(int id) {
        return OwnerRepository.existsById(id);
    }

    public List<ModelOwner> findAllOwners() {
        return OwnerRepository.findAll();
    }

    public void deleteById(int id) {
        ModelOwner owner = getOwnerById(id);
        OwnerRepository.delete(owner);
    }
    
    public void deleteAll() {
        OwnerRepository.deleteAll();
    }

    public ModelOwner changeMail(int idOfOwner, String mail) throws Exception {
        ModelOwner owner = getOwnerById(idOfOwner);
        
        ModelOwner newOwner = owner.toBuilder().withMail(mail).build();
        return OwnerRepository.saveAndFlush(newOwner);
    }

    public ModelOwner addCat(@NonNull ModelOwner owner, @NonNull ModelCat cat) throws Exception {
        if (!isOwnerExist(owner.getId()))
            throw new Exception("Owner haven't registered!");

        owner = owner.toBuilder().withCat(cat).build();
        ModelOwner updatedOwner = OwnerRepository.saveAndFlush(owner);
        return updatedOwner;
    }

    public void deleteCat(int idOfOwner, ModelCat cat) throws Exception {
        if(cat == null)
            return;

        ModelOwner owner = getOwnerById(idOfOwner);

        List<ModelCat> cats = findCatsOfOwner(idOfOwner);
        ModelCat foundedCat = cats.stream().filter(someCat -> someCat.getId() == cat.getId())
                .findFirst().orElse(null);
        if (foundedCat == null)
            throw new Exception("Can't find owner's cat");
        
        cats.remove(foundedCat);

        owner = owner.toBuilder().clearCats().withCats(cats).build();
        OwnerRepository.saveAndFlush(owner);
    }

    public List<ModelCat> findCatsOfOwner(int idOfOwner) throws Exception {
        ModelOwner owner = getOwnerById(idOfOwner);
        return owner.getCats();
    }
    
    public OwnerView owner2View(@NonNull ModelOwner owner) {
        return new OwnerView(
                owner.getId(), 
                owner.getName(), 
                owner.getSurname(), 
                owner.getDateOfBirth(), 
                owner.getMail(), 
                owner.getCats());
    }
}
