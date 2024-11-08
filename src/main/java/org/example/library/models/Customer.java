package org.example.library.models;

public record Customer(
		String name,
		String email,
		String phone,
		String address,
		String city,
		String state,
		String zipCode,
		String country) {

}
