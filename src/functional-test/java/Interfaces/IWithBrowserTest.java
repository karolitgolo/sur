package Interfaces;

/**
 * The interface With browser test.
 */
public interface IWithBrowserTest {

    /**
     * Before.
     *
     * @throws Exception the exception
     */
    void setup() throws Exception;

    /**
     * Test.
     *
     * @throws Exception the exception
     */
    void test() throws Exception;

    /**
     * After.
     *
     * @throws Exception the exception
     */
    void tearDown() throws Exception;
}
