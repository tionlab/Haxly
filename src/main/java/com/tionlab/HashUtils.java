package com.tionlab;

import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.crypto.digests.Blake2sDigest;

import javax.swing.*;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public class HashUtils {

    public static void computeHash(Main main) {
        main.hashButton.setEnabled(false);
        main.algorithmComboBox.setEnabled(false);
        main.progressBar.setValue(0);
        main.progressBar.setVisible(true);

        SwingWorker<String, Integer> worker = new SwingWorker<String, Integer>() {
            @Override
            protected String doInBackground() throws Exception {
                String algorithm = (String) main.algorithmComboBox.getSelectedItem();

                if (algorithm.equals("CRC32")) {
                    CRC32 crc32 = new CRC32();
                    try (InputStream fis = new FileInputStream(main.selectedFile)) {
                        byte[] buffer = new byte[8192];
                        int read;
                        long fileSize = main.selectedFile.length();
                        long processedBytes = 0;

                        while ((read = fis.read(buffer)) != -1) {
                            crc32.update(buffer, 0, read);
                            processedBytes += read;
                            setProgress((int) ((processedBytes * 100) / fileSize));
                        }
                    }
                    return Long.toHexString(crc32.getValue());
                }

                if (algorithm.equals("BLAKE2B-512")) {
                    Blake2bWrapper wrapper = new Blake2bWrapper(new Blake2bDigest(512));
                    ProgressCallback callback = progress -> setProgress(progress);
                    return computeBlakeHash(wrapper, callback, main);
                }
                if (algorithm.equals("BLAKE2S-256")) {
                    Blake2sWrapper wrapper = new Blake2sWrapper(new Blake2sDigest(256));
                    ProgressCallback callback = progress -> setProgress(progress);
                    return computeBlakeHash(wrapper, callback, main);
                }

                MessageDigest digest = MessageDigest.getInstance(algorithm, "BC");
                long fileSize = main.selectedFile.length();
                long processedBytes = 0;

                try (InputStream fis = new FileInputStream(main.selectedFile)) {
                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = fis.read(buffer)) != -1) {
                        digest.update(buffer, 0, read);
                        processedBytes += read;
                        setProgress((int) ((processedBytes * 100) / fileSize));
                    }
                }

                byte[] hashBytes = digest.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                main.progressBar.setValue(getProgress());
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    main.resultArea.setText(result);
                } catch (Exception e) {
                    main.resultArea.setText(DialogTexts.HASH_ERROR);
                    main.resultArea.setForeground(new Color(40, 0, 0));
                    e.printStackTrace();
                }
                main.hashButton.setEnabled(true);
                main.algorithmComboBox.setEnabled(true);
                main.progressBar.setVisible(false);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                main.progressBar.setValue((Integer) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private static String computeBlakeHash(DigestWrapper digest, ProgressCallback progressCallback, Main main)
            throws IOException {
        long fileSize = main.selectedFile.length();
        long processedBytes = 0;

        try (InputStream fis = new FileInputStream(main.selectedFile)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
                processedBytes += read;
                progressCallback.setProgress((int) ((processedBytes * 100) / fileSize));
            }
        }

        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void compareHash(Main main) {
        String resultHash = main.resultArea.getText().trim();
        String compareHash = main.compareArea.getText().trim();

        if (resultHash.isEmpty() || compareHash.isEmpty()) {
            main.compareResultLabel.setText(DialogTexts.COMPARE_ERROR);
            main.compareResultLabel.setForeground(new Color(40, 0, 0));
            return;
        }

        if (resultHash.equalsIgnoreCase(compareHash)) {
            main.compareResultLabel.setText(DialogTexts.MATCH);
            main.compareResultLabel.setForeground(new Color(0, 100, 0));
        } else {
            main.compareResultLabel.setText(DialogTexts.NO_MATCH);
            main.compareResultLabel.setForeground(new Color(150, 0, 0));
        }
    }

    private interface ProgressCallback {
        void setProgress(int progress);
    }

    private interface DigestWrapper {
        void update(byte[] data, int offset, int length);

        byte[] digest();

        int getDigestSize();
    }

    private static class Blake2bWrapper implements DigestWrapper {
        private final Blake2bDigest digest;

        public Blake2bWrapper(Blake2bDigest digest) {
            this.digest = digest;
        }

        @Override
        public void update(byte[] data, int offset, int length) {
            digest.update(data, offset, length);
        }

        @Override
        public byte[] digest() {
            byte[] hash = new byte[digest.getDigestSize()];
            digest.doFinal(hash, 0);
            return hash;
        }

        @Override
        public int getDigestSize() {
            return digest.getDigestSize();
        }
    }

    private static class Blake2sWrapper implements DigestWrapper {
        private final Blake2sDigest digest;

        public Blake2sWrapper(Blake2sDigest digest) {
            this.digest = digest;
        }

        @Override
        public void update(byte[] data, int offset, int length) {
            digest.update(data, offset, length);
        }

        @Override
        public byte[] digest() {
            byte[] hash = new byte[digest.getDigestSize()];
            digest.doFinal(hash, 0);
            return hash;
        }

        @Override
        public int getDigestSize() {
            return digest.getDigestSize();
        }
    }
}