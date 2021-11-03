package application.model;

public enum UserRole {
    USER(
            UserAuth.CREATE_BLOG,
            UserAuth.CREATE_ENTRY,
            UserAuth.CREATE_COMMENT,

            UserAuth.READ_MY_DATA, //excluding password

            UserAuth.MODIFY_OWN_DATA,
            UserAuth.MODIFY_MY_BLOG,
            UserAuth.MODIFY_MY_ENTRY,
            UserAuth.MODIFY_MY_COMMENT,

            UserAuth.DELETE_MY_ACCOUNT,
            UserAuth.DELETE_MY_BLOG,
            UserAuth.DELETE_MY_ENTRY,
            UserAuth.DELETE_MY_COMMENT
    ),
    MODERATOR(
            UserAuth.CREATE_BLOG,
            UserAuth.CREATE_ENTRY,
            UserAuth.CREATE_COMMENT,

            UserAuth.READ_MY_DATA, //excluding password

            UserAuth.MODIFY_OWN_DATA,
            UserAuth.MODIFY_MY_BLOG,
            UserAuth.MODIFY_MY_ENTRY,
            UserAuth.MODIFY_MY_COMMENT,
            UserAuth.MODIFY_OTHERS_BLOG,
            UserAuth.MODIFY_OTHERS_ENTRY,
            UserAuth.MODIFY_OTHERS_COMMENT,

            UserAuth.DELETE_MY_ACCOUNT,
            UserAuth.DELETE_MY_BLOG,
            UserAuth.DELETE_MY_ENTRY,
            UserAuth.DELETE_MY_COMMENT,
            UserAuth.DELETE_OTHERS_BLOG,
            UserAuth.DELETE_OTHERS_ENTRY,
            UserAuth.DELETE_OTHERS_COMMENT
    ),
    ADMIN(
            UserAuth.CREATE_TEMPLATE,
            UserAuth.CREATE_BLOG,
            UserAuth.CREATE_ENTRY,
            UserAuth.CREATE_COMMENT,

            UserAuth.READ_MY_DATA, //excluding password
            UserAuth.READ_OTHERS_DATA, //excluding password

            UserAuth.MODIFY_OWN_DATA,
            UserAuth.MODIFY_OTHERS_DATA, //excluding password
            UserAuth.MODIFY_TEMPLATE,
            UserAuth.MODIFY_MY_BLOG,
            UserAuth.MODIFY_MY_ENTRY,
            UserAuth.MODIFY_MY_COMMENT,
            UserAuth.MODIFY_OTHERS_BLOG,
            UserAuth.MODIFY_OTHERS_ENTRY,
            UserAuth.MODIFY_OTHERS_COMMENT,

            UserAuth.DELETE_MY_ACCOUNT,
            UserAuth.DELETE_OTHERS_ACCOUNT,
            UserAuth.DELETE_TEMPLATE,
            UserAuth.DELETE_MY_BLOG,
            UserAuth.DELETE_MY_ENTRY,
            UserAuth.DELETE_MY_COMMENT,
            UserAuth.DELETE_OTHERS_BLOG,
            UserAuth.DELETE_OTHERS_ENTRY,
            UserAuth.DELETE_OTHERS_COMMENT
    );

    public final UserAuth[] AUTHS;

    UserRole(UserAuth... auths) {
        AUTHS = auths;
    }
}
