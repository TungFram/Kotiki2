package ru.itmo.kotiki2.view;

import lombok.Value;
import ru.itmo.kotiki2.model.ModelCat;

import java.time.LocalDate;
import java.util.List;

@Value
public class OwnerView {
    int id;
    String name;
    String surname;
    LocalDate dateOfBirth;
    String mail;
    List<ModelCat> cats;
}
