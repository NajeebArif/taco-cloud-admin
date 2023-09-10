package narif.poc.projects.tacocloudadmin;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

public class RestIngredientService {

    public static final String DATA_API_INGREDIENTS = "http://localhost:8080/data-api/ingredients";
    private final RestTemplate restTemplate;

    public RestIngredientService(final String accessToken){
        this.restTemplate = new RestTemplate();
        if(accessToken!=null){
            this.restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        }
    }

    public DataHolder findAll() {
        return restTemplate.getForObject(DATA_API_INGREDIENTS, DataHolder.class);
    }

    public Ingredient addIngredient(Ingredient ingredient){
        ResponseEntity<Ingredient> ingredientDtoResponseEntity = restTemplate.postForEntity(DATA_API_INGREDIENTS, ingredient, Ingredient.class);
        Ingredient body = ingredientDtoResponseEntity.getBody();
        System.out.println("INGREDIENT ADDED>"+body);
        return body;
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer "+accessToken);
            return execution.execute(request, body);
        };
    }
}
