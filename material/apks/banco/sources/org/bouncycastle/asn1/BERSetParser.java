package org.bouncycastle.asn1;

import java.io.IOException;

public class BERSetParser implements ASN1SetParser {
    private ASN1StreamParser a;

    BERSetParser(ASN1StreamParser aSN1StreamParser) {
        this.a = aSN1StreamParser;
    }

    public ASN1Primitive getLoadedObject() {
        return new BERSet(this.a.a());
    }

    public ASN1Encodable readObject() {
        return this.a.readObject();
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
