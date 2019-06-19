import com.passwd.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    @Mock
    GroupData mockGroupData;

    @Mock
    PasswrdData mockPaswrdData;

    @InjectMocks
    MainController mainControllerTest = new MainController();

    private Passwrd passwrd;
    private HashSet<Passwrd> passwrdSet;
    private String sh;
    private Integer id;

    private Group group;
    private HashSet<Group> groupSet;
    private List<String> members;
    private String name;


    @Before
    public void setUp(){
        name = "testName";
        sh = "shell";

        passwrd = new Passwrd(name, 0, 0, "thisIsAComment", "home", sh);
        passwrdSet = new HashSet<Passwrd>(Arrays.asList(passwrd));

        when(mockPaswrdData.getUsers()).thenReturn(passwrdSet);



        when(mockPaswrdData.getShellQuery(sh)).thenReturn(passwrdSet);

        id=0;
        when(mockPaswrdData.getUidQuery(id)).thenReturn(passwrd);

        when(mockPaswrdData.getOptionalUsers(name, 0, 0, "thisIsAComment", "home", sh)).thenReturn(passwrdSet);

        members = new ArrayList<String>();
        members.add("member1");
        members.add("member2");
        group = new Group(name, 0, members);
        groupSet = new HashSet<Group>(Arrays.asList(group));


        when(mockGroupData.getUserGroups(id)).thenReturn(groupSet);

        when(mockGroupData.getGroups()).thenReturn(groupSet);

        when(mockGroupData.getOptionalGroup(name, 0, members)).thenReturn(groupSet);

        when(mockGroupData.getGroupGid(id)).thenReturn(group);

    }

    @Test
    public void testFindUsers() {
        HashSet<Passwrd> result = mainControllerTest.findUsers();
        assertEquals(result, passwrdSet);
    }

    @Test
    public void TestOptionalUser(){
        HashSet<Passwrd> result = mainControllerTest.optionalUsers(name, 0, 0, "thisIsAComment", "home", sh);
        assertEquals(result, passwrdSet);

    }

    @Test
    public void testShellQuery(){
        HashSet<Passwrd> result = mainControllerTest.shellQuery(sh);
        assertEquals(result, passwrdSet);
    }

    @Test
    public void testUidQuery(){
        Passwrd result = mainControllerTest.uidQuery(id);
        assertEquals(result, passwrd);

    }

    @Test
    public void testGetIdGroups(){
       HashSet<Group> result = mainControllerTest.getIdGroups(id);
       assertEquals(result, groupSet);
    }

    @Test
    public void testGetGroups(){
        HashSet<Group> result = mainControllerTest.getGroups();
        assertEquals(result, groupSet);
    }

    @Test
    public void testOptionalGroupUsers(){
        HashSet<Group> result = mainControllerTest.optionalGroupUsers(name, id, members);
        assertEquals(result, groupSet);
    }

    @Test
    public void testGetGidGroup(){
        Group result = mainControllerTest.getGidGroup(id);
        assertEquals(result, group);
    }
}
