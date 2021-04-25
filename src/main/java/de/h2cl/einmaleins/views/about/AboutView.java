package de.h2cl.einmaleins.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route(value = "about")
@PageTitle("About")
public class AboutView extends Div {

    public AboutView() {
        addClassName("about-view");
        add(new Text("Content placeholder"));
    }

}
