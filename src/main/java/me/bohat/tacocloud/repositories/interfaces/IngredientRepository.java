package me.bohat.tacocloud.repositories.interfaces;

import me.bohat.tacocloud.models.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient fineOne(String id);
    Ingredient save(Ingredient ingredient);
}
