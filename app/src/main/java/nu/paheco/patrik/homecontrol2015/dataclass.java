package nu.paheco.patrik.homecontrol2015;

/**
 * Created by user on 1/18/15.
 */
public class dataclass {
    //public int icon;
    public String title;
    public String info;
    public dataclass(){
        super();
    }

    //public dataclass(int icon, String title) {
    public dataclass(String title, String info) {
        super();
        //this.icon = icon;
        this.title = title;
        this.info = info;
    }
}