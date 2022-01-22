package view;

import app.AppCore;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import resource.tree.DBtree;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;

public class MainFrame extends JFrame implements Subscriber {

	private static MainFrame instance = null;
	private DBtree dbTree;
	private JTabbedPane topTab;
	private JTabbedPane bottomTab;
	private AppCore appCore;
	private InformationResource ir;

	public static MainFrame getInstance() {
		if (instance == null)
			instance = new MainFrame();
		return instance;
	}

	public MainFrame() {
		setTitle("Database Project");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		setSize(screenWidth/2, screenHeight/2);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		appCore = new AppCore();
		appCore.addSubscriber(this);
		appCore.loadResource();
		JScrollPane scroll1=new JScrollPane(dbTree);
		topTab = new JTabbedPane();
		bottomTab = new JTabbedPane();
		topTab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (topTab.getTabCount() != 0) {
					RightTopPanel selectedTab = (RightTopPanel) topTab.getSelectedComponent();
					appCore.readDataFromTable(selectedTab.getEntity().getName());
					while (bottomTab.getTabCount() > 0) {
						bottomTab.removeTabAt(0);
					}
					initBottomPanel();
				}
			}
		});
		bottomTab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (bottomTab.getTabCount() != 0) {
					RightBottomPanel selectedTab = (RightBottomPanel) bottomTab.getSelectedComponent();
					appCore.readDataFromRelationTable(selectedTab.getEntity().getName());
				}
			}
		});
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topTab, bottomTab);
		splitPane1.setDividerLocation(screenHeight/2);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll1, splitPane1);
		splitPane.setDividerLocation(165);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane);
		setVisible(true);
	}

	public void initBottomPanel() {
		if (topTab.getTabCount() != 0) {
			RightTopPanel selectedTab = (RightTopPanel) topTab.getSelectedComponent();
			Entity entity = (Entity) selectedTab.getEntity();
			for (int i = 0; i < entity.getInRelationWith().size(); i++) {
				Entity relationEntity = (Entity) ir.getChildByName(entity.getInRelationWith().get(i));
				RightBottomPanel bottomPanel = new RightBottomPanel(relationEntity);
				bottomPanel.getjTable().setModel(appCore.getTableModel2());
				appCore.readDataFromRelationTable(relationEntity.getName());
				bottomTab.addTab(entity.getInRelationWith().get(i), bottomPanel);
				bottomTab.setSelectedComponent(bottomPanel);
			}
		}
	}

	public JTabbedPane getTopTab() {
		return topTab;
	}

	public AppCore getAppCore() {
		return appCore;
	}

	@Override
	public void update(Notification notification) {
		if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
			dbTree =new DBtree();
			InformationResource root = (InformationResource)notification.getData();
			ir = root;
			dbTree.setModel(new DefaultTreeModel(ir));
		} else if (notification.getCode() == NotificationCode.DATA_UPDATED){
			Entity entity = (Entity) notification.getData();
			appCore.readDataFromTable(entity.getName());
			RightBottomPanel rbp = (RightBottomPanel) bottomTab.getSelectedComponent();
			appCore.readDataFromRelationTable(rbp.getEntity().getName());
		} else if (notification.getCode() == NotificationCode.NEW_RIGHT_PANEL) {
			Entity entity = (Entity) notification.getData();
			int pozicijaIstog = -1;
			for (int j = 0; j < topTab.getTabCount(); j++) {
				RightTopPanel rightTopPanel = (RightTopPanel) topTab.getComponentAt(j);
				String tabTitle = rightTopPanel.getEntity().getName();
				if (tabTitle.equals(entity.getName())){
					pozicijaIstog = j;
					break;
				}
			}
			if (pozicijaIstog == -1) {
				RightTopPanel topTableView = new RightTopPanel(entity);
				topTableView.getjTable().setModel(appCore.getTableModel1());
				appCore.readDataFromTable(entity.getName());
				topTab.addTab(entity.getName(), topTableView);
				topTab.setSelectedComponent(topTableView);
			}
			else {
				RightTopPanel topTableView = (RightTopPanel) topTab.getComponentAt(pozicijaIstog);
				topTab.setSelectedComponent(topTableView);
			}
		}
	}

	public InformationResource getIr() {
		return ir;
	}

	public JTabbedPane getBottomTab() {
		return bottomTab;
	}
}
