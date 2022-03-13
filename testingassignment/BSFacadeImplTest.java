package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BSFacadeImplTest {
    BSFacade bsFacade;
    AuthenticationModule authenticationModule;
    AuthorisationModule authorisationModule;
    AuthenticationModule authenticationModuleMock;
    AuthorisationModule authorisationModuleMock;

    @BeforeEach
    public void setup() {
        this.bsFacade = new BSFacadeImpl();
        ERPCheatFactory erpCheatFactory = new ERPCheatFactory();
        this.authenticationModule = erpCheatFactory.getAuthenticationModule();
        this.authorisationModule = erpCheatFactory.getAuthorisationModule();
        this.authenticationModuleMock = mock(AuthenticationModule.class);
        this.authorisationModuleMock = mock(AuthorisationModule.class);

    }

    @Test
    public void addProject() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);

        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
        boolean foundProject = false;
        int project = 0;

        for (Project proj : bsFacade.getAllProjects()) {
            if (proj.getName().equals("Project") && proj.getOverDifference() == 1.0 && proj.getStandardRate() == 1.0) {
                foundProject = true;
                project += 1;
            }
        }

        assertTrue(foundProject);
        assertThat(project, equalTo(1));
    }

    @Test
    public void addProjectNullName() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject(null, "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null name.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyName() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("", "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty name.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullClient() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", null, 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null client.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyClient() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty client.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeStandardRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 0.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate smaller than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeStandardRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 100.1, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate larger than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeOverRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 0.0),
                "Didn't throw IllegalArgumentException when addProject is provided a overRate smaller than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeOverRate() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 100.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate larger than the range.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectOverRateLessThanPercentage() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 5.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate that is not 10% larger.");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullNameNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject(null, "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null name (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyNameNoModule() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("", "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty name (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullClientNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", null, 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null client (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyClientNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty client (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeStandardRateNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 0.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate smaller than the range (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeStandardRateNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 100.1, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate larger than the range (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeOverRateNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 0.0),
                "Didn't throw IllegalArgumentException when addProject is provided a overRate smaller than the range (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeOverRateNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 100.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate larger than the range (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectOverRateLessThanPercentageNoModules() {
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 5.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate that is not 10% larger (No Auth).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullNameLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject(null, "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null name (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyNameLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("", "John", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty name (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNullClientLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", null, 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a null client (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectEmptyClientLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "", 1.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a empty client (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeStandardRateLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 0.0, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate smaller than the range (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeStandardRateLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 100.1, 2.0),
                "Didn't throw IllegalArgumentException when addProject is provided a standardRate larger than the range (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectSmallerThanRangeOverRateLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 0.0),
                "Didn't throw IllegalArgumentException when addProject is provided a overRate smaller than the range (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectLargerThanRangeOverRateLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 100.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate larger than the range (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectOverRateLessThanPercentageLoggedIn() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addProject("Project", "John", 5.0, 5.1),
                "Didn't throw IllegalArgumentException when addProject is provided overRate that is not 10% larger (Logged in).");
        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
    }

    @Test
    public void addProjectNoModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.addProject("Project", "John", 1.0, 2.0),
                "Didn't throw IllegalStateException when addProject has no auth module.");

        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
        boolean foundProject = false;
        int project = 0;

        for (Project proj : bsFacade.getAllProjects()) {
            if (proj.getName().equals("Project") && proj.getOverDifference() == 1.0 && proj.getStandardRate() == 1.0) {
                foundProject = true;
                project += 1;
            }
        }

        assertFalse(foundProject);
        assertThat(project, equalTo(0));
    }

    @Test
    public void removeProject() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.removeProject(id);

        assertThat(bsFacade.getAllProjects().size(), equalTo(0));
        boolean foundProject = false;
        int project = 0;

        for (Project proj : bsFacade.getAllProjects()) {
            if (proj.getName().equals("Project") && proj.getOverDifference() == 1.0 && proj.getStandardRate() == 1.0) {
                foundProject = true;
                project += 1;
            }
        }

        assertFalse(foundProject);
        assertThat(project, equalTo(0));
    }

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
    public void removeProjectIncorrectId() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalStateException.class, () -> bsFacade.removeProject(id + 10),
                "Didn't throw IllegalStateException when removeProject is provided an incorrect id.");

        assertThat(bsFacade.getAllProjects().size(), equalTo(1));
        boolean foundProject = false;
        int project = 0;

        for (Project proj : bsFacade.getAllProjects()) {
            if (proj.getName().equals("Project") && proj.getOverDifference() == 1.0 && proj.getStandardRate() == 1.0) {
                foundProject = true;
                project += 1;
            }
        }

        assertTrue(foundProject);
        assertThat(project, equalTo(1));
    }

    @Test
    public void addTask() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertTrue(bsFacade.addTask(id, "Description", 100, false), "Failed to add task to the project.");
    }

    @Test
    public void addTaskForce() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertTrue(bsFacade.addTask(id, "Description", 90, true), "Failed to add task to the project (Force enabled).");
    }

    @Test
    public void addTaskNullDescription() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, null, 90, false),
                "Didn't throw IllegalArgumentException when addTask is provided a null description.");

        assertTrue(bsFacade.addTask(id, "Description", 20, false),
                "Added a task when a null description was provided.");
    }

    @Test
    public void addTaskEmptyDescription() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "", 90, false),
                "Didn't throw IllegalArgumentException when addTask is provided an empty description.");

        assertTrue(bsFacade.addTask(id, "Description", 20, false),
                "Added a task when an empty description was provided.");
    }

    @Test
    public void addTaskSmallerThanRangeHours() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", 0, false),
                "Didn't throw IllegalArgumentException when addTask is provided a task hour smaller than the range.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a task hour smaller than the range was provided.");
    }

    @Test
    public void addTaskLargerThanRangeHours() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", 120, false),
                "Didn't throw IllegalArgumentException when addTask is provided a task hour larger than the range.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a task hour larger than the range was provided.");
    }

    @Test
    public void addTaskNegativeHours() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.addTask(id, "Description", -50, false),
                "Didn't throw IllegalArgumentException when addTask is provided a negative task hour.");

        assertTrue(bsFacade.addTask(id, "Description", 100, false),
                "Added a task when a negative task hour was provided.");
    }

    @Test
    public void addTaskIncorrectProjectId() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id + 10, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is provided an incorrect id.");
    }

    @Test
    public void addTaskNoLogin() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.logout();
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is used without being logged in.");
    }

    @Test
    public void addTaskForceOverHours() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.addTask(id, "Description", 50, false);
        assertTrue(bsFacade.addTask(id, "Description", 70, true),
                "Failed to add task to the project when hours over project ceiling (Force enabled).");
    }

    @Test
    public void addTaskNoForceOverHours() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.addTask(id, "Description", 50, false);
        assertFalse(bsFacade.addTask(id, "Description", 70, false),
                "Failed to add task to the project when hours over project ceiling.");

        assertTrue(bsFacade.addTask(id, "Description", 50, false),
                "Added a task when the task hour was larger than the ceiling.");
    }

    @Test
    public void addTaskNoModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.addTask(id, "Description", 50, false),
                "Didn't throw IllegalStateException when addTask is used with no auth module.");
    }

    @Test
    public void setProjectCeiling() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.setProjectCeiling(id, 70);
        bsFacade.addTask(id, "Description", 80, false);
        assertFalse(bsFacade.addTask(id, "Description", 70, false),
                "Failed to add task to the project after changing ceiling.");
    }

    @Test
    public void setProjectCeilingSmallerThanRangeCeiling() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.setProjectCeiling(id, 0),
                "Didn't throw IllegalArgumentException when setProjectCeiling is provided a smaller than range ceiling.");
    }

    @Test
    public void setProjectCeilingLargerThanRangeCeiling() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalArgumentException.class, () -> bsFacade.setProjectCeiling(id, 1001),
                "Didn't throw IllegalArgumentException when setProjectCeiling is provided a larger than range ceiling.");
    }

    @Test
    public void setProjectCeilingNoModule() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.injectAuth(null, null);
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is used with no auth module.");
    }

    @Test
    public void setProjectCeilingIncorrectProjectId() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id + 10, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is provided an incorrect id.");
    }

    @Test
    public void setProjectCeilingNoLogin() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        int id = bsFacade.findProjectID("Project", "John");
        bsFacade.logout();
        assertThrows(IllegalStateException.class, () -> bsFacade.setProjectCeiling(id, 50),
                "Didn't throw IllegalStateException when setProjectCeiling is used without being logged in.");
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
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        assertThrows(IllegalStateException.class, () -> bsFacade.findProjectID("proJect", "John"),
                "Didn't throw IllegalStateException when findProjectID found no matching projects (Case sensitive).");
    }

    @Test
    public void searchProjects() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
        bsFacade.addProject("Project", "John", 1.0, 2.0);
        bsFacade.addProject("Project2", "Billy", 2.0, 4.0);
        List<Project> projects = bsFacade.searchProjects("John");
        assertThat(projects.size(), equalTo(1));
    }

    @Test
    public void searchProjectsCaseSensitive() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        bsFacade.login("user", "password");
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
        assertThrows(IllegalStateException.class, () -> bsFacade.searchProjects(null),
                "Didn't throw IllegalStateException when searchProjects is provided a null client.");
    }
}
