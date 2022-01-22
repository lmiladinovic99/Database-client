package view;

import resource.implementation.Entity;
import resource.tree.DBNode;

import java.awt.BorderLayout;

import javax.swing.*;

public class RightBottomPanel extends JPanel{

	private DBNode entity;
	private JTable jTable;
	private JScrollPane scrollPane;
	
	public RightBottomPanel(Entity entity) {
		super(new BorderLayout());
		this.entity = entity;

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

}
