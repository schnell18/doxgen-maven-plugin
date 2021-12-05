package cf.tinkerit.rosetta.generator.impl.utils;

import org.junit.Test;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.wrapFootnote;
import static org.junit.Assert.assertEquals;


public class CommonUtilTest {

    @Test
    public void wrapFootnoteMatchedEnglishComma() {
        String str = "显示模板编码,  参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212";
        String exp = "显示模板编码\\footnote{参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212}";
        assertEquals(exp, wrapFootnote(str));
    }

    @Test
    public void wrapFootnoteMatchedChineseComma() {
        String str = "显示模板编码，    参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212";
        String exp = "显示模板编码\\footnote{参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212}";
        assertEquals(exp, wrapFootnote(str));
    }

    @Test
    public void wrapFootnoteMatchedEnglishColon() {
        String str = "显示模板编码:  参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212";
        String exp = "显示模板编码\\footnote{参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212}";
        assertEquals(exp, wrapFootnote(str));
    }

    @Test
    public void wrapFootnoteMatchedChineseColon() {
        String str = "显示模板编码：    参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212";
        String exp = "显示模板编码\\footnote{参考:http://doc.tinkerit.cf/pages/viewpage.action?pageId=40935212}";
        assertEquals(exp, wrapFootnote(str));
    }

    @Test
    public void wrapFootnoteNoneMatched() {
        String str = "该可选值所属的参数数据类型";
        assertEquals(str, wrapFootnote(str));
    }

}
