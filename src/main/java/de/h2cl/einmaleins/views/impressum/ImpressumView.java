package de.h2cl.einmaleins.views.impressum;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.h2cl.einmaleins.views.MainView;

@PageTitle("Game Exchange | Impressum")
@Route(value = "impressum", layout = MainView.class)
public class ImpressumView extends VerticalLayout {

    public ImpressumView() {
        add(new H3("Impressum"));
    }

}
