CREATE TABLE PUBLIC.FLOW_LOGS(
    ID          SERIAL      PRIMARY KEY,
    FLOW_ID     INTEGER     NOT NULL,
    HANDLER_ID  INTEGER     NOT NULL,
    STATUS      VARCHAR(32) NOT NULL,
    DETAIL      JSONB,

    FOREIGN KEY (FLOW_ID) REFERENCES public.FLOWS (ID),
    FOREIGN KEY (HANDLER_ID) REFERENCES public.HANDLERS (ID)
);