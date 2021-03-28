package communication;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.Contract;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    @Contract(pure = true)
    public static boolean parseResponse(String response){
        switch (response.toLowerCase()){
            case "true":
            case "1":
                return true;
        }
        return false;
    }

    @Contract(pure = true)
    public static void getRequestData(IRequest request, IRequestCallback<String> callback){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request.url().toString());
        List<NameValuePair> params = new ArrayList<>();
        for(String key : request.values().keySet()){
            String value = request.values().get(key);
            params.add(new BasicNameValuePair(key, value));
            //System.out.println("Added param ( " + key + "=" + value + " )");
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            callback.onFailure(e);
            return;
        }
        CloseableHttpResponse response;
        try {
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(e);
            return;
        }
        if(response.getStatusLine().getStatusCode() != 200){
            String s = "Status code InValid (" + response.getStatusLine().getStatusCode() + ")";
            System.out.println(s);
            callback.onFailure(new Exception(s));
            return;
        }
        try {
            //System.out.println("Reading received");
            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8), 8);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            sb.append(line);
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            is.close();
            //System.out.println("Received: " + result);
            callback.onSuccess(result);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Error received");
            callback.onFailure(e);
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(e);
        }
    }

}
