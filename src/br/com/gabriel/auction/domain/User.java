package br.com.gabriel.auction.domain;

import java.util.Objects;

public class User {

	private int id;
	private String name;
	
	public User(String name) {
		this(0, name);
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getId() == user.getId() &&
				getName().equals(user.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
	}
}
