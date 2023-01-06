package Semana11;

import java.util.concurrent.Flow;

public class PrinterSubscriber implements Flow.Subscriber<String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Suscrito");
        this.subscription = subscription;
        this.subscription.request(3);
    }

    @Override
    public void onNext(String item) {
        System.out.printf("Valor %s\n", item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Todo procesado");

    }
}
