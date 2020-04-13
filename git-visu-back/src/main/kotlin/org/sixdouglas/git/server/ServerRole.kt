package org.sixdouglas.git.server

enum class ServerRole {
    APPLICATION_SERVER,
    BATCH,
    DATABASE,
    REPORTING,
    EXTRACTION,
    ELASTICSEARCH,
    TRANSCODING,
    DECLARATIVE,
    INVOICING,
    JMS;
}