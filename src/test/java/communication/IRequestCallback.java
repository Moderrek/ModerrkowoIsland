package communication;

public interface IRequestCallback<T> {

    void onSuccess(T result);
    void onFailure(Exception exception);

}
