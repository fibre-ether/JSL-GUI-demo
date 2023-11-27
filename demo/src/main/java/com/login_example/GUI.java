package com.login_example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener {

	private static JLabel password1, label;
	private static JTextField username;
	private static JButton loginButton;
	private static JButton signupButton;
	private static JButton fetchAllButton;
	private static JPasswordField Password;

	public static void main(String[] args) {

		DAO.createNewDatabase();
		DAO.createNewTable();

		// Dimensions
		int outerWidth = 400;
		int outerHeight = 230;
		int innerWidth = 250;
		int innerHeight = 20;
		int spaceBetweenX = 5;
		int spaceBetweenY = 5;

		int rowY = innerHeight + spaceBetweenY;
		int buttonWidth = (innerWidth - spaceBetweenX) / 2;
		int buttonHeight = 30;
		// Panel class
		JPanel panel = new JPanel();
		panel.setLayout(null);

		// JFrame
		JFrame frame = new JFrame();
		frame.setTitle("Manage Users");
		frame.setLocation(new Point(500, 300));
		frame.add(panel);
		frame.setSize(new Dimension(outerWidth, outerHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Username label
		label = new JLabel("Username");
		label.setBounds((outerWidth - innerWidth) / 2, rowY * 0, innerWidth, innerHeight);
		panel.add(label);

		// Username TextField
		username = new JTextField();
		username.setBounds((outerWidth - innerWidth) / 2, rowY * 1, innerWidth, innerHeight);
		panel.add(username);

		// Password Label
		password1 = new JLabel("Password");
		password1.setBounds((outerWidth - innerWidth) / 2, rowY * 2, innerWidth, innerHeight);
		panel.add(password1);

		// Password TextField
		Password = new JPasswordField();
		Password.setBounds((outerWidth - innerWidth) / 2, rowY * 3, innerWidth, innerHeight);
		panel.add(Password);

		// Login Button component
		loginButton = new JButton("Login");
		loginButton.setBounds((outerWidth - innerWidth) / 2, rowY * 4, buttonWidth, buttonHeight);
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLACK);
		loginButton.addActionListener(
				e -> LoginButtonPressed());
		panel.add(loginButton);

		// Signup Button component
		signupButton = new JButton("Create User");
		signupButton.setBounds((outerWidth - innerWidth) / 2 + spaceBetweenX + buttonWidth, rowY * 4, buttonWidth,
				buttonHeight);
		signupButton.setForeground(Color.WHITE);
		signupButton.setBackground(Color.BLACK);
		signupButton.addActionListener(
				e -> SignupButtonPressed());
		panel.add(signupButton);

		// fetch all Button component
		fetchAllButton = new JButton("Fetch all Users");
		fetchAllButton.setBounds((outerWidth - innerWidth) / 2, rowY * 4 + buttonHeight + spaceBetweenY, buttonWidth,
				buttonHeight);
		fetchAllButton.setForeground(Color.WHITE);
		fetchAllButton.setBackground(Color.BLACK);
		fetchAllButton.addActionListener(
				e -> fetchAllButtonPressed());
		panel.add(fetchAllButton);

		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static void fetchAllButtonPressed() {
		String allUsers = DAO.selectAll();
		System.out.println("Found users: " + allUsers);
		JOptionPane.showMessageDialog(null, "Users in database:\n" + allUsers);
	}

	public static void LoginButtonPressed() {
		System.out.println("login button pressed");
		String inputUsername = username.getText();
		String inputPassword = String.valueOf(Password.getPassword());

		String[] result = DAO.select(inputUsername, inputPassword);
		if (result != null && result[0] != null) {
			System.out.println("Found user: " + result[0] + " " + result[1]);
			JOptionPane.showMessageDialog(null, "Successfully logged into " + inputUsername);
		} else {
			System.err.println("Username or Password does not match!");
			JOptionPane.showMessageDialog(null, "Username or Password does not match");
		}
	}

	public static void SignupButtonPressed() {
		System.out.println("signup button pressed");
		String inputUsername = username.getText();
		String inputPassword = String.valueOf(Password.getPassword());
		int resultCode = DAO.insert(inputUsername, inputPassword);
		if (resultCode == 0) {
			JOptionPane.showMessageDialog(null, "There was an issue creating the user");
		} else {
			JOptionPane.showMessageDialog(null, "Successfully created user: " + inputUsername);
		}
	}

}
