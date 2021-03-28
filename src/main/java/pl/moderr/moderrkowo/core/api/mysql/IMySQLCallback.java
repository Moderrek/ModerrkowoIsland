package pl.moderr.moderrkowo.core.api.mysql;

public interface IMySQLCallback<T> {

    void onSuccess(T result);
    void onFailure(Exception exception);

}
