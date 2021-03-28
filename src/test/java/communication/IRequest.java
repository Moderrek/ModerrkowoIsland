package communication;

import java.net.URL;
import java.util.HashMap;

public interface IRequest {

    URL url();
    HashMap<String, String> values();

}
