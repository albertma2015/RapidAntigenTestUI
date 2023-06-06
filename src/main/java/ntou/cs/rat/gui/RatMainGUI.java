package ntou.cs.rat.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ntou.cs.rat.model.RatHandler;
import ntou.cs.rat.model.Pharmacy;

public class RatMainGUI extends JFrame implements ActionListener {

	private final JTextField zoneField;
	private final JTextField pharmacyNameField;
	private final JButton search;
	private final JButton refresh;
	private final JPanel searchPanel;
	private JPanel pharmacyPanel;

	private RatHandler ratHandler;
	private Pharmacy[] pharmacies;
	private RatInfoViewer[] pharmacyPanels;
	private int pharmacyCounter;

	public RatMainGUI() {
		super("快篩追蹤系統");
		zoneField = new JTextField("輸入地區(如基隆市中正區)");
		pharmacyNameField = new JTextField("輸入藥局名稱(如海大)");

		search = new JButton("搜尋藥局");
		search.addActionListener(this);

		refresh = new JButton("更新資料");
		refresh.addActionListener(this);

		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(zoneField);
		searchPanel.add(pharmacyNameField);
		searchPanel.add(search);
		searchPanel.add(refresh);

		add(searchPanel, BorderLayout.NORTH);

		pharmacyPanel = new JPanel();
		pharmacyPanel.setLayout(new GridLayout(2, 2, 10, 10));

		add(pharmacyPanel, BorderLayout.CENTER);

		// pharmacyPanel.add(new MaskInfoViewer());
		// pharmacyPanel.add(new MaskInfoViewer());
		// pharmacyPanel.add(new MaskInfoViewer());
		// pharmacyPanel.add(new MaskInfoViewer());

		initializeMaskHandler();
		pharmacies = new Pharmacy[4];
		pharmacyPanels = new RatInfoViewer[4];
		pharmacyCounter = 0;
	}

	private void initializeMaskHandler() {
		ratHandler = new RatHandler();
		try {
			ratHandler.initialize();
		} catch (IOException e) {
			System.err.println(e);
		} catch (URISyntaxException e) {
			System.err.println(e);
		}
	}

	private void addPharmacy() {
		String zone = zoneField.getText();
		System.out.println("Zone: " + zone);
		String pharmacyName = pharmacyNameField.getText();
		System.out.println("Pharmacy: " + pharmacyName);

		List<Pharmacy> searchResults = ratHandler.findPharmacies(pharmacyName, zone);
		if ((searchResults != null) && (searchResults.size() != 0) && (pharmacyCounter < 4)) {
			pharmacies[pharmacyCounter] = searchResults.get(0);
			RatInfoViewer maskInfoViewer = new RatInfoViewer();
			maskInfoViewer.setPharmacyInfo(pharmacies[pharmacyCounter]);
			pharmacyPanels[pharmacyCounter] = maskInfoViewer;
			pharmacyPanel.add(maskInfoViewer);
			zoneField.setText("輸入地區(如基隆市中正區)");
			pharmacyNameField.setText("輸入藥局名稱(如海大)");
			pharmacyCounter++;
			revalidate();
		} else {
			JOptionPane.showMessageDialog(RatMainGUI.this, "無搜尋結果或超過可顯示數量");
			zoneField.setText("輸入地區(如基隆市中正區)");
			pharmacyNameField.setText("輸入藥局名稱(如和平)");
		}

	}

	private void refreshPharmacy() {
		for (int i = 0; i < pharmacyCounter; i++) {
			Pharmacy p = pharmacies[i];
			List<Pharmacy> searchResults = ratHandler.findPharmacies(p.getName(), p.getAddress());
			RatInfoViewer viewer = pharmacyPanels[i];
			try {
				viewer.setPharmacyInfo(searchResults.get(0));
			} catch (NullPointerException e) {
				continue;
			}
		}
		JOptionPane.showMessageDialog(RatMainGUI.this, "更新為最新資料！");
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search) {
			addPharmacy();
		} else if (e.getSource() == refresh) {
			refreshPharmacy();
		}
	}

}
