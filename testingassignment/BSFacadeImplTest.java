package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BSFacadeImplTest {
    BSFacade bsFacade;
    AuthenticationModule authenticationModule;
    AuthorisationModule authorisationModule;

    @BeforeEach
    public void setup() {
        this.bsFacade = new BSFacadeImpl();
        ERPCheatFactory erpCheatFactory = new ERPCheatFactory();
        this.authenticationModule = erpCheatFactory.getAuthenticationModule();
        this.authorisationModule = erpCheatFactory.getAuthorisationModule();
    }

    @Test
    public void addProject() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
        assertThat(bsFacade.getAllProjects(), containsInAnyOrder(proj));
        assertThat(proj.getName(), equalTo("Project"));
        assertThat(proj.getStandardRate(), equalTo(1.0));
        assertThat(proj.getOverDifference(), equalTo(1.0));
    }

    @Test
    public void addProjectNullName() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject(null, "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null name.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyName() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("", "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty name.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullClient() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", null, 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null client.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyClient() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty client.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeStandardRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 0.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate smaller than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeStandardRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 100.1, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate larger than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeOverRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 0.0),
                "Didn't throw IllegalArgumentException when addProject is provided a overRate smaller than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeOverRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 100.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate larger than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectOverRateLessThanPercentage() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 5.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate that is not 10% larger.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNoAuthModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.addProject("Project", "John", 1.0, 2.0),
                "Didn't throw IllegalStateException when addProject has no auth module.");

        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
        assertThat(bsFacade.getAllProjects(), containsInAnyOrder(proj));
    }

    @Test
    public void addProjectNoBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");

        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.addProject("Project", "John", 1.0, 2.0),
                "Didn't throw IllegalStateException when addProject is used with no basic user.");
    }

    @Test
    public void addProjectNoLogin() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalStateException.class, () -> bsFacade.addProject("Project", "John", 1.0, 2.0),
                "Didn't throw IllegalStateException when addProject is used with no user logged in.");
    }

//    @Test
//    public void removeProject() {
//        bsFacade.injectAuth(authenticationModule, authorisationModule);
//        bsFacade.login("user", "password");
//        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
//        int id = proj.getId();
//        bsFacade.removeProject(id);
//
//        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
//    }

    @Test
    public void removeProjectNoModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.removeProject(100),
                "Didn't throw IllegalStateException when removeProject is called without an auth module set.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void removeProjectBasicSecureIncorrectId() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.removeProject(id + 10),
                "Didn't throw IllegalStateException when removeProject is provided an incorrect id.");

        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
        assertThat(bsFacade.getAllProjects(), containsInAnyOrder(proj));
    }

    @Test
    public void removeProjectBasicSecureUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        bsFacade.removeProject(id);

        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void removeProjectBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.removeProject(id),
                "Didn't throw IllegalStateException when removeProject using a basic user.");
    }
    @Test
    public void removeProjectNoUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.logout();
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.removeProject(id),
                "Didn't throw IllegalStateException when removeProject is used with no logged in user.");
    }

    @Test
    public void addTaskSecure() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 100, false),
                "Didn't throw IllegalStateException when addTask is used with a secure user.");
    }

    @Test
    public void addTaskSecureForce() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertTrue(bsFacade.addTask(id, "Description", 90, true), "Failed to add task to the project (Force enabled).");
    }

    @Test
    public void addTaskBasicNullDescription() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, null, 90, false),
                "Didn't throw IllegalArgumentException when addTask is provided a null description.");

        assertTrue(bsFacade.addTask(id, "Description", 20, false),
                "Added a task when a null description was provided.");
    }

    @Test
    public void addTaskBasicEmptyDescription() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "", 90, false),
                "Didn't throw IllegalArgumentException when addTask is provided an empty description.");

        assertTrue(bsFacade.addTask(id, "Description", 20, false),
                "Added a task when an empty description was provided.");
    }

    @Test
    public void addTaskBasicSmallerThanRangeHours() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", 0, false),
                "Didn't throw IllegalArgumentException when addTask is provided a task hour smaller than the range.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a task hour smaller than the range was provided.");
    }

    @Test
    public void addTaskBasicLargerThanRangeHours() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", 120, false),
                "Didn't throw IllegalArgumentException when addTask is provided a task hour larger than the range.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a task hour larger than the range was provided.");
    }

    @Test
    public void addTaskBasicNegativeHours() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", -50, false),
                "Didn't throw IllegalArgumentException when addTask is provided a negative task hour.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a negative task hour was provided.");
    }

    @Test
    public void addTaskBasicIncorrectProjectId() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id + 10, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is provided an incorrect id.");
    }

    @Test
    public void addTaskNoLogin() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(false);
        bsFacade.logout();
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is used without being logged in.");
    }

    @Test
    public void addTaskBasicSecureForceOverHours() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        bsFacade.addTask(id, "Description", 50, false);
        assertTrue(bsFacade.addTask(id, "Description", 70, true),
                "Failed to add task to the project when hours over project ceiling (Force enabled).");
    }

    @Test
    public void addTaskNoForceOverHours() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        bsFacade.addTask(id, "Description", 50, false);
        assertFalse(bsFacade.addTask(id, "Description", 70, false),
                "Failed to add task to the project when hours over project ceiling.");

        assertTrue(bsFacade.addTask(id, "Description", 50, false),
                "Added a task when the task hour was larger than the ceiling.");
    }

    @Test
    public void addTaskNoModule() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is used with no auth module.");
    }

    @Test
    public void addTaskBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Failed to add task to the project using a basic user.");
    }

    @Test
    public void addTaskBasicUserForce() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 100, true),
                "Didn't throw IllegalStateException when addTask force is used with a basic user.");
    }

    @Test
    public void addTaskBasicSecureUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertTrue(bsFacade.addTask(id, "Description", 100, false), "Failed to add task to the project using a secure user.");
    }

