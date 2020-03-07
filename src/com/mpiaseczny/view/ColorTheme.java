package com.mpiaseczny.view;

public enum ColorTheme {
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme) {
        switch(colorTheme) {
            case DEFAULT:
                return "css/themeDefault.css";
            case DARK:
                return "css/themeDark.css";
            default:
                return null;
        }
    }
}
