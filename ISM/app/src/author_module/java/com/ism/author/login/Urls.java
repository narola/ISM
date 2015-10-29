package com.ism.author.login;

/**
 * these is the common class for all the api related url.
 */
public class Urls {

    private static final String URL_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_ARTI_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_RAVI_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";


    public static final String URL_LOGIN = URL_ARTI_HOST + "AuthenticateUser";
    public static final String URL_GETALLFEEDS = URL_ARTI_HOST + "GetAllFeeds";
    public static final String URL_ADDCOMMENT = URL_ARTI_HOST + "AddComment";
    public static final String URL_GETSTUDYMATES = URL_ARTI_HOST + "GetStudymates";
    public static final String URL_TAGFRIENDINFEED = URL_ARTI_HOST + "TagFriendInFeed";
    public static final String URL_GETALLCOMMENTS = URL_ARTI_HOST + "GetAllComments";
    public static final String URL_LIKEFEED = URL_ARTI_HOST + "LikeFeed";


    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";


    //these are the temparory data for testing

    public static final String TEST_USER_ID = "370";
    public static final String TEST_GET_ALL_FEEDS = "141";
    public static final String TEST_GETSTUDYMATES = "167";
    public static final String TEST_FEEDID = "240";
    public static final String TEST_TAGGED_BY = "134";
    public static final String[] tagUserArray = new String[]{"141", "167"};
    public static final String TEST_LIKEUSERID = "141";


//        "feed_id":240,
//            "tagged_by":134,
//            "user_id":[141,167]
//    }

}

