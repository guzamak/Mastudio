package presentation.receipt.controller;

import app.core.components.Macolor;
import app.core.components.fonts.IBMPlexSansThaiFont;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Receipt {

    private String customerName;
    private String date;
    private final List<LineItem> items = new ArrayList<>();

    private static final int WIDTH = 420;
    private static final int PAD = 32;
    private static final int CONTENT_W = WIDTH - PAD * 2;
    private static final Color GREEN = Macolor.magreen;
    private static final Color BG = new Color(250, 252, 255);
    private static final Color TEXT_DARK = new Color(30, 30, 30);
    private static final Color TEXT_MID = new Color(100, 110, 120);
    private static final Color DIVIDER = new Color(210, 220, 230);

    public Receipt() {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addItem(String description, double amount) {
        items.add(new LineItem(description, amount));
    }

    public double getTotal() {
        return items.stream().mapToDouble(i -> i.amount).sum();
    }

    public BufferedImage toImage() {
        int estimatedHeight = 420 + items.size() * 28;
        BufferedImage measure = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D mg = measure.createGraphics();
        mg.setFont(IBMPlexSansThaiFont.regular(13f));
        mg.dispose();

        BufferedImage img = new BufferedImage(WIDTH, estimatedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int y = 0;

        // -- Background with rounded rect --
        g.setColor(new Color(235, 240, 245));
        g.fillRect(0, 0, WIDTH, estimatedHeight);

        RoundRectangle2D card = new RoundRectangle2D.Float(8, 8, WIDTH - 16, estimatedHeight - 16, 20, 20);
        g.setColor(BG);
        g.fill(card);
        g.setClip(card);

        // -- Green header bar --
        y = 8;
        g.setColor(GREEN);
        g.fillRoundRect(8, y, WIDTH - 16, 80, 20, 20);
        g.fillRect(8, y + 40, WIDTH - 16, 40);

        // Brand text
        g.setColor(Color.WHITE);
        g.setFont(IBMPlexSansThaiFont.bold(26f));
        drawCentered(g, "./MA Studio", y + 38);
        g.setFont(IBMPlexSansThaiFont.light(11f));
        drawCentered(g, "Official Receipt", y + 58);

        y = 108;

        // -- Date & Customer --
        g.setFont(IBMPlexSansThaiFont.regular(12f));
        g.setColor(TEXT_MID);
        g.drawString("วันที่ (Date)", PAD, y);
        g.setColor(TEXT_DARK);
        g.setFont(IBMPlexSansThaiFont.medium(13f));
        g.drawString(date, PAD, y + 18);

        y += 44;
        g.setFont(IBMPlexSansThaiFont.regular(12f));
        g.setColor(TEXT_MID);
        g.drawString("ลูกค้า (Customer)", PAD, y);
        g.setColor(TEXT_DARK);
        g.setFont(IBMPlexSansThaiFont.medium(13f));
        g.drawString(customerName != null ? customerName : "—", PAD, y + 18);

        y += 36;

        // -- Divider --
        y = drawDivider(g, y);

        // -- Column headers --
        y += 4;
        g.setFont(IBMPlexSansThaiFont.medium(11f));
        g.setColor(TEXT_MID);
        g.drawString("รายการ (Description)", PAD, y);
        drawRight(g, "จำนวนเงิน (THB)", PAD + CONTENT_W, y);
        y += 8;
        y = drawDivider(g, y);

        // -- Line items --
        g.setFont(IBMPlexSansThaiFont.regular(13f));
        for (LineItem item : items) {
            y += 4;
            g.setColor(TEXT_DARK);
            g.drawString(item.description, PAD, y);
            drawRight(g, String.format("%.2f", item.amount), PAD + CONTENT_W, y);
            y += 24;
        }

        if (items.isEmpty()) {
            y += 4;
            g.setColor(TEXT_MID);
            drawCentered(g, "— ไม่มีรายการ —", y);
            y += 24;
        }

        // -- Total divider (double line) --
        y += 4;
        g.setColor(GREEN);
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(PAD, y, PAD + CONTENT_W, y);
        g.drawLine(PAD, y + 4, PAD + CONTENT_W, y + 4);
        y += 20;

        // -- Total --
        g.setFont(IBMPlexSansThaiFont.bold(16f));
        g.setColor(TEXT_DARK);
        g.drawString("รวมทั้งหมด (TOTAL)", PAD, y);
        g.setColor(GREEN);
        drawRight(g, String.format("%.2f THB", getTotal()), PAD + CONTENT_W, y);

        y += 12;
        g.setColor(GREEN);
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(PAD, y, PAD + CONTENT_W, y);
        g.drawLine(PAD, y + 4, PAD + CONTENT_W, y + 4);

        y += 36;

        // -- Footer --
        g.setColor(TEXT_MID);
        g.setFont(IBMPlexSansThaiFont.regular(12f));
        drawCentered(g, "ขอบคุณที่ใช้บริการ!", y);
        y += 20;
        g.setColor(GREEN);
        g.setFont(IBMPlexSansThaiFont.medium(14f));
        drawCentered(g, "./MA Studio", y);

        y += 30;

        g.dispose();

        // Crop to actual content height
        return img.getSubimage(0, 0, WIDTH, Math.min(y, estimatedHeight));
    }

    public File saveAsImage(String directory) throws IOException {
        BufferedImage image = toImage();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeName = (customerName != null ? customerName.replaceAll("[^a-zA-Z0-9ก-๙]", "_") : "receipt");
        String filename = "receipt_" + safeName + "_" + timestamp + ".png";

        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        ImageIO.write(image, "png", file);
        return file;
    }

    // --- Drawing helpers ---

    private void drawCentered(Graphics2D g, String text, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    private void drawRight(Graphics2D g, String text, int rightX, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, rightX - fm.stringWidth(text), y);
    }

    private int drawDivider(Graphics2D g, int y) {
        y += 10;
        g.setColor(DIVIDER);
        g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{4f, 4f}, 0f));
        g.drawLine(PAD, y, PAD + CONTENT_W, y);
        g.setStroke(new BasicStroke(1f));
        return y + 10;
    }

    // --- Line item ---

    public static class LineItem {
        public final String description;
        public final double amount;

        public LineItem(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }
}
