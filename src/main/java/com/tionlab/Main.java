package com.tionlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.InputStream;
import java.security.Security;
import com.formdev.flatlaf.FlatLightLaf;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Main extends JFrame {
    public File selectedFile;
    public JComboBox<String> algorithmComboBox;
    public JButton hashButton;
    public JTextArea resultArea;
    public JTextArea fileInfoLabel;
    public JTextArea compareArea;
    public JButton compareButton;
    public JLabel compareResultLabel;
    public JButton reselectButton;
    public JLabel iconLabel;
    public JProgressBar progressBar;

    public Main() {
        this(null);
    }

    public Main(String filePath) {
        Security.addProvider(new BouncyCastleProvider());
        FlatLightLaf.setup();
        setTitle(DialogTexts.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(600, 380));
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/imgs/Haxly_logo.png")).getImage());

        try (InputStream is = getClass().getResourceAsStream("/fonts/Pretendard.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            UIManager.put("Label.font", font);
            UIManager.put("Button.font", font);
            UIManager.put("TextArea.font", font);
            UIManager.put("ComboBox.font", font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeComponents();

        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                selectedFile = file;
                showMainUI();
                FileUtils.updateFileInfo(this, selectedFile);
                algorithmComboBox.setEnabled(true);
                hashButton.setEnabled(true);
                pack();
                setLocationRelativeTo(null);
            } else {
                showFileSelectionUI();
                pack();
            }
        } else {
            showFileSelectionUI();
            pack();
        }
    }

    private void initializeComponents() {
        fileInfoLabel = new JTextArea(DialogTexts.NO_FILE_SELECTED);
        fileInfoLabel.setEditable(false);
        fileInfoLabel.setBackground(null);
        fileInfoLabel.setBorder(null);
        fileInfoLabel.setFont(new JLabel().getFont());
        algorithmComboBox = new JComboBox<>(DialogTexts.ALGORITHMS);
        hashButton = new JButton(DialogTexts.HASH_BUTTON);
        hashButton.addActionListener(_ -> HashUtils.computeHash(this));
        resultArea = new JTextArea(1, 40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        compareArea = new JTextArea(1, 40);
        compareArea.setLineWrap(true);
        compareButton = new JButton(DialogTexts.COMPARE_BUTTON);
        compareButton.addActionListener(_ -> HashUtils.compareHash(this));
        compareResultLabel = new JLabel("");
        compareResultLabel.setFont(new Font(compareResultLabel.getFont().getName(), Font.BOLD, 16));
        compareResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reselectButton = new JButton(DialogTexts.RESELECT_BUTTON);
        reselectButton.addActionListener(_ -> resetToFileSelection());
        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
    }

    private void showFileSelectionUI() {
        JPanel fileSelectionPanel = new JPanel(new GridBagLayout());
        fileSelectionPanel.setBorder(BorderFactory.createTitledBorder(""));

        JLabel instructionLabel = new JLabel(DialogTexts.INSTRUCTION_LABEL);
        instructionLabel.setFont(instructionLabel.getFont().deriveFont(16f));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fileSelectionPanel.add(instructionLabel);

        fileSelectionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileUtils.openFileChooser(Main.this);
            }
        });

        new FileDropHandler(fileSelectionPanel, this);

        getContentPane().add(fileSelectionPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void showMainUI() {
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel fileInfoPanel = new JPanel(new BorderLayout());
        fileInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        fileInfoPanel.add(fileInfoLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(reselectButton);
        fileInfoPanel.add(buttonPanel, BorderLayout.EAST);

        JPanel fileDetailsPanel = new JPanel();
        fileDetailsPanel.setLayout(new BoxLayout(fileDetailsPanel, BoxLayout.Y_AXIS));
        fileDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileDetailsPanel.add(iconLabel);
        fileDetailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        fileDetailsPanel.add(fileInfoPanel);

        mainPanel.add(fileDetailsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel horizontalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        horizontalPanel.add(algorithmComboBox);
        horizontalPanel.add(hashButton);

        controlPanel.add(horizontalPanel);
        controlPanel.add(progressBar);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(500, 25));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel resultLabel = new JLabel(DialogTexts.RESULT_LABEL);
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel compareLabel = new JLabel(DialogTexts.COMPARE_LABEL);
        compareLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane compareScrollPane = new JScrollPane(compareArea);
        compareScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        compareScrollPane.setPreferredSize(new Dimension(500, 25));
        compareScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.add(compareResultLabel, BorderLayout.CENTER);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(controlPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultLabel);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(compareLabel);
        mainPanel.add(compareScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(compareButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultPanel);

        add(mainPanel);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    public void resetToFileSelection() {
        getContentPane().removeAll();
        selectedFile = null;
        resultArea.setText("");
        compareArea.setText("");
        compareResultLabel.setText("");
        algorithmComboBox.setEnabled(false);
        hashButton.setEnabled(false);
        iconLabel.setIcon(null);
        showFileSelectionUI();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (args.length > 0) {
                new Main(args[0]).setVisible(true);
            } else {
                new Main().setVisible(true);
            }
        });
    }
}