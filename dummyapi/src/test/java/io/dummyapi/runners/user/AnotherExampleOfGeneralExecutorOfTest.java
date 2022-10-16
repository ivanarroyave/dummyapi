package io.dummyapi.runners.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                CreateUserRunner.class,
                DeleteUserRunner.class,
                SearchUserRunner.class,
                UpdateUserRunner.class
        }
)
public class AnotherExampleOfGeneralExecutorOfTest {
}
