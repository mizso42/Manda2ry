package application.model;

public enum UserAuth {

    //CREATE type authentications
    CREATE_TEMPLATE,
    CREATE_BLOG,
    CREATE_ENTRY,
    CREATE_COMMENT,

    //READ type authentications
    READ_MY_DATA, //excluding password
    READ_OTHERS_DATA, //excluding password

    //MODIFY type authentications
    MODIFY_OWN_DATA,
    MODIFY_OTHERS_DATA, //excluding password
    MODIFY_TEMPLATE,
    MODIFY_MY_BLOG,
    MODIFY_MY_ENTRY,
    MODIFY_MY_COMMENT,
    MODIFY_OTHERS_BLOG,
    MODIFY_OTHERS_ENTRY,
    MODIFY_OTHERS_COMMENT,

    //DELETE type authentications
    DELETE_MY_ACCOUNT,
    DELETE_OTHERS_ACCOUNT,
    DELETE_TEMPLATE,
    DELETE_MY_BLOG,
    DELETE_MY_ENTRY,
    DELETE_MY_COMMENT,
    DELETE_OTHERS_BLOG,
    DELETE_OTHERS_ENTRY,
    DELETE_OTHERS_COMMENT
}
