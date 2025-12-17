package com.nuvei.nuvei_sdk.helper;


import com.nuvei.nuvei_sdk.R;
import com.nuvei.nuvei_sdk.models.addCard.CardInfoModel;

import java.util.Arrays;
import java.util.List;

public class CardTypes {
    public static final List<CardInfoModel> ALL = Arrays.asList(
            new CardInfoModel(
                    "Visa",
                    "^4",
                    "#### #### #### ####",
                    3,
                    Arrays.asList(13, 16, 19),
                    "vi",
                    R.drawable.ic_visa,
                    new int[]{0xFF1a1f71, 0xFF3d5bf6}
            ),
            new CardInfoModel(
                    "Mastercard",
                    "^(5[1-5]|2[2-7])",
                    "#### #### #### ####",
                    3,
                    Arrays.asList(16),
                    "mc",
                    R.drawable.ic_mastercard,

                    new int[]{0xFFeb001b, 0xFFf79e1b}
            ),
            new CardInfoModel(
                    "American Express",
                    "^3[47]",
                    "#### ###### #####",
                    4,
                    Arrays.asList(15),
                    "ax",
                    R.drawable.ic_amex,
                    new int[]{0xFF2e77bb, 0xFF1e5799}
            ),
            new CardInfoModel(
                    "Diners",
                    "^3(0[0-5]|[68])",
                    "#### ###### ####",
                    3,
                    Arrays.asList(14),
                    "di",
                    R.drawable.ic_diners,
                    new int[]{0xFF006ba1, 0xFF00b5e2}
            )
    );

}
