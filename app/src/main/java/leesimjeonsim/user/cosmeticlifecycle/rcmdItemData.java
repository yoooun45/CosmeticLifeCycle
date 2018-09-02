package leesimjeonsim.user.cosmeticlifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rcmdItemData {
    public String name;
    public String rcmd_dday;
    public String item_id1;
    public String item_name1;
    public String item_brand1;
    public String item_id2;
    public String item_name2;
    public String item_brand2;
    public String item_id3;
    public String item_name3;
    public String item_brand3;

    public rcmdItemData() {
    }

    /*public rcmdItemData(String name, String rcmd_dday
            ,String item_id1, String item_name1, String item_brand1
            ,String item_id2, String item_name2, String item_brand2
            ,String item_id3, String item_name3, String item_brand3) {
        this.name = name;
        this.rcmd_dday = rcmd_dday;
        this.item_id1 = item_id1;
        this.item_name1 = item_name1;
        this.item_brand1 = item_brand1;
        this.item_id2 = item_id2;
        this.item_name2 = item_name2;
        this.item_brand2 = item_brand2;
        this.item_id3 = item_id3;
        this.item_name3 = item_name3;
        this.item_brand3 = item_brand3;
    }*/

    @Override
    public String toString() {
        return name;
    }

}
