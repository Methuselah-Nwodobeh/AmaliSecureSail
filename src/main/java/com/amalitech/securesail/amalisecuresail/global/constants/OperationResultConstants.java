package com.amalitech.securesail.amalisecuresail.global.constants;

public enum OperationResultConstants {
    SUCCESS,
    FAIL,
    ERROR,
    USER_ALREADY_EXISTS("User already exists"),
    ;

    OperationResultConstants(String s) {
//        this.s = s;
    }

    OperationResultConstants() {

    }
}
