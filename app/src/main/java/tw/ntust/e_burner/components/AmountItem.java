package tw.ntust.e_burner.components;

public class AmountItem {

    private long id;
    private String title;

    public AmountItem(int _id, String _title) {
        id =_id;
        title = _title;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
}
