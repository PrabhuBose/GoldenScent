package com.senthil.prabhu.mobile.android.godenscent.constants;

import com.senthil.prabhu.mobile.android.godenscent.R;

/**
 * Created by PrAbHu on 10/15/18 GodenScent.
 */
public class AppConstants {

    public final static String[] categories = {"Make Up", "Skin Care", "Hair Care", "Beauty\nTools",
            "Home\nFragrances", "Gift", "Men"};

    public final static Integer[] categoriesImages = {R.drawable.makeup, R.drawable.skincare, R.drawable.haircare,
            R.drawable.beauty_tools, R.drawable.home_fragrance, R.drawable.gift, R.drawable.men};

    public static final int MAX_CELLS_PER_GRID_ROW = 3;
    public static final int[] CELLS_IDS = {
            R.id.cell_1,
            R.id.cell_2,
            R.id.cell_3
    };


    public final static String[] gridData = {"Pencil", "Lipstick", "Lipgloss", "Lip Balm", "Treatment", "Palette"};

    public final static Integer[] thumbNailImages = {R.drawable.pencil, R.drawable.lipstick, R.drawable.lip_gloss,
            R.drawable.lip_balm, R.drawable.treatment, R.drawable.palette};

    public final static String[] groupHeading = {"Lips", "Face", "Nails"};

    public final static Integer[] bestSellersProductImage = {R.drawable.parada, R.drawable.dolce_gaban, R.drawable.agner,
            R.drawable.parada, R.drawable.dolce_gaban, R.drawable.agner,
            R.drawable.parada, R.drawable.dolce_gaban, R.drawable.agner};

    public final static String[] bestSellersProductName = {"Parada", "Dolice & Gabbana", "Aigner", "Parada", "Dolice & Gabbana", "Aigner",
            "Parada", "Dolice & Gabbana", "Aigner"};

    public final static String[] bestSellersProductDesc = {"Candy", "The One and text goes here until it breaks", "N.1",
            "Candy", "The One and text goes here until it breaks", "N.1",
            "Candy", "The One and text goes here until it breaks", "N.1"};

    public final static String[] bestSellersProductOldPrice = {"356 SR", "356 SR", "356 SR",
            "356 SR", "356 SR", "356 SR",
            "356 SR", "356 SR", "356 SR"};

    public final static String[] bestSellersProductNewPrice = {"320 SR", "", "", "320 SR", "", "", "320 SR", "", ""};
}
