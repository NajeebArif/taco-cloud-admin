package narif.poc.projects.tacocloudadmin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DataValue {

    private List<Ingredient> ingredients;
}
