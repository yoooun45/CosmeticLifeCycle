package leesimjeonsim.user.cosmeticlifecycle;

public class UserData {
    public String userName; // 사용자 이름
    public UserFeature feature; //사용자 특성
    public int age;
    public int preference; // 사용자 선호도
    public boolean sex;//true :  여성, false : 남성

    UserData(){}

    UserData(String userName,UserFeature feature,int preference,int age){
        this.userName = userName;
        this.feature = feature;
        this.preference = preference;
        this.age = age;
    }

    public void setUserName(String userName){this.userName = userName;}

    public void setFeature(UserFeature feature){this.feature = feature;}

    public void setAge(int age){this.age = age;}

    public void setPreference(int preference){this.preference = preference;}

    public void setSex(boolean sex){this.sex = sex;}

}
