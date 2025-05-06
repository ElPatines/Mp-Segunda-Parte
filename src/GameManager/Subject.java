package GameManager;

public interface Subject {
    public void addObserver(Observer user);

    public void removeObserver(Observer user);

    public void notifyObservers(Player result);
}
