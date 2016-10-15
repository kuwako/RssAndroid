package jp.kuwako.masaki.rssandroid.util;

/**
 * Created by m_kuwako on 2016/10/11.
 */

public class StringUtil {
    /**
     * 指定された文字数 + ... を返す
     * @param text
     * @param maxLength
     * @return convertedText
     */
    public static String cutText(String text, int maxLength) {
        text = text.substring(0, maxLength);

        return text + "...";
    }

    /**
     * htmlタグの削除、改行文字の削除、空白文字の削除
     * @param str
     * @return
     */
    public static String htmlTagRemover(String str) {
        str = str.replaceAll("<.+?>", "");
        str = str.replaceAll("\n", "");
        str = str.trim();
        return str;
    }
}
