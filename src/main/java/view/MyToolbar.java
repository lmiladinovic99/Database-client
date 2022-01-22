package view;

import actions.ActionManager;

import javax.swing.*;

public class MyToolbar extends JToolBar {

    public MyToolbar() {
        super(SwingConstants.HORIZONTAL);

        add(ActionManager.getInstance().getAddAction());

        addSeparator();

        add(ActionManager.getInstance().getDeleteAction());

        addSeparator();

        add(ActionManager.getInstance().getUpdateAction());

        addSeparator();

        add(ActionManager.getInstance().getFilterSortAction());

        addSeparator();

        add(ActionManager.getInstance().getRelationsAction());

        addSeparator();

        add(ActionManager.getInstance().getReportsAction());

        addSeparator();

        add(ActionManager.getInstance().getSearchAction());

        setFloatable(false);

    }

}
