package ru.itmo.kotiki2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.itmo.kotiki2.model.ModelCat;

@Repository
public interface CatRepository extends JpaRepository<ModelCat, Integer> {
}
