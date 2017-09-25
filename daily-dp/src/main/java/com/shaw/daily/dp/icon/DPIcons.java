package com.shaw.daily.dp.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by shaw on 2017/9/25.
 */

public enum DPIcons implements Icon {

    icon_wei_bo('\ue66b'),
    icon_we_chat('\ue623');

    private char character;

    DPIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
