package ru.itmo.kotiki2.view;

import lombok.Value;
import ru.itmo.kotiki2.enums.CatColor;
import ru.itmo.kotiki2.enums.CatType;
import ru.itmo.kotiki2.model.ModelCat;
import ru.itmo.kotiki2.model.ModelOwner;

import java.time.LocalDate;
import java.util.List;

@Value
public class CatView {
    int id;
    String name;
    LocalDate dateOfBirth;
    CatType type;
    CatColor color;
    ModelOwner owner;
    List<ModelCat> friends;
}
