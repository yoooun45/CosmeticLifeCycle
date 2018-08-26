package leesimjeonsim.user.cosmeticlifecycle;

public class UserFeature {
    public Boolean Oily = false;
    public Boolean Dry = false;
    public Boolean Complexity = false;

    public Boolean Atopy = false;
    public Boolean Acne = false;
    public Boolean Sensitivity = false;
    public Boolean Blush = false;
    public Boolean Dead = false;
    public Boolean Pore = false;
    public Boolean Elasticity = false;

    public float[] calFinalFeature(float[][] data) {
        float[] result = new float[]{0, 0, 0, 0, 0, 0};
        float count=0;
        if (Oily) {
            addFeature(data, result, 0);
            ++count;
        }
        if (Dry) {
            addFeature(data, result, 1);
            ++count;
        }
        if (Complexity) {
            addFeature(data, result, 2);
            ++count;
        }

        if (Atopy) {
            addFeature(data, result, 3);
            ++count;
        }
        if (Acne) {
            addFeature(data, result, 4);
            ++count;
        }
        if (Sensitivity) {
            addFeature(data, result, 5);
            ++count;
        }
        if (Blush) {
            addFeature(data, result, 6);
            ++count;
        }
        if (Dead) {
            addFeature(data, result, 7);
            ++count;
        }
        if (Pore) {
            addFeature(data, result, 8);
            ++count;
        }
        if (Elasticity) {
            addFeature(data, result, 9);
            ++count;
        }
        for(int i = 0;i<6;i++){result[i]=result[i]/count;}
        return result;
    }

    public void addFeature(
            float[][] data, float[] result, int Num) {
        for (int i = 0; i < 6; i++) {
            result[i] += data[Num][i];
        }
    }

    public void setOily(Boolean oily) {
        Oily = oily;
    }

    public void setDry(Boolean dry) {
        Dry = dry;
    }

    public void setComplexity(Boolean complexity) {
        Complexity = complexity;
    }

    public void setAtopy(Boolean atopy) {
        Atopy = atopy;
    }

    public void setAcne(Boolean acne) {
        Acne = acne;
    }

    public void setSensitivity(Boolean sensitivity) {
        Sensitivity = sensitivity;
    }

    public void setBlush(Boolean blush) {
        Blush = blush;
    }

    public void setDead(Boolean dead) {
        Dead = dead;
    }

    public void setPore(Boolean pore) {
        Pore = pore;
    }

    public void setElasticity(Boolean elasticity) {
        Elasticity = elasticity;
    }

}
