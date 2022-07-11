## The Order Shipping sample project copied from Spring State Machine

This an example of porting a Spring State Machine sample project into a Maven build. Assumes Maven is installed locally.

The parent project Gradle is quite large and has many sub-projects so porting a child project to a Maven build was a bit of trial and error.

The parent project is here https://github.com/spring-projects/spring-statemachine

The sample project is here https://github.com/spring-projects/spring-statemachine/tree/main/spring-statemachine-samples/ordershipping

The ordershipping project uses a UML file to configure its state machine

Some minor additions were made:

    Added a REST API
    added some webjars
    added Bootstrap CSS 
    added jQuery for testing REST API

The UML project is somewhat embedded into the resources. It can be opened in Papyrus, an Eclipse based UML editor.
Here is a gist illustrating roughly how to import a UML project into Papyrus https://gist.github.com/gregbown/cdab7f73511e3ba8c1325a79d6218653
