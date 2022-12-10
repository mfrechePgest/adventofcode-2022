public class Day10Test extends AbstractMultiStepDayTest<Day10, Long, String> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day10Test() {
        super(() -> new Day10(SAMPLE_FILE), 13140L,
                """
                        
                        ##..##..##..##..##..##..##..##..##..##..
                        ###...###...###...###...###...###...###.
                        ####....####....####....####....####....
                        #####.....#####.....#####.....#####.....
                        ######......######......######......####
                        #######.......#######.......#######.....
                            """);
    }

}
