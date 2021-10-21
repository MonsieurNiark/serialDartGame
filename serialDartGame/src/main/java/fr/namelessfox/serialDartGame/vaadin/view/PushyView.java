package fr.namelessfox.serialDartGame.vaadin.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

@Push
@Route("push")
public class PushyView extends VerticalLayout {
    private FeederThread thread;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        add(new Span("Waiting for updates"));

        // Start the data feed thread
        thread = new FeederThread(attachEvent.getUI(), this);
        thread.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // Cleanup
        thread.interrupt();
        thread = null;
    }

    private static class FeederThread extends Thread {
        private final UI ui;
        private final PushyView view;

        private int count = 0;

        public FeederThread(UI ui, PushyView view) {
            this.ui = ui;
            this.view = view;
        }

        @Override
        public void run() {
            try {
                // Update the data for a while
                while (count < 10) {
                    // Sleep to emulate background work
                    Thread.sleep(500);
                    String message = "This is update " + count++;

                    ui.access(() -> view.add(new Span(message)));
                }

                // Inform that we are done
                ui.access(() -> {
                    view.add(new Span("Done updating"));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}