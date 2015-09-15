/*
 * Copyright (C) 2015 MrMagaw <MrMagaw@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mrmagaw.games.reversi.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class Tile extends javax.swing.JPanel {

    public int colour = 0; //0 = empty, 1 = white, 2 = black
    private static final Color[] PALETTE = new Color[]{
	new Color(31, 108, 28),
	new Color(255, 255, 255),
	new Color(0, 0, 0)
    };
    /**
     * Creates new form Tile
     */
    public Tile() {
	initComponents();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	Graphics2D g2 = (Graphics2D)g;
	g2.setColor(PALETTE[colour]);
	int h = (int)(this.getHeight() * 0.75);
	int w = (int)(this.getWidth() * 0.75);
	g2.fillOval((this.getWidth() - w) >> 1, (this.getHeight() - h) >> 1, w, h);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(31, 108, 28));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}