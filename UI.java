import java.awt.Button;

import java.awt.Font;

import java.awt.Frame;

import java.awt.Label;

import java.awt.TextField;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class UI extends JFrame implements ActionListener {

	// Declaring Objects

	Frame f = new Frame("Hexadecimal Calculation");

	Label l1 = new Label("Formula");

	Label l2 = new Label("Result");

	TextField t1 = new TextField();

	TextField t2 = new TextField();

	Button b1 = new Button("Do");

	UI() {

		// Giving Coordinates

		l1.setBounds(100, 100, 100, 20);

		l2.setBounds(100, 140, 100, 20);

		t1.setBounds(200, 100, 100, 20);

		t2.setBounds(200, 140, 100, 20);

		b1.setBounds(200, 200, 50, 20);

		// Adding components to the frame

		f.add(l1);

		f.add(l2);

		f.add(t1);

		f.add(t2);

		f.add(b1);

		b1.addActionListener(this);

		Font font1 = new Font("SansSerif", Font.ITALIC, 15);

		t1.setFont(font1);

		t2.setFont(font1);

		l1.setFont(font1);

		l2.setFont(font1);

		Font font2 = new Font("SansSerif", Font.BOLD, 15);

		b1.setFont(font2);

		f.setLayout(null);

		f.setVisible(true);

		f.setSize(400, 350);

		this.setResizable(false);

		f.addWindowListener(new WindowHandler());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class WindowHandler extends WindowAdapter {

		public void windowClosing(WindowEvent e) {

			System.exit(0);

		}

	}

	public void actionPerformed(ActionEvent e) {

		String input = t1.getText();

		if (e.getSource() == b1) {

			Calculator main = new Calculator(input); // use formula as a

			// parameter

			try {

				long wow = main.computation();

				// print only result is positive because it is printed the value

				// if it is positive

				if (wow > 0) {

					String output = Long.toHexString(wow).toUpperCase();

					t2.setText(output);

					// if it is negative

				} else if (wow < 0) {

					System.out.println("\nResult: " + "-" + Long.toHexString(Math.abs(wow)).toUpperCase());

					t2.setText("-" + Long.toHexString(Math.abs(wow)));

				}

			} catch (Exception e1) {

				String output = "Error";

				t2.setText(output);

				System.out.println("\nIt is not appropriate formula!!");

			}

		}

	}

	void close() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {

		new UI();

	}

}