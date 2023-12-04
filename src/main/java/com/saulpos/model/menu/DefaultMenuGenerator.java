/*
 * Copyright (C) 2012-2023 Saúl Hidalgo <saulhidalgoaular at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.saulpos.model.menu;

import com.saulpos.model.bean.MenuModel;

import java.util.ArrayList;
import java.util.HashSet;

public class DefaultMenuGenerator {
    public ArrayList<MenuModel> generateMenu() {
        ArrayList<MenuModel> answer = new ArrayList<>();

        // SESSION ROOT MENU

        MenuModel session = new MenuModel("Session", null, null, null, MenuModel.MenuType.Administrative);
        answer.add(session);

        MenuModel killSession = new MenuModel("Log out", session, "CLOSE", "LogoutMenuAction", MenuModel.MenuType.Administrative);
        answer.add(killSession);

        // SYSTEM ROOT MENU

        MenuModel system = new MenuModel("System", null, null, null, MenuModel.MenuType.Administrative);
        answer.add(system);

        MenuModel cashierMachine = new MenuModel("Cash Registers", system, "CALCULATOR", "ManageCashierMenuAction", MenuModel.MenuType.Administrative);
        answer.add(cashierMachine);

        MenuModel configuration = new MenuModel("Configuration", system, "COG", "ManageConfigMenuAction", MenuModel.MenuType.Administrative);
        answer.add(configuration);

        MenuModel configureStore = new MenuModel("Configure Store", system, "WRENCH", "LogoutMenuAction", MenuModel.MenuType.Administrative);
        answer.add(configureStore);

        MenuModel profiles = new MenuModel("Profiles", system, "USERS", "ManageProfileMenuAction", MenuModel.MenuType.Administrative);
        answer.add(profiles);

        MenuModel bankPOS = new MenuModel("Bank Point of Sale", system, "MONEY", "ManageBankPOSMenuAction", MenuModel.MenuType.Administrative);
        answer.add(bankPOS);

        MenuModel users = new MenuModel("Users", system, "USER", "ManageUserMenuAction", MenuModel.MenuType.Administrative);
        answer.add(users);

        // SHOP ROOT MENU

        MenuModel shop = new MenuModel("Shop", null, null, null, MenuModel.MenuType.Administrative);
        answer.add(shop);

        MenuModel product = new MenuModel("Products", shop, "PRODUCT_HUNT", "ManageProductsMenuAction", MenuModel.MenuType.Administrative);
        answer.add(product);

        MenuModel assignment = new MenuModel("Assignments", shop, "TASKS", "ManageAssignmentsMenuAction", MenuModel.MenuType.Administrative);
        answer.add(assignment);

        MenuModel closeDay = new MenuModel("Close Day", shop, "CALENDAR_CHECK_ALT", "LogoutMenuAction", MenuModel.MenuType.Administrative);
        answer.add(closeDay);

        MenuModel messages = new MenuModel("Messages", shop, "WECHAT", "ManageMessagesMenuAction", MenuModel.MenuType.Administrative);
        answer.add(messages);

        MenuModel configurePrinter = new MenuModel("Configure Printer", shop, "PRINT", "LogoutMenuAction", MenuModel.MenuType.Administrative);
        answer.add(configurePrinter);

        MenuModel reports = new MenuModel("Reports", shop, "TABLE", "LogoutMenuAction", MenuModel.MenuType.Administrative);
        answer.add(reports);

        MenuModel shifts = new MenuModel("Shifts", shop, "CLOCK_ALT", "ManageShiftsMenuAction", MenuModel.MenuType.Administrative);
        answer.add(shifts);


        // We need to order them before adding them into the menu;
        HashSet<MenuModel> visited = new HashSet<>();
        // Let's order them using dfs

        ArrayList<MenuModel> orderedMenu = new ArrayList<>();

        for (MenuModel menu : answer) {
            topologicalOrder(orderedMenu, visited, menu);
        }

        return orderedMenu;
    }

    public static void topologicalOrder(ArrayList<MenuModel> order, HashSet<MenuModel> visited, MenuModel menu) {
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