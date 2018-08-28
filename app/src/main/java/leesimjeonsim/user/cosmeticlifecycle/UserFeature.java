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

    public double[] calFinalFeature(double[][] data) {
        double[] result = new double[]{0, 0, 0, 0, 0, 0};
        double count=0;
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
            double[][] data, double[] result, int Num) {
        for (int i = 0; i < 6; i++) {
            result[i] += data[Num][i];
        }
    }

    public void set_Oily(Boolean oily) {
        Oily = oily;
    }

    public void set_Dry(Boolean dry) {
        Dry = dry;
    }

    public void set_Complexity(Boolean complexity) {
        Complexity = complexity;
    }

    public void set_Atopy(Boolean atopy) {
        Atopy = atopy;
    }

    public void set_Acne(Boolean acne) {
        Acne = acne;
    }

    public void set_Sensitivity(Boolean sensitivity) {
        Sensitivity = sensitivity;
    }

    public void set_Blush(Boolean blush) {
        Blush = blush;
    }

    public void set_Dead(Boolean dead) {
        Dead = dead;
    }

    public void set_Pore(Boolean pore) {
        Pore = pore;
    }

    public void set_Elasticity(Boolean elasticity) {
        Elasticity = elasticity;
    }

}
