package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packagesOf = ArchitectureTest.class, importOptions = DoNotIncludeTests.class)
class ArchitectureTest {
    /**
     * Checks that a class is not accessed by any other classes. This should normally apply to e.g. Controllers.
     */
    static class NeverBeAccessedByAnotherClass extends ArchCondition<JavaClass> {

        NeverBeAccessedByAnotherClass() {
            super("never be accessed by another class");
        }

        @Override
        public void check(final JavaClass item, final ConditionEvents events) {
            item.getAccessesToSelf()
                .stream()
                .filter(javaAccess -> !item.equals(javaAccess.getOriginOwner()))
                .map(javaAccess -> {
                    final String message =
                        String.format("Found access to class '%s': %s.", item.getName(), javaAccess.getDescription());
                    return SimpleConditionEvent.violated(javaAccess, message);
                })
                .forEach(events::add);
        }
    }

    static NeverBeAccessedByAnotherClass neverBeAccessedByAnotherClass() {
        return new NeverBeAccessedByAnotherClass();
    }

    @ArchTest
    static final ArchRule NO_CLASSES_SHOULD_USE_CONTROLLERS = classes()
        .that()
        .areAnnotatedWith(RestController.class)
        .should(neverBeAccessedByAnotherClass());
}
