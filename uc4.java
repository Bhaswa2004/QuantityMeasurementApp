public class uc4 {

    // ===== ENUM with ALL units =====
    enum LengthUnit {
        FEET(1.0),                 // base unit
        INCH(1.0 / 12.0),          // 1 inch = 1/12 feet
        YARD(3.0),                 // 1 yard = 3 feet
        CM(0.0328084);             // 1 cm = 0.0328084 feet (0.393701 inch)

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ===== Generic Quantity Class (UNCHANGED from UC3) =====
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

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // ===== Main Method (Test cases) =====
    public static void main(String[] args) {

        // Yard ↔ Feet
        System.out.println("1 yard == 3 feet : " +
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        // Yard ↔ Inches
        System.out.println("1 yard == 36 inch : " +
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(36.0, LengthUnit.INCH)));

        // Yard ↔ Yard
        System.out.println("2 yard == 2 yard : " +
                new QuantityLength(2.0, LengthUnit.YARD)
                        .equals(new QuantityLength(2.0, LengthUnit.YARD)));

        // CM ↔ CM
        System.out.println("2 cm == 2 cm : " +
                new QuantityLength(2.0, LengthUnit.CM)
                        .equals(new QuantityLength(2.0, LengthUnit.CM)));

        // CM ↔ Inch
        System.out.println("1 cm == 0.393701 inch : " +
                new QuantityLength(1.0, LengthUnit.CM)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH)));

        // Non-equal case
        System.out.println("1 cm == 1 ft : " +
                new QuantityLength(1.0, LengthUnit.CM)
                        .equals(new QuantityLength(1.0, LengthUnit.FEET)));

        // Transitive check
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("Transitive (yard == feet && feet == inch): " +
                (yard.equals(feet) && feet.equals(inch) && yard.equals(inch)));
    }
}
