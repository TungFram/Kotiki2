package ru.itmo.kotiki2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.kotiki2.model.ModelOwner;

public interface OwnerRepository extends JpaRepository<ModelOwner, Integer> {
}
