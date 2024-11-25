package com.tionlab;

import java.util.Locale;
import java.util.ResourceBundle;

public class DialogTexts {
    private static final ResourceBundle bundle;

    static {
        Locale defaultLocale = Locale.getDefault();
        if (defaultLocale.getLanguage().equals(Locale.KOREAN.getLanguage())) {
            bundle = ResourceBundle.getBundle("DialogTexts", Locale.KOREAN);
        } else {
            bundle = ResourceBundle.getBundle("DialogTexts", Locale.ENGLISH);
        }
    }

    public static final String TITLE = bundle.getString("title");
    public static final String NO_FILE_SELECTED = bundle.getString("noFileSelected");
    public static final String HASH_BUTTON = bundle.getString("hashButton");
    public static final String COMPARE_BUTTON = bundle.getString("compareButton");
    public static final String RESELECT_BUTTON = bundle.getString("reselectButton");
    public static final String INSTRUCTION_LABEL = bundle.getString("instructionLabel");
    public static final String RESULT_LABEL = bundle.getString("resultLabel");
    public static final String COMPARE_LABEL = bundle.getString("compareLabel");
    public static final String FILE_DIALOG_TITLE = bundle.getString("fileDialogTitle");
    public static final String DATE_FORMAT = bundle.getString("dateFormat");
    public static final String FILE_INFO_FORMAT = bundle.getString("fileInfoFormat");
    public static final String HASH_ERROR = bundle.getString("hashError");
    public static final String COMPARE_ERROR = bundle.getString("compareError");
    public static final String MATCH = bundle.getString("match");
    public static final String NO_MATCH = bundle.getString("noMatch");
    public static final String[] ALGORITHMS = {
            "SHA-256", "SHA-1", "MD5", "CRC32", "BLAKE2B-512",
            "BLAKE2S-256", "WHIRLPOOL", "RIPEMD160", "TIGER", "SHA-512"
    };
}