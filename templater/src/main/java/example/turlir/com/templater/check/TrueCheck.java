package example.turlir.com.templater.check;

public class TrueCheck<T> implements Checker {

    @Override
    public boolean check(String value) {
        return true;
    }

    @Override
    public String error() {
        return null;
    }
}
