package com.tionlab;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.security.*;
import java.util.zip.CRC32;
import javax.swing.*;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

class MainTest {
    private Main main;
    private File testFile;
    private static final String TEST_CONTENT = "Test Content";

    @BeforeAll
    static void setUpClass() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @BeforeEach
    void setUp() throws IOException {
        main = new Main();
        testFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(TEST_CONTENT);
        }
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void testGUIComponents() {
        assertNotNull(main);
        assertTrue(main instanceof JFrame);
        assertNotNull(main.getContentPane());
    }

    @Test
    void testSupportedHashAlgorithms() throws Exception {
        String[] algorithms = { "MD5", "SHA-1", "SHA-256", "SHA-512", "WHIRLPOOL", "RIPEMD160", "TIGER" };

        for (String algorithm : algorithms) {
            MessageDigest digest = MessageDigest.getInstance(algorithm, "BC");
            assertNotNull(digest, "알고리즘 " + algorithm + " 지원 확인");
        }
    }

    @Test
    void testBlake2bHash() throws IOException {
        Blake2bDigest digest = new Blake2bDigest(512);
        byte[] input = TEST_CONTENT.getBytes();
        byte[] hash = new byte[digest.getDigestSize()];

        digest.update(input, 0, input.length);
        digest.doFinal(hash, 0);

        assertNotNull(hash);
        assertEquals(64, hash.length);
    }

    @Test
    void testBlake2sHash() throws IOException {
        Blake2sDigest digest = new Blake2sDigest(256);
        byte[] input = TEST_CONTENT.getBytes();
        byte[] hash = new byte[digest.getDigestSize()];

        digest.update(input, 0, input.length);
        digest.doFinal(hash, 0);

        assertNotNull(hash);
        assertEquals(32, hash.length); // 256비트 = 32바이트
    }

    @Test
    void testCRC32Hash() throws IOException {
        CRC32 crc32 = new CRC32();
        byte[] input = TEST_CONTENT.getBytes();
        crc32.update(input);

        long hashValue = crc32.getValue();
        String hashHex = Long.toHexString(hashValue);

        assertNotNull(hashHex);
        assertTrue(hashHex.length() > 0);
    }

    @Test
    void testFileHandling() {
        assertTrue(testFile.exists(), "테스트 파일 존재 확인");
        assertTrue(testFile.length() > 0, "테스트 파일 크기 확인");
        assertTrue(testFile.canRead(), "테스트 파일 읽기 권한 확인");
    }

    @Test
    void testHashComparison() {
        String hash1 = "5e543256c480ac577d30f76f9120eb74"; // MD5
        String hash2 = "5e543256c480ac577d30f76f9120eb74";
        String hash3 = "different_hash";

        assertEquals(hash1, hash2, "동일 해시 비교");
        assertNotEquals(hash1, hash3, "다른 해시 비교");
    }

    @Test
    void testProgressCalculation() {
        long fileSize = 1000L;
        long processedBytes = 500L;
        int progress = (int) ((processedBytes * 100) / fileSize);

        assertEquals(50, progress, "진행률 계산 확인");
    }
}