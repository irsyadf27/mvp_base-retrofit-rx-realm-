package kitmybit.mvp_base_example.ui.main;

public class MainRepository implements MainContract.Repository{
    /*
    * Class work with API, DB
    * */

    @Override
    public String getLogs() {
        return "Alex";
    }
}
