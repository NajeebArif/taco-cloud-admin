package narif.poc.projects.tacocloudadmin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataHolder {

    private DataValue _embedded;
}
