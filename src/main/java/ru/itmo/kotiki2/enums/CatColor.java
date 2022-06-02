package ru.itmo.kotiki2.enums;

public enum CatColor {
    BLACK,
    SEAL,
    BROWN,
    RUDDY,
    TAWNY,
    BLUE,
    CHOCOLATE,
    LILAC,
    RED,
    CREAM,
    CINNAMON,
    FAWN,
    WHITE,
    AMBER,
    CHESTNUT,
    BLUE_CREAM,
    EBONY,
    SABLE,
    SORREL,
    HONEY,
    SILVER,
    SMOKE,
    GOLDEN,
    BALD,
    LIGHT_AMBER,
    RED_AMBER,
    CREAM_AMBER,
    BLACK_TORTIE,
    BLUE_TORTIE,
    CHOCOLATE_TORTIE,
    LILAC_TORTIE,
    CINNAMON_TORTIE,
    FAWN_TORTIE,
    AMBER_TORTIE,
    SORREL_TORTIE,
    UNREGISTERED;

    @Override
    public String toString()
    {
        String res = super.toString();
        if (super.toString().contains("_")) {
            res = res.replace('_', '-');
        }
        return res;
    }
}
