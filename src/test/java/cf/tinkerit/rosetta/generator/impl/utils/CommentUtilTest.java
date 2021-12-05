package cf.tinkerit.rosetta.generator.impl.utils;

import org.junit.Test;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.stripCommentMarker;
import static org.junit.Assert.assertEquals;

public class CommentUtilTest {

    @Test
    public void stripDoubleSlash() {
        String str = "//参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripDoubleSlashWithSpace() {
        String str = "//      参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripTripleSlash() {
        String str = "///参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripTripleSlashWithSpace() {
        String str = "///  参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripStar() {
        String str = "/*  参考，api.model.enums.RelationType */";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripStarOnly() {
        String str = "*  参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripStarOnlyWithLeadingSpaces() {
        String str = "  *  参考，api.model.enums.RelationType";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripDoubleStar() {
        String str = "/**  参考，api.model.enums.RelationType */";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

    @Test
    public void stripMultipleStar() {
        String str = "/****  参考，api.model.enums.RelationType ****/";
        String exp = "参考，api.model.enums.RelationType";
        assertEquals(exp, stripCommentMarker(str));
    }

}
