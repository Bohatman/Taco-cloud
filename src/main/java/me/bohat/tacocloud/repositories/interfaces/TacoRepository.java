package me.bohat.tacocloud.repositories.interfaces;

import me.bohat.tacocloud.models.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}
