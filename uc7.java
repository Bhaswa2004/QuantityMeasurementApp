static class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        validate(value, unit);
        this.value = value;
        this.unit = unit;
    }

    // ===== UC6: Default add (first operand unit) =====
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // ===== UC7: NEW add with target unit =====
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        // Step 1: convert both to base (feet)
        double thisFeet = this.unit.toFeet(this.value);
        double otherFeet = other.unit.toFeet(other.value);

        // Step 2: add
        double sumFeet = thisFeet + otherFeet;

        // Step 3: convert to target unit
        double resultValue = targetUnit.fromFeet(sumFeet);

        return new QuantityLength(resultValue, targetUnit);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    private static void validate(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
    }
}
