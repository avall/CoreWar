package com.gmail.s0o3r0a4.corewar.maths;

public class Maths
{
    public static int mod(int integer, int maximum)
    {
        assert maximum != 0;

//        integer = (maximum + (integer % maximum) % maximum);
        integer = integer % maximum;
        if (integer < 0)
        {
            integer += maximum;
        }

        return integer;
    }
}
