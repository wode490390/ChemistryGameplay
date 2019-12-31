package cn.wode490390.nukkit.chemistry.resourcepacks;

import cn.nukkit.resourcepacks.ResourcePack;

public abstract class DownloadedPack implements ResourcePack {

    private static final byte[] EMPTY_DATA = new byte[0];

    @Override
    public String getPackName() {
        return "";
    }

    @Override
    public int getPackSize() {
        return 0;
    }

    @Override
    public byte[] getSha256() {
        return EMPTY_DATA;
    }

    @Override
    public byte[] getPackChunk(int off, int len) {
        return EMPTY_DATA;
    }
}
