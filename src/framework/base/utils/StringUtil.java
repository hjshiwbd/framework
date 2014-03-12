package framework.base.utils;

import java.io.UnsupportedEncodingException;

/**
 * StringUtil
 * User: Administrator
 * Date: 14-3-12
 * Time: 下午9:58
 */
public class StringUtil
{
    /**
     * string encoding by: new String(s.getBytes("iso-8859-1"), "utf-8");
     *
     * @param str str to be encoding
     * @return result
     */
    public static String getUTF8String(String str)
    {
        if (str == null)
        {
            return null;
        }
        if (str.equals(""))
        {
            return "";
        }

        try
        {
            return new String(str.getBytes("iso-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException("unknown encoding");
        }
    }
}
