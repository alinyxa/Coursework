public class CalculationTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== ТЕСТИ РОЗРАХУНКІВ (таблиця рішень) ===\n");

        testAverageArea();
        testAverageTerm();
        testMaxCost();
        testCostPerMeter();
        testEmptyArray();
        testNegativeValues();

        System.out.println("\n=== ПІДСУМОК ===");
        System.out.println("Пройдено: " + passed);
        System.out.println("Провалено: " + failed);
        System.out.println("Всього тестів: " + (passed + failed));
    }

    static void testAverageArea() {
        double[] areas = {120.0, 180.0, 150.0};
        double result = Calculations.average(areas);
        assertEqual(150.0, result, 0.001, "testAverageArea");
    }

    static void testAverageTerm() {
        int[] days = {30, 45, 60};
        double result = Calculations.averageTerm(days);
        assertEqual(45.0, result, 0.001, "testAverageTerm");
    }

    static void testMaxCost() {
        double[] costs = {100000, 250000, 175000};
        double result = Calculations.maxCost(costs);
        assertEqual(250000.0, result, 0.001, "testMaxCost");
    }

    static void testCostPerMeter() {
        double result = Calculations.costPerMeter(200000, 100);
        assertEqual(2000.0, result, 0.001, "testCostPerMeter");
    }

    static void testEmptyArray() {
        double[] empty = {};
        double result = Calculations.average(empty);
        assertEqual(0.0, result, 0.001, "testEmptyArray");
    }

    static void testNegativeValues() {
        double result = Calculations.costPerMeter(100000, -50);
        assertEqual(0.0, result, 0.001, "testNegativeValues");
    }

    static void assertEqual(double expected, double actual, double tolerance, String testName) {
        if (Math.abs(expected - actual) <= tolerance) {
            System.out.println(testName + " — пройдено (" + actual + ")");
            passed++;
        } else {
            System.out.println(testName + " — провалено");
            System.out.println("   Очікувано: " + expected);
            System.out.println("   Отримано:  " + actual);
            failed++;
        }
    }
}

class Calculations {
    static double average(double[] values) {
        if (values == null || values.length == 0) return 0;
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }

    static double averageTerm(int[] days) {
        if (days == null || days.length == 0) return 0;
        int sum = 0;
        for (int d : days) sum += d;
        return (double) sum / days.length;
    }

    static double maxCost(double[] values) {
        if (values == null || values.length == 0) return 0;
        double max = values[0];
        for (double v : values) if (v > max) max = v;
        return max;
    }

    static double costPerMeter(double totalCost, double area) {
        if (area <= 0) return 0;
        return totalCost / area;
    }
}
