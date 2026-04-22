enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    // Convert to base unit (KG)
    public double convertToBaseUnit(double value) {
        return value * toKgFactor;
    }

    // Convert from base unit (KG)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKgFactor;
    }
}
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // Convert to another unit
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // Equality (base unit comparison)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityWeight)) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        return Double.valueOf(base).hashCode();
    }

    // Addition (default → this.unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // Addition with target unit
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
// ===== WEIGHT TESTS (UC9) =====
System.out.println("\n===== WEIGHT OPERATIONS =====");

// Equality
QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

System.out.println("1kg == 1000g: " + w1.equals(w2));

// Conversion
QuantityWeight w3 = new QuantityWeight(2.0, WeightUnit.POUND);
System.out.println("2 lb in kg: " + w3.convertTo(WeightUnit.KILOGRAM));

// Addition
QuantityWeight w4 = new QuantityWeight(500.0, WeightUnit.GRAM);
QuantityWeight w5 = new QuantityWeight(0.5, WeightUnit.KILOGRAM);

System.out.println("500g + 0.5kg: " + w4.add(w5));

// Addition with target unit
System.out.println("1kg + 1000g in grams: " + w1.add(w2, WeightUnit.GRAM));

// Cross unit addition
QuantityWeight w6 = new QuantityWeight(1.0, WeightUnit.POUND);
QuantityWeight w7 = new QuantityWeight(453.592, WeightUnit.GRAM);

System.out.println("1lb + 453.592g in pounds: " + w6.add(w7, WeightUnit.POUND));
