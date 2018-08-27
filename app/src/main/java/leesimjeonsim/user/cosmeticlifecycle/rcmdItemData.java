package leesimjeonsim.user.cosmeticlifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rcmdItemData {
    public static final List<rcmdItem> ITEMS = new ArrayList<rcmdItem>();

    public static final Map<String, rcmdItem> ITEM_MAP = new HashMap<String, rcmdItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(rcmdItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }


    private static rcmdItem createDummyItem(int position) {
        return new rcmdItem("VDL 페스티벌 립스틱", "D - 7" + position
                , "제품 이름","nature republic - 카테고리"
                , "제품 이름","nature republic - 카테고리"
                , "제품 이름","nature republic - 카테고리");
    }

    public static class rcmdItem {
        public final String name;
        public final String rcmd_dday;
        public final String item_name1;
        public final String item_brand1;
        public final String item_name2;
        public final String item_brand2;
        public final String item_name3;
        public final String item_brand3;

        public rcmdItem(String name,String rcmd_dday,String item_name1, String item_brand1
                , String item_name2, String item_brand2, String item_name3, String item_brand3) {
            this.name = name;
            this.rcmd_dday = rcmd_dday;
            this.item_name1 = item_name1;
            this.item_brand1 = item_brand1;
            this.item_name2 = item_name2;
            this.item_brand2 = item_brand2;
            this.item_name3 = item_name3;
            this.item_brand3 = item_brand3;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
