import com.passwd.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PasswrdData.class})
public class PasswrdDataTest {
    @InjectMocks
    PasswrdData passwrdDataTest;

    @Mock
    Path path;

    private List<String> inputData;
    private Passwrd passwrd;
    private HashSet<Passwrd> passwrdSet;
    private String sh;
    private Integer id;

    @Before
    public void setUp() throws IOException {
        inputData = new ArrayList<>();
        inputData.add("testName:*:0:0:thisIsAComment:home:shell");
        inputData.add("0:0");
        inputData.add("testName::::::");
        inputData.add(":0:::::");
        inputData.add(":::0:::");
        inputData.add("::::thisIsAComment::");
        inputData.add(":::::home:");
        inputData.add("::::::shell");
        inputData.add("::::::");



        PowerMockito.mockStatic(Paths.class);
        PowerMockito.mockStatic(Files.class);
        when(Paths.get(anyString())).thenReturn(path);
        when(Files.readAllLines(path)).thenReturn(inputData);
        MockitoAnnotations.initMocks(this);

        sh = "shell";
        passwrd = new Passwrd("testName", 0, 0, "thisIsAComment", "home", sh);

        passwrdSet = new HashSet<Passwrd>(Arrays.asList(passwrd));
    }

    @Test
    public void testGetUsers() throws IOException {
        HashSet<Passwrd> results = passwrdDataTest.getUsers();
        //assertThat(results).hasSize(1);
        results.stream().forEach(result -> assertThat(result).isEqualToComparingFieldByField(passwrd));

    }

    @Test
    public void testGetOptionalUsers(){
        HashSet<Passwrd> results = passwrdDataTest.getOptionalUsers("testName", 0, 0, "thisIsAComment", "home", "shell");
        results.stream().forEach(result -> assertThat(result).isEqualToComparingFieldByField(passwrd));
    }

    @Test
    public void testGetShellQuery(){
        HashSet<Passwrd> results = passwrdDataTest.getShellQuery(sh);
        results.stream().forEach(result -> assertThat(result).isEqualToComparingFieldByField(passwrd));
    }

    @Test
    public void testGetUidQuery(){
        id=0;
        Passwrd result = passwrdDataTest.getUidQuery(id);
        assertThat(result).isEqualToComparingFieldByField(passwrd);
    }


}
