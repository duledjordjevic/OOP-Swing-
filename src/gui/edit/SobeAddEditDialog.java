package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import entity.Soba;
import entity.TipSobe;
import gui.SobeTableFrame;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class SobeAddEditDialog extends JDialog{
	private SobeManager sobeManager;
	private Soba editSoba;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public SobeAddEditDialog(JFrame parent, SobeManager sobeManager, Soba editSoba) {
		super(parent, true);
		this.parent = parent;
		if (editSoba != null) {
			setTitle("Izmena sobe");
		} else {
			setTitle("Dodavanje sobe");
		}
		this.sobeManager = sobeManager;
		this.editSoba = editSoba;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		// 1. nacin sa MigLayout - dosta laksi
		MigLayout ml = new MigLayout("wrap 3", "[][]", "[]10[]10[]10[]10[]10[]10[]20[]");
		setLayout(ml);

		JLabel lblTipSobe = new JLabel("Tip Sobe");
		add(lblTipSobe);
		
		JComboBox combobox = new JComboBox(TipSobe.values());
		add(combobox, "span 2");
		
		
		JLabel lblDodatneUsluge = new JLabel("Dodatne usluge");
		add(lblDodatneUsluge);
		
		String[] dodatneUsluge = {"Klima", "Balkon", "Tv"};
		JList lista = new JList(dodatneUsluge);
		add(lista, "span 2");
		
		add(new JLabel());

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		if(editSoba != null) {
			
			combobox.setSelectedItem(editSoba.getTipSobe());
			if(editSoba.isKlima() & editSoba.isBalkon() & editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 0, 1, 2 });
			}else if(editSoba.isKlima() & editSoba.isBalkon() & !editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 0, 1 });
			}else if(editSoba.isKlima() & !editSoba.isBalkon() & editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 0, 2 });
			}else if(editSoba.isKlima() & !editSoba.isBalkon() & !editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 0 });
			}else if(!editSoba.isKlima() & editSoba.isBalkon() & editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 1, 2 });
			}else if(!editSoba.isKlima() & editSoba.isBalkon() & !editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 1 });
			}else if(!editSoba.isKlima() & !editSoba.isBalkon() & editSoba.isTv()) {
				lista.setSelectedIndices(new int[] { 2 });
			}
			
		}

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TipSobe tipSobe = TipSobe.valueOf(combobox.getSelectedItem().toString());
				if(editSoba != null) {
					boolean klima = false, balkon = false, tv = false ;
					if(!(lista.getSelectedValuesList().size() == 0)) {
						ArrayList<String> lista1 = (ArrayList<String>) lista.getSelectedValuesList();
						if(lista1.contains("Klima")) {
							klima = true;
						}
						if(lista1.contains("Balkon")) {
							balkon = true;
						}
						if(lista1.contains("Tv")) {
							tv = true;
						}
					}
					sobeManager.editSoba(editSoba.getId(), tipSobe, klima, balkon, tv);
					((SobeTableFrame) parent).refreshData();
					SobeAddEditDialog.this.dispose();
				}else {
					boolean klima = false, balkon = false, tv = false ;
					if(!(lista.getSelectedValuesList().size() == 0)) {
						ArrayList<String> lista1 = (ArrayList<String>) lista.getSelectedValuesList();
						if(lista1.contains("Klima")) {
							klima = true;
						}
						if(lista1.contains("Balkon")) {
							balkon = true;
						}
						if(lista1.contains("Tv")) {
							tv = true;
						}
					}
					
					sobeManager.addSoba(tipSobe, klima, balkon, tv);
					((SobeTableFrame) parent).refreshData();
					SobeAddEditDialog.this.dispose();
				}
			}
				
		});		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SobeAddEditDialog.this.dispose();
			}
		});
	}
}
