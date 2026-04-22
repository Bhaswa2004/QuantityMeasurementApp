public class uc3 {

    // ===== ENUM for Units =====
    enum LengthUnit {
        FEET(1.0),          // base unit
        INCH(1.0 / 12.0);   // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ===== Generic Quantity Class =====
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            // Reflexive
            if (this == obj) return true;

            // Null + Type check
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            // Compare after conversion to common unit
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // ===== Main Method =====
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength q5 = new QuantityLength(2.0, LengthUnit.FEET);

        // Cross-unit equality
        System.out.println("1 ft == 12 inch : " + q1.equals(q2)); // true

        // Same unit equality
        System.out.println("1 inch == 1 inch : " + q3.equals(q4)); // true

        // Different values
        System.out.println("1 ft == 2 ft : " + q1.equals(q5)); // false

        // Null comparison
        System.out.println("q1 == null : " + q1.equals(null)); // false

        // Same reference
        System.out.println("Same reference : " + q1.equals(q1)); // true
    }
}
