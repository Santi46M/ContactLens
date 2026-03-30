package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class LensDiagramPanel extends JPanel {

    private double curvaEsferica = 0.0;
    private double curvaCilindrica = 0.0;
    private double eje = 0.0;
    private boolean hasData = false;

    private final Color verdePrincipal = new Color(0, 140, 0);
    private final Color verdeSecundario = new Color(0, 180, 90);

    public LensDiagramPanel() {
        setPreferredSize(new Dimension(400, 320));
        setBackground(new Color(255, 252, 235));
        setBorder(BorderFactory.createTitledBorder("Diagrama de curvas"));
    }

    public void updateData(double curvaEsferica, double curvaCilindrica, double eje) {
        this.curvaEsferica = curvaEsferica;
        this.curvaCilindrica = curvaCilindrica;
        this.eje = eje;
        this.hasData = true;
        repaint();
    }

    public void clearData() {
        this.curvaEsferica = 0.0;
        this.curvaCilindrica = 0.0;
        this.eje = 0.0;
        this.hasData = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        Insets insets = getInsets();

        int availableWidth = w - insets.left - insets.right;
        int availableHeight = h - insets.top - insets.bottom;

        int cx = insets.left + availableWidth / 2;
        int cy = insets.top + availableHeight / 2 + 10;
        int radius = Math.min(availableWidth, availableHeight) / 2 - 20;

        g2.setColor(verdeSecundario);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);

        if (!hasData) {
            g2.setColor(Color.GRAY);
            g2.drawString("Sin datos", cx - 25, cy);
            g2.dispose();
            return;
        }

        drawAxisLine(g2, cx, cy, radius, eje, curvaEsferica, verdePrincipal, 1.0);

        double ejeTranspuesto = (eje + 90.0) % 180.0;
        drawAxisLine(g2, cx, cy, radius, ejeTranspuesto, curvaCilindrica, verdePrincipal, 0.82);

        g2.setColor(verdePrincipal);
        g2.fillOval(cx - 4, cy - 4, 8, 8);

        g2.dispose();
    }

    private void drawAxisLine(Graphics2D g2, int cx, int cy, int radius,
                              double axisDeg, double curva, Color lineColor, double lineFactor) {
        double angleRad = Math.toRadians(-axisDeg);
        int effectiveRadius = (int) (radius * lineFactor);

        int x1 = (int) (cx - effectiveRadius * Math.cos(angleRad));
        int y1 = (int) (cy - effectiveRadius * Math.sin(angleRad));
        int x2 = (int) (cx + effectiveRadius * Math.cos(angleRad));
        int y2 = (int) (cy + effectiveRadius * Math.sin(angleRad));

        g2.setColor(lineColor);
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.5f));

        double perpX = -Math.sin(angleRad);
        double perpY = Math.cos(angleRad);
        int offset = 4;

        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        g2.draw(new Line2D.Double(x1 + perpX * offset, y1 + perpY * offset,
                x2 + perpX * offset, y2 + perpY * offset));
        g2.draw(new Line2D.Double(x1 - perpX * offset, y1 - perpY * offset,
                x2 - perpX * offset, y2 - perpY * offset));

        g2.setStroke(oldStroke);

        String text = String.format("%.2f mm %.0f°", curva, axisDeg);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));

        drawTextOnBorder(g2, text, cx, cy, radius, angleRad);
    }

    private void drawTextOnBorder(Graphics2D g2, String text, int cx, int cy, int radius, double angleRad) {
        FontMetrics fm = g2.getFontMetrics();

        int tx = (int) (cx + (radius + 2) * Math.cos(angleRad));
        int ty = (int) (cy + (radius + 2) * Math.sin(angleRad));

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        if (Math.cos(angleRad) < 0) {
            tx -= textWidth;
        }

        if (Math.sin(angleRad) >= 0) {
            ty += textHeight;
        }

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(tx - 2, ty - textHeight, textWidth + 4, textHeight + 4, 6, 6);

        g2.setColor(Color.BLACK);
        g2.drawString(text, tx, ty);
    }
}