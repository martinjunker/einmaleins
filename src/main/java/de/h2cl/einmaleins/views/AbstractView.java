package de.h2cl.einmaleins.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.Title;
import de.h2cl.einmaleins.domain.TitleRepository;

@RequiredArgsConstructor
public abstract class AbstractView extends HorizontalLayout {
    
    private final TitleRepository titleRepository;

    Image createImage(final String titleId) {
        var title = titleRepository.findById(titleId);
        var image = new Image(title.map(Title::getImgUrl).orElse("https://"),
                "Image of " + title.map(Title::getName).orElse("No Name"));
        image.setHeight(150, Unit.PIXELS);
        return image;
    }
}
