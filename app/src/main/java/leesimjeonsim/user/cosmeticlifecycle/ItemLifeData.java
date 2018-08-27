package leesimjeonsim.user.cosmeticlifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ItemLifeData {
    public String id;
    public String title;
    public String content;
    public String details;
    public String d_day;

    public ItemLifeData() {}

    public ItemLifeData(String id,String title, String content, String details, String d_day) {
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
