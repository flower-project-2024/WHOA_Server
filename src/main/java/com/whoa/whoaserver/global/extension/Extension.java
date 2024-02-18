package com.whoa.whoaserver.global.extension;

import static com.whoa.whoaserver.global.extension.Extension.Type.IMAGE;
import static com.whoa.whoaserver.global.extension.Extension.Type.TEXT;

import java.util.Objects;
import lombok.Getter;

@Getter
public enum Extension {
    TXT(TEXT),
    JPEG(IMAGE),
    JPG(IMAGE),
    PNG(IMAGE);

    private final Type type;

    Extension(Type type) {
        this.type = type;
    }

    public boolean isImageType() {
        return Objects.equals(this.type, IMAGE);
    }

    enum Type {
        TEXT,
        IMAGE;
    }
}
