package systems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crafting {
    private Map<List<String>, String> recipes;

    public Crafting() { // Crafting System ist noch nicht implementiert?
        recipes = new HashMap<>();
        // Example recipes: list of input item names to output item name
        recipes.put(List.of("Holz", "Stein"), "Steinbeil");
        recipes.put(List.of("Eisenerz", "Holz"), "Eisenschwert");
        recipes.put(List.of("Heilkräuter", "Flasche"), "Heiltrank");
    }

    public String craft(List<String> inputItems) {
        for (Map.Entry<List<String>, String> entry : recipes.entrySet()) {
            List<String> recipeInputs = entry.getKey();
            if (inputItems.containsAll(recipeInputs) && recipeInputs.containsAll(inputItems)) {
                return entry.getValue();
            }
        }
        return null; // No matching recipe
    }
}
