package com.example06.rxjava;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ObservableExample {
    public static void main(String[] args) {
        // 배압 조절 불가능
        getItems()
                .subscribe(new SimpleObserver());
    }

    private static Observable<Integer> getItems() {
        return Observable.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
