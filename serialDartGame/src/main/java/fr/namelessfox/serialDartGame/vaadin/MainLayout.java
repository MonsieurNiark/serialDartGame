package fr.namelessfox.serialDartGame.vaadin;

import com.vaadin.flow.component.dependency.CssImport;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.RouterLayout;

import fr.namelessfox.serialDartGame.vaadin.view.HeaderView;

@Push
@CssImport("./styles/global.css")
public class MainLayout extends VerticalLayout implements RouterLayout{

	private final HeaderView header;

	public MainLayout() {
		header = new HeaderView();
		setMargin(false);
		setPadding(false);
		setSpacing(false);
		add(header);
		setSizeFull();
	}
}
