package me.bohat.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import me.bohat.tacocloud.models.Ingredient;
import me.bohat.tacocloud.models.Ingredient.Type;

import me.bohat.tacocloud.models.Order;
import me.bohat.tacocloud.models.Taco;
import me.bohat.tacocloud.repositories.interfaces.IngredientRepository;
import me.bohat.tacocloud.repositories.interfaces.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepo;
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        List<Ingredient> result = ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
        return result;
    }
    @GetMapping
    public String showDesignForm(Model model){
//    List<Ingredient> ingredients = Arrays.asList(
//        new Ingredient("FLTO","Flour Tortilla", Type.WRAP),
//            new Ingredient("COTO","Corn Tortilla", Type.WRAP),
//            new Ingredient("GRBF","Ground Beef", Type.PROTEIN),
//            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//            new Ingredient("CHED", "Cheddar", Type.CHEESE),
//            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//            new Ingredient("SLSA", "Salsa", Type.SAUCE),
//            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//            );
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
    Type[] types = Ingredient.Type.values();
    for(Ingredient.Type type : types){
        model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
    }
    model.addAttribute("design",new Taco());
    return "design";
}

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
@PostMapping
    public String processDesign(@Valid Taco design, Errors errors,@ModelAttribute Order order) {
        if (errors.hasErrors()){
            return "design";
        }
        log.info("Data in ");
        Taco saved = tacoRepo.save(design);
        order.addDesign(saved);
        log.info("Processing design: " + design);
        return "redirect:/orders/current";

}
}
