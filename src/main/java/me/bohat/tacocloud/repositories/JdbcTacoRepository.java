package me.bohat.tacocloud.repositories;

import me.bohat.tacocloud.models.Ingredient;
import me.bohat.tacocloud.models.Taco;
import me.bohat.tacocloud.repositories.interfaces.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Taco save(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);
        for (Ingredient ingredient: design.getIngredients()){
            saveIngredientToTaco(ingredient,tacoId);
        }
        return null;
    }
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "INSERT INTO Taco (name, createdAt) VALUES (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }
    private void saveIngredientToTaco(Ingredient ingredient,long tacoId){
        jdbcTemplate.update(
        "INSERT INTO Taco_Ingredients (taco,ingredient) VALUES(?,?)",tacoId,ingredient.getId());
    }
}
