import communication.DataManager;
import communication.IRequestCallback;
import communication.panel.requests.IRequest_IsAdmin;

public class DataManagerTest {


    private static final String ModerrkowoPanel_MODERR_UUID = "b24e41ab-8e0d-4c2f-843a-c6cf762be630";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("0 ms");
        System.out.println("DataManagerTest");
        System.out.println("Started request >> " + (System.currentTimeMillis() - start) + " ms");
        DataManager.getRequestData(new IRequest_IsAdmin(ModerrkowoPanel_MODERR_UUID), new IRequestCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("Received >> " + (System.currentTimeMillis() - start) + " ms");
                System.out.println("Received raw result: " + result);
                System.out.println("Received: " + DataManager.parseResponse(result));
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("Received >> " + (System.currentTimeMillis() - start) + " ms");
                System.out.println("Received exception: " + exception);
            }
        });
    }
}
