public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.0328084);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert this unit → base (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert base (feet) → this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }

    public double getConversionFactor() {
        return toFeetFactor;
    }
}
public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        validate(value, unit);
        this.value = value;
        this.unit = unit;
    }

    // ===== Conversion =====
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);

        return new QuantityLength(result, targetUnit);
    }

    // ===== Addition (UC7 style) =====
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityLength(result, targetUnit);
    }

    // ===== Default add (UC6) =====
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // ===== Equality =====
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisBase, otherBase) == 0;
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
public class uc8 {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        // Conversion
        System.out.println(q1.convertTo(LengthUnit.INCH)); // 12 INCH

        // Equality
        System.out.println(q1.equals(q2)); // true

        // Addition (UC7)
        System.out.println(q1.add(q2, LengthUnit.FEET)); // 2 FEET
        System.out.println(q1.add(q2, LengthUnit.YARD)); // ~0.667 YARD

        // Yard + Feet
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARD)
                        .add(new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.YARD)
        ); // 2 YARD
    }
}