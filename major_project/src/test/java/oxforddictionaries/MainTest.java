package oxforddictionaries;

import oxforddictionaries.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class MainTest {
    private Main main;

    @BeforeEach
    public void setup() {
        this.main = new Main();
    }

    @Test
    public void GetInputEngineOffline() {
        InputEngine inputEngine = main.getInputEngine("offline");
        assertNotNull(inputEngine);
        assertThat(inputEngine, instanceOf(OfflineInputEngine.class));
    }

//    @Test
    public void GetInputEngineOnline() {
        InputEngine inputEngine = main.getInputEngine("online");
        assertNotNull(inputEngine);
        assertThat(inputEngine, instanceOf(OnlineInputEngine.class));
    }

    @Test
    public void GetInputEngineInvalid() {
        InputEngine inputEngine = main.getInputEngine("test");
        assertNull(inputEngine);
    }

    @Test
    public void GetOutputEngineOffline() {
        OutputEngine outputEngine = main.getOutputEngine("offline");
        assertNotNull(outputEngine);
        assertThat(outputEngine, instanceOf(OfflineOutputEngine.class));
    }

//    @Test
    public void GetOutputEngineOnline() {
        OutputEngine outputEngine = main.getOutputEngine("online");
        assertNotNull(outputEngine);
        assertThat(outputEngine, instanceOf(OnlineOutputEngine.class));
    }

    @Test
    public void GetOutputEngineInvalid() {
        OutputEngine outputEngine = main.getOutputEngine("test");
        assertNull(outputEngine);
    }
}
