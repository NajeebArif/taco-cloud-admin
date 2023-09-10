package narif.poc.projects.tacocloudadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/ingredients")
@RequiredArgsConstructor
public class AdminController {

    private final RestIngredientService restIngredientService;

    @SneakyThrows
    @GetMapping
    public String ingredientsAdmin(Model model) {
        model.addAttribute("ingredients", restIngredientService.findAll().get_embedded().getIngredients());
        return "ingredientsAdmin";
    }

    @PostMapping
    public String addIngredient(Ingredient ingredient) {
        System.out.println("ADDING A NEW INGREDIENT: "+ingredient);
        restIngredientService.addIngredient(ingredient);
        return "redirect:/admin/ingredients";
    }

}
