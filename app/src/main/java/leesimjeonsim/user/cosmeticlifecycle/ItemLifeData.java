package leesimjeonsim.user.cosmeticlifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemLifeData {
    public static final List<LifeItem> ITEMS = new ArrayList<LifeItem>();

    public static final Map<String, LifeItem> ITEM_MAP = new HashMap<String, LifeItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(LifeItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    private static LifeItem createDummyItem(int position) {
        return new LifeItem(String.valueOf(position), "노세범 " + position, "content","details","30");
    }

    public static class LifeItem {
        public final String id;
        public final String title;
        public final String content;
        public final String details;
        public final String d_day;

        public LifeItem(String id,String title, String content, String details, String d_day) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.details = details;
            this.d_day = d_day;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
