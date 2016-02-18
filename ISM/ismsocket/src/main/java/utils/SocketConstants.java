package utils;

/**
 * Created by c85 on 07/12/15.
 */
public class SocketConstants {

    private static final String SOCKET_PORT = "8100";
//    public static final String SOCKET_URL = "http://52.29.122.89:"+SOCKET_PORT;
    public static final String SOCKET_URL = "http://52.28.165.231:"+SOCKET_PORT;
    public static final String JOIN_SOCKET = "Join Socket";

    /**
     * SOCKET EVENTS
     */
    public static final String JOIN_GROUP = "join group";
    public static final String NEW_MESSAGE = "New Message";


    /**
     * SOCKET EVENT KEYS
     */
    public static final String USER_ID = "userId";
    public static final String GROUP_ID = "groupId";
    public static final String TUTORIAL_TOPIC_ID = "tutorialTopicId";
    public static final String SENDER_ID = "senderId";
    public static final String COMMENT_SCORE = "commentScore";
    public static final String MESSAGE = "message";
    public static final String IN_ACTIVE_HOURS = "inActiveHours";
    public static final String FULL_NAME = "fullName";
    public static final String PROFILE_PICTURE = "profilePicture";
    public static final String CREATED_DATE = "created_date";


}
