package oxforddictionaries.model.request;

public class PastebinPost {
    private String api_dev_key;
    private String api_option;
    private String api_paste_code;
    private int api_paste_private;
    private String api_user_key;
    private String api_paste_name;
    private String api_paste_format;
    private String api_paste_expire_date;
    private String api_folder_key;

    public PastebinPost(String api_dev_key, String api_option, String api_paste_code, int api_paste_private) {
        this.api_dev_key = api_dev_key;
        this.api_option = api_option;
        this.api_paste_code = api_paste_code;
        this.api_paste_private = api_paste_private;
    }

    public void setApi_user_key(String api_user_key) {
        this.api_user_key = api_user_key;
    }

    public void setApi_paste_name(String api_paste_name) {
        this.api_paste_name = api_paste_name;
    }

    public void setApi_paste_format(String api_paste_format) {
        this.api_paste_format = api_paste_format;
    }

    public void setApi_paste_expire_date(String api_paste_expire_date) {
        this.api_paste_expire_date = api_paste_expire_date;
    }

    public void setApi_folder_key(String api_folder_key) {
        this.api_folder_key = api_folder_key;
    }

    @Override
    public String toString() {
        String str = "api_dev_key=" + api_dev_key + "&api_option=" + api_option + "&api_paste_code=" + api_paste_code + "&api_paste_private=" + api_paste_private;
        if (api_paste_name != null) {
            str += "&api_paste_name=" + api_paste_name;
        }
        if (api_paste_format != null) {
            str += "&api_paste_format=" + api_paste_format;
        }
        if (api_user_key != null) {
            str += "&api_user_key=" + api_user_key;
        }
        if (api_paste_expire_date != null) {
            str += "&api_paste_expire_date=" + api_paste_expire_date;
        }
        if (api_folder_key != null) {
            str += "&api_folder_key=" + api_folder_key;
        }
        return str;
    }
}
