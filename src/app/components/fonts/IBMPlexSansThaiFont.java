/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.components.fonts;

/**
 *
 * @author poke
 */
import java.awt.Font;

public class IBMPlexSansThaiFont {

    private static Font load(String path, float size) {
        try {
            return Font.createFont(
                    Font.TRUETYPE_FONT,
                    IBMPlexSansThaiFont.class.getResourceAsStream(path)
            ).deriveFont(size);
        } catch (Exception e) {
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    public static Font thin(float size) {
        return load("/resources/fonts/IBMPlexSansThai-Thin.ttf", size);
    }

    public static Font extraLight(float size) {
        return load("/resources/fonts/IBMPlexSansThai-ExtraLight.ttf", size);
    }

    public static Font light(float size) {
        return load("/resources/fonts/IBMPlexSansThai-Light.ttf", size);
    }

    public static Font regular(float size) {
        return load("/resources/fonts/IBMPlexSansThai-Regular.ttf", size);
    }

    public static Font medium(float size) {
        return load("/resources/fonts/IBMPlexSansThai-Medium.ttf", size);
    }

    public static Font semiBold(float size) {
        return load("/resources/fonts/IBMPlexSansThai-SemiBold.ttf", size);
    }

    public static Font bold(float size) {
        return load("/resources/fonts/IBMPlexSansThai-Bold.ttf", size);
    }
}
