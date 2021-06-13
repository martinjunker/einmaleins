package de.h2cl.einmaleins.security;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import de.h2cl.einmaleins.views.games.MyGamesView;
import de.h2cl.einmaleins.views.login.LoginView;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            var ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter); //
        });
    }


    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event
     *            before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {
        if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
            if (SecurityUtils.isUserLoggedIn()) {
                Notification.show("Keine Rechte.");
                event.rerouteTo(MyGamesView.class);
            } else {
                event.rerouteTo(LoginView.class);
            }
        }
    }
}