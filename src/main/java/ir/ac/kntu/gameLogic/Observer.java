package ir.ac.kntu.gameLogic;

import ir.ac.kntu.scene.Observable;

public interface Observer<T extends Observable<?>> {
    void update(T observable);
}


//public interface Observer {
//    void update(Observable observable);
//
//}
