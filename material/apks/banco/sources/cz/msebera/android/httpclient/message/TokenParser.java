package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.BitSet;

@Immutable
public class TokenParser {
    public static final char CR = '\r';
    public static final char DQUOTE = '\"';
    public static final char ESCAPE = '\\';
    public static final char HT = '\t';
    public static final TokenParser INSTANCE = new TokenParser();
    public static final char LF = '\n';
    public static final char SP = ' ';

    public static boolean isWhitespace(char c) {
        return c == ' ' || c == 9 || c == 13 || c == 10;
    }

    public static BitSet INIT_BITSET(int... iArr) {
        BitSet bitSet = new BitSet();
        for (int i : iArr) {
            bitSet.set(i);
        }
        return bitSet;
    }

    public String parseToken(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet) {
        StringBuilder sb = new StringBuilder();
        loop0:
        while (true) {
            boolean z = false;
            while (!parserCursor.atEnd()) {
                char charAt = charArrayBuffer.charAt(parserCursor.getPos());
                if (bitSet != null && bitSet.get(charAt)) {
                    break loop0;
                } else if (isWhitespace(charAt)) {
                    skipWhiteSpace(charArrayBuffer, parserCursor);
                    z = true;
                } else {
                    if (z && sb.length() > 0) {
                        sb.append(SP);
                    }
                    copyContent(charArrayBuffer, parserCursor, bitSet, sb);
                }
            }
            break loop0;
        }
        return sb.toString();
    }

    public String parseValue(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet) {
        StringBuilder sb = new StringBuilder();
        loop0:
        while (true) {
            boolean z = false;
            while (!parserCursor.atEnd()) {
                char charAt = charArrayBuffer.charAt(parserCursor.getPos());
                if (bitSet != null && bitSet.get(charAt)) {
                    break loop0;
                } else if (isWhitespace(charAt)) {
                    skipWhiteSpace(charArrayBuffer, parserCursor);
                    z = true;
                } else if (charAt == '\"') {
                    if (z && sb.length() > 0) {
                        sb.append(SP);
                    }
                    copyQuotedContent(charArrayBuffer, parserCursor, sb);
                } else {
                    if (z && sb.length() > 0) {
                        sb.append(SP);
                    }
                    copyUnquotedContent(charArrayBuffer, parserCursor, bitSet, sb);
                }
            }
            break loop0;
        }
        return sb.toString();
    }

    public void skipWhiteSpace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int pos = parserCursor.getPos();
        int pos2 = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        while (pos2 < upperBound && isWhitespace(charArrayBuffer.charAt(pos2))) {
            pos++;
            pos2++;
        }
        parserCursor.updatePos(pos);
    }

    public void copyContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet, StringBuilder sb) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        for (int pos2 = parserCursor.getPos(); pos2 < upperBound; pos2++) {
            char charAt = charArrayBuffer.charAt(pos2);
            if ((bitSet != null && bitSet.get(charAt)) || isWhitespace(charAt)) {
                break;
            }
            pos++;
            sb.append(charAt);
        }
        parserCursor.updatePos(pos);
    }

    public void copyUnquotedContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet, StringBuilder sb) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        for (int pos2 = parserCursor.getPos(); pos2 < upperBound; pos2++) {
            char charAt = charArrayBuffer.charAt(pos2);
            if ((bitSet != null && bitSet.get(charAt)) || isWhitespace(charAt) || charAt == '\"') {
                break;
            }
            pos++;
            sb.append(charAt);
        }
        parserCursor.updatePos(pos);
    }

    public void copyQuotedContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, StringBuilder sb) {
        if (!parserCursor.atEnd()) {
            int pos = parserCursor.getPos();
            int pos2 = parserCursor.getPos();
            int upperBound = parserCursor.getUpperBound();
            if (charArrayBuffer.charAt(pos) == '\"') {
                int i = pos2 + 1;
                int i2 = pos + 1;
                boolean z = false;
                while (true) {
                    if (i >= upperBound) {
                        break;
                    }
                    char charAt = charArrayBuffer.charAt(i);
                    if (z) {
                        if (!(charAt == '\"' || charAt == '\\')) {
                            sb.append(ESCAPE);
                        }
                        sb.append(charAt);
                        z = false;
                    } else if (charAt == '\"') {
                        i2++;
                        break;
                    } else if (charAt == '\\') {
                        z = true;
                    } else if (!(charAt == 13 || charAt == 10)) {
                        sb.append(charAt);
                    }
                    i++;
                    i2++;
                }
                parserCursor.updatePos(i2);
            }
        }
    }
}
