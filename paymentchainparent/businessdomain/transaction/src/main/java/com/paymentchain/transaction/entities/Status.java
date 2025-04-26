/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.entities;

/**
 *
 * @author juaoroga
 */
public enum Status {
    PENDIENTE("01"),
    LIQUIDADA("02"),
    RECHAZADA("03"),
    CANCELADA("04");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Status fromCode(String code) {
        for (Status status : Status.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}