//    @Test
//    public void addTaskBasicSecureUserForce() {
//        AuthToken authTokenMock = mock(AuthToken.class);
//        when(authTokenMock.getToken()).thenReturn("token");
//        when(authTokenMock.getUsername()).thenReturn("username");
//
//        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
//        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
//        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);
//
//        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
//        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
//        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(true);
//
//        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);
//
//        bsFacade.login("username", "password");
//        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
//        int id = proj.getId();
//        assertTrue(bsFacade.addTask(id, "Description", 100, true), "Failed to add task to the project using a secure user.");
//    }

    @Test
    public void setProjectCeilingSecure() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        bsFacade.setProjectCeiling(id, 70);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        bsFacade.addTask(id, "Description", 60, false);
        assertFalse(bsFacade.addTask(id, "Description", 70, false),
                "Failed to add task to the project after changing ceiling when using a secure user.");
    }

    @Test
    public void setProjectCeilingSmallerThanRangeCeiling() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.setProjectCeiling(id, 0),
                "Didn't throw IllegalArgumentException when setProjectCeiling is provided a smaller than range ceiling.");
    }

    @Test
    public void setProjectCeilingLargerThanRangeCeiling() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.setProjectCeiling(id, 1001),
                "Didn't throw IllegalArgumentException when setProjectCeiling is provided a larger than range ceiling.");
    }

    @Test
    public void setProjectCeilingNoModule() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is used with no auth module.");
    }

    @Test
    public void setProjectCeilingIncorrectProjectId() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id + 10, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is provided an incorrect id.");
    }

    @Test
    public void setProjectCeilingNoLogin() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        bsFacade.logout();
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is used without being logged in.");
    }

    @Test
    public void setProjectCeilingBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is used with basic user.");
    }

    @Test
    public void setProjectCeilingBasicSecureUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(authTokenMock)).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(authTokenMock, false)).thenReturn(true);
        when(authorisationModuleMock.authorise(authTokenMock, true)).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = proj.getId();
        bsFacade.setProjectCeiling(id, 50);
        assertFalse(bsFacade.addTask(id, "Description", 70, false),
                "Failed to add task to the project after changing ceiling as basic secure user.");
    }

    @Test
    public void findProjectID() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThat(id, equalTo(proj.getId()));
    }

    @Test
    public void findProjectIDNullName() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.findProjectID(null, "John"),
                "Didn't throw IllegalArgumentException when findProjectID was provided a null name.");
    }

    @Test
    public void findProjectIDNullClient() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.findProjectID("Project", null),
                "Didn't throw IllegalArgumentException when findProjectID was provided a null client.");
    }

    @Test
    public void findProjectIDNoMatching() {
        assertThrows(IllegalStateException.class, () -> bsFacade.findProjectID("Project", "John"),
                "Didn't throw IllegalStateException when findProjectID found no matching projects.");
    }

    @Test
    public void findProjectIDNoMatchingCaseSensitive() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        assertThrows(IllegalStateException.class, () -> bsFacade.findProjectID("proJect", "John"),
                "Didn't throw IllegalStateException when findProjectID found no matching projects (Case sensitive).");
    }

    @Test
    public void searchProjects() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addProject("Project2", "Billy", 2.0, 4.0);
        List<Project> projects = bsFacade.searchProjects("John");
        assertThat(projects.size(), equalTo(1));
        assertThat(projects, containsInAnyOrder(proj));
    }

    @Test
    public void searchProjectsCaseSensitive() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addProject("Project2", "Billy", 2.0, 4.0);
        List<Project> projects = bsFacade.searchProjects("john");
        assertThat(projects.size(), equalTo(0));
    }

    @Test
    public void searchProjectsEmpty() {
        List<Project> projects = bsFacade.searchProjects("John");
        assertThat(projects.size(), equalTo(0));
    }

    @Test
    public void searchProjectsNullClient() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.searchProjects(null),
                "Didn't throw IllegalArgumentException when searchProjects is provided a null client.");
    }

    @Test
    public void audit() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ComplianceReporting complianceReportingMock = mock(ComplianceReporting.class);
        bsFacade.injectCompliance(complianceReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        Project proj2 = bsFacade.addProject("Project2", "Billy", 2.0, 4.0);
        Project proj3 = bsFacade.addProject("Project3", "Smith", 1.5, 3.0);
        bsFacade.addTask(proj.getId(), "Description", 60, true);
        bsFacade.addTask(proj.getId(), "Description", 60, true);
        bsFacade.addTask(proj2.getId(), "Description", 50, false);
        bsFacade.addTask(proj3.getId(), "Description", 60, true);
        bsFacade.addTask(proj3.getId(), "Description", 50, true);

        bsFacade.audit();
        verify(complianceReportingMock, times(2)).sendReport(anyString(), anyInt(), eq(authTokenMock));
    }

    @Test
    public void auditNoAuthModule() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ComplianceReporting complianceReportingMock = mock(ComplianceReporting.class);
        bsFacade.injectCompliance(complianceReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addTask(proj.getId(), "Description", 50, true);
        bsFacade.addTask(proj.getId(), "Description", 70, true);

        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.audit(),
                "Didn't throw IllegalStateException when no authentication module has been set for audit.");
    }

    @Test
    public void auditNoCompliance() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addTask(proj.getId(), "Description", 60, true);
        bsFacade.addTask(proj.getId(), "Description", 60, true);

        assertThrows(IllegalStateException.class, () -> bsFacade.audit(),
                "Didn't throw IllegalStateException when no compliance module has been set for audit.");
    }

    @Test
    public void auditNoSecureUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ComplianceReporting complianceReportingMock = mock(ComplianceReporting.class);
        bsFacade.injectCompliance(complianceReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addTask(proj.getId(), "Description", 70, true);
        bsFacade.addTask(proj.getId(), "Description", 50, true);

        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.audit(),
                "Didn't throw IllegalStateException when not using a secure user for audit.");
    }

    @Test
    public void finaliseProjectBasicSecure() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ClientReporting clientReportingMock = mock(ClientReporting.class);
        bsFacade.injectClient(clientReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        bsFacade.finaliseProject(proj.getId());
        verify(clientReportingMock, times(1)).sendReport(eq("John"), anyString(), eq(authTokenMock));
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void finaliseProjectSecure() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ClientReporting clientReportingMock = mock(ClientReporting.class);
        bsFacade.injectClient(clientReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.finaliseProject(proj.getId()),
                "Didn't throw IllegalStateException when not using a secure user for finaliseProject.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
    }

    @Test
    public void finaliseProjectNoAuthModule() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ClientReporting clientReportingMock = mock(ClientReporting.class);
        bsFacade.injectClient(clientReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.finaliseProject(proj.getId()),
                "Didn't throw IllegalStateException when no authentication module has been set for finaliseProject.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
    }

    @Test
    public void finaliseProjectNoClient() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.finaliseProject(proj.getId()),
                "Didn't throw IllegalStateException when no client reporting module has been set for finaliseProject.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
    }

    @Test
    public void finaliseProjectBasicUser() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(false))).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        ClientReporting clientReportingMock = mock(ClientReporting.class);
        bsFacade.injectClient(clientReportingMock);

        bsFacade.login("username", "password");
        Project proj = bsFacade.addProject("Project", "John", 1.0, 2.0);

        when(authorisationModuleMock.authorise(eq(authTokenMock), eq(true))).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> bsFacade.finaliseProject(proj.getId()),
                "Didn't throw IllegalStateException when using a basic user for finaliseProject.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
    }

    @Test
    public void injectAuthNullAuthentication() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.injectAuth(null, authorisationModule),
                "Didn't throw IllegalArgumentException when only authentication was provided a null for finaliseProject.");
    }

    @Test
    public void injectAuthNullAuthorisation() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.injectAuth(authenticationModule, null),
                "Didn't throw IllegalArgumentException when only authorisation was provided a null for finaliseProject.");
    }

    @Test
    public void login() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertTrue(bsFacade.login("user", "password"), "Failed to login with correct credentials.");
    }

    @Test
    public void loginIncorrect() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertFalse(bsFacade.login("username", "pass"), "Logged in with incorrect credentials.");
    }

    @Test
    public void loginNullUsername() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.login(null, "password"),
                "Didn't throw IllegalArgumentException when username is null for login.");
    }

    @Test
    public void loginNullPassword() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.login("user", null),
                "Didn't throw IllegalArgumentException when username is null for login.");
    }

    @Test
    public void loginNoAuthModule() {
        assertThrows(IllegalStateException.class, () -> bsFacade.login("user", "password"),
                "Didn't throw IllegalStateException when the authentication module has not be set for login.");
    }

    @Test
    public void loginWhileLoggedIn() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthToken authTokenMock2 = mock(AuthToken.class);
        when(authTokenMock2.getToken()).thenReturn("token2");
        when(authTokenMock2.getUsername()).thenReturn("test");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(eq("username"), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.login(eq("test"), anyString())).thenReturn(authTokenMock2);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);
        when(authenticationModuleMock.authenticate(eq(authTokenMock2))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);
        when(authorisationModuleMock.authorise(eq(authTokenMock2), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);
        bsFacade.login("username", "password");
        assertTrue(bsFacade.login("test", "test"),
                "Failed to login when provided correct login credentials while already logged in.");

        verify(authenticationModuleMock, times(1)).logout(authTokenMock);
    }

    @Test
    public void logout() {
        AuthToken authTokenMock = mock(AuthToken.class);
        when(authTokenMock.getToken()).thenReturn("token");
        when(authTokenMock.getUsername()).thenReturn("username");

        AuthenticationModule authenticationModuleMock = mock(AuthenticationModule.class);
        when(authenticationModuleMock.login(anyString(), anyString())).thenReturn(authTokenMock);
        when(authenticationModuleMock.authenticate(eq(authTokenMock))).thenReturn(true);

        AuthorisationModule authorisationModuleMock = mock(AuthorisationModule.class);
        when(authorisationModuleMock.authorise(eq(authTokenMock), anyBoolean())).thenReturn(true);

        bsFacade.injectAuth(authenticationModuleMock, authorisationModuleMock);

        bsFacade.login("username", "password");
        bsFacade.logout();
        verify(authenticationModuleMock, times(1)).logout(eq(authTokenMock));
    }

    @Test
    public void logoutNotLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalStateException.class, () -> bsFacade.logout(),
                "Didn't throw IllegalStateException when logout was used with no logged in user.");
    }

    @Test
    public void logoutNoAuthModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.logout(),
                "Didn't throw IllegalStateException when logout was used with no auth modules.");
    }

}
