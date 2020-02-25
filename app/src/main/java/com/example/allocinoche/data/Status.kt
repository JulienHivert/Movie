package com.example.allocinoche.data

enum class Status(val statusMessage: String) {
    SUCCESS("Success"),
    ERROR("Error"),
    CONNECTED("Connected"),
    NOT_CONNECTED("Not connected"),
    UNKNOWN("Unknown")
}