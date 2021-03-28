package pl.moderr.moderrkowo.core.api.mysql.callback;

public interface IMySQLUpdateCallback {

    void startUpdate();
    void onUpdated();
    void onFailure(Exception exception);

}
