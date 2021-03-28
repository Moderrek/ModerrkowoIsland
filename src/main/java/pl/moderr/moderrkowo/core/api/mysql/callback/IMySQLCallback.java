package pl.moderr.moderrkowo.core.api.mysql.callback;

public interface IMySQLCallback<T> {

    void onSuccess(T result);
    void onFailure(Exception exception);

}
