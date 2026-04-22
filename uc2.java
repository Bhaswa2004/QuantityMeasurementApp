public class uc2 {

    // ===== Feet Class =====
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ===== Inches Class =====
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ===== Static Methods (as required) =====
    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    // ===== Main Method =====
    public static void main(String[] args) {

        // Feet comparison
        System.out.println("1.0 ft == 1.0 ft : " + compareFeet(1.0, 1.0));
        System.out.println("1.0 ft == 2.0 ft : " + compareFeet(1.0, 2.0));

        // Inches comparison
        System.out.println("1.0 inch == 1.0 inch : " + compareInches(1.0, 1.0));
        System.out.println("1.0 inch == 2.0 inch : " + compareInches(1.0, 2.0));

        // Edge cases
        Feet f = new Feet(1.0);
        System.out.println("Feet vs null : " + f.equals(null)); // false
        System.out.println("Same reference : " + f.equals(f));  // true
    }
}
