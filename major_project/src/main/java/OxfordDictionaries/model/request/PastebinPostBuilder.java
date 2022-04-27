package oxfordDictionaries.model.request;

public class PastebinPostBuilder {
    private PastebinPost pastebinPost;

    public void newItem(String api_dev_key, String api_option, String api_paste_code, int api_paste_private) {
        this.pastebinPost = new PastebinPost(api_dev_key, api_option, api_paste_code, api_paste_private);
    }

    public void setUserKey(String api_user_key) {
        pastebinPost.setApi_user_key(api_user_key);
    }

    public void setPasteName(String api_paste_name) {
        pastebinPost.setApi_paste_name(api_paste_name);
    }

    public void setPasteFormat(String api_paste_format) {
        pastebinPost.setApi_paste_format(api_paste_format);
    }

    public void setPasteExpireDate(String api_paste_expire_date) {
        pastebinPost.setApi_paste_expire_date(api_paste_expire_date);
    }

    public void setFolderKey(String api_folder_key) {
        pastebinPost.setApi_folder_key(api_folder_key);
    }

    public PastebinPost getPastebinPost() {
        return pastebinPost;
    }


}
