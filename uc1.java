public class QuantityMeasurementApp {

    // Inner class to represent Feet measurement
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals() method
        @Override
        public boolean equals(Object obj) {
            // Same reference check (reflexive)
            if (this == obj) {
                return true;
            }

            // Null check and type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Type casting
            Feet other = (Feet) obj;

            // Compare values using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method
    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        // Test cases
        System.out.println("1.0 ft == 1.0 ft : " + f1.equals(f2)); // true
        System.out.println("1.0 ft == 2.0 ft : " + f1.equals(f3)); // false
        System.out.println("1.0 ft == null   : " + f1.equals(null)); // false
        System.out.println("Same reference   : " + f1.equals(f1)); // true
    }
}