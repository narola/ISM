package com.ism.author.login;

/**
 * these is the common class for all the api related url.
 */
public class Urls {

    private static final String URL_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    //    public static final String URL_LOGIN = URL_HOST + "AuthenticateUser";

    public static final String URL_LOGIN = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=AuthenticateUser";
    public static final String URL_GETALLFEEDS = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=GetAllFeeds";
    public static final String URL_ADDCOMMENT = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=AddComment";
    public static final String URL_GETSTUDYMATES = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=GetStudymates";
    public static final String URL_TAGFRIENDINFEED = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=TagFriendInFeed";
    public static final String URL_GETALLCOMMENTS = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=GetAllComments";


    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";


}
