package com.turlir.abakgists.model;

public interface GistsTable {

    String
            GISTS = "gists",
            ID = "id",
            DESC = "desc",
            URL = "url",
            CREATED = "created",
            OWNER_LOGIN = "ownerLogin",
            OWNER_AVATAR = "ownerAvatarUrl",
            NOTE = "note";

    String CREATE =
            "CREATE TABLE " + GISTS + "( " +
                    ID + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE, " +
                    DESC + " TEXT, " +
                    URL + " TEXT NOT NULL, " +
                    CREATED + " TEXT NOT NULL, " +
                    OWNER_LOGIN + " TEXT, " +
                    OWNER_AVATAR + " TEXT, " +
                    NOTE + " TEXT " +
                    ")";

}