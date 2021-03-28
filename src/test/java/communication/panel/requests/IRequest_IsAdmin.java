package communication.panel.requests;

import communication.IRequest;
import org.jetbrains.annotations.Contract;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class IRequest_IsAdmin implements IRequest {

    private final String uuid;
    private URL url;

    @Contract(pure = true)
    public IRequest_IsAdmin(String uuid){
        this.uuid = uuid;
        try {
            this.url = new URL("https://moderr.pl/MP/is_admin.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public URL url() {
        return url;
    }

    @Override
    public HashMap<String, String> values() {
        return new HashMap<String, String>(){
            {
                put("UUID", uuid);
            }
        };
    }
}
