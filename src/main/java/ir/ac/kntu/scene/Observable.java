package ir.ac.kntu.scene;

import ir.ac.kntu.gameLogic.Observer;

public interface Observable<SELF extends Observable<?>> {
    void addObserver(Observer<SELF> observer);

    void updateAllObservers();
}

//public interface Observable {
//
//    void addObserver(Observer observer);
//
//    void updateAllObservers();
//}
