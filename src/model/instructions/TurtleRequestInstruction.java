package model.instructions;

public class TurtleRequestInstruction extends Instruction {
	// TODO call view for these fields, make sure that before calling .execute we first update view. also mode turtle command out of this package
	public TurtleRequestInstruction(String[] input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		// Need view for all of these, holding off
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}

}