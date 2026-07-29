package com.karmick.stecs;

/** Holds incident data. */
public class Incident {
	private String id = "";
	private String name = "";
	private boolean checked = false;

	public Incident() {
	}

	public Incident(String name) {
		this.name = name;
	}

	public Incident(String name, boolean checked) {
		this.name = name;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toString() {
		return name;
	}

	public void toggleChecked() {
		checked = !checked;
	}

}
