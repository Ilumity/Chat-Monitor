package com.lumity.chatmonitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class CustomButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Color backgroundColor = new Color(255, 255, 255);
	private Color foregroundColor = new Color(0, 0, 0);
	private Color hoverBackgroundColor = new Color(200, 200, 200);
	private int arc = 20;
	private int height = 30;
	private int width = 150;

	public CustomButton(String text) {
		super(text);
		setBorderPainted(false);
		setOpaque(false);
		setForeground(foregroundColor);
		setBackground(backgroundColor);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (getModel().isPressed()) {
			g2.setColor(foregroundColor);
		} else if (getModel().isRollover()) {
			g2.setColor(hoverBackgroundColor);
		} else {
			g2.setColor(backgroundColor);
		}

		Shape shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
		g2.fill(shape);
		g2.setColor(getForeground());
		g2.draw(shape);
		g2.dispose();

		super.paintComponent(g);
	}

	public void setArc(int arc) {
		this.arc = arc;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setHoverBackgroundColor(Color hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

	public void setHoverForegroundColor(Color hoverForegroundColor) {
	}

	public void setButtonSize(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
	}
}