package de.h2cl.einmaleins.views.helloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "hello")
@PageTitle("Hello World")
public class HelloWorldView extends HorizontalLayout {

    private final TextField name;
    private final Button sayHello;

    public HelloWorldView() {
        addClassName("hello-world-view");
        Label text = new Label("Spieglein Spieglein an der Wand. Wer ist die schönste im ganzen Land?");
        name = new TextField();
        sayHello = new Button(" ist die schönste");
        add(text, name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            if(name.getValue().equalsIgnoreCase("Belén")) {
                Notification.show("Richtig!!!" );
            } else {
                Notification.show("Nee nee hinter den Bergen, weißte Bescheid!");
            }
        });
    }

}
