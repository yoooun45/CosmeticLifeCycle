package leesimjeonsim.user.cosmeticlifecycle;

public class CsmtData {
    public String CsmtID;
    public String category;
    public String image;
    public String name;
    public String brand;
    public float moisture;
    public float oil;
    public float smooth;
    public float absorption;
    public float last;
    public float harmful;

    public float Score(float[] inputdata) {
        float result;
        result = moisture*inputdata[0]
                +oil*inputdata[1]
                +smooth*inputdata[2]
                +absorption*inputdata[3]
                +last*inputdata[4]
                -harmful*inputdata[5];
        return result;
    }
}
