package com.saulpos.view.menu;

import com.saulpos.model.bean.MenuModel;
import com.saulpos.model.menu.action.LogoutMenuAction;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MenuBarGenerator {
    public static MenuBar generateMenuNode(MenuModel[] allMenu) {
        // We need to order them before adding them into the menu;
        HashSet<MenuModel> visited = new HashSet<>();
        // Let's order them using dfs

        ArrayList<MenuModel> order = new ArrayList<>();
        for (int i = 0; i < allMenu.length; i++) {
            topologicalOrder(order, visited, allMenu[i]);
        }

        // we have the order to add them;

        MenuBar menuBar = new MenuBar();

        HashMap<MenuModel, MenuItem> allMenuObjects = new HashMap<>();
        for (MenuModel menu : order) {
            MenuItem newMenu;

            if (menu.getAction() != null && !menu.getAction().isBlank()) {
                newMenu = new MenuItem(menu.getName());
                newMenu.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            //menu.getMenuAction().run();
                            String actionClassName = "com.saulpos.model.menu.action." + menu.getAction();
                            Class<?> dogClass = Class.forName(actionClassName); // convert string classname to class
                            Object actionContainerClass = dogClass.newInstance(); // invoke empty constructor

                            final String methodName = "run";
                            Method getNameMethod = actionContainerClass.getClass().getMethod(methodName);
                            getNameMethod.invoke(actionContainerClass); // explicit cast
                        }
                        catch (Exception e){
                            // TODO: decide what to do if exception occurs: log, throw, etc. . .
                        }
                    }
                });
            } else {
                newMenu = new Menu(menu.getName());
            }
            if (menu.getIcon() != null && !menu.getIcon().isBlank()) {
                newMenu.setGraphic(GlyphsDude.createIconLabel(FontAwesomeIcon.AMAZON, "", "20px", "10px", ContentDisplay.LEFT));
            }
            allMenuObjects.put(menu, newMenu);

            if (menu.getPredecessor() == null) {
                // It is a top menu
                menuBar.getMenus().add((Menu) newMenu);
            } else {
                // we add it to their parent
                ((Menu) allMenuObjects.get(menu.getPredecessor())).getItems().add(newMenu);
            }
        }

        return menuBar;
    }

    private static void topologicalOrder(ArrayList<MenuModel> order, HashSet<MenuModel> visited, MenuModel menu) {
        if (!visited.contains(menu)) {
            // I need to add my parent first.
            visited.add(menu);

            if (menu.getPredecessor() != null) {
                topologicalOrder(order, visited, menu.getPredecessor());
            }

            order.add(menu);
        }
    }
}
