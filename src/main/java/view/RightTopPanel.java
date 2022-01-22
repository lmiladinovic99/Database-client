package view;

import model.TableModel;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResource;
import resource.tree.DBNode;

import java.awt.*;

import javax.swing.*;

public class RightTopPanel extends JPanel implements Subscriber {

	private DBNode entity;
	private MyToolbar toolbar;
	private JTable jTable;
	private JScrollPane scrollPane;
	
	public RightTopPanel(DBNode entity) {
		super(new BorderLayout());
		this.entity = entity;

		toolbar=new MyToolbar();
		this.add(toolbar, BorderLayout.NORTH);

		jTable = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(jTable);
		this.add(scrollPane, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public JTable getjTable() {
		return jTable;
	}

	public DBNode getEntity() {
		return entity;
	}

	@Override
	public void update(Notification notification) {
		if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
			System.out.println((InformationResource)notification.getData());
		}
		else{
			jTable.setModel((TableModel) notification.getData());
		}
	}
}
