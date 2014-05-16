package com.playground.springwebapp;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.googlecode.yatspec.state.givenwhenthen.WithTestState;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class FirstAcceptanceTest implements WithTestState {

    private TestState yatspec = new TestState();

    @Test
    public void firstTest() {
        givenARequirement();
        whenIDoSomething();
        thenTheRequirementWorks();
    }

    private void givenARequirement() {
        yatspec.log("Interesting input", "mmmh, looks interesting...");
    }

    private void whenIDoSomething() {

    }

    private void thenTheRequirementWorks() {
        assertThat("my string").isNotEqualTo("this other string");
    }

    @Override
    public TestState testState() {
        return yatspec;
    }
}
