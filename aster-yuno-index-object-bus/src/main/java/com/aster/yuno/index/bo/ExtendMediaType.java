package com.aster.yuno.index.bo;

import org.springframework.http.MediaType;

public class ExtendMediaType extends MediaType {
    public ExtendMediaType(String type, String subType) {
        super(type, subType);
    }

    public static final MediaType APPLICATION_TEXT;

    static {
        APPLICATION_TEXT = new ExtendMediaType("application", "text");
    }
}
