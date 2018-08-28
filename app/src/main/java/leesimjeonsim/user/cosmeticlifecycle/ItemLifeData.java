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
    public String category;
    public String d_day;
    public String end_day;

    public ItemLifeData() {}

    public ItemLifeData(String id,String title, String content, String details, String category,String d_day, String end_day) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.details = details;
        this.category = category;
        this.d_day = d_day;
        this.end_day = end_day;

    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemLifeData){
            ItemLifeData lifeDataObj = (ItemLifeData) obj;

            if ((lifeDataObj.title.equals(this.title))
                    && lifeDataObj.content.equals(this.content)
                    && lifeDataObj.details.equals(this.details)
                    //&& lifeDataObj.d_day.equals(this.d_day)
                    && lifeDataObj.id.equals(this.id)) {
                return true;
            }
            return false;
        }
        return super.equals(obj);
    }
}
