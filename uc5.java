public class uc5 {

    // ===== ENUM (same as UC4) =====
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // ===== Quantity Class =====
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        // ===== Static Conversion API =====
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            validate(value, source);
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            // Step 1: convert to base (feet)
            double valueInFeet = source.toFeet(value);

            // Step 2: convert to target
            return target.fromFeet(valueInFeet);
        }

        // ===== Instance Conversion =====
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        // ===== Equality (same as UC3/UC4) =====
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    this.unit.toFeet(this.value),
                    other.unit.toFeet(other.value)
            ) == 0;
        }

        // ===== toString() override =====
        @Override
        public String toString() {
            return value + " " + unit;
        }

        // ===== Validation =====
        private static void validate(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
        }
    }

    // ===== Overloaded Demo Methods =====

    // Method 1: raw values
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Method 2: using object
    public static void demonstrateLengthConversion(QuantityLength q, LengthUnit to) {
        QuantityLength result = q.convertTo(to);
        System.out.println(q + " = " + result);
    }

    // Equality demo
    public static void demonstrateLengthEquality(QuantityLength q1, QuantityLength q2) {
        System.out.println(q1 + " == " + q2 + " : " + q1.equals(q2));
    }

    // ===== Main Method =====
    public static void main(String[] args) {

        // Basic conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);   // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);   // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);  // 1
        demonstrateLengthConversion(1.0, LengthUnit.CM, LengthUnit.INCH);     // ~0.393701

        // Instance conversion
        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.INCH);

        // Equality checks
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        demonstrateLengthEquality(q1, q2); // true

        // Edge cases
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);  // 0
        demonstrateLengthConversion(-1.0, LengthUnit.FEET, LengthUnit.INCH); // -12
    }
}
