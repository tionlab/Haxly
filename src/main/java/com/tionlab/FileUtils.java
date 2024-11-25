package com.tionlab;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.filechooser.FileSystemView;

public class FileUtils {

    public static void openFileChooser(Main main) {
        FileDialog fileDialog = new FileDialog(main, DialogTexts.FILE_DIALOG_TITLE, FileDialog.LOAD);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (filename != null) {
            main.selectedFile = new File(directory, filename);
            displayFileInfo(main);
            main.algorithmComboBox.setEnabled(true);
            main.hashButton.setEnabled(true);
            main.showMainUI();
        }
    }

    public static void displayFileInfo(Main main) {
        String lastModified = new java.text.SimpleDateFormat(DialogTexts.DATE_FORMAT)
                .format(new java.util.Date(main.selectedFile.lastModified()));
        String info = String.format(DialogTexts.FILE_INFO_FORMAT,
                main.selectedFile.getName(),
                main.selectedFile.length(),
                formatFileSize(main.selectedFile.length()),
                lastModified);
        main.fileInfoLabel.setText(info);
        main.fileInfoLabel.setRows(3);
        updateFileIcon(main);
    }

    private static void updateFileIcon(Main main) {
        if (main.selectedFile != null) {
            if (isImageFile(main.selectedFile)) {
                try {
                    BufferedImage img = ImageIO.read(main.selectedFile);
                    if (img != null) {
                        Image scaledImg = getScaledImage(img, 50, 50);
                        main.iconLabel.setIcon(new ImageIcon(scaledImg));
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Icon originalIcon = FileSystemView.getFileSystemView().getSystemIcon(main.selectedFile);
            if (originalIcon instanceof ImageIcon) {
                Image img = ((ImageIcon) originalIcon).getImage();
                Image scaledImg = getScaledImage(img, 50, 50);
                main.iconLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                main.iconLabel.setIcon(originalIcon);
            }
        } else {
            main.iconLabel.setIcon(null);
        }
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".png") || name.endsWith(".gif") ||
                name.endsWith(".bmp");
    }

    private static Image getScaledImage(Image srcImg, int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }

    public static void updateFileInfo(Main main, File file) {
        if (file != null && file.exists()) {
            String lastModified = new java.text.SimpleDateFormat(DialogTexts.DATE_FORMAT)
                    .format(new java.util.Date(file.lastModified()));
            String info = String.format(DialogTexts.FILE_INFO_FORMAT,
                    file.getName(),
                    file.length(),
                    formatFileSize(file.length()),
                    lastModified);
            main.fileInfoLabel.setText(info);
            main.fileInfoLabel.setRows(3);

            try {
                if (isImageFile(file)) {
                    BufferedImage img = ImageIO.read(file);
                    if (img != null) {
                        Image scaledImg = getScaledImage(img, 50, 50);
                        main.iconLabel.setIcon(new ImageIcon(scaledImg));
                        return;
                    }
                }
                Icon originalIcon = FileSystemView.getFileSystemView().getSystemIcon(file);
                if (originalIcon instanceof ImageIcon) {
                    Image img = ((ImageIcon) originalIcon).getImage();
                    Image scaledImg = getScaledImage(img, 50, 50);
                    main.iconLabel.setIcon(new ImageIcon(scaledImg));
                } else {
                    main.iconLabel.setIcon(originalIcon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String formatFileSize(long size) {
        if (size < 1024)
            return size + " B";
        int z = (63 - Long.numberOfLeadingZeros(size)) / 10;
        return String.format("%.1f %sB", (double) size / (1L << (z * 10)), " KMGTPE".charAt(z));
    }
}