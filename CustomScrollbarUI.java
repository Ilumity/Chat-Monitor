package com.lumity.chatmonitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollbarUI extends BasicScrollBarUI {

	private final Dimension d = new Dimension();

	@Override
	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(new Color(214, 217, 223));
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
		g.setColor(new Color(197, 199, 206));
		g.drawLine(trackBounds.x, trackBounds.y, trackBounds.x, trackBounds.y + trackBounds.height);
		g.drawLine(trackBounds.x + trackBounds.width - 1, trackBounds.y, trackBounds.x + trackBounds.width - 1,
				trackBounds.y + trackBounds.height);
	}

	@Override
	public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
			return;
		}
		int w = thumbBounds.width;
		int h = thumbBounds.height;
		g.translate(thumbBounds.x, thumbBounds.y);
		g.setColor(new Color(160, 160, 160));
		g.fillRect(0, 0, w - 1, h - 1);
		g.setColor(new Color(197, 199, 206));
		g.drawRect(0, 0, w - 1, h - 1);
		g.translate(-thumbBounds.x, -thumbBounds.y);
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		return d;
	}
}