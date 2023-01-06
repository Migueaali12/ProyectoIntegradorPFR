package Semana11;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        //Crear el subscriber
        PrinterSubscriber printerSubscriber = new PrinterSubscriber();

        //Funcion
        Function<String, String> toUpper = String :: toUpperCase;

        //Crear Processor
        TransformProcessor transformProcessor = new TransformProcessor(toUpper);

        //Subscripcion
        publisher.subscribe(transformProcessor);
        transformProcessor.subscribe(printerSubscriber);

        List<String> items = List.of("juan", "pedro", "mayra", "ama", "ariel", "miguel");

        //Enviar los datos a los subscriptores
        items.forEach(publisher::submit);

        Thread.sleep(1*1000);

        publisher.close();

    }

}


