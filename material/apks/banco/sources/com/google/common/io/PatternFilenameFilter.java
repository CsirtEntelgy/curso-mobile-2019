package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class PatternFilenameFilter implements FilenameFilter {
    private final Pattern a;

    public PatternFilenameFilter(String str) {
        this(Pattern.compile(str));
    }

    public PatternFilenameFilter(Pattern pattern) {
        this.a = (Pattern) Preconditions.checkNotNull(pattern);
    }

    public boolean accept(@Nullable File file, String str) {
        return this.a.matcher(str).matches();
    }
}
