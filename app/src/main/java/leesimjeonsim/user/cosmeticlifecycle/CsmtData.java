package leesimjeonsim.user.cosmeticlifecycle;

public class CsmtData {
    public String CsmtID;
    public float Moisture;
    public float Oil;
    public float Smooth;
    public float Absorption;
    public float Last;
    public float Harmful;

    public float Score(float[] inputdata) {
        float result;
        result = Moisture*inputdata[0]
                +Oil*inputdata[1]
                +Smooth*inputdata[2]
                +Absorption*inputdata[3]
                +Last*inputdata[4]
                -Harmful*inputdata[5];
        return result;
    }
}
