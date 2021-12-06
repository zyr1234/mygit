package com.geovis.jg.common.util;

//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
   // private static HanyuPinyinOutputFormat format = null;

    static  {
     //   format = new HanyuPinyinOutputFormat();
     //   format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    //转换单个字符
    public static String getCharacterPinYin(char c) {
        String[] pinyin = null;
       // try {
         //   pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
     //   } catch (BadHanyuPinyinOutputFormatCombination e) {
        //    e.printStackTrace();
      //  }

        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null) return null;

        // 只取一个发音，如果是多音字，仅取第一个发音
        return pinyin[0];

    }

    //转换一个字符串
    public static String getStringPinYin(String str) {
        StringBuilder sb = new StringBuilder();
        String tempPinyin;
        String prePinyin = null;

        for (int i = 0; i < str.length(); ++i) {

            tempPinyin = getCharacterPinYin(str.charAt(i));

            if (tempPinyin == null) {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            } else {
                if(i != 0 && null == prePinyin && null != tempPinyin){
                    sb.append(" ");
                }
                sb.append(tempPinyin);
                if (i!=str.length()-1){
                    sb.append(" ");
                }
            }

            prePinyin = tempPinyin;
        }
        return sb.toString();
    }
}
