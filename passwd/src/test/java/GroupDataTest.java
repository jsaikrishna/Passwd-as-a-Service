import com.passwd.Group;
import com.passwd.GroupData;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GroupData.class})
public class GroupDataTest {
    @InjectMocks
    GroupData groupDataTest;

    @Mock
    Path path;

    private List<String> inputData;

    private Group group;
    private HashSet<Group> groupSet;
    private List<String> members;
    private String name;
    private Integer id;


    @Before
    public void setUp() throws IOException {

        inputData = new ArrayList<>();
        inputData.add("testName:*:0:member1,member2");
        inputData.add("0:0");
        inputData.add(":*:0:member1,member2");
        inputData.add("testName::0:member1,member2");
        inputData.add("testName:*::member1,member2");
        inputData.add("testName:*:0:");
        inputData.add("testName:::");
        inputData.add(":*::");
        inputData.add("::0:");
        inputData.add(":::member1,member2");
        inputData.add(":::");


        PowerMockito.mockStatic(Paths.class);
        PowerMockito.mockStatic(Files.class);
        when(Paths.get(anyString())).thenReturn(path);
        when(Files.readAllLines(path)).thenReturn(inputData);
        MockitoAnnotations.initMocks(this);

        name = "testName";
        id=0;
        members = new ArrayList<String>();
        members.add("member1");
        members.add("member2");
        group = new Group(name, 0, members);
        groupSet = new HashSet<Group>(Arrays.asList(group));

    }

    @Test
    public void testGetUserGroups(){
        HashSet<Group> results = groupDataTest.getUserGroups(id);
        for(Group result : results)
            assertThat(result).isEqualToComparingFieldByField(group);
    }

    @Test
    public void testGetGroups(){
        HashSet<Group> results = groupDataTest.getGroups();
        for(Group result : results)
            assertThat(result).isEqualToComparingFieldByField(group);

    }

    @Test
    public void testGetOptionalGroup(){
        HashSet<Group> results = groupDataTest.getOptionalGroup(name, id, members);
        for(Group result : results)
            assertThat(result).isEqualToComparingFieldByField(group);

    }

    @Test
    public void testGetGroupGid(){
        Group result = groupDataTest.getGroupGid(id);
        assertThat(result).isEqualToComparingFieldByField(group);
    }
}
