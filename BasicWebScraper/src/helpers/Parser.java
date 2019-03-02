package helpers;

public interface Parser<T> {
    Parser<T> parse();

    T getType();
}
