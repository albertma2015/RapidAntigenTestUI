package ntou.cs.rat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import ntou.cs.rat.model.Pharmacy;

public class RatInfoViewer extends JPanel {
	private static final int fontSize = 28;
	private static final String space = "    ";

	private final JPanel fieldPanel;
	private final JPanel valuePanel;
	private final JLabel nameLabel;
	private final JLabel addressLabel;
	private final JLabel ratBrandLabel;
	private final JLabel ratNumberLabel;
	private final JLabel noteLabel;
	private final JTextField nameValue;
	private final JTextField addressValue;
	private final JTextField ratBrandValue;
	private final JTextField ratNumberValue;
	private final JTextField noteValue;

	public RatInfoViewer() {

		fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(5, 1, 15, 0));

		valuePanel = new JPanel();
		valuePanel.setLayout(new GridLayout(5, 1, 15, 0));

		nameLabel = buildTitleLabel("藥局名稱");
		fieldPanel.add(nameLabel);

		addressLabel = buildTitleLabel("地址");
		fieldPanel.add(addressLabel);

		ratBrandLabel = buildTitleLabel("快篩品牌");
		fieldPanel.add(ratBrandLabel);

		ratNumberLabel = buildTitleLabel("剩餘快篩數量");
		fieldPanel.add(ratNumberLabel);

		noteLabel = buildTitleLabel("備註");
		fieldPanel.add(noteLabel);

		nameValue = buildValueField();
		valuePanel.add(nameValue);

		addressValue = buildValueField();
		valuePanel.add(addressValue);

		ratBrandValue = buildValueField();
		valuePanel.add(ratBrandValue);

		ratNumberValue = buildValueField();
		ratNumberValue.setFont(new Font("Calibri", Font.PLAIN, fontSize));
		ratNumberValue.setForeground(Color.BLUE);
		valuePanel.add(ratNumberValue);

		noteValue = buildValueField();
		valuePanel.add(noteValue);

		setLayout(new BorderLayout());
		add(fieldPanel, BorderLayout.WEST);
		add(valuePanel, BorderLayout.CENTER);

		setBorder(BorderFactory.createLineBorder(Color.gray));
	}

	private JLabel buildTitleLabel(String title) {
		JLabel label = new JLabel(space + title + space);
		label.setHorizontalAlignment(JTextField.RIGHT);
		return label;
	}

	private JTextField buildValueField() {
		JTextField field = new JTextField("");
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setEditable(false);
		return field;
	}

	public void setPharmacyInfo(Pharmacy pharmacy) {
		nameValue.setText(pharmacy.getName());
		addressValue.setText(pharmacy.getAddress());
		ratBrandValue.setText("" + pharmacy.getBrandOfRapidAntigenTests());
		ratNumberValue.setText("" + pharmacy.getNumberOfRapidAntigenTests());
		noteValue.setText("" + pharmacy.getNote());
	}

}
