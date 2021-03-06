CREATE TABLE IF NOT EXISTS TaskList
(
    ID   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    NAME TEXT                          NOT NULL
);

CREATE TABLE IF NOT EXISTS Task
(
    ID      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    TASK    TEXT                           NOT NULL,
    DONE    BOOL                           NOT NULL DEFAULT FALSE,
    LIST_ID INT                            NOT NULL,

    CONSTRAINT FK FOREIGN KEY (LIST_ID)
        REFERENCES TaskList (ID)
        ON DELETE CASCADE
);