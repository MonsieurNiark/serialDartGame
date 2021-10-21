package fr.namelessfox.serialDartGame.vaadin.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import fr.namelessfox.serialDartGame.vaadin.config.ConstantesRoutes;

@CssImport(value = "./styles/header.css")
public class HeaderView extends VerticalLayout {

	public HeaderView() {
		this.setWidthFull();
		this.setAlignItems(Alignment.CENTER);
		this.setHeight("60px");
		this.setClassName("header");
		Button titleButton = new Button("Serial Dart Game");
		titleButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// TODO Auto-generated method stub
				event.getSource().getUI().get().navigate(ConstantesRoutes.HOME);
			}
		});
		this.add(titleButton);
	}
}
