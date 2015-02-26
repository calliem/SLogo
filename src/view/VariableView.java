package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableView {

	private StringProperty myName;
	private DoubleProperty myVar;

	public VariableView(String varName, double varValue) {
		this.myName = new SimpleStringProperty(varName);
		this.myVar = new SimpleDoubleProperty(varValue);
	}

	public void setName(String name) {
		myName.set(name);
	}

	public String getName() {
		return myName.get();
	}

	public void setValue(double value) {
		myVar.set(value);
	}

	public double getValue() {
		return myVar.get();
	}

	public double getVar() {
		return myVar.get();
	}

	public StringProperty myNameProperty() {
		return myName;
	}

	public DoubleProperty myVarProperty() {
		return myVar;
	}
}