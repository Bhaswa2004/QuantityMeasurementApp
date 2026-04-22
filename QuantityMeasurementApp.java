public class uc6 {

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

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        // ===== ADDITION (Core UC6) =====
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            // Step 1: convert both to base (feet)
            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            // Step 2: add
            double sumInFeet = thisInFeet + otherInFeet;

            // Step 3: convert back to THIS unit
            double resultValue = this.unit.fromFeet(sumInFeet);

            return new QuantityLength(resultValue, this.unit);
        }

        // ===== Static version (optional API) =====
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            if (a == null || b == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }
            return a.add(b);
        }

        // ===== Equality (unchanged) =====
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

    // ===== Main Method (Test cases) =====
    public static void main(String[] args) {

        // Same unit
        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .add(new QuantityLength(2.0, LengthUnit.FEET))
        ); // 3 FEET

        // Feet + Inches
        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .add(new QuantityLength(12.0, LengthUnit.INCH))
        ); // 2 FEET

        // Inches + Feet
        System.out.println(
                new QuantityLength(12.0, LengthUnit.INCH)
                        .add(new QuantityLength(1.0, LengthUnit.FEET))
        ); // 24 INCH

        // Yard + Feet
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARD)
                        .add(new QuantityLength(3.0, LengthUnit.FEET))
        ); // 2 YARD

        // CM + Inch
        System.out.println(
                new QuantityLength(2.54, LengthUnit.CM)
                        .add(new QuantityLength(1.0, LengthUnit.INCH))
        ); // ~5.08 CM

        // Zero
        System.out.println(
                new QuantityLength(5.0, LengthUnit.FEET)
                        .add(new QuantityLength(0.0, LengthUnit.INCH))
        ); // 5 FEET

        // Negative
        System.out.println(
                new QuantityLength(5.0, LengthUnit.FEET)
                        .add(new QuantityLength(-2.0, LengthUnit.FEET))
        ); // 3 FEET
    }
}
