package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@Immutable
public class BasicLineParser implements LineParser {
    @Deprecated
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    public static final BasicLineParser INSTANCE = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) {
            protocolVersion = HttpVersion.HTTP_1_1;
        }
        this.protocol = protocolVersion;
    }

    public BasicLineParser() {
        this(null);
    }

    public static ProtocolVersion parseProtocolVersion(String str, LineParser lineParser) {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseProtocolVersion(charArrayBuffer, parserCursor);
    }

    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        String protocol2 = this.protocol.getProtocol();
        int length = protocol2.length();
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        skipWhitespace(charArrayBuffer, parserCursor);
        int pos2 = parserCursor.getPos();
        int i = pos2 + length;
        if (i + 4 > upperBound) {
            StringBuilder sb = new StringBuilder();
            sb.append("Not a valid protocol version: ");
            sb.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb.toString());
        }
        boolean z = true;
        int i2 = 0;
        while (z && i2 < length) {
            z = charArrayBuffer.charAt(pos2 + i2) == protocol2.charAt(i2);
            i2++;
        }
        if (z) {
            z = charArrayBuffer.charAt(i) == '/';
        }
        if (!z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Not a valid protocol version: ");
            sb2.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb2.toString());
        }
        int i3 = pos2 + length + 1;
        int indexOf = charArrayBuffer.indexOf(46, i3, upperBound);
        if (indexOf == -1) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Invalid protocol version number: ");
            sb3.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb3.toString());
        }
        try {
            int parseInt = Integer.parseInt(charArrayBuffer.substringTrimmed(i3, indexOf));
            int i4 = indexOf + 1;
            int indexOf2 = charArrayBuffer.indexOf(32, i4, upperBound);
            if (indexOf2 == -1) {
                indexOf2 = upperBound;
            }
            try {
                int parseInt2 = Integer.parseInt(charArrayBuffer.substringTrimmed(i4, indexOf2));
                parserCursor.updatePos(indexOf2);
                return createProtocolVersion(parseInt, parseInt2);
            } catch (NumberFormatException unused) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Invalid protocol minor version number: ");
                sb4.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(sb4.toString());
            }
        } catch (NumberFormatException unused2) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Invalid protocol major version number: ");
            sb5.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb5.toString());
        }
    }

    /* access modifiers changed from: protected */
    public ProtocolVersion createProtocolVersion(int i, int i2) {
        return this.protocol.forVersion(i, i2);
    }

    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        String protocol2 = this.protocol.getProtocol();
        int length = protocol2.length();
        if (charArrayBuffer.length() < length + 4) {
            return false;
        }
        if (pos < 0) {
            pos = (charArrayBuffer.length() - 4) - length;
        } else if (pos == 0) {
            while (pos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
                pos++;
            }
        }
        int i = pos + length;
        if (i + 4 > charArrayBuffer.length()) {
            return false;
        }
        boolean z = true;
        int i2 = 0;
        while (z && i2 < length) {
            z = charArrayBuffer.charAt(pos + i2) == protocol2.charAt(i2);
            i2++;
        }
        if (z) {
            z = charArrayBuffer.charAt(i) == '/';
        }
        return z;
    }

    public static RequestLine parseRequestLine(String str, LineParser lineParser) {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseRequestLine(charArrayBuffer, parserCursor);
    }

    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        try {
            skipWhitespace(charArrayBuffer, parserCursor);
            int pos2 = parserCursor.getPos();
            int indexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
            if (indexOf < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid request line: ");
                sb.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(sb.toString());
            }
            String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, indexOf);
            parserCursor.updatePos(indexOf);
            skipWhitespace(charArrayBuffer, parserCursor);
            int pos3 = parserCursor.getPos();
            int indexOf2 = charArrayBuffer.indexOf(32, pos3, upperBound);
            if (indexOf2 < 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid request line: ");
                sb2.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(sb2.toString());
            }
            String substringTrimmed2 = charArrayBuffer.substringTrimmed(pos3, indexOf2);
            parserCursor.updatePos(indexOf2);
            ProtocolVersion parseProtocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
            skipWhitespace(charArrayBuffer, parserCursor);
            if (parserCursor.atEnd()) {
                return createRequestLine(substringTrimmed, substringTrimmed2, parseProtocolVersion);
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Invalid request line: ");
            sb3.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb3.toString());
        } catch (IndexOutOfBoundsException unused) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Invalid request line: ");
            sb4.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb4.toString());
        }
    }

    /* access modifiers changed from: protected */
    public RequestLine createRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(str, str2, protocolVersion);
    }

    public static StatusLine parseStatusLine(String str, LineParser lineParser) {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        ParserCursor parserCursor = new ParserCursor(0, str.length());
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseStatusLine(charArrayBuffer, parserCursor);
    }

    public StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        try {
            ProtocolVersion parseProtocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
            skipWhitespace(charArrayBuffer, parserCursor);
            int pos2 = parserCursor.getPos();
            int indexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
            if (indexOf < 0) {
                indexOf = upperBound;
            }
            String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, indexOf);
            for (int i = 0; i < substringTrimmed.length(); i++) {
                if (!Character.isDigit(substringTrimmed.charAt(i))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Status line contains invalid status code: ");
                    sb.append(charArrayBuffer.substring(pos, upperBound));
                    throw new ParseException(sb.toString());
                }
            }
            return createStatusLine(parseProtocolVersion, Integer.parseInt(substringTrimmed), indexOf < upperBound ? charArrayBuffer.substringTrimmed(indexOf, upperBound) : "");
        } catch (NumberFormatException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Status line contains invalid status code: ");
            sb2.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb2.toString());
        } catch (IndexOutOfBoundsException unused2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Invalid status line: ");
            sb3.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(sb3.toString());
        }
    }

    /* access modifiers changed from: protected */
    public StatusLine createStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        return new BasicStatusLine(protocolVersion, i, str);
    }

    public static Header parseHeader(String str, LineParser lineParser) {
        Args.notNull(str, "Value");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        if (lineParser == null) {
            lineParser = INSTANCE;
        }
        return lineParser.parseHeader(charArrayBuffer);
    }

    public Header parseHeader(CharArrayBuffer charArrayBuffer) {
        return new BufferedHeader(charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        while (pos < upperBound && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
            pos++;
        }
        parserCursor.updatePos(pos);
    }
}
