package com.manerajona.gateway.domain;

public record Account(String iban, CurrencyISOCode currency, String country, String bank, String name) {

}