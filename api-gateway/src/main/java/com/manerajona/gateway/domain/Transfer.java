package com.manerajona.gateway.domain;

public record Transfer(Payment payment, Account sender, Account recipient, String reference) {

}